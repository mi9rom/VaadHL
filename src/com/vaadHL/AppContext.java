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

package com.vaadHL;

import com.vaadHL.i18n.I18Sup;
import com.vaadHL.i18n.VaadHLi18n;
import com.vaadHL.utl.msgs.IMsgs;
import com.vaadHL.utl.msgs.Msgs;
import com.vaadHL.utl.state.IVHLStateLoader;
import com.vaadHL.utl.state.InMemVHLStateLoader;
import com.vaadHL.window.base.perm.IWinPermFactory;

/**
 * Default implementation of the {@link IAppContext} Application context
 * interface}
 * 
 */
public class AppContext implements IAppContext {

	private IMsgs msgs; // messages
	private I18Sup i18; // internationalization
	private IVHLStateLoader stateLoader; // saving/restoring states
	IWinPermFactory winPermFactory;

	public AppContext() {

	}
	
	public AppContext(I18Sup i18) {
		setI18(i18);
	}
	
	public AppContext(I18Sup i18, IWinPermFactory winPermFactory) {
		setI18(i18);
		this.winPermFactory = winPermFactory;
	}

	public AppContext(IMsgs msgs, I18Sup i18, IVHLStateLoader stateLoader,
			IWinPermFactory winPermFactory) {
		super();
		this.msgs = msgs;
		this.i18 = i18;
		this.stateLoader = stateLoader;
		this.winPermFactory = winPermFactory;
	}

	
	@Override
	public IMsgs getMsgs() {
		if (msgs == null) {
			msgs = new Msgs();
		}
		return msgs;
	}

	@Override
	public void setMsgs(IMsgs msgs) {
		this.msgs = msgs;
	}

	@Override
	public I18Sup getI18() {
		if (i18 == null) {
			i18 = new VaadHLi18n();
		}
		return i18;
	}

	@Override
	public void setI18(I18Sup i18) {
		this.i18 = i18;
	}

	@Override
	public IVHLStateLoader getStateLoader() {
		if (stateLoader == null)
			stateLoader = new InMemVHLStateLoader();
		return stateLoader;
	}

	@Override
	public void setStateLoader(IVHLStateLoader stateLoader) {
		this.stateLoader = stateLoader;
	}

	@Override
	public IWinPermFactory getWinPermFactory() {
		return winPermFactory;
	}

	@Override
	public void setWinPermFactory(IWinPermFactory winPermFactory) {
		this.winPermFactory = winPermFactory;
	}

}
