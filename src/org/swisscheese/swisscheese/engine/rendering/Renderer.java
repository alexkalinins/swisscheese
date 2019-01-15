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

import java.awt.Color;

import org.swisscheese.swisscheese.annotations.Hack;
import org.swisscheese.swisscheese.engine.camera.Camera;
import org.swisscheese.swisscheese.engine.camera.Mover;
import org.swisscheese.swisscheese.engine.camera.View;
import org.swisscheese.swisscheese.engine.texture.WallTexture;
import org.swisscheese.swisscheese.engine.texture.WallTextureList;
import org.swisscheese.swisscheese.map.Map;
import org.swisscheese.swisscheese.texturePacks.TexturePack;

/**
 * An interface for rendering engines.
 * 
 * @author Alex Kalinins
 * @since 2019-01-05
 * @since v0.5
 * @version v1.0
 */
@Hack(reason="inheritance")
public abstract class Renderer {
	/** The color to which the floor (bottom half of screen) is colored */
	protected final Color FLOOR = Color.DARK_GRAY;
	/** The color to which the sky (top half of screen) is colored */
	protected final Color SKY = Color.cyan;
	/** An object containing details about renderer that will not change */
	private static RendererDetails details;
	/** The {@link Camera} of the game */
	protected final Camera camera;
	/** A {@link View} object that is retrieved from <code>camera</code>. */
	protected View view;

	/**
	 * A constructor for the abstract class
	 * 
	 * @param width       the width of the screen
	 * @param height      the height of the screen
	 * @param texturePack the List of {@link WallTexture} objects containing wall
	 *                    textures.
	 * @param camera      the {@link Camera} of the game.
	 * @param map         the map in which the player is.
	 */
	public Renderer(float width, float height, TexturePack texturePack, Camera camera, Map map) {
		this.camera = camera;
		details = new RendererDetails(width, height, new WallTextureList(texturePack).getList(), map.getMap());
	}

	/**
	 * Renders the <code>pixels</code> array.
	 * 
	 * @param pixels array of RGB values being rendered
	 * @return a rendered <code>pixel</code> array.
	 */
	public abstract int[] render(int[] pixels);
	
	/**
	 * To see if this class returns threads
	 * @return true if program is multi-threaded
	 */
	public abstract boolean canGetThreads();
	
	/**
	 * To see how many threads are declared for the Renderer.
	 * @return number of threads.
	 * @throws IllegalStateException if class is single threaded.
	 */
	public abstract int getThreads() throws IllegalStateException;

	/**
	 * Updates and gets {@link Mover}.
	 * 
	 * @return a {@link Mover} from {@link Camera}.
	 */
	public final Mover getMover() {
		return camera.getMover();
	}

	/**
	 * Fills the background (with SKY and FLOOR color). Also clears
	 * <code>pixels</code>.
	 * 
	 * @param pixels pixels array
	 * @return pixels with filled background.
	 */
	protected final int[] fillBackground(int[] pixels) {
		for (int i = 0; i < pixels.length / 2; i++) {
			pixels[i] = SKY.getRGB();
		}

		// colors pixels below midway point
		for (int i = pixels.length / 2; i < pixels.length; i++) {
			pixels[i] = FLOOR.getRGB();
		}
		return pixels;
	}

	static final RendererDetails getDetails() {
		return details;
	}

}
