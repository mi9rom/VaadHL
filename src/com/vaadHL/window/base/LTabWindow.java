package com.vaadHL.window.base;

import java.util.Set;

import com.vaadHL.AppContext;
import com.vaadHL.utl.helper.TableHelper;
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
	protected TableHelper tableHelper;

	public LTabWindow(String winId, String caption,
			IWinPermChecker permChecker, ICustomizeLWMultiMode customize,
			ChoosingMode chooseMode, boolean readOnly, AppContext appContext) {
		super(winId, caption, permChecker, customize, chooseMode, readOnly,
				appContext);

		if (approvedToOpen == false)
			return;

		if (table == null) {
			throw new RuntimeException("VHL-019: " + getI18S("MVHL-019"));
		}

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
	protected Object getCallFormSelIdMsg(Object mRowId) {
		Object rowId = null;

		if (mRowId == null)
			rowId = table.getValue();
		else
			rowId = mRowId;

		if (rowId == null) {
			getMsgs().showInfo("VHL-011: " + getI18S("MVHL-011"));
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
	public void closeCancel() {
		closeCause = new CloseCause(CloseCauseEnum.CANCEL,
				tableHelper.getSelectedItems());
		super.closeCancel();
	}

	@Override
	public void closeChoose() {
		Object selection = tableHelper.getSelectedItems();
		if (selection == null) {
			getMsgs().showInfo("VHL-009: " + getI18S("MVHL-011"));
			return;
		}
		closeCause = new CloseCause(CloseCauseEnum.CHOOSE, selection);
		super.closeChoose();
	}

	@Override
	public void closeExit() {
		closeCause = new CloseCause(CloseCauseEnum.NOCHOOSE,
				tableHelper.getSelectedItems());
		super.closeExit();
	}

}
