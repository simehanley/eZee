package com.ezee.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.model.entity.EzeePayer;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceWeb implements EntryPoint {

	private static final Logger log = Logger.getLogger("EzeeInvoiceWeb");

	private final EzeeInvoiceServiceAsync invoiceService = GWT.create(EzeeInvoiceService.class);

	public void onModuleLoad() {
		final Button sendButton = new Button("PAYER");
		sendButton.addStyleName("sendButton");

		RootPanel.get("sendButtonContainer").add(sendButton);

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler {
			public void onClick(ClickEvent event) {
				sendNameToServer();
			}

			private void sendNameToServer() {
				sendButton.setEnabled(false);
				invoiceService.getPayer("TEST", new AsyncCallback<EzeePayer>() {

					@Override
					public void onFailure(Throwable throwable) {
						log.log(Level.SEVERE, "Failed to get payer.", throwable);
						sendButton.setEnabled(true);
					}

					@Override
					public void onSuccess(EzeePayer result) {
						log.log(Level.SEVERE, "Successfully retrieved payer.");
						sendButton.setEnabled(true);
					}
				});
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
	}
}
