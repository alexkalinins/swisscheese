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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.swisscheese.swisscheese.engine.io.gson.InterfacetoJson;
import org.swisscheese.swisscheese.engine.io.gson.JsonAdapterRegistrar;
import org.swisscheese.swisscheese.engine.keyboard.KeyActionPreference;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.ExitGame;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.GoBackward;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.GoForward;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.GoLeft;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.GoRight;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.KeyAction;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.OpenMenu;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.PanLeft;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.PanRight;

import com.google.gson.Gson;

/**
 * Creates a default setting file for KeyActionPreference. Uses GSON
 * 
 * @author Alex Kalinins
 * @version v0.2
 *
 */
class DefaultKeyBindCreator {
	public static KeyActionPreference p;
	public static Gson gson;

	@SuppressWarnings("deprecation")
	public static void load() {
		// don't to use 'full' constructor.
		p = new KeyActionPreference(new GoForward(), new GoLeft(), new GoBackward(), new GoRight(), null, null,
				new PanLeft(), new PanRight(), new OpenMenu(), null, null, null, null, null, null, null, null, null,
				null, new ExitGame());
	}

	public static void main(String[] args) {
		gson = JsonAdapterRegistrar.makeGson(KeyAction.class, new InterfacetoJson<KeyAction>());
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
