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
package org.swisscheese.swisscheese.engine.imageEffects;

/**
 * A state enum to track if the image that is being affected by
 * {@link ChangeGamma} will need to be darkened, brightened, or to remain
 * normal.
 * 
 * @author Alex Kalinins
 * @since 2019-01-19
 * @since v0.5
 * @version v1.0
 */
public enum GammaState {
	NORMAL, DARK, BRIGHT,;
}
