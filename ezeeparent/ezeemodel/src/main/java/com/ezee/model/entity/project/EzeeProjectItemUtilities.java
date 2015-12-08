package com.ezee.model.entity.project;

import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.model.entity.enums.EzeeProjectItemType.variation;

/**
 * 
 * @author siborg
 *
 */
public final class EzeeProjectItemUtilities {

	private EzeeProjectItemUtilities() {
	}

	public static EzeeProjectAmount resolvePaid(final EzeeProjectItem item) {
		double amount = ZERO_DBL, tax = ZERO_DBL;
		if (item.getPayments() != null) {
			for (EzeeProjectPayment pmt : item.getPayments()) {
				amount += pmt.getAmount();
				tax += pmt.getTax();
			}
		}
		return new EzeeProjectAmount(amount, tax);
	}

	public static EzeeProjectAmount resolveBudgeted(final EzeeProjectItem item) {
		double amount = ZERO_DBL, tax = ZERO_DBL;
		if (item.getDetails() != null) {
			for (EzeeProjectItemDetail detail : item.getDetails()) {
				if (detail.getType() != variation) {
					amount += detail.getAmount();
					tax += detail.getTax();
				}
			}
		}
		return new EzeeProjectAmount(amount, tax);
	}

	public static EzeeProjectAmount resolveActual(final EzeeProjectItem item) {
		double amount = ZERO_DBL, tax = ZERO_DBL;
		if (item.getDetails() != null) {
			for (EzeeProjectItemDetail detail : item.getDetails()) {
				amount += detail.getAmount();
				tax += detail.getTax();
			}
		}
		return new EzeeProjectAmount(amount, tax);
	}
}
