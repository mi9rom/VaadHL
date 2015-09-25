package com.vaadHL.i18n;

import java.util.Locale;

/**
 * Internationalization support using default (included) library resources. <br>
 * 
 * Attention: Assuming resources in UTF-8
 */
public class VaadHL18Def extends VaadHLi18n {

	public static String source() {
		return VaadHL18Def.class.getPackage().getName() + ".Strings";
	}

	static volatile VaadHL18Def singleton = null;

	static synchronized VaadHL18Def getVaadHL18() {
		if (singleton != null)
			return singleton;
		else
			return new VaadHL18Def();
	}

	public VaadHL18Def() {
		super();
		addBundleSourceF(source());
	}

	public VaadHL18Def(Locale locale) {
		super();
		addBundleSourceF(source());
		setLocale(locale);
	}

}
