package com.ezee.client.grid.project;

import com.ezee.model.entity.project.EzeeProject;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class EzeeProjectDetail extends Composite {

	private static EzeeProjectDetailUiBinder uiBinder = GWT.create(EzeeProjectDetailUiBinder.class);

	interface EzeeProjectDetailUiBinder extends UiBinder<Widget, EzeeProjectDetail> {
	}

	@UiField
	Button btnClose;

	private final EzeeProject project;

	private final EzeeProjectDetailCloseHandler handler;

	public EzeeProjectDetail(final EzeeProject project, final EzeeProjectDetailCloseHandler handler) {
		this.project = project;
		this.handler = handler;
		initWidget(uiBinder.createAndBindUi(this));
	}

	public final EzeeProject getProject() {
		return project;
	}

	@UiHandler("btnClose")
	void onCloseClick(ClickEvent event) {
		handler.closed(project);
	}
}