package com.ezee.client.css;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface EzeeProjectResources extends ClientBundle {

	public static EzeeProjectResources INSTANCE = GWT.create(EzeeProjectResources.class);

	interface EzeeProjectCss extends CssResource {

		String gwtLabelProjectModify();

		String gwtLabelProjectModifyUnmod();

		String gwtLabelProjectModifyMod();
		
		String projectMainBackGround();
	}

	@Source("EzeeProject.css")
	EzeeProjectCss css();
}
