package com.ezee.web.common.ui.grid.payee;

import com.ezee.model.entity.EzeePayee;
import com.ezee.web.common.ui.grid.EzeeFinancialEntityToolbar;

public abstract class EzeePayeeToolbar<T extends EzeePayee> extends EzeeFinancialEntityToolbar<T> {

	public EzeePayeeToolbar(final EzeePayeeGrid<T> grid) {
		super(grid);
	}
}