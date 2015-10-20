package com.ezee.client.crud.payee;

import static com.ezee.client.EzeeInvoiceWebConstants.DELETE_SUPPLIER;
import static com.ezee.client.EzeeInvoiceWebConstants.EDIT_SUPPLIER;
import static com.ezee.client.EzeeInvoiceWebConstants.NEW_SUPPLIER;
import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.create;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.client.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.client.crud.common.EzeeCreateUpdateDeleteFinancialEntity;
import com.ezee.client.crud.common.EzeePayeeBank;
import com.ezee.client.crud.common.EzeePayerPayeeCommon;
import com.ezee.model.entity.EzeePayee;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
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

	@UiField
	Button btnDelete;

	public EzeeCreateUpdateDeletePayee(final EzeeInvoiceServiceAsync service, final EzeeInvoiceEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeePayee> handler) {
		this(service, cache, handler, null, create);
	}

	public EzeeCreateUpdateDeletePayee(final EzeeInvoiceServiceAsync service, final EzeeInvoiceEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeePayee> handler, final EzeePayee entity,
			final EzeeCreateUpdateDeleteEntityType type) {
		super(service, cache, handler, entity, type);
		setWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void center() {
		switch (type) {
		case create:
			setText(NEW_SUPPLIER);
			btnDelete.setEnabled(false);
			break;
		case update:
			setText(EDIT_SUPPLIER);
			initialise();
			btnDelete.setEnabled(false);
			break;
		case delete:
			setText(DELETE_SUPPLIER);
			initialise();
			disable();
			break;
		}
		super.center();
	}

	@Override
	protected void initialise() {
		payee.setName(entity.getName());
		payee.setAddressLine1(entity.getAddressLineOne());
		payee.setAddressLine2(entity.getAddressLineTwo());
		payee.setSuburb(entity.getSuburb());
		payee.setCity(entity.getCity());
		payee.setState(entity.getState());
		payee.setPostCode(entity.getPostcode());
		payee.setPhone(entity.getPhone());
		payeebank.setBank(entity.getBank());
		payeebank.setAccountName(entity.getAccountName());
		payeebank.setAccountNumber(entity.getAccountNumber());
		payeebank.setAccountBsb(entity.getAccountBsb());
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

	private void disable() {
		payee.disable();
		payeebank.disable();
		btnSave.setEnabled(false);
	}

	@UiHandler("btnClose")
	void onCloseClick(ClickEvent event) {
		close();
	}

	@UiHandler("btnSave")
	void onSaveClick(ClickEvent event) {
		bind();
		service.saveEntity(EzeePayee.class.getName(), entity, new AsyncCallback<EzeePayee>() {

			@Override
			public void onFailure(final Throwable caught) {
				log.log(Level.SEVERE, "Error persisting supplier '" + entity + "'.", caught);
				Window.alert("Error persisting supplier '" + entity + "'.  Please see log for details.");
			}

			@Override
			public void onSuccess(final EzeePayee result) {
				log.log(Level.INFO, "Saved supplier '" + entity + "' successfully");
				handler.onSave(result);
				updateCache(result, type);
				close();
			}
		});
	}

	@UiHandler("btnDelete")
	void onDeleteClick(ClickEvent event) {
		service.deleteEntity(EzeePayee.class.getName(), entity, new AsyncCallback<EzeePayee>() {

			@Override
			public void onFailure(Throwable caught) {
				log.log(Level.SEVERE, "Error deleting supplier '" + entity + "'.", caught);
				Window.alert("Error deleting supplier '" + entity + "'.  Please see log for details");
			}

			@Override
			public void onSuccess(EzeePayee result) {
				log.log(Level.INFO, "Supplier '" + entity + "' deleted successfully");
				handler.onDelete(result);
				updateCache(result, type);
				close();
			}
		});
	}
}