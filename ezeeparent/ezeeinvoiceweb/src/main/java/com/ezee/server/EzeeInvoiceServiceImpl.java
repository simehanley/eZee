package com.ezee.server;

import com.ezee.client.EzeeInvoiceService;
import com.ezee.model.entity.EzeePayer;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceServiceImpl extends RemoteServiceServlet implements EzeeInvoiceService {

	private static final long serialVersionUID = 1L;

	@Override
	public EzeePayer getPayer(final String name) {
		EzeePayer payer = new EzeePayer();
		payer.setName(name);
		return payer;
	}
}