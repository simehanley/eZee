package com.ezee.web.common.ui.grid.payer;

import com.ezee.model.entity.EzeePayer;
import com.ezee.web.common.ui.grid.EzeeFinancialEntityToolbar;

public abstract class EzeePayerToolbar<T extends EzeePayer> extends EzeeFinancialEntityToolbar<T> {

	public EzeePayerToolbar(EzeePayerGrid<T> grid) {
		super(grid);
	}
}