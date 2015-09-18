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

import com.vaadHL.utl.msgs.IMsgs;
import com.vaadHL.window.base.perm.IWinPermChecker;
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
public abstract class FWindow extends BaseEditWindow implements ICustomizeFWin {

	private static final long serialVersionUID = -6883321082928354658L;

	protected Button btOk = null;
	protected Button btCancel = null;
	protected Button btSave = null;
	protected Button btDiscard = null;
	protected Button btNextRec = null;
	protected Button btPrevRec = null;
	protected Button btClose = null;
	protected Button btAdd = null;
	protected Button btDelete = null;
	protected Button btEdit = null;
	boolean showOKCancel = true;

	public FWindow(String winId, String caption, IWinPermChecker permChecker,
			ICustomizeFWin cust, MWLaunchMode launchMode, IMsgs msgs,
			boolean readOnlyW) {
		super(winId, caption, permChecker, cust, launchMode, msgs, readOnlyW);
		if (!approvedToOpen)
			return;
		setShowOKCancel(cust.isShowOKCancel());
		btAdd = new Button("New");
		btDelete = new Button("Delete");
		btOk = new Button("OK");
		btPrevRec = new Button("<");
		btNextRec = new Button(">");
		btCancel = new Button("Cancel");
		btSave = new Button("Save");
		btDiscard = new Button("Discard");
		btClose = new Button("Close");
		btEdit = new Button("Edit");

		btOk.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -1556072964935330377L;

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				commitAndClose();
			}
		});

		btNextRec.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -126742841511297625L;

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				moveToNextRecordChk();
			}
		});

		btPrevRec.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -6612432787611519817L;

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				moveToPrevRecordChk();
			}

		});

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

		btDelete.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 726940831676037620L;

			@Override
			public void buttonClick(ClickEvent event) {
				deleteAskMsg();

			}
		});

		btAdd.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 5194123651061486740L;

			@Override
			public void buttonClick(ClickEvent event) {
				createAskMsg();
			}
		});

		btEdit.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -5141223393911623763L;

			@Override
			public void buttonClick(ClickEvent event) {
				toggleEditing();

			}
		});

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

			bottPanel.addComponent(btAdd);
			if (permChecker != null)
				if (!permChecker.canCreate(getWinId()))
					btAdd.setEnabled(false);

			bottPanel.addComponent(btDelete);
			if (permChecker != null)
				if (!permChecker.canDelete(getWinId()))
					btDelete.setEnabled(false);

			bottPanel.addComponent(btEdit);
			if (permChecker != null)
				if (!permChecker.canEdit(getWinId()))
					btEdit.setEnabled(false);
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
		return showOKCancel;
	}

	public void setShowOKCancel(boolean showOKCancel) {
		this.showOKCancel = showOKCancel;
	}

	/*	*//*
		 * Sets the window components enabled state.
		 * 
		 * @param enabled
		 * 
		 * @return TRUE - operation performed
		 */
	/*
	 * protected boolean setFwCompEnabled(boolean enabled) { //
	 * btAdd.setEnabled(enabled); // btDelete.setEnabled(enabled); return true;
	 * 
	 * }
	 * 
	 * @Override protected boolean disableEdit() {
	 * 
	 * return setFwCompEnabled(false); }
	 * 
	 * @Override protected boolean enableEdit() { return setFwCompEnabled(true);
	 * }
	 */

	@Override
	public boolean setEditModeChkMsg(boolean editMode) {
		if (super.setEditModeChkMsg(editMode)) {
			btEdit.setCaption(editMode == true ? "ROnly" : "Edit");
			return true;
		} else
			return false;
	}
}
