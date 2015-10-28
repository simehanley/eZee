package com.ezee.client.images;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeInvoiceImageResources extends ClientBundle {

	public static EzeeInvoiceImageResources INSTANCE = GWT.create(EzeeInvoiceImageResources.class);

	@Source("pdf.jpeg")
	ImageResource pdf();
}