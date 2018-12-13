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
package SwissCheese.engine.io;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * A {@code Json} to allow interface types to be serialized by {@code Gson}.
 * <p>
 * For an interface to be serializable by {@code Gson}, it must extend
 * {@code Convertable} marker interface.
 * 
 * @author Alex Kalinins
 * @since 2018-12-13
 * @since v0.3
 * @version v0.1
 * 
 * @see SwissCheese#engine#io#Convertable
 *
 */
public class InterfacetoJson<T extends Convertable> implements JsonSerializer<T>, JsonDeserializer<T> {

	private static final String CLASSNAME = "className";
	private static final String DATA = "DATA";

	/**
	 * Helper method gets class of an object
	 * @param className name of the class
	 * @return Class of the object
	 */
	@SuppressWarnings("rawtypes")
	private Class getObjectClass(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			// e.printStackTrace();
			throw new JsonParseException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();
		final JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
		final String className = prim.getAsString();
		final Class<T> classs = getObjectClass(className);
		return context.deserialize(jsonObject, classs);
	}

	@Override
	public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(CLASSNAME, src.getClass().getName());
		jsonObject.add(DATA, context.serialize(src));
		return jsonObject;
	}

}
