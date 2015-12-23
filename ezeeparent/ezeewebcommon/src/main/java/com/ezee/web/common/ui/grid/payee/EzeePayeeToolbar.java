package com.ezee.web.common.ui.grid.payee;

import static com.ezee.web.common.enums.EzeeReportType.payee_report_excel;

import com.ezee.model.entity.EzeePayee;
import com.ezee.web.common.enums.EzeeReportType;
import com.ezee.web.common.ui.grid.EzeeFinancialEntityToolbar;

public class EzeePayeeToolbar<T extends EzeePayee> extends EzeeFinancialEntityToolbar<T> {

	public EzeePayeeToolbar(final EzeePayeeGrid<T> grid) {
		super(grid);
	}

	@Override
	protected EzeeReportType getReportType() {
		return payee_report_excel;
	}
}