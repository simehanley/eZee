package com.ezee.client.smartgwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SmartGwtIntegration extends Composite {

	private static SmartGwtIntegrationUiBinder uiBinder = GWT.create(SmartGwtIntegrationUiBinder.class);

	interface SmartGwtIntegrationUiBinder extends UiBinder<Widget, SmartGwtIntegration> {
	}

	public SmartGwtIntegration() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
