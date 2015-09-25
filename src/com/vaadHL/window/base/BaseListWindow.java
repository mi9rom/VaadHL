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

import com.vaadHL.AppContext;
import com.vaadHL.window.base.perm.IWinPermChecker;

/**
 * Base list window. <br>
 * Basic functionality of closing , creating , form calling, customization.
 * 
 * @author Miroslaw Romaniuk
 *
 */
public abstract class BaseListWindow extends BaseWindow implements
		ICustomizeListWindow {

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

	/**
	 * Is Details functionality available.
	 */
	boolean detailsFunc = true;
	/**
	 * Show the Add functionality available.
	 */
	boolean addFunc = true;
	/**
	 * Show the delete functionality available.
	 */
	boolean deleteFunc = true;
	/**
	 * Show the edit functionality available.
	 */
	boolean editFunc = true;
	/**
	 * Show the View functionality available.
	 */
	boolean viewFunc = true;

	DoubleClickAc doubleClickAc = DoubleClickAc.CHOOSE;

	public BaseListWindow(String winId, String caption,
			IWinPermChecker permChecker, ICustomizeLWMultiMode customize,
			ChoosingMode chooseMode, boolean readOnly, AppContext appContext) {
		super(winId, caption, permChecker, appContext);

		this.chooseMode = chooseMode;
		this.readOnlyWin = readOnly;
		this.customize(customize);
	}

	// ---------------- customization ------------

	/**
	 * Window behaviour customization setting.
	 * 
	 * @param cust
	 *            customization parameters
	 */
	public void customize(ICustomizeLWMultiMode custM) {
		ICustomizeListWindow cust;
		if (chooseMode == ChoosingMode.NO_CHOOSE)
			cust = custM.getNoChooseMode();
		else
			cust = custM.getChooseMode();
		
		setDetailsFunc(cust.isDetailsFunc());
		setAddFunc(cust.isAddFunc());
		setDeleteFunc(cust.isDeleteFunc());
		setViewFunc(cust.isViewFunc());
		setEditFunc(cust.isEditFunc());
		setDoubleClickAc(cust.getDoubleClickAc());
	}

	@Override
	public boolean isDetailsFunc() {
		return detailsFunc;
	}

	public void setDetailsFunc(boolean details) {
		this.detailsFunc = details;
	}

	@Override
	public boolean isAddFunc() {
		return addFunc;
	}

	public void setAddFunc(boolean addRec) {
		this.addFunc = addRec;
	}

	@Override
	public boolean isDeleteFunc() {
		return deleteFunc;
	}

	public void setDeleteFunc(boolean deleteRec) {
		this.deleteFunc = deleteRec;
	}

	@Override
	public boolean isViewFunc() {
		return viewFunc;
	}

	public void setViewFunc(boolean view) {
		this.viewFunc = view;
	}

	@Override
	public boolean isEditFunc() {
		return editFunc;
	}

	public void setEditFunc(boolean edit) {
		this.editFunc = edit;
	}

	public DoubleClickAc getDoubleClickAc() {
		return doubleClickAc;
	}

	public void setDoubleClickAc(DoubleClickAc doubleClickAc) {
		this.doubleClickAc = doubleClickAc;
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
	 * Calls the form (detail) window for the specific record
	 * 
	 * @param launchMode
	 *            {@link MWLaunchMode}
	 * @param mRowid
	 */
	public void callForm(MWLaunchMode launchMode, Object mRowid) {

	}

	/**
	 * Calls the form (detail) window.
	 * 
	 * @param launchMode
	 *            {@link MWLaunchMode}
	 */
	public void callForm(MWLaunchMode launchMode) {
		callForm(launchMode, null);
	}

	/**
	 * Calls the form (detail) window in the details mode for the specific
	 * record id
	 * 
	 * @param mRowid
	 *            item id/ record id
	 */

	public void details(Object mRowid) {
		callForm(
				isReadOnly() ? MWLaunchMode.VIEW_ONLY : MWLaunchMode.VIEW_EDIT,
				mRowid);
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

	/**
	 * Gets selected object on the list to return in selection mode.
	 * 
	 * @return
	 */
	protected Object getReturnSelection() {
		return null;
	}

	/**
	 * Gets single selectoin to pass to the associated form window.<br> In case the is no single selection shows message an returns null.
	 * 
	 * @return
	 */
	protected Object getCallFormSelIdMsg(Object mRowid) {
		return null;
	}

}
