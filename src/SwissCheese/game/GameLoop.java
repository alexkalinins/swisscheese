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
public final strictfp class GameLoop implements Runnable {
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
	private synchronized void start() {
		running.set(true);
		thread.start();
	}

	/**
	 * stops the game safely
	 */
	public static synchronized void stop() {
		running.set(false);
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Run method of class. Main loop in here
	 */
	@Override
	public void run() {

		do {
			try {
				if (WindowTimer.isUpdating().get()) {
					// game loop goes here
					System.out.println("Rendering");
					window.render();
				}
				window.switchBuffer();
			} catch (NullPointerException e) {
				// happens when the game closes but not fully (BufferStrategy)
			}
		} while (running.get());
	}

	/**
	 * WindowTimer class for limiting the frame rate of the game to a specific frame
	 * rate. This way, performance is bound to the frame rate and not the hardware
	 * performance. Limitless frame refreshment can be damaging to hardware.
	 * <p>
	 * Borrowed from Vimulateur du Sol 3D project.
	 * 
	 * @author Alex Kalinins
	 * @since 2018-12-11
	 * @since v0.2
	 * @version v1.0
	 *
	 */
	public static class WindowTimer {

		private static float seconds;
		private static float processedSeconds = 0;
		private static float deltaSeconds;
		private static float futureSeconds;
		private static AtomicBoolean updating = new AtomicBoolean(false);

		/**
		 * Gets time from System.nanoTime as seconds
		 * 
		 * @return a double of seconds.
		 */
		static float getSeconds() {
			return System.nanoTime() / 1000000000f;
		}

		/**
		 * Checks is the Window is updating. This method limits the update rate (FPS) to
		 * the FPS rate specified in the Window constructor.
		 * 
		 * @return true if the window is updating.
		 */
		private static boolean isWindowUpdating() {
			futureSeconds = getSeconds(); // ahead by one frame
			deltaSeconds = futureSeconds - seconds; // difference in time
			processedSeconds += deltaSeconds;
			seconds = futureSeconds; // seconds is updated to latest time

			/**
			 * If the Window is updating, processedSeconds will be less than FRAME_DURATION.
			 */
			while (processedSeconds > FRAME_DURATION) {
				processedSeconds -= FRAME_DURATION;
				return true;
			}
			return false;
		}
		
		public static AtomicBoolean isUpdating() {
			updating.set(isWindowUpdating());
			return updating;
		}
	}

//	public synchronized void closeEverything(WindowEvent e) {
//		System.out.println("window closed by user");
//		try {
//
//			Mover.stopAllThreads();
//		} catch (InterruptedException e1) {
//			e1.printStackTrace();
//		}
//		stop();
//		System.exit(0);
//	}

	public synchronized AtomicBoolean isRunning() {
		return running;
	}

	public synchronized float getFRAME_RATE() {
		return FRAME_RATE;
	}

}
