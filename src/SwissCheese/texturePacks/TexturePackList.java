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
package SwissCheese.texturePacks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

/**
 * A list of {@link TexturePack} objects that are available for the user to
 * select. The list is build every single time the game is stated.
 * 
 * @author Alex Kalinins
 * @since v0.5
 * @since 2018-12-27
 * @version v1.0
 */
public class TexturePackList {
	private final List<TexturePack> list;
	private final Gson gson = new Gson();

	public static final TexturePackList LIST = new TexturePackList();

	/**
	 * Private constructor of the {@code TexturePackList}. Since the list will not
	 * change during the game runtime, the object does not need to be
	 * re-constructed. The only way to interact with {@code TexturePackList} is with
	 * the public static final {@code LIST}.
	 */
	private TexturePackList() {
		list = makeList();
	}

	private List<TexturePack> makeList() {
		List<TexturePack> list = new ArrayList<>();

		File dir = new File("texture-packs");
		File[] existingFiles = dir.listFiles();

		for (File file : existingFiles) {
			if (file.isDirectory() && (file = new File(file, "textures.json")).exists()) {
				try (BufferedReader br = new BufferedReader(new FileReader(file))) {
					StringBuilder builder = new StringBuilder();
					String s;
					while ((s = br.readLine()) != null) {
						builder.append(s);
					}

					s = builder.toString();

					TexturePack tp = gson.fromJson(s, TexturePack.class);
					tp.make();
					list.add(tp);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

		return list;
	}

	public final List<TexturePack> getList() {
		return list;
	}

}
