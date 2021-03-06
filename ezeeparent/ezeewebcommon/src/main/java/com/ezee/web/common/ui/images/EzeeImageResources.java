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

	@Source("word.png")
	ImageResource word();

	@Source("excel.png")
	ImageResource excel();

	@Source("image.jpg")
	ImageResource image();
	
	@Source("mail.png")
	ImageResource mail();

	@Source("bank.png")
	ImageResource bank();

	@Source("clear.png")
	ImageResource clear();

	@Source("refresh.png")
	ImageResource refresh();

	@Source("report.png")
	ImageResource report();
}