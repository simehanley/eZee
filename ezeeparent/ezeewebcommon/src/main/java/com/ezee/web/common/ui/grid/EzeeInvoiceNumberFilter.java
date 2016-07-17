package com.ezee.web.common.ui.grid;

import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.common.string.EzeeStringUtils.hasLength;

import java.util.ArrayList;
import java.util.List;

import com.ezee.model.entity.EzeeAuditableDatabaseEntity;
import com.ezee.model.entity.filter.EzeeEntityFilter;
import com.google.gwt.regexp.shared.RegExp;

public abstract class EzeeInvoiceNumberFilter<T extends EzeeAuditableDatabaseEntity> implements EzeeEntityFilter<T> {

	protected List<RegExp> invoiceRegExp;

	public EzeeInvoiceNumberFilter(final String invoiceText) {
		invoiceRegExp = (hasLength(invoiceText)) ? resolveInvoiceRegExp(invoiceText) : null;
	}

	private List<RegExp> resolveInvoiceRegExp(final String invoiceText) {
		String[] invoices = invoiceText.split(",");
		if (invoices != null && invoices.length != ZERO) {
			invoiceRegExp = new ArrayList<>();
			for (String invoice : invoices) {
				invoiceRegExp.add(RegExp.compile(invoice));
			}
		}
		return invoiceRegExp;
	}
}