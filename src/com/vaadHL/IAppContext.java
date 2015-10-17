package com.vaadHL;

import com.vaadHL.i18n.I18Sup;
import com.vaadHL.utl.msgs.IMsgs;
import com.vaadHL.utl.state.IVHLStateLoader;
import com.vaadHL.window.base.perm.IWinPermFactory;
import com.vaadHL.window.customize.IWinCustomizerFactory;

public interface IAppContext {
	public abstract IMsgs getMsgs();

	public abstract void setMsgs(IMsgs msgs);

	public abstract I18Sup getI18();

	public abstract void setI18(I18Sup i18);

	public abstract IVHLStateLoader getStateLoader();

	public abstract void setStateLoader(IVHLStateLoader stateLoader);

	public abstract IWinPermFactory getWinPermFactory();

	public abstract void setWinPermFactory(IWinPermFactory winPermFactory);
	
	public IWinCustomizerFactory getWinCustomizerFactory();

	public void setWinCustomizerFactory(IWinCustomizerFactory winCustomizerFactory);


}