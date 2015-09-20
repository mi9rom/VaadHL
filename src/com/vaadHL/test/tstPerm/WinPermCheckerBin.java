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

package com.vaadHL.test.tstPerm;

import java.util.Set;

import com.vaadHL.window.base.perm.IWinPermChecker;
/**
 * Mock single window permission container class 
 * @author Miroslaw Romaniuk
 *
 */
public class WinPermCheckerBin  {

	

	boolean canOpen = false;
	boolean canEdit = false;
	boolean canCreate = false;
	boolean canDelete = false;
	// canDo testing
	boolean canDo = false; 
	public final static String canDoId = "CANDO";

	public void fromSet(Set<Integer> s) {
		canOpen = false;
		canEdit = false;
		canCreate = false;
		canDelete = false;
		canDo = false; 

		for (Object n : s) {
			int iv = (int) n;
			switch (iv) {
			case 0:
				canOpen = true;
				break;
			case 1:
				canEdit = true;
				break;
			case 2:
				canCreate = true;
				break;
			case 3:
				canDelete = true;
				break;
			case 4:
				canDo = true;
				break;
			default:
				break;
			}
		}
	}

	public WinPermCheckerBin(boolean canOpen, boolean canEdit, boolean canCreate,
			boolean canDelete) {
		super();
		this.canOpen = canOpen;
		this.canEdit = canEdit;
		this.canCreate = canCreate;
		this.canDelete = canDelete;
	}

	public WinPermCheckerBin() {
		
	}

	public boolean isCanOpen() {
		return canOpen;
	}

	public void setCanOpen(boolean canOpen) {
		this.canOpen = canOpen;
	}

	public boolean isCanEdit() {
		return canEdit;
	}

	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}

	public boolean isCanCreate() {
		return canCreate;
	}

	public void setCanCreate(boolean canCreate) {
		this.canCreate = canCreate;
	}

	public boolean isCanDelete() {
		return canDelete;
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

	public boolean isCanDo() {
		return canDo;
	}

	public void setCanDo(boolean canDo) {
		this.canDo = canDo;
	}
}
