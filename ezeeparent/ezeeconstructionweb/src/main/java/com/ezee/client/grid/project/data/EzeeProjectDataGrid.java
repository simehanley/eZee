package com.ezee.client.grid.project.data;

import static com.ezee.common.EzeeCommonConstants.ZERO;

import com.ezee.model.entity.EzeeDatabaseEntity;
import com.ezee.model.entity.project.EzeeProject;
import com.ezee.web.common.ui.css.EzeeGwtOverridesResources;
import com.ezee.web.common.ui.grid.EzeeGridModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SingleSelectionModel;

public abstract class EzeeProjectDataGrid<T extends EzeeDatabaseEntity> extends Composite {

	protected static final int DEFAULT_PAGE_SIZE = 40;
	protected static final int DEFAULT_GRID_SIZE = 600;

	private static EzeeProjectDataGridUiBinder uiBinder = GWT.create(EzeeProjectDataGridUiBinder.class);

	interface EzeeProjectDataGridUiBinder extends UiBinder<Widget, EzeeProjectDataGrid<?>> {
	}

	@UiField(provided = true)
	protected DataGrid<T> grid;

	protected EzeeGridModel<T> model;

	protected final EzeeProject project;

	public EzeeProjectDataGrid(final EzeeProject project) {
		this.project = project;
		init();
		initWidget(uiBinder.createAndBindUi(this));
	}

	private void init() {
		initGrid();
		loadEntities();
	}

	protected void initGrid() {
		grid = new DataGrid<T>(DEFAULT_PAGE_SIZE, EzeeGwtOverridesResources.INSTANCE);
		grid.setMinimumTableWidth(DEFAULT_GRID_SIZE, Style.Unit.PX);
		grid.setSelectionModel(new SingleSelectionModel<>());
	}

	protected abstract void loadEntities();

	public final DataGrid<T> getGrid() {
		return grid;
	}

	public final EzeeGridModel<T> getModel() {
		return model;
	}

	public T getSelected() {
		if (model.getHandler().getList().size() > ZERO) {
			return grid.getVisibleItem(grid.getKeyboardSelectedRow());
		}
		return null;
	}
}
