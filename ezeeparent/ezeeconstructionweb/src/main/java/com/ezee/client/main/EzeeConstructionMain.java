package com.ezee.client.main;

import com.ezee.model.entity.EzeeUser;
import com.ezee.web.common.ui.main.EzeeWebMain;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;

public class EzeeConstructionMain extends EzeeWebMain {

	private static EzeeConstructionMainUiBinder uiBinder = GWT.create(EzeeConstructionMainUiBinder.class);

	interface EzeeConstructionMainUiBinder extends UiBinder<Widget, EzeeConstructionMain> {
	}

	public EzeeConstructionMain(final EzeeUser user) {
		super(user);
		initWidget(uiBinder.createAndBindUi(this));
		initUser();
		initMain();
	}
}
