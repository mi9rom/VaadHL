/*
 * Copyright 2015 Mirosław Romaniuk (mi9rom@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.vaadHL.window.base;

import java.io.Serializable;

import com.vaadHL.IAppContext;
import com.vaadHL.utl.state.VHLFilterState;
import com.vaadHL.utl.state.VHLSortState;
import com.vaadHL.utl.state.VHLState;
import com.vaadHL.window.base.perm.IWinPermChecker;
import com.vaadHL.window.customize.ICustomizeLWMultiMode;
import com.vaadHL.window.customize.ICustomizeListWindow;
import com.vaadHL.window.customize.ICustomizeListWindow.DoubleClickAc;
import com.vaadin.ui.UI;

/**
 * Base list window. <br>
 * Basic functionality of closing , creating , form calling, customization,
 * filtering, sorting
 * 
 * @author Miroslaw Romaniuk
 *
 */
public abstract class BaseListWindow extends BaseWindow {

	private static final long serialVersionUID = 7671927662680629987L;

	/**
	 * 
	 * The window selection mode.
	 *
	 */
	public enum ChoosingMode {
		NO_CHOOSE, SINGLE_CHOOSE, MULTIPLE_CHOOSE
	};

	private ChoosingMode chooseMode = null;
	private boolean readOnlyWin;
	ICustomizeListWindow customize;

	public BaseListWindow(String winId, String caption,
			IWinPermChecker permChecker, ICustomizeLWMultiMode customize,
			ChoosingMode chooseMode, boolean readOnly, IAppContext appContext) {
		super(winId, caption,
				(chooseMode == ChoosingMode.NO_CHOOSE) ? customize
						.getNoChooseMode() : customize.getChooseMode(),
				permChecker, appContext);

		this.chooseMode = chooseMode;
		this.readOnlyWin = readOnly;
		if (chooseMode == ChoosingMode.NO_CHOOSE)
			this.customize = customize.getNoChooseMode();
		else
			this.customize = customize.getChooseMode();
	}

	public boolean isDetailsFunc() {
		return customize.isDetailsFunc();
	}

	public boolean isAddFunc() {
		return customize.isAddFunc();
	}

	public boolean isDeleteFunc() {
		return customize.isDeleteFunc();
	}

	public boolean isViewFunc() {
		return customize.isViewFunc();
	}

	public boolean isEditFunc() {
		return customize.isEditFunc();
	}

	public DoubleClickAc getDoubleClickAc() {
		return customize.getDoubleClickAc();
	}

	// ---------------- ------------
	/**
	 * Tests if the window has been launched in the read-only mode
	 * 
	 * @return true - it is
	 */
	public boolean isReadOnlyWin() {
		return readOnlyWin;
	}

	public void setReadOnlyWin(boolean readOnly) {
		this.readOnlyWin = readOnly;
	}

	/**
	 * Cancels selection(s) and closes the window.
	 */
	public void closeCancel() {
		super.close();
	}

	/**
	 * Accepts selection(s) and closes the window.
	 */
	public void closeChoose() {
		super.close();
	}

	/**
	 * Exits the window when no-selection mode.
	 */
	public void closeExit() {
		super.close();
	}

	@Override
	public void close() {
		if (chooseMode == ChoosingMode.NO_CHOOSE)
			closeExit();
		else
			closeCancel();
	}

	public ChoosingMode getChooseMode() {
		return chooseMode;
	}

	protected void setChooseMode(ChoosingMode chooseMode) {
		this.chooseMode = chooseMode;
	}

	/**
	 * Creates the form (detail) window for the specific record/item id
	 * 
	 * @param launchMode
	 *            {@link MWLaunchMode}
	 * @param mRowid
	 *            the id of the selected Item(s) , if null an id is selected
	 *            internally
	 * 
	 * @return the called object or null
	 */
	protected BaseWindow getForm(MWLaunchMode launchMode, Object mRowid) {
		return null;
	}

	/**
	 * Fires after a form is called (and shown). Default behaviour - registers
	 * on close listener for the called form
	 * 
	 * @param win
	 *            the just called form
	 * @param launchMode
	 *            {@link MWLaunchMode}
	 * @param mRowid
	 *            the id of the selected Item(s) , if null an id is selected
	 *            internally
	 */
	protected void afterFormCall(BaseWindow win, MWLaunchMode launchMode,
			Object mRowid) {
		win.addCloseListener(closeListener);

	}

	CloseListener closeListener = new CloseListener() {
		private static final long serialVersionUID = -6467466981831792900L;

		@Override
		public void windowClose(CloseEvent e) {
			afterFormClosed((BaseWindow) e.getSource());
		}
	};

	/**
	 * Fires after a form window closing
	 * 
	 * @param win
	 *            the form window
	 */
	protected void afterFormClosed(BaseWindow win) {

	}

	/**
	 * Calls the form (detail) window for the specific record/item id and fires
	 * {@link #callForm(MWLaunchMode launchMode, Object mRowid) afterCallForm }
	 * 
	 * @param launchMode
	 *            {@link MWLaunchMode}
	 * @param mRowid
	 *            the id of the selected Item(s) , if null an id is selected
	 *            internally
	 */
	public void callForm(MWLaunchMode launchMode, Object mRowid) {
		BaseWindow win = getForm(launchMode, mRowid);
		if (win == null)
			return;
		UI.getCurrent().addWindow(win);
		afterFormCall(win, launchMode, mRowid);
	}

	/**
	 * Calls the form (detail) window. An Id is selected internally.
	 * 
	 * @param launchMode
	 *            {@link MWLaunchMode}
	 */
	private void getForm(MWLaunchMode launchMode) {
		getForm(launchMode, null);
	}

	/**
	 * Calls the form (detail) window. An Id is selected internally. Next fires
	 * {@link #callForm(MWLaunchMode launchMode, Object mRowid) afterCallForm }
	 * 
	 * @param launchMode
	 *            {@link MWLaunchMode}
	 */
	public void callForm(MWLaunchMode launchMode) {
		callForm(launchMode, null);
	}

	/**
	 * Calls the form (detail) window in the details mode for the specific
	 * record/item id
	 * 
	 * @param mRowid
	 *            record/item id
	 */

	public void details(Object mRowid) {
		MWLaunchMode mode = isReadOnly() ? MWLaunchMode.VIEW_ONLY
				: MWLaunchMode.VIEW_EDIT;

		callForm(mode, mRowid);
	}

	/**
	 * Calls the form (detail) window in the add (new record) mode for the
	 * specific record id
	 * 
	 * @param mRowid
	 *            item id/ record id
	 */
	protected void add() {
		callForm(MWLaunchMode.NEW_REC);
	}

	/**
	 * Calls the form (detail) window in the delete mode for the specific record
	 * id
	 * 
	 * @param mRowid
	 *            item id/ record id
	 */
	public void delete(Object mRowid) {
		callForm(MWLaunchMode.DELETE, mRowid);
	}

	/**
	 * Calls the form (detail) window in the edit mode for the specific record
	 * id
	 * 
	 * @param mRowid
	 *            item id/ record id
	 */
	protected void edit(Object mRowid) {
		callForm(MWLaunchMode.EDIT, mRowid);
	}

	/**
	 * Calls the form (detail) window in the view mode for the specific record
	 * id
	 * 
	 * @param mRowid
	 *            item id/ record id
	 */
	protected void view(Object mRowid) {
		callForm(MWLaunchMode.VIEW_ONLY, mRowid);
	}

	/**
	 * Calls the form (detail) window in the details mode
	 */
	public void details() {
		callForm(isReadOnlyWin() ? MWLaunchMode.VIEW_ONLY
				: MWLaunchMode.VIEW_EDIT);
	}

	/**
	 * Calls the form (detail) window in the delete mode
	 */
	public void delete() {
		callForm(MWLaunchMode.DELETE);
	}

	/**
	 * Calls the form (detail) window in the edit mode
	 */
	protected void edit() {
		callForm(MWLaunchMode.EDIT);
	}

	/**
	 * Calls the form (detail) window in the view mode
	 */
	protected void view() {
		callForm(MWLaunchMode.VIEW_ONLY);
	}

	protected CloseCause closeCause;

	/**
	 * Gets the cause of a window close and selected objects if necessary
	 * 
	 */
	public CloseCause getCloseCause() {
		return closeCause;
	}

	/**
	 * Gets single selection to pass to the associated form window.<br>
	 * In case there is no single selection shows a message an returns null.
	 * 
	 * @return
	 */
	protected Object getCallFormSelIdMsg(Object mRowid) {
		return null;
	}

	public enum CloseCauseEnum {
		CHOOSE, CANCEL, NOCHOOSE
	};

	public class CloseCause {
		public CloseCause(CloseCauseEnum cause, Object addInfo) {
			super();
			this.cause = cause;
			this.addInfo = addInfo;
		}

		public CloseCauseEnum cause;
		public Object addInfo;
	}

	/**
	 * Remove all selecions
	 * 
	 */
	public void deselectAll() {

	}

	// ------- State handling -------

	/**
	 * List widow state key
	 */
	public class LWinStateKey implements Serializable {

		public LWinStateKey(String winId, ChoosingMode chooseMode) {
			super();
			this.winId = winId;
			this.chooseMode = chooseMode;
		}

		private static final long serialVersionUID = 6946110034836041223L;
		String winId;
		ChoosingMode chooseMode;

		public String getWinId() {
			return winId;
		}

		public void setWinId(String winId) {
			this.winId = winId;
		}

		public ChoosingMode getChooseMode() {
			return chooseMode;
		}

		public void setChooseMode(ChoosingMode chooseMode) {
			this.chooseMode = chooseMode;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof LWinStateKey) {
				LWinStateKey c = (LWinStateKey) obj;
				return c.getWinId().equals(getWinId())
						&& c.getChooseMode() == getChooseMode();

			} else
				return super.equals(obj);
		}

		@Override
		public int hashCode() {
			return getChooseMode().hashCode() + getWinId().hashCode() << 2;
		}
	}

	/**
	 * Saves the list state taking into account the choosing mode
	 */
	@Override
	protected void saveState() {
		getAppContext().getStateLoader().saveState(
				new LWinStateKey(getWinId(), getChooseMode()), getVHLState());
	}

	/**
	 * Restores the list state taking into account the choosing mode
	 */
	@Override
	protected void restoreState() {
		VHLState state = getAppContext().getStateLoader().loadState(
				new LWinStateKey(getWinId(), getChooseMode()));
		if (state != null)
			setVHLState(state);
	}

	/**
	 * Gets filtering settings
	 * 
	 * @return filtering settings (null allowed)
	 */
	public VHLFilterState getFiltering() {
		return null;
	}

	/**
	 * Sets filtering
	 * 
	 * @param filtering
	 *            settings (null allowed)
	 */
	public void setFiltering(VHLFilterState filtering) {

	}

	/**
	 * Gets sorting settings
	 * 
	 * @return sorting settings (null allowed)
	 */
	public VHLSortState getSorting() {
		return null;
	}

	/**
	 * Sets the sorting
	 * 
	 * @param sorting
	 *            settings (null allowed)
	 */
	public void setSorting(VHLSortState sorting) {

	}

	/**
	 * {@link BaseListWindow Base list window} state
	 *
	 */
	public class BLWState extends VHLState {
		private static final long serialVersionUID = -6905266860844604689L;
		VHLFilterState filtering;
		VHLSortState sorting;
		VHLState ancestor;

		public BLWState() {
			super(1);
		}

		public BLWState(VHLFilterState filtering, VHLSortState sorting,
				VHLState ancestor) {
			this();
			this.filtering = filtering;
			this.sorting = sorting;
			this.ancestor = ancestor;
		}

		public VHLFilterState getFiltering() {
			return filtering;
		}

		public void setFiltering(VHLFilterState filtering) {
			this.filtering = filtering;
		}

		public VHLSortState getSorting() {
			return sorting;
		}

		public void setSorting(VHLSortState sorting) {
			this.sorting = sorting;
		}

		public VHLState getAncestor() {
			return ancestor;
		}

		public void setAncestor(VHLState ancestor) {
			this.ancestor = ancestor;
		}
	}

	@Override
	public void setVHLState(VHLState state) {
		if (state == null)
			return;
		BLWState s = (BLWState) state;
		super.setVHLState(s.getAncestor());
		setSorting(s.getSorting());
		setFiltering(s.getFiltering());
	}

	@Override
	public VHLState getVHLState() {
		return new BLWState(getFiltering(), getSorting(), super.getVHLState());
	}
}
