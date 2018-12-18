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

/**
 * Opens The Menu
 * 
 * @author Alex Kalinins
 * @since 2018-12-1
 * @since v0.2
 * @version v0.1
 */
public class OpenMenu implements KeyAction {

	public OpenMenu() {
	}

	@Override
	public void doAction() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return "Opens Menu";
	}

	/**
	 * Menu Opening is not a continuous action
	 */
	@Override
	public void stopAction() {
		throw new UnsupportedOperationException();
	}

}
