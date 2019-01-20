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

import org.swisscheese.swisscheese.annotations.ThreadSafe;

/**
 * Re-implementation of {@link java.awt.geom.Point2D} to accommodate generic
 * parameters.
 * 
 * @author Alex Kalinins
 * @since 2018-12-20
 * @since v0.3
 * @version v1.0
 */
@ThreadSafe
public final class GeomPoint2D<T extends Number> {
	private T x;
	private T y;

	/**
	 * Constructor
	 * 
	 * @param x x-value
	 * @param y y-value
	 */
	public GeomPoint2D(T x, T y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructor from another GeomPoint2D {@code point}
	 * 
	 * @param point
	 */
	public GeomPoint2D(GeomPoint2D<T> point) {
		this.x = point.getX();
		this.y = point.getY();
	}

	/**
	 * No args constructor
	 */
	public GeomPoint2D() {

	}

	/**
	 * Constructor from two {@link Number} objects.
	 * 
	 * @param x  x-location
	 * @param y  y-location
	 * @param bs used to prevent ambiguity.
	 */
	@SuppressWarnings("unchecked")
	public GeomPoint2D(Number x, Number y, byte... bs) {
		this.x = (T) x;
		this.y = (T) y;
	}

	@SuppressWarnings("unchecked")
	public Class<T> getClassType() {
		return (Class<T>) x.getClass();
	}

	/**
	 * Adds {@code value} to x and y of the {@code GeomPoint2D}.
	 * 
	 * @param value value being added to the point.
	 * @see GenericsMath#addObj(Number, Number)
	 */
	public synchronized void add(T value) {
		setX(GenericsMath.addObj(x, value));
		setY(GenericsMath.addObj(y, value));
	}

	/**
	 * Getter for the x-location of the point.
	 * 
	 * @return x location of the point.
	 */
	public final T getX() {
		return x;
	}

	/**
	 * Getter of the y-location of the point.
	 * 
	 * @return y location of the point.
	 */
	public final T getY() {
		return y;
	}

	/**
	 * Setter for the x-location of the point.
	 * 
	 * @param x the new x location.
	 */
	public final void setX(T x) {
		this.x = x;
	}

	/**
	 * Setter for the y-location of the point.
	 * 
	 * @param y the new y location.
	 */
	public final void setY(T y) {
		this.y = y;
	}

	/**
	 * Setter for the location of the point from points {@code x} and {@code y}
	 * 
	 * @param x new x location.
	 * @param y new y location
	 */
	public final void setLocation(T x, T y) {
		setX(x);
		setY(y);
	}

	/**
	 * Setter for the location of the point from another {@code GeomPoint2D}.
	 * 
	 * @param other the other {@link GeomPoint2D}.
	 */
	public final void setLocation(GeomPoint2D<T> other) {
		setLocation(other.getX(), other.getY());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		GeomPoint2D other = (GeomPoint2D) obj;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		return true;
	}
}
