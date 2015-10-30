package com.ezee.dao;

import java.util.List;

import com.ezee.model.entity.EzeePayment;

/**
 * 
 * @author siborg
 *
 */
public interface EzeePaymentDao extends EzeeBaseDao<EzeePayment> {
	
	List<EzeePayment> getOutstandingCheques(Long premisesId);
}