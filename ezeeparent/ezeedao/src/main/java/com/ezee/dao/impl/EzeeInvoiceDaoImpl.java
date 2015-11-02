package com.ezee.dao.impl;

import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import java.util.List;
import java.util.Set;

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

	@Override
	@Transactional(propagation = REQUIRED, readOnly = false)
	public void save(final Set<EzeeInvoice> invoices) {
		if (!isEmpty(invoices)) {
			for (EzeeInvoice invoice : invoices) {
				preprocess(invoice);
				merge(invoice);
			}
		}
	}

	private void preprocess(final EzeeInvoice invoice) {
		EzeeInvoice persisted = get(invoice.getId(), EzeeInvoice.class);
		if (persisted != null && persisted.getFile() != null) {
			invoice.setFile(persisted.getFile());
		}
	}

	@Override
	public EzeeInvoice get(long id) {
		return super.get(id, EzeeInvoice.class);
	}

	@Override
	public List<EzeeInvoice> get() {
		return super.get(EzeeInvoice.class);
	}
}