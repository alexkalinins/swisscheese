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
package SwissCheese.settings;

import SwissCheese.annotations.Immutable;
import SwissCheese.engine.display.WindowSize;
import SwissCheese.texturePacks.TexturePack;
import SwissCheese.uiWindows.StartMenu;

/**
 * This is a game settings object class that contains settings of the game that
 * are used when the game is loaded from the {@link StartMenu}.
 * <p>
 * A {@code GameSettings} object can be serialized and deserialized to save the
 * preferences.
 * <p>
 * The constructors of {@code GameSettings} are overloaded in a way that makes
 * it easy to have multiple windows where the {@code GameSettings} can change.
 * If there will be more fields for {@code GameSettings}, a builder class can be
 * made in the future.
 * 
 * @author Alex Kalinins
 * @since 2018-12-26
 * @since v0.5
 * @version v1.0
 *
 */
@Immutable
public class GameSettings {
	private final WindowSize windowSize;

	/**
	 * if {@code fitToScreen} boolean is true, then {@code windowSize} is
	 * disregarded.
	 */
	private final boolean fitToScreen;
	private final TexturePack texturePack;
	private final float FOV;

	//TODO make a default texturepack.
	public static GameSettings DEFAULT = new GameSettings(WindowSize.S640X480, false, -0.9f, null, 1);

	/**
	 * A no-args constructor for {@code GSON} serialization and deserialization.
	 */
	@Deprecated
	public GameSettings() {
		this(null,false,-0.9f, null,1);
	}

	/**
	 * Regular constructor of {@code GameSettings}
	 * 
	 * @param windowSize  window size enum of the game.
	 * @param fitToScreen if the game will fit the user screen
	 * @param FOV         the field of view of the user. <strong>Recommended Value:
	 *                    -0.5 to -1.5</strong>
	 * @param useless useless variable to avoid ambigous constructor.
	 * @param texturePack the texture pack the game will use.
	 */
	public GameSettings(WindowSize windowSize, boolean fitToScreen, float FOV, TexturePack texturePack,
			int... useless) {
		if (FOV > -0.1f) {
			throw new IllegalArgumentException("FOV must be less than -0.1");
		}
		this.windowSize = windowSize;
		this.fitToScreen = fitToScreen;
		this.FOV = FOV;
		this.texturePack = texturePack;
	}

	/**
	 * Constructor from existing {@code GameSettings} and {@code windowSize} and
	 * {@code fitToScreen}. This constructor would be used by the
	 * {@link SettingsPanel} panel, since texture-pack selection is done on a
	 * different panel.
	 * <p>
	 * Infers texture pack from {@code existing}.
	 * 
	 * @param windowSize  window size enum of the game
	 * @param fitToScreen if the game will fit the user screen
	 * @param existing    an existing {@code GameSave}
	 */
	public GameSettings(WindowSize windowSize, boolean fitToScreen, float FOV, GameSettings existing) {
		this(windowSize, fitToScreen, FOV, existing.getTexturePack());
	}

	/**
	 * Constructor. Everything but {@code TexturePack} is infered from
	 * {@code existing}.
	 * 
	 * @param texturePack texture pack
	 * @param existing    existing GameSettings object.
	 */
	public GameSettings(TexturePack texturePack, GameSettings existing) {
		this(existing.getWindowSize(), existing.isFitToScreen(), existing.getFOV(), texturePack);

	}

	public final WindowSize getWindowSize() {
		return windowSize;
	}

	public final boolean isFitToScreen() {
		return fitToScreen;
	}

	public final TexturePack getTexturePack() {
		return texturePack;
	}

	public final float getFOV() {
		return FOV;
	}

}
