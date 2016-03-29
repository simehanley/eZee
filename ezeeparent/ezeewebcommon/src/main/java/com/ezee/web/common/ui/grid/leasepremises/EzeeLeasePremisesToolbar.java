package com.ezee.web.common.ui.grid.leasepremises;

import static com.ezee.web.common.enums.EzeeReportType.lease_premises_report_excel;

import com.ezee.model.entity.lease.EzeeLeasePremises;
import com.ezee.web.common.enums.EzeeReportType;
import com.ezee.web.common.ui.grid.payee.EzeePayeeToolbar;

public class EzeeLeasePremisesToolbar extends EzeePayeeToolbar<EzeeLeasePremises> {

	public EzeeLeasePremisesToolbar(EzeeLeasePremisesGrid grid) {
		super(grid);
	}

	@Override
	protected EzeeReportType getReportType() {
		return lease_premises_report_excel;
	}
}