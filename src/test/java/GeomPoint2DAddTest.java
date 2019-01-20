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

import org.junit.Test;
import org.swisscheese.swisscheese.math.GeomPoint2D;

/**
 * A test for {@link GeomPoint2D#add(Number)} method.
 * 
 * @author Alex Kalinins
 * @since 2019-01-04
 * @since v0.5
 * @version v1.0
 */
public strictfp class GeomPoint2DAddTest {
	GeomPoint2D<Integer>intNew = new GeomPoint2D<Integer>(1, 1);
	GeomPoint2D<Integer>intOriginal = new GeomPoint2D<>(intNew);
	{
		intNew.add(2);
	}
	GeomPoint2D<Integer>intExpected = new GeomPoint2D<>(3,3);

	GeomPoint2D<Float>floatNew = new GeomPoint2D<>(1f, 1f);
	GeomPoint2D<Float>floatOriginal = new GeomPoint2D<>(floatNew);
	{
		floatNew.add(2f);
	}
	GeomPoint2D<Float>floatExpected = new GeomPoint2D<>(3f,3f);
	

	/**
	 * Testing {@link GeomPoint2D#add(Number)} with Integer.
	 */
	@Test
	public void testWithInteger() {
		assertNotEquals(intNew, intOriginal);
		assertEquals(intExpected, intNew);
	}
	/**
	 * Testing {@link GeomPoint2D#add(Number)} with Float.
	 */
	@Test
	public void testWithFloat() {
		assertNotEquals(floatNew, floatOriginal);
		assertEquals(floatExpected, floatNew);
	}
}
