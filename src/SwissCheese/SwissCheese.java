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
package SwissCheese;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import SwissCheese.game.GameFromSettings;
import SwissCheese.settings.GameSettings;
import SwissCheese.uiWindows.StartMenu;

/**
 * This is the main class of SwissCheese.
 * 
 * @author Alex Kalinins
 * @since 2018-12-27
 * @since v0.5
 * @version v1.0
 */
public class SwissCheese {
	private final GameFromSettings game;

	/**
	 * 
	 */
	public SwissCheese() {
		StartMenu menu = new StartMenu();
		GameSettings settings;
		if (menu.isNewGame()) {
			settings = menu.getSettings();
			game = GameFromSettings.newGame(settings, menu.getDiff(), menu.getGameName());
		} else if (!menu.isNewGame()) {
			settings = menu.getSettings();
			game = GameFromSettings.fromGameSave(settings, menu.getGameSave());
		} else {
			// Boolean is null, player didn't start new game
			game = null;
			System.out.println("The user didn't start game");
			System.exit(0);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		new SwissCheese();
	}

}
