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
import com.vaadHL.IAppContext;
import com.vaadHL.i18n.I18Sup;
import com.vaadHL.utl.action.ActionGroup;
import com.vaadHL.utl.msgs.IMsgs;
import com.vaadHL.utl.state.IStateVHL;
import com.vaadHL.utl.state.ScreenInfo;
import com.vaadHL.utl.state.VHLState;
import com.vaadHL.window.base.perm.IWinPermChecker;
import com.vaadHL.window.customize.ICustomizeWin;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Base window.
 * 
 * @author Miroslaw Romaniuk
 *
 */
public abstract class BaseWindow extends Window implements IStateVHL {

	private static final long serialVersionUID = 3460211791860318900L;
	private String winId;
	protected IWinPermChecker permChecker;
	/**
	 * Can window be opened in a normal way.
	 */
	protected boolean approvedToOpen;

	private ActionGroup actions;
	private IAppContext appContext;
	private ICustomizeWin customize;

	/**
	 * Creates a new base window. Sets the title.
	 * 
	 * @param winId
	 *            the window unique ID
	 * @param caption
	 *            the part of the title {@link BaseWindow#makeTitle makeTitle}
	 * @param permChecker
	 */
	public BaseWindow(String winId, String caption, ICustomizeWin customize,
			IWinPermChecker permChecker, IAppContext appContext) {
		super();
		this.appContext = appContext;
		this.winId = winId;
		this.permChecker = permChecker;
		this.customize = customize;
		setCaption(makeTitle(winId, caption));
		if (!canShow()) {
			approvedToOpen = false;
			setNotPermitedContent(winId + "- " + getI18S("MVHL-021"));
		} else
			approvedToOpen = true;
		initConstructorWidgets();
	}

	/**
	 * Displays the window content in case the window cannot be open due to
	 * permissions.
	 * 
	 * @param msg
	 *            the message to display.
	 */
	protected void setNotPermitedContent(String msg) {

		Button btClose = null;
		btClose = new Button(getI18S("btClose"));
		btClose.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -1610492227149824003L;

			@Override
			public void buttonClick(ClickEvent event) {
				BaseWindow.super.close();
			}
		});
		VerticalLayout p = new VerticalLayout();
		p.addComponent(new Label(msg));
		p.addComponent(btClose);
		p.setMargin(true);
		setContent(p);
	}

	/**
	 * Creates the window title.
	 * 
	 * @param winId
	 *            - the window unique ID
	 * @param caption
	 *            - the part of the title
	 * @return The window title. The default value is "ID - caption"
	 */
	public String makeTitle(String winId, String caption) {
		return winId + " - " + caption;
	}

	/**
	 * 
	 * @return the window Identifier string
	 */
	public String getWinId() {
		return winId;
	}

	/**
	 * Sets the window ID. Doesn't change anything else.
	 * 
	 * @param winID
	 */
	protected void setWinID(String winID) {
		this.winId = winID;
	}

	public IMsgs getMsgs() {
		return appContext.getMsgs();
	}

	public I18Sup getI18() {
		return appContext.getI18();
	}

	/**
	 * Gets localized string, if not found returns ?@param name?
	 * 
	 * @param name
	 *            the property name
	 * @return
	 */
	public String getI18S(String name) {

		return appContext.getI18().getStringNE(name);

	}

	/**
	 * Gets localized array of string
	 * 
	 * @param name
	 *            the property name
	 * @return
	 */
	public String[] getI18AS(String name) {

		return appContext.getI18().getArryString(name);

	}

	public ActionGroup getActions() {
		if (actions == null) {
			actions = new ActionGroup(ActionGroup.AC_ROOT);
		}
		return actions;
	}

	/**
	 * Checks if there is permission to show the window.
	 * 
	 * @return Permission to show the window (true = permitted)
	 */
	public boolean canShow() {
		if (permChecker != null)
			if (!permChecker.canOpen(winId))
				return false;
		return true;
	}

	/**
	 * Makes (does not display) the upper area of the window content.
	 */
	public Component makeUpperArea() {
		return null;
	}

	/**
	 * Makes (does not display) the middle area of the window content.
	 */
	public Component makeMiddleArea() {
		return null;
	}

	/**
	 * Makes (does not display) the bottom area of the window content.
	 */
	public Component makeBottomArea() {
		return null;
	}

	/**
	 * Creates the content of the window but does not set (display). The content
	 * consists of three vertically placed areas: the upper, middle and bottom
	 * area. Create content but don't bind data.
	 * 
	 * @return The just created content of the window.
	 */
	public Component getCompositeContent() {
		VerticalLayout content = new VerticalLayout();
		Component c;
		c = makeUpperArea();
		if (c != null)
			content.addComponent(c);
		c = makeMiddleArea();
		if (c != null) {
			c.setSizeFull();
			content.addComponent(c);
			content.setExpandRatio(c, 1);
		}
		c = makeBottomArea();
		if (c != null) {
			VerticalLayout v = new VerticalLayout();
			v.addComponent(c);
			v.setComponentAlignment(c, Alignment.BOTTOM_CENTER);
			/*
			 * Label gap = new Label(); gap.setHeight("5px");
			 * v.addComponent(gap);
			 */
			content.addComponent(v);
			content.setComponentAlignment(v, Alignment.BOTTOM_CENTER);
		}
		content.setSpacing(true);
		return (content);
	}

	/**
	 * Creates and sets the content of the window. Uses
	 * {@link #getCompositeContent getCompositeContent} for the content
	 * creation. Create content but don't bind data.
	 */
	public void setCompositeContent() {
		Component c = getCompositeContent();
		c.setSizeFull();
		VerticalLayout l = new VerticalLayout();
		l.addComponent(c);
		l.setSizeFull();
		l.setMargin(true);
		setContent(l);
	}

	public boolean beforeClose() {
		return true;
	}

	@Override
	public void close() {
		if (approvedToOpen) {
			beforeClose();
			if (isAutoSaveState())
				saveState();
		}
		super.close();
	}

	/**
	 * If necessery, inside constructor objects creation. Only create , don't
	 * bind data e.t.c
	 */
	public void initConstructorWidgets() {

	}

	/**
	 * Adds action group to the window actions.
	 * 
	 * @param ag
	 */
	protected void addActions(ActionGroup ag) {
		getActions().put(ag);
	}

	/**
	 * Adds action group to the window actions and sets its state using
	 * permission checker.
	 * 
	 * @param ag
	 *            the action group
	 */
	protected void addActionsAndChkPerm(ActionGroup ag) {
		getActions().put(ag);
		ag.setPermisions(getWinId(), permChecker);
	}

	public IAppContext getAppContext() {
		return appContext;
	}

	/**
	 * Refresh the window content
	 */
	public void refresh() {

	}

	// -------------- State handling -------------------

	public boolean isAutoSaveState() {
		return customize.isAutoSaveState();
	}

	public boolean isAutoRestoreState() {
		return customize.isAutoRestoreState();
	}

	/**
	 * Gets the current window state
	 */
	@Override
	public VHLState getVHLState() {
		ScreenInfo si = new ScreenInfo();
		si.readFrom(this);
		return si;
	}

	/**
	 * Sets the current window state
	 */
	public void setVHLState(VHLState state) {
		if (state == null)
			return;
		try {
			ScreenInfo si = (ScreenInfo) state;
			if (si != null)
				si.applyToWin(this);
		} catch (Exception e) {
			getMsgs().showError("VHL-023", e);
		}
	}

	/**
	 * Saves the current window state
	 */
	protected void saveState() {
		try {
			appContext.getStateLoader().saveState(getWinId(), getVHLState());
		} catch (Exception e) {
			getMsgs().showError("VHL-024", e);
		}
	}

	/**
	 * Restores the window state
	 */
	protected void restoreState() {
		try {
			setVHLState(appContext.getStateLoader().loadState(getWinId()));
		} catch (Exception e) {
			getMsgs().showError("VHL-025", e);
		}
	}

	@Override
	public void attach() {
		super.attach();
		if (isAutoRestoreState())
			restoreState();
	}
}
