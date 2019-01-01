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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.swisscheese.swisscheese.game.GameFromSettings;

/**
 * An abstract class for a panel that is used to start a new instance of
 * {@link GameFromSettings}.
 * 
 * @author Alex Kalinins
 * @since 2018-12-28
 * @since v0.5
 * @version v1.0
 */
public abstract class AbstractMakeGamePanel extends JPanel {
	private static final long serialVersionUID = 8830656830281914905L;
	protected JButton start = new JButton("Start Game!");

	/**
	 * Constructor for {@code AbstractMakeGamePanel}.
	 */
	protected AbstractMakeGamePanel() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

		initComponents();
		makeSpace();
		add(start);
	}

	/**
	 * Creates all components above {@code start} JButton. The layout manager of
	 * this panel is {@code BoxLayout}. A space between components created by
	 * {@code initComponents()} and the {@code start} button is already provided.
	 */
	protected abstract void initComponents();

	/**
	 * Makes a separator between Components in a {@code BoxLayout} by creating a
	 * {@code RigidArea}.
	 */
	protected void makeSpace() {
		Dimension box = new Dimension(0, 7);
		add(Box.createRigidArea(box));
		add(Box.createRigidArea(new Dimension(2, 0)));
	}

	/**
	 * Adds an action listener to the JButton {@code start}.
	 * <p>
	 * When pressed, this button should call for the parent Frame to dispose and for
	 * the game to start.
	 * 
	 * @param l ActinLisner to be added to the button.
	 */
	public void addStartActionListner(ActionListener l) {
		start.addActionListener(l);
	}
	
	/**
	 * Makes the inputed {@code label} text to be displayed with bold font.
	 * 
	 * @param label not bold label.
	 * @return bold font applied to {@code label}.
	 */
	protected JLabel makeBoldLabel(JLabel label) {
		Font f = label.getFont();
		label.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
		return label;
	}

}
