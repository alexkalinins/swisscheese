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
package engine.keyboard;

import engine.keyboard.keyActions.*;

/**
 * ENUMS for the Keyboard. All the usable keys for the game. While the list is
 * limited, ENUMS allows more keys to be added in the future as needed.
 * <p>
 * All Keys are initialized as without an action. Key Action Setter will be used
 * to load the setting from a file. User can change the preferences and
 * key-bindings, and the game will open with the new key bindings.
 * 
 * @author Alex Kalinins
 * @since 2018-12-1
 * @since v0.2
 * @version v0.1
 */
@ThreadSafe
public enum Keys {
	A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z, N1, N2, N3, N4, N5, N6, N7, N8, N9,
	N0, SHIFT, ESC, UP, DOWN, LEFT, RIGHT, SPACE, ENTER, BACK_SPACE;

	private KeyAction action;

	/**
	 * Sets the action of the Key ENUM to a specific action
	 * 
	 * @param action new action
	 */
	public synchronized void setAction(KeyAction action) {
		this.action = action;
	}

	/**
	 * Calls action to do the action
	 */
	public synchronized void doAction() {
		action.doAction();
	}

	/**
	 * Calls action to stop an action. Only applicable to continuous actions such as
	 * walking.
	 */
	public synchronized void stopAction() {
		action.stopAction();
	}

	/**
	 * KeyAction Getter
	 * 
	 * @return
	 */
	public synchronized KeyAction getAction() {
		return action;
	}
}
