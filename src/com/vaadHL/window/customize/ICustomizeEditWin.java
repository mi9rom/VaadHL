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
 * Editing window customization parameters.
 * 
 * @author Miroslaw Romaniuk
 *
 */
public interface ICustomizeEditWin extends ICustomizeWin{
	/**
	 * Before window closing if there are modifications:<br>
	 * <li>ASK - ask to save or discard changes & close the window</li> <li>SAVE
	 * - save changes without asking & close the window</li> <li>DISCARD -
	 * discard changes without asking & close the window</li> <li>MESSAGE - show
	 * the message but do not close the window
	 */
	public enum AutoSaveDiscard {
		ASK, SAVE, DISCARD, MESSAGE
	}

	/**
	 * Confirmation before saving changes
	 * 
	 * @return true = ask
	 */
	public boolean isAskSave();

	/**
	 * Confirmation before discarding changes
	 * 
	 * @return true = ask
	 */
	public boolean isAskDiscard();

	/**
	 * @see AutoSaveDiscard
	 * @return modifications autosave behaviour.
	 */
	public AutoSaveDiscard getAutoSaveDiscard();

	/**
	 * Confirmation before creation
	 * 
	 * @return true = ask
	 */
	public boolean isAskDelete();

	/**
	 * Confirmation before deletion
	 * 
	 * @return true = ask
	 */
	public boolean isAskCreate();

	/**
	 * Is there record moving functionality available
	 * @return true = available
	 */
	public boolean isPrevNextFunc();
	
}
