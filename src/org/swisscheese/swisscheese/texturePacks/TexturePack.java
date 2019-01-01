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
package org.swisscheese.swisscheese.texturePacks;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.swisscheese.swisscheese.annotations.Immutable;

/**
 * A texture pack object that contains WallTextures to be used in game. A user
 * can create their own texture packs which may eventually be added to the game
 * natively. There will be an article on the SwissCheese wiki on creating a
 * texturepack
 *
 * 
 * @author Alex Kalinins
 * @since 2018-12-22
 * @since v0.5
 * @version v0.1
 * 
 * @see <a href=
 *      "https://gitlab.com/poof/swisscheese/wikis/Texture--Packs">SwissCheese
 *      wiki - Texture-Packs</a>
 */
@Immutable
public final class TexturePack {
	private final String name;
	private final String author;
	private final String version;
	private String[] filePaths;
	private List<File> fileList = new ArrayList<>();

	/**
	 * Constructor
	 * 
	 * @param fileList List of files containing texture images.
	 * @param name     The name of the texture-pack.
	 * @param author   The name of the texture-pack author.
	 * @param version  The version of game for which this pack was made.
	 */
	public TexturePack(String name, String author, String version, String[] filePaths) {
		this.filePaths = filePaths;
		this.name = name;
		this.author = author;
		this.version = version;
	}

	/**
	 * 
	 */
	public void make() {
		for (String path : filePaths) {
			fileList.add(new File(path.replaceAll("#name#", name)));
		}
	}

	/**
	 * No args constructor for GSON serialization.
	 */
	public TexturePack() {
		this(null, null, null, null);
	}

	public List<File> getFileList() {
		return fileList;
	}

	public String[] getFilePaths() {
		return filePaths;
	}

	public String getName() {
		return name;
	}

	public String getAuthor() {
		return author;
	}

	public String getVersion() {
		return version;
	}

	/**
	 * To string of {@code TexturePack}.
	 */
	@Override
	public String toString() {
		return String.format("%s (%s) by %s", name, version, author);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((fileList == null) ? 0 : fileList.hashCode());
		result = prime * result + Arrays.hashCode(filePaths);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TexturePack other = (TexturePack) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (fileList == null) {
			if (other.fileList != null)
				return false;
		} else if (!fileList.equals(other.fileList))
			return false;
		if (!Arrays.equals(filePaths, other.filePaths))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
}
