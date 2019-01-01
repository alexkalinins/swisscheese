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
package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import SwissCheese.engine.keyboard.keyActions.GoForward;
import SwissCheese.engine.keyboard.keyActions.GoLeft;
import SwissCheese.engine.keyboard.keyActions.KeyAction;

/**
 * A test for {@code equals} method of classes implementing {@link KeyAction}.
 * 
 * @author Alex Kalinins
 * @since 2018-12-30
 * @since v0.5
 */
class KeyActionEqualsTest {

	@Test
	void testEqualsObject() {
		KeyAction left1 = new GoLeft();
		KeyAction left2 = new GoLeft();
		GoLeft left3 = new GoLeft();
		KeyAction notleft = new GoForward();

		assertEquals(left1, left1);
		assertTrue(left1.equals(left2));
		assertTrue(left1.equals(left3));
		assertNotEquals(notleft, left3);

	}

}
