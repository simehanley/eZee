package com.ezee.web.common.ui.images;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeImageResources extends ClientBundle {

	public static EzeeImageResources INSTANCE = GWT.create(EzeeImageResources.class);

	@Source("pdf.jpeg")
	ImageResource pdf();
}