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

import org.vaadin.dialogs.ConfirmDialog;

import com.vaadHL.IAppContext;
import com.vaadHL.utl.action.Action;
import com.vaadHL.utl.action.Action.Command;
import com.vaadHL.utl.action.ActionGroup;
import com.vaadHL.utl.action.ActionsIds;
import com.vaadHL.window.base.perm.IWinPermChecker;
import com.vaadHL.window.customize.ICustomizeEditWin;
import com.vaadHL.window.customize.ICustomizeEditWin.AutoSaveDiscard;

/**
 * Base editing form window.
 * 
 * @author Miroslaw Romaniuk
 *
 */
public abstract class BaseEditWindow extends BaseWindow {

	private static final long serialVersionUID = 3045993334696013902L;

	private MWLaunchMode launchMode = null;
	private MWLaunchMode curWinMode = null;
	private boolean editingMode = false;
	private boolean readOnlyWin;
	private ICustomizeEditWin customize;
	protected ActionGroup crudActions;

	public BaseEditWindow(String winId, String caption,
			IWinPermChecker masterPermChecker, ICustomizeEditWin customize,
			MWLaunchMode launchMode, IAppContext appContext, boolean readOnlyW) {
		super(winId, caption, customize, masterPermChecker, appContext);
		if (!approvedToOpen)
			return;
		this.customize = customize;
		this.setReadOnlyWin(readOnlyW);
		curWinMode = this.launchMode = launchMode;

		if (isReadOnlyWin()) {
			if (launchMode == MWLaunchMode.VIEW_ONLY
					|| launchMode == MWLaunchMode.EDIT
					|| launchMode == MWLaunchMode.VIEW_EDIT) {
				curWinMode = this.launchMode = MWLaunchMode.VIEW_ONLY;

			} else {
				approvedToOpen = false;
				setNotPermitedContent(getWinId() + "VHL-015: "
						+ getI18S("MVHL-015"));
				return;
			}

			if (isReadOnlyWin())
				crudActions.setEnabled(false);
		}

		if (this.launchMode == MWLaunchMode.EDIT && !hasEditPerm()) {
			approvedToOpen = false;
			setNotPermitedContent(getWinId() + "VHL-016: "
					+ getI18S("MVHL-016"));
			return;
		} else if (this.launchMode == MWLaunchMode.DELETE && !hasDeletePerm()) {
			approvedToOpen = false;
			setNotPermitedContent(getWinId() + "VHL-017: "
					+ getI18S("MVHL-017"));
			return;
		} else if (this.launchMode == MWLaunchMode.NEW_REC && !hasCreatePerm()) {
			approvedToOpen = false;
			setNotPermitedContent(getWinId() + "VHL-018: "
					+ getI18S("MVHL-018"));
			return;
		}
	}

	@Override
	protected void createActions() {
		super.createActions();

		ActionGroup agCommit = new ActionGroup(ActionsIds.GAC_COMMIT);

		agCommit.put(new Action(getAppContext(),
				ActionsIds.AC_COMMIT_AND_CLOSE, new Command() {
					@Override
					public void run(Action action) {
						commitAndClose();
					}
				}, true));

		agCommit.put(new Action(getAppContext(),
				ActionsIds.AC_CANCEL_AND_CLOSE, new Command() {
					@Override
					public void run(Action action) {
						cancelAndClose();
					}
				}, true));

		agCommit.put(new Action(getAppContext(), ActionsIds.AC_SAVE_ASK_MSG,
				new Command() {
					@Override
					public void run(Action action) {
						saveAskMsg(null);
					}
				}, true));

		agCommit.put(new Action(getAppContext(), ActionsIds.AC_DISCARD_ASK_MSG,
				new Command() {
					@Override
					public void run(Action action) {
						discardAskMsg(null);
					}
				}, true));

		addActionsAndChkPerm(agCommit);

		ActionGroup newActions = new ActionGroup(ActionsIds.AC_BASE_EDIT_WIN);

		newActions.put(new Action(getAppContext(), ActionsIds.AC_DELETE,
				new Command() {

					@Override
					public void run(Action action) {
						deleteAskMsg();
					}
				}));
		newActions.put(new Action(getAppContext(), ActionsIds.AC_CREATE,
				new Command() {

					@Override
					public void run(Action action) {
						createAskMsg();
					}
				}));
		newActions.put(new Action(getAppContext(), ActionsIds.AC_EDIT,
				new Command() {

					@Override
					public void run(Action action) {
						toggleEditing();
					}
				}));

		crudActions = newActions;

		addActionsAndChkPerm(newActions);
	}

	// ---------------- Window scope permission checking ------------

	public boolean hasEditPerm() {
		if (launchMode == MWLaunchMode.VIEW_ONLY)
			return false;
		if (permChecker != null)
			return (permChecker.canEdit());
		return true;
	}

	public boolean hasDeletePerm() {
		if (launchMode == MWLaunchMode.VIEW_ONLY)
			return false;
		if (permChecker != null)
			return (permChecker.canDelete());
		return true;
	}

	public boolean hasCreatePerm() {
		if (launchMode == MWLaunchMode.VIEW_ONLY)
			return false;
		if (permChecker != null)
			return (permChecker.canCreate());
		return true;
	}

	// ---------------- change checking ------------

	/**
	 * Checks if the content is not modified , and in the case it is, shows the
	 * message to save or discard changes.
	 * 
	 * @return true - there is no modification
	 */
	public boolean checkModifiedMsg() {
		if (isModified()) {
			showSaveDiscardMsg();
			return false;
		} else
			return true;
	}

	/**
	 * Checks the content modification state.
	 * 
	 * @return true - there is at least one modification
	 */
	public abstract boolean isModified();

	// ---------------- save , discard handling ------------
	/**
	 * Saves changes without any checks.
	 * 
	 * @throws Exception
	 */
	abstract protected void save() throws Exception;

	/**
	 * Validates the content before saving changes.<br>
	 * Show a message or inform a user other way.
	 * 
	 * @param showMessages
	 *            do show validation messages
	 * @return true - OK
	 */
	public boolean validateSave(boolean showMessages) {
		return true;
	}

	/**
	 * Saves changes with prior validation.
	 * 
	 * @return true - saved
	 */
	public boolean saveChk() {
		if (!validateSave(true))
			return false;

		return saveEInt();

	}

	/**
	 * Saves changes with exception interception. In case of error shows the
	 * message.
	 * 
	 * @return true - saved
	 */
	public boolean saveEInt() {
		try {
			save();
			return true;
		} catch (RuntimeException re) {
			if (re.getCause() instanceof javax.persistence.OptimisticLockException
					|| re.getCause() instanceof org.eclipse.persistence.exceptions.OptimisticLockException
					|| re.getCause() instanceof com.vaadin.data.util.sqlcontainer.OptimisticLockException)
				getMsgs().showError("VHL-005: " + getI18S("MVHL-005"));
			else
				getMsgs().showError("VHL-006", re);
			return false;
		} catch (Exception e) {
			getMsgs().showError("VHL-007", e);
			return false;
		}
	}

	/**
	 * Validates and saves changes with prior asking depending on customization
	 * parameters.
	 * 
	 * @param actionClose
	 *            the action to perform after successful saving. null - do
	 *            nothing
	 */
	public void saveAskMsg(final Runnable actionClose) {
		if (!validateSave(true))
			return;
		if (isModified() && isAskSave()) {

			ConfirmDialog.show(getUI(), getI18S("SaveQ"),
					getI18S("Save_changesQ"), getI18S("btYes"),
					getI18S("btNo"), new Runnable() {

						@Override
						public void run() {
							if (saveChk())
								if (actionClose != null)
									actionClose.run();
						}
					});
		} else if (saveEInt())
			if (actionClose != null)
				actionClose.run();
	}

	/**
	 * Discards changes without any checks.
	 */
	protected void discard() throws Exception {
		if (launchMode == MWLaunchMode.NEW_REC)
			close();
	}

	/**
	 * Discards changes with exception interception. In case of error show the
	 * message.
	 * 
	 * @return true - saved
	 */
	public boolean discardEInt() {
		try {
			discard();
			return true;
		} catch (Exception e) {
			getMsgs().showError("E001,e");
			return false;
		}
	}

	/**
	 * Validates the content before discarding changes.
	 * 
	 * @return true - OK
	 */
	public boolean validateDiscard(boolean showMessages) {
		return true;
	}

	/**
	 * Discards changes with prior validation. In case of error show the
	 * message.
	 * 
	 * @return true - discarded
	 */
	public boolean discardChk() {
		if (validateDiscard(true)) {
			return discardEInt();

		} else
			return false;

	}

	/**
	 * Validates and discards changes with behaviour depending on customization
	 * parameters.
	 * 
	 * @param actionClose
	 *            the action to perform after successful discarding. null - do
	 *            nothing
	 */
	public void discardAskMsg(final Runnable actionClose) {
		if (!validateDiscard(true))
			return;

		if (isModified() && isAskDiscard()) {
			ConfirmDialog.show(getUI(), getI18S("DiscardQ"),
					getI18S("Discard_changesQ"), getI18S("btYes"),
					getI18S("btNo"), new Runnable() {

						@Override
						public void run() {
							if (discardChk())
								if (actionClose != null)
									actionClose.run();
						}
					});
		} else if (discardChk())
			if (actionClose != null)
				actionClose.run();
	}

	/**
	 * Validates, next saves or discards changes depending on customization
	 * parameters.
	 * 
	 * @param actionClose
	 *            the action to perform after successful saving or discarding.
	 *            null - do nothing
	 */
	protected void saveDiscardAction(final Runnable actionClose) {

		if (getAutoSaveDiscard() == AutoSaveDiscard.ASK) {
			ConfirmDialog.show(getUI(), getI18S("Unsaved_changes"),
					getI18S("Wtd_changes"), getI18S("btSave"),
					getI18S("btCancel"), getI18S("btDiscard"),
					new ConfirmDialog.Listener() {

						private static final long serialVersionUID = -8823586806134361654L;

						public void onClose(ConfirmDialog dialog) {
							if (dialog.isConfirmed()) {
								if (saveChk())
									actionClose.run();
							} else if (dialog.isCanceled()) {

							} else if (discardChk())
								actionClose.run();
						}
					});
			return;
		} else if (getAutoSaveDiscard() == AutoSaveDiscard.SAVE) {
			if (saveChk())
				actionClose.run();
		} else if (getAutoSaveDiscard() == AutoSaveDiscard.DISCARD) {
			if (discardChk())
				actionClose.run();
		} else {
			showSaveDiscardMsg();

			return;
		}
	}

	// ---------------- delete, create ------------

	/**
	 * Deletes the content without any checks.
	 */
	public void delete() {

	}

	/**
	 * Checks other issues than validating content before performing deletion.
	 * In case deletion is not allowed shows appropriate message.
	 * 
	 * @return TRUE - can delete
	 */
	protected boolean canDeleteMsg() {
		return true;
	}

	/**
	 * Validates and deletes the content with prior asking depending on
	 * customization parameters.
	 */
	public void deleteAskMsg() {
		if (!canDeleteMsg())
			return;
		if (isAskDelete())
			ConfirmDialog.show(getUI(), getI18S("DeleteQ"),
					getI18S("Confirm_deleteQ"), getI18S("btYes"),
					getI18S("btNo"), new Runnable() {

						@Override
						public void run() {
							deleteIfNotMod();
						}
					});
		else
			deleteIfNotMod();
	}

	/**
	 * Deletes the content if there is no modification, otherwise show the
	 * message.
	 * 
	 * @return true - deletion performed
	 */
	public boolean deleteIfNotMod() {
		if (isModified()) {
			showSaveDiscardMsg();
			return false;
		} else {
			delete();
			return true;
		}
	}

	/**
	 * Checks other issues than validating content and the form level permission
	 * before switching to the new record mode. In case creation is not allowed
	 * shows appropriate message.
	 * 
	 * @return TRUE - can edit
	 */
	protected boolean canCreateMsg() {
		return true;
	}

	/**
	 * Validates and creates the content with prior asking depending on
	 * customization parameters.
	 */
	public void createAskMsg() {
		if (!canCreateMsg())
			return;
		if (isAskCreate())
			ConfirmDialog.show(getUI(), getI18S("CreateQ"),
					getI18S("Confirm_createQ"), getI18S("btYes"),
					getI18S("btNo"), new Runnable() {

						@Override
						public void run() {
							createIfNotMod();
						}
					});
		else
			createIfNotMod();
	}

	/**
	 * Creates a new content if there is no modification, otherwise show the
	 * message.
	 * 
	 * @return true - creation performed
	 */
	public boolean createIfNotMod() {
		if (isModified()) {
			showSaveDiscardMsg();
			return false;
		} else {
			create();
			return true;
		}
	}

	/**
	 * Creates a new content ( eg. a new record).
	 */
	protected void create() {
		curWinMode = MWLaunchMode.NEW_REC;
		setEditModeChkMsg(true);
	}

	// ---------------- window closing ------------
	/**
	 * Validates and saves changes with prior asking depending on customization
	 * parameters. Next closes the window.
	 */
	public void commitAndClose() {

		saveAskMsg(new Runnable() {

			@Override
			public void run() {
				close();
			}
		});
	}

	/**
	 * Validates and discards changes with prior asking depending on
	 * customization parameters. Next closes the window.
	 */
	public void cancelAndClose() {
		if (isModified())
			discardAskMsg(new Runnable() {
				@Override
				public void run() {
					close();
				}
			});
		else
			close();
	}

	/**
	 * Closes the window with modification checking and actions depending on
	 * customization parameters.
	 */
	@Override
	public void close() {
		if (!approvedToOpen)
			super.close();
		else if (isModified()) {
			saveDiscardAction(new Runnable() {
				@Override
				public void run() {
					BaseEditWindow.super.close();
				}
			});
		} else
			super.close();
	}

	public AutoSaveDiscard getAutoSaveDiscard() {
		return customize.getAutoSaveDiscard();
	}

	public boolean isAskSave() {
		return customize.isAskSave();
	}

	public boolean isAskDiscard() {
		return customize.isAskDiscard();
	}

	public boolean isAskCreate() {
		return customize.isAskCreate();
	}

	public boolean isAskDelete() {
		return customize.isAskDelete();
	}

	public boolean isPrevNextFunc() {
		return customize.isPrevNextFunc();
	}

	// ---------------- Edit mode ------------
	/**
	 * Enables or disables editing mode. Before changing the mode necessary
	 * checks are performed.
	 * 
	 * @param editMode
	 *            TRUE - set the editing mode otherwise set the viewing mode
	 * @return TRUE - operation successful
	 */
	public boolean setEditModeChkMsg(boolean editMode) {
		if (editMode)
			return enableEditChkMsg();
		else
			return disableEdit();
	}

	/**
	 * Disables editing.
	 * 
	 * @return TRUE - edition disabled
	 */
	protected boolean disableEdit() {
		setEditingMode(false);
		return true;
	}

	/**
	 * Checks other issues than validating content and the form level permission
	 * before switching to the edit mode. In case edition is not allowed shows
	 * appropriate message.
	 * 
	 * @return TRUE - can edit
	 */
	protected boolean canEditMsg() {
		return true;
	}

	protected boolean enableEditChkMsg() {
		if (getCurWinMode() == MWLaunchMode.NEW_REC)
			return enableEdit();
		else {
			if (!canEditMsg())
				return false;
			else
				return enableEdit();
		}
	}

	/**
	 * Enables editing.
	 * 
	 * @return TRUE - edition enabled
	 */
	protected boolean enableEdit() {
		setEditingMode(true);
		return true;
	}

	/**
	 * Enables or disables editing mode. Before changing the mode necessary
	 * checks are performed. If enabling fails performs disabling.
	 * 
	 * @param editMode
	 *            TRUE - set the editing mode otherwise set the viewing mode
	 * 
	 */
	protected void setEditModeChkMsgAlways(boolean editMode) {
		if (editMode) {
			if (!setEditModeChkMsg(true))
				setEditModeChkMsg(false);
		} else
			setEditModeChkMsg(false);
	}

	/**
	 * Sets default editing state of the window. <br>
	 * <b>Attention</b>: shouldn't be called before objects binding.
	 */
	protected void setDefaultEditingMode() {

		if (launchMode == MWLaunchMode.EDIT
				|| launchMode == MWLaunchMode.NEW_REC)
			setEditModeChkMsgAlways(true);
		else
			setEditModeChkMsgAlways(false);
	}

	/**
	 * Toggles the editing state of the window.
	 */
	protected void toggleEditing() {
		setEditModeChkMsg(!isEditingMode());
	}

	// ---------------- others ------------

	/**
	 * Shows the message about to save or discard changes
	 */
	protected void showSaveDiscardMsg() {
		getMsgs().showInfo(getI18S("PlSavDisc"));
	}

	/**
	 * Returns window read-only status.
	 * 
	 * @return true - read-only
	 */
	public boolean isReadOnlyWin() {
		return readOnlyWin;
	}

	public void setReadOnlyWin(boolean readOnlyW) {
		this.readOnlyWin = readOnlyW;
	}

	public MWLaunchMode getLaunchMode() {
		return launchMode;
	}

	@SuppressWarnings("unused")
	private void setLaunchMode(MWLaunchMode launchMode) {
		this.launchMode = launchMode;
	}

	public MWLaunchMode getCurWinMode() {
		return curWinMode;
	}

	protected void setCurWinMode(MWLaunchMode curWinMode) {
		this.curWinMode = curWinMode;
	}

	public boolean isEditingMode() {
		return editingMode;
	}

	private void setEditingMode(boolean editingMode) {
		this.editingMode = editingMode;
	}

}
