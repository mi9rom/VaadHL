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

import com.vaadHL.utl.action.Action.ObjInfo;
import com.vaadHL.window.base.perm.IPermChecker;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Action grouping. Contains Actions and ActionGroups.
 */
public class ActionGroup implements IActionsManipulate {
	public static final int AC_ROOT = 0;
	/**
	 * action group identifier
	 */
	int id;
	Map<Integer, Object> content;

	boolean enabled = true;
	boolean visible = true;

	public ActionGroup(int groupId) {
		this.id = groupId;
	}

	public Map<Integer, Object> getContent() {
		if (content == null)
			content = new HashMap<Integer, Object>();
		return content;
	}

	public void put(Action a) {
		if (getContent().containsKey(new Integer(a.getId())))
			return;
		getContent().put(new Integer(a.getId()), a);
	}

	public void put(ActionGroup a) {
		if (getContent().containsKey(new Integer(a.getId())))
			return;
		getContent().put(new Integer(a.getId()), a);
	}

	@Override
	public Map<Object, ObjInfo> getAttached() {
		throw new NotImplementedException();
	}

	@Override
	public void detach(Object o) {
		throw new NotImplementedException();
	}

	@Override
	public void detachAll() {
		throw new NotImplementedException();
	}

	@Override
	public void setEnabled(boolean enabled) {
		setEnabled(enabled, true);
	}

	@Override
	public void setEnabled(boolean enabled, boolean changeAttached) {
		setEnabled(enabled, changeAttached, false);

	}

	@Override
	public void setEnabled(boolean enabled, boolean changeAttached,
			boolean force) {
		if (force == false && this.enabled == enabled)
			return; // no change

		this.enabled = enabled;
		IActionsManipulate m;
		for (Object o : content.values()) {
			m = ((IActionsManipulate) o);
			m.setEnabled(enabled, changeAttached, force);
		}

	}

	/**
	 * Sets enabled state of and Action or ActionGroup
	 * 
	 * @param id
	 *            the identifier of the Action or ActionGroup
	 * @param enabled
	 *            enabled state to set
	 * @param changeAttached
	 *            do change enabled state of Objects attached to
	 * @param force
	 *            force to change enabled state even if the current state = @param
	 *            enabled
	 */
	public void setEnabled(int id, boolean enabled, boolean changeAttached,
			boolean force) {
		IActionsManipulate m = (IActionsManipulate) getActionOrGr(id);
		if (m == null)
			return;
		m.setEnabled(enabled, changeAttached, force);
	}

	@Override
	public void setVisible(boolean visible) {
		setVisible(visible, false);
	}

	@Override
	public void setVisible(boolean visible, boolean force) {
		if (force == false && this.visible == visible)
			return; // no change
		this.visible = visible;
		IActionsManipulate m;
		for (Object o : content.values()) {
			m = ((IActionsManipulate) o);
			m.setVisible(visible);
		}
	}

	/**
	 * 
	 * @param id
	 *            the identifier of the Action or ActionGroup
	 * @param visible
	 */
	public void setVisible(int id, boolean visible) {
		IActionsManipulate m = (IActionsManipulate) getActionOrGr(id);
		if (m == null)
			return;
		m.setVisible(visible);
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	/**
	 * 
	 * @param id
	 *            the identifier of the Action
	 */
	public Object getAction(int id) {
		return (Action) getActionOrGr(id);
	}

	/**
	 * 
	 * @param id
	 *            the identifier of the Action or ActionGroup
	 */
	public Object getActionOrGr(int id) {
		if (this.id == id)
			return this;
		Object r;
		r = content.get(new Integer(id));
		if (r != null)
			return r;

		for (Object o : content.values()) {
			if (o instanceof ActionGroup) {
				r = ((ActionGroup) o).getActionOrGr(new Integer(id));
				if (r != null)
					return r;
			}
		}
		return null;
	}

	public boolean isEnabled(int id) {
		IActionsManipulate m = ((IActionsManipulate) getActionOrGr(id));
		if (m == null)
			return false;
		else
			return m.isEnabled();
	}

	@Override
	public boolean isEnabled() {
		return enabled;

	}

	/**
	 * Gets the ActinnGroup id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the ActionGroup elements enabled state basing on provided permissions
	 * @param winId the window id
	 * @param perm the permission checker
	 */
	public void setPermisions(String winId, IPermChecker perm) {
		for (Object o : content.values()) {
			if (o instanceof Action) {
				Action a = (Action) o;
				if (perm != null)
					a.setEnabled(perm.canDo(winId, a.getId()));
				else
					a.setEnabled(true);
			} else if (o instanceof ActionGroup)
				((ActionGroup) o).setPermisions(winId, perm);
		}
	}

}
