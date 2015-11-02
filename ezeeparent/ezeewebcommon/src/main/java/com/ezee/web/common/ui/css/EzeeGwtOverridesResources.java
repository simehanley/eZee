package com.ezee.web.common.ui.css;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.cellview.client.DataGrid;

public interface EzeeGwtOverridesResources extends DataGrid.Resources {

	public static EzeeGwtOverridesResources INSTANCE = GWT.create(EzeeGwtOverridesResources.class);

	interface EzeeInvoiceGridOverrideCss extends DataGrid.Style {
	}

	@Source({ DataGrid.Style.DEFAULT_CSS, "EzeeGwtOverrides.css" })
	EzeeInvoiceGridOverrideCss dataGridStyle();
}