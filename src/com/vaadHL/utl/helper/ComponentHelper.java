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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.vaadHL.utl.data.IdName;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;

/**
 * Helper class for components manipulations
 * 
 * @author Miroslaw Romaniuk
 *
 */
public class ComponentHelper {

	/**
	 * Populate the AbstractSelect component with strings.<br>
	 * Creates new datasource.<br>
	 * Each entry is of type {@link IdName } .
	 * 
	 * @param component
	 *            the component
	 * @param s
	 *            the comma separated string list or array of strings
	 */
	static public void populateIdName(AbstractSelect component, String... s) {
		BeanItemContainer<IdName> bic = new BeanItemContainer<IdName>(
				IdName.class);
		int i = 0;
		for (String sp : s)
			bic.addItem(new IdName(i++, sp));

		component.setContainerDataSource(bic);
		component.setItemCaptionPropertyId("name");

	}

	/**
	 * Populate the AbstractSelect component with strings.<br>
	 * The Itemid is an integer = the index of the string in the sequence ,
	 * starting from 0
	 * 
	 * @param as
	 *            component
	 * @param s
	 *            comma separated string list or array of strings
	 */
	static public void populateWIds(AbstractSelect as, String... s) {
		int i = 0;
		for (String sp : s) {
			as.addItem(i);
			as.setItemCaption(i, sp);
			i++;
		}
		as.setItemCaptionMode(ItemCaptionMode.EXPLICIT_DEFAULTS_ID);
	}

	public static void populateWIdsSkip(AbstractSelect as, String[] s,
			Integer[] skip) {

		Set<Integer> se = new HashSet<Integer>(Arrays.asList(skip));

		int i = 0;
		for (String sp : s) {
			if (!se.contains(i)) {
				as.addItem(i);
				as.setItemCaption(i, sp);
			}
			i++;
		}
		as.setItemCaptionMode(ItemCaptionMode.EXPLICIT_DEFAULTS_ID);

	}

}
