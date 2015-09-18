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

package com.vaadHL.utl.helper;

import java.util.HashSet;
import java.util.Set;

import com.vaadHL.utl.msgs.IMsgs;
import com.vaadin.data.Item;
import com.vaadin.ui.Table;

public class TableHelper {

	private Table table;
	IMsgs msgs = null;

	public TableHelper(Table table, IMsgs msgs) {
		super();
		this.table = table;
		this.msgs = msgs;
	}

	public Object getSelectedItems() {
		Object valT = table.getValue();

		if (valT == null) {
			return null;
		}

		if (valT instanceof Set<?>) {
			if (((Set<?>) valT).isEmpty())
				return null;

			Set<Object> itms = new HashSet<Object>();

			for (Object rowidLW2 : ((Set<?>) valT)) {
				Item item = table.getItem(rowidLW2);
				if (item != null)
					itms.add(item);
			}
			return itms;

		} else {
			Item item = table.getItem(valT);
			return item;
		}
	}
}
