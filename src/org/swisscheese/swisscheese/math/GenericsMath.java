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
package org.swisscheese.swisscheese.math;

/**
 * A method to do mathematical calculations on variables of generic type.
 * <p>
 * Operations currently supported:
 * <p>
 * <li>Addition</li>
 * <li>Subtraction</li>
 * <li>Multiplication</li>
 * <li>Casting</li>
 * <p>
 * 
 * @author Alex Kalinins
 * @since 2018-12-20
 * @since v0.3
 * @version v0.2
 */
public strictfp class GenericsMath {

	/**
	 * Multiplies {@code x} by {@code y}
	 * 
	 * @param x operand 1
	 * @param y operand 2
	 * @return a product of {@code x} and {@code y}
	 */
	@SuppressWarnings("unchecked")
	public static final <T extends Number> T multiplyObj(T x, T y) {
		if (x == null || y == null) {
			return null;
		}
		if (x instanceof Double) {
			return (T) new Double(((Double) x).doubleValue() * ((Double) y).doubleValue());
		} else if (x instanceof Integer) {
			return (T) new Integer(((Integer) x).intValue() * ((Integer) y).intValue());
		} else if (x instanceof Float) {
			return (T) new Float(((Float) x).floatValue() * ((Float) y).floatValue());
		} else {
			throw new IllegalArgumentException("Type " + x.getClass() + " is not supported by this method");
		}
	}

	/**
	 * Adds {@code x} and {@code y}
	 * 
	 * @param x operand 1
	 * @param y operand 2
	 * @return sum of {@code x} and {@code y}
	 */
	@SuppressWarnings("unchecked")
	public static final <T extends Number> T addObj(T x, T y) {
		if (x == null || y == null) {
			return null;
		}
		if (x instanceof Double) {
			return (T) new Double(((Double) x).doubleValue() + ((Double) y).doubleValue());
		} else if (x instanceof Integer) {
			return (T) new Integer(((Integer) x).intValue() + ((Integer) y).intValue());
		} else if (x instanceof Float) {
			return (T) new Float(((Float) x).floatValue() + ((Float) y).floatValue());
		} else {
			throw new IllegalArgumentException("Type " + x.getClass() + " is not supported by this method");
		}
	}

	public static final <T extends Number> T power(T x, final int degree) {
		if (x == null) {
			return null;
		}
		if (degree < 2) {
			throw new IllegalArgumentException("Does not support degree less than 2");
		}

		T t = x;
		for (int i = 1; i < degree; i++) {
			t = multiplyObj(t, x);
		}
		return t;
	}

	/**
	 * Subtracts {@code y} <strong>from</strong> {@code x}
	 * 
	 * @param x subtracting from
	 * @param y subtracting what
	 * @return {@code y} subtracted from {@code x}
	 */
	@SuppressWarnings("unchecked")
	public static final <T extends Number> T subtractObj(T x, T y) {
		if (x == null || y == null) {
			return null;
		}
		if (x instanceof Double) {
			return (T) new Double(((Double) x).doubleValue() - ((Double) y).doubleValue());
		} else if (x instanceof Integer) {
			return (T) new Integer(((Integer) x).intValue() - ((Integer) y).intValue());
		} else if (x instanceof Float) {
			return (T) new Float(((Float) x).floatValue() - ((Float) y).floatValue());
		} else {
			throw new IllegalArgumentException("Type " + x.getClass() + " is not supported by this method");
		}
	}

	/**
	 * Casts {@code obj} to {@code classs} type.
	 * 
	 * @param obj    Object being casted
	 * @param classs Type
	 * @return {@code obj} casted to {@code classs}
	 */
	public static final <T> T castToGeneric(Object obj, Class<T> classs) {
		try {
			return classs.cast(obj);
		} catch (ClassCastException e) {
			return null;
		}
	}
}
