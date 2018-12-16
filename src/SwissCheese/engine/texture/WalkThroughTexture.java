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

/**
 * {@code WalkThroughTexture} is a texture object through which a player can
 * walk. This texture is intended to be used on exit walls. When a person walks
 * through the texture, a method will be ran to do a specific action.
 * 
 * @author Alex Kalinins
 * @since 2018-12-13
 * @since v0.3
 * @version v0.1
 */
public class WalkThroughTexture extends WallTexture {
	public WalkThroughTexture(File file) {
		super(file);
	}

	public WalkThroughTexture(String fileLoc) {
		super(fileLoc);
	}

	public boolean walkThrough() {
		// TODO game closes with a congratulatory message
		return true;
	}

}
