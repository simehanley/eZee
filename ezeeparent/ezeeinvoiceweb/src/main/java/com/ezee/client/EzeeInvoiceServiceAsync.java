package com.ezee.client;

import java.util.List;

import com.ezee.model.entity.EzeeDebtAgeRule;
import com.ezee.model.entity.EzeePayment;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeInvoiceServiceAsync {

	void getOutstandingCheques(Long premisesId, AsyncCallback<List<EzeePayment>> payments);

	void calculateDueDate(EzeeDebtAgeRule rule, String today, AsyncCallback<String> duedate);
}