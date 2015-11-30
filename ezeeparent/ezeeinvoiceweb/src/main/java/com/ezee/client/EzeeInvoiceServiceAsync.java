package com.ezee.client;

import java.util.List;

import com.ezee.model.entity.EzeeDatabaseEntity;
import com.ezee.model.entity.EzeeDebtAgeRule;
import com.ezee.model.entity.EzeePayment;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeInvoiceServiceAsync {

	<T extends EzeeDatabaseEntity> void getEntity(String clazz, long id, AsyncCallback<List<T>> entities);

	<T extends EzeeDatabaseEntity> void saveEntity(String clazz, T entity, AsyncCallback<T> result);

	<T extends EzeeDatabaseEntity> void deleteEntity(String clazz, T entity, AsyncCallback<T> result);

	<T extends EzeeDatabaseEntity> void getEntities(String clazz, AsyncCallback<List<T>> entity);

	void getOutstandingCheques(Long premisesId, AsyncCallback<List<EzeePayment>> payments);

	void calculateDueDate(EzeeDebtAgeRule rule, String today, AsyncCallback<String> duedate);
}