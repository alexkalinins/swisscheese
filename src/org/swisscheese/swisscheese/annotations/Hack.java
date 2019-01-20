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

/**
 * An annotation for a type containing temporary implementations, a so called
 * "<i>Hack</i>". Such <i>hacks</i> may have been implemented <b>to satisfy
 * project criteria</b>, and are likely considered bad practice/unstable. These
 * <i>Hack</i> may or may not re-implemented to a better implementation.
 * <p>
 * If a type is annotated as a "Hack", its bad design should be outstandingly
 * obvious. Class may be considered as a <i>hack</i> if it contains (but not
 * limited to):
 * <p>
 * <ul>
 * <li><b>Inheritance</b> using abstract classes if could be replaced by
 * interface</li>
 * <li><b>Catching all exceptions as {@link Exception} or
 * {@link Throwable}</b></li>
 * <li>Using <b>one-time parameters</b> declared as variables (only acceptable
 * if any the parameter's constructor contains many fields)</li>
 * <li>Declaring and coding <b>against an interface instance</b> as opposed to
 * the interface itself (e.g.,<br>
 * <code>{@code ArrayList<Object> list = new ArrayList();}</code>)</li>
 * <li><b>Not</b> using a <code>try-with-resources</code> OR a
 * <code>try-finally</code> to close resources</li>
 * <li>more god-awful bad practices...</li>
 * </ul>
 * 
 * @author Alex Kalinins
 * @since 2019-01-15
 * @since v0.5
 * @version v1.0
 */
@Target(TYPE)
public @interface Hack {
	/** The reason why the type contains a hack or what is wrong. */
	String reason();
}
