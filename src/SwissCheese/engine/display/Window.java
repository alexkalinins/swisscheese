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
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import SwissCheese.engine.camera.Camera;
import SwissCheese.engine.camera.Mover;
import SwissCheese.engine.keyboard.KeyPreferenceUI;
import SwissCheese.engine.keyboard.Keyboard;
import SwissCheese.map.Map;

/**
 * A window class. This is a JFrame window which renders the game while the game
 * is running. This JFrame is only used for the main game loop, and not for any
 * other prompts or menus.
 * 
 * @author Alex Kalinins
 * @since 2018-12-11
 * @since v0.2
 * @version v0.1
 *
 */
public class Window extends JFrame {
	private static final long serialVersionUID = 4044145833868554127L;

	// not my stuff:
	private BufferStrategy buffer; // renders the next frame while the current one is being displayed
	private BufferedImage bufferImage; // a image buffer
	private Graphics graphics;

	// my stuff:
	private Renderer renderer;
	private Camera camera;
	public static Mover mover;
	private Keyboard keyboard;

	// primitives:
	private int width;
	private int height;
	private int[] pixels;

	public Window(int width, int height, Map map, int FOV) {
		this.width = width;
		this.height = height;
		
		camera = new Camera(width, height, map, map.getEntry(), FOV);
		renderer = new Renderer(map, camera, width, height);
		mover = camera.getMover();
		
		
		bufferImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		pixels = ((DataBufferInt)bufferImage.getRaster().getDataBuffer()).getData();
		
		keyboard = new Keyboard();
		KeyPreferenceUI.readFromFile();
		
		setSize(width, height);
		setResizable(false);
		setTitle("Swiss Cheese - v0.2a");
		addKeyListener(keyboard);
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * {@code switchBuffer()} switches the buffers of the BufferStrategy. This allows the next
	 * frame to be rendered inside a buffer of the buffer strategy, and then it
	 * switches.
	 */
	private void switchBuffer() {
		buffer = getBufferStrategy();
		if (buffer == null) {
			/**
			 * createBufferStrategy argument is for number of buffers. 3 looks smoothest.
			 */
			createBufferStrategy(3);
			return;
		}
		graphics = buffer.getDrawGraphics();
		graphics.drawImage(bufferImage, 0, 0, bufferImage.getWidth(), bufferImage.getHeight(), null);
		buffer.show();

	}

	public void render() {
		pixels = renderer.render(pixels);
		switchBuffer();
	}

	public synchronized final int getWidth() {
		return width;
	}

	public synchronized final int getHeight() {
		return height;
	}
	
	

}
