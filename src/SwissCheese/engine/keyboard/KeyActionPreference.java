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
package SwissCheese.engine.keyboard;

import SwissCheese.annotations.Immutable;
import SwissCheese.engine.keyboard.keyActions.KeyAction;

/**
 * Class for setting and binding the key binding preferences to the game. Action
 * preference must be added to parameters every single time a new key or action
 * is added to the game.
 * <p>
 * This object is serialized and deserialized by the {@code GSON} library.
 * 
 * @author Alex Kalinins
 * @since 2018-12-1
 * @since v0.2
 * @version v0.4
 */
@Immutable
public final class KeyActionPreference {
	private KeyAction aAction;
	private KeyAction bAction;
	private KeyAction cAction;
	private KeyAction dAction;
	private KeyAction eAction;
	private KeyAction fAction;
	private KeyAction gAction;
	private KeyAction hAction;
	private KeyAction iAction;
	private KeyAction jAction;
	private KeyAction kAction;
	private KeyAction lAction;
	private KeyAction mAction;
	private KeyAction nAction;
	private KeyAction oAction;
	private KeyAction pAction;
	private KeyAction qAction;
	private KeyAction rAction;
	private KeyAction sAction;
	private KeyAction tAction;
	private KeyAction uAction;
	private KeyAction vAction;
	private KeyAction wAction;
	private KeyAction xAction;
	private KeyAction yAction;
	private KeyAction zAction;

	private KeyAction upAction;
	private KeyAction downAction;
	private KeyAction leftAction;
	private KeyAction rightAction;
	private KeyAction escAction;
	private KeyAction shiftAction;
	private KeyAction enterAction;
	private KeyAction backSpaceAction;
	private KeyAction spaceAction;
	private KeyAction n1Action;
	private KeyAction n2Action;
	private KeyAction n3Action;
	private KeyAction n4Action;
	private KeyAction n5Action;
	private KeyAction n6Action;
	private KeyAction n7Action;
	private KeyAction n8Action;
	private KeyAction n9Action;
	private KeyAction n0Action;

	/**
	 * This method sets the {@link KeyAction} of their respective {@link Keys} enum.
	 * 
	 * @param aAction
	 * @param bAction
	 * @param cAction
	 * @param dAction
	 * @param eAction
	 * @param fAction
	 * @param gAction
	 * @param hAction
	 * @param iAction
	 * @param jAction
	 * @param kAction
	 * @param lAction
	 * @param mAction
	 * @param nAction
	 * @param oAction
	 * @param pAction
	 * @param qAction
	 * @param rAction
	 * @param sAction
	 * @param tAction
	 * @param uAction
	 * @param vAction
	 * @param wAction
	 * @param xAction
	 * @param yAction
	 * @param zAction
	 * @param upAction
	 * @param downAction
	 * @param leftAction
	 * @param rightAction
	 * @param escAction
	 * @param shiftAction
	 * @param enterAction
	 * @param backSpaceAction
	 * @param spaceAction
	 * @param n1Action
	 * @param n2Action
	 * @param n3Action
	 * @param n4Action
	 * @param n5Action
	 * @param n6Action
	 * @param n7Action
	 * @param n8Action
	 * @param n9Action
	 * @param n0Action
	 */
	public KeyActionPreference(KeyAction aAction, KeyAction bAction, KeyAction cAction, KeyAction dAction,
			KeyAction eAction, KeyAction fAction, KeyAction gAction, KeyAction hAction, KeyAction iAction,
			KeyAction jAction, KeyAction kAction, KeyAction lAction, KeyAction mAction, KeyAction nAction,
			KeyAction oAction, KeyAction pAction, KeyAction qAction, KeyAction rAction, KeyAction sAction,
			KeyAction tAction, KeyAction uAction, KeyAction vAction, KeyAction wAction, KeyAction xAction,
			KeyAction yAction, KeyAction zAction, KeyAction upAction, KeyAction downAction, KeyAction leftAction,
			KeyAction rightAction, KeyAction escAction, KeyAction shiftAction, KeyAction enterAction,
			KeyAction backSpaceAction, KeyAction spaceAction, KeyAction n1Action, KeyAction n2Action,
			KeyAction n3Action, KeyAction n4Action, KeyAction n5Action, KeyAction n6Action, KeyAction n7Action,
			KeyAction n8Action, KeyAction n9Action, KeyAction n0Action) {
		super();
		this.aAction = aAction;
		this.bAction = bAction;
		this.cAction = cAction;
		this.dAction = dAction;
		this.eAction = eAction;
		this.fAction = fAction;
		this.gAction = gAction;
		this.hAction = hAction;
		this.iAction = iAction;
		this.jAction = jAction;
		this.kAction = kAction;
		this.lAction = lAction;
		this.mAction = mAction;
		this.nAction = nAction;
		this.oAction = oAction;
		this.pAction = pAction;
		this.qAction = qAction;
		this.rAction = rAction;
		this.sAction = sAction;
		this.tAction = tAction;
		this.uAction = uAction;
		this.vAction = vAction;
		this.wAction = wAction;
		this.xAction = xAction;
		this.yAction = yAction;
		this.zAction = zAction;
		this.upAction = upAction;
		this.downAction = downAction;
		this.leftAction = leftAction;
		this.rightAction = rightAction;
		this.escAction = escAction;
		this.shiftAction = shiftAction;
		this.enterAction = enterAction;
		this.backSpaceAction = backSpaceAction;
		this.spaceAction = spaceAction;
		this.n1Action = n1Action;
		this.n2Action = n2Action;
		this.n3Action = n3Action;
		this.n4Action = n4Action;
		this.n5Action = n5Action;
		this.n6Action = n6Action;
		this.n7Action = n7Action;
		this.n8Action = n8Action;
		this.n9Action = n9Action;
		this.n0Action = n0Action;
	}

	/**
	 * No arguments constructor necessary for GSON serialization.
	 */
	public KeyActionPreference() {
		this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null);
	}

	/**
	 * Deprecated method for setting key actions.
	 * 
	 * @param wAction
	 * @param aAction
	 * @param sAction
	 * @param dAction
	 * @param upAction
	 * @param downAction
	 * @param leftAction
	 * @param rightAction
	 * @param escAction
	 * @param shiftAction
	 * @param n1Action
	 * @param n2Action
	 * @param n3Action
	 * @param n4Action
	 * @param n5Action
	 * @param n6Action
	 * @param n7Action
	 * @param n8Action
	 * @param n9Action
	 * @param n0Action
	 */
	@Deprecated
	public KeyActionPreference(KeyAction wAction, KeyAction aAction, KeyAction sAction, KeyAction dAction,
			KeyAction upAction, KeyAction downAction, KeyAction leftAction, KeyAction rightAction, KeyAction escAction,
			KeyAction shiftAction, KeyAction n1Action, KeyAction n2Action, KeyAction n3Action, KeyAction n4Action,
			KeyAction n5Action, KeyAction n6Action, KeyAction n7Action, KeyAction n8Action, KeyAction n9Action,
			KeyAction n0Action) {
		this(aAction, null, escAction, dAction, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, sAction, shiftAction, null, null, wAction, null, null, null, upAction, downAction,
				leftAction, rightAction, escAction, shiftAction, null, null, null, n1Action, n2Action, n3Action,
				n4Action, n5Action, n6Action, n7Action, n8Action, n9Action, n0Action);
	}

	/**
	 * Sets the action of each {@code Keys} enums to the specified
	 * {@code KeyAction}.
	 */
	public void bindPreferences() {
		Keys.A.setAction(aAction);
		Keys.B.setAction(bAction);
		Keys.C.setAction(cAction);
		Keys.D.setAction(dAction);
		Keys.E.setAction(eAction);
		Keys.F.setAction(fAction);
		Keys.G.setAction(gAction);
		Keys.H.setAction(hAction);
		Keys.I.setAction(iAction);
		Keys.J.setAction(jAction);
		Keys.K.setAction(kAction);
		Keys.L.setAction(lAction);
		Keys.M.setAction(mAction);
		Keys.N.setAction(nAction);
		Keys.O.setAction(oAction);
		Keys.P.setAction(pAction);
		Keys.Q.setAction(qAction);
		Keys.R.setAction(rAction);
		Keys.S.setAction(sAction);
		Keys.T.setAction(tAction);
		Keys.U.setAction(uAction);
		Keys.V.setAction(vAction);
		Keys.W.setAction(wAction);
		Keys.X.setAction(xAction);
		Keys.Y.setAction(yAction);
		Keys.Z.setAction(zAction);

		Keys.N0.setAction(n0Action);
		Keys.N1.setAction(n1Action);
		Keys.N2.setAction(n2Action);
		Keys.N3.setAction(n3Action);
		Keys.N4.setAction(n4Action);
		Keys.N5.setAction(n5Action);
		Keys.N6.setAction(n6Action);
		Keys.N7.setAction(n7Action);
		Keys.N8.setAction(n8Action);
		Keys.N9.setAction(n9Action);

		Keys.UP.setAction(upAction);
		Keys.DOWN.setAction(downAction);
		Keys.LEFT.setAction(leftAction);
		Keys.RIGHT.setAction(rightAction);

		Keys.SHIFT.setAction(shiftAction);
		Keys.SPACE.setAction(spaceAction);
		Keys.BACK_SPACE.setAction(backSpaceAction);
		Keys.ENTER.setAction(enterAction);
		Keys.ESC.setAction(escAction);
	}

	/**
	 * Public static factory that makes a new {@code KeyActionPreference} from
	 * {@link Keys} {@link KeyAction} selection.
	 * 
	 * @return new KeyActionPreference
	 */
	public static KeyActionPreference makeFromKeys() {
		return new KeyActionPreference(Keys.A.getAction(), Keys.B.getAction(), Keys.C.getAction(), Keys.D.getAction(),
				Keys.E.getAction(), Keys.F.getAction(), Keys.G.getAction(), Keys.H.getAction(), Keys.I.getAction(),
				Keys.J.getAction(), Keys.K.getAction(), Keys.L.getAction(), Keys.M.getAction(), Keys.N.getAction(),
				Keys.O.getAction(), Keys.P.getAction(), Keys.Q.getAction(), Keys.R.getAction(), Keys.S.getAction(),
				Keys.T.getAction(), Keys.U.getAction(), Keys.V.getAction(), Keys.W.getAction(), Keys.X.getAction(),
				Keys.Y.getAction(), Keys.Z.getAction(), Keys.UP.getAction(), Keys.DOWN.getAction(),
				Keys.LEFT.getAction(), Keys.RIGHT.getAction(), Keys.ESC.getAction(), Keys.SHIFT.getAction(),
				Keys.ENTER.getAction(), Keys.BACK_SPACE.getAction(), Keys.SPACE.getAction(), Keys.N1.getAction(),
				Keys.N2.getAction(), Keys.N3.getAction(), Keys.N4.getAction(), Keys.N5.getAction(), Keys.N6.getAction(),
				Keys.N7.getAction(), Keys.N8.getAction(), Keys.N9.getAction(), Keys.N0.getAction());
	}

	@Override
	public String toString() {
		return "KeyActionPreference [aAction=" + aAction + ", bAction=" + bAction + ", cAction=" + cAction
				+ ", dAction=" + dAction + ", eAction=" + eAction + ", fAction=" + fAction + ", gAction=" + gAction
				+ ", hAction=" + hAction + ", iAction=" + iAction + ", jAction=" + jAction + ", kAction=" + kAction
				+ ", lAction=" + lAction + ", mAction=" + mAction + ", nAction=" + nAction + ", oAction=" + oAction
				+ ", pAction=" + pAction + ", qAction=" + qAction + ", rAction=" + rAction + ", sAction=" + sAction
				+ ", tAction=" + tAction + ", uAction=" + uAction + ", vAction=" + vAction + ", wAction=" + wAction
				+ ", xAction=" + xAction + ", yAction=" + yAction + ", zAction=" + zAction + ", upAction=" + upAction
				+ ", downAction=" + downAction + ", leftAction=" + leftAction + ", rightAction=" + rightAction
				+ ", escAction=" + escAction + ", shiftAction=" + shiftAction + ", enterAction=" + enterAction
				+ ", backSpaceAction=" + backSpaceAction + ", spaceAction=" + spaceAction + ", n1Action=" + n1Action
				+ ", n2Action=" + n2Action + ", n3Action=" + n3Action + ", n4Action=" + n4Action + ", n5Action="
				+ n5Action + ", n6Action=" + n6Action + ", n7Action=" + n7Action + ", n8Action=" + n8Action
				+ ", n9Action=" + n9Action + ", n0Action=" + n0Action + "]";
	}
	
	

}
