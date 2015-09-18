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

package com.vaadHL.utl.msgs;

/**
 * Showing messages in a window
 * 
 * @author mir
 *
 */
public interface IMsgs {

	/**
	 * Shows the information message with the default delay
	 * 
	 * @param caption
	 */
	public void showInfo(String caption);

	/**
	 * Shows the information message with the custom delay
	 * 
	 * @param caption
	 * @param delay
	 *            the custom delay (milliseconds) of showing the message, -1 -
	 *            unlimited
	 */
	public void showInfo(String caption, int delay);

	/**
	 * Shows the warning message with the default delay
	 * 
	 * @param caption
	 */
	public void showWarning(String caption);

	/**
	 * Shows the warning message with the custom delay
	 * 
	 * @param caption
	 * @param delay
	 *            the custom delay (milliseconds) of showing the message, -1 -
	 *            unlimited
	 */
	public void showWarning(String caption, int delay);

	/**
	 * Shows the error message with the default delay
	 * 
	 * @param caption
	 */

	public void showError(String caption);

	/**
	 * Shows the warning message with the custom delay
	 * 
	 * @param caption
	 * @param delay
	 *            the custom delay (milliseconds) of showing the message, -1 -
	 *            unlimited
	 */
	public void showError(String caption, int delay);

	/**
	 * Shows the error code with the additional Exception description. 
	 * @param code
	 * @param e
	 */
	public void showError(String code, Exception e);

}
