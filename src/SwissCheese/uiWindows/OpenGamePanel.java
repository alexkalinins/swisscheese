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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import SwissCheese.gameSaving.GameSave;
import SwissCheese.gameSaving.GameSaveManager;

/**
 * A panel for selecting a {@code GameSave} from the {@code GameSaveList} to
 * allow the user to select a saved game file to resume a previously saved game.
 * 
 * @author Alex Kalinins
 * @since 2018-12-28
 * @since v0.5
 * @version v1.0
 * 
 * @see SwissCheese.gameSaving.GameSaveList
 */
@SuppressWarnings("serial")
final class OpenGamePanel extends AbstractMakeGamePanel {
	@SuppressWarnings("rawtypes")
	private DefaultListModel model;
	@SuppressWarnings("rawtypes")
	private JList gameList;

	private JButton delete;

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void initComponents() {
		makeSpace();
		JLabel subtitle = makeBoldLabel(new JLabel("Start Game!"));
		add(subtitle);
		makeSpace();
		model = loadModel(new DefaultListModel());
		gameList = new JList(model);
		gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(gameList);
		makeSpace();
		delete = new JButton("Delete");
		delete.addActionListener(e -> deleteSelected());
		add(delete);
	}

	/**
	 * Deletes the selected {@code GameSave} from {@link GameSaveManager}.
	 */
	private void deleteSelected() {
		int index = gameList.getSelectedIndex();
		GameSaveManager.getInstance().deleteGame(index);
		updateJList();

	}

	/**
	 * @return the index selected by user from {@code gameList}.
	 */
	int getGameSaveIndex() {
		return gameList.getSelectedIndex();
	}

	/**
	 * Updates the {@code gameList} with the model from {@code loadModel} method.
	 */
	@SuppressWarnings("unchecked")
	private void updateJList() {
		model = loadModel(model);
		gameList.setModel(model);
	}

	/**
	 * Re-loads {@code model} with {@link GameSave} objects from
	 * {@link GameSaveManager#MANAGER}.
	 * 
	 * @param model model being reloaded
	 * @return re-loaded {@code model}.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private DefaultListModel loadModel(DefaultListModel model) {
		model.clear();
		for (GameSave save : GameSaveManager.getInstance().getList()) {
			model.addElement(save);
		}
		return model;
	}

}
