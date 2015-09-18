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
import com.vaadHL.window.base.BaseListWindow.ChoosingMode;
import com.vaadHL.window.base.ICustomizeListWindow.DoubleClickAc;
import com.vaadHL.window.base.perm.IWinPermChecker;
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
	protected Button btAdd = null;
	protected Button btDelete = null;
	protected Button btEdit = null;
	protected Button btView = null;

	
	
	public LWindow(String winId, String caption, IWinPermChecker permChecker,
			ICustomizeLWMultiMode customize, ChoosingMode chooseMode,
			boolean readOnly, IMsgs msgs) {
		super(winId, caption, permChecker, customize, chooseMode, readOnly,
				msgs);
		if (approvedToOpen == false)
			return;

		btOk = new Button("OK");
		btCancel = new Button("Cancel");
		btClose = new Button("Close");
		btDetails = new Button("Details");
		btAdd = new Button("Add");
		btDelete = new Button("Delete");
		btEdit = new Button("Edit");
		btView = new Button("View");

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

		btAdd.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -6260434736494673567L;
           
			@Override
			public void buttonClick(ClickEvent event) {
				add();
				
			}
		});

		btDelete.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 2589075267689608411L;

			@Override
			public void buttonClick(ClickEvent event) {
				delete();
			}

			
		});

		btEdit.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 2493584539912040786L;

			@Override
			public void buttonClick(ClickEvent event) {
				edit();
				
			}
		});

		btView.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -7738495347949482479L;

			@Override
			public void buttonClick(ClickEvent event) {
				view();
				
			}
		});

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
			bottPanel.addComponent(btAdd);
		if (isDeleteFunc())
			bottPanel.addComponent(btDelete);
		if (isEditFunc())
			bottPanel.addComponent(btEdit);
		if (isViewFunc())
			bottPanel.addComponent(btView);

		if (isReadOnlyWin()) {
			btDetails.setEnabled(false);
			btAdd.setEnabled(false);
			btDelete.setEnabled(false);
			btEdit.setEnabled(false);
		}

		if (permChecker != null) {
			if (!permChecker.canCreate(getWinId()))
				btAdd.setEnabled(false);
			if (!permChecker.canDelete(getWinId()))
				btDelete.setEnabled(false);
			if (!permChecker.canEdit(getWinId()))
				btEdit.setEnabled(false);
		}

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
