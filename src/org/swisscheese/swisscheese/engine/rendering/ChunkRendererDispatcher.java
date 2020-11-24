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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.swisscheese.swisscheese.annotations.ThreadSafe;
import org.swisscheese.swisscheese.engine.camera.Camera;
import org.swisscheese.swisscheese.engine.camera.View;
import org.swisscheese.swisscheese.engine.details.MultithreadedRendererDetails;
import org.swisscheese.swisscheese.engine.imageEffects.ChangeGamma;
import org.swisscheese.swisscheese.engine.imageEffects.GammaState;
import org.swisscheese.swisscheese.map.Map;
import org.swisscheese.swisscheese.texturePacks.TexturePack;

/**
 * A Chunk Renderer dispatcher. This rendering strategy involves dividing the
 * screen vertically into chunks and assigning each chunk to be rendered.
 * <p>
 * This allows chunks to be reused more easily than the strips. The only
 * possible issue with chunk rendering is if one thread lags behind forcing the
 * entire rendering engine to wait (blocking operation).
 * 
 * @author Alex Kalinins
 * @since 2019-01-14
 * @since v0.5
 * @version v1.0
 */
@ThreadSafe
public class ChunkRendererDispatcher extends MultithreadedRendererDispatcher {
	private int[] pixels;
	private int chunkSize;
	private List<RenderChunk> chunkList = new ArrayList<>();

	/**
	 * {@inheritDoc}}
	 * 
	 * @param nChunks the number of chunks and number of threads used (must divide
	 *                width with no remainder)
	 * @throws IllegalArgumentException - if width is not divisible by nChunks
	 *                                  without remainder.
	 * 
	 */
	ChunkRendererDispatcher(float width, float height, TexturePack texturePack, Camera camera, Map map,
			int nChunks) throws IllegalStateException, IllegalArgumentException {
		super(width, height, texturePack, camera, map, nChunks, nChunks);
		if (width % nChunks != 0 || nChunks > 16)
			throw new IllegalArgumentException("Width must be devidable by nChunks and less than 16");
		chunkSize = (int) (width / nChunks);
		View view = camera.getView();
		for (int i = 0; i < nChunks; i++) {
			chunkList.add(new RenderChunk(this, i * chunkSize, (i + 1) * chunkSize, view));
		}
	}

	ChunkRendererDispatcher(MultithreadedRendererDetails details, Camera camera) {
		super(details, camera, details.nThreads);
		if (details.width % details.nThreads != 0 || details.nThreads > 16)
			throw new IllegalArgumentException("Width must be devidable by nChunks and less than 16");
		chunkSize = (int) (details.width / details.nThreads);
		View view = camera.getView();
		for (int i = 0; i < details.nThreads; i++) {
			chunkList.add(new RenderChunk(this, i * chunkSize, (i + 1) * chunkSize, view));
		}
	}

	@Override
	public int[] render(int[] pixels) {
		if(!psychadelic) {
		this.pixels = fillBackground(pixels);
		if (state == GammaState.DARK) {
			for (int i = 0; i < this.pixels.length; i++) {
				this.pixels[i] = ChangeGamma.getColor(this.pixels[i], 0.5f);
			}
		} else if (state == GammaState.BRIGHT) {
			for (int i = 0; i < this.pixels.length; i++) {
				this.pixels[i] = ChangeGamma.getColor(this.pixels[i], 2f);
			}
		}
		}
		View view = camera.getView();

		// updating and loading futures.
		for (int i = 0; i < chunkList.size(); i++) {
			chunkList.get(i).update(view);
			futureQueue.addLast(pool.submit(chunkList.get(i)));
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
