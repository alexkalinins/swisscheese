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
package SwissCheese.engine.display;

import java.awt.event.WindowEvent;

import SwissCheese.engine.camera.Mover;
import SwissCheese.game.GameLoop;

/**
 * Class for WindowCloser window listener. Properly closes the game when the
 * user closes the window.
 * 
 * @author Alex Kalinins
 * @since 2018-12-15
 * @since v0.3
 * @version v0.1
 */
public class WindowCloser {

	/**
	 * Stops the game
	 * @param e WindowEvent
	 */
	public synchronized static void closeEverything() {
		System.out.println("window closed by user");
		Mover.stopAllThreads();
		GameLoop.stop();
		System.exit(0);
	}
}
