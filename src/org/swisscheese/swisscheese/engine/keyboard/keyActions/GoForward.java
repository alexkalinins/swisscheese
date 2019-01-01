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

import org.swisscheese.swisscheese.annotations.ThreadSafe;
import org.swisscheese.swisscheese.engine.display.Window;

/**
 * Go Forward Action (moves camera forward).
 * 
 * @author Alex Kalinins
 * @since 2018-12-1
 * @since v0.2
 * @version v0.2
 */
@ThreadSafe
public class GoForward implements KeyAction {
	public GoForward() {

	}

	@Override
	public synchronized void doAction() {
		Window.mover.moveForward();
	}

	public static String getDesc() {
		return "Walk Forward";
	}

	@Override
	public synchronized void stopAction() {
		Window.mover.stopForward();
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj || obj instanceof GoForward;
	}

	@Override
	public int hashCode() {
		return GoForward.class.hashCode();
	}

}
