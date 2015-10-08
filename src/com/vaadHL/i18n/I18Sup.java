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

package com.vaadHL.i18n;

import java.util.Locale;
import java.util.MissingResourceException;

import com.vaadin.ui.HasComponents;

/**
 * Internationalization support.
 *
 */
public interface I18Sup {

	/**
	 * Sets the locale used by other functions.
	 * 
	 * @param locale
	 */
	public void setLocale(Locale locale);

	/**
	 * Gets the locale used by other functions. If the locale is not set returns null.
	 */
	public Locale getLocale();

	/**
	 * Gets a national representation of the string for the set locale. <br>
	 * In case the code "name" cannot be found throws the exception.
	 * 
	 * @param name
	 *            the string code
	 * 
	 * @throws MissingResourceException
	 */
	public String getString(String name) throws MissingResourceException;

	/**
	 * Gets a national representation of the string for the set locale. <br>
	 * In case the code "name" cannot be found returns a string.
	 * 
	 * @param name
	 *            the string code
	 * 
	 * @throws MissingResourceException
	 */
	public String getStringNE(String name);

	/**
	 * Gets a national representation of the string array for the set locale. <br>
	 * In case the code "name" cannot be found throws the exception.
	 * 
	 * @param name
	 *            the string code
	 * 
	 * @throws MissingResourceException
	 */
	String[] getArryString(String name) throws MissingResourceException;

}
