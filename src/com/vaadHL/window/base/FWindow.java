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

import com.vaadHL.IAppContext;
import com.vaadHL.utl.action.Action;
import com.vaadHL.utl.action.Action.Command;
import com.vaadHL.utl.action.ActionGroup;
import com.vaadHL.utl.action.ActionsIds;
import com.vaadHL.window.base.perm.IWinPermChecker;
import com.vaadHL.window.customize.ICustomizeEditWin;
import com.vaadHL.window.customize.ICustomizeFWin;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 * Form window <br>
 * = {@link BaseEditWindow BaseEditWindow} + user interface (bottom panel) +
 * prev, next record move handling
 * 
 * @author Miroslaw Romaniuk
 *
 */
public abstract class FWindow extends BaseEditWindow {

	private static final long serialVersionUID = -6883321082928354658L;

	protected Button btOk = null;
	protected Button btCancel = null;
	protected Button btSave = null;
	protected Button btDiscard = null;
	protected Button btNextRec = null;
	protected Button btPrevRec = null;
	protected Button btClose = null;
	protected Button btCreate = null;
	protected Button btDelete = null;
	protected Button btEdit = null;

	private ICustomizeFWin customize;

	public FWindow(String winId, String caption,
			IWinPermChecker masterPermChecker, MWLaunchMode launchMode,
			IAppContext appContext, boolean readOnlyW,
			ICustomizeFWin forceCustomize) {
		super(winId, caption, masterPermChecker, launchMode, appContext,
				readOnlyW, forceCustomize);
		if (!approvedToOpen)
			return;
		this.customize = forceCustomize == null ? (ICustomizeFWin) appContext
				.getWinCustomizerFactory().getCustomizer(winId)
				: forceCustomize;
		btCreate = new Button(getI18S("btCreate"));
		getAction(ActionsIds.AC_CREATE).attach(btCreate);
		btDelete = new Button(getI18S("btDelete"));
		getAction(ActionsIds.AC_DELETE).attach(btDelete);
		btOk = new Button(getI18S("btOK"));
		getAction(ActionsIds.AC_COMMIT_AND_CLOSE).attach(btOk);
		btPrevRec = new Button("<");
		getAction(ActionsIds.AC_PREV_ITM).attach(btPrevRec);
		btNextRec = new Button(">");
		getAction(ActionsIds.AC_NEXT_ITM).attach(btNextRec);
		btCancel = new Button(getI18S("btCancel"));
		getAction(ActionsIds.AC_CANCEL_AND_CLOSE).attach(btCancel);
		btSave = new Button(getI18S("btSave"));
		getAction(ActionsIds.AC_SAVE_ASK_MSG).attach(btSave);
		btDiscard = new Button(getI18S("btDiscard"));
		getAction(ActionsIds.AC_DISCARD_ASK_MSG).attach(btDiscard);
		btClose = new Button(getI18S("btClose"));
		getAction(ActionsIds.AC_CLOSE).attach(btClose);
		btEdit = new Button(getI18S("btEdit"));
		getAction(ActionsIds.AC_EDIT).attach(btEdit);

	}

	@Override
	protected void createActions() {
		super.createActions();
		ActionGroup newActions = new ActionGroup(ActionsIds.GAC_F_WIN);

		newActions.put(new Action(getAppContext(), ActionsIds.AC_PREV_ITM,
				new Command() {
					@Override
					public void run(Action action) {
						moveToPrevRecordChk();
					}
				}));

		newActions.put(new Action(getAppContext(), ActionsIds.AC_NEXT_ITM,
				new Command() {

					@Override
					public void run(Action action) {
						moveToNextRecordChk();
					}
				}));
		addActionsAndChkPerm(newActions);

	}

	protected MenuBar makeDefaultMenu() {
		MenuBar mb = super.makeMainMenu();
		if (mb == null)
			mb = new MenuBar();

		MenuItem menEd = mb.addItem(getI18S("mnEdition"), null);
		getAction(ActionsIds.AC_EDIT).attach(
				menEd.addItem(getI18S("btEdit"), null));
		getAction(ActionsIds.AC_DELETE).attach(
				menEd.addItem(getI18S("btDelete"), null));
		getAction(ActionsIds.AC_CREATE).attach(
				menEd.addItem(getI18S("btCreate"), null));

		MenuItem toolIt = mb.addItem(getI18S("mnTools"), null);
		addStateMenu(toolIt);
		return mb;
	}

	@Override
	protected MenuBar makeMainMenu() {
		return makeDefaultMenu();
	}

	/**
	 * Creates ( does not display) panel containing default window buttons
	 * 
	 * @return the created buttons panel
	 */
	public Component makeButtonsPanel() {
		HorizontalLayout bottPanel = new HorizontalLayout();
		if (getLaunchMode() == MWLaunchMode.VIEW_EDIT) {
			bottPanel.addComponent(btCreate);
			bottPanel.addComponent(btDelete);
			bottPanel.addComponent(btEdit);
		} else {
			crudActions.setVisible(false);
		}

		// record move buttons
		if ((getLaunchMode() == MWLaunchMode.VIEW_EDIT
				|| getLaunchMode() == MWLaunchMode.EDIT || getLaunchMode() == MWLaunchMode.VIEW_ONLY)
				&& isPrevNextFunc()) {
			bottPanel.addComponent(btPrevRec);
			bottPanel.addComponent(btNextRec);
		}

		// save cancel buttons
		if (getLaunchMode() == MWLaunchMode.VIEW_EDIT
				|| getLaunchMode() == MWLaunchMode.EDIT) {
			bottPanel.addComponent(btSave);
			bottPanel.addComponent(btDiscard);
			if (isShowOKCancel()) {
				bottPanel.addComponent(btOk);
				bottPanel.addComponent(btCancel);
			} else
				bottPanel.addComponent(btClose);
		}

		if (getLaunchMode() == MWLaunchMode.NEW_REC) {
			bottPanel.addComponent(btOk);
			bottPanel.addComponent(btCancel);
		} else if (getLaunchMode() == MWLaunchMode.DELETE) {
			bottPanel.addComponent(btDelete);
			getAction(ActionsIds.AC_DELETE).setVisible(true);
			bottPanel.addComponent(btCancel);
		} else if (getLaunchMode() == MWLaunchMode.VIEW_ONLY)
			bottPanel.addComponent(btClose);

		// bottPanel.setWidth("100%");
		bottPanel.setSpacing(true);
		return bottPanel;
	}

	@Override
	public Component makeBottomArea() {

		return makeButtonsPanel();
	}

	/**
	 * Moves to the next record. Before moving checks modification state and
	 * performs actions according to customization.
	 */
	public void moveToNextRecordChk() {

		if (isModified()) {
			saveDiscardAction(new Runnable() {
				@Override
				public void run() {
					moveToNextRecord();
				}
			});
		} else
			moveToNextRecord();
	}

	/**
	 * Moves to the previous record. Before moving checks modification state and
	 * performs actions according to customization.
	 */
	public void moveToPrevRecordChk() {

		if (isModified()) {
			saveDiscardAction(new Runnable() {
				@Override
				public void run() {
					moveToPrevRecord();
				}
			});
		} else
			moveToPrevRecord();
	}

	/**
	 * Moves to the next record. No checks.
	 */
	abstract protected void moveToNextRecord();

	/**
	 * Moves to the previous record. No checks.
	 */
	abstract protected void moveToPrevRecord();

	public boolean isShowOKCancel() {
		return customize.isShowOKCancel();
	}

	@Override
	public boolean setEditModeChkMsg(boolean editMode) {
		if (super.setEditModeChkMsg(editMode)) {
			btEdit.setCaption(editMode == true ? getI18S("btROnly")
					: getI18S("btEdit"));
			return true;
		} else
			return false;
	}

}
