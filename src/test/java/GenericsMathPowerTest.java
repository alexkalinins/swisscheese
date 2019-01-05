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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.swisscheese.swisscheese.math.GenericsMath;

/**
 * A test for the power calculation of the {@link GenericsMath} class.
 * 
 * @author Alex Kalinins
 */
public class GenericsMathPowerTest {
	private float ansA1;
	private float ansA2;
	private float ansB;
	private int ansC;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		float a = 2;
		float b = 3;

		ansA1 = GenericsMath.power(a, 2); // should be 4
		ansA2 = GenericsMath.power(a, 3); // should be 8

		ansB = GenericsMath.power(b, 6); // should be 729

		int c = 6;

		ansC = GenericsMath.power(c, 4);// should be 1296
	}

	@Test
	public void test() {
		assertEquals(4f, ansA1, 0.1f);
		assertEquals(8f, ansA2, 0.1f);
		assertEquals(729f, ansB, 0.1f);
		assertEquals(1296, ansC);
	}

}
