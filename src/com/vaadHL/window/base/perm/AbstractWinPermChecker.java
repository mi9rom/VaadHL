package com.vaadHL.window.base.perm;

import com.vaadHL.utl.action.ActionsIds;

public abstract class AbstractWinPermChecker implements IWinPermChecker {

	public AbstractWinPermChecker() {
	}

	@Override
	abstract public boolean canDo(String winId, int actionId) ;

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
