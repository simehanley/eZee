package com.ezee.client.css;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.cellview.client.DataGrid;

public interface EzeeInvoiceGwtOverridesResources extends DataGrid.Resources {

	public static EzeeInvoiceGwtOverridesResources INSTANCE = GWT.create(EzeeInvoiceGwtOverridesResources.class);

	interface EzeeInvoiceGridOverrideCss extends DataGrid.Style {
	}

	@Source({ DataGrid.Style.DEFAULT_CSS, "EzeeInvoiceGwtOverrides.css" })
	EzeeInvoiceGridOverrideCss dataGridStyle();
}