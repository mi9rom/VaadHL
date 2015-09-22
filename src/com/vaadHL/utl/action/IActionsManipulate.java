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

import java.util.Map;

import com.vaadHL.utl.action.Action.ObjInfo;

public interface IActionsManipulate {

	public abstract int getId();

	/**
	 * Gets objects attached to.
	 * 
	 * @return attached objects
	 */
	public abstract Map<Object, ObjInfo> getAttached();

	/**
	 * Detaches form the object;
	 * 
	 * @param o
	 *            the Object to detach from;
	 */
	public abstract void detach(Object o);

	/**
	 * Detaches from all objects;
	 */
	public abstract void detachAll();

	public abstract boolean isEnabled();

	/**
	 * Sets enabled state and enabled state of Objects attached to. In case the
	 * state is not changed does nothing.
	 * 
	 * @param enabled
	 */
	public abstract void setEnabled(boolean enabled);

	/**
	 * Sets enabled state and if changeAttached = true sets enabled state of
	 * Objects attached to. In case the state is not changed does nothing.
	 * 
	 * @param enabled
	 * @param changeAttached
	 *            do change enabled state of Objects attached to
	 */
	public abstract void setEnabled(boolean enabled, boolean changeAttached);

	/**
	 * Sets enabled state and if changeAttached = true sets enabled state of
	 * Objects attached to. If force = true forces to set enabled state of all
	 * Objects attached to.
	 * 
	 * @param enabled
	 * @param changeAttached
	 *            do change enabled state of Objects attached to
	 * @param force
	 *            force to change enabled state even if the current state = @param
	 *            enabled
	 */
	public abstract void setEnabled(boolean enabled, boolean changeAttached,
			boolean force);

	public abstract boolean isVisible();

	/**
	 * Sets visibility of Objects attached to. In case the visibility is not
	 * changed does nothing.
	 * 
	 * @param visible
	 */
	public abstract void setVisible(boolean visible);

	/**
	 * Sets visibility of Objects attached to. If force = true forces to set
	 * visible state of all Objects.
	 * 
	 * @param visible
	 * @param force
	 *            force to change visible state even if the current state = @param
	 *            visible
	 */
	public abstract void setVisible(boolean visible, boolean force);

}