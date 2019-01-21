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
package org.swisscheese.swisscheese.engine.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import org.swisscheese.swisscheese.SwissCheese;
import org.swisscheese.swisscheese.annotations.NotThreadSafe;
import org.swisscheese.swisscheese.engine.camera.Camera;
import org.swisscheese.swisscheese.engine.camera.Mover;
import org.swisscheese.swisscheese.engine.camera.View;
import org.swisscheese.swisscheese.engine.details.MultithreadedRendererDetails;
import org.swisscheese.swisscheese.engine.details.RendererDetails;
import org.swisscheese.swisscheese.engine.details.UseRenderer;
import org.swisscheese.swisscheese.engine.io.images.ImageFromArray;
import org.swisscheese.swisscheese.engine.keyboard.KeyPreferenceIO;
import org.swisscheese.swisscheese.engine.keyboard.Keyboard;
import org.swisscheese.swisscheese.engine.rendering.MultithreadedRendererDispatcher;
import org.swisscheese.swisscheese.engine.rendering.Renderer;
import org.swisscheese.swisscheese.engine.rendering.RendererFactory;
import org.swisscheese.swisscheese.engine.rendering.RendererType;
import org.swisscheese.swisscheese.engine.texture.WallTextureList;
import org.swisscheese.swisscheese.gameSaving.SaveMetadata;
import org.swisscheese.swisscheese.map.Map;
import org.swisscheese.swisscheese.texturePacks.TexturePack;

/**
 * A window class. This is a JFrame window which renders the game while the game
 * is running. This JFrame is only used for the main game loop, and not for any
 * other prompts or menus.
 * 
 * @author Alex Kalinins
 * @since 2018-12-11
 * @since v0.2
 * @version v1.0
 *
 */
@NotThreadSafe
public class Window extends JFrame {
	private static Window window = null;

	private static final long serialVersionUID = 4044145833868554127L;

	// not my stuff:
	private BufferStrategy buffer; // renders the next frame while the current one is being displayed
	private BufferedImage bufferImage; // a image buffer
	private Graphics graphics;

	// my stuff:
	private Renderer renderer;
	private static Camera camera;
	public static Mover mover;
	private Keyboard keyboard;
	private static SaveMetadata metadata;

	// primitives:
	private int width;
	private int height;
	private volatile int[] pixels;

	/**
	 * Constructor for the window of {@code SwissCheese}.
	 * 
	 * @param width       the width of the {@code Window}.
	 * @param height      the height of the {@code Window}
	 * @param fitToScreen true if {@code Window} will fit the monitor size. If true,
	 *                    {@code width} and {@code height} will be disregarded, and
	 *                    the monitor width and height will be used in their place.
	 * @param map         the {@link Map} of the game in which the player is placed.
	 * @param FOV         the field of view of the player - how much they are
	 *                    seeing. This float must be between -0.1 and -2.5. If it is
	 *                    less than -2.5 the game is un-playable. If it is 0 or
	 *                    more, raycasting calculations will not work.
	 * @param metadataa   the {@link SaveMetadata} of the game. Needed for saving
	 *                    the game so that they can be identified in the future
	 *                    based on their name, and the time of saving. (Variable
	 *                    name misspelled intentionally).
	 * @param texturePack the {@link TexturePack} object from which texture
	 *                    resources will be opened in order to have walls with
	 *                    textures.
	 * @param view        the view from the camera. This variable is <b>only needed
	 *                    if opening a game save!</b> If no variable is passed
	 *                    through, a new {@code View} is created.
	 */
	private Window(int width, int height, boolean fitToScreen, Map map, float FOV, SaveMetadata metadataa,
			TexturePack texturePack, UseRenderer useRenderer, View... view) {
		if (window != null) {
			throw new SecurityException();
		}

		if (fitToScreen) {
			// Getting the screen size.
			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			System.out.println("Fitting window size to screen: " + screen.toString());
			this.width = (int) screen.getWidth();
			this.height = (int) screen.getHeight();
		} else {
			this.width = width;
			this.height = height;
		}
		metadata = metadataa;

		camera = new Camera(this.width, this.height, map, FOV, view);
		RendererDetails details = useRenderer.type == RendererType.SINGLE_THREAD
				? new RendererDetails(width, height, new WallTextureList(texturePack).getList(), map.getMap())
				: new MultithreadedRendererDetails(width, height, new WallTextureList(texturePack).getList(),
						map.getMap(), useRenderer.nThreads);
		System.out.printf("Creating new %s Renderer%n", useRenderer.type.toString());
		renderer = RendererFactory.createFromEnum(useRenderer.type, details, camera);
		mover = camera.getMover();

		bufferImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);

		pixels = ((DataBufferInt) bufferImage.getRaster().getDataBuffer()).getData();

		keyboard = new Keyboard();
		KeyPreferenceIO.readFromFile();

		setSize(this.width, this.height);
		setResizable(false);
		setTitle(SwissCheese.TITLE);
		addKeyListener(keyboard);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				WindowCloser.closeEverything();
			}
		});
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	/**
	 * Calls the private constructor to create a new window.
	 * 
	 * @param width       the width of the {@code Window}.
	 * @param height      the height of the {@code Window}
	 * @param fitToScreen true if {@code Window} will fit the monitor size. If true,
	 *                    {@code width} and {@code height} will be disregarded, and
	 *                    the monitor width and height will be used in their place.
	 * @param map         the {@link Map} of the game in which the player is placed.
	 * @param FOV         the field of view of the player - how much they are
	 *                    seeing. This float must be between -0.1 and -2.5. If it is
	 *                    less than -2.5 the game is un-playable. If it is 0 or
	 *                    more, raycasting calculations will not work.
	 * @param metadataa   the {@link SaveMetadata} of the game. Needed for saving
	 *                    the game so that they can be identified in the future
	 *                    based on their name, and the time of saving. (Variable
	 *                    name misspelled intentionally).
	 * @param texturePack the {@link TexturePack} object from which texture
	 *                    resources will be opened in order to have walls with
	 *                    textures.
	 * @param useRenderer the renderer and the number of threads that will be used.
	 * @param view        the view from the camera. This variable is <b>only needed
	 *                    if opening a game save!</b> If no variable is passed
	 *                    through, a new {@code View} is created.
	 * 
	 * @throws SecurityException thrown if an instance already exists.
	 */
	public static synchronized void makeWindow(int width, int height, boolean fitToScreen, Map map, float FOV,
			SaveMetadata metadataa, TexturePack texturePack, UseRenderer useRenderer, View... view)
			throws SecurityException {
		if (window == null) {
			window = new Window(width, height, fitToScreen, map, FOV, metadataa, texturePack, useRenderer, view);
		} else
			throw new SecurityException("Window has already been instantiated");
	}

	/**
	 * Getter for the Window instance.
	 * 
	 * @return the window instance.
	 * @throws IllegalStateException thrown if <code>window</code> has not been
	 *                               instantiated.
	 */
	public static synchronized Window getWindow() throws IllegalStateException {
		if (window == null) {
			throw new IllegalStateException("Window has not been instantiated");
		}
		return window;
	}

	/**
	 * {@code switchBuffer()} switches the buffers of the BufferStrategy. This
	 * allows the next frame to be rendered inside a buffer of the buffer strategy,
	 * and then it switches.
	 */
	public void switchBuffer() {
		buffer = getBufferStrategy();
		if (buffer == null) {
			System.out.println("Creating a new buffer");
			createBufferStrategy(3);
			return;
		}
		try {
			graphics = buffer.getDrawGraphics();
			graphics.drawImage(bufferImage, 0, 0, bufferImage.getWidth(), bufferImage.getHeight(), null);
		} finally {
			graphics.dispose();
		}
		buffer.show();

	}

	public void render() {
		mover.update();
		pixels = renderer.render(pixels);
		bufferImage = (BufferedImage) ImageFromArray.getImage(pixels, width, height);
	}

	@Override
	public final int getWidth() {
		return width;
	}

	@Override
	public final int getHeight() {
		return height;
	}

	public static View getView() {
		return camera.getView();
	}

	public static final SaveMetadata getMetadata() {
		return metadata;
	}

	/**
	 * Renderer getter
	 * @return <code>renderer</code>
	 */
	public final Renderer getRenderer() {
		return renderer;
	}

	/**
	 * Hot-swap <code>renderer</code> with a different {@link Renderer}.
	 * 
	 * @param use     the {@link UseRenderer} from which a {@link Renderer} will be
	 *                created
	 * @param details the details of the renderer
	 * @throws IllegalArgumentException if not {@link MultithreadedRendererDetails}
	 *                                  are passed when creating a sub-class of
	 *                                  {@link MultithreadedRendererDispatcher}.
	 */
	public final void swapRenderer(UseRenderer use, RendererDetails details) throws IllegalArgumentException {
		if ((use.type == RendererType.CHUNK || use.type == RendererType.STRIP)
				&& !(details instanceof MultithreadedRendererDetails)) {
			throw new IllegalArgumentException("RendererDetails must be MultithreadedRendererDetails");
		}
		renderer = RendererFactory.createFromEnum(use.type, details, camera);
		System.out.printf("Creating new %s Renderer%n", use.type.toString());

	}

	/**
	 * Sets the {@link SaveMetadata} of the window
	 * @param metadataa new {@link SaveMetadata}
	 */
	public static final void setMetadata(SaveMetadata metadataa) {
		metadata = metadataa;
	}

}
