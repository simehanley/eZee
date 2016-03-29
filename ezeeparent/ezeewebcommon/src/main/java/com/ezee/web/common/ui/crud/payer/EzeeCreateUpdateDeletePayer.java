package com.ezee.web.common.ui.crud.payer;

import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;
import static com.ezee.web.common.EzeeWebCommonConstants.ENTITY_SERVICE;
import static com.ezee.web.common.EzeeWebCommonConstants.ERROR;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.create;
import static com.ezee.web.common.ui.dialog.EzeeMessageDialog.showNew;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showDefaultCursor;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showWaitCursor;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.model.entity.EzeePayer;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.web.common.ui.crud.common.EzeeCreateUpdateDeleteFinancialEntity;
import com.ezee.web.common.ui.crud.common.EzeePayerPayeeCommon;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

public abstract class EzeeCreateUpdateDeletePayer<T extends EzeePayer>
		extends EzeeCreateUpdateDeleteFinancialEntity<T> {

	private static final Logger log = Logger.getLogger("EzeeCreateUpdateDeletePayer");

	private static EzeeCreateUpdateDeletePayerUiBinder uiBinder = GWT.create(EzeeCreateUpdateDeletePayerUiBinder.class);

	interface EzeeCreateUpdateDeletePayerUiBinder extends UiBinder<Widget, EzeeCreateUpdateDeletePayer<?>> {
	}

	@UiField
	EzeePayerPayeeCommon payer;

	@UiField
	Button btnSave;

	@UiField
	Button btnClose;

	@UiField
	Button btnDelete;

	public EzeeCreateUpdateDeletePayer(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<T> handler, final String[] headers) {
		this(cache, handler, null, create, headers);
	}

	public EzeeCreateUpdateDeletePayer(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<T> handler, final T entity,
			final EzeeCreateUpdateDeleteEntityType type, final String[] headers) {
		super(cache, handler, entity, type, headers);
		setWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void show() {
		switch (type) {
		case create:
			setText(headers[NEW_HEADER_INDEX]);
			setFocus(payer.getTxtName());
			btnDelete.setEnabled(false);
			break;
		case update:
			setText(headers[EDIT_HEADER_INDEX]);
			initialise();
			setFocus(payer.getTxtName());
			btnDelete.setEnabled(false);
			break;
		case delete:
			setText(headers[DELETE_HEADER_INDEX]);
			initialise();
			disable();
			break;
		}
		super.show();
	}

	@Override
	protected void initialise() {
		payer.setName(entity.getName());
		payer.setContact(entity.getContact());
		payer.setAddressLine1(entity.getAddressLineOne());
		payer.setAddressLine2(entity.getAddressLineTwo());
		payer.setSuburb(entity.getSuburb());
		payer.setCity(entity.getCity());
		payer.setState(entity.getState());
		payer.setPostCode(entity.getPostcode());
		payer.setPhone(entity.getPhone());
		payer.setFax(entity.getFax());
		payer.setEmail(entity.getEmail());
	}

	@Override
	protected void bind() {
		if (entity == null) {
			entity = createEntity();
			entity.setCreated(DATE_UTILS.toString(new Date()));
		} else {
			entity.setUpdated(DATE_UTILS.toString(new Date()));
		}
		entity.setName(payer.getName());
		entity.setContact(payer.getContact());
		entity.setAddressLineOne(payer.getAddressLine1());
		entity.setAddressLineTwo(payer.getAddressLine2());
		entity.setSuburb(payer.getSuburb());
		entity.setCity(payer.getCity());
		entity.setState(payer.getState());
		entity.setPostcode(payer.getPostCode());
		entity.setPhone(payer.getPhone());
		entity.setFax(payer.getFax());
		entity.setEmail(payer.getEmail());
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
		btnSave.setEnabled(false);
		showWaitCursor();
		bind();
		ENTITY_SERVICE.saveEntity(entity.getClass().getName(), entity, new AsyncCallback<T>() {

			@Override
			public void onFailure(final Throwable caught) {
				btnSave.setEnabled(true);
				showDefaultCursor();
				log.log(Level.SEVERE, "Error persisting entity '" + entity + "'.", caught);
				showNew(ERROR, "Error persisting entity '" + entity + "'.  Please see log for details.");
			}

			@Override
			public void onSuccess(final T result) {
				log.log(Level.INFO, "Saved entity '" + entity + "' successfully");
				handler.onSave(result);
				updateCache(result, type);
				btnSave.setEnabled(true);
				showDefaultCursor();
				close();
			}
		});
	}

	@UiHandler("btnDelete")
	void onDeleteClick(ClickEvent event) {
		btnDelete.setEnabled(false);
		showWaitCursor();
		ENTITY_SERVICE.deleteEntity(entity.getClass().getName(), entity, new AsyncCallback<T>() {

			@Override
			public void onFailure(Throwable caught) {
				btnDelete.setEnabled(true);
				showDefaultCursor();
				log.log(Level.SEVERE, "Error deleting entity '" + entity + "'.", caught);
				showNew(ERROR, "Error deleting entity '" + entity + "'.  Please see log for details.");
			}

			@Override
			public void onSuccess(T result) {
				log.log(Level.INFO, "Entity '" + entity + "' deleted successfully");
				handler.onDelete(result);
				updateCache(result, type);
				btnDelete.setEnabled(true);
				showDefaultCursor();
				close();
			}
		});
	}

	protected abstract T createEntity();
}