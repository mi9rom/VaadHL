package com.vaadHL.window.base;

import java.util.Set;

import com.vaadHL.IAppContext;
import com.vaadHL.utl.helper.TableHelper;
import com.vaadHL.utl.helper.TableState;
import com.vaadHL.utl.state.VHLSortState;
import com.vaadHL.utl.state.VHLState;
import com.vaadHL.window.EM.SingIeItemFWindow;
import com.vaadHL.window.base.perm.IWinPermChecker;
import com.vaadHL.window.customize.ICustomizeLWMultiMode;
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
			IWinPermChecker masterPermChecker, ChoosingMode chooseMode,
			boolean readOnly, IAppContext appContext,ICustomizeLWMultiMode forceCustomize) {
		super(winId, caption, masterPermChecker, chooseMode, readOnly,
				appContext, forceCustomize);

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

		makeContextMenu().setAsContextMenuOf(table);
	}

	protected void onTableCLick(ItemClickEvent event) {
		if (event.isDoubleClick()) {
			onTableDoubleCLick(event);
		}
	}

	protected void onTableDoubleCLick(ItemClickEvent event) {
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
				getMsgs().showInfo("VHL-012:" + getI18S("MVHL-012"));
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

	@Override
	protected void afterFormClosed(BaseWindow win) {
		if (win instanceof SingIeItemFWindow) {
			Object id = ((SingIeItemFWindow) win).getCurItId();
			table.setCurrentPageFirstItemId(id);
			if (getChooseMode() != ChoosingMode.MULTIPLE_CHOOSE)
				table.setValue(id);
		}
		super.afterFormClosed(win);
	}

	@Override
	public void refresh() {
		/*
		 * Attention: this doesn't refresh any container. Refresh container if ,
		 * for instance, you would like to reload data from a database .
		 */
		table.refreshRowCache();
	}

	@Override
	public void deselectAll() {
		table.setValue(null);
	}

	// ------- State handling -------

	/**
	 * Table state (without sorting and filtering) + ancestor state
	 * 
	 *
	 */
	public class LTabWinState extends VHLState {
		private static final long serialVersionUID = -1970124421143425782L;
		TableState tableState;
		VHLState acestorState;

		public LTabWinState(TableState tableState, VHLState belowState) {
			super(1);
			this.tableState = tableState;
			this.acestorState = belowState;
		}

		public TableState getTableState() {
			return tableState;
		}

		public void setTableState(TableState tableState) {
			this.tableState = tableState;
		}

		public VHLState getAncestorState() {
			return acestorState;
		}

		public void setAncestorState(VHLState belowState) {
			this.acestorState = belowState;
		}

	}

	@Override
	public VHLState getVHLState() {
		try {
			return new LTabWinState(new TableState(table), super.getVHLState());
		} catch (Exception e) {
			getMsgs().showError("VHL-026", e);
			return null;
		}
	}

	@Override
	public void setVHLState(VHLState state) {
		if (state == null)
			return;
		try {
			LTabWinState s = (LTabWinState) state;
			super.setVHLState(s.getAncestorState());
			s.getTableState().applyTo(table);
		} catch (Exception e) {
			getMsgs().showError("VHL-022", e);
		}
	}

	/**
	 * Sorting settings for a table
	 *
	 */
	public class LTWSortState extends VHLSortState {
		Object column;
		boolean ascending;

		public LTWSortState(Object column, boolean ascending) {
			super();
			this.column = column;
			this.ascending = ascending;
		}

		public Object getColumn() {
			return column;
		}

		public void setColumn(Object column) {
			this.column = column;
		}

		public boolean isAscending() {
			return ascending;
		}

		public void setAscending(boolean ascending) {
			this.ascending = ascending;
		}

		private static final long serialVersionUID = 7655038815071638086L;

	}

	@Override
	public VHLSortState getSorting() {
		return new LTWSortState(table.getSortContainerPropertyId(),
				table.isSortAscending());
	}

	@Override
	public void setSorting(VHLSortState sorting) {
		if (sorting == null) {
			return;
		}
		LTWSortState so = (LTWSortState) sorting;
		table.setSortContainerPropertyId(so.getColumn());
		table.setSortAscending(so.isAscending());
	}

}
