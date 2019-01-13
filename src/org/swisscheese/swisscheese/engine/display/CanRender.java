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
package org.swisscheese.swisscheese.engine.display;

import org.swisscheese.swisscheese.engine.camera.Camera;
import org.swisscheese.swisscheese.engine.camera.Mover;

/**
 * An interface for rendering engines.
 * 
 * @author Alex Kalinins
 * @since 2019-01-05
 * @since v0.5
 * @version v1.0
 */
public interface CanRender {
	/**
	 * Renders the <code>pixels</code> array.
	 * 
	 * @param pixels array of RGB values being rendered
	 * @return a rendered <code>pixel</code> array.
	 */
	public int[] render(int[] pixels);

	/**
	 * Updates and gets {@link Mover}.
	 * 
	 * @return a {@link Mover} from {@link Camera}.
	 */
	public Mover getMover();

}
