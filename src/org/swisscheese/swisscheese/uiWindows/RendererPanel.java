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

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import org.swisscheese.swisscheese.engine.details.UseRenderer;
import org.swisscheese.swisscheese.engine.display.Window;
import org.swisscheese.swisscheese.engine.rendering.ChunkRendererDispatcher;
import org.swisscheese.swisscheese.engine.rendering.MultithreadedRendererDispatcher;
import org.swisscheese.swisscheese.engine.rendering.Renderer;
import org.swisscheese.swisscheese.engine.rendering.RendererType;
import org.swisscheese.swisscheese.engine.rendering.SingleThreadedRenderer;

/**
 * A panel that is use for displaying rendering engine setting inside a menu.
 * 
 * @author Alex Kalinins
 * @since 2019-01-18
 * @since v0.5
 * @version v1.0
 */
class RendererPanel extends JPanel {
	private static final long serialVersionUID = 8747797986392977898L;
	private final JComboBox<RendererType> type = new JComboBox<>(RendererType.values());
	private final JSlider threadSlider = new JSlider(SwingConstants.HORIZONTAL, 1, 64, 1);
	private final GridBagConstraints cst = new GridBagConstraints();

	/**
	 * Creates a new {@link RendererPanel} that is used in the start menu.
	 */
	RendererPanel(UseRenderer useRenderer) {
		type.setSelectedItem(useRenderer.type);
		threadSlider.setMajorTickSpacing(2);
		threadSlider.setValue(useRenderer.nThreads);
		type.addActionListener(e -> selectedAction());

		setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1));
		setLayout(new GridBagLayout());

		cst.gridx = 0;
		cst.gridy = 0;
		cst.fill = GridBagConstraints.BOTH;
		cst.insets = new Insets(5, 5, 5, 5);
		cst.gridheight = 1;
		cst.gridwidth = 2;
		add(new JLabel("Rendering Settings"), cst);
		cst.gridy = 1;
		cst.gridwidth = 1;
		add(new JLabel("Engine Type: "), cst);
		cst.gridx = 1;
		add(type, cst);
		cst.gridx = 0;
		cst.gridy = 2;
		add(new JLabel("Threads Used: "), cst);
		cst.gridwidth = 2;
		cst.gridy = 3;
		add(threadSlider, cst);

	}

	/**
	 * Constructor to display the values of the existing renderer. Used when
	 * modifying {@link Window} {@link Renderer} during runtime.
	 * 
	 * @param renderer existing renderer.
	 */
	RendererPanel(Renderer renderer) {
		// nThread doesnt matter in the overloading.
		this(new UseRenderer(RendererType.fromRenderer(renderer), 1));

		if (renderer.isMultithreaded()) {
			threadSlider.setValue(((MultithreadedRendererDispatcher) renderer).getNThreads());
		} else {
			threadSlider.setValue(1);
			threadSlider.setEnabled(false);
		}

		if (renderer instanceof SingleThreadedRenderer) {
			type.setSelectedItem(RendererType.SINGLE_THREAD);
			cst.gridy = 4;
			cst.gridwidth = 2;
			String time = String.format("Avg. Time: %.3f sec.",
					((SingleThreadedRenderer) renderer).getAverageRenderingTime());
			add(new JLabel(time), cst);
		} else if (renderer instanceof ChunkRendererDispatcher) {
			type.setSelectedItem(RendererType.CHUNK);
		} else {
			type.setSelectedItem(RendererType.STRIP);
		}

	}

	/**
	 * Creates and returns a {@link UseRenderer} to be passed on to {@link Window}
	 * to create a new renderer.
	 * 
	 * @return new {@link UseRenderer}
	 */
	public UseRenderer getUseRenderer() {
		if (type.getSelectedItem() == RendererType.CHUNK
				&& (threadSlider.getValue() != 1 && threadSlider.getValue() % 2 != 0)) {
			JOptionPane.showMessageDialog(this, "ChunkRenderer must use even number of threads (or one)",
					"Select Renderer", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return new UseRenderer((RendererType) type.getSelectedItem(), threadSlider.getValue());
	}

	/**
	 * Done when something is selected on the type {@link JComboBox}. If the
	 * selected is single thread, the {@link JSlider} will have a value of 1, and
	 * will be disabled. Otherwise, the slider will be enabled, and the default
	 * value is set to 4.
	 */
	private void selectedAction() {
		RendererType rtype = (RendererType) type.getSelectedItem();
		switch (rtype) {
		case SINGLE_THREAD:
			threadSlider.setValue(1);
			threadSlider.setEnabled(false);
		default:
			threadSlider.setValue(4);
			threadSlider.setEnabled(true);
		}
	}
}
