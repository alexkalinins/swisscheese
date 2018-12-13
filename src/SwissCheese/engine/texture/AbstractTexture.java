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
package SwissCheese.engine.texture;

import java.io.File;

import SwissCheese.engine.io.ImageToPixels;

/**
 * Abstract Texture class for creating textures
 * 
 * @author Alex Kalinins
 * @since 2018-12-10
 * @since v0.2
 * @version v0.1
 *
 */
public abstract class AbstractTexture {
	protected final int width;
	protected final int height;
	protected final int textureid;
	protected final File file;
	private static int currenttextureid = 0;
	private final int[] pixels;

	public AbstractTexture(File file) {
		textureid = nextTextureId();
		this.file = file;
		pixels = ImageToPixels.getPixels(file);
		width = ImageToPixels.getWidth();
		height = ImageToPixels.getHeight();
	}

	public AbstractTexture(String fileLocation) {
		this(new File(fileLocation));
	}


	private final static int nextTextureId() {
		return ++currenttextureid;
	}

	public synchronized int getWidth() {
		return width;
	}

	public synchronized File getFile() {
		return file;
	}

	public synchronized int getHeight() {
		return height;
	}

	public final synchronized int[] getPixels() {
		return pixels;
	}

}
