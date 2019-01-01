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

import org.swisscheese.swisscheese.engine.io.gson.Convertable;

/**
 * Interface for Key Actions of buttons. Key Actions are actions that are done
 * when a key is pressed and/or released. Each KeyAction has a doAction, which
 * is done when the key is pressed, and a stopAction which is executed when the
 * key is released.
 * <p>
 * Some actions are single actions (such as opening a menu), in that case,
 * stopAction would do nothing, and the action will be done in doAction.
 * <p>
 * All implementing methods should have a static {@code getDesc()} String method
 * that returns a description of what the key action does.
 * <p>
 * It is assumed that all implementing methods contain no fields, and just call
 * other <i>static</i> methods. Therefore, the <code>equals</code>
 * implementation does not look at any fields, but rather if an object is an
 * instance of implementing class.
 * <p>
 * This interface extends the {@link Convertable} interface in order for the
 * {@code GSON} library to work. Every time a new class implementing the
 * {@code KeyAction} interface is written or the name is changed,
 * {@link org.swisscheese.swisscheese.devTools.DefaultKeyBindCreator} must be run.
 * 
 * @author Alex Kalinins
 * @since 2018-12-1
 * @since v0.2
 * @version v0.2
 */
public interface KeyAction extends Convertable {

	/**
	 * Does an action (only for continuous actions).
	 */
	public void doAction();

	/**
	 * Stops doing an action. (only applicable to continuous actions such as
	 * walking).
	 */
	public void stopAction();
}
