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

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
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
 * A test for the {@code hashcode} method of classes implementing
 * {@link KeyAction}.
 * <p>
 * Since classes implementing the {@code KeyAction} interface contain no fields,
 * it is important for the {@code hashCode} to be the same for every instance of
 * a class, since a majority of operations relating to {@code KeyAction} classes
 * rely on the {@link HashMap}.
 * 
 * @author Alex Kalinins
 * @since 2018-12-31
 * @since v0.5
 */
class KeyActionHashcodeTest {

	@Test
	void test() {
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
