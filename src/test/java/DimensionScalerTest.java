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

import java.awt.Dimension;

import org.junit.Before;
import org.junit.Test;
import org.swisscheese.swisscheese.math.DimensionScaler;

/**
 * Test for {@link DimensionScaler#scale(Dimension, Dimension)}
 * 
 * @author Alex Kalinins
 * @since v0.5
 */
public class DimensionScalerTest {
	Dimension t1original = new Dimension(480, 720);
	Dimension t1prefered = new Dimension(200, 200);
	Dimension t1result;
	Dimension t1expected = new Dimension(133, 200);

	Dimension t2original = new Dimension(500, 400);
	Dimension t2prefered = new Dimension(400, 400);
	Dimension t2result;
	Dimension t2expected = new Dimension(400, 320);
	
	/**
	 * The setup for the test.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		t1result = DimensionScaler.scale(t1original, t1prefered);
		t2result = DimensionScaler.scale(t2original, t2prefered);

	}

	@Test
	public void testScale() {
		assertEquals(t1expected, t1result);
		assertEquals(t2expected, t2result);
	}

}
