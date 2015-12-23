package com.ezee.web.common.ui.grid.payer;

import static com.ezee.web.common.enums.EzeeReportType.payer_report_excel;

import com.ezee.model.entity.EzeePayer;
import com.ezee.web.common.enums.EzeeReportType;
import com.ezee.web.common.ui.grid.EzeeFinancialEntityToolbar;

public class EzeePayerToolbar extends EzeeFinancialEntityToolbar<EzeePayer> {

	public EzeePayerToolbar(EzeePayerGrid grid) {
		super(grid);
	}

	@Override
	protected EzeeReportType getReportType() {
		return payer_report_excel;
	}
}