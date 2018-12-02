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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import engine.keyboard.*;
import engine.keyboard.keyActions.*;

/**
 * Creates a default setting file for KeyActionPreference
 * @author alex
 *
 */
public class DefaultFileCreator {
	public static KeyActionPreference p;
	
	public static void load() {
		p = new KeyActionPreference();
		p.setPreference(new GoForward(), new GoLeft(), new GoBackward(), new GoRight(),
				new LookUp(), new LookDown(), new LookLeft(), new LookRight(),
				new OpenMenu(), null, null, null, null, null,null, null, null,
				null,null, null);
	}
	
	public static void main(String[] args) {
		load();
		writeToFile(p);
	}
	
	/**
	 * Serializes and writes preferences to file (/settings/user/keybind.config)
	 */
	public static void writeToFile(KeyActionPreference p) {
		File file = new File("settings/default/keybind.config");
		if (file.exists()) {
			file.delete();
		}
		try (FileOutputStream fileOut = new FileOutputStream(file);
				ObjectOutputStream objOut = new ObjectOutputStream(fileOut)) {
			objOut.writeObject(p);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
