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
import com.vaadHL.window.customize.ICustomizeFWin;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

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

	public FWindow(String winId, String caption, IWinPermChecker permChecker,
			ICustomizeFWin customize, MWLaunchMode launchMode,
			IAppContext appContext, boolean readOnlyW) {
		super(winId, caption, permChecker, customize, launchMode, appContext,
				readOnlyW);
		if (!approvedToOpen)
			return;
		this.customize = customize;
		btCreate = new Button(getI18S("btCreate"));
		btDelete = new Button(getI18S("btDelete"));
		btOk = new Button(getI18S("btOK"));
		btPrevRec = new Button("<");
		btNextRec = new Button(">");
		btCancel = new Button(getI18S("btCancel"));
		btSave = new Button(getI18S("btSave"));
		btDiscard = new Button(getI18S("btDiscard"));
		btClose = new Button(getI18S("btClose"));
		btEdit = new Button(getI18S("btEdit"));

		btOk.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -1556072964935330377L;

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				commitAndClose();
			}
		});

		// new actions added
		ActionGroup newActions = new ActionGroup(ActionsIds.GAC_FWIN);

		newActions.put(new Action(getAppContext(), ActionsIds.AC_PREV_ITM,
				new Command() {

					@Override
					public void run(Action action) {
						moveToPrevRecordChk();
					}
				}, btPrevRec));

		newActions.put(new Action(getAppContext(), ActionsIds.AC_NEXT_ITM,
				new Command() {

					@Override
					public void run(Action action) {
						moveToNextRecordChk();
					}
				}, btNextRec));

		btCancel.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -7802414620464909596L;

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				cancelAndClose();
			}

		});

		btSave.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -8503804187231367058L;

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				saveAskMsg(null);
			}
		});

		btDiscard.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -8254287623127616951L;

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				discardAskMsg(null);
			}
		});

		btClose.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -1610492227149824003L;

			@Override
			public void buttonClick(ClickEvent event) {
				close();

			}
		});

		newActions.put(new Action(getAppContext(), ActionsIds.AC_DELETE,
				new Command() {

					@Override
					public void run(Action action) {
						deleteAskMsg();
					}
				}, btDelete));

		newActions.put(new Action(getAppContext(), ActionsIds.AC_CREATE,
				new Command() {

					@Override
					public void run(Action action) {
						createAskMsg();
					}
				}, btCreate));

		newActions.put(new Action(getAppContext(), ActionsIds.AC_EDIT,
				new Command() {

					@Override
					public void run(Action action) {
						toggleEditing();
					}
				}, btEdit));

		addActionsAndChkPerm(newActions);

	}

	/**
	 * Toggles the editing state of the window.
	 */
	protected void toggleEditing() {
		setEditModeChkMsg(!isEditingMode());
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
