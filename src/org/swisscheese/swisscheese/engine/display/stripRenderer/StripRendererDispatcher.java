/**
 * Copyright (C) 2018 Alex Kalinins
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.swisscheese.swisscheese.engine.display.stripRenderer;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.swisscheese.swisscheese.engine.camera.Camera;
import org.swisscheese.swisscheese.engine.camera.Mover;
import org.swisscheese.swisscheese.engine.camera.View;
import org.swisscheese.swisscheese.engine.display.CanRender;
import org.swisscheese.swisscheese.engine.texture.WallTextureList;
import org.swisscheese.swisscheese.map.Map;
import org.swisscheese.swisscheese.texturePacks.TexturePack;

/**
 * A class for dispatching the threads to work on {@link StripRenderer} class.
 * 
 * @author Alex Kalinins
 *
 */
public class StripRendererDispatcher implements CanRender {
	private final Camera camera;
	private View view;
	private volatile int[] pixels;
	private static RendererDetails details;

	private final Color FLOOR = Color.DARK_GRAY; // the color of the floor
	private final Color SKY = Color.cyan; // the color of the sky
	private final ArrayBlockingQueue<Runnable> threadPoolQueue; // BLOCKING!!!!
	private final ThreadPoolExecutor pool;
	private final Deque<Future<Void>> futureQueue = new ArrayDeque<>();

	public StripRendererDispatcher(Map map, Camera camera, TexturePack texturePack, float width, float height,
			int nThreads) {
		details = new RendererDetails(width, height, new WallTextureList(texturePack).getList(), map.getMap());
		threadPoolQueue = new ArrayBlockingQueue<Runnable>((int) details.width);
		pool = new ThreadPoolExecutor(nThreads, 64, 100, TimeUnit.MILLISECONDS, threadPoolQueue);

		this.camera = camera;
		// pool = Executors.newFixedThreadPool(nThreads);
	}

	public int[] render(int[] pixels) {
		pixels = fillBackground(pixels);
		this.pixels = pixels;
		view = camera.getView();

		// loading all strips into a Future Deque.
		for (int counter = 0; counter < details.width; counter++) {
			int[] strip = Arrays.copyOfRange(pixels, (int) details.height * counter,
					(int) details.height * (counter + 1));
			assert (strip.length == details.height);
			futureQueue.addLast(pool.submit(new StripRenderer(this, strip, counter, view)));
		}

		// executing futures.
		try {
			do {
				try {
					// a blocking operation
					futureQueue.removeFirst().get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			} while (!futureQueue.isEmpty());
		} finally {
			// making sure the queues are empty
			futureQueue.clear();
			threadPoolQueue.clear();
		}

		// working, but not rendering
		return this.pixels;
	}

	/**
	 * Merges <code>strip</code> with <code>pixels</code> based on the
	 * <code>stripNumber</code>
	 * 
	 * @param strip       the strip of RGB values being copied into the pixels array
	 * @param stripNumber the number of the strip (the width pixel number that was
	 *                    rendered). <strong> This number count starts count at
	 *                    0</strong>.
	 */
	synchronized void mergeWithPixels(Integer[] strip, int stripNumber) {
		int[] intStrip = Arrays.stream(strip).mapToInt(Integer::intValue).toArray();
		assert (intStrip.length == details.height);

//		System.arraycopy(intStrip,
//				(int) (stripNumber * details.height), pixels, 0, (int) details.height);
		try {
			System.arraycopy(intStrip, 0, pixels, (int) (stripNumber * details.height), (int) details.height);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.printf(
					"System.arraycopy exeption.%nStripNumber: %d%nDestinationIndex: %.0f%nStrip size: %d%nPixels size: %d%nWould-be Last Index: %.0f%n",
					stripNumber, stripNumber * details.height, intStrip.length, pixels.length,
					(stripNumber * details.height) + details.height);
			System.exit(-1);
		}
	}

	synchronized void insertValue(int value, int index) {
		pixels[index] = value;
	}

	/**
	 * Fills the background (with SKY and FLOOR color). Also clears
	 * <code>pixels</code>.
	 * 
	 * @param pixels pixels array
	 * @return pixels with filled background.
	 */
	private int[] fillBackground(int[] pixels) {
		for (int i = 0; i < pixels.length / 2; i++) {
			pixels[i] = SKY.getRGB();
		}

		// colors pixels below midway point
		for (int i = pixels.length / 2; i < pixels.length; i++) {
			pixels[i] = FLOOR.getRGB();
		}
		return pixels;
	}

	public static final RendererDetails getDetails() {
		return details;
	}

	public Mover getMover() {
		return camera.getMover();
	}
}
