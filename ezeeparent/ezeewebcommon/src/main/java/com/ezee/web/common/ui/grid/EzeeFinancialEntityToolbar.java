package com.ezee.web.common.ui.grid;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.web.common.EzeeWebCommonConstants.EXCEL_FE_FILTER;
import static com.ezee.web.common.EzeeWebCommonConstants.REPORT_SERVICE;
import static com.ezee.web.common.EzeeWebCommonConstants.REPORT_TYPE;

import com.ezee.common.string.EzeeStringUtils;
import com.ezee.model.entity.EzeeFinancialEntity;
import com.ezee.model.entity.filter.EzeeEntityFilter;
import com.ezee.model.entity.filter.EzeeStringFilter;
import com.ezee.web.common.enums.EzeeReportType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public abstract class EzeeFinancialEntityToolbar<T extends EzeeFinancialEntity> extends EzeeGridToolbar<T> {

	private static EzeeFinancialEntityToolbarUiBinder uiBinder = GWT.create(EzeeFinancialEntityToolbarUiBinder.class);

	interface EzeeFinancialEntityToolbarUiBinder extends UiBinder<Widget, EzeeFinancialEntityToolbar<?>> {
	}

	@UiField
	TextBox txtName;

	@UiField
	Button btnRefresh;

	@UiField
	Button btnClear;

	@UiField
	public Button btnReport;

	public EzeeFinancialEntityToolbar(EzeeFinancialEntityGrid<T> grid) {
		super(grid);
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public String getName() {
		if (EzeeStringUtils.hasLength(txtName.getText())) {
			return txtName.getText();
		}
		return EMPTY_STRING;
	}

	@Override
	public void init() {
		KeyPressHandler filterHandler = new EzeeToolbarKeyPressHandler();
		txtName.addKeyPressHandler(filterHandler);
		ClickHandler refreshHandler = new EzeeToolbarRefreshHandler();
		btnRefresh.addClickHandler(refreshHandler);
		ClickHandler clearHandler = new EzeeToolbarClearHandler();
		btnClear.addClickHandler(clearHandler);
	}

	@Override
	public void clearToolbar() {
		txtName.setText(EMPTY_STRING);
	}

	@Override
	public EzeeEntityFilter<T> resolveFilter() {
		return new EzeeStringFilter<T>(getName());
	}

	@UiHandler("btnReport")
	public void onReportClick(ClickEvent event) {
		String reportServiceUrl = GWT.getModuleBaseURL() + resolveReportParamString();
		Window.Location.assign(reportServiceUrl);
	}

	protected String resolveReportParamString() {
		StringBuilder builder = new StringBuilder();
		builder.append(REPORT_SERVICE + "?" + REPORT_TYPE + "=" + getReportType());
		builder.append("&" + EXCEL_FE_FILTER + "=" + getName());
		return builder.toString();
	}

	protected abstract EzeeReportType getReportType();
}