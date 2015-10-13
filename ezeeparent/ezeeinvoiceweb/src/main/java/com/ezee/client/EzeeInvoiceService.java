package com.ezee.client;

import com.ezee.model.entity.EzeePayer;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author siborg
 *
 */
@RemoteServiceRelativePath("invoiceservice")
public interface EzeeInvoiceService extends RemoteService {

	EzeePayer getPayer(long id);
}
