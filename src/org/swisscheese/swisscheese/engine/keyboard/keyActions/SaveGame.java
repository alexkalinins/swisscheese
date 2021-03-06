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
package org.swisscheese.swisscheese.engine.keyboard.keyActions;

/**
 * Saves Game
 * 
 * @author Alex Kalinins
 * @since 2018-12-1
 * @since v0.2
 * @version v1.0
 */
public class SaveGame implements KeyAction {
	public SaveGame() {
	}

	@Override
	public synchronized void doAction() {
		throw new UnsupportedOperationException();
	}

	public static String getDesc() {
		return "Save Game";
	}

	@Override
	public synchronized void stopAction() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean equals(Object obj) {
		return  this == obj || obj instanceof SaveGame;
	}
	
	@Override
	public int hashCode() {
		return SaveGame.class.hashCode();
	}

}
