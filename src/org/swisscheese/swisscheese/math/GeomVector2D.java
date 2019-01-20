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

import org.swisscheese.swisscheese.annotations.Immutable;

/**
 * A 2D vector to be used in mathematics or geometry calculations
 * 
 * @author Alex Kalinins
 * @since 2018-12-12
 * @since v0.2
 * @since v0.1
 */
@Immutable
public class GeomVector2D<T extends Number> {
	private final GeomPoint2D<T> endPoint;
	private final GeomPoint2D<T> startPoint;
	/**
	 * Constructor
	 * 
	 * @param startPoint start point of vector
	 * @param endPoint   end point of vector
	 */
	public GeomVector2D(GeomPoint2D<T> startPoint, GeomPoint2D<T> endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}

	/**
	 * Constructor for {@code GeomVector2D}. Assuming that start point is origin
	 * 
	 * @param endPoint end point of vector
	 */
	public GeomVector2D(GeomPoint2D<T> endPoint) {
		this(new GeomPoint2D<T>(GenericsMath.castToGeneric(0, endPoint.getClassType()), GenericsMath.castToGeneric(0, endPoint.getClassType())), endPoint);
		GenericsMath.castToGeneric(new Integer(0), Number.class);
	}

	/**
	 * Constructor for {@code GeomVector2D}. Using floats, assuming start is origin.
	 *
	 * @param x x location of end point
	 * @param y y location of end point
	 */
	public GeomVector2D(T x, T y) {
		this(new GeomPoint2D<T>(x, y));
	}

	public GeomVector2D<T> multiplyScalar(final T s) {
		return new GeomVector2D<T>(startPoint, new GeomPoint2D<T>(GenericsMath.multiplyObj(endPoint.getX(), s),
				GenericsMath.multiplyObj(endPoint.getY(), s)));
	}

	public GeomVector2D<T> add(final GeomVector2D<T> o) {
		GeomPoint2D<T> start = new GeomPoint2D<T>(GenericsMath.addObj(o.getStartPoint().getX(), startPoint.getX()),
				GenericsMath.addObj(o.getStartPoint().getY(), startPoint.getY()));
		GeomPoint2D<T> end = new GeomPoint2D<T>(GenericsMath.addObj(o.getEndPoint().getX(), endPoint.getX()),
				GenericsMath.addObj(o.getEndPoint().getY(), endPoint.getY()));
		return new GeomVector2D<T>(start, end);
	}
	
	public GeomVector2D<T> power(final int power){
		GeomPoint2D<T> start = new GeomPoint2D<T>(GenericsMath.power(startPoint.getX(),power), GenericsMath.power(startPoint.getY(),power));
		GeomPoint2D<T> end = new GeomPoint2D<T>(GenericsMath.power(endPoint.getX(),power), GenericsMath.power(endPoint.getY(),power));

		return new GeomVector2D<T>(start,end);
	}
	
	public GeomVector2D<T> swapXY(){
		return new GeomVector2D<>(endPoint, startPoint);
	}

	public final GeomPoint2D<T> getEndPoint() {
		return endPoint;
	}

	public final GeomPoint2D<T> getStartPoint() {
		return startPoint;
	}

	/**
	 * Retrieves x of endpoint
	 * 
	 * @return x of endpoint
	 */
	public final T getX() {
		return endPoint.getX();
	}

	/**
	 * Retrieves y of endpoint
	 * 
	 * @return y of endpoint
	 */
	public final T getY() {
		return endPoint.getY();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endPoint == null) ? 0 : endPoint.hashCode());
		result = prime * result + ((startPoint == null) ? 0 : startPoint.hashCode());
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
		GeomVector2D other = (GeomVector2D) obj;
		if (endPoint == null) {
			if (other.endPoint != null)
				return false;
		} else if (!endPoint.equals(other.endPoint))
			return false;
		if (startPoint == null) {
			if (other.startPoint != null)
				return false;
		} else if (!startPoint.equals(other.startPoint))
			return false;
		return true;
	}

}
