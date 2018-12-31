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

import java.security.SecureRandom;
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
	/** The name of the {@code GameSave}. */
	private final String name;
	/** The time at which the game was saved. */
	private final Calendar date;
	/** A random long used to identify {@code GameSaves}. */
	private final long identifier;

	/**
	 * Private metadata constructor.
	 * 
	 * @param name       name of the {@code GameSave}
	 * @param identifier the random identifier for the {@code GameSave}. If left
	 *                   blank, a random {@code identifier} will be generated using
	 *                   {@link SecureRandom}.
	 */
	private SaveMetadata(String name, long... identifier) {
		this.name = name;
		date = Calendar.getInstance();
		if (identifier.length == 0) {
			this.identifier = (new SecureRandom()).nextLong();
		} else {
			this.identifier = identifier[0];
		}
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
	 * Updates the {@code Calendar} of {@code SaveMetadata}. This method creates a
	 * new instance of {@code SaveMetadata} with the <b>same</b> identifier.
	 * 
	 * @return a new instance of {@code SaveMetadata} with the same name and
	 *         identifier, but updated {@code Calendar}.
	 */
	public SaveMetadata updateMetadata() {
		return new SaveMetadata(name, identifier);
	}

	public final String getName() {
		return name;
	}

	public final Calendar getDate() {
		return date;
	}

	public final long getIdentifier() {
		return identifier;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (identifier ^ (identifier >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Equals method. This method compares this instance of {@code SaveMetadata} to
	 * Object {@code obj} based on their name and identifier.
	 * <p>
	 * This method does not look at the {@code date} so that if there are two game
	 * saves with the same name and identifier (thus older and newer
	 * {@code GameSave}), the older game-save can be deleted so that there are no
	 * confusing duplicates.s
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SaveMetadata other = (SaveMetadata) obj;
		if (identifier != other.identifier)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * To String that returns {@code name} and {@code date} in a pretty fashion:<br>
	 * "Name — HH:MM - Month, DD"
	 */
	@Override
	public String toString() {
		return String.format("%s — %s", name, PrettyTime.tohmDDMonth(date));
	}
	
	

}
