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

import org.swisscheese.swisscheese.annotations.Hack;
import org.swisscheese.swisscheese.annotations.Immutable;
import org.swisscheese.swisscheese.engine.camera.Camera;
import org.swisscheese.swisscheese.engine.details.MultithreadedRendererDetails;
import org.swisscheese.swisscheese.engine.details.RendererDetails;
import org.swisscheese.swisscheese.map.Map;
import org.swisscheese.swisscheese.texturePacks.TexturePack;

/**
 * A Factory that creates renderer objects. All classes extending
 * {@link Renderer} must be instantiated through {@code RendererFactory}. Forces
 * the client to use {@link Renderer} rather than its subclasses.
 * 
 * @author Alex Kalinins
 * @since 2019-01-14
 * @since v0.5
 * @version v1.0
 */
@Hack(reason = "inheritance")
@Immutable
public final class RendererFactory {

	/** A private constructor - do not instantiate */
	private RendererFactory() {
		throw new SecurityException("RendererFactory not meant to be instantiated");
	}

	/**
	 * Creates a new renderer based on the <code>type</code> enum.
	 * 
	 * @param type    the type of the renderer
	 * @param details the details of the renderer
	 * @param camera  the camera that is used.
	 * @return a new {@link Renderer} instance
	 * @throws IllegalArgumentException - thrown if:
	 *                                  <p>
	 *                                  <ul>
	 *                                  <li><code>details</code> <b>not</b> an
	 *                                  instance of
	 *                                  {@link MultithreadedRendererDetails} if
	 *                                  declaring a sub-class of
	 *                                  {@link MultithreadedRendererDispatcher}.</li>
	 *                                  <li><code>details</code> <b>is</b> an
	 *                                  instance of
	 *                                  {@link MultithreadedRendererDetails} when
	 *                                  creating a
	 *                                  {@link SingleThreadedRenderer}</li>
	 *                                  <li><code>type</code> is an invalid
	 *                                  enum</li>
	 *                                  </ul>
	 */
	public static Renderer createThreadFromEnum(RendererType type, RendererDetails details, Camera camera)
			throws IllegalArgumentException {
		switch (type) {
		case STRIP:
			if (!(details instanceof MultithreadedRendererDetails)) {
				throw new IllegalArgumentException("Invalid RendererDetails");
			}
			return new StripRendererDispatcher((MultithreadedRendererDetails) details, camera);
		case CHUNK:
			if (!(details instanceof MultithreadedRendererDetails)) {
				throw new IllegalArgumentException("Invalid RendererDetails");
			}
			return new ChunkRendererDispatcher((MultithreadedRendererDetails) details, camera);
		case SINGLE_THREAD:
			if (details instanceof MultithreadedRendererDetails) {
				throw new IllegalArgumentException("Invalid RendererDetails");
			}
			return new SingleThreadedRenderer(details, camera);
		default:
			throw new IllegalArgumentException("Invalid RendererType");
		}
	}

	/**
	 * A public static factory method for {@link SingleThreadedRenderer}
	 * 
	 * @return a new instance of SingleThreadedRenderer.
	 * @see SingleThreadedRenderer
	 */
	public static Renderer createSingleThreadedRenderer(float width, float height, TexturePack texturePack,
			Camera camera, Map map) {
		return new SingleThreadedRenderer(width, height, texturePack, camera, map);
	}

	/**
	 * A public static factory method for {@link StripRendererDispatcher}.
	 * 
	 * @return a new instance of StripRendererDispatcher.
	 * @see StripRendererDispatcher
	 */
	public static Renderer createStripRenderer(float width, float height, TexturePack texturePack, Camera camera,
			Map map, int nThreads) {
		return new StripRendererDispatcher(width, height, texturePack, camera, map, nThreads);
	}

	/**
	 * A public static factory method for {@link ChunkRendererDispatcher}.
	 * 
	 * @return a new instance of StripRendererDispatcher.
	 * @see ChunkRendererDispatcher
	 */
	public static Renderer createChunkRenderer(float width, float height, TexturePack texturePack, Camera camera,
			Map map, int nChunks) {
		return new ChunkRendererDispatcher(width, height, texturePack, camera, map, nChunks);
	}

}
