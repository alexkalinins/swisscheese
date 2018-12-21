package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import SwissCheese.math.GenericsMath;

class GenericsMathPowerTest {

	private float ansA1;
	private float ansA2;
	private float ansB;
	private int ansC;

	@BeforeEach
	void setUp() throws Exception {
		float a = 2;
		float b = 3;

		ansA1 = GenericsMath.power(a, 2); // should be 4
		ansA2 = GenericsMath.power(a, 3); // should be 8

		ansB = GenericsMath.power(b, 6); // should be 729

		int c = 6;

		ansC = GenericsMath.power(c, 4);// should be 1296

	}

	@Test
	void testPower() {
		assertEquals(4f, ansA1);
		assertEquals(8f, ansA2);
		assertEquals(729f, ansB);
		assertEquals(1296, ansC);
	}

}
