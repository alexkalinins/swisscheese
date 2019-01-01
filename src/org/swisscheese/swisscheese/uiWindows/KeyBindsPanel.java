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
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.swisscheese.swisscheese.engine.keyboard.KeyActionPreference;
import org.swisscheese.swisscheese.engine.keyboard.KeyPreferenceIO;
import org.swisscheese.swisscheese.engine.keyboard.Keys;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.ExitGame;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.GoBackward;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.GoForward;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.GoLeft;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.GoRight;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.KeyAction;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.OpenMenu;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.PanLeft;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.PanRight;
import org.swisscheese.swisscheese.engine.keyboard.keyActions.SaveGame;

/**
 * This class generates a {@code JPanel} that is used by other {@code JFrame}
 * windows to allow the user to change the key bindings/assignment in the game.
 * This class will also be able to poll and apply changes made by the user in
 * the keyBinds {@code JPanel}.
 * <p>
 * In this using this panel, the user can select the key to use (from the
 * {@link Keys} {@code enums}) to perform a {@link KeyAction} action.
 * <p>
 * {@code KeyBindsPanel} was created so that a menu in which a user changed
 * KeyBinds did not have to re-implement the panel. * 
 * @author Alex Kalinins
 * @since 2018-12-26
 * @since v0.5
 * @version v1.0a
 * 
 * @see org.swisscheese.swisscheese.engine.keyboard.keyActions.KeyAction
 * @see org.swisscheese.swisscheese.engine.keyboard.Keys
 */
final class KeyBindsPanel extends JPanel {
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
	private final Map<KeyAction, Keys> map;
	private final Dimension parentSize;
	private final JScrollPane scrollPane;

	private static final long serialVersionUID = -8923512910358561465L;

	/**
	 * Constructor for {@code KeyBindsPanel}.
	 * 
	 * @param parent the parent component of {@code KeyBindsPanel}.
	 */
	public KeyBindsPanel(Component parent) {
		super();
		map = makeActionMap();
		parentSize = parent.getSize();
		scrollPane = makeScrollPane();
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		applyKeys.addActionListener(e -> pollChanges());
		add(applyKeys, BorderLayout.SOUTH);
	}

	/**
	 * Private void for collecting and saving all changes made by the user.
	 */
	private void pollChanges() {
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

	/**
	 * Creates a {@code JScrollPane} that is added to the panel. Inside of the
	 * ScrollPane is a {@code JPanel} containing {@code Container} objects of
	 * JLabels for {@link KeyAction} descriptions and a JComboBoxes to select the
	 * appropriate {@link Keys} {@code enums} for the action.
	 * 
	 * @return a new JScrollPane with components
	 */
	private JScrollPane makeScrollPane() {
		JPanel keyBinds = new JPanel();
		keyBinds.setLayout(new BoxLayout(keyBinds, BoxLayout.Y_AXIS));

		Container cGoForward = new Container();
		cGoForward.setLayout(new BorderLayout());
		JLabel lblForward = new JLabel(GoForward.getDesc());
		cGoForward.add(lblForward, BorderLayout.WEST);
		dForward.setSelectedItem(map.get(new GoForward()));
		cGoForward.add(dForward, BorderLayout.EAST);
		keyBinds.add(cGoForward);

		Container cGoBackward = new Container();
		cGoBackward.setLayout(new BorderLayout());
		JLabel lblBackward = new JLabel(GoBackward.getDesc());
		cGoBackward.add(lblBackward, BorderLayout.WEST);
		dBackward.setSelectedItem(map.get(new GoBackward()));
		cGoBackward.add(dBackward, BorderLayout.EAST);
		keyBinds.add(cGoBackward);

		Container cGoLeft = new Container();
		cGoLeft.setLayout(new BorderLayout());
		JLabel lblLeft = new JLabel(GoLeft.getDesc());
		cGoLeft.add(lblLeft, BorderLayout.WEST);
		dLeft.setSelectedItem(map.get(new GoLeft()));
		cGoLeft.add(dLeft, BorderLayout.EAST);
		keyBinds.add(cGoLeft);

		Container cGoRight = new Container();
		cGoRight.setLayout(new BorderLayout());
		JLabel lblRight = new JLabel(GoRight.getDesc());
		cGoRight.add(lblRight, BorderLayout.WEST);
		dRight.setSelectedItem(map.get(new GoRight()));
		cGoRight.add(dRight, BorderLayout.EAST);
		keyBinds.add(cGoRight);

		Container cPanLeft = new Container();
		cPanLeft.setLayout(new BorderLayout());
		JLabel lblPanLeft = new JLabel(PanLeft.getDesc());
		cPanLeft.add(lblPanLeft, BorderLayout.WEST);
		dPanLeft.setSelectedItem(map.get(new PanLeft()));
		cPanLeft.add(dPanLeft, BorderLayout.EAST);
		keyBinds.add(cPanLeft);

		Container cPanRight = new Container();
		cPanRight.setLayout(new BorderLayout());
		JLabel lblPanRight = new JLabel(PanRight.getDesc());
		cPanRight.add(lblPanRight, BorderLayout.WEST);
		dPanRight.setSelectedItem(map.get(new PanRight()));
		cPanRight.add(dPanRight, BorderLayout.EAST);
		keyBinds.add(cPanRight);

		Container cOpenMenu = new Container();
		cOpenMenu.setLayout(new BorderLayout());
		JLabel lblOpenMenu = new JLabel(OpenMenu.getDesc());
		cOpenMenu.add(lblOpenMenu, BorderLayout.WEST);
		dOpenMenu.setSelectedItem(map.get(new OpenMenu()));
		cOpenMenu.add(dOpenMenu, BorderLayout.EAST);
		keyBinds.add(cOpenMenu);

		Container cSaveGame = new Container();
		cSaveGame.setLayout(new BorderLayout());
		JLabel lblSaveGame = new JLabel(SaveGame.getDesc());
		cSaveGame.add(lblSaveGame, BorderLayout.WEST);
		dSaveGame.setSelectedItem(map.get(new SaveGame()));
		cSaveGame.add(dSaveGame, BorderLayout.EAST);
		keyBinds.add(cSaveGame);

		Container cExitGame = new Container();
		cExitGame.setLayout(new BorderLayout());
		JLabel lblExitGame = new JLabel(ExitGame.getDesc());
		cExitGame.add(lblExitGame, BorderLayout.WEST);
		dExitGame.setSelectedItem(map.get(new ExitGame()));
		cExitGame.add(dExitGame, BorderLayout.EAST);
		keyBinds.add(cExitGame);

		JScrollPane scroll = new JScrollPane(keyBinds);
		scroll.setPreferredSize(parentSize);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return scroll;
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
}
