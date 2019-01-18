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

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.naming.InsufficientResourcesException;

import org.swisscheese.swisscheese.annotations.Hack;
import org.swisscheese.swisscheese.engine.camera.Camera;
import org.swisscheese.swisscheese.engine.details.MultithreadedRendererDetails;
import org.swisscheese.swisscheese.map.Map;
import org.swisscheese.swisscheese.texturePacks.TexturePack;

/**
 * An abstract class for creating a Dispatcher that performs ray-casting
 * rendering, but distributes the work load onto multiple threads for improving
 * performance and efficiency of the program.
 * <p>
 * A sub-class of <code>MultithreadedRendererDispatcher</code> would assign
 * classes that follow the naming convention of
 * {@code Render<rendering-strategy>}, for example {@link RenderStrip}. The
 * individual work unit of the Dispatcher (such as <code>RenderStrip</code>)
 * must implement the {@link Callable} interface of type {@link Void}.
 * <p>
 * Once the {@code Callable} work unit is submitted to the thread-pool for
 * execution, its {@link Future} is stored in a queue.
 * 
 * @author Alex Kalinins
 * @since 2019-01-12
 * @since v0.5
 * @version v1.0
 */
@Hack(reason = "Inheritance")
public abstract class MultithreadedRendererDispatcher extends Renderer {
	/** ThreadPoolExecutor that contains threads that carry-out rendering. */
	protected final ThreadPoolExecutor pool;
	/** Stores Futures of rendering operations. */
	protected final Deque<Future<Void>> futureQueue = new ArrayDeque<>();
	/** ArrayBlockingQueue for storing Runnables to be ran (by thread-pool) */
	protected static ArrayBlockingQueue<Runnable> threadPoolQueue;
	/** The amount of threads used to create <code>pool</code>. */
	private final int nThreads;

	/**
	 * Constructor for <code>MultithreadedRendererDispatcher</code>.
	 * 
	 * @param width                      the width of the screen
	 * @param height                     the height of the screen
	 * @param texturePack                the {@link TexturePack} used.
	 * @param camera                     the {@link Camera} used.
	 * @param map                        the {@link Map} used.
	 * @param nThreads                   the number of threads in the
	 *                                   {@link ThreadPoolExecutor}.
	 * @param arrayBlockingQueueCapacity the capacity of the
	 *                                   {@link ArrayBlockingQueue}.
	 * @see Renderer#Renderer(float, float, TexturePack, Camera, Map)
	 */
	protected MultithreadedRendererDispatcher(float width, float height, TexturePack texturePack, Camera camera,
			Map map, int nThreads, int arrayBlockingQueueCapacity) throws IllegalStateException {
		super(width, height, texturePack, camera, map);
		this.nThreads = nThreads;
		threadPoolQueue = new ArrayBlockingQueue<>(arrayBlockingQueueCapacity);
		pool = new ThreadPoolExecutor(nThreads, 64, 100, TimeUnit.MILLISECONDS, threadPoolQueue);
	}

	/**
	 * A constructor from existing {@link MultithreadedRendererDetails}.
	 * @param details the details from which it is created
	 * @param camera camera used
	 * @param arrayBlockingQueueCapacity capacity of {@link ArrayBlockingQueue}.
	 */
	protected MultithreadedRendererDispatcher(MultithreadedRendererDetails details, Camera camera,
			int arrayBlockingQueueCapacity) {
		super(details.getRegularDetails(), camera);
		this.nThreads = details.nThreads;
		threadPoolQueue = new ArrayBlockingQueue<>(arrayBlockingQueueCapacity);
		pool = new ThreadPoolExecutor(nThreads, 64, 100, TimeUnit.MILLISECONDS, threadPoolQueue);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.swisscheese.swisscheese.engine.rendering.Renderer#render(int[])
	 */
	@Override
	public abstract int[] render(int[] pixels);

	/**
	 * Makes a new {@link MultithreadedRendererDetails} from the
	 * {@link #getDetails()} method of the superclass along with the number of
	 * threads used by the {@link MultithreadedRendererDispatcher}.
	 * 
	 * @return new {@link MultithreadedRendererDetails}
	 */
	public MultithreadedRendererDetails getMultiDetails() {
		return new MultithreadedRendererDetails(getDetails(), nThreads);
	}

	/**
	 * Checks to see if this platforms supports MultiThreading-based operations
	 * inside the Java Virtual Machine.<br>
	 * <b>DO NOT MODIFY-WILL NOT WORK!</b>
	 * 
	 * @return true if multithreading is supported.
	 */
	@Override
	public boolean isMultithreaded() {
		// safest way to do multithreading.
		try {
			// creating a new thread to see if it works
			Thread testThread = new Thread();
			Field validationData = testThread.getClass().getDeclaredField("MAX_PRIORITY");
			validationData.setAccessible(true);
			int supportedMaximumThreads = (int) validationData.get(testThread);
			if (supportedMaximumThreads == 1)
				// going to the catch bloc
				throw new InsufficientResourcesException();
			else
				return true;
		} catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
			System.out.println("Something went wrong!");
			System.exit(-1);
			return false;
		} catch (InsufficientResourcesException in) {
			System.out.println("This JVM does not support multithreading");
			return false;
		}
	}

	/**
	 * Getter for the thread
	 * 
	 * @return
	 */
	public int getNThreads() {
		return nThreads;
	}
}
