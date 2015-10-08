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

package com.vaadHL.window.customize;

/**
 * List window customization in the particular launch mode.
 * 
 * @author Miroslaw Romaniuk
 *
 */
public interface ICustomizeListWindow extends ICustomizeWin {

	/**
	 * 
	 * @return is the details functionality available
	 */
	public boolean isDetailsFunc();

	/**
	 * 
	 * @return is the add functionality available
	 */
	public boolean isAddFunc();

	/**
	 * 
	 * @return is the delete functionality available
	 */
	public boolean isDeleteFunc();

	/**
	 * 
	 * @return is the view functionality available
	 */
	public boolean isViewFunc();

	/**
	 * 
	 * @return is the edit functionality available
	 */
	public boolean isEditFunc();

	/**
	 * 
	 * Double click action
	 * <ul>
	 * <li>DETAILS - open the associated form window in the details mode</li>
	 * <li>VIEW - open the associated form window in the view mode</li>
	 * <li>EDIT - open the associated form window in the edit mode</li>
	 * <li>DELETE - delete the clicked record</li>
	 * <li>CREATE - create a new record</li>
	 * <li>CHOOSE - when in the choosing mode - choose the selected record(s))
	 * and exit in the OK mode</li>
	 * <li>NOTHING - do nothing</li>
	 * </ul>
	 */
	public enum DoubleClickAc {
		DETAILS, VIEW, EDIT, DELETE, CREATE, CHOOSE, NOTHING
	}

	/**
	 * 
	 * @return {@link DoubleClickAc the double click action }
	 * @see DoubleClickAc
	 */
	DoubleClickAc getDoubleClickAc();
}