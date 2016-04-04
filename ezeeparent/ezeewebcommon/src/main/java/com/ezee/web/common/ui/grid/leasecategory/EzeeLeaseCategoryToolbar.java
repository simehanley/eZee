package com.ezee.web.common.ui.grid.leasecategory;

import static com.ezee.web.common.enums.EzeeReportType.lease_category_report_excel;

import com.ezee.model.entity.lease.EzeeLeaseCategory;
import com.ezee.web.common.enums.EzeeReportType;
import com.ezee.web.common.ui.grid.EzeeFinancialEntityToolbar;

public class EzeeLeaseCategoryToolbar extends EzeeFinancialEntityToolbar<EzeeLeaseCategory> {

	public EzeeLeaseCategoryToolbar(final EzeeLeaseCategoryGrid grid) {
		super(grid);
	}

	@Override
	protected EzeeReportType getReportType() {
		return lease_category_report_excel;
	}
}