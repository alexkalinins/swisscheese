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
package org.swisscheese.swisscheese.engine.details;

import java.util.List;

import org.swisscheese.swisscheese.annotations.Immutable;
import org.swisscheese.swisscheese.engine.texture.WallTexture;

/**
 * A RendererDetails object for multi-threaded renderers.
 * 
 * @author Alex Kalinins
 * @since 2019-01-17
 * @since v0.5
 * @version v1.0
 */
@Immutable
public class MultithreadedRendererDetails extends RendererDetails {
	public final int nThreads;

	/**
	 * Constructor for {@code MultithreadedRendererDetails}.
	 * 
	 * {@inheritDoc}
	 * 
	 * @param nThreads the number of threads the multithreaded renderer uses.
	 */
	public MultithreadedRendererDetails(float width, float height, List<WallTexture> wallTextures, int[][] maze,
			int nThreads) {
		super(width, height, wallTextures, maze);
		this.nThreads = nThreads;
	}

	/**
	 * Constructor for creating Details object from the superclass and number of
	 * threads
	 * 
	 * @param from     a {@link RendererDetails} object
	 * @param nThreads the number of threads
	 */
	public MultithreadedRendererDetails(RendererDetails from, int nThreads) {
		super(from.width, from.height, from.wallTextures, from.maze);
		this.nThreads = nThreads;
	}

	/**
	 * Extracts the a regular {@link RendererDetails} from this object.
	 * 
	 * @return returns extracted {@link RendererDetails}.
	 */
	public RendererDetails getRegularDetails() {
		return new RendererDetails(width, height, wallTextures, maze);
	}

}
