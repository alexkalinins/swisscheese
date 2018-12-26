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
import java.util.ArrayList;
import java.util.List;

import SwissCheese.annotations.Immutable;

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
 */
//TODO make wiki article
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
		for (String path: filePaths) {
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
}
