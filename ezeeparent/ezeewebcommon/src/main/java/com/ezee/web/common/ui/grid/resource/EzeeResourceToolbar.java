package com.ezee.web.common.ui.grid.resource;

import static com.ezee.web.common.enums.EzeeReportType.resource_report_excel;

import com.ezee.model.entity.EzeeResource;
import com.ezee.web.common.enums.EzeeReportType;
import com.ezee.web.common.ui.grid.payee.EzeePayeeToolbar;

public class EzeeResourceToolbar extends EzeePayeeToolbar<EzeeResource> {

	public EzeeResourceToolbar(EzeeResourceGrid grid) {
		super(grid);
	}

	@Override
	protected EzeeReportType getReportType() {
		return resource_report_excel;
	}
}