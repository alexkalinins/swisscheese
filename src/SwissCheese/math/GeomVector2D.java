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

import java.awt.geom.Point2D;

import SwissCheese.annotations.Immutable;


/**
 * A 2D vector to be used in mathematics or geometry calculations
 * 
 * @author Alex Kalinins
 * @since 2018-12-12
 * @since v0.2
 * @since v0.1
 */
@Immutable
public strictfp class GeomVector2D<T extends Number> {
	private final Point2D.Float endPoint;
	private final Point2D.Float startPoint;
	

	/**
	 * Constructor
	 * 
	 * @param startPoint start point of vector
	 * @param endPoint   end point of vector
	 */
	public GeomVector2D(Point2D.Float startPoint, Point2D.Float endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}

	/**
	 * Constructor for {@code GeomVector2D}. Assuming that start point is origin
	 * 
	 * @param endPoint end point of vector
	 */
	public GeomVector2D(Point2D.Float endPoint) {
		this(new Point2D.Float(0, 0), endPoint);
	}

	/**
	 * Constructor for {@code GeomVector2D}. Using floats, assuming start is origin.
	 *
	 * @param x x location of end point
	 * @param y y location of end point
	 */
	public GeomVector2D(T x, T y) {
		this(new Point2D.Float((float)x, (float)y));
	}
	
	public GeomVector2D<T> multiplyScalar(final T s) {
		return new GeomVector2D<T>(startPoint, new Point2D.Float((float)(endPoint.getX()*(float)s),(float)(endPoint.getY()*(float)s)));
	}
	
	public GeomVector2D<T> add(final GeomVector2D<T> o) {
		Point2D.Float start = new Point2D.Float((float)(o.getStartPoint().getX()+startPoint.getX()), (float) (o.getStartPoint().getY()+startPoint.getY()));
		Point2D.Float end = new Point2D.Float((float)(o.getEndPoint().getX()+endPoint.getX()), (float) (o.getEndPoint().getY()+endPoint.getY()));
		return new GeomVector2D<T>(start, end);
	}

	public final Point2D getEndPoint() {
		return endPoint;
	}

	public final Point2D getStartPoint() {
		return startPoint;
	}
	
	/**
	 * Retrieves x of endpoint
	 * @return x of endpoint
	 */
	public final float getX() {
		return (float)endPoint.getX();
	}
	
	/**
	 * Retrieves y of endpoint
	 * @return y of endpoint
	 */
	public final float getY() {
		return (float)endPoint.getY();
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
