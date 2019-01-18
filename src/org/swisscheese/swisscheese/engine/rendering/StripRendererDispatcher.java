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
package org.swisscheese.swisscheese.engine.rendering;

import java.util.concurrent.ExecutionException;

import org.swisscheese.swisscheese.annotations.Hack;
import org.swisscheese.swisscheese.annotations.ThreadSafe;
import org.swisscheese.swisscheese.engine.camera.Camera;
import org.swisscheese.swisscheese.engine.details.MultithreadedRendererDetails;
import org.swisscheese.swisscheese.engine.details.RendererDetails;
import org.swisscheese.swisscheese.map.Map;
import org.swisscheese.swisscheese.texturePacks.TexturePack;

/**
 * A class for dispatching the threads to work on {@link RenderStrip} class. The
 * "Strip-Rendering" strategy involves assigning each vertical scan of the
 * screen "Strip" to a new thread to render. However, the performance of the
 * <code>StripRendererDispatcher</code> is equal to or worse than
 * {@link SingleThreadedRenderer}, likely as a result of object creation. <br>
 * (n times more objects and primitives created, where n is the width of the
 * screen).
 * 
 * @author Alex Kalinins
 * @since 2019-01-06
 * @since v0.5
 * @version v1.0
 */
@Hack(reason="inheritance")
@ThreadSafe
public class StripRendererDispatcher extends MultithreadedRendererDispatcher {
	/** The pixels array to which the screen is being rendered. */
	private volatile int[] pixels;
	/** {@link RendererDetails} from {@link Renderer}. */
	private final RendererDetails details = getDetails();

	/**
	 * {@inheritDoc}
	 */
	StripRendererDispatcher(float width, float height, TexturePack texturePack, Camera camera, Map map, int nThreads)
			throws IllegalStateException {
		super(width, height, texturePack, camera, map, nThreads, (int) width);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public StripRendererDispatcher(MultithreadedRendererDetails details, Camera camera) {
		super(details, camera, (int)details.width);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int[] render(int[] pixels) {
		pixels = fillBackground(pixels);
		this.pixels = pixels;
		view = camera.getView();

		// loading all strips into a Future Deque.
		for (int counter = 0; counter < details.width; counter++) {
			futureQueue.addLast(pool.submit(new RenderStrip(this, counter, view)));
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
		return this.pixels;
	}

	void insertValue(int value, int index) {
		pixels[index] = value;
	}
}