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
package org.swisscheese.swisscheese.engine.rendering;

import java.util.concurrent.Callable;

import org.swisscheese.swisscheese.annotations.ThreadSafe;
import org.swisscheese.swisscheese.engine.camera.View;
import org.swisscheese.swisscheese.engine.details.RendererDetails;
import org.swisscheese.swisscheese.engine.imageEffects.ChangeGamma;
import org.swisscheese.swisscheese.math.GeomVector2D;

/**
 * Renders a chunk (multiple lines at once). Dispatched by
 * {@link ChunkRendererDispatcher}.
 * <p>
 * This method implements {@link Callable} with type {@link Void}
 * 
 * @author Alex Kalinins
 * @since 2019-01-14
 * @since v0.5
 * @version v1.0
 */
@ThreadSafe
public class RenderChunk implements Callable<Void> {
	/** Parent dispatcher of this rendering unit. */
	private final ChunkRendererDispatcher parent;
	/** The view from which the image is rendered. */
	private View view;
	/** The start index of {@code RenderChunk} (inclusive). */
	private final int fromIndex;
	/** The end index of {@code RenderChunk} (exclusive). */
	private final int toIndex;
	/** Details object. */
	private final RendererDetails DETAILS = Renderer.getDetails();

	// reused render variables
	private GeomVector2D<Float> dir;
	private GeomVector2D<Float> plane;
	// Does not make sense to use GeomVector2D for pos
	private float xPos;
	private float yPos;
	private float scanLine;
	private int xMap;
	private int yMap;
	// private length of a ray from the current position to next x or y-side
	private double xSideDistance;
	private double ySideDistance;
	private double distanceToWall;
	// direction to go in x and y
	private int xStep;
	private int yStep;
	private int wallLength; // the length of wall on scanLine
	private int wallStart; // y coordinate on scanLine where the wall starts.
	private int wallEnd;// y coordinate on scanLine where the wall ends.
	private int textureType; // type of texture (number in map wall)
	private double wallHit; // point where the ray hits the wall
	private int xTexture;
	private int yTexture;
	private int rgb; // color of a pixel
	// the wall is horizontal or vertical relative to map (top-down)
	private boolean wallVertical;
	private GeomVector2D<Float> rayDir; // ray direction vector
	private GeomVector2D<Float> deltaDist; // difference in distance
	// end reused variables

	/**
	 * Constructor
	 * 
	 * @param parent    the parent {@link ChunkRendererDispatcher} of this
	 *                  RenderChunk.
	 * @param fromIndex the from index (inclusive).
	 * @param toIndex   the to index (exclusive).
	 * @param view      the view used.
	 */
	RenderChunk(ChunkRendererDispatcher parent, int fromIndex, int toIndex, View view) {
		this.parent = parent;
		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
		this.view = view;
	}

	/**
	 * Updates the view of the render chunk to be <code>view</code>.
	 * 
	 * @param view the view that is being updated.
	 */
	public synchronized void update(View view) {
		this.view = view;
	}

	/**
	 * Call method of the <code>Callable</code>.
	 */
	@Override
	public Void call() throws Exception {
		dir = view.getDir();
		plane = view.getPlane();
		xPos = view.getxPos();
		yPos = view.getyPos();

		for (int x = fromIndex; x < toIndex; x++) {
			scanLine = 2 * x / DETAILS.width - 1f;
			wallVertical = false;

			// position on the map; can't move out of for loop for some reason
			xMap = (int) xPos;
			yMap = (int) yPos;

			// where the ray going.
			rayDir = dir.add(plane.multiplyScalar(scanLine));

			// distance between two walls
			deltaDist = new GeomVector2D<Float>(
					(float) Math.sqrt(1 + Math.pow(rayDir.getY(), 2) / Math.pow(rayDir.getX(), 2)),
					(float) Math.sqrt(1 + Math.pow(rayDir.getX(), 2) / Math.pow(rayDir.getY(), 2)));

			// calculating in which direction ray is going and initial distance.
			// in x direction:
			if (rayDir.getX() < 0) {
				xStep = -1;
				xSideDistance = (xPos - xMap) * deltaDist.getX();
			} else {
				xStep = 1;
				xSideDistance = (xMap + 1d - xPos) * deltaDist.getX();
			}

			// in y direction:
			if (rayDir.getY() < 0) {
				yStep = -1;
				ySideDistance = (yPos - yMap) * deltaDist.getY();
			} else {
				yStep = 1;
				ySideDistance = (yMap + 1d - yPos) * deltaDist.getY();
			}

			// calculating distance to wall:
			do {
				// going closer towards the wall
				if (xSideDistance < ySideDistance) {
					xSideDistance += deltaDist.getX();
					xMap += xStep;
					wallVertical = false;
				} else {
					ySideDistance += deltaDist.getY();
					yMap += yStep;
					wallVertical = true;
				}
			} while (DETAILS.maze[xMap][yMap] <= 0);// ray has hit the wall

			// distance from the player to the wall
			distanceToWall = (wallVertical) ? Math.abs((yMap - yPos + (1 - yStep) / 2) / rayDir.getY())
					: Math.abs((xMap - xPos + (1 - xStep) / 2) / rayDir.getX());

			// calculating wall line length from wall distance (perspective)
			wallLength = (int) ((distanceToWall > 0) ? Math.abs(DETAILS.height / distanceToWall) : DETAILS.height);

			// wall line start point:
			wallStart = (int) (-wallLength / 2 + DETAILS.height / 2);
			wallStart = (wallStart < 0) ? 0 : wallStart; // if it's off the screen

			// wall line end point:
			wallEnd = (int) (wallLength / 2 + DETAILS.height / 2);
			wallEnd = (wallEnd > DETAILS.height) ? (int) DETAILS.height : wallEnd; // off the screen
			// getting textureType from the wall
			textureType = DETAILS.maze[Math.abs(xMap)][Math.abs(yMap)] - 1;
			DETAILS.wallTextures.get(textureType).doAction();
			wallHit = (wallVertical) ? (xPos + ((yMap - yPos + (1 - yStep) / 2) / rayDir.getY()) * rayDir.getX())
					: (yPos + ((xMap - xPos + (1 - xStep) / 2) / rayDir.getX()) * rayDir.getY());
			wallHit -= Math.floor(wallHit);

			// caching texture size
			final int textureSize = DETAILS.wallTextures.get(textureType).getSize();

			// stretching the texture according to the wall shape (perspective)
			// calculating x coordinate of the texture
			xTexture = (int) (wallHit * (textureSize));

			if ((!wallVertical && rayDir.getX() > 0) || (wallVertical && rayDir.getY() < 0))
				xTexture = textureSize - xTexture - 1;
			// calculating y coordinate of the texture
			for (int y = wallStart; y < wallEnd; y++) {
				yTexture = (((int) (y * 2 - DETAILS.height + wallLength) << 6) / wallLength) / 2;

				// getting a color from texture
				rgb = DETAILS.wallTextures.get(textureType).getImage().getPixels()[xTexture + (yTexture * textureSize)];
				// darkening some walls for 3D effect, 0.6 works the best IMO
				if (wallVertical)
					rgb = ChangeGamma.getColor(rgb, 0.6f);
				// strip[(int) (stripNumber + y * (DETAILS.width))] = rgb;
				parent.insertValue(rgb, (int) (x + y * DETAILS.width));
			}
		}
		return null;
	}

}
