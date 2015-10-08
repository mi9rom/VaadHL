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

package com.vaadHL.utl.helper;

import java.io.Serializable;

import com.vaadin.ui.Table;

/**
 * Saving/restoring of a visual table state, that is:
 * <ul>
 * <li>columns visibility</li>
 * <li>columns order</li>
 * <li>columns widths</li>
 * </ul>
 * Sorting and filtering intentionally not included
 */
public class TableState implements Serializable {
	private static final long serialVersionUID = 8907880234490070712L;
	private Object[] visibleColumns;
	private int[] widths;
	private boolean[] collapsed;

	public TableState(Table t) {
		readFrom(t);
	}

	public void readFrom(Table t) {
		visibleColumns = t.getVisibleColumns();
		widths = new int[visibleColumns.length];
		collapsed = new boolean[visibleColumns.length];
		for (int i = 0; i < visibleColumns.length; i++) {
			widths[i] = t.getColumnWidth(visibleColumns[i]);
			collapsed[i] = t.isColumnCollapsed(visibleColumns[i]);
		}

	}

	public void applyTo(Table t) {
		final Object[] saved;
		saved = t.getVisibleColumns();

		if (saved.length != visibleColumns.length)
			return;

		try {
			t.setVisibleColumns(visibleColumns);
		} catch (IllegalArgumentException nothing) {
			t.setVisibleColumns(saved);
			return;
		}

		for (int i = 0; i < visibleColumns.length; i++) {
			t.setColumnWidth(visibleColumns[i], widths[i]);
			t.setColumnCollapsed(visibleColumns[i], collapsed[i]);
		}
	}

}