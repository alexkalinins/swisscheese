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
package org.swisscheese.swisscheese.annotations;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Target;

@Target(TYPE)
/**
 * A tag for temporary implementations (a.k.a. "Hacks"). Such hacks may have been
 * implemented to satisfy project criteria, and are likely considered bad
 * practice/unstable. These "Hack" may or may not re-implemented to a better
 * implementation.
 * 
 * @author Alex Kalinins
 * @since 2019-01-15
 * @since v0.5
 * @version v1.0
 */
public @interface Hack {
	/**The reason why the type is a hack*/
	String reason();
}
