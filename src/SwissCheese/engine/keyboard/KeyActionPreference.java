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
package SwissCheese.engine.keyboard;

import SwissCheese.engine.keyboard.keyActions.KeyAction;

/**
 * Class for setting and binding the key binding preferences to the game. Action preference
 * must be added to parameters every single time a new key or action is added to
 * the game.
 * <p>
 * This object is serialized and deserialized by the {@code GSON} library.
 * 
 * @author Alex Kalinins
 * @since 2018-12-1
 * @since v0.2
 * @version v0.3
 */
public final class KeyActionPreference {
	private final KeyAction wAction;
	private final KeyAction aAction;
	private final KeyAction sAction;
	private final KeyAction dAction;
	private final KeyAction upAction;
	private final KeyAction downAction;
	private final KeyAction leftAction;
	private final KeyAction rightAction;
	private final KeyAction escAction;
	private final KeyAction shiftAction;
	private final KeyAction n1Action;
	private final KeyAction n2Action;
	private final KeyAction n3Action;
	private final KeyAction n4Action;
	private final KeyAction n5Action;
	private final KeyAction n6Action;
	private final KeyAction n7Action;
	private final KeyAction n8Action;
	private final KeyAction n9Action;
	private final KeyAction n0Action;

	/**
	 * No arguments constructor necessary for GSON serialization.
	 */
	public KeyActionPreference() {
		this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null);
	}

	//Constructor: no javadoc, KeyActions might change
	public KeyActionPreference(KeyAction wAction, KeyAction aAction, KeyAction sAction, KeyAction dAction,
			KeyAction upAction, KeyAction downAction, KeyAction leftAction, KeyAction rightAction, KeyAction escAction,
			KeyAction shiftAction, KeyAction n1Action, KeyAction n2Action, KeyAction n3Action, KeyAction n4Action,
			KeyAction n5Action, KeyAction n6Action, KeyAction n7Action, KeyAction n8Action, KeyAction n9Action,
			KeyAction n0Action) {
		this.wAction = wAction;
		this.aAction = aAction;
		this.sAction = sAction;
		this.dAction = dAction;
		this.upAction = upAction;
		this.downAction = downAction;
		this.leftAction = leftAction;
		this.rightAction = rightAction;
		this.escAction = escAction;
		this.shiftAction = shiftAction;
		this.n1Action = n1Action;
		this.n2Action = n2Action;
		this.n3Action = n3Action;
		this.n4Action = n4Action;
		this.n5Action = n5Action;
		this.n6Action = n6Action;
		this.n7Action = n7Action;
		this.n8Action = n8Action;
		this.n9Action = n9Action;
		this.n0Action = n0Action;
	}

	/**
	 * Sets the action of each {@code Keys} enums to the specified {@code KeyAction}.
	 */
	public void bindPreferences() {
		Keys.W.setAction(wAction);
		Keys.A.setAction(aAction);
		Keys.S.setAction(sAction);
		Keys.D.setAction(dAction);
		Keys.UP.setAction(upAction);
		Keys.DOWN.setAction(downAction);
		Keys.LEFT.setAction(leftAction);
		Keys.RIGHT.setAction(rightAction);
		Keys.ESC.setAction(escAction);
		Keys.SHIFT.setAction(shiftAction);
		Keys.N1.setAction(n1Action);
		Keys.N2.setAction(n2Action);
		Keys.N3.setAction(n3Action);
		Keys.N4.setAction(n4Action);
		Keys.N5.setAction(n5Action);
		Keys.N6.setAction(n6Action);
		Keys.N7.setAction(n7Action);
		Keys.N8.setAction(n8Action);
		Keys.N9.setAction(n9Action);
		Keys.N0.setAction(n0Action);

	}

}
