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



public class PermAndChecker implements IWinPermChecker {

	IWinPermChecker c1;
	IWinPermChecker c2;
	
	public PermAndChecker(IWinPermChecker c1, IWinPermChecker c2) {
		super();
		this.c1 = c1;
		this.c2 = c2;
	}
	
	@Override
	public boolean canOpen(String winId) {
		return c1.canOpen(winId) && c2.canOpen(winId);
	}

	@Override
	public boolean canEdit(String winId) {
		return c1.canEdit(winId) && c2.canEdit(winId);
	}
	

	@Override
	public boolean canCreate(String winId) {
		return c1.canCreate(winId) && c2.canCreate(winId);
	}

	@Override
	public boolean canDelete(String winId) {
		return c1.canDelete(winId) && c2.canDelete(winId);
	}

	

}
