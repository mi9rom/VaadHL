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

/**
 * Returns the list of selected items and the cose.
 * 
 * @author mir
 *
 */
public interface IListSelectionAction {
	/**
	 * Fires when selection is being confirmed
	 * 
	 * @param items
	 *            - single item or set of selected items
	 */
	void Confirm(Object items);

	/**
	 * * Fires when selection is being canceled
	 * 
	 * @param items
	 *            single item or set of selected items if  necessary or null
	 */
	void Cancel(Object items);

	/**
	 * Fires when exiting the non-selection mode
	 * 
	 * @param items
	 *            single item or set of selected items if  necessary or null
	 */
	void Exit(Object items);

}
