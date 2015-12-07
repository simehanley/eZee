package com.ezee.client.crud.project;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class EzeeCreateUpdateProject extends Composite {

	private static EzeeCreateUpdateProjectUiBinder uiBinder = GWT.create(EzeeCreateUpdateProjectUiBinder.class);

	interface EzeeCreateUpdateProjectUiBinder extends UiBinder<Widget, EzeeCreateUpdateProject> {
	}

	public EzeeCreateUpdateProject() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
