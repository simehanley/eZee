package com.ezee.client.bank;

import static com.ezee.client.EzeeInvoiceWebConstants.BANK;
import static com.ezee.client.EzeeInvoiceWebConstants.INVOICE_SERVICE;
import static com.ezee.client.grid.payment.EzeePaymentGridModel.DESCRIPTION;
import static com.ezee.client.grid.payment.EzeePaymentGridModel.PAYMENT_DATE;
import static com.ezee.client.grid.payment.EzeePaymentGridModel.PAYMENT_TYPE;
import static com.ezee.client.ui.EzeeInvoiceUiUtils.loadEntities;
import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.common.web.EzeeFromatUtils.getAmountFormat;
import static com.ezee.web.common.ui.css.EzeeGwtOverridesResources.INSTANCE;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showDefaultCursor;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showWaitCursor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.client.grid.payment.EzeePaymentGridModel;
import com.ezee.model.entity.EzeePayer;
import com.ezee.model.entity.EzeePayment;
import com.ezee.web.common.ui.dialog.EzeeDialog;
import com.ezee.web.common.ui.utils.EzeeTextBoxUtils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EzeeBankBalance extends EzeeDialog {

	private static final Logger log = Logger.getLogger("EzeeBankBalance");

	private static EzeeBankBalanceUiBinder uiBinder = GWT.create(EzeeBankBalanceUiBinder.class);

	private static final String ALL = "ALL";

	@UiField
	ListBox lstPremises;

	@UiField
	TextBox txtBalance;

	@UiField
	TextBox txtChequeBalance;

	@UiField
	TextBox txtNetBalance;

	@UiField(provided = true)
	DataGrid<EzeePayment> grdCheques;

	@UiField
	Button btnClose;

	@UiField
	Button btnRefresh;

	private EzeePaymentGridModel model;

	private final EzeeInvoiceEntityCache cache;

	interface EzeeBankBalanceUiBinder extends UiBinder<Widget, EzeeBankBalance> {
	}

	public EzeeBankBalance(final EzeeInvoiceEntityCache cache) {
		super(false, false);
		this.cache = cache;
		setText(BANK);
		initGrid();
		setWidget(uiBinder.createAndBindUi(this));
		initForm();
	}

	private void initGrid() {
		grdCheques = new DataGrid<>();
		grdCheques = new DataGrid<EzeePayment>(100, INSTANCE);
		grdCheques.setMinimumTableWidth(600, Style.Unit.PX);
		model = resolveModel();
		model.bind(grdCheques);
	}

	private void initForm() {
		txtBalance.setText(getAmountFormat().format(ZERO_DBL));
		lstPremises.addItem(ALL);
		loadEntities(EzeePayer.class, lstPremises, cache);
		loadOutstandingCheques();
		lstPremises.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				loadOutstandingCheques();
			}
		});
		txtBalance.addKeyPressHandler(new EzeeTextBoxUtils.NumericKeyPressHandler());
		txtBalance.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				updateTotals();
			}
		});
	}

	@UiHandler("btnRefresh")
	void onRefreshClick(ClickEvent event) {
		loadOutstandingCheques();
	}

	@UiHandler("btnClose")
	void onCloseClick(ClickEvent event) {
		close();
	}

	private EzeePaymentGridModel resolveModel() {
		Set<String> hiddenColumns = new HashSet<>();
		hiddenColumns.add(PAYMENT_DATE);
		hiddenColumns.add(PAYMENT_TYPE);
		hiddenColumns.add(DESCRIPTION);
		return new EzeePaymentGridModel(hiddenColumns);
	}

	private void loadOutstandingCheques() {
		String premisesName = lstPremises.getSelectedItemText();
		EzeePayer premises = (EzeePayer) cache.getEntities(EzeePayer.class).get(premisesName);
		Long id = (premises == null) ? null : premises.getId();
		showWaitCursor();
		INVOICE_SERVICE.getOutstandingCheques(id, new AsyncCallback<List<EzeePayment>>() {

			@Override
			public void onSuccess(final List<EzeePayment> result) {
				model.getHandler().getList().clear();
				model.getHandler().getList().addAll(result);
				updateTotals();
				grdCheques.redraw();
				showDefaultCursor();
			}

			@Override
			public void onFailure(Throwable caught) {
				log.log(Level.SEVERE, "Failed to load outstanding cheques.", caught);
				showDefaultCursor();
			}
		});
	}

	private void updateTotals() {
		List<EzeePayment> payments = model.getHandler().getList();
		double balance = getAmountFormat().parse(txtBalance.getText());
		double chequeBalance = ZERO_DBL;
		for (EzeePayment payment : payments) {
			chequeBalance += payment.getPaymentAmount();
		}
		double netBalance = (balance - chequeBalance);
		txtBalance.setText(getAmountFormat().format(balance));
		txtChequeBalance.setText(getAmountFormat().format(chequeBalance));
		txtNetBalance.setText(getAmountFormat().format(netBalance));
	}
}