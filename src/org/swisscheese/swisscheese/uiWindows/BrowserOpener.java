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
package org.swisscheese.swisscheese.uiWindows;

import java.awt.Desktop;
import java.net.URL;

/**
 * A class for opening a website link in the default browser of the OS.
 * 
 * @author Alex Kalinins
 * @since 2018-12-28
 * @since v0.5
 * @version v1.0
 */
final class BrowserOpener {

	/**
	 * Opens {@code url} in default browser of the native OS.
	 * @param url url link being opened.
	 */
	static void openLink(URL url) {
		Desktop desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(url.toURI());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
