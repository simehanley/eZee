package com.ezee.model.entity.lease;

import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.common.numeric.EzeeNumericUtils.round;

import java.util.ArrayList;
import java.util.Collection;

public class EzeeLeaseCollection extends ArrayList<EzeeLease> {

	private static final long serialVersionUID = -6005024756080085461L;

	public EzeeLeaseCollection() {
		super();
	}

	public EzeeLeaseCollection(Collection<? extends EzeeLease> c) {
		super(c);
	}

	public double monthlyAmount(final String type) {
		double monthlyAmount = ZERO_DBL;
		for (EzeeLease lease : this) {
			monthlyAmount += lease.monthlyAmount(type);
		}
		return monthlyAmount;
	}

	public double monthlyGst(final String type) {
		double monthlyGst = ZERO_DBL;
		for (EzeeLease lease : this) {
			monthlyGst += lease.monthlyGst(type);
		}
		return monthlyGst;
	}

	public double monthlyTotal(final String type) {
		return round(monthlyAmount(type) + monthlyGst(type));
	}

	public double yearlyAmount(final String type) {
		double yearlyAmount = ZERO_DBL;
		for (EzeeLease lease : this) {
			yearlyAmount += lease.yearlyAmount(type);
		}
		return yearlyAmount;
	}

	public double yearlyGst(final String type) {
		double yearlyGst = ZERO_DBL;
		for (EzeeLease lease : this) {
			yearlyGst += lease.yearlyGst(type);
		}
		return yearlyGst;
	}

	public double yearlyTotal(final String type) {
		return round(yearlyAmount(type) + yearlyGst(type));
	}
}