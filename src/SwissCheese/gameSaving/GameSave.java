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
package SwissCheese.gameSaving;

import SwissCheese.annotations.Immutable;
import SwissCheese.engine.camera.View;
import SwissCheese.map.Map;

/**
 * This is a class for a {@code GameSave} object that contains data of the user
 * game that can be saved to file to save the game progress, and reopened at a
 * later time to resume the game.
 * 
 * @author Alex Kalinins
 * @since 2018-12-26
 * @since v0.5
 * @version v1.0
 */
@Immutable
public class GameSave implements Comparable<GameSave> {
	private final Map map;
	private final View view;
	private final SaveMetadata metadata;

	/**
	 * Constructor.
	 * 
	 * @param map      {@link Map} object of the current game.
	 * @param view     {@link View} object of the current game (players camera)
	 * @param metadata the metadata of the {@code GameSave}.
	 */
	public GameSave(Map map, View view, SaveMetadata metadata) {
		super();
		this.map = map;
		this.view = view;
		this.metadata = metadata;
	}

	public final Map getMap() {
		return map;
	}

	public final View getView() {
		return view;
	}

	public final SaveMetadata getMetadata() {
		return metadata;
	}

	/**
	 * Compares two {@code GameSave} objects by their {@code SaveMetadata} objects.
	 * 
	 * @param other the other {@code GameSave} object being compared.
	 * @return comparison value
	 * @see SwissCheese.gameSaving.SaveMetadata#compareTo(SwissCheese.gameSaving.SaveMetadata)
	 */
	public int compareTo(GameSave other) {
		return metadata.compareTo(other.getMetadata());
	}

	@Override
	public String toString() {
		return metadata.toString();
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof GameSave))
			return false;
		if (obj == this)
			return true;
		return metadata.equals(((GameSave)obj).getMetadata());
	}

}
