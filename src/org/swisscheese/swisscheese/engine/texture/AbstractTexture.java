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
package org.swisscheese.swisscheese.engine.texture;

import java.io.File;

import org.swisscheese.swisscheese.annotations.Immutable;
import org.swisscheese.swisscheese.engine.io.images.PixelImage;

/**
 * Abstract Texture class for creating textures
 * 
 * @author Alex Kalinins
 * @since 2018-12-10
 * @since v0.2
 * @version v0.2
 */
@Immutable
public abstract class AbstractTexture {
	protected final PixelImage image;
	protected final int textureid;
	protected final File file;
	private static int currenttextureid = 0;

	public AbstractTexture(File file) {
		this.file = file;
		textureid = nextTextureId();
		image = new PixelImage(file);
	}

	public AbstractTexture(String fileLocation) {
		this(new File(fileLocation));
	}

	private static final synchronized int nextTextureId() {
		return ++currenttextureid;
	}

	public final PixelImage getImage() {
		return image;
	}

	public void doAction() {
		return;
	}

}
