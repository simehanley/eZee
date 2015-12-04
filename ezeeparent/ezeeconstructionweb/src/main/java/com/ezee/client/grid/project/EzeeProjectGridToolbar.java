package com.ezee.client.grid.project;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.string.EzeeStringUtils.hasLength;

import com.ezee.model.entity.filter.EzeeEntityFilter;
import com.ezee.model.entity.filter.EzeeStringFilter;
import com.ezee.model.entity.project.EzeeProject;
import com.ezee.web.common.ui.grid.EzeeGridToolbar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EzeeProjectGridToolbar extends EzeeGridToolbar<EzeeProject> {

	private static EzeeProjectGridToolbarUiBinder uiBinder = GWT.create(EzeeProjectGridToolbarUiBinder.class);

	interface EzeeProjectGridToolbarUiBinder extends UiBinder<Widget, EzeeProjectGridToolbar> {
	}

	@UiField
	TextBox txtProjectName;

	@UiField
	Button btnRefresh;

	@UiField
	Button btnClear;

	public EzeeProjectGridToolbar(final EzeeProjectGrid grid) {
		super(grid);
		initWidget(uiBinder.createAndBindUi(this));
	}

	public String getProjectName() {
		if (hasLength(txtProjectName.getText())) {
			return txtProjectName.getText();
		}
		return EMPTY_STRING;
	}

	@Override
	public void init() {
		KeyPressHandler filterHandler = new EzeeToolbarKeyPressHandler();
		txtProjectName.addKeyPressHandler(filterHandler);
		ClickHandler refreshHandler = new EzeeToolbarRefreshHandler();
		btnRefresh.addClickHandler(refreshHandler);
		ClickHandler clearHandler = new EzeeToolbarClearHandler();
		btnClear.addClickHandler(clearHandler);
	}

	@Override
	public void clearToolbar() {
		txtProjectName.setText(EMPTY_STRING);
	}

	@Override
	public EzeeEntityFilter<EzeeProject> resolveFilter() {
		return new EzeeStringFilter<EzeeProject>(getProjectName());
	}
}