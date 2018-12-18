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
package SwissCheese.engine.texture;

import java.io.File;

import SwissCheese.annotations.Immutable;

/**
 * A WallTexture object for storing a wall texture
 * 
 * @author Alex Kalinins
 * @since 2018-12-10
 * @since v0.2
 * @version v0.1
 */
@Immutable
public class WallTexture extends AbstractTexture{
	private final int size;
	
	public WallTexture(File file) {
		super(file);
		size = image.getWidth();
	}
	
	public WallTexture(String fileLocation) {
		super(fileLocation);
		size = image.getWidth();
	}
	
	public int getSize() {
		return size;
	}
}
