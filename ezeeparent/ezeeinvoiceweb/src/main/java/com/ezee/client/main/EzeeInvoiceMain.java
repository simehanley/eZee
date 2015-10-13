package com.ezee.client.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.model.entity.EzeePayer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceMain extends Composite {

	private static final Logger log = Logger.getLogger("EzeeInvoiceMain");

	private EzeeInvoiceServiceAsync service;

	private static EzeeInvoiceMainUiBinder uiBinder = GWT.create(EzeeInvoiceMainUiBinder.class);

	interface EzeeInvoiceMainUiBinder extends UiBinder<Widget, EzeeInvoiceMain> {
	}

	public EzeeInvoiceMain() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public final EzeeInvoiceServiceAsync getService() {
		return service;
	}

	public void setService(EzeeInvoiceServiceAsync service) {
		this.service = service;
	}

	@UiHandler("btnRefresh")
	void onBtnRefreshClick(ClickEvent event) {
		service.getPayer(1, new AsyncCallback<EzeePayer>() {

			@Override
			public void onSuccess(EzeePayer payer) {
				log.log(Level.INFO, "Got payer with name '" + payer.getName() + "'.");
			}

			@Override
			public void onFailure(Throwable caught) {
				log.log(Level.SEVERE, "Error retrieving user.", caught);
			}
		});
	}
}
