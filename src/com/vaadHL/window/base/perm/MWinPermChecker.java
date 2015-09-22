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
 * Master (details) window permission checker which is associated with a list window.  
 * @author Miroslaw Romaniuk
 *
 */
public class MWinPermChecker implements IWinPermChecker {

	String lWinId=null;
	IWinPermChecker pch = null;
	
	/**
	 * 
	 * @param lWinId the associated list window ID
	 * @param pch master window permission checker
	 */
	public MWinPermChecker(String lWinId, IWinPermChecker pch) {
		super();
		this.lWinId = lWinId;
		this.pch = pch;
	}

	@Override
	public boolean canOpen(String winId) {
		return pch.canOpen(winId) && pch.canOpen(lWinId); 
	}

	@Override
	public boolean canEdit(String winId) {
		return pch.canEdit(winId) && pch.canEdit(lWinId); 
	}

	@Override
	public boolean canCreate(String winId) {
		return pch.canCreate(winId) && pch.canCreate(lWinId); 
	}

	@Override
	public boolean canDelete(String winId) {
		return pch.canDelete(winId) && pch.canDelete(lWinId); 
	}

	@Override
	public boolean canDo(String winId, int actionId) {
		return pch.canDo(winId, actionId) && pch.canDo(lWinId, actionId); 
	}
	
	
	
}
