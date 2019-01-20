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

import java.text.DateFormatSymbols;
import java.util.Calendar;

/**
 * A class for converting a {@code Calendar} object into an easy to read
 * {@code String}.
 * 
 * @author Alex Kalinins
 * @since 2018-12-28
 * @since v0.5
 * @version v1.0
 * 
 * @see java.util.Calendar
 */
class PrettyTime {

	/**
	 * Returns {@code calendar} in the format:<br>
	 * HH:MM - DD, Month
	 * 
	 * @param calendar the {@code Calendar} being converted.
	 * @return calendar in pretty format.
	 */
	static String tohmDDMonth(Calendar calendar) {
		int minute = calendar.get(Calendar.MINUTE);
		return String.format("%d:%s - %s %d", calendar.get(Calendar.HOUR), String.format("%s%d",(minute<10?'0':""),minute),
				intToMonth(calendar.get(Calendar.MONTH)), calendar.get(Calendar.DATE));
	}

	/**
	 * Converts {@code month} integer into a Month word (e.g., 2 = February)
	 * 
	 * @param month the month number (starting count at 0)
	 * @return month as word
	 */
	private static String intToMonth(int month) {
		return new DateFormatSymbols().getMonths()[month];
	}

}
