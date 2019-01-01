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
package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import SwissCheese.devTools.GameCleaner;
import SwissCheese.gameSaving.GameSave;
import SwissCheese.gameSaving.GameSaveList;
import SwissCheese.gameSaving.GameSaveManager;
import SwissCheese.gameSaving.SaveMetadata;
import SwissCheese.settings.GameSettings;
import SwissCheese.settings.GameSettingsManager;

/**
 * A test to see if {@link GameCleaner} deletes files.
 * 
 * @author Alex Kalinins
 * @since v0.5
 * @since 2018-12-30
 */
class GameCleanerTest {
	/** The file location of the {@link GameSettings} file */
	private static final File SETTINGS = new File("settings/game-settings.json");
	/** The file location of the {@link GameSaveList} file */
	private static final File SAVES = new File("settings/user-saves/list.json");

	/**
	 * Creates a null {@link GameSave} and null {@link GameSettings}. Calls upon
	 * their respective serializing classes. Calls {@link GameCleaner} to delete the
	 * created files.
	 * 
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		GameSave save = new GameSave(null, null, SaveMetadata.makeMetadata("hello world"));
		GameSaveManager.getInstance().saveGame(save);

		@SuppressWarnings("deprecation")
		GameSettings settings = new GameSettings();
		GameSettingsManager.MANAGER.setSettings(settings);

		GameCleaner.main(new String[0]);
	}

	/**
	 * Tests to see that the files deleted by {@link GameCleaner} are in-fact
	 * deleted.
	 */
	@Test
	void test() {
		assertTrue(!SETTINGS.exists());
		assertTrue(!SAVES.exists());
	}

}
