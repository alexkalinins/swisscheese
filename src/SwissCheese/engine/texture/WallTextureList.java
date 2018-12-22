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
import java.util.ArrayList;
import java.util.List;

import SwissCheese.annotations.Immutable;

/**
 * This class loads images from a list of {@code File} objects into a list of
 * {@code WallTexture} objects.
 * 
 * @author Alex Kalinins
 * @since 2018-12-15
 * @since v0.3
 * @version v0.1
 */
@Immutable
public class WallTextureList {
	private final List<File> fileList = new ArrayList<>();

	/**
	 * Constructor
	 */
	public WallTextureList() {
		fileList.add(new File("resources/textures/test.bmp"));
		fileList.add(new File("resources/textures/test1.png"));
		fileList.add(new File("resources/textures/test2.png"));
	}

	/**
	 * Loads a list of textures
	 * @return
	 */
	public List<WallTexture> getList() {
		List<WallTexture> list = new ArrayList<>();
//		for (File file : fileList) {
//			list.add(new WallTexture(file));
//		}
		list.add(new WallTexture(fileList.get(0)));
		list.add(new WallTexture(fileList.get(1)));
		list.add(new WalkThroughTexture(fileList.get(2)));
		return list;
	}
}
