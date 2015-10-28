package com.ezee.client.images;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeInvoiceImages extends ClientBundle {

	public static EzeeInvoiceImages INSTANCE = GWT.create(EzeeInvoiceImages.class);

	@Source("pdf.jpeg")
	ImageResource pdf();
}