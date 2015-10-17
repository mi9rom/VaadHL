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

import com.vaadHL.IAppContext;
import com.vaadHL.window.base.LTabWindow;
import com.vaadHL.window.base.LWindow;
import com.vaadHL.window.base.perm.IWinPermChecker;
import com.vaadHL.window.customize.ICustomizeLWMultiMode;

/**
 * Entity Manager based list window.<br>
 * = {@link LWindow} + {@link EntityManager} setting.
 * 
 * @author Miroslaw Romaniuk
 *
 */
public class LEMWindow extends LTabWindow {

	private static final long serialVersionUID = -3569392737049508383L;
	protected EntityManager em;

	public LEMWindow(String winId, String caption,
			IWinPermChecker masterPermChecker, ChoosingMode chooseMode,
			boolean readOnly, EntityManager em, IAppContext appContext) {
		super(winId, caption, masterPermChecker,  chooseMode,
				readOnly, appContext);
		this.em = em;
		if (approvedToOpen == false)
			return;
	}
}
