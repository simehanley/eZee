package com.ezee.client.crud.project.item.detail;

import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.create;
import static com.ezee.web.common.ui.utils.EzeeListBoxUtils.loadEnums;

import com.ezee.model.entity.enums.EzeeProjectItemType;
import com.ezee.model.entity.project.EzeeProjectItemDetail;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteTaxableEntity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class EzeeCreateUpdateDeleteProjectItemDetail
		extends EzeeCreateUpdateDeleteTaxableEntity<EzeeProjectItemDetail> {

	private static EzeeCreateUpdateDeleteProjectItemDetailUiBinder uiBinder = GWT
			.create(EzeeCreateUpdateDeleteProjectItemDetailUiBinder.class);

	interface EzeeCreateUpdateDeleteProjectItemDetailUiBinder
			extends UiBinder<Widget, EzeeCreateUpdateDeleteProjectItemDetail> {
	}

	@UiField
	Button btnDelete;

	@UiField
	Button btnSave;

	@UiField
	Button btnClose;

	@UiField
	ListBox lstType;

	public EzeeCreateUpdateDeleteProjectItemDetail(EzeeEntityCache cache,
			EzeeCreateUpdateDeleteEntityHandler<EzeeProjectItemDetail> handler, String[] headers) {
		this(cache, handler, null, create, headers);
	}

	public EzeeCreateUpdateDeleteProjectItemDetail(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeeProjectItemDetail> handler,
			final EzeeProjectItemDetail entity, final EzeeCreateUpdateDeleteEntityType type, final String[] headers) {
		super(cache, handler, entity, type, headers);
		setWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void show() {
		loadTypes();
		initForm();
		switch (type) {
		case create:
			setText(headers[NEW_HEADER_INDEX]);
			initialiseNew();
			setFocus(txtAmount);
			btnDelete.setEnabled(false);
			break;
		case update:
			setText(headers[EDIT_HEADER_INDEX]);
			setFocus(txtAmount);
			initialise();
			btnDelete.setEnabled(false);
			break;
		case delete:
			setText(headers[EDIT_HEADER_INDEX]);
			initialise();
			// disable();
			break;
		}
		super.show();
	}

	@Override
	protected void initialise() {
	}

	@Override
	protected void bind() {
	}

	private void loadTypes() {
		loadEnums(EzeeProjectItemType.values(), lstType);
	}

	@UiHandler("btnClose")
	void onCloseClick(ClickEvent event) {
		close();
	}
}