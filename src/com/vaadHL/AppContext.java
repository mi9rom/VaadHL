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
import com.vaadHL.utl.msgs.IMsgs;

/**
 * Application context i.e. run environment.
 * 
 *
 */
public class AppContext {

	private IMsgs msgs;
	I18Sup i18;

	public AppContext() {
	
	}

	public AppContext(IMsgs msgs, I18Sup i18) {
		super();
		this.msgs = msgs;
		this.i18 = i18;
	}

	public IMsgs getMsgs() {
		return msgs;
	}

	public void setMsgs(IMsgs msgs) {
		this.msgs = msgs;
	}

	public I18Sup getI18() {
		return i18;
	}

	public void setI18(I18Sup i18) {
		this.i18 = i18;
	}
}
