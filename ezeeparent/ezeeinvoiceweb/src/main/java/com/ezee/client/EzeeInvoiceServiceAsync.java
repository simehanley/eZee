package com.ezee.client;

import com.ezee.model.entity.EzeePayer;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeInvoiceServiceAsync {

	void getPayer(long id, AsyncCallback<EzeePayer> payer);
}