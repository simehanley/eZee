package com.ezee.client.grid.payee;

import com.ezee.client.grid.invoice.EzeeInvoiceGrid;
import com.ezee.model.entity.EzeePayee;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.grid.payee.EzeePayeeGrid;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoicePayeeGrid extends EzeePayeeGrid {

	private final EzeeInvoiceGrid invoiceGrid;

	public EzeeInvoicePayeeGrid(final EzeeEntityCache cache, final String[] crudHeaders,
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