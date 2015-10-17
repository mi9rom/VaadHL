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
import com.vaadHL.utl.action.ActionsIds;
import com.vaadHL.window.base.perm.IWinPermChecker;
import com.vaadHL.window.customize.ICustomizeLWMultiMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

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

	public LWindow(String winId, String caption, IWinPermChecker masterPermChecker,
			ICustomizeLWMultiMode customize, ChoosingMode chooseMode,
			boolean readOnly, IAppContext appContext) {
		super(winId, caption, masterPermChecker, customize, chooseMode, readOnly,
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

		getAction(ActionsIds.AC_DETAILS).attach(btDetails);
		getAction(ActionsIds.AC_CREATE).attach(btCreate);
		getAction(ActionsIds.AC_DELETE).attach(btDelete);
		getAction(ActionsIds.AC_EDIT).attach(btEdit);
		getAction(ActionsIds.AC_VIEW).attach(btView);

	}

	protected MenuBar makeDefaultMenu() {
		MenuBar mb = super.makeMainMenu();
		if (mb == null)
			mb = new MenuBar();

		MenuItem menEd = mb.addItem(getI18S("mnEdition"), null);

		if (isDetailsFunc())
			getAction(ActionsIds.AC_DETAILS).attach(
					menEd.addItem(getI18S("btDetails"), null));
		if (isAddFunc())
			getAction(ActionsIds.AC_CREATE).attach(
					menEd.addItem(getI18S("btCreate"), null));

		if (isDeleteFunc())
			getAction(ActionsIds.AC_DELETE).attach(
					menEd.addItem(getI18S("btDelete"), null));

		if (isEditFunc())
			getAction(ActionsIds.AC_EDIT).attach(
					menEd.addItem(getI18S("btEdit"), null));
		if (isViewFunc())
			getAction(ActionsIds.AC_VIEW).attach(
					menEd.addItem(getI18S("btView"), null));

		MenuItem toolIt = mb.addItem(getI18S("mnTools"), null);
		getAction(ActionsIds.AC_REFRESH).attach(
				toolIt.addItem(getI18S("mnRefresh"), null));
		getAction(ActionsIds.AC_DESELECT_ALL).attach(
				toolIt.addItem(getI18S("mnUnselAll"), null));
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
