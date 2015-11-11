package com.ezee.client.grid.payee;

import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.update;

import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.client.crud.payee.EzeeCreateUpdateDeletePayee;
import com.ezee.client.grid.EzeeFinancialEntityGrid;
import com.ezee.client.grid.EzeeFinancialEntityToolbar;
import com.ezee.client.grid.invoice.EzeeInvoiceGrid;
import com.ezee.model.entity.EzeePayee;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;

/**
 * 
 * @author siborg
 *
 */
public class EzeePayeeGrid extends EzeeFinancialEntityGrid<EzeePayee> {

	private final EzeeInvoiceGrid invoiceGrid;

	public EzeePayeeGrid(final EzeeInvoiceEntityCache cache, final EzeeInvoiceGrid invoiceGrid) {
		super(cache);
		this.invoiceGrid = invoiceGrid;
	}

	protected void initGrid() {
		super.initGrid();
		model = new EzeePayeeGridModel();
		model.bind(grid);
	}

	@Override
	protected void initFilter() {
		super.initFilter();
		toolBar = new EzeeFinancialEntityToolbar<EzeePayee>(this);
		filterpanel.add(toolBar);
	}

	@Override
	public String getGridClass() {
		return EzeePayee.class.getName();
	}

	@Override
	public void deleteEntity() {
		EzeePayee entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePayee(cache, this, entity, delete).show();
		}
	}

	@Override
	public void newEntity() {
		new EzeeCreateUpdateDeletePayee(cache, this).show();
	}

	@Override
	public void editEntity() {
		EzeePayee entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePayee(cache, this, entity, update).show();
		}
	}

	public void newSupplierInvoice() {
		EzeePayee entity = getSelected();
		if (entity != null && invoiceGrid != null) {
			invoiceGrid.newSupplierInvoice(entity.getName());
		}
	}

	protected MenuBar createContextMenu() {
		MenuBar menu = super.createContextMenu();
		menu.addSeparator();
		menu.addItem("Raise Invoice", new Command() {
			@Override
			public void execute() {
				newSupplierInvoice();
				contextMenu.hide();
			}
		});
		return menu;
	}
}