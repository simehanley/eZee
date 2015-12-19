package com.ezee.client;

import static com.ezee.client.EzeeInvoiceWebConstants.INVOICES;
import static com.ezee.client.EzeeInvoiceWebConstants.LOGIN_USER;
import static com.ezee.client.EzeeInvoiceWebConstants.PAYMENTS;
import static com.ezee.client.EzeeInvoiceWebConstants.PREMISES;
import static com.ezee.client.EzeeInvoiceWebConstants.PREMISES_CRUD_HEADERS;
import static com.ezee.client.EzeeInvoiceWebConstants.REGISTER_USER;
import static com.ezee.client.EzeeInvoiceWebConstants.SUPPLIERS;
import static com.ezee.client.EzeeInvoiceWebConstants.SUPPLIER_CRUD_HEADERS;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.grid.invoice.EzeeInvoiceGrid;
import com.ezee.client.grid.payee.EzeeInvoicePayeeGrid;
import com.ezee.client.grid.payment.EzeePaymentGrid;
import com.ezee.client.main.EzeeInvoiceMain;
import com.ezee.model.entity.EzeePayee;
import com.ezee.web.common.ui.entrypoint.EzeeWebEntryPoint;
import com.ezee.web.common.ui.grid.payee.EzeePayeeGrid;
import com.ezee.web.common.ui.grid.payer.EzeePayerGrid;
import com.ezee.web.common.ui.main.EzeeWebMain;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceWeb extends EzeeWebEntryPoint {

	private static final Logger log = Logger.getLogger("EzeeInvoiceWeb");

	public EzeeInvoiceWeb() {
		super(LOGIN_USER, REGISTER_USER);
	}

	@Override
	protected void initApplication() {
		initCache();
		initMain();
	}

	private void initMain() {
		log.log(Level.INFO, "Initialising application.");
		EzeeWebMain main = new EzeeInvoiceMain(user, cache);
		EzeeInvoiceGrid invoice = createInvoiceGrid();
		EzeePaymentGrid payment = createPaymentGrid();
		invoice.setListener(payment);
		payment.setListener(invoice);
		main.getTab().add(invoice, INVOICES);
		main.getTab().add(payment, PAYMENTS);
		main.getTab().add(createSupplierGrid(invoice), SUPPLIERS);
		main.getTab().add(createPremisesGrid(), PREMISES);
		RootLayoutPanel.get().add(main);
		log.log(Level.INFO, "Application initialised.");
	}

	private EzeePayeeGrid<EzeePayee> createSupplierGrid(final EzeeInvoiceGrid invoice) {
		return new EzeeInvoicePayeeGrid(cache, SUPPLIER_CRUD_HEADERS, invoice);
	}

	private EzeePayerGrid createPremisesGrid() {
		return new EzeePayerGrid(cache, PREMISES_CRUD_HEADERS);
	}

	private EzeeInvoiceGrid createInvoiceGrid() {
		return new EzeeInvoiceGrid(cache);
	}

	private EzeePaymentGrid createPaymentGrid() {
		return new EzeePaymentGrid(cache);
	}
}