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
package devTools;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;

import engine.keyboard.KeyActionPreference;
import engine.keyboard.keyActions.*;

/**
 * Creates a default setting file for KeyActionPreference. Uses GSON
 * 
 * @author Alex Kalinins
 * @version v0.2
 *
 */
public class DefaultFileCreator {
	public static KeyActionPreference p;
	public static Gson gson;

	public static void load() {
		p = new KeyActionPreference();
		p.setPreference(new GoForward(), new GoLeft(), new GoBackward(), new GoRight(), null, null,
				new LookLeft(), new LookRight(), new OpenMenu(), null, null, null, null, null, null, null, null, null,
				null, null);
	}

	public static void main(String[] args) {
		gson = new Gson();
		load();
		writeToFile(p);
	}

	/**
	 * Serializes and writes preferences to file (/settings/user/keybind.config).
	 * Now using GSON
	 */
	public static void writeToFile(KeyActionPreference p) {
		File file = new File("settings/default/keybind.config");
		if (file.exists()) {
			file.delete();
		}

		String jsonString = gson.toJson(p);

		try (PrintWriter out = new PrintWriter(file)) {
			out.write(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(jsonString);
	}

}
