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

package com.vaadHL.window.base.perm;

/**
 * Window level permission checking interface.
 * 
 * @author Miroslaw Romaniuk
 *
 */
public interface IWinPermChecker {
	/**
	 * Is there permission to open the window.
	 * 
	 * @param winId the window identifier
	 */
	public boolean canOpen(String winId);

	/**
	 * Is there permission for editing in the window.
	 * 
	 * @param winId the window identifier
	 * @return
	 */
	public boolean canEdit(String winId);

	/**
	 * Is there permission for creating in the window.
	 * 
	 * @param winId the window identifier
	 * @return
	 */
	public boolean canCreate(String winId);

	/**
	 * Is there permission for deleting in the window.
	 * 
	 * @param winId the window identifier
	 * @return
	 */
	public boolean canDelete(String winId);

	/**
	 * Is there permission for the action 
	 * @param winId the window identifier
	 * @param actionId the id of the action to check 
	 * @return
	 */
	public boolean canDo(String winId,String actionId);
}
