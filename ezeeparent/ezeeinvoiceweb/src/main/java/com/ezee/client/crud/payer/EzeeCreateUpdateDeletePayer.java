package com.ezee.client.crud.payer;

import static com.ezee.client.EzeeInvoiceWebConstants.DELETE_PREMISES;
import static com.ezee.client.EzeeInvoiceWebConstants.EDIT_PREMISES;
import static com.ezee.client.EzeeInvoiceWebConstants.NEW_PREMISES;
import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.create;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.client.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.client.crud.common.EzeeCreateUpdateDeleteFinancialEntity;
import com.ezee.client.crud.common.EzeePayerPayeeCommon;
import com.ezee.model.entity.EzeePayer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
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

	@UiField
	Button btnDelete;

	public EzeeCreateUpdateDeletePayer(final EzeeInvoiceServiceAsync service, final EzeeInvoiceEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeePayer> handler) {
		this(service, cache, handler, null, create);
	}

	public EzeeCreateUpdateDeletePayer(final EzeeInvoiceServiceAsync service, final EzeeInvoiceEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeePayer> handler, final EzeePayer entity,
			final EzeeCreateUpdateDeleteEntityType type) {
		super(service, cache, handler, entity, type);
		setWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void center() {
		switch (type) {
		case create:
			setText(NEW_PREMISES);
			btnDelete.setEnabled(false);
			break;
		case update:
			setText(EDIT_PREMISES);
			initialise();
			btnDelete.setEnabled(false);
			break;
		case delete:
			setText(DELETE_PREMISES);
			initialise();
			disable();
			break;
		}
		super.center();
	}

	@Override
	protected void initialise() {
		payer.setName(entity.getName());
		payer.setAddressLine1(entity.getAddressLineOne());
		payer.setAddressLine2(entity.getAddressLineTwo());
		payer.setSuburb(entity.getSuburb());
		payer.setCity(entity.getCity());
		payer.setState(entity.getState());
		payer.setPostCode(entity.getPostcode());
		payer.setPhone(entity.getPhone());
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
		entity.setUpdated(new Date());
	}

	private void disable() {
		payer.disable();
		btnSave.setEnabled(false);
	}

	@UiHandler("btnClose")
	void onCloseClick(ClickEvent event) {
		close();
	}

	@UiHandler("btnSave")
	void onSaveClick(ClickEvent event) {
		bind();
		service.saveEntity(EzeePayer.class.getName(), entity, new AsyncCallback<EzeePayer>() {

			@Override
			public void onFailure(final Throwable caught) {
				log.log(Level.SEVERE, "Error persisting premises '" + entity + "'.", caught);
				Window.alert("Error persisting premises '" + entity + "'.  Please see log for details.");
			}

			@Override
			public void onSuccess(final EzeePayer result) {
				log.log(Level.INFO, "Saved premises '" + entity + "' successfully");
				handler.onSave(result);
				updateCache(result, type);
				close();
			}
		});
	}

	@UiHandler("btnDelete")
	void onDeleteClick(ClickEvent event) {
		service.deleteEntity(EzeePayer.class.getName(), entity, new AsyncCallback<EzeePayer>() {

			@Override
			public void onFailure(Throwable caught) {
				log.log(Level.SEVERE, "Error deleting premises '" + entity + "'.", caught);
				Window.alert("Error deleting premises '" + entity + "'.  Please see log for details.");
			}

			@Override
			public void onSuccess(EzeePayer result) {
				log.log(Level.INFO, "Premises '" + entity + "' deleted successfully");
				handler.onDelete(result);
				updateCache(result, type);
				close();
			}
		});
	}
}