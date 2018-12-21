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
package SwissCheese.game;

import java.util.concurrent.atomic.AtomicBoolean;

import SwissCheese.annotations.NotThreadSafe;
import SwissCheese.engine.display.Window;
import SwissCheese.map.Map;

/**
 * Main class of the 3D portion of the game. This class is only responsible for
 * running the game, and does not do other features like collecting user
 * settings or starting the game.
 * 
 * @author Alex Kalinins
 * @since 2018-12-10
 * @since v0.2
 * @version v0.2
 */
@NotThreadSafe
public final class GameLoop implements Runnable {
	private static Thread thread;
	private static AtomicBoolean running;
	private final float FRAME_RATE; // how many frames in a second
	private static float FRAME_DURATION; // length of a frame (in seconds)
	private Map map;
	private Window window;

	public GameLoop(int width, int height, final float FRAME_RATE, float FOV, int mapSize) {
		// calculate how long each frame is.
		this.FRAME_RATE = FRAME_RATE;
		FRAME_DURATION = 1f / FRAME_RATE;

		thread = new Thread(this);
		running = new AtomicBoolean();

		map = new Map(mapSize);
		window = new Window(width, height, map, FOV);
		thread = new Thread(this);
		start();
	}

	/**
	 * starts the game thread
	 */
	private void start() {
		running.set(true);
		thread.start();
	}

	/**
	 * stops the game safely
	 */
	public static void stop() {
		running.set(false);
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private float getTime() {
		return (float)System.nanoTime()/1000000000F;
	}
	
	/**
	 * Run method of class. Main loop in here
	 */
	@Override
	public void run() {
		float time;
		float passedTime;
		
		
		do {
			try {
				time = getTime();
				window.render();
				window.switchBuffer();
				
				passedTime = getTime() - time; //how much time passed
				
				if(passedTime<FRAME_DURATION) {
					//computer went ahead
					try {
						Thread.sleep((long) ((FRAME_DURATION - passedTime)*1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (NullPointerException e) {
				// happens when the game closes but not fully (BufferStrategy)
			}
		} while (running.get());
	}

	public void pauseGame() {
		try {
			thread.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		running.set(false);
	}

	public void resumeGame() {
		running.set(true);
		thread.notify();

	}

	public AtomicBoolean isRunning() {
		return running;
	}

	public float getFRAME_RATE() {
		return FRAME_RATE;
	}

}
