package com.ezee.web.common.ui.grid.premises;

import static com.ezee.web.common.enums.EzeeReportType.premises_report_excel;

import com.ezee.model.entity.EzeePremises;
import com.ezee.web.common.enums.EzeeReportType;
import com.ezee.web.common.ui.grid.payer.EzeePayerToolbar;

public class EzeePremisesToolbar extends EzeePayerToolbar<EzeePremises> {

	public EzeePremisesToolbar(EzeePremisesGrid grid) {
		super(grid);
	}

	@Override
	protected EzeeReportType getReportType() {
		return premises_report_excel;
	}
}