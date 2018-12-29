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

import java.util.Calendar;

import SwissCheese.annotations.Immutable;

/**
 * A class for making metadata objects for {@link GameSave} class.
 * <p>
 * This class implements the {@link Comparable} interface, where the comparison
 * is made based on its {@link Calendar} object, {@code date}.
 * 
 * @author Alex Kalinins
 * @since 2018-12-26
 * @since v0.5
 * @version v1.0
 */
@Immutable
public final class SaveMetadata implements Comparable<SaveMetadata> {
	private final String name;
	// The time at which the game was saved:
	private final Calendar date;

	/**
	 * Private metadata constructor.
	 * 
	 * @param name name of the {@code GameSave}
	 */
	private SaveMetadata(String name) {
		this.name = name;
		date = Calendar.getInstance();
	}

	/**
	 * Public static factory for {@code SaveMetadata}.
	 * 
	 * @param name name of {@code GameSave}.
	 * @return a new instance of SaveMetadata.
	 */
	public static SaveMetadata makeMetadata(String name) {
		return new SaveMetadata(name);
	}

	/**
	 * Updates the {@code Calendar} of {@code SaveMetadata}.
	 * 
	 * @return a new instance of {@code SaveMetadata} with the same name, but
	 *         updated {@code Calendar}.
	 */
	public SaveMetadata updateMetadata() {
		return new SaveMetadata(name);
	}

	public final String getName() {
		return name;
	}

	public final Calendar getDate() {
		return date;
	}

	/**
	 * The {@code compareTo} method that compares two {@code SaveMetadata} objects
	 * by their calendar.
	 * 
	 * @param other object being compared to
	 * @return compareTo value
	 * @see java.util.Calendar#compareTo(java.util.Calendar)
	 */
	@Override
	public int compareTo(SaveMetadata other) {
		return date.compareTo(other.getDate());
	}

	/**
	 * To String that returns {@code name} and {@code date} in a pretty fashion:<br>
	 * "Name — HH:MM - DD, Month"
	 */
	@Override
	public String toString() {
		return String.format("%s — %s", name, PrettyTime.tohmDDMonth(date));
	}

}
