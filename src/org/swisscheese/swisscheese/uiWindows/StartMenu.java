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

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu.Separator;
import javax.swing.JSeparator;

import org.swisscheese.swisscheese.engine.details.UseRenderer;
import org.swisscheese.swisscheese.engine.display.WindowSize;
import org.swisscheese.swisscheese.engine.io.images.FileToImageIcon;
import org.swisscheese.swisscheese.gameSaving.GameSave;
import org.swisscheese.swisscheese.gameSaving.GameSaveManager;
import org.swisscheese.swisscheese.map.DifficultyLevel;
import org.swisscheese.swisscheese.settings.GameSettings;
import org.swisscheese.swisscheese.settings.GameSettingsManager;

/**
 * This is the menu that the user sees every single time they start the game.
 * This menu will get all necessary configuration details from the user prior to
 * starting the game. When this menu starts a game, it is disposed.
 * <p>
 * When the user closes the game (through the {@link InGameMenu}), the game
 * portion closes, and the user sees this menu again, where they can choose to
 * fully quit the game or start playing a new game.
 * 
 * @author Alex Kalinins
 * @since 2018-12-26
 * @since v0.5
 * @version v1.0
 */
public class StartMenu extends JDialog {
	private static final long serialVersionUID = 4125313847974704824L;
	private Boolean newGame = null;
	private final Font REALITY_HYPER;
	private final File GREETING_IMAGE = new File("resources/greeting-image.png");
	private final URL WIKI;

	private final CardLayout cards;
	private final JPanel mainPanel;

	private GameSettings settings;

	// panels that will be added to the CardLayout:
	private JPanel homePanel;
	private KeyBindsPanel keysPanel;
	private NewGamePanel newGamePanel;
	private OpenGamePanel openGamePanel;
	private TexturePackPanel texPackPanel;
	private SettingsPanel settingsPanel;

	// final name variables for CardLayout cards
	private final String HOME = "home";
	private final String KEYBINDS = "keyBinds";
	private final String OPENGAME = "openGame";
	private final String TEXPACK = "texturePacks";
	private final String SETTINGS = "settings";
	/**
	 * Must be added last to the {@code CardLayout} (contains buttons that link to
	 * other cards).
	 */
	private final String NEWGAME = "newGame";

	private final JButton startNewGame = new JButton("Start New Game");
	private final JButton openSavedGame = new JButton("Open Saved Game");
	private final JButton selectTexturePack = new JButton("Select Texture-Pack");
	private final JButton goToSettings = new JButton("Settings");
	private final JButton aboutGame = new JButton("SwissCheese Wiki");
	private final JButton exitToOS = new JButton("Exit to the OS");
	private final JButton goBack = new JButton("Return");

	private boolean showingHome;
	private boolean showingKeyBinds;

	private DifficultyLevel diff;
	private String gameName;
	private GameSave gameSave;

	public StartMenu() {
		cards = new CardLayout();
		mainPanel = new JPanel(cards);
		settings = GameSettingsManager.MANAGER.getSettings();

		setModal(true);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("User exited from Start Menu");
				System.exit(0);
			}
		});
		setSize(300, 400);
		setTitle("SwissCheese - v0.5a");
		REALITY_HYPER = registerFont(new File("resources/reality-hyper-regular.ttf"));
		URL wikiUrl = null;
		try {
			wikiUrl = new URL("https://gitlab.com/poof/swisscheese/wikis/home");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		WIKI = wikiUrl;

		buildFrame();

		setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.CENTER);
		goBack.addActionListener(e -> goBack());
		add(goBack, BorderLayout.SOUTH);
		pack();
		showHome();
		setVisible(true);
	}

	private void goBack() {
		if (showingKeyBinds) {
			showSettings();
		} else if (showingHome) {
			return;
		} else {
			showHome();
		}
	}

	private void showHome() {
		showingHome = true;
		showingKeyBinds = false;
		goBack.setVisible(false);
		validate();
		cards.show(mainPanel, HOME);
	}

	private void showKeyBinds() {
		showingHome = false;
		showingKeyBinds = true;
		goBack.setVisible(true);
		validate();
		cards.show(mainPanel, KEYBINDS);
	}

	private void showNewGame() {
		showingHome = false;
		goBack.setVisible(true);
		validate();
		cards.show(mainPanel, NEWGAME);
	}

	private void showOpenGame() {
		showingHome = false;
		goBack.setVisible(true);
		validate();
		cards.show(mainPanel, OPENGAME);
	}

	private void showSettings() {
		showingHome = false;
		showingKeyBinds = false;
		goBack.setVisible(true);
		validate();
		cards.show(mainPanel, SETTINGS);
	}

	private void showTexturePacks() {
		showingHome = false;
		goBack.setVisible(true);
		validate();
		cards.show(mainPanel, TEXPACK);
	}

	private void buildFrame() {
		keysPanel = new KeyBindsPanel(this);
		mainPanel.add(keysPanel, KEYBINDS);

		settingsPanel = new SettingsPanel(this, true, settings);
		settingsPanel.addKeyBindActionListener(e -> showKeyBinds());
		settingsPanel.addApplyActionListener(e -> applySettingsChanges());
		mainPanel.add(settingsPanel, SETTINGS);

		texPackPanel = new TexturePackPanel();
		texPackPanel.addApplyChangesActionListner(e -> applyTexturePackChanges());
		mainPanel.add(texPackPanel, TEXPACK);

		newGamePanel = new NewGamePanel();
		newGamePanel.addStartActionListner(e -> startNewGame());
		mainPanel.add(newGamePanel, NEWGAME);

		openGamePanel = new OpenGamePanel();
		openGamePanel.addStartActionListner(e -> startSavedGame());
		mainPanel.add(openGamePanel, OPENGAME);

		homePanel = makeHomePanel();
		bindHomeButtons();
		mainPanel.add(homePanel, HOME);

	}

	private void bindHomeButtons() {
		startNewGame.addActionListener(e -> showNewGame());
		openSavedGame.addActionListener(e -> showOpenGame());
		selectTexturePack.addActionListener(e -> showTexturePacks());
		goToSettings.addActionListener(e -> showSettings());
		aboutGame.addActionListener(e -> BrowserOpener.openLink(WIKI));
		exitToOS.addActionListener(e -> System.exit(0));
	}

	private JPanel makeHomePanel() {
		Dimension space = new Dimension(0, 5);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JLabel homeTitle = new JLabel("SwissCheese");
		homeTitle.setFont(REALITY_HYPER.deriveFont(Font.PLAIN, 30));
		homeTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(homeTitle);
		panel.add(Box.createRigidArea(space));
		panel.add(new JSeparator(Separator.HORIZONTAL));
		panel.add(Box.createRigidArea(space));

		JLabel greetingImage = new JLabel(FileToImageIcon.getScaledToBoundsIcon(GREETING_IMAGE, this.getWidth(), 150));
		greetingImage.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(greetingImage);
		panel.add(Box.createRigidArea(space));

		JPanel p1 = new JPanel(new BorderLayout());
		startNewGame.setAlignmentX(Component.LEFT_ALIGNMENT);
		p1.add(startNewGame);
		panel.add(p1);
		panel.add(Box.createRigidArea(space));

		JPanel p2 = new JPanel(new BorderLayout());
		openSavedGame.setAlignmentX(Component.LEFT_ALIGNMENT);
		p2.add(openSavedGame);
		panel.add(p2);
		panel.add(Box.createRigidArea(space));

		JPanel p3 = new JPanel(new BorderLayout());
		selectTexturePack.setAlignmentX(Component.LEFT_ALIGNMENT);
		p3.add(selectTexturePack);
		panel.add(p3);
		panel.add(Box.createRigidArea(space));

		JPanel p4 = new JPanel(new BorderLayout());
		goToSettings.setAlignmentX(Component.LEFT_ALIGNMENT);
		p4.add(goToSettings);
		panel.add(p4);
		panel.add(Box.createRigidArea(space));

		JPanel p5 = new JPanel(new BorderLayout());
		aboutGame.setAlignmentX(Component.LEFT_ALIGNMENT);
		p5.add(aboutGame);
		panel.add(p5);
		panel.add(Box.createRigidArea(space));

		JPanel p6 = new JPanel(new BorderLayout());
		exitToOS.setAlignmentX(Component.LEFT_ALIGNMENT);
		p6.add(exitToOS);
		panel.add(p6);
		panel.add(Box.createRigidArea(space));

		return panel;
	}

	public Boolean isNewGame() {
		return newGame;
	}

	private void startNewGame() {
		newGame = true;
		gameName = newGamePanel.getNameField();
		diff = newGamePanel.getDifficultyField();
		dispose();
	}

	private void startSavedGame() {
		newGame = false;
		gameSave = GameSaveManager.getInstance().getList().get(openGamePanel.getGameSaveIndex());
		dispose();
	}

	/**
	 * Polls changes from {@code settingsPanel} into a new {@link GameSettings}
	 * object. Will serialize the object to file.
	 */
	private void applySettingsChanges() {
		final WindowSize size = (WindowSize) settingsPanel.getSizeSelect();
		final boolean fitToScreen = settingsPanel.getFitToScreen();
		final float FOV = settingsPanel.getFovSelection();
		final UseRenderer useRenderer = settingsPanel.getUseRenderer();
		if(useRenderer == null) {
			return;
		}
		settings = new GameSettings(size, fitToScreen, FOV, useRenderer, settings);
		GameSettingsManager.MANAGER.updateSettings(settings);
	}

	private void applyTexturePackChanges() {
		settings = new GameSettings(texPackPanel.getSelectedTexturePack(), settings);
		GameSettingsManager.MANAGER.updateSettings(settings);
	}

	/**
	 * Registers a font from the file {@code font} in the GraphicsEnvirnoment
	 * 
	 * @param font font file being registered (ttf).
	 */
	private Font registerFont(File font) {
		Font fontt = null;
		try {
			fontt = Font.createFont(Font.TRUETYPE_FONT, font);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(fontt);
			System.out.printf("%s font registered%n", fontt.getName());
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}

		return fontt;
	}

	public final GameSettings getSettings() {
		return settings;

	}

	public final DifficultyLevel getDiff() {
		if (newGame) {
			return diff;
		} else {
			throw new IllegalStateException("Cannot return diff: not new game");
		}
	}

	public final String getGameName() {
		if (newGame) {
			return gameName;
		} else {
			throw new IllegalStateException("Cannot return name: not new game");
		}
	}

	public final GameSave getGameSave() {
		if (newGame) {
			throw new IllegalStateException("Cannot return gameSave: new game");
		} else {
			return gameSave;
		}
	}
}
