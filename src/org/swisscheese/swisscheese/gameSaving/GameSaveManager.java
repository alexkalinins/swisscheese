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
package org.swisscheese.swisscheese.gameSaving;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.swisscheese.swisscheese.annotations.ThreadSafe;

import com.google.gson.Gson;

/**
 * Class for managing serialized {@link GameSave} objects. Operations such as
 * creation (serialization), opening (deserialization) and deletion could be
 * done through this {@code GameSaveManager} class.
 * <p>
 * This class saves all {@code GameSaves} to a list, and then saves the list to
 * file. All {@code GameSave} objects are in the same file.
 * <p>
 * All actions involving serialization are done through the {@code GSON}
 * library.
 * 
 * @author Alex Kalinins
 * @since 2018-12-28
 * @since v0.5
 * @version v1.0
 *
 * @see com.google.gson.Gson
 */
@ThreadSafe
public final class GameSaveManager {
	private final GameSaveList list;
	private final Gson gson;
	private static final File FILE = new File("settings/user-saves/list.json");

	private static volatile GameSaveManager MANAGER;

	/**
	 * Lazy initialization for {@code GameSaveManager}.
	 * <p>
	 * If {@code MANAGER} is null, then this method calls the constructor to create
	 * a new instance of {@code GameSaveManager}. Otherwise, no new instance is
	 * created. In any case, the method returns {@code MANAGER}.
	 * 
	 * @return MANAGER
	 */
	public static synchronized final GameSaveManager getInstance() {
		if (MANAGER == null) {
			MANAGER = new GameSaveManager();
		}

		return MANAGER;
	}

	/**
	 * Private constructor.
	 */
	private GameSaveManager() {
		if (MANAGER != null) {
			throw new RuntimeException("Use getInstance() to get an instane of GameSaveManager");
		}
		gson = new Gson();
		if (FILE.exists()) {
			list = readFromFile();
		} else {
			list = makeList();
		}
	}

	/**
	 * Adds the {@link GameSave} {@code save} to {@code list}, calls
	 * {@link GameSaveList#sort()} and calls
	 * {@link GameSaveManager#writeToFile(GameSaveList)}. Also deletes any duplicate
	 * {@code GameSave} objects..
	 * 
	 * @param save the {@code GameSave} being added to the list and saved.
	 */
	public void saveGame(GameSave save) {
		// checking for duplicate games.
		// search is not necessary if list empty
		if (list.size() != 0) {
			int val = searchList(save);
			if (val != -1) {
				// deleting duplicate game.
				deleteGame(val);
			}
		}
		list.add(save);
		list.sort();
		writeToFile(list);
	}

	/**
	 * Deletes a game of {@code index} from the list. If the list is empty (size ==
	 * 0), {@code FILE} is deleted.
	 * 
	 * @param index the index of the {@code GameSave} being deleted.
	 */
	public synchronized void deleteGame(int index) {
		System.out.printf("Deleting a GameSave: [%s]%n", list.get(index).toString());
		list.remove(index);
		if (list.size() == 0) {
			System.out.println("Deleting GameSaveList file");
			FILE.delete();
		} else {
			writeToFile(list);
		}
	}

	/**
	 * Creates a new {@code GameSaveList} object.
	 * <p>
	 * Should only be done if a no existing {@code GameSaveList} objects exist in
	 * serialized form.
	 * 
	 * @return a new {@code GameSaveList}
	 */
	private GameSaveList makeList() {
		GameSaveList list = new GameSaveList();
		writeToFile(list);
		return list;
	}

	/**
	 * Opens file and deserializes {@code GameSaveList}.
	 * 
	 * @return a {@code GameSaveList} from {@code FILE}.
	 */
	private GameSaveList readFromFile() {
		String s;
		StringBuilder builder = new StringBuilder();

		try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
			while ((s = reader.readLine()) != null) {
				builder.append(s);
			}
			s = builder.toString();
			return gson.fromJson(s, GameSaveList.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Linear search of the list to find {@code save}. Linear search was used
	 * instead of binary search because comparison must be made based on
	 * {@code save}'s identifier and name and not its {@code Calendar}
	 * 
	 * @param save the query of the search
	 * @return the index of the found save or -1 if the search was unsuccessful.
	 */
	private int searchList(GameSave save) {
		System.out.println("Searching GameSaveList");
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(save)) {
				System.out.println("Game found on index " + i);
				return i;
			}
		}
		System.out.println("GameSaveList search unsuccessful");
		return -1;
	}

	/**
	 * Serializes and writes {@code list} to the {@code FILE} File.
	 * 
	 * @param list a {@link GameSaveList} being serialized.
	 */
	private void writeToFile(GameSaveList list) {
		if(!FILE.exists()) {
			try {
				FILE.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String jsonCode = gson.toJson(list);
		try (PrintWriter writer = new PrintWriter(FILE)) {
			writer.print(jsonCode);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public final List<GameSave> getList() {
		return list.getList();
	}
}
