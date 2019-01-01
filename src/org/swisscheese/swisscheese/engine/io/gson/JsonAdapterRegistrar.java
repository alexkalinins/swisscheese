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
package org.swisscheese.swisscheese.engine.io.gson;

import java.lang.reflect.Type;

import org.swisscheese.swisscheese.annotations.Immutable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Registers a {@code Gson} adapter to allow certain types such as interfaces to
 * be serialized {@code Gson}
 * 
 * @author Alex Kalinins
 * @since 2018-12-13
 * @since v0.3
 * @version v0.1
 */
@Immutable
public class JsonAdapterRegistrar {

	/**
	 * Public static {@code GSON} factory for making a {@code Gson} with a
	 * registered adapter.
	 * 
	 * @param type        Class being serialized
	 * @param typeAdapter type adapter being registered
	 * @return a new {@code GSON} registered adapter
	 */
	public static Gson makeGson(Type type, Object typeAdapter) {
		GsonBuilder b = new GsonBuilder();
		b.registerTypeAdapter(type, typeAdapter);
		return b.create();
	}
}
