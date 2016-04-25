package com.ezee.client.crud.leasemetadata;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class EzeeCreateUpdateLeaseMetaData extends Composite {

	private static EzeeCreateUpdateLeaseMetaDataUiBinder uiBinder = GWT
			.create(EzeeCreateUpdateLeaseMetaDataUiBinder.class);

	interface EzeeCreateUpdateLeaseMetaDataUiBinder extends UiBinder<Widget, EzeeCreateUpdateLeaseMetaData> {
	}

	public EzeeCreateUpdateLeaseMetaData() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
