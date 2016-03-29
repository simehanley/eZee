package com.ezee.client.css;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface EzeeLeaseResources extends ClientBundle {

	public static EzeeLeaseResources INSTANCE = GWT.create(EzeeLeaseResources.class);

	interface EzeeLeaseCss extends CssResource {
	}

	@Source("EzeeLease.css")
	EzeeLeaseCss css();
}