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
package SwissCheese.engine.keyboard.keyActions;

import SwissCheese.annotations.ThreadSafe;
import SwissCheese.engine.display.Window;

/**
 * Pans camera right
 * 
 * @author Alex Kalinins
 * @since 2018-12-1
 * @since v0.2
 * @version v0.2
 */
@ThreadSafe
public class PanRight implements KeyAction {

	public PanRight() {
	}

	@Override
	public synchronized void doAction() {
		Window.mover.panRight();
	}

	public static String getDesc() {
		return "Look right";
	}

	@Override
	public synchronized void stopAction(){
		Window.mover.stopPanRight();
	}
	
	@Override
	public boolean equals(Object obj) {
		return  this == obj ||obj instanceof PanRight;
	}
	
	@Override
	public int hashCode() {
		return PanRight.class.hashCode();
	}

}
