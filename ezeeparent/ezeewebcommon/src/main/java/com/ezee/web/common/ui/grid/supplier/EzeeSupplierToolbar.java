package com.ezee.web.common.ui.grid.supplier;

import static com.ezee.web.common.enums.EzeeReportType.supplier_report_excel;

import com.ezee.model.entity.EzeeSupplier;
import com.ezee.web.common.enums.EzeeReportType;
import com.ezee.web.common.ui.grid.payee.EzeePayeeToolbar;

public class EzeeSupplierToolbar extends EzeePayeeToolbar<EzeeSupplier> {

	public EzeeSupplierToolbar(final EzeeSupplierGrid grid) {
		super(grid);
	}

	@Override
	protected EzeeReportType getReportType() {
		return supplier_report_excel;
	}
}