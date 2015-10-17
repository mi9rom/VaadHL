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
 * A permission checker which is an AND combination of two checkers
 * 
 * @author Miroslaw Romaniuk
 *
 */
public class MasterSlavePermChecker implements IWinPermChecker {

	IWinPermChecker pch1;
	IWinPermChecker pch2;

	/**
	 * 
	 * @param master
	 *            master window permission checker
	 * @param slave
	 *            window permission checker
	 */
	public MasterSlavePermChecker(IWinPermChecker master, IWinPermChecker slave) {
		super();
		this.pch1 = master;
		this.pch2 = slave;
	}

	@Override
	public boolean canOpen() {
		return pch1.canOpen() && pch2.canOpen();
	}

	@Override
	public boolean canEdit() {
		return pch1.canEdit() && pch2.canEdit();
	}

	@Override
	public boolean canCreate() {
		return pch1.canCreate() && pch2.canCreate();
	}

	@Override
	public boolean canDelete() {
		return pch1.canDelete() && pch2.canDelete();
	}

	@Override
	public boolean canDo(int actionId) {
		return pch1.canDo(actionId) && pch2.canDo(actionId);
	}

	@Override
	public boolean canDo(int actionId, boolean defValue) {
		return pch1.canDo(actionId, defValue) && pch2.canDo(actionId, defValue);
	}

}
