/* Copyright 2015 Mirosław Romaniuk (mi9rom@gmail.com)
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

import com.vaadHL.utl.action.ActionsIds;

public abstract class AbstractWinPermChecker implements IWinPermChecker {

	public AbstractWinPermChecker() {
	}

	@Override
	abstract public boolean canDo(String winId, int actionId);

	@Override
	public boolean canOpen(String winId) {
		return canDo(winId, ActionsIds.AC_OPEN);
	}

	@Override
	public boolean canEdit(String winId) {
		return canDo(winId, ActionsIds.AC_EDIT);
	}

	@Override
	public boolean canCreate(String winId) {
		return canDo(winId, ActionsIds.AC_CREATE);
	}

	@Override
	public boolean canDelete(String winId) {
		return canDo(winId, ActionsIds.AC_DELETE);
	}

}
