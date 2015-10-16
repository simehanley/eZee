package com.ezee.client.crud.payer;

import static com.ezee.client.EzeeInvoiceWebConstants.EDIT_PREMISES;
import static com.ezee.client.EzeeInvoiceWebConstants.NEW_PREMISES;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.crud.common.EzeeCreateUpdateDeleteFinancialEntity;
import com.ezee.client.crud.common.EzeePayerPayeeCommon;
import com.ezee.model.entity.EzeePayer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

public class EzeeCreateUpdateDeletePayer extends EzeeCreateUpdateDeleteFinancialEntity<EzeePayer> {

	private static final Logger log = Logger.getLogger("EzeeCreateUpdateDeletePayer");

	private static EzeeCreateUpdateDeletePayerUiBinder uiBinder = GWT.create(EzeeCreateUpdateDeletePayerUiBinder.class);

	interface EzeeCreateUpdateDeletePayerUiBinder extends UiBinder<Widget, EzeeCreateUpdateDeletePayer> {
	}

	@UiField
	EzeePayerPayeeCommon payer;

	@UiField
	Button btnSave;

	@UiField
	Button btnClose;

	public EzeeCreateUpdateDeletePayer(final EzeeInvoiceServiceAsync invoiceService) {
		this(null, invoiceService);
	}

	public EzeeCreateUpdateDeletePayer(final EzeePayer entity, final EzeeInvoiceServiceAsync invoiceService) {
		super(entity, invoiceService);
		setWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void center() {
		if (entity != null) {
			setText(EDIT_PREMISES);
			initialise();
		} else {
			setText(NEW_PREMISES);
		}
		super.center();
	}

	@Override
	protected void initialise() {
		/** for editing initialise fields **/
	}

	@Override
	protected void bind() {
		if (entity == null) {
			entity = new EzeePayer();
			entity.setCreated(new Date());
		}
		entity.setName(payer.getName());
		entity.setAddressLineOne(payer.getAddressLine1());
		entity.setAddressLineTwo(payer.getAddressLine2());
		entity.setSuburb(payer.getSuburb());
		entity.setCity(payer.getCity());
		entity.setState(payer.getState());
		entity.setPostcode(payer.getPostCode());
		entity.setPhone(payer.getPhone());
	}

	@UiHandler("btnClose")
	void onCloseClick(ClickEvent event) {
		close();
	}

	@UiHandler("btnSave")
	void onSaveClick(ClickEvent event) {
		bind();
		invoiceService.saveEntity(EzeePayer.class.getName(), entity, new AsyncCallback<Void>() {

			@Override
			public void onFailure(final Throwable caught) {
				log.log(Level.SEVERE, "Error persisting premises.", caught);
			}

			@Override
			public void onSuccess(final Void result) {
				log.log(Level.INFO, "Saved premises successfully");
				close();
			}
		});
	}
}