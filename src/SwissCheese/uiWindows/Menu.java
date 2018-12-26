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
package SwissCheese.uiWindows;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import SwissCheese.annotations.Immutable;
import SwissCheese.engine.keyboard.KeyActionPreference;
import SwissCheese.engine.keyboard.KeyPreferenceIO;
import SwissCheese.engine.keyboard.Keys;
import SwissCheese.engine.keyboard.keyActions.ExitGame;
import SwissCheese.engine.keyboard.keyActions.GoBackward;
import SwissCheese.engine.keyboard.keyActions.GoForward;
import SwissCheese.engine.keyboard.keyActions.GoLeft;
import SwissCheese.engine.keyboard.keyActions.GoRight;
import SwissCheese.engine.keyboard.keyActions.KeyAction;
import SwissCheese.engine.keyboard.keyActions.OpenMenu;
import SwissCheese.engine.keyboard.keyActions.PanLeft;
import SwissCheese.engine.keyboard.keyActions.PanRight;
import SwissCheese.engine.keyboard.keyActions.SaveGame;

/**
 * This class is the in-game menu that the user sees when they open the menu in
 * the game.
 * 
 * @author Alex Kalinins
 * @since 2018-12-23
 * @since v0.5
 * @version v0.1
 */
@Immutable
public final class Menu extends JFrame {
	private static final long serialVersionUID = 3038952637134428918L;
	private final CardLayout cards;
	private final JPanel mainPanel;
	private final Map<KeyAction, Keys> actions;

	private final String HOME = "home";
	private final String KEYS = "keys";
	private final String SETTINGS = "settings";

	private JPanel settingsPanel;
	private JPanel keyBindsPanel;
	private JPanel homePanel;

	// home card:
	private final JButton saveGame = new JButton("Save Game");
	private final JButton keyBinds = new JButton("Key Binds");
	private final JButton settings = new JButton("Settings");
	private final JButton goBack = new JButton("Return");
	private final JButton exitGame = new JButton("Exit To Menu");
	// keybind card:
	private final JComboBox<Keys> dForward = new JComboBox<>(Keys.values());
	private final JComboBox<Keys> dBackward = new JComboBox<>(Keys.values());
	private final JComboBox<Keys> dLeft = new JComboBox<>(Keys.values());
	private final JComboBox<Keys> dRight = new JComboBox<>(Keys.values());
	private final JComboBox<Keys> dPanLeft = new JComboBox<>(Keys.values());
	private final JComboBox<Keys> dPanRight = new JComboBox<>(Keys.values());
	private final JComboBox<Keys> dOpenMenu = new JComboBox<>(Keys.values());
	private final JComboBox<Keys> dSaveGame = new JComboBox<>(Keys.values());
	private final JComboBox<Keys> dExitGame = new JComboBox<>(Keys.values());
	private final JButton applyKeys = new JButton("Apply Changes");

	private static boolean displaying = false;
	private static Menu menu;

	/**
	 * Private Constructor. The only way to display the {@code Menu} is through
	 * {@link Menu#display()}.
	 */
	private Menu() {
		actions = makeActionMap();
		cards = new CardLayout();
		mainPanel = new JPanel(cards);
		setSize(300, 300);
		setTitle("Menu");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		setLayout(new BorderLayout());

		initCards();

		// do AFTER making cards
		Container buttons = bottomButtonContainer();
		add(buttons, BorderLayout.SOUTH);
		add(mainPanel, BorderLayout.CENTER);
		pack();

		cards.show(mainPanel, HOME);
		pack();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setVisible(true);

	}

	/**
	 * Makes a {@code Container} that contains two {@code JButtons}; {@code goBack}
	 * and {@code exitGame}.
	 * <p>
	 * Also assigns action listeners to buttons.
	 * 
	 * @return new {@code Container} with buttons.
	 */
	private Container bottomButtonContainer() {
		Container buttons = new Container();
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
		goBack.addActionListener(e -> goBack());
		buttons.add(goBack);
		exitGame.addActionListener(e -> exitGame());
		buttons.add(exitGame);
		return buttons;
	}

	/**
	 * Makes and adds each card of the menu to {@code mainPanel}, into a
	 * {@code CardLayout}.
	 * 
	 * @see CardLayout
	 */
	private void initCards() {
		keyBindsPanel = makeKeyBindsCard();
		mainPanel.add(keyBindsPanel, KEYS);

		settingsPanel = makeSettingsCard();
		mainPanel.add(settingsPanel, SETTINGS);

		homePanel = makeHomeCard();
		mainPanel.add(homePanel, HOME);
	}

	/**
	 * Makes the 'settings' card of the menu. At the moment, it is empty and has no
	 * features. In the future, buttons, labels and other components will be added
	 * to a {@code JScrollPane}.
	 * <p>
	 * This card does not contain KeyBind settings; they are made by
	 * {@link makeKeyBindsCard()}.
	 * 
	 * @return a {@code JPanel} containing a {@code JScrollPane} with settings
	 *         components.
	 */
	private JPanel makeSettingsCard() {
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		pane.add(new JLabel("Nothing here yet..."), BorderLayout.NORTH);
		JScrollPane scroll = new JScrollPane(pane);
		scroll.setPreferredSize(this.getSize());
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		;

		JPanel panel = new JPanel();
		panel.add(scroll);
		return panel;
	}

	/**
	 * Makes the 'home' card of the menu.
	 * <p>
	 * Also assigns action listeners to buttons.
	 * 
	 * @return a JPanel that is added to the {@code CardLayout} of
	 *         {@code mainPanel}.
	 */
	private JPanel makeHomeCard() {
		// TODO make buttons justify to the center of panel, not the left.
		JPanel home = new JPanel();
		saveGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		keyBinds.setAlignmentX(Component.CENTER_ALIGNMENT);
		settings.setAlignmentX(Component.CENTER_ALIGNMENT);		
		
		home.setLayout(new BoxLayout(home, BoxLayout.PAGE_AXIS));
		home.add(Box.createRigidArea(new Dimension(0, 7)));
		saveGame.addActionListener(e -> saveGame());
		home.add(saveGame);
		home.add(Box.createRigidArea(new Dimension(0, 10)));
		keyBinds.addActionListener(e -> cards.show(mainPanel, KEYS));
		home.add(keyBinds);
		home.add(Box.createRigidArea(new Dimension(0, 10)));
		settings.addActionListener(e -> cards.show(mainPanel, SETTINGS));
		home.add(settings);
		home.add(Box.createRigidArea(new Dimension(0, 10)));
		return home;
	}

	/**
	 * Makes a {@code JPanel} with a {@code JScrollPane} of key bind selections.
	 * 
	 * @return a {@code JPanel} of key bind preference selection options.
	 */
	private JPanel makeKeyBindsCard() {
//		JScrollPane keyBinds = new JScrollPane();
//		keyBinds.setLayout(new ScrollPaneLayout());
//		keyBinds.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));

		JPanel keyBinds = new JPanel();
		keyBinds.setLayout(new BoxLayout(keyBinds, BoxLayout.Y_AXIS));

		Container cGoForward = new Container();
		cGoForward.setLayout(new BorderLayout());
		JLabel lblForward = new JLabel(GoForward.getDesc());
		cGoForward.add(lblForward, BorderLayout.WEST);
		dForward.setSelectedItem(actions.get(new GoForward()));
		cGoForward.add(dForward, BorderLayout.EAST);
		keyBinds.add(cGoForward);

		Container cGoBackward = new Container();
		cGoBackward.setLayout(new BorderLayout());
		JLabel lblBackward = new JLabel(GoBackward.getDesc());
		cGoBackward.add(lblBackward, BorderLayout.WEST);
		dBackward.setSelectedItem(actions.get(new GoBackward()));
		cGoBackward.add(dBackward, BorderLayout.EAST);
		keyBinds.add(cGoBackward);

		Container cGoLeft = new Container();
		cGoLeft.setLayout(new BorderLayout());
		JLabel lblLeft = new JLabel(GoLeft.getDesc());
		cGoLeft.add(lblLeft, BorderLayout.WEST);
		dLeft.setSelectedItem(actions.get(new GoLeft()));
		cGoLeft.add(dLeft, BorderLayout.EAST);
		keyBinds.add(cGoLeft);

		Container cGoRight = new Container();
		cGoRight.setLayout(new BorderLayout());
		JLabel lblRight = new JLabel(GoRight.getDesc());
		cGoRight.add(lblRight, BorderLayout.WEST);
		dRight.setSelectedItem(actions.get(new GoRight()));
		cGoRight.add(dRight, BorderLayout.EAST);
		keyBinds.add(cGoRight);

		Container cPanLeft = new Container();
		cPanLeft.setLayout(new BorderLayout());
		JLabel lblPanLeft = new JLabel(PanLeft.getDesc());
		cPanLeft.add(lblPanLeft, BorderLayout.WEST);
		dPanLeft.setSelectedItem(actions.get(new PanLeft()));
		// TODO dPanLeft not showing
		cPanLeft.add(dPanLeft, BorderLayout.EAST);
		keyBinds.add(cPanLeft);

		Container cPanRight = new Container();
		cPanRight.setLayout(new BorderLayout());
		JLabel lblPanRight = new JLabel(PanRight.getDesc());
		cPanRight.add(lblPanRight, BorderLayout.WEST);
		dPanRight.setSelectedItem(actions.get(new PanRight()));
		cPanRight.add(dPanRight, BorderLayout.EAST);
		keyBinds.add(cPanRight);

		Container cOpenMenu = new Container();
		cOpenMenu.setLayout(new BorderLayout());
		JLabel lblOpenMenu = new JLabel(OpenMenu.getDesc());
		cOpenMenu.add(lblOpenMenu, BorderLayout.WEST);
		dOpenMenu.setSelectedItem(actions.get(new OpenMenu()));
		cOpenMenu.add(dOpenMenu, BorderLayout.EAST);
		keyBinds.add(cOpenMenu);

		Container cSaveGame = new Container();
		cSaveGame.setLayout(new BorderLayout());
		JLabel lblSaveGame = new JLabel(SaveGame.getDesc());
		cSaveGame.add(lblSaveGame, BorderLayout.WEST);
		dSaveGame.setSelectedItem(actions.get(new SaveGame()));
		cSaveGame.add(dSaveGame, BorderLayout.EAST);
		keyBinds.add(cSaveGame);

		Container cExitGame = new Container();
		cExitGame.setLayout(new BorderLayout());
		JLabel lblExitGame = new JLabel(ExitGame.getDesc());
		cExitGame.add(lblExitGame, BorderLayout.WEST);
		dExitGame.setSelectedItem(actions.get(new ExitGame()));
		cExitGame.add(dPanLeft, BorderLayout.EAST);
		keyBinds.add(cExitGame);

		applyKeys.addActionListener(e -> saveKeyBinds());

		JScrollPane scroll = new JScrollPane(keyBinds);
		scroll.setPreferredSize(this.getSize());
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(scroll, BorderLayout.CENTER);
		panel.add(applyKeys, BorderLayout.SOUTH);

		return panel;
	}

	private void saveGame() {
		// TODO write save game method
	}

	private void exitGame() {
		// TODO write exit game method
	}

	/**
	 * Creates a {@code Menu} if it is not being displayed already. Otherwise, it
	 * calls {@link Menu#goBack} to either return to the 'home' card of the menu or
	 * to quit the menu and resume the game.
	 */
	public static void display() {
		if (!displaying) {
			menu = new Menu();
		} else {
			menu.goBack();
		}
	}

	/**
	 * Loads a {@code Map} of classes extending {@link KeyAction} that correspond to
	 * their {@link Keys} ENUM.
	 * 
	 * @return new {@code Map<KeyAction, Keys>}
	 */
	private Map<KeyAction, Keys> makeActionMap() {
		Map<KeyAction, Keys> map = new HashMap<>();
		KeyAction action;
		for (Keys key : Keys.values()) {
			if ((action = key.getAction()) != null) {
				map.put(action, key);
			}
		}
		return map;
	}

	/**
	 * Resumes the game and disposes of the JFrame.
	 */
	@Override
	public void dispose() {
		// TODO un-pause game
		super.dispose();
	}

	/**
	 * Action of the "Return" button of the menu. If the homePanel
	 * <strong>is</strong> visible, returns back to game. If homePane is
	 * <strong>not</strong> visible, calls {@code CardLayout} to show HOME card.
	 */
	private void goBack() {
		if (homePanel.isVisible()) {
			dispose();
		} else {
			cards.show(mainPanel, HOME);
		}
	}

	/**
	 * Sets actions to the selected {@link Keys} from each KeyBind
	 * {@code JComboBox}. Calls to
	 * {@link SwissCheese.engine.keyboard.KeyActionPreferenceIO#writeToFile()} to
	 * save the new preference to a file and to bind the keys.
	 */
	private void saveKeyBinds() {
		((Keys) dForward.getSelectedItem()).setAction(new GoForward());
		((Keys) dBackward.getSelectedItem()).setAction(new GoBackward());
		((Keys) dLeft.getSelectedItem()).setAction(new GoLeft());
		((Keys) dRight.getSelectedItem()).setAction(new GoRight());
		((Keys) dPanLeft.getSelectedItem()).setAction(new PanLeft());
		((Keys) dPanRight.getSelectedItem()).setAction(new PanRight());
		((Keys) dOpenMenu.getSelectedItem()).setAction(new OpenMenu());
		((Keys) dSaveGame.getSelectedItem()).setAction(new SaveGame());
		((Keys) dExitGame.getSelectedItem()).setAction(new ExitGame());
		KeyPreferenceIO.writeToFile(KeyActionPreference.makeFromKeys());
	}
}