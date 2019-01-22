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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import org.swisscheese.swisscheese.texturePacks.TexturePack;
import org.swisscheese.swisscheese.texturePacks.TexturePackList;

/**
 * A JPanel for selecting the {@link TexturePack} that is used in the game.
 * 
 * @author Alex Kalinins
 * @since 2018-12-27
 * @since v0.5
 * @version v1.0
 */
class TexturePackPanel extends JPanel {
	private static final long serialVersionUID = -3441876791942185644L;
	private final JButton applyChanges = new JButton("Apply Changes");
	@SuppressWarnings("rawtypes")
	private final JList selection;

	TexturePackPanel(boolean... wApply) {
		setLayout(new BorderLayout());
		selection = makeSelectionList();
		JPanel panel = new JPanel();
		GridBagConstraints cst = new GridBagConstraints();
		cst.gridx = 0;
		cst.gridy = 0;
		cst.gridwidth = 2;
		cst.gridwidth = 1;
		cst.insets = new Insets(5, 5, 5, 5);
		cst.fill = GridBagConstraints.BOTH;
		panel.setLayout(new GridBagLayout());
		panel.add(new JLabel("Select a Texture-Pack:"), cst);
		cst.gridy++;
		panel.add(selection, cst);

		add(panel, BorderLayout.NORTH);
		if (wApply.length == 0 || wApply[0]) {
			add(applyChanges, BorderLayout.SOUTH);
		}
	}

	public void addApplyChangesActionListner(ActionListener l) {
		applyChanges.addActionListener(l);
	}

	@SuppressWarnings("rawtypes")
	private JList makeSelectionList() {
		Vector<TexturePack> vector = new Vector<>(TexturePackList.LIST.getList());
		JList list = new JList<>(vector);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		return list;
	}

	public TexturePack getSelectedTexturePack() {
		return (TexturePack) selection.getSelectedValue();
	}

}
