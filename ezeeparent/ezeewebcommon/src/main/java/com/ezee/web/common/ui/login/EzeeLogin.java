package com.ezee.web.common.ui.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author siborg
 *
 */
public class EzeeLogin extends Composite {

	private static EzeeLoginUiBinder uiBinder = GWT.create(EzeeLoginUiBinder.class);

	interface EzeeLoginUiBinder extends UiBinder<Widget, EzeeLogin> {
	}

	public EzeeLogin() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
