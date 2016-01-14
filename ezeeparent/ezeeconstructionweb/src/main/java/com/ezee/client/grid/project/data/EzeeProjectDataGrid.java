package com.ezee.client.grid.project.data;

import static com.ezee.common.EzeeCommonConstants.ONE;
import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_ENTER;
import static com.google.gwt.user.client.Event.ONCONTEXTMENU;

import com.ezee.client.grid.project.EzeeProjectDetail;
import com.ezee.model.entity.EzeeDatabaseEntity;
import com.ezee.web.common.ui.css.EzeeGwtOverridesResources;
import com.ezee.web.common.ui.grid.EzeeGridModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PopupPanel;
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

	protected final EzeeProjectDetail projectDetail;

	protected PopupPanel contextMenu;

	public EzeeProjectDataGrid(final EzeeProjectDetail projectDetail) {
		this.projectDetail = projectDetail;
		init();
		initWidget(uiBinder.createAndBindUi(this));
	}

	private void init() {
		initGrid();
		loadEntities();
		initContextMenu();
	}

	protected void initGrid() {
		grid = new DataGrid<T>(DEFAULT_PAGE_SIZE, EzeeGwtOverridesResources.INSTANCE);
		grid.setMinimumTableWidth(DEFAULT_GRID_SIZE, Style.Unit.PX);
		grid.addDomHandler(new EzeeGridDoubleClickHandler(), DoubleClickEvent.getType());
		grid.addDomHandler(new EzeeGridKeyPressHandler(), KeyPressEvent.getType());
		grid.setSelectionModel(new SingleSelectionModel<>());
	}

	protected abstract void loadEntities();

	public void reloadEntities() {
		loadEntities();
	}

	public void addEntity(T entity, int index) {
		getModel().getHandler().getList().add(index, entity);
		getGrid().getSelectionModel().setSelected(entity, true);
	}

	public void addEntity(T entity) {
		getModel().getHandler().getList().add(entity);
		getGrid().getSelectionModel().setSelected(entity, true);
	}

	public void removeEntity(T entity) {
		int index = getIndex(entity);
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

	public void setSelected(int index) {
		if (index >= ZERO && model.getHandler().getList().size() > index) {
			T entity = model.getHandler().getList().get(index);
			getGrid().getSelectionModel().setSelected(entity, true);
		}
	}

	public int getIndex(T entity) {
		return getModel().getHandler().getList().indexOf(entity);
	}

	private void initContextMenu() {
		contextMenu = new PopupPanel(true);
		contextMenu.add(createContextMenu());
		contextMenu.hide();
		grid.sinkEvents(ONCONTEXTMENU);
		grid.addHandler(new ContextMenuHandler() {
			@Override
			public void onContextMenu(final ContextMenuEvent event) {
				event.preventDefault();
				event.stopPropagation();
				contextMenu.setPopupPosition(event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY());
				contextMenu.show();
			}
		}, ContextMenuEvent.getType());

	}

	private MenuBar createContextMenu() {
		MenuBar menu = new MenuBar(true);
		menu.setAnimationEnabled(true);
		menu.addItem("New", new Command() {
			@Override
			public void execute() {
				newEntity();
				contextMenu.hide();
			}
		});
		menu.addItem("Edit", new Command() {
			@Override
			public void execute() {
				editEntity();
				contextMenu.hide();
			}
		});
		menu.addItem("Delete", new Command() {
			@Override
			public void execute() {
				deleteEntity();
				contextMenu.hide();
			}
		});
		return menu;
	}

	private class EzeeGridDoubleClickHandler implements DoubleClickHandler {

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			editEntity();
		}
	}

	private class EzeeGridKeyPressHandler implements KeyPressHandler {

		@Override
		public void onKeyPress(KeyPressEvent event) {
			if (event.getNativeEvent().getKeyCode() == KEY_ENTER) {
				editEntity();
			}
		}
	}

	protected abstract void newEntity();

	protected abstract void editEntity();

	protected abstract void deleteEntity();
}