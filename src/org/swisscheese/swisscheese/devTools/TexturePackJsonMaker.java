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
package org.swisscheese.swisscheese.devTools;

import org.swisscheese.swisscheese.texturePacks.TexturePack;

import com.google.gson.Gson;

/**
 * Makes texture pack JSON code. Will be deleted later.
 * 
 * @author Alex Kalinins
 *
 */
public class TexturePackJsonMaker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		serialize();
	}

	private static void serialize() {
		Gson gson = new Gson();
		String[] paths = { "/texture-packs/%name%/entry.png", "/texture-packs/%name%/exit.png",
				"/texture-packs/%name%/wall1.png", "/texture-packs/%name%/wall2.png",
				"/texture-packs/%name%/wall3.png" };


		TexturePack pack = new TexturePack("Vanilla", "Alex Kalinins", "v1.0", paths);
		String code = gson.toJson(pack);
		System.out.println(code);

	}

}
