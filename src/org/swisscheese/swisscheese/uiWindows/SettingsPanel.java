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
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;

import org.swisscheese.swisscheese.annotations.Immutable;
import org.swisscheese.swisscheese.engine.display.WindowSize;
import org.swisscheese.swisscheese.settings.GameSettings;

/**
 * This class is the JPanel for the settings selection by the user. In this
 * panel, the user can select the settings they wish to change. When they press
 * a button to save the changes, the changes are collected and serialized to
 * disk.
 * <p>
 * The {@code SettingsPanel} class was created in order to reduce the amount of
 * times the entire settings JPanel needs to be written.
 * 
 * @author Alex Kalinins
 * @since 2018-12-26
 * @since v0.5
 * @version v1.0
 *
 */
@Immutable
class SettingsPanel extends JPanel {
	private static final long serialVersionUID = -7719234122723321628L;
	private final Dimension size;
	private JButton keyBinds = null;
	private final boolean withKeyBinds;
	private final Dimension space = new Dimension(0, 10);
	private final JScrollPane scrollPane;
	private final JComboBox<WindowSize> sizeSelect = new JComboBox<>(WindowSize.values());
	private final JCheckBox fitToScreen = new JCheckBox("Fit to Screen");
	private final GameSettings settings;
	private final JButton applyChanges = new JButton("Apply Changes");
	private final JSlider fovSlider = new JSlider(JSlider.HORIZONTAL, 5, 15, 9);

	/**
	 * Constructor.
	 * 
	 * @param parent       parent component of the JPanel.
	 * @param withKeyBinds true if the settings panel contains a button to go to a
	 *                     KeyBinds panel.
	 * @param settings     existing {@link GameSettings} object for default
	 *                     selections.
	 */
	SettingsPanel(Component parent, boolean withKeyBinds, GameSettings settings) {
		size = parent.getSize();
		this.withKeyBinds = withKeyBinds;
		this.settings = settings;

		setLayout(new BorderLayout());
		scrollPane = makeScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		add(applyChanges, BorderLayout.SOUTH);

	}

	/**
	 * Adds an {@link ActionListener} to the {@code keyBinds} button.
	 * <p>
	 * Does nothing if {@code withKeyBinds} boolean is set to false.
	 * 
	 * @param listener {@code ActionListener} for {@code keyBinds} button.
	 */
	public void addKeyBindActionListener(ActionListener listener) {
		if (withKeyBinds) {
			keyBinds.addActionListener(listener);
		}
	}

	/**
	 * Adds an {@link ActionListener} to the {@code applyChanges} button.
	 * 
	 * @param listner {@code ActionListener} for {@code applyChanges} button.
	 */
	public void addApplyActionListener(ActionListener listner) {
		applyChanges.addActionListener(listner);
	}

	/**
	 * Makes a {@code JScrollPane} with components that is added to the
	 * {@code SettingsPanel}
	 * 
	 * @return a new {@code JScrollPane} with settings selection components.
	 */
	private JScrollPane makeScrollPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(Box.createRigidArea(new Dimension(0, 7)));

		if (withKeyBinds) {
			keyBinds = new JButton("Key-Binding");
			panel.add(keyBinds);
		}

		panel.add(new JLabel("Game Window Size:"));
		panel.add(Box.createRigidArea(space));
		sizeSelect.setSelectedItem(settings.getWindowSize());
		panel.add(sizeSelect);
		panel.add(Box.createRigidArea(space));
		fitToScreen.setSelected(settings.isFitToScreen());
		panel.add(fitToScreen);
		panel.add(Box.createRigidArea(space));
		panel.add(new JLabel("FOV:"));
		panel.add(Box.createRigidArea(space));

		fovSlider.setMajorTickSpacing(1);
		panel.add(fovSlider);

		JScrollPane pane = new JScrollPane(panel);
		pane.setPreferredSize(size);
		pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return pane;
	}

	public final WindowSize getSizeSelect() {
		return (WindowSize) sizeSelect.getSelectedItem();
	}

	public final boolean getFitToScreen() {
		return fitToScreen.isSelected();
	}

	/**
	 * Returns an FOV decimal that the user has selected between -0.5 and -1.5
	 * 
	 * @return FOV decimal.
	 */
	public final float getFovSelection() {
		return (float) fovSlider.getValue() / -10f;
	}

}