package com.ezee.client;

import java.util.List;

import com.ezee.model.entity.EzeeDatabaseEntity;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author siborg
 *
 */
@RemoteServiceRelativePath("invoiceservice")
public interface EzeeInvoiceService extends RemoteService {

	<T extends EzeeDatabaseEntity> List<T> getEntities(String clazz);
}
