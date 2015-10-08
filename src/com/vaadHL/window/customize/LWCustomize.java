package com.vaadHL.window.customize;


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

/**
 * Simple implementation of the {@link ICustomizeLWMultiMode} list window
 * customization interface.
 * 
 * @author Miroslaw Romaniuk
 *
 */

public class LWCustomize implements ICustomizeLWMultiMode {

	LWCustomizeLM choose = null;
	LWCustomizeLM noChoose = null;

	public LWCustomize() {
		super();
		choose = new LWCustomizeLM();
		noChoose = new LWCustomizeLM();
	}

	public LWCustomize(LWCustomizeLM choose, LWCustomizeLM noChoose) {
		super();
		this.choose = choose;
		this.noChoose = noChoose;
	}

	/**
	 * @return List window customization in the choose launch mode.
	 */
	public ICustomizeListWindow getChooseMode() {
		return choose;
	}

	/**
	 * @return List window customization in the no-choose launch mode.
	 */
	public ICustomizeListWindow getNoChooseMode() {
		return noChoose;
	}

}
