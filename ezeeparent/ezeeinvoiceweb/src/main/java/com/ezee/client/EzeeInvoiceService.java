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

	<T extends EzeeDatabaseEntity> T getEntity(String clazz, long id);

	<T extends EzeeDatabaseEntity> List<T> getEntities(String clazz);

	<T extends EzeeDatabaseEntity> T saveEntity(String clazz, T entity);

	<T extends EzeeDatabaseEntity> T deleteEntity(String clazz, T entity);
}