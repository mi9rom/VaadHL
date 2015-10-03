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

package com.vaadHL.window.EM;

import javax.persistence.EntityManager;

import org.vaadin.addons.lazyquerycontainer.LazyEntityContainer;

import com.vaadHL.AppContext;
import com.vaadHL.window.base.FWindow;
import com.vaadHL.window.base.ICustomizeFWin;
import com.vaadHL.window.base.MWLaunchMode;
import com.vaadHL.window.base.perm.IWinPermChecker;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;

/**
 * Single item form window.<br>
 * {@link javax.persistence.EntityManager EntityManager} and
 * {@link org.vaadin.addons.lazyquerycontainer.LazyEntityContainer
 * LazyEntityContainer} based. ,It is very important itemId not to be changed
 * after container.refresh()
 * 
 * @author Miroslaw Romaniuk
 *
 */
public abstract class SingIeItemFWindow extends FWindow {
	private static final long serialVersionUID = 5582665524383059819L;
	protected LazyEntityContainer<?> container;
	protected EntityManager em;
	protected FieldGroup binder;
	/**
	 * Current item identifier
	 */
	protected Object curItId;

	/**
	 * It id remembered before creation of a new item
	 */
	protected Object befCreateId;

	public SingIeItemFWindow(String winId, String caption,
			IWinPermChecker permChecker, ICustomizeFWin cust,
			MWLaunchMode launchMode, EntityManager em,
			LazyEntityContainer<?> container, Object itemId,
			AppContext appContext, boolean readOnlyW) {
		super(winId, caption, permChecker, cust, launchMode, appContext,
				readOnlyW);
		if (!approvedToOpen)
			return;
		this.em = em;
		this.container = container;
		curItId = itemId;
		binder = new FieldGroup();
		postContructor(curItId);
	}

	/**
	 * Bind the item to fields
	 * 
	 * @param item
	 *            The item to bind
	 */

	protected void bind(Item item) {
		binder.setItemDataSource(item);
	}

	protected void rebind() {
		bindId(curItId);
	}

	/**
	 * Bind the item to fields <br>
	 * Side effects: sets curItId
	 * 
	 * @param itId
	 *            - the item id do bind
	 */
	protected void bindId(Object itId) {
		if (itId == null)
			return;
		Item item = container.getItem(itId);
		if (item == null) {
			getMsgs().showError("VHL-002: " + getI18S("MVHL-002"));
		} else {
			curItId = itId;
			bind(item);
		}
	}

	@Override
	protected void moveToNextRecord() {
		Object id = container.nextItemId(curItId);
		if (id == null)
			return;
		bindId(id);
		setDefaultEditingMode();
	}

	@Override
	protected void moveToPrevRecord() {
		Object id = container.prevItemId(curItId);
		if (id == null)
			return;
		bindId(id);
		setDefaultEditingMode();
	}

	@Override
	protected void save() throws Exception {

		binder.commit();
		container.commit();

		if (getCurWinMode() == MWLaunchMode.NEW_REC) {
			if (getLaunchMode() == MWLaunchMode.VIEW_EDIT) {
				/*
				 * after the commit the itemId is changed and itemID searching
				 * is not time efficient so simply reposition to the latter
				 * itemId
				 */
				bindId(befCreateId == null ? container.getIdByIndex(0)
						: befCreateId);
				setDefaultEditingMode();
				setCurWinMode(getLaunchMode());
			}
		} else
			rebind();
	}

	@Override
	protected void discard() {

		binder.discard();
		container.discard();
		if (getLaunchMode() == MWLaunchMode.NEW_REC)
			close();
		if (getCurWinMode() == MWLaunchMode.NEW_REC
				&& getLaunchMode() != MWLaunchMode.NEW_REC) {
			bindId(befCreateId == null ? container.getIdByIndex(0)
					: befCreateId);
			setDefaultEditingMode();
		}
		setCurWinMode(getLaunchMode());
	}

	@Override
	public boolean isModified() {
		return (binder.isModified() || container.isModified());
	}

	@Override
	public void delete() {
		Object id = null;
		id = nearestItemId();
		container.removeItem(curItId);
		container.commit();
		if (getLaunchMode() == MWLaunchMode.VIEW_EDIT) {
			bindId(id);
		} else {
			curItId = id;
			close();
		}
	}

	/**
	 * 
	 * @return The nearest item id. ( The next item id is searched first if not
	 *         found then searches the previous one )
	 */
	protected Object nearestItemId() {
		Object id = null;
		id = container.nextItemId(curItId);
		if (id == null)
			id = container.prevItemId(curItId);
		return id;
	}

	@Override
	public void create() {
		super.create();
		befCreateId = curItId;
		Object itemId = container.addItem();
		bindId(itemId);
	}

	/**
	 * Set content of the window and bind data.
	 * 
	 * @param itemId
	 *            - the item identifier
	 */
	protected void postContructor(Object itemId) {
		if (!approvedToOpen)
			return;

		setCompositeContent();
		if (getLaunchMode() == MWLaunchMode.NEW_REC)
			create();
		else {
			if (itemId == null) {
				getMsgs().showError(getI18S("MVHL-020"));
				// close(); doesn't work here
			} else
				bindId(itemId);
		}
		setDefaultEditingMode();

	}

	@Override
	protected boolean disableEdit() {
		if (super.disableEdit()) {
			binder.setReadOnly(true);
			return true;
		} else
			return false;
	}

	@Override
	protected boolean enableEdit() {
		if (super.enableEdit()) {
			binder.setReadOnly(false);
			return true;
		} else
			return false;
	}

	/**
	 * Gets the last current item id
	 * 
	 * @return
	 */
	public Object getCurItId() {
		return curItId;
	}

}
