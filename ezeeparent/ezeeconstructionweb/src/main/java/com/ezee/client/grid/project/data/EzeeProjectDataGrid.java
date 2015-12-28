package com.ezee.client.grid.project.data;

import static com.ezee.common.EzeeCommonConstants.ONE;
import static com.ezee.common.EzeeCommonConstants.ZERO;

import java.util.Map;

import com.ezee.client.grid.project.EzeeProjectDetail;
import com.ezee.model.entity.EzeeDatabaseEntity;
import com.ezee.model.entity.EzeeContractor;
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

	protected final Map<String, EzeeContractor> resources;

	protected final EzeeProjectDetail projectDetail;

	public EzeeProjectDataGrid(final EzeeProjectDetail projectDetail) {
		this(projectDetail, null);
	}

	public EzeeProjectDataGrid(final EzeeProjectDetail projectDetail, final Map<String, EzeeContractor> resources) {
		this.projectDetail = projectDetail;
		this.resources = resources;
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

	public abstract T newEntity();

	public void addEntity(T entity) {
		getModel().getHandler().getList().add(entity);
		getGrid().getSelectionModel().setSelected(entity, true);
	}

	public void removeEntity(T entity) {
		int index = getModel().getHandler().getList().indexOf(entity);
		getModel().getHandler().getList().remove(entity);
		int size = getModel().getHandler().getList().size();
		if (size > ZERO) {
			if (size == index) {
				index = size - ONE;
			}
			T selected = getModel().getHandler().getList().get(index);
			getGrid().getSelectionModel().setSelected(selected, true);
		}
	}

	public final DataGrid<T> getGrid() {
		return grid;
	}

	public final EzeeGridModel<T> getModel() {
		return model;
	}

	@SuppressWarnings("unchecked")
	public T getSelected() {
		if (model.getHandler().getList().size() > ZERO) {
			return ((SingleSelectionModel<T>) grid.getSelectionModel()).getSelectedObject();
		}
		return null;
	}

	protected void setSelected(int index) {
		if (index >= ZERO && model.getHandler().getList().size() > index) {
			T entity = model.getHandler().getList().get(index);
			getGrid().getSelectionModel().setSelected(entity, true);
		}
	}
}