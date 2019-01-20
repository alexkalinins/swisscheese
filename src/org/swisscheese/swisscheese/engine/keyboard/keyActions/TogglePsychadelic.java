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

import org.swisscheese.swisscheese.engine.rendering.Renderer;

/**
 * Toggles the <code>psychadelic</code> boolean of {@link Renderer}.
 * <p>
 * Causes the renderer to not clear the screen and not make the background fill.
 * 
 * @author Alex Kalinins
 * @since 2019-01-20
 * @since v0.5
 * @version v1.0
 */
public class TogglePsychadelic implements KeyAction {

	@Override
	public void doAction() {
		Renderer.makePsychadelic();

	}

	@Override
	public void stopAction() {
		return;

	}

}
