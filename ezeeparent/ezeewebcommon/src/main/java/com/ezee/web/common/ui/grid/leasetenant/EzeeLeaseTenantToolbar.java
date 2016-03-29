package com.ezee.web.common.ui.grid.leasetenant;

import static com.ezee.web.common.enums.EzeeReportType.lease_tenant_report_excel;

import com.ezee.model.entity.lease.EzeeLeaseTenant;
import com.ezee.web.common.enums.EzeeReportType;
import com.ezee.web.common.ui.grid.payer.EzeePayerToolbar;

public class EzeeLeaseTenantToolbar extends EzeePayerToolbar<EzeeLeaseTenant> {

	public EzeeLeaseTenantToolbar(final EzeeLeaseTenantGrid grid) {
		super(grid);
	}

	@Override
	protected EzeeReportType getReportType() {
		return lease_tenant_report_excel;
	}
}
