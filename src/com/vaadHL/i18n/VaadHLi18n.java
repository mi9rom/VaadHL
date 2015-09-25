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

import java.io.UnsupportedEncodingException;
import java.util.ArrayDeque;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.vaadin.data.Property;
import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents;

/**
 * Internationalization support. Base implementation of the {@link I18Sup}
 * interface.<br>
 * Attention: Assuming resources in UTF-8
 */
public class VaadHLi18n implements I18Sup {

	protected Locale locale;
	private ArrayDeque<ResourceBundle> bundle;
	private ArrayDeque<String> sources;

	public VaadHLi18n() {
		super();

		sources = new ArrayDeque<String>();
	}

	public VaadHLi18n(Locale locale) {
		super();
		setLocale(locale);
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Gets a bundle using the base name "src".<br>
	 * Tries to load the bundle using the set locale, if unable tries to load
	 * the default bundle.
	 * 
	 * @param src
	 * @return
	 */
	ResourceBundle getBungle(String src) throws MissingResourceException {
		try {
			return (ResourceBundle.getBundle(src, locale));

		} catch (MissingResourceException e) {
		}

		return (ResourceBundle.getBundle(src));
	}

	@Override
	public void setLocale(Locale locale) {

		bundle = new ArrayDeque<ResourceBundle>();
		this.locale = locale;

		for (String src : sources) {
			try {
				bundle.addLast(getBungle(src));
				continue;
			} catch (MissingResourceException e) {
			}
		}
	}

	/**
	 * Adds a resource bundle using the base name "src" on top of the bundle
	 * list .<br>
	 * Tries to load the bundle using the set locale, if unable tries to load
	 * the default bundle, when fails throws the MissingResourceException.
	 * 
	 * @param src
	 *            the base name
	 */
	public void addBundleSourceF(String src) throws MissingResourceException {
		if (locale != null)
			bundle.addFirst(getBungle(src));
		sources.addFirst(src);
	}

	/**
	 * Gets a national representation of the string for the set locale. <br>
	 * In case the code "name" cannot be found throws the
	 * MissingResourceException.<br>
	 * Iterates through the list of bundles and returns the first found (even it
	 * is a default bundle).
	 * 
	 * @param name
	 *            the string code
	 * 
	 * @throws MissingResourceException
	 */
	@Override
	public String getString(String name) throws MissingResourceException {
		String s = null;
		MissingResourceException er = null;
		for (ResourceBundle rb : bundle) {
			try {
				s = rb.getString(name);
				break;
			} catch (MissingResourceException e) {
				er = e;
			}

		}

		if (s == null) {
			throw er;
		}

		try {
			return new String(s.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return s;
		}
	}

	/**
	 * Gets a national representation of the string array for the set locale. <br>
	 * In case the code "name" cannot be found throws the
	 * MissingResourceException.<br>
	 * Iterates through the list of bundles and returns the first found (even it
	 * is a default bundle).
	 * 
	 * @param name
	 *            the string code
	 * 
	 * @throws MissingResourceException
	 */
	@Override
	public String[] getArryString(String name) throws MissingResourceException {
		String s = getString(name);
		return s.split(",");
	}

	/**
	 * Gets a national representation of the string for the set locale. <br>
	 * In case the code "name" cannot be found returns <"?" + name + "?">.
	 * Iterates through the list of bundles and returns the first found (even it
	 * is a default bundle).
	 * 
	 * @param name
	 *            the string code
	 * 
	 * @throws MissingResourceException
	 */
	@Override
	public String getStringNE(String name) {
		try {
			return getString(name);
		} catch (MissingResourceException e) {
			return "?" + name + "?";
		}
	}

	protected String prefix = "<!--";
	protected String suffix = "-->";

	// TODO use semantic search
	String valueI18(Component child, String varName) {
		String pref = prefix + varName + "=";
		String s = child.getDescription();

		return null;
	}

	@SuppressWarnings("unchecked")
	public void changeAll(HasComponents root) {
		for (Component child : root) {
			if (child instanceof HasComponents) {
				changeAll((HasComponents) child);
			} else {
				String s;
				s = valueI18(child, "caption");
				if (s != null)
					child.setCaption(s);

				if (child instanceof Property) {
					s = valueI18(child, "value");
					if (s != null)
						((Property<String>) child).setValue(s);
				}

			}
		}
	}
}