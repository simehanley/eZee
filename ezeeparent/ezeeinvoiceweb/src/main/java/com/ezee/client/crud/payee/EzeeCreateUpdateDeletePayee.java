package com.ezee.client.crud.payee;

import static com.ezee.client.EzeeInvoiceWebConstants.EDIT_SUPPLIER;
import static com.ezee.client.EzeeInvoiceWebConstants.NEW_SUPPLIER;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.crud.common.EzeeCreateUpdateDeleteFinancialEntity;
import com.ezee.client.crud.common.EzeePayeeBank;
import com.ezee.client.crud.common.EzeePayerPayeeCommon;
import com.ezee.model.entity.EzeePayee;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

public class EzeeCreateUpdateDeletePayee extends EzeeCreateUpdateDeleteFinancialEntity<EzeePayee> {

	private static final Logger log = Logger.getLogger("EzeeCreateUpdateDeletePayee");

	private static EzeeCreateUpdateDeletePayeeUiBinder uiBinder = GWT.create(EzeeCreateUpdateDeletePayeeUiBinder.class);

	interface EzeeCreateUpdateDeletePayeeUiBinder extends UiBinder<Widget, EzeeCreateUpdateDeletePayee> {
	}
	
	@UiField
	EzeePayerPayeeCommon payee;

	@UiField
	EzeePayeeBank payeebank;

	@UiField
	Button btnClose;

	@UiField
	Button btnSave;

	public EzeeCreateUpdateDeletePayee(final EzeeInvoiceServiceAsync invoiceService) {
		this(null, invoiceService);
	}

	public EzeeCreateUpdateDeletePayee(final EzeePayee entity, final EzeeInvoiceServiceAsync invoiceService) {
		super(entity, invoiceService);
		setWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void center() {
		if (entity != null) {
			setText(EDIT_SUPPLIER);
			initialise();
		} else {
			setText(NEW_SUPPLIER);
		}
		super.center();
	}

	@Override
	protected void initialise() {
		/* implement me */
	}

	@Override
	protected void bind() {
		if (entity == null) {
			entity = new EzeePayee();
			entity.setCreated(new Date());
		}
		entity.setName(payee.getName());
		entity.setAddressLineOne(payee.getAddressLine1());
		entity.setAddressLineTwo(payee.getAddressLine2());
		entity.setSuburb(payee.getSuburb());
		entity.setCity(payee.getCity());
		entity.setState(payee.getState());
		entity.setPostcode(payee.getPostCode());
		entity.setPhone(payee.getPhone());
		entity.setBank(payeebank.getBank());
		entity.setAccountName(payeebank.getAccountName());
		entity.setAccountNumber(payeebank.getAccountNumber());
		entity.setAccountBsb(payeebank.getAccountBsb());
		entity.setUpdated(new Date());
	}

	@UiHandler("btnClose")
	void onCloseClick(ClickEvent event) {
		close();
	}

	@UiHandler("btnSave")
	void onSaveClick(ClickEvent event) {
		bind();
		invoiceService.saveEntity(EzeePayee.class.getName(), entity, new AsyncCallback<Void>() {

			@Override
			public void onFailure(final Throwable caught) {
				log.log(Level.SEVERE, "Error persisting supplier.", caught);
			}

			@Override
			public void onSuccess(final Void result) {
				log.log(Level.INFO, "Saved supplier successfully");
				close();
			}
		});
	}
}