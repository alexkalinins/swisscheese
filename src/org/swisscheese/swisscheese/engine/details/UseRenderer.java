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

import org.swisscheese.swisscheese.engine.display.Window;
import org.swisscheese.swisscheese.engine.rendering.RendererType;

/**
 * A class that contains the {@link RendererType} enum and number of threads to
 * create a renderer. Number of threads will be set to one if not provided.
 * <p>
 * A {@link UseRenderer} object will be passed to {@link Window} in order to
 * create the renderer from the selected type. It will also be saved with the
 * rest of game-save.
 * 
 * @author Alex Kalinins
 * @since 2019-01-18
 * @since v0.5
 * @version v1.0
 */
public class UseRenderer {
	public final RendererType type;
	public final int nThreads;

	/**
	 * A constructor.
	 * 
	 * @param type     the type of renderer that will be used.
	 * @param nThreads
	 */
	public UseRenderer(RendererType type, int... nThreads) {
		this.nThreads = (nThreads.length == 0) ? 1 : nThreads[0];
		this.type = type;
	}
}
