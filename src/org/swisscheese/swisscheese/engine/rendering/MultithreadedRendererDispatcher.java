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

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.swisscheese.swisscheese.annotations.Hack;
import org.swisscheese.swisscheese.engine.camera.Camera;
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
@Hack(reason="Inheritance")
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
	 * <p>
	 * Instantiation for the {@link ArrayBlockingQueue} <code>threadPoolQueue</code>
	 * must be instantiated outside of the constructor with a quotation block.
	 * Otherwise, an exception will be thrown.
	 * 
	 * @param width       the width of the screen
	 * @param height      the height of the screen
	 * @param texturePack the {@link TexturePack} used.
	 * @param camera      the {@link Camera} used.
	 * @param map         the {@link Map} used.
	 * @param nThreads    the number of threads in the {@link ThreadPoolExecutor}.
	 * 
	 * @throws IllegalStateException - If <code>threadPoolQueue</code> is null.
	 * 
	 * @see Renderer#Renderer(float, float, TexturePack, Camera, Map)
	 */
	public MultithreadedRendererDispatcher(float width, float height, TexturePack texturePack, Camera camera, Map map,
			int nThreads) throws IllegalStateException {
		super(width, height, texturePack, camera, map);
		this.nThreads = nThreads;
		if (threadPoolQueue == null)
			throw new IllegalStateException("threadPoolQueue must be instantiated");
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
	 * Always returns true.
	 */
	@Override
	public final boolean canGetThreads() {
		return true;
	}

	/**
	 * Gets number of threads used.
	 * 
	 * @return nThreads
	 */
	@Override
	public final int getThreads() {
		return nThreads;
	}

}
