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

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import SwissCheese.annotations.Immutable;

/**
 * This class is the in-game menu that the user sees when they open the menu in
 * the game.
 * 
 * @author Alex Kalinins
 * @since 2018-12-23
 * @since v0.5
 * @version v0.2
 */
@Immutable
public final class Menu extends JFrame {
	private static final long serialVersionUID = 3038952637134428918L;
	private final CardLayout cards;
	private final JPanel mainPanel;

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
	
	private static boolean displaying = false;
	private static Menu menu;

	/**
	 * Private Constructor. The only way to display the {@code Menu} is through
	 * {@link Menu#display()}.
	 */
	private Menu() {
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
	 * <p>
	 * The keyBindsPanel is a {@link KeyBindsPanel}.
	 * 
	 * @see CardLayout
	 * @see SwissCheese.uiWindows.KeyBindsPanel
	 */
	private void initCards() {
		keyBindsPanel = new KeyBindsPanel(this);
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
}