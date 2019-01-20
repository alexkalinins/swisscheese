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
package org.swisscheese.swisscheese.gameSaving;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A wrapper class for a {@code List} of {@link GameSave} objects.
 * <p>
 * {@code GameSaveList} object is serialized into a file, and then can be opened
 * to retrieved {@code GameSaves} so that the user can resume a previously saved
 * game.s
 * 
 * @author Alex Kalinins
 * @since 2018-12-28
 * @since v0.5
 * @version v1.0
 */
public final class GameSaveList {
	private List<GameSave> list = new ArrayList<>();

	/**
	 * Constructor
	 */
	public GameSaveList() {

	}

	public final List<GameSave> getList() {
		return list;
	}

	public final void add(GameSave arg) {
		list.add(arg);
	}

	public final GameSave get(int index) {
		return list.get(index);
	}
	
	public final void remove(int index) {
		list.remove(index);
	}
	
	public final int size() {
		return list.size();
	}
	
	public final void sort() {
		Collections.sort(list);
	}

}
