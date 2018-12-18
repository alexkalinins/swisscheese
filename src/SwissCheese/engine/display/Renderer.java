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
package SwissCheese.engine.display;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import SwissCheese.annotations.NotThreadSafe;
import SwissCheese.engine.camera.Camera;
import SwissCheese.engine.camera.Mover;
import SwissCheese.engine.camera.View;
import SwissCheese.engine.texture.WallTexture;
import SwissCheese.engine.texture.WallTextureList;
import SwissCheese.map.Map;
import SwissCheese.math.GeomVector2D;

/**
 * Display Object for rendering graphics on screen.
 * 
 * @author Alex Kalinins
 * @since 2018-12-10
 * @since v0.2
 * @version v0.1
 */
@NotThreadSafe
public class Renderer {
	private final float width;
	private final float height;
	private List<WallTexture> wallTextures;
	private Map map;
	private View view;
	private Camera camera;
	public static Lock fillLoc = new ReentrantLock();
	// public static Lock update = new ReentrantLock();

	private final Color FLOOR = Color.DARK_GRAY; // the color of the floor
	private final Color SKY = Color.cyan; // the color of the sky

	public Renderer(Map map, Camera camera, float width, float height) {
		this.map = map;
		this.camera = camera;
		this.width = width;
		this.height = height;
		wallTextures = new WallTextureList().getList();
	}

	/**
	 * Renders what the player is seeing through {@code Camera}. Renders into an
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
	public synchronized int[] render(int[] pixels) {
		fillLoc.lock();
		view = camera.getView();
		fillLoc.unlock();

		float xStrip; // x coordinate of scanning strip on camera plane

		GeomVector2D<Float> rayDir; // rayDirection vector

		int xMap; // x location on the map
		int yMap; // y location on the map

		float xSideDistance; // distance between current position and next side (x)
		float ySideDistance; // distance between current position and next side (y)

		GeomVector2D<Float> deltaDistance;

		float wallDist; // distance between player and wall

		int xStep; // the direction in which the ray goes (up or down)
		int yStep; // the direction in which the ray goes (left or right)

		boolean hit;
		boolean wallVertical; // true if the wall is vertical

		int lineHeight;
		int wallStart; // the starting point from which the wall is drawn (top).
		int wallEnd; // the ending point to which the wall is drawn (bottom).
		int wallHit; // where on the strip the wall was hit

		int textureType; // the type of texture on the wall (wall number in map array)
		int xTexture; // x coordinate of the texture
		int yTexture; // y coordinate of the texture
		int color; // the color of a pixel.

		pixels = fillBackground(pixels);

		fillLoc.lock();
		// vertical slices:
		for (int x = 0; x < (int) width; x++) {
			hit = false;
			wallVertical = false;

			xStrip = 2 * x / width - 1;

			// moving away from single objects
			rayDir = view.getDir().add(view.getPlane().multiplyScalar(xStrip));

			xMap = (int) view.getxPos();
			yMap = (int) view.getyPos();

			deltaDistance = new GeomVector2D<Float>(
					(float) Math.sqrt(1 + Math.pow(rayDir.getY(), 2) / Math.pow(rayDir.getX(), 2)),
					(float) Math.sqrt(1 + Math.pow(rayDir.getX(), 2) / Math.pow(rayDir.getY(), 2)));

			// checks in which direction steps go and distance (x)
			if (rayDir.getX() < 0) {
				xStep = -1;
				xSideDistance = (view.getxPos() - xMap) * deltaDistance.getX();
			} else {
				xStep = 1;
				xSideDistance = (xMap + 1f - view.getxPos()) * deltaDistance.getX();
			}

			// checks in which direction steps go and distance (y)
			if (rayDir.getY() < 0) {
				yStep = -1;
				ySideDistance = (view.getyPos() - yMap) * deltaDistance.getY();
			} else {
				yStep = 1;
				ySideDistance = (yMap + 1f - view.getyPos()) * deltaDistance.getY();
			}

			// Loop to find where the ray hits a wall
			while (!hit) {
				// Jump to next square
				if (xSideDistance < ySideDistance) {
					xSideDistance += deltaDistance.getX();
					xMap += xStep;
					wallVertical = false;
				} else {
					ySideDistance += deltaDistance.getY();
					yMap += yStep;
					wallVertical = true;
				}

				hit = map.getMap()[xMap][yMap] > 0;
			}

			textureType = map.getMap()[xMap][yMap] - 1;

			wallDist = (wallVertical) ? Math.abs((yMap - view.getyPos() + (1 - yStep) / 2) / rayDir.getY())
					: Math.abs((xMap - view.getxPos() + (1 - xStep) / 2) / rayDir.getX());
			lineHeight = (wallDist > 0) ? (int) Math.abs((int) (height / wallDist)) : (int) height;

			wallStart = (int) (height - lineHeight) / 2;
			wallStart = (0 > wallStart) ? 0 : wallStart;

			wallEnd = (int) (height + lineHeight) / 2;
			wallEnd = (wallEnd > height) ? (int) height : wallStart;

			wallHit = (int) ((wallVertical)
					? (view.getxPos() + ((yMap - view.getyPos() + (1 - yStep) / 2) / rayDir.getY()) * rayDir.getX())
					: (view.getyPos() + ((xMap - view.getxPos() + (1 - xStep) / 2) / rayDir.getX()) * rayDir.getY()));
			wallHit -= Math.floor(wallHit);

			xTexture = wallHit * (wallTextures.get(textureType).getSize());

			if (!wallVertical && rayDir.getX() > 0)
				xTexture = wallTextures.get(textureType).getSize() - textureType - 1;
			if (wallVertical && rayDir.getY() < 0)
				xTexture = wallTextures.get(textureType).getSize() - textureType - 1;
			for (int i = wallStart; i < wallEnd; i++) {
				yTexture = (((int) (i * 2 - height + lineHeight) << 6) / lineHeight) / 2;

				// Array index out of bounds, somethign wrong with math or thread safety

				try {
					color = wallTextures.get(textureType).getImage().getPixels()[xTexture
							+ (yTexture * wallTextures.get(textureType).getSize())];
				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
					color = 0;
				}
				// makes vertical walls darker
				// TODO rename to wallHorizontal
				if (!wallVertical) {
					color >>= 1;
					color &= 8500000;
				}

				pixels[(int) (x + i * (width))] = color;
			}
		}
		fillLoc.unlock();
		return pixels;
	}

	private synchronized int[] fillBackground(int[] pixels) {
		fillLoc.lock();
		for (int i = 0; i < pixels.length / 2; i++) {
			pixels[i] = SKY.getRGB();
		}

		// colors pixels below midway point
		for (int i = pixels.length / 2; i < pixels.length; i++) {
			pixels[i] = FLOOR.getRGB();
		}
		fillLoc.unlock();
		return pixels;
	}

	public Mover getMover() {
		return camera.getMover();
	}

}
