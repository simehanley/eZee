package com.ezee.dao.impl;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import org.springframework.transaction.annotation.Transactional;

import com.ezee.dao.EzeeInvoiceDao;
import com.ezee.model.entity.EzeeInvoice;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceDaoImpl extends EzeeBaseDaoImpl<EzeeInvoice> implements EzeeInvoiceDao {

	@Override
	@Transactional(propagation = REQUIRED, readOnly = false)
	public void delete(final EzeeInvoice entity) {
		deleteMappings(entity, "deletePayeeMappingsSql");
		deleteMappings(entity, "deletePayerMappingsSql");
		deleteMappings(entity, "deleteAgeDebtMappingsSql");
		entity.setPayee(null);
		entity.setPayer(null);
		entity.setAgeRule(null);
		super.delete(entity);
	}

	private void deleteMappings(final EzeeInvoice entity, final String mappingQuery) {
		getSessionFactory().getCurrentSession().getNamedQuery(mappingQuery).setLong("id", entity.getId())
				.executeUpdate();
	}
}