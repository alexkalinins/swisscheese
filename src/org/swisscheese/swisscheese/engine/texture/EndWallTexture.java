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
package org.swisscheese.swisscheese.engine.texture;

import java.io.File;

import org.swisscheese.swisscheese.annotations.Immutable;
import org.swisscheese.swisscheese.engine.camera.Mover;

/**
 * {@code WalkThroughTexture} is a texture object through which a player can
 * walk. This texture is intended to be used on exit walls. When a person walks
 * through the texture, a method will be ran to do a specific action.
 * 
 * @author Alex Kalinins
 * @since 2018-12-13
 * @since v0.3
 * @version v1.0
 */
@Immutable
public class EndWallTexture extends WallTexture {
	public EndWallTexture(File file) {
		super(file);
	}

	public EndWallTexture(String fileLoc) {
		super(fileLoc);
	}

	private boolean done = false;
	@Override
	public void doAction() {
		if(!done) {
			done = true;
			System.out.println("User completed maze");
			Mover.close();
			//Congratulations message
		}
	}

}
