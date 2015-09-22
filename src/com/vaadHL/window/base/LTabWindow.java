package com.vaadHL.window.base;

import java.util.Set;

import com.vaadHL.utl.helper.TableHelper;
import com.vaadHL.utl.msgs.IMsgs;
import com.vaadHL.window.base.perm.IWinPermChecker;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Table;

/**
 * List window based on a table.<br>
 * = {@link LWindow} + table support
 * 
 * @author Miroslaw Romaniuk
 *
 */
public class LTabWindow extends LWindow {
	private static final long serialVersionUID = 9053183490003952417L;
	protected Table table;
	private IListSelectionAction selAction;
	protected TableHelper tableHelper;

	public LTabWindow(String winId, String caption,
			IWinPermChecker permChecker, ICustomizeLWMultiMode customize,
			ChoosingMode chooseMode, boolean readOnly, IMsgs msgs,
			IListSelectionAction selAction) {
		super(winId, caption, permChecker, customize, chooseMode, readOnly,
				msgs);

		if (approvedToOpen == false)
			return;

		if (table == null) {
			throw new RuntimeException(
					"LWindow.initConstructorWidgets() must be overrriden and the field \"table\" instantiated inside it.");
		}
		this.selAction = selAction;
		tableHelper = new TableHelper(table, getMsgs());
		table.addItemClickListener(new ItemClickListener() {
			private static final long serialVersionUID = 881449509470779229L;

			@Override
			public void itemClick(ItemClickEvent event) {
				onTableCLick(event);
			}
		});

	}

	void onTableCLick(ItemClickEvent event) {
		if (event.isDoubleClick()) {
			onTableDoubleCLick(event);
		}
	}

	void onTableDoubleCLick(ItemClickEvent event) {
		switch (getDoubleClickAc()) {
		case DETAILS:
			details(event.getItemId());
			break;
		case VIEW:
			view(event.getItemId());
			break;
		case EDIT:
			edit(event.getItemId());
			break;
		case DELETE:
			delete(event.getItemId());
			break;
		case CREATE:
			add();
			break;
		case CHOOSE:
			if (getChooseMode() == ChoosingMode.NO_CHOOSE)
				closeExit();
			else {
				// if (getChooseMode() == ChoosingMode.SINGLE_CHOOSE)
				table.select(event.getItemId());
				closeChoose();
			}
			break;

		default:
			break;

		}

	}

	@Override
	public void closeCancel() {
		super.closeCancel();
		if (selAction != null)
			selAction.Cancel(null);

	}

	@Override
	protected Object getReturnSelection() {
		return tableHelper.getSelectedItems();
	}

	@Override
	protected Object getCallFormSelIdMsg(Object mRowId) {
		Object rowId = null;

		if (mRowId == null)
			rowId = table.getValue();
		else
			rowId = mRowId;

		if (rowId == null) {
			getMsgs().showInfo("VHL-011: No item selected.");
			return null;
		}

		if (rowId instanceof Set) {
			if (((Set<?>) rowId).size() != 1) {
				getMsgs().showInfo(
						"VHL-012: Only single selection is permitted.");
				return null;
			} else {
				rowId = (((Set<?>) rowId).toArray())[0];
			}
		}

		return rowId;
	}

	@Override
	public void closeChoose() {
		Object selection = getReturnSelection();
		if (selection == null) {
			getMsgs().showInfo("VHL-009: No item selected!");
		}
		if (selection == null) {
			return;
		}
		super.closeChoose();
		if (selAction != null)
			selAction.Confirm(selection);

	}

	@Override
	public void closeExit() {
		super.closeExit();
		if (selAction != null)
			selAction.Exit(null);
	}

}
