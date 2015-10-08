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
import com.vaadHL.window.customize.ICustomizeLWMultiMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

/**
 * List window.<br>
 * = {@link BaseListWindow} + bottom area buttons
 * 
 * @author Miroslaw Romaniuk
 *
 */
public class LWindow extends BaseListWindow {

	private static final long serialVersionUID = -3054090684577334443L;

	protected Button btOk = null;
	protected Button btCancel = null;
	protected Button btClose = null;
	protected Button btDetails = null;
	protected Button btCreate = null;
	protected Button btDelete = null;
	protected Button btEdit = null;
	protected Button btView = null;

	public LWindow(String winId, String caption, IWinPermChecker permChecker,
			ICustomizeLWMultiMode customize, ChoosingMode chooseMode,
			boolean readOnly, IAppContext appContext) {
		super(winId, caption, permChecker, customize, chooseMode, readOnly,
				appContext);
		if (approvedToOpen == false)
			return;

		btOk = new Button(getI18S("btOK"));
		btCancel = new Button(getI18S("btCancel"));
		btClose = new Button(getI18S("btClose"));
		btDetails = new Button(getI18S("btDetails"));
		btCreate = new Button(getI18S("btCreate"));
		btDelete = new Button(getI18S("btDelete"));
		btEdit = new Button(getI18S("btEdit"));
		btView = new Button(getI18S("btView"));

		ActionGroup newActions = new ActionGroup(ActionsIds.GAC_FWIN);
		ActionGroup readOnlyActions = new ActionGroup(200002);
		Action ac;

		btOk.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -4027804800730671542L;

			@Override
			public void buttonClick(ClickEvent event) {
				closeChoose();
			}
		});

		btCancel.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1283611336735142616L;

			@Override
			public void buttonClick(ClickEvent event) {
				closeCancel();
			}
		});

		btClose.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -8832039342969723178L;

			@Override
			public void buttonClick(ClickEvent event) {
				closeExit();
			}
		});

		btDetails.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 6988932903821049711L;

			@Override
			public void buttonClick(ClickEvent event) {
				details();

			}
		});

		ac = new Action(getAppContext(), ActionsIds.AC_CREATE, new Command() {

			@Override
			public void run(Action action) {
				add();
			}
		}, btCreate);
		newActions.put(ac);
		readOnlyActions.put(ac);

		ac = new Action(getAppContext(), ActionsIds.AC_DELETE, new Command() {

			@Override
			public void run(Action action) {
				delete();
			}
		}, btDelete);
		newActions.put(ac);
		readOnlyActions.put(ac);

		ac = new Action(getAppContext(), ActionsIds.AC_EDIT, new Command() {

			@Override
			public void run(Action action) {
				edit();
			}
		}, btEdit);
		newActions.put(ac);
		readOnlyActions.put(ac);

		btView.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -7738495347949482479L;

			@Override
			public void buttonClick(ClickEvent event) {
				view();

			}
		});

		addActionsAndChkPerm(newActions);
		if (isReadOnlyWin()) {
			readOnlyActions.setEnabled(false);
		}
	}

	/**
	 * Creates ( does not display) panel containing default window buttons
	 * 
	 * @return the created buttons panel
	 */
	public Component makeButtonsPanel() {
		HorizontalLayout bottPanel = new HorizontalLayout();

		if (isDetailsFunc())
			bottPanel.addComponent(btDetails);
		if (isAddFunc())
			bottPanel.addComponent(btCreate);
		if (isDeleteFunc())
			bottPanel.addComponent(btDelete);
		if (isEditFunc())
			bottPanel.addComponent(btEdit);
		if (isViewFunc())
			bottPanel.addComponent(btView);

		if (getChooseMode() == ChoosingMode.NO_CHOOSE) {
			bottPanel.addComponent(btClose);
		} else {
			bottPanel.addComponent(btOk);
			bottPanel.addComponent(btCancel);
		}

		// bottPanel.setWidth("100%");
		bottPanel.setSpacing(true);
		return bottPanel;

	}

	@Override
	public Component makeBottomArea() {
		return makeButtonsPanel();
	}

}
