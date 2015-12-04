package com.ezee.client;

import java.util.List;

import com.ezee.model.entity.EzeeDebtAgeRule;
import com.ezee.model.entity.EzeePayment;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author siborg
 *
 */
@RemoteServiceRelativePath("invoiceservice")
public interface EzeeInvoiceService extends RemoteService {

	List<EzeePayment> getOutstandingCheques(Long premisesId);

	String calculateDueDate(EzeeDebtAgeRule rule, String today);
}