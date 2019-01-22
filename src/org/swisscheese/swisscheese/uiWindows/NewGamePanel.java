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

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.swisscheese.swisscheese.map.DifficultyLevel;


/**
 * A panel for selecting variables for a new instance of game.
 * 
 * @author Alex Kalinins
 * @since 2018-12-28
 * @since v0.5
 * @version v1.0
 */
@SuppressWarnings("serial")
final class NewGamePanel extends AbstractMakeGamePanel {
	private JTextField name;
	private JComboBox<DifficultyLevel> diff;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.swisscheese.swisscheese.uiWindows.AbstractMakeGamePanel#initComponents()
	 */
	@Override
	protected void initComponents() {
		JLabel subtitle = makeBoldLabel(new JLabel("New Game"));
		subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(subtitle, cst);
		cst.gridy++;
		cst.gridwidth = 2;
		add(new JSeparator(SwingConstants.HORIZONTAL),cst);
		cst.gridy++;
		cst.gridwidth = 1;
		add(new JLabel("Save Name: "), cst);
		cst.gridx=1;
		name = new JTextField();
		add(name,cst);
		cst.gridy++;
		cst.gridx = 0;

		add(new JLabel("Difficulty:"),cst);
		cst.gridx = 1;
		diff = new JComboBox<>(DifficultyLevel.values());
		diff.setSelectedItem(DifficultyLevel.NORMAL);
		add(diff,cst);
	}

	/**
	 * Name getter.
	 * 
	 * @return the String input from {@code name} {@code JLabel}.
	 */
	final String getNameField() {
		return name.getText();
	}

	/**
	 * {@code DifficultyLevel} enum getter.
	 * 
	 * @return the selected {@link DifficultyLevel} enum from the
	 *         {@code JComboBox diff}. If the user did not select any,
	 *         {@code DifficultyLevel.NORMAL} will be returned.
	 */
	final DifficultyLevel getDifficultyField() {
		return (DifficultyLevel) diff.getSelectedItem();
	}

}
