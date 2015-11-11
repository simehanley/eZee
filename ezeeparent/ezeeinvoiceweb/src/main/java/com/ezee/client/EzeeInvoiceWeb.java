package com.ezee.client;

import static com.ezee.client.EzeeInvoiceWebConstants.INVOICES;
import static com.ezee.client.EzeeInvoiceWebConstants.LOGIN_USER;
import static com.ezee.client.EzeeInvoiceWebConstants.PAYMENTS;
import static com.ezee.client.EzeeInvoiceWebConstants.PREMISES;
import static com.ezee.client.EzeeInvoiceWebConstants.REGISTER_USER;
import static com.ezee.client.EzeeInvoiceWebConstants.SUPPLIERS;
import static com.ezee.web.common.EzeeWebCommonConstants.AUTO_LOGIN_HELPER;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showDefaultCursor;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showWaitCursor;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.client.grid.invoice.EzeeInvoiceGrid;
import com.ezee.client.grid.payee.EzeePayeeGrid;
import com.ezee.client.grid.payer.EzeePayerGrid;
import com.ezee.client.grid.payment.EzeePaymentGrid;
import com.ezee.client.main.EzeeInvoiceMain;
import com.ezee.model.entity.EzeeUser;
import com.ezee.web.common.ui.css.EzeeDefaultResources;
import com.ezee.web.common.ui.css.EzeeGwtOverridesResources;
import com.ezee.web.common.ui.login.EzeeLogin;
import com.ezee.web.common.ui.login.EzeeLoginListener;
import com.ezee.web.common.ui.register.EzeeRegister;
import com.ezee.web.common.ui.register.EzeeRegisterListener;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceWeb implements EntryPoint, EzeeLoginListener, EzeeRegisterListener {

	private static final Logger log = Logger.getLogger("EzeeInvoiceWeb");

	private EzeeInvoiceEntityCache cache;

	private final EzeeLogin login = new EzeeLogin(LOGIN_USER, this, this);

	private final EzeeRegister register = new EzeeRegister(REGISTER_USER, this);

	private EzeeUser user;

	public void onModuleLoad() {
		init();
	}

	private void init() {
		initResources();
		if (AUTO_LOGIN_HELPER.doAutoLogin()) {
			showWaitCursor();
			user = AUTO_LOGIN_HELPER.getAutoLoginUser();
			initApplication();
			showDefaultCursor();
		} else {
			initLogin();
		}

	}

	private void initLogin() {
		RootLayoutPanel.get().add(login);
	}

	private void initApplication() {
		initCache();
		initMain();
	}

	private void initCache() {
		cache = new EzeeInvoiceEntityCache();
	}

	private void initResources() {
		EzeeDefaultResources.INSTANCE.css().ensureInjected();
		EzeeGwtOverridesResources.INSTANCE.dataGridStyle().ensureInjected();
	}

	private void initMain() {
		log.log(Level.INFO, "Initialising application.");
		EzeeInvoiceMain main = new EzeeInvoiceMain(user);
		EzeeInvoiceGrid invoice = createInvoiceGrid();
		EzeePaymentGrid payment = createPaymentGrid();
		invoice.setListener(payment);
		payment.setListener(invoice);
		main.getTab().add(invoice, INVOICES);
		main.getTab().add(payment, PAYMENTS);
		main.getTab().add(createPayeeGrid(invoice), SUPPLIERS);
		main.getTab().add(createPayerGrid(), PREMISES);
		RootLayoutPanel.get().add(main);
		log.log(Level.INFO, "Application initialised.");
	}

	private EzeePayeeGrid createPayeeGrid(final EzeeInvoiceGrid invoice) {
		return new EzeePayeeGrid(cache, invoice);
	}

	private EzeePayerGrid createPayerGrid() {
		return new EzeePayerGrid(cache);
	}

	private EzeeInvoiceGrid createInvoiceGrid() {
		return new EzeeInvoiceGrid(cache);
	}

	private EzeePaymentGrid createPaymentGrid() {
		return new EzeePaymentGrid(cache);
	}

	@Override
	public void loginSuccessful(EzeeUser user) {
		RootLayoutPanel.get().remove(login);
		log.log(Level.INFO, "Successfully logged in as user '" + user + "'.");
		this.user = user;
		initApplication();
		showDefaultCursor();
	}

	@Override
	public void requestNewRegistration() {
		RootLayoutPanel.get().remove(login);
		RootLayoutPanel.get().add(register);
	}

	@Override
	public void cancelRegistration() {
		RootLayoutPanel.get().remove(register);
		RootLayoutPanel.get().add(login);
	}

	@Override
	public void registrationSuccess(final EzeeUser user) {
		RootLayoutPanel.get().remove(register);
		log.log(Level.INFO, "Successfully registered user '" + user + "'.");
		this.user = user;
		initApplication();
		showDefaultCursor();
	}
}