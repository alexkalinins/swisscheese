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
package SwissCheese.math;

import SwissCheese.annotations.ThreadSafe;

/**
 * Re-implementation of {@link java.awt.geom.Point2D} to accommodate generic
 * parameters.
 * 
 * @author Alex Kalinins
 * @since 2018-12-20
 * @since v0.3
 * @version v0.1
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
	
	
	@SuppressWarnings("unchecked")
	public GeomPoint2D(Number x, Number y, byte... bs) {
		this.x = (T) x;
		this.y = (T) y;
	}

	@SuppressWarnings("unchecked")
	public Class<T> getClassType(){
		return (Class<T>) x.getClass();
	}

	public final T getX() {
		return x;
	}

	public final T getY() {
		return y;
	}

	public synchronized final void setX(T x) {
		this.x = x;
	}

	public synchronized final void setY(T y) {
		this.y = y;
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
