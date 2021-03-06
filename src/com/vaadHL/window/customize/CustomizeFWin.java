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
 * Simple implementation of the {@link ICustomizeFWin} Form Window customization
 * interface
 *
 */
public class CustomizeFWin extends CustomizeBaseWin implements ICustomizeFWin {

	private boolean askSave;
	private boolean askDiscard;
	private AutoSaveDiscard autoSaveDiscard;
	private boolean askDelete;
	private boolean askCreate;
	private boolean showOKCancel;
	private boolean prevNextFunc;

	@Override
	void setDefault() {
		super.setDefault();
		askSave = false;
		askDiscard = true;
		autoSaveDiscard = AutoSaveDiscard.ASK;
		askDelete = true;
		askCreate = false;
		showOKCancel = true;
		prevNextFunc = true;
	}

	public void setAskSave(boolean askSave) {
		this.askSave = askSave;
	}

	public void setAskDiscard(boolean askDiscard) {
		this.askDiscard = askDiscard;
	}

	public void setAutoSaveDiscard(AutoSaveDiscard autoSaveDiscard) {
		this.autoSaveDiscard = autoSaveDiscard;
	}

	public void setAskDelete(boolean askDelete) {
		this.askDelete = askDelete;
	}

	public void setAskCreate(boolean askCreate) {
		this.askCreate = askCreate;
	}

	public void setShowOKCancel(boolean showOKCancel) {
		this.showOKCancel = showOKCancel;
	}

	public boolean isPrevNextFunc() {
		return prevNextFunc;
	}

	public void setPrevNextFunc(boolean prevNextFunc) {
		this.prevNextFunc = prevNextFunc;
	}

	@Override
	public boolean isAskSave() {
		return askSave;
	}

	@Override
	public boolean isAskDiscard() {
		return askDiscard;
	}

	@Override
	public AutoSaveDiscard getAutoSaveDiscard() {
		return autoSaveDiscard;
	}

	@Override
	public boolean isAskDelete() {
		return askDelete;
	}

	@Override
	public boolean isAskCreate() {
		return askCreate;
	}

	@Override
	public boolean isShowOKCancel() {
		return showOKCancel;
	}

}
