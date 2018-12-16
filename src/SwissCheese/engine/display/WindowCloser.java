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
 * @author alex
 *
 */
public class WindowCloser {
	
	public synchronized static void closeEverything(WindowEvent e) {
		System.out.println("window closed by user");
		try {

			Mover.stopAllThreads();
			GameLoop.stop();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.exit(0);
	}
}
