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

import java.util.List;

import org.swisscheese.swisscheese.annotations.Immutable;
import org.swisscheese.swisscheese.engine.texture.WallTexture;

/**
 * A class to make an object that groups all the fields of the
 * {@link StripRendererDispatcher} together in order to pass it to
 * {@link StripRenderer}.
 * <p>
 * A {@code RednererDetails} object will not change during the game.
 * 
 * @author Alex Kalinins
 * @since 2019-01-05
 * @since v0.5
 * @version v1.0
 */
@Immutable
public class RendererDetails {
	// package private
	final float width;
	final float height;
	final List<WallTexture> wallTextures;
	final int[][] maze;

	/**
	 * Constructor.
	 * 
	 * @param width        width of the screen
	 * @param height       height of the screen
	 * @param wallTextures list of {@link WallTexture} objects
	 * @param maze         maze of the game
	 */
	public RendererDetails(float width, float height, List<WallTexture> wallTextures, int[][] maze) {
		this.width = width;
		this.height = height;
		this.wallTextures = wallTextures;
		this.maze = maze;
	}
}
