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

package com.vaadHL.utl.converter;

import java.text.NumberFormat;
import java.util.Locale;

import com.vaadin.data.util.converter.StringToIntegerConverter;

public class StringToPlainIntegerConverter extends StringToIntegerConverter {
	private static final long serialVersionUID = -1063446223387317845L;

	protected java.text.NumberFormat getFormat(Locale locale) {
		NumberFormat format = super.getFormat(locale);
		format.setGroupingUsed(false);
		return format;
	}
}
