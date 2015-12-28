package com.ezee.client.grid.supplier;

import com.ezee.client.grid.invoice.EzeeInvoiceGrid;
import com.ezee.model.entity.EzeePayee;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.grid.supplier.EzeeSupplierGrid;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceSupplierGrid extends EzeeSupplierGrid {

	private final EzeeInvoiceGrid invoiceGrid;

	public EzeeInvoiceSupplierGrid(final EzeeEntityCache cache, final String[] crudHeaders,
			final EzeeInvoiceGrid invoiceGrid) {
		super(cache, crudHeaders);
		this.invoiceGrid = invoiceGrid;
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