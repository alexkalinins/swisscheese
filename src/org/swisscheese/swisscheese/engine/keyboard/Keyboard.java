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
package org.swisscheese.swisscheese.engine.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import org.swisscheese.swisscheese.annotations.Immutable;

/**
 * Keyboard listener class for game's ui
 * 
 * @author Alex Kalinins
 * @since 2018-12-1
 * @since v0.2
 * @version v1.0
 */
@Immutable
public class Keyboard implements KeyListener {
	private final Map<Integer, Keys> keyMap;

	/**
	 * Keyboard constructor.
	 */
	public Keyboard() {
		keyMap = loadKeyMap();
	}

	/**
	 * Loads {@code Keys} enums into a Map, where the key is their (integer)
	 * {@code KeyEvent} key-code.
	 * 
	 * @return map
	 */
	private Map<Integer, Keys> loadKeyMap() {
		Map<Integer, Keys> map = new HashMap<Integer, Keys>();

		map.put(KeyEvent.VK_A, Keys.A);
		map.put(KeyEvent.VK_B, Keys.B);
		map.put(KeyEvent.VK_C, Keys.C);
		map.put(KeyEvent.VK_D, Keys.D);
		map.put(KeyEvent.VK_E, Keys.E);
		map.put(KeyEvent.VK_F, Keys.F);
		map.put(KeyEvent.VK_G, Keys.G);
		map.put(KeyEvent.VK_H, Keys.H);
		map.put(KeyEvent.VK_I, Keys.I);
		map.put(KeyEvent.VK_J, Keys.J);
		map.put(KeyEvent.VK_K, Keys.K);
		map.put(KeyEvent.VK_L, Keys.L);
		map.put(KeyEvent.VK_M, Keys.M);
		map.put(KeyEvent.VK_N, Keys.N);
		map.put(KeyEvent.VK_O, Keys.O);
		map.put(KeyEvent.VK_P, Keys.P);
		map.put(KeyEvent.VK_Q, Keys.Q);
		map.put(KeyEvent.VK_R, Keys.R);
		map.put(KeyEvent.VK_S, Keys.S);
		map.put(KeyEvent.VK_T, Keys.T);
		map.put(KeyEvent.VK_U, Keys.U);
		map.put(KeyEvent.VK_V, Keys.V);
		map.put(KeyEvent.VK_W, Keys.W);
		map.put(KeyEvent.VK_X, Keys.X);
		map.put(KeyEvent.VK_Y, Keys.Y);
		map.put(KeyEvent.VK_Z, Keys.Z);

		map.put(KeyEvent.VK_1, Keys.N1);
		map.put(KeyEvent.VK_2, Keys.N2);
		map.put(KeyEvent.VK_3, Keys.N3);
		map.put(KeyEvent.VK_4, Keys.N4);
		map.put(KeyEvent.VK_5, Keys.N5);
		map.put(KeyEvent.VK_6, Keys.N6);
		map.put(KeyEvent.VK_7, Keys.N7);
		map.put(KeyEvent.VK_8, Keys.N8);
		map.put(KeyEvent.VK_9, Keys.N9);
		map.put(KeyEvent.VK_0, Keys.N0);

		map.put(KeyEvent.VK_UP, Keys.UP);
		map.put(KeyEvent.VK_DOWN, Keys.DOWN);
		map.put(KeyEvent.VK_LEFT, Keys.LEFT);
		map.put(KeyEvent.VK_RIGHT, Keys.RIGHT);

		map.put(KeyEvent.VK_ESCAPE, Keys.ESC);
		map.put(KeyEvent.VK_SHIFT, Keys.SHIFT);
		map.put(KeyEvent.VK_ENTER, Keys.ENTER);
		map.put(KeyEvent.VK_SPACE, Keys.SPACE);
		map.put(KeyEvent.VK_BACK_SPACE, Keys.BACK_SPACE);

		return map;
	}

	@Override
	public synchronized void keyPressed(KeyEvent key) {
		try {
			keyMap.get(key.getKeyCode()).doAction();
		} catch (NullPointerException e) {
			System.out.println("User pressed a non-bound key: " + key.getKeyChar());
		}

	}

	@Override
	public synchronized void keyReleased(KeyEvent key) {
		try {
			keyMap.get(key.getKeyCode()).stopAction();
		} catch (NullPointerException e) {
			System.out.println("User released a non-bound key: " + key.getKeyChar());
		}
	}

	@Override
	public void keyTyped(KeyEvent key) {
		return;
	}
}
