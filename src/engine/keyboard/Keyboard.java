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
package engine.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import engine.keyboard.keyActions.TestAction;

/**
 * Keyboard listener class for game's ui
 * 
 * @author Alex Kalinins
 * @since 2018-12-1
 * @since v0.2
 * @version v0.1
 */
public class Keyboard implements KeyListener {

	private static Map<Integer, Keys> keyMap;

	private Keys A = Keys.A;
	private Keys B = Keys.B;
	private Keys C = Keys.C;
	private Keys D = Keys.D;
	private Keys E = Keys.E;
	private Keys F = Keys.F;
	private Keys G = Keys.G;
	private Keys H = Keys.H;
	private Keys I = Keys.I;
	private Keys J = Keys.J;
	private Keys K = Keys.K;
	private Keys L = Keys.L;
	private Keys M = Keys.M;
	private Keys N = Keys.N;
	private Keys O = Keys.O;
	private Keys P = Keys.P;
	private Keys Q = Keys.Q;
	private Keys R = Keys.R;
	private Keys S = Keys.S;
	private Keys T = Keys.T;
	private Keys U = Keys.U;
	private Keys V = Keys.V;
	private Keys W = Keys.W;
	private Keys X = Keys.X;
	private Keys Y = Keys.Y;
	private Keys Z = Keys.Z;

	private Keys N1 = Keys.N1;
	private Keys N2 = Keys.N2;
	private Keys N3 = Keys.N3;
	private Keys N4 = Keys.N4;
	private Keys N5 = Keys.N5;
	private Keys N6 = Keys.N6;
	private Keys N7 = Keys.N7;
	private Keys N8 = Keys.N8;
	private Keys N9 = Keys.N9;
	private Keys N0 = Keys.N0;

	private Keys UP = Keys.UP;
	private Keys DOWN = Keys.DOWN;
	private Keys LEFT = Keys.LEFT;
	private Keys RIGHT = Keys.RIGHT;
	private Keys ESC = Keys.ESC;
	private Keys SHIFT = Keys.SHIFT;
	private Keys ENTER = Keys.ENTER;
	private Keys SPACE = Keys.SPACE;
	private Keys BACK_SPACE = Keys.BACK_SPACE;

	public Keyboard() {

		loadKeyMap();
	}

	private void loadKeyMap() {

		keyMap = new HashMap<Integer, Keys>();

		Keys.A.setAction(new TestAction());

//		keyMap.put(KeyEvent.VK_A, A);
//		keyMap.put(KeyEvent.VK_B, B);
//		keyMap.put(KeyEvent.VK_C, C);
//		keyMap.put(KeyEvent.VK_D, D);
//		keyMap.put(KeyEvent.VK_E, E);
//		keyMap.put(KeyEvent.VK_F, F);
//		keyMap.put(KeyEvent.VK_G, G);
//		keyMap.put(KeyEvent.VK_H, H);
//		keyMap.put(KeyEvent.VK_I, I);
//		keyMap.put(KeyEvent.VK_J, J);
//		keyMap.put(KeyEvent.VK_K, K);
//		keyMap.put(KeyEvent.VK_L, L);
//		keyMap.put(KeyEvent.VK_M, M);
//		keyMap.put(KeyEvent.VK_N, N);
//		keyMap.put(KeyEvent.VK_O, O);
//		keyMap.put(KeyEvent.VK_P, P);
//		keyMap.put(KeyEvent.VK_Q, Q);
//		keyMap.put(KeyEvent.VK_R, R);
//		keyMap.put(KeyEvent.VK_S, S);
//		keyMap.put(KeyEvent.VK_T, T);
//		keyMap.put(KeyEvent.VK_U, U);
//		keyMap.put(KeyEvent.VK_V, V);
//		keyMap.put(KeyEvent.VK_W, W);
//		keyMap.put(KeyEvent.VK_X, X);
//		keyMap.put(KeyEvent.VK_Y, Y);
//		keyMap.put(KeyEvent.VK_Z, Z);
//
//		keyMap.put(KeyEvent.VK_1, N1);
//		keyMap.put(KeyEvent.VK_2, N2);
//		keyMap.put(KeyEvent.VK_3, N3);
//		keyMap.put(KeyEvent.VK_4, N4);
//		keyMap.put(KeyEvent.VK_5, N5);
//		keyMap.put(KeyEvent.VK_6, N6);
//		keyMap.put(KeyEvent.VK_7, N7);
//		keyMap.put(KeyEvent.VK_8, N8);
//		keyMap.put(KeyEvent.VK_9, N9);
//		keyMap.put(KeyEvent.VK_0, N0);
//
//		keyMap.put(KeyEvent.VK_UP, UP);
//		keyMap.put(KeyEvent.VK_DOWN, DOWN);
//		keyMap.put(KeyEvent.VK_LEFT, LEFT);
//		keyMap.put(KeyEvent.VK_RIGHT, RIGHT);
//
//		keyMap.put(KeyEvent.VK_ESCAPE, ESC);
//		keyMap.put(KeyEvent.VK_SHIFT, SHIFT);
//		keyMap.put(KeyEvent.VK_ENTER, ENTER);
//		keyMap.put(KeyEvent.VK_SPACE, SPACE);
//		keyMap.put(KeyEvent.VK_BACK_SPACE, BACK_SPACE);

		keyMap.put(KeyEvent.VK_A, Keys.A);
		keyMap.put(KeyEvent.VK_B, Keys.B);
		keyMap.put(KeyEvent.VK_C, Keys.C);
		keyMap.put(KeyEvent.VK_D, Keys.D);
		keyMap.put(KeyEvent.VK_E, Keys.E);
		keyMap.put(KeyEvent.VK_F, Keys.F);
		keyMap.put(KeyEvent.VK_G, Keys.G);
		keyMap.put(KeyEvent.VK_H, Keys.H);
		keyMap.put(KeyEvent.VK_I, Keys.I);
		keyMap.put(KeyEvent.VK_J, Keys.J);
		keyMap.put(KeyEvent.VK_K, Keys.K);
		keyMap.put(KeyEvent.VK_L, Keys.L);
		keyMap.put(KeyEvent.VK_M, Keys.M);
		keyMap.put(KeyEvent.VK_N, Keys.N);
		keyMap.put(KeyEvent.VK_O, Keys.O);
		keyMap.put(KeyEvent.VK_P, Keys.P);
		keyMap.put(KeyEvent.VK_Q, Keys.Q);
		keyMap.put(KeyEvent.VK_R, Keys.R);
		keyMap.put(KeyEvent.VK_S, Keys.S);
		keyMap.put(KeyEvent.VK_T, Keys.T);
		keyMap.put(KeyEvent.VK_U, Keys.U);
		keyMap.put(KeyEvent.VK_V, Keys.V);
		keyMap.put(KeyEvent.VK_W, Keys.W);
		keyMap.put(KeyEvent.VK_X, Keys.X);
		keyMap.put(KeyEvent.VK_Y, Keys.Y);
		keyMap.put(KeyEvent.VK_Z, Keys.Z);

		keyMap.put(KeyEvent.VK_1, Keys.N1);
		keyMap.put(KeyEvent.VK_2, Keys.N2);
		keyMap.put(KeyEvent.VK_3, Keys.N3);
		keyMap.put(KeyEvent.VK_4, Keys.N4);
		keyMap.put(KeyEvent.VK_5, Keys.N5);
		keyMap.put(KeyEvent.VK_6, Keys.N6);
		keyMap.put(KeyEvent.VK_7, Keys.N7);
		keyMap.put(KeyEvent.VK_8, Keys.N8);
		keyMap.put(KeyEvent.VK_9, Keys.N9);
		keyMap.put(KeyEvent.VK_0, Keys.N0);

		keyMap.put(KeyEvent.VK_UP, Keys.UP);
		keyMap.put(KeyEvent.VK_DOWN, Keys.DOWN);
		keyMap.put(KeyEvent.VK_LEFT, Keys.LEFT);
		keyMap.put(KeyEvent.VK_RIGHT, Keys.RIGHT);

		keyMap.put(KeyEvent.VK_ESCAPE, Keys.ESC);
		keyMap.put(KeyEvent.VK_SHIFT, Keys.SHIFT);
		keyMap.put(KeyEvent.VK_ENTER, Keys.ENTER);
		keyMap.put(KeyEvent.VK_SPACE, Keys.SPACE);
		keyMap.put(KeyEvent.VK_BACK_SPACE, Keys.BACK_SPACE);

	}

	@Override
	public void keyPressed(KeyEvent key) {
		try {
			keyMap.get(key.getKeyCode()).doAction();
		} catch (NoSuchElementException e) {
			// there may be no keys bound to the keycode.
			System.out.println("User pressed a non-bound key");
		}

	}

	@Override
	public void keyReleased(KeyEvent key) {
		try {
			keyMap.get(key.getKeyCode()).stopAction();
		} catch (NoSuchElementException e) {
			System.out.println("User released a non-bound key");
		}
	}

	@Override
	public void keyTyped(KeyEvent key) {
		try {
			keyMap.get(key.getKeyCode()).singleAction();
		} catch (NoSuchElementException e) {
			System.out.println("User typed a non-bound key");
		}
	}

}
