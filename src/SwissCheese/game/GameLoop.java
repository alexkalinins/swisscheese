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

import SwissCheese.map.Map;

/**
 * Main class of the game
 * 
 * @author Alex Kalinins
 * @since 2018-12-10
 * @since v0.2
 * @version v0.1 *
 */
public final strictfp class GameLoop implements Runnable {
	private Thread thread;
	private boolean running;
	private final float FRAME_RATE; // how many frames in a second
	private static float FRAME_DURATION; // length of a frame (in seconds)
	private Map map;

	public GameLoop(final float FRAME_RATE, int mapSize) {
		this.FRAME_RATE = FRAME_RATE;
		FRAME_DURATION = 1f / FRAME_RATE;
		map = new Map(mapSize);
	}
	
	/**
	 * starts the game thread
	 */
	private synchronized void start() {

	}

	/**
	 * stops the game safely
	 */
	private synchronized void stop() {

	}

	/**
	 * Run method of class. Main loop in here
	 */
	@Override
	public void run() {
		if (WindowTimer.isWindowUpdating()) {
			//game loop goes here
			
		}
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
	static class WindowTimer {

		static float seconds;
		static float processedSeconds = 0;
		static float deltaSeconds;
		static float futureSeconds;

		/**
		 * Gets time from System.nanoTime as seconds
		 * 
		 * @return a double of seconds.
		 */
		static float getSeconds() {
			return (float) System.nanoTime() / (float) 1000000000;
		}

		/**
		 * Checks is the Window is updating. This method limits the update rate (FPS) to
		 * the FPS rate specified in the Window constructor.
		 * 
		 * @return true if the window is updating.
		 */
		static boolean isWindowUpdating() {
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
	}


	
	
	public synchronized boolean isRunning() {
		return running;
	}

	public synchronized float getFRAME_RATE() {
		return FRAME_RATE;
	}

	
	
}
