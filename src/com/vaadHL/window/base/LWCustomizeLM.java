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

package com.vaadHL.window.base;

/**
 * Simple implementation of the {@link ICustomizeListWindow} list window
 * customization interface for the particular launch mode.
 * 
 * @author Miroslaw Romaniuk
 *
 */
public class LWCustomizeLM implements ICustomizeListWindow {

	/**
	 * Show the Details button
	 */
	boolean detailsFunc = true;
	/**
	 * Show the Add button
	 */
	boolean addFunc = true;
	/**
	 * Show the delete button
	 */
	boolean deleteFunc = true;
	/**
	 * Show the edit button
	 */
	boolean editFunc = true;
	/**
	 * Show the View button
	 */
	boolean viewFunc = true;

	DoubleClickAc doubleClickAc = DoubleClickAc.CHOOSE;

	public LWCustomizeLM() {

	}

	public LWCustomizeLM(boolean details, boolean add, boolean delete,
			boolean edit, boolean view, DoubleClickAc doubleClickAc) {
		super();
		this.detailsFunc = details;
		this.addFunc = add;
		this.deleteFunc = delete;
		this.editFunc = edit;
		this.viewFunc = view;
		this.doubleClickAc = doubleClickAc;
	}

	public void fullFeatures() {
		detailsFunc = true;
		addFunc = true;
		deleteFunc = true;
		editFunc = true;
		viewFunc = true;
	}

	public void noFeatures() {
		detailsFunc = false;
		addFunc = false;
		deleteFunc = false;
		editFunc = false;
		viewFunc = false;
	}

	public void listOnly() {
		noFeatures();
	}

	public void detailsOnly() {
		noFeatures();
		detailsFunc = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadHL.window.base.ILWLaunchMode#isDetails()
	 */
	@Override
	public boolean isDetailsFunc() {
		return detailsFunc;
	}

	public void setDetailsFunc(boolean details) {
		this.detailsFunc = details;
	}

	@Override
	public boolean isAddFunc() {
		return addFunc;
	}

	public void setAddFunc(boolean addRec) {
		this.addFunc = addRec;
	}

	@Override
	public boolean isDeleteFunc() {
		return deleteFunc;
	}

	public void setDeleteFunc(boolean deleteRec) {
		this.deleteFunc = deleteRec;
	}

	@Override
	public boolean isViewFunc() {
		return viewFunc;
	}

	public void setViewFunc(boolean view) {
		this.viewFunc = view;
	}

	@Override
	public boolean isEditFunc() {
		return editFunc;
	}

	public void setEditFunc(boolean edit) {
		this.editFunc = edit;
	}

	public DoubleClickAc getDoubleClickAc() {
		return doubleClickAc;
	}

	public void setDoubleClickAc(DoubleClickAc doubleClickAc) {
		this.doubleClickAc = doubleClickAc;
	}

}
