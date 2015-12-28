package com.ezee.client.crud.project.item;

import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.create;

import com.ezee.model.entity.project.EzeeProjectItem;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntity;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;

public class EzeeCreateUpdateDeleteProjectItem extends EzeeCreateUpdateDeleteEntity<EzeeProjectItem> {

	private static EzeeCreateUpdateDeleteProjectItemUiBinder uiBinder = GWT
			.create(EzeeCreateUpdateDeleteProjectItemUiBinder.class);

	interface EzeeCreateUpdateDeleteProjectItemUiBinder extends UiBinder<Widget, EzeeCreateUpdateDeleteProjectItem> {
	}

	public EzeeCreateUpdateDeleteProjectItem(EzeeEntityCache cache,
			EzeeCreateUpdateDeleteEntityHandler<EzeeProjectItem> handler, String[] headers) {
		this(cache, handler, null, create, headers);
	}

	public EzeeCreateUpdateDeleteProjectItem(EzeeEntityCache cache,
			EzeeCreateUpdateDeleteEntityHandler<EzeeProjectItem> handler, EzeeProjectItem entity,
			EzeeCreateUpdateDeleteEntityType type, String[] headers) {
		super(cache, handler, entity, type, headers);
		setWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	protected void initialise() {

	}

	@Override
	protected void bind() {
	}
}
