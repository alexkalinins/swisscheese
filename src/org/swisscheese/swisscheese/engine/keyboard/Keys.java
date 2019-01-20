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
package org.swisscheese.swisscheese.engine.keyboard;

import org.swisscheese.swisscheese.annotations.ThreadSafe;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.KeyAction;

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
 * @version v1.0
 */
@ThreadSafe
public enum Keys {
	A("A"), B("B"), C("C"), D("D"), E("E"), F("F"), G("G"), H("H"), I("I"), J("J"), K("K"), L("L"), M("M"), N("N"),
	O("O"), P("P"), Q("Q"), R("R"), S("S"), T("T"), U("U"), V("V"), W("W"), X("X"), Y("K"), Z("Z"), N1("1"), N2("2"),
	N3("3"), N4("4"), N5("5"), N6("6"), N7("7"), N8("8"), N9("9"), N0("0"), SHIFT("Shift"), ESC("Esc"), UP("Up"),
	DOWN("Down"), LEFT("Left"), RIGHT("Right"), SPACE("Space"), ENTER("Enter"), BACK_SPACE("Back Space");

	private KeyAction action;
	private final String name;
	
	/**
	 * Private constructor
	 * @param name name of enum
	 */
	private Keys(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
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
	 * 
	 * @throws InterruptedException
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
