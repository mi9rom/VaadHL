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

package com.vaadHL.utl.action;

import com.vaadHL.i18n.I18Sup;

/**
 * Simply Actions ids container.
 *
 */
public class ActionsIds {
	public final static int AC_OPEN = 1;
	public final static int AC_CREATE = 2;
	public final static int AC_EDIT = 3;
	public final static int AC_DELETE = 4;
	public final static int AC_PREV_ITM = 5;
	public final static int AC_NEXT_ITM = 6;

	public final static int GAC_FWIN = 100001;
	public final static int GAC_LWIN = 100002;

	public ActionsIds() {
	}

	public static String getName(I18Sup i18, int ac) {
		switch (ac) {
		case AC_OPEN:
			return i18.getString("AC_OPEN");
		case AC_CREATE:
			return i18.getString("AC_CREATE");
		case AC_EDIT:
			return i18.getString("AC_EDIT");
		case AC_DELETE:
			return i18.getString("AC_DELETE");
		case AC_PREV_ITM:
			return i18.getString("AC_PREV_ITM");
		case AC_NEXT_ITM:
			return i18.getString("AC_NEXT_ITM");

		default:
			return i18.getString("AC_UNKNOWN");
		}
	}
}
