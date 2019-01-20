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

import org.swisscheese.swisscheese.annotations.Hack;
import org.swisscheese.swisscheese.annotations.ThreadSafe;
import org.swisscheese.swisscheese.engine.camera.Camera;
import org.swisscheese.swisscheese.engine.details.RendererDetails;
import org.swisscheese.swisscheese.engine.imageEffects.ChangeGamma;
import org.swisscheese.swisscheese.engine.imageEffects.GammaState;
import org.swisscheese.swisscheese.map.Map;
import org.swisscheese.swisscheese.math.GeomVector2D;
import org.swisscheese.swisscheese.texturePacks.TexturePack;

/**
 * Display Object for rendering graphics on screen.
 * <p>
 * The rendering method and the single-threaded nature of this class makes the
 * game run poorly with higher screen resolutions. Two possible solutions exist
 * for improving the game performance:
 * <p>
 * <li>Assigning a single scan line to an executor from a thread-pool</li>
 * <li>Breaking the screen up into chunks, and each thread gets a chunk</li>
 * <p>
 * With the first solution, the threads do not have to do the same amount of
 * work. If one thread is faster, that wouldn't affect the final outcome. Also,
 * it would be easy to specify how many threads will do the rendering. However,
 * this solution could result in heavier resource usage due to object creation.
 * <p>
 * If the screen was broken-up into chunks, then the current
 * {@link SingleThreadedRenderer#render(int[])} method only needs to be slightly
 * modified to work with the chunks. However, the amount of threads needs to be
 * an even number, and it would be difficult to work with many threads (if the
 * client CPU supports). Also, it is possible that one thread would complete
 * working on a chunk, while a lagging thread would slow down the performance.
 * <p>
 * Strip solution would be attempted first, and if it is not good enough, the
 * chunk solution would be tried and implemented.
 * 
 * @author Alex Kalinins
 * @since 2018-12-10
 * @since v0.2
 * @version v1.0
 */
@Hack(reason = "inheritance")
@ThreadSafe
public class SingleThreadedRenderer extends Renderer {
	private float scanLine;
	private int xMap;
	private int yMap;

	// length of a ray from the current position to next x or y-side
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

	private float[] avgTime = new float[10];
	private float time = 0;
	private int timeCounter = 10;
	private boolean count = true;

	/**
	 * {@inheritDoc}
	 */
	SingleThreadedRenderer(float width, float height, TexturePack texturePack, Camera camera, Map map) {
		super(width, height, texturePack, camera, map);
	}

	/**
	 * Constructor from RendererDetails
	 * 
	 * @param details
	 * @param camera
	 */
	SingleThreadedRenderer(RendererDetails details, Camera camera) {
		super(details, camera);
	}

	/**
	 * Renders what the player is seeing through {@link Camera}. Renders into an
	 * array of ints (pixels) through ray-casting.
	 * <p>
	 * This algorithm goes through each vertical strip of the pixels array and
	 * calculates the distance between the location of the player and the wall. The
	 * distance is then used to calculate the height of the wall (perspective) in
	 * that strip. The algorithm retrieves the color values of the pixels in the
	 * texture, and applies them to each pixel in the new line. The new pixel array
	 * is returned.
	 * 
	 * @param {@code int} array of pixels (rgb value for each pixel.
	 * @return updated pixels array.
	 * @see <a href="https://lodev.org/cgtutor/raycasting.html">Ray-Casting</a>
	 */
	@Override
	public int[] render(int[] pixels) {
		count = --timeCounter >= 0;
		if (count) {
			avgTime[timeCounter] = (float) System.nanoTime();
		}

		if (!psychadelic) {
			pixels = fillBackground(pixels);
		}

		view = camera.getView();

		// caching data from view:
		GeomVector2D<Float> dir = view.getDir();
		GeomVector2D<Float> plane = view.getPlane();

		// Does not make sense to use GeomVector2D for pos
		final float xPos = view.getxPos();
		final float yPos = view.getyPos();

		/**
		 * Rendering the image in a series of vertical scans, each one pixel thick.
		 */
		for (int x = 0; x < getDetails().width; x++) {
			// the position of this scan line on the camera plane relative to POV
			scanLine = 2 * x / getDetails().width - 1f;
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
			} while (getDetails().maze[xMap][yMap] <= 0);// ray has hit the wall

			// distance from the player to the wall
			distanceToWall = (wallVertical) ? Math.abs((yMap - yPos + (1 - yStep) / 2) / rayDir.getY())
					: Math.abs((xMap - xPos + (1 - xStep) / 2) / rayDir.getX());

			// calculating wall line length from wall distance (perspective)
			wallLength = (int) ((distanceToWall > 0) ? Math.abs(getDetails().height / distanceToWall)
					: getDetails().height);

			// wall line start point:
			wallStart = (int) (-wallLength / 2 + getDetails().height / 2);
			wallStart = (wallStart < 0) ? 0 : wallStart; // if it's off the screen

			// wall line end point:
			wallEnd = (int) (wallLength / 2 + getDetails().height / 2);
			wallEnd = (wallEnd > getDetails().height) ? (int) getDetails().height : wallEnd; // off the
																								// screen

			// getting textureType from the wall
			textureType = getDetails().maze[Math.abs(xMap)][Math.abs(yMap)] - 1;
			getDetails().wallTextures.get(textureType).doAction();
			wallHit = (wallVertical) ? (xPos + ((yMap - yPos + (1 - yStep) / 2) / rayDir.getY()) * rayDir.getX())
					: (yPos + ((xMap - xPos + (1 - xStep) / 2) / rayDir.getX()) * rayDir.getY());
			wallHit -= Math.floor(wallHit);

			// caching texture size
			final int textureSize = getDetails().wallTextures.get(textureType).getSize();

			// stretching the texture according to the wall shape (perspective)
			// calculating x coordinate of the texture
			xTexture = (int) (wallHit * (textureSize));

			if ((!wallVertical && rayDir.getX() > 0) || (wallVertical && rayDir.getY() < 0))
				xTexture = textureSize - xTexture - 1;
			// calculating y coordinate of the texture
			for (int y = wallStart; y < wallEnd; y++) {
				yTexture = (((int) (y * 2 - getDetails().height + wallLength) << 6) / wallLength) / 2;

				// getting a color from texture
				rgb = getDetails().wallTextures.get(textureType).getImage().getPixels()[xTexture
						+ (yTexture * textureSize)];
				// darkening some walls for 3D effect, 0.6 works the best IMO
				if (wallVertical)
					rgb = ChangeGamma.getColor(rgb, 0.6f);
				pixels[(int) (x + y * (getDetails().width))] = rgb;
			}
		}

		pixels = applyDarkness(pixels);

		if (count) {
			avgTime[timeCounter] = ((float) System.nanoTime() - avgTime[timeCounter]) / 1000000000f;
		}

		return pixels;

	}

	/**
	 * A method that calculates and return the amount of time it takes for the
	 * {@link SingleThreadedRenderer} to render.
	 * 
	 * @return average time of 10 rendering passes.
	 */
	public float getAverageRenderingTime() {
		if (time == 0) {
			for (int i = 0; i < avgTime.length; i++) {
				time += avgTime[i];
			}

			time /= avgTime.length;
		}

		return time;

	}

	/**
	 * Darkening or brightening the <code>pixels</code> array based on
	 * {@link GammaState} from {@link Renderer}. Similar to
	 * {@link Renderer#fillBackground(int[])}.
	 * <p>
	 * Made for the pause screen.
	 * 
	 * @param pixels the array of pixels being darkened
	 * @return the pixels either darkened or brightened.
	 */
	private int[] applyDarkness(int[] pixels) {
		// running through arrays is not the best thing, but faster than 'enhanced for
		// loop' (no iterator).
		if (state == GammaState.DARK) {
			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = ChangeGamma.getColor(pixels[i], 0.5f);
			}
		} else if (state == GammaState.BRIGHT) {
			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = ChangeGamma.getColor(pixels[i], 2f);
			}
		}

		return pixels;
	}

	/**
	 * This is a <code>SingleThreadedRenderer</code>. As the name implies, this
	 * method can only utilize one thread.
	 * 
	 * @return false.
	 */
	@Override
	public boolean isMultithreaded() {
		return false;
	}
}
