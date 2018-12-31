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
package SwissCheese.engine.keyboard.keyActions;

import SwissCheese.annotations.Immutable;
import SwissCheese.engine.display.WindowCloser;

/**
 * Quits Game (WITHOUT SAVING)
 * 
 * @author Alex Kalinins
 * @since 2018-12-1
 * @since v0.2
 * @version v0.2
 */
@Immutable
public class ExitGame implements KeyAction {
	public ExitGame() {
	}

	@Override
	public synchronized void doAction() {
		WindowCloser.closeEverything();
	}

	public static String getDesc() {
		return "Quits Game (NO SAVE!)";
	}

	@Override
	public synchronized void stopAction() {
		return;
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj || obj instanceof ExitGame;
	}
	
	@Override
	public int hashCode() {
		return ExitGame.class.hashCode();
	}

}
