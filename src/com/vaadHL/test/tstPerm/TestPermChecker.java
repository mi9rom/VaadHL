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

package com.vaadHL.test.tstPerm;

import java.util.HashMap;

import com.vaadHL.window.base.perm.IWinPermChecker;
/**
 * Mock class simulating permission checking 
 * @author Miroslaw Romaniuk
 *
 */
public class TestPermChecker implements IWinPermChecker{

	HashMap<String,WinPermCheckerBin> a = new HashMap<String, WinPermCheckerBin>();
	
	
	public void add(String wid, WinPermCheckerBin wb){
		a.put(wid, wb);
	}
	
	public WinPermCheckerBin get(String wid){
		return a.get(wid);
	}
	
	
	@Override
	public boolean canOpen(String winId) {
		return get(winId).isCanOpen();
	}

	@Override
	public boolean canEdit(String winId) {
		return get(winId).isCanEdit();
	}

	@Override
	public boolean canCreate(String winId) {
		return get(winId).isCanCreate();
	}

	@Override
	public boolean canDelete(String winId) {
		return get(winId).isCanDelete();
	}

	@Override
	public boolean canDo(String winId, String actionId) {
		if(actionId.equals(WinPermCheckerBin.canDoId))
			return get(winId).isCanDo();
		else
			return false;
	}
}
