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

/**
 * Enum used to create a new renderer from a saved file.
 * 
 * @author Alex Kalinins
 */
public enum RendererType {
	STRIP, CHUNK, SINGLE_THREAD,;

	/**
	 * Returns a {@link RendererType} from <code>renderer</code>.
	 * 
	 * @param renderer from which type returned
	 * @return enumn {@link RendererType}
	 */
	public static RendererType fromRenderer(Renderer renderer) {
		if (renderer instanceof SingleThreadedRenderer)
			return SINGLE_THREAD;
		else if (renderer instanceof ChunkRendererDispatcher)
			return CHUNK;
		else if (renderer instanceof StripRendererDispatcher)
			return STRIP;
		else
			throw new IllegalArgumentException();
	}
}
