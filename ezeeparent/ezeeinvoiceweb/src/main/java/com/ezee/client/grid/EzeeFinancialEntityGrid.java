package com.ezee.client.grid;

import static com.ezee.client.css.EzeeInvoiceDefaultResources.INSTANCE;
import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.model.entity.EzeeFinancialEntity;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public abstract class EzeeFinancialEntityGrid<T extends EzeeFinancialEntity> extends EzeeGrid<T> {

	protected TextBox nameText;

	public EzeeFinancialEntityGrid(final EzeeInvoiceServiceAsync service, final EzeeInvoiceEntityCache cache) {
		super(service, cache);
	}

	@Override
	protected void initFilter() {
		super.initFilter();
		Label nameLabel = new Label("Name");
		nameLabel.setStyleName(INSTANCE.css().gwtLabelMedium());
		nameText = new TextBox();
		nameText.setStyleName(INSTANCE.css().gwtTextBoxMedium());
		filterpanel.add(nameLabel);
		filterpanel.add(nameText);
		KeyPressHandler filterHandler = new EzeeFilterKeyPressHandler();
		nameText.addKeyPressHandler(filterHandler);
		initRefreshButton();
		initClearButton();
	}

	@Override
	protected void clearFilter() {
		nameText.setText(EMPTY_STRING);
		filter = null;
		loadEntities();
	}

	@Override
	protected EzeeFinancialEntityFilter<T> createFilter() {
		return new EzeeFinancialEntityFilter<T>(nameText.getText());
	}
}