package com.ezee.web.common.ui.grid.contractor;

import static com.ezee.web.common.enums.EzeeReportType.contractor_report_excel;

import com.ezee.model.entity.EzeeContractor;
import com.ezee.web.common.enums.EzeeReportType;
import com.ezee.web.common.ui.grid.payee.EzeePayeeToolbar;

public class EzeeContractorToolbar extends EzeePayeeToolbar<EzeeContractor> {

	public EzeeContractorToolbar(EzeeContractorGrid grid) {
		super(grid);
	}

	@Override
	protected EzeeReportType getReportType() {
		return contractor_report_excel;
	}
}