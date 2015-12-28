package com.ezee.dao.impl;

import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.ezee.dao.EzeeInvoiceDao;
import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.filter.invoice.EzeeInvoiceFilter;

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
		entity.setSupplier(null);
		entity.setPayer(null);
		entity.setAgeRule(null);
		super.delete(entity);
	}

	@Override
	@Transactional(propagation = REQUIRED, readOnly = false)
	public void save(final EzeeInvoice invoice) {
		if (invoice.getId() != NULL_ID) {
			preprocess(invoice);
			merge(invoice);
		} else {
			super.save(invoice);
		}
	}

	@Override
	@Transactional(propagation = REQUIRED, readOnly = false)
	public void save(final Set<EzeeInvoice> invoices) {
		if (!isEmpty(invoices)) {
			for (EzeeInvoice invoice : invoices) {
				this.save(invoice);
			}
		}
	}

	private void preprocess(final EzeeInvoice invoice) {
		if (hasLength(invoice.getFilename())) {
			EzeeInvoice persisted = get(invoice.getId(), EzeeInvoice.class);
			if (persisted != null && persisted.getFile() != null) {
				invoice.setFile(persisted.getFile());
			}
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

	@Override
	public List<EzeeInvoice> get(final EzeeInvoiceFilter filter) {
		List<EzeeInvoice> unfiltered = get();
		List<EzeeInvoice> filtered = new ArrayList<>();
		for (EzeeInvoice invoice : unfiltered) {
			if (filter.include(invoice)) {
				filtered.add(invoice);
			}
		}
		return filtered;
	}
}