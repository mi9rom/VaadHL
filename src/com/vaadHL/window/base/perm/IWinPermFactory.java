package com.vaadHL.window.base.perm;

/**
 * Provides window permission checkers
 *
 */
public interface IWinPermFactory {
	IWinPermChecker getChecker(String winId);
}
