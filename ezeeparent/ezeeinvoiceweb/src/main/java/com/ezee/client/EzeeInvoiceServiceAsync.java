package com.ezee.client;

import java.util.List;

import com.ezee.model.entity.EzeeDatabaseEntity;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeInvoiceServiceAsync {

	<T extends EzeeDatabaseEntity> void getEntities(String clazz, AsyncCallback<List<T>> entities);
}