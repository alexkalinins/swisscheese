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
package org.swisscheese.swisscheese;

import java.awt.List;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.Future;

import org.swisscheese.swisscheese.engine.details.MultithreadedRendererDetails;
import org.swisscheese.swisscheese.engine.details.RendererDetails;
import org.swisscheese.swisscheese.engine.keyboard.Keyboard;
import org.swisscheese.swisscheese.engine.keyboard.Keys;
import org.swisscheese.swisscheese.engine.rendering.ChunkRendererDispatcher;
import org.swisscheese.swisscheese.engine.rendering.MultithreadedRendererDispatcher;
import org.swisscheese.swisscheese.engine.rendering.Renderer;
import org.swisscheese.swisscheese.engine.rendering.RendererFactory;
import org.swisscheese.swisscheese.engine.rendering.SingleThreadedRenderer;
import org.swisscheese.swisscheese.engine.rendering.StripRendererDispatcher;
import org.swisscheese.swisscheese.map.maze.CellGrid;
import org.swisscheese.swisscheese.map.maze.Generator;
import org.swisscheese.swisscheese.texturePacks.TexturePack;
import org.swisscheese.swisscheese.texturePacks.TexturePackList;

/**
 * <h1>Where is the criteria of the project met?</h1>
 * <h2>Inheritance</h2> By criteria this project must contain an inheritance
 * hierarchy of an abstract class and sub-classes of the abstract class.
 * <h3>Abstract class:</h3> The abstract class that was made for this project is
 * {@link Renderer}, which is used to render the frame onto the screen.
 * <h4>Abstract class method criteria:</h4> The criteria is met in the following
 * classes. There may be more methods that fit the criteria.
 * <ul>
 * <li>Abstract method: {@link Renderer#isMultithreaded()}</li>
 * <li>Regular method (overridden): {@link Renderer#render(int[])}</li>
 * <li>Final methods:</li>
 * <ol>
 * <li>Renderer#fillBackground()</li>
 * <li>{@link Renderer#getMover()}</li>
 * </ol>
 * <li>Static Method:
 * {@link Renderer#changeTextures(org.swisscheese.swisscheese.texturePacks.TexturePack)}</li>
 * </ul>
 * <h3>Classes extending {@link Renderer}:</h3>
 * <ul>
 * <li>{@link SingleThreadedRenderer}</li>
 * <li>{@link MultithreadedRendererDispatcher}</li>
 * <ul>
 * <li>{@link StripRendererDispatcher}</li>
 * <li>{@link ChunkRendererDispatcher}</li>
 * </ul>
 * </ul>
 * <h4>Sub-class criteria</h4>
 * <ul>
 * <li>Exclusive methods:</li>
 * <ul>
 * <li>{@link SingleThreadedRenderer}:{@link SingleThreadedRenderer#getAverageRenderingTime()}
 * {@code ->} returns average time for rendering of 10 passes</li>
 * <li>{@link MultithreadedRendererDispatcher}:
 * {@link MultithreadedRendererDispatcher#getMultiDetails()} {@code ->} converts
 * {@link RendererDetails} of {@link Renderer} to
 * {@link MultithreadedRendererDetails} to include number of threads used.</li>
 * </ul>
 * </ul>
 * <h3>Polymorphism:</h3> The only way to create an {@link Renderer} object is
 * to use {@link RendererFactory}
 * <h3>Collections of Objects</h3> List of some collections used in the game:
 * <ul>
 * <li>{@link Deque} (as a stack): {@link Generator} log of
 * {@link CellGrid.Direction} enums</li>
 * <li>{@link Deque} (as a queue): {@link StripRendererDispatcher} and
 * {@link ChunkRendererDispatcher} to collect a queue of multi-threading
 * {@link Future}</li>
 * <li>{@link Map}: {@link Keyboard}: stores {@link Keys} enums as values and
 * virtual keyboard codes ({@link Integer}) as keys.
 * <li>{@link List}: {@link TexturePackList} stores {@link TexturePack}
 * objects</li>
 * </ul>
 * <br>
 * <h5>About this class:</h5> This class provides javadoc and information on the
 * criteria met in the project. Does not have any uses besides javadoc. <br>
 * For more info about installing this game, see
 * <a href='https://gitlab.org/poof/swisscheese'>read me file</a> on Gitlab.
 * 
 * 
 * 
 * @author Alex Kalinins
 * @since 2019-1-22
 * @since v1.0
 * @version v1.0
 *
 */
public class Criteria {

	/**
	 * Does this project fulfill criteria???
	 * 
	 * @return true
	 */
	public static boolean fullfilsCriteria() {
		return true;
	}

}
