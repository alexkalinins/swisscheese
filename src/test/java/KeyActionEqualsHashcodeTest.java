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
package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.GoBackward;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.GoForward;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.GoLeft;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.GoRight;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.KeyAction;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.OpenMenu;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.PanLeft;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.PanRight;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.SaveGame;

/**
 * A combined test for the hashCode and equals methods of some arbitrarily
 * selected classes implementing the {@link KeyAction} interface.
 * 
 * @author Alex Kalinins
 * @since 2019-01-04
 * @since v0.5
 * @version v1.0
 */
public class KeyActionEqualsHashcodeTest {

	/**
	 * A test for the hashcode method of some classes implementing the
	 * {@link KeyAction} interface.
	 */
	@Test
	public void hashcodeTest() {
		KeyAction left1 = new GoLeft();
		KeyAction left2 = new GoLeft();
		GoLeft left3 = new GoLeft();
		KeyAction notleft = new GoForward();

		assertEquals(left1, left1);
		assertTrue(left1.equals(left2));
		assertTrue(left1.equals(left3));
		assertNotEquals(notleft, left3);
	}

	/**
	 * A test for the equals method of some classes implementing the
	 * {@link KeyAction} interface.
	 */
	@Test
	public void equalsTest() {
		// arbitrarily picked classes
		assertEquals(new PanLeft().hashCode(), new PanLeft().hashCode());
		assertEquals(new PanRight().hashCode(), new PanRight().hashCode());
		assertEquals(new SaveGame().hashCode(), new SaveGame().hashCode());
		assertEquals(new GoForward().hashCode(), new GoForward().hashCode());
		assertEquals(new OpenMenu().hashCode(), new OpenMenu().hashCode());
		assertEquals(new GoLeft().hashCode(), new GoLeft().hashCode());
		assertEquals(new GoBackward().hashCode(), new GoBackward().hashCode());

		assertNotEquals(new SaveGame().hashCode(), new GoForward().hashCode());
		assertNotEquals(new GoLeft().hashCode(), new GoForward().hashCode());
		assertNotEquals(new GoRight().hashCode(), new GoForward().hashCode());
		assertNotEquals(new OpenMenu().hashCode(), new GoForward().hashCode());
	}

}
