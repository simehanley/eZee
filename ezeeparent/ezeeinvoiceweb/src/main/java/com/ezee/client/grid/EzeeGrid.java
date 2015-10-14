package com.ezee.client.grid;

import static com.ezee.client.css.EzeeInvoiceGwtOverridesResources.INSTANCE;
import static java.util.logging.Level.SEVERE;

import java.util.List;
import java.util.logging.Logger;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.model.entity.EzeeDatabaseEntity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public abstract class EzeeGrid<T extends EzeeDatabaseEntity> extends Composite implements EzeeHasGrid<T> {

	private static final Logger log = Logger.getLogger("EzeeGrid");

	protected static final int DEFAULT_PAGE_SIZE = 100;
	protected static final int DEFAULT_GRID_SIZE = 600;

	private static EzeeGridUiBinder uiBinder = GWT.create(EzeeGridUiBinder.class);

	@UiField(provided = true)
	protected DataGrid<T> grid;

	protected EzeeGridModel<T> model;

	protected EzeeInvoiceServiceAsync service;

	interface EzeeGridUiBinder extends UiBinder<Widget, EzeeGrid<?>> {
	}

	public EzeeGrid(final EzeeInvoiceServiceAsync service) {
		this.service = service;
		init();
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public final DataGrid<T> getGrid() {
		return grid;
	}

	private void init() {
		initGrid();
		loadEntities();
	}

	protected void initGrid() {
		grid = new DataGrid<T>(DEFAULT_PAGE_SIZE, INSTANCE);
		grid.setMinimumTableWidth(DEFAULT_GRID_SIZE, Style.Unit.PX);
	}

	private void setEntities(final List<T> entities) {
		model.getHandler().getList().clear();
		model.getHandler().getList().addAll(entities);
		grid.redraw();
	}

	private void loadEntities() {
		service.getEntities(getGridClass(), new AsyncCallback<List<T>>() {

			@Override
			public void onFailure(Throwable caught) {
				log.log(SEVERE, "Error loading entities of type '" + getGridClass() + "'", caught);
			}

			@Override
			public void onSuccess(List<T> entities) {
				setEntities(entities);
			}
		});
	}

	public abstract String getGridClass();
}