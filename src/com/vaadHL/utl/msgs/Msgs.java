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

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

/**
 * Showing messages in a window.<br>
 * Implementation of the {@link IMsgs} interface
 * 
 * @author Miroslaw Romaniuk
 *
 */
public class Msgs implements IMsgs {

	public void showInfo(String caption) {
		showInfo(caption, 3000);
	}

	public void showInfo(String caption, int delay) {
		Notification not = new Notification(caption);
		not.setDelayMsec(delay);
		not.setHtmlContentAllowed(true);
		not.show(Page.getCurrent());
	}

	public void showWarning(String caption) {
		showWarning(caption, 3000);
	}

	public void showWarning(String caption, int delay) {
		Notification not = new Notification(caption,Notification.Type.WARNING_MESSAGE);
		not.setDelayMsec(delay);
		not.setHtmlContentAllowed(true);
		not.setCaption(caption);
		not.show(Page.getCurrent());
	}

	public void showError(String caption) {
		showError(caption, -1);
	}

	public void showError(String caption, int delay) {
		Notification not = new Notification("",Notification.Type.ERROR_MESSAGE);
		not.setDelayMsec(delay);
		not.setHtmlContentAllowed(true);
		not.setCaption(caption);
		not.show(Page.getCurrent());
		
	}

	/**
	 * Shows the error code with the additional Exception description. Shows
	 * until cancelled manually
	 */
	public void showError(String code, Exception e) {
		showError(code + ": " + e.getLocalizedMessage(), -1);
	}

}
