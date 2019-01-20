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
package org.swisscheese.swisscheese.settings;

import org.swisscheese.swisscheese.annotations.Immutable;
import org.swisscheese.swisscheese.engine.details.UseRenderer;
import org.swisscheese.swisscheese.engine.display.WindowSize;
import org.swisscheese.swisscheese.engine.rendering.RendererType;
import org.swisscheese.swisscheese.texturePacks.TexturePack;
import org.swisscheese.swisscheese.texturePacks.TexturePackList;
import org.swisscheese.swisscheese.uiWindows.StartMenu;

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
	private final UseRenderer useRenderer;
	private final float FOV;

	public static GameSettings DEFAULT = new GameSettings();

	/**
	 * Empty constructor used to create a default {@link GameSettings} and for
	 * saving games (GSON/Serialization).
	 * 
	 * @deprecated do not use because it defaults to values.
	 */
	@Deprecated
	public GameSettings() {
		this(WindowSize.S640X480, false, -0.9f, TexturePackList.LIST.getList().get(0),
				new UseRenderer(RendererType.SINGLE_THREAD));
	}

	/**
	 * Regular constructor of {@code GameSettings}
	 * 
	 * @param windowSize  window size enum of the game.
	 * @param fitToScreen if the game will fit the user screen
	 * @param FOV         the field of view of the user. <strong>Recommended Value:
	 *                    -0.5 to -1.5</strong>
	 * @param useless     useless variable to avoid ambigous constructor.
	 * @param texturePack the texture pack the game will use.
	 */
	public GameSettings(WindowSize windowSize, boolean fitToScreen, float FOV, TexturePack texturePack,
			UseRenderer useRenderer, int... useless) {
		if (FOV > -0.1f) {
			throw new IllegalArgumentException("FOV must be less than -0.1");
		}
		this.windowSize = windowSize;
		this.fitToScreen = fitToScreen;
		this.FOV = FOV;
		this.useRenderer = useRenderer;
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
	public GameSettings(WindowSize windowSize, boolean fitToScreen, float FOV, UseRenderer useRenderer,
			GameSettings existing) {
		this(windowSize, fitToScreen, FOV, existing.getTexturePack(), useRenderer);
	}

	/**
	 * Constructor. Everything but {@code TexturePack} is infered from
	 * {@code existing}.
	 * 
	 * @param texturePack texture pack
	 * @param existing    existing GameSettings object.
	 */
	public GameSettings(TexturePack texturePack, GameSettings existing) {
		this(existing.getWindowSize(), existing.isFitToScreen(), existing.getFOV(), texturePack,
				existing.getUseRenderer());

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

	public final UseRenderer getUseRenderer() {
		return useRenderer;
	}

}
