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
package SwissCheese.engine.keyboard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import com.google.gson.Gson;

import SwissCheese.annotations.Immutable;
import SwissCheese.engine.io.gson.InterfacetoJson;
import SwissCheese.engine.io.gson.JsonAdapterRegistrar;
import SwissCheese.engine.keyboard.keyActions.KeyAction;

/**
 * Reads and writes preferences to a file.
 * <p>
 * Default preferences and user preferences are saved into separate files. If a
 * user preference file is missing (when the game is loaded for the first time),
 * the default preferences will be used. User preference file is deleted if the
 * user wishes to revert settings to default.
 * <p>
 * GSON library is used to serialize and deserialize KeyActionPreference, as it
 * is safer than native Serialization.
 * 
 * @author Alex Kalinins
 * @since 2018-12-1
 * @since v0.2
 * @version v0.4
 */
@Immutable
public class KeyPreferenceIO {

	/**
	 * Calls {@code JsonAdapterRegistrar} to register InterfacetoJson adapter to
	 * adapt KeyAction interface
	 */
	private static Gson makeGson() {
		return JsonAdapterRegistrar.makeGson(KeyAction.class, new InterfacetoJson<KeyAction>());
	}

	/**
	 * Reads and deserializes preferences from a file. Uses GSON to deserialize
	 * <p>
	 * The file being read is keybind.config
	 */
	public static void readFromFile() {
		Gson gson = makeGson();
		File settings = new File(
				(checkForSaved()) ? "settings/user/keybind.config" : "settings/default/keybind.config");
		StringBuilder sb = new StringBuilder();
		String line;
		try (BufferedReader in = new BufferedReader(new FileReader(settings))) {
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		line = sb.toString();
		KeyActionPreference p = gson.fromJson(line, KeyActionPreference.class);
		p.bindPreferences();
	}

	/**
	 * Serializes and writes preferences to file (/settings/user/keybind.config)
	 */
	public static void writeToFile(KeyActionPreference p) {
		Gson gson = makeGson();
		File file = new File("settings/user/keybind.config");
		if (file.exists()) {
			file.delete();
		}

		try (PrintStream out = new PrintStream(new FileOutputStream(file))) {
			out.print(gson.toJson(p));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			p.bindPreferences();
		}
	}

	/**
	 * Checks if the user has any preferences saved
	 * 
	 * @return true if a preference file is present.
	 */
	private static boolean checkForSaved() {
		return new File("settings/user/keybind.config").exists();

	}
}
