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

package com.vaadHL.window.customize;

/**
 * Customization of a form editing window.
 * @author 
 *
 */
public interface ICustomizeFWin extends ICustomizeEditWin {

	/**
	 * Whether to show the OK & Cancel buttons. If not, the close button is shown instead.
	 * @return true = show
	 */
	boolean isShowOKCancel();
}
