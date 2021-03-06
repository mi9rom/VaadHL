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

package com.vaadHL.utl.action;

import java.util.HashMap;
import java.util.Map;

import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuItem;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuItemClickEvent;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuItemClickListener;

import com.vaadHL.IAppContext;
import com.vaadHL.utl.data.WrongObjectTypeException;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 * Something important to run you have to control, especially to control
 * externally. The Action can be associated with widgets to control their state
 * and respond for particular events.
 * 
 * @author Miroslaw Romaniuk
 *
 */
public class Action implements IActionsManipulate {

	/**
	 * action identifier
	 */
	int id;

	/**
	 * (Object the action is attached to, object info)
	 */
	Map<Object, ObjInfo> attached;
	Command command;
	boolean enabled = true;
	boolean visible = true;
	IAppContext appContext;
	boolean permChecking = true;

	public Action(IAppContext appContext, int actionId) {
		this(appContext, actionId, null);
	}

	public Action(IAppContext appContext, int actionId, Command command) {
		this.id = actionId;
		this.appContext = appContext;
		this.command = command;
	}

	/**
	 * Creates an action without permission checking. Only internal state.
	 * 
	 * @param appContext
	 * @param actionId
	 * @param command
	 * @param enabled
	 */
	public Action(IAppContext appContext, int actionId, Command command,
			boolean enabled) {
		this.id = actionId;
		this.appContext = appContext;
		this.command = command;
		this.enabled = enabled;
		this.permChecking = false;
	}

	public Action(IAppContext appContext, int actionId, Command command,
			Object... objs) {
		this(appContext, actionId, command, true, true, true, objs);
	}

	public Action(IAppContext appContext, int actionId, Command command,
			boolean runAction, boolean enabling, boolean hiding, Object... objs) {
		this.id = actionId;
		this.command = command;
		this.appContext = appContext;
		if (objs != null)
			for (Object o : objs) {
				attach(o, runAction, enabling, hiding);
			}
	}

	@Override
	public Map<Object, ObjInfo> getAttached() {
		if (attached == null)
			attached = new HashMap<Object, ObjInfo>();
		return attached;
	}

	/**
	 * Attach the action to the object.<br>
	 * Depending on the object type attaches the action command if
	 * runAction=true
	 * 
	 * @param runAction
	 *            do attach the action command
	 * @param o
	 *            the Object to attach to
	 * @param enabling
	 *            true- the action changes enabled state of the object
	 * 
	 * @param hiding
	 *            true - the action can hide the object
	 */
	public void attach(Object o, boolean runAction, boolean enabling,
			boolean hiding) {
		if (!getAttached().containsKey(o)) {
			Object listener = null;
			if (runAction) {

				if (o instanceof Button) {
					listener = new Button.ClickListener() {
						private static final long serialVersionUID = -6387554438687264016L;

						@Override
						public void buttonClick(ClickEvent event) {
							if (enabled && command != null)
								command.run(Action.this);
						}
					};
					((Button) o)
							.addClickListener((Button.ClickListener) listener);
				} else if (o instanceof MenuItem) {
					listener = new MenuBar.Command() {
						private static final long serialVersionUID = 9067240940931195222L;

						@Override
						public void menuSelected(MenuItem selectedItem) {
							if (enabled && command != null)
								command.run(Action.this);
						}
					};
					((MenuItem) o).setCommand((MenuBar.Command) listener);
				} else if (o instanceof ContextMenuItem) {
					listener = new ContextMenuItemClickListener() {
						@Override
						public void contextMenuItemClicked(
								ContextMenuItemClickEvent event) {
							if (enabled && command != null)
								command.run(Action.this);
						}
					};
					((ContextMenuItem) o)
							.addItemClickListener((ContextMenuItemClickListener) listener);
				} else {
					throw new WrongObjectTypeException(
							"VHL-014: Only Buttons, MenuItems, ContextMenus are allowed to attach.");
				}
			}
			getAttached().put(o, new ObjInfo(o, listener, enabling, hiding));
			setEnabled(o, enabled);
		}
	}

	/**
	 * Attach the action to the object.<br>
	 * 
	 * @param o
	 *            the Object to attach to
	 */
	public void attach(Object o) {
		attach(o, true, true, true);
	}

	@Override
	public void detach(Object o) {
		if (attached == null)
			return;

		detachLsnr(o);
		attached.remove(o);
	}

	/**
	 * Detach the action run listener from the object.
	 * 
	 * @param o
	 */
	protected void detachLsnr(Object o) {
		if (o == null)
			return;
		ObjInfo objInfo = attached.get(o);
		if (objInfo == null)
			return;

		if (o instanceof Button) {
			Button.ClickListener al = (ClickListener) objInfo.listener;
			if (al != null)
				((Button) o).removeClickListener(al);
		} else if (o instanceof MenuItem) {
			((MenuItem) o).setCommand(null);
		} else if (o instanceof ContextMenuItem) {
			((ContextMenuItem) o)
					.removeItemClickListener((ContextMenuItemClickListener) objInfo.listener);
		}
	}

	@Override
	public void detachAll() {
		if (attached == null)
			return;

		for (Object o : attached.keySet()) {
			detachLsnr(o);
		}
		attached = null;
	}

	/**
	 * Run the command.
	 */
	public void run() {

		if (enabled && command != null)
			command.run(this);

	}

	public interface Command {
		public void run(Action action);
	}

	@Override
	public void setEnabled(boolean enabled) {
		setEnabled(enabled, true);
	}

	@Override
	public void setEnabled(boolean enabled, boolean changeAttached,
			boolean force) {
		if (force == false && this.enabled == enabled)
			return; // no change
		this.enabled = enabled;

		if (attached == null)
			return;
		if (changeAttached) {
			for (ObjInfo oi : attached.values()) {
				if (oi.enabling)
					setEnabled(oi.o, enabled);
			}
		}

	}

	@Override
	public void setEnabled(boolean enabled, boolean changeAttached) {
		setEnabled(enabled, changeAttached, false);
	}

	/**
	 * Sets enabled state of the object attached to.
	 * 
	 * @param o
	 * @param enabled
	 */
	protected void setEnabled(Object o, boolean enabled) {

		if (o instanceof AbstractComponent) {
			((AbstractComponent) o).setEnabled(enabled);
		} else if (o instanceof MenuItem) {
			((MenuItem) o).setEnabled(enabled);
		} else if (o instanceof ContextMenuItem) {
			((ContextMenuItem) o).setEnabled(enabled);
		} else {
			throw new WrongObjectTypeException("VHL-013: "
					+ appContext.getI18().getString("MVHL-013")
					+ o.getClass().getName());
		}
	}

	@Override
	public void setVisible(boolean visible) {
		setVisible(visible, false);
	}

	@Override
	public void setVisible(boolean visible, boolean force) {
		if (attached == null)
			return;
		if (force == false && this.visible == visible)
			return; // no change
		this.visible = visible;

		for (ObjInfo oi : attached.values()) {
			if (oi.enabling)
				setVisible(oi.o, visible);
		}

	}

	/**
	 * Sets visibility of the object attached to.
	 * 
	 * @param o
	 * @param visible
	 */
	protected void setVisible(Object o, boolean visible) {
		if (attached == null)
			return;
		if (o instanceof AbstractComponent) {
			((AbstractComponent) o).setVisible(visible);
		} else if (o instanceof MenuItem) {
			((MenuItem) o).setVisible(visible);
		} else if (o instanceof ContextMenuItem) {
			((ContextMenuItem) o).setEnabled(enabled); // not possible to hide
		} else {
			throw new WrongObjectTypeException(
					"VHL-014: Cannot control visibility state of the object type:"
							+ o.getClass().getName());
		}
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Gets the Action id
	 */
	public int getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Action) {
			return (getId() == ((Action) obj).getId());
		} else
			return false;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean isEnabled() {
		return enabled;

	}

	/**
	 * Object info the action is attached to.
	 *
	 */
	public class ObjInfo {
		public Object o;
		public Object listener;
		public boolean enabling;
		public boolean hiding;

		public ObjInfo(Object o, Object listener, boolean enabling,
				boolean hiding) {
			super();
			this.o = o;
			this.listener = listener;
			this.enabling = enabling;
			this.hiding = hiding;
		}

		public ObjInfo(Object o, Object listener) {
			this(o, listener, true, true);
		}

	}

	public boolean isPermChecking() {
		return permChecking;
	}

}
