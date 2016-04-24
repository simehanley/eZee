package com.ezee.web.common.ui.grid;

import static com.ezee.common.EzeeCommonConstants.ONE;
import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.web.common.EzeeWebCommonConstants.ENTITY_SERVICE;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_ENTER;
import static com.google.gwt.user.cellview.client.SimplePager.TextLocation.CENTER;
import static com.google.gwt.user.client.Event.ONCONTEXTMENU;
import static java.util.logging.Level.SEVERE;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.ezee.model.entity.EzeeDatabaseEntity;
import com.ezee.model.entity.filter.EzeeEmptyFilter;
import com.ezee.model.entity.filter.EzeeEntityFilter;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.css.EzeeGwtOverridesResources;
import com.ezee.web.common.ui.utils.EzeeCursorUtils;
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
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

public abstract class EzeeGrid<T extends EzeeDatabaseEntity> extends Composite
		implements EzeeHasGrid<T>, EzeeCreateUpdateDeleteEntityHandler<T> {

	private static final Logger log = Logger.getLogger("EzeeGrid");

	public static final int DEFAULT_PAGE_SIZE = 40;
	public static final int DEFAULT_GRID_SIZE = 600;

	private static EzeeGridUiBinder uiBinder = GWT.create(EzeeGridUiBinder.class);

	@UiField
	protected DockLayoutPanel main;

	@UiField(provided = true)
	protected DataGrid<T> grid;

	@UiField(provided = true)
	protected SimplePager pager;

	@UiField(provided = true)
	protected HorizontalPanel filterpanel;

	protected Button btnRefresh;

	protected Button btnClear;

	protected EzeeGridModel<T> model;

	protected EzeeGridToolbar<T> toolBar;

	protected EzeeEntityCache cache;

	protected PopupPanel contextMenu;

	interface EzeeGridUiBinder extends UiBinder<Widget, EzeeGrid<?>> {
	}

	public EzeeGrid(final EzeeEntityCache cache) {
		this(cache, false);
	}

	public EzeeGrid(final EzeeEntityCache cache, final boolean disableContextMenu) {
		this(cache, DEFAULT_PAGE_SIZE, disableContextMenu);
	}

	public EzeeGrid(final EzeeEntityCache cache, final int pageSize, final boolean disableContextMenu) {
		this.cache = cache;
		init(pageSize, disableContextMenu);
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public final DataGrid<T> getGrid() {
		return grid;
	}

	@SuppressWarnings("unchecked")
	protected T getSelected() {
		if (model.getHandler().getList().size() > ZERO) {
			if (grid.getSelectionModel() instanceof SingleSelectionModel<?>) {
				return ((SingleSelectionModel<T>) grid.getSelectionModel()).getSelectedObject();
			} else if (grid.getSelectionModel() instanceof MultiSelectionModel<?>) {
				Set<T> selected = ((MultiSelectionModel<T>) grid.getSelectionModel()).getSelectedSet();
				if (!isEmpty(selected)) {
					return new ArrayList<>(selected).get(ZERO);
				}
			}
		}
		return null;
	}

	protected void clearSelected() {
		T selected = getSelected();
		if (selected != null) {
			grid.getSelectionModel().setSelected(selected, false);
		}
	}

	protected void setSelected(int index) {
		if (index >= ZERO && model.getHandler().getList().size() > index) {
			T entity = model.getHandler().getList().get(index);
			getGrid().getSelectionModel().setSelected(entity, true);
		}
	}

	protected void init(final int pageSize, final boolean disableContextMenu) {
		initFilter();
		initGrid(pageSize);
		loadEntities();
		initContextMenu(disableContextMenu);
	}

	protected void initGrid(final int pageSize) {
		grid = new DataGrid<T>(pageSize, EzeeGwtOverridesResources.INSTANCE);
		grid.setMinimumTableWidth(DEFAULT_GRID_SIZE, Style.Unit.PX);
		grid.addDomHandler(new EzeeGridDoubleClickHandler(), DoubleClickEvent.getType());
		grid.addDomHandler(new EzeeGridKeyPressHandler(), KeyPressEvent.getType());
		SingleSelectionModel<T> model = new SingleSelectionModel<>();
		grid.setSelectionModel(model);
		SimplePager.Resources resources = GWT.create(SimplePager.Resources.class);
		pager = new SimplePager(CENTER, resources, false, ZERO, true);
		pager.setDisplay(grid);
	}

	protected void initFilter() {
		filterpanel = new HorizontalPanel();
	}

	protected void setEntities(final List<T> entities) {
		model.getHandler().getList().clear();
		EzeeEntityFilter<T> filter = resolveFilter();
		for (T entity : entities) {
			if (filter.include(entity)) {
				model.getHandler().getList().add(entity);
			}
		}
		grid.redraw();
		setSelected(ZERO);
	}

	protected void loadEntities() {
		EzeeCursorUtils.showWaitCursor();
		ENTITY_SERVICE.getEntities(getGridClass(), new AsyncCallback<List<T>>() {

			@Override
			public void onFailure(Throwable caught) {
				log.log(SEVERE, "Error loading entities of type '" + getGridClass() + "'", caught);
				EzeeCursorUtils.showDefaultCursor();
			}

			@Override
			public void onSuccess(List<T> entities) {
				setEntities(entities);
				EzeeCursorUtils.showDefaultCursor();
			}
		});
	}

	protected void initContextMenu(final boolean disableContextMenu) {
		if (!disableContextMenu) {
			contextMenu = new PopupPanel(true);
			contextMenu.add(createContextMenu());
			contextMenu.hide();
			grid.sinkEvents(ONCONTEXTMENU);
			grid.addHandler(new ContextMenuHandler() {
				@Override
				public void onContextMenu(final ContextMenuEvent event) {
					event.preventDefault();
					event.stopPropagation();
					contextMenu.setPopupPosition(event.getNativeEvent().getClientX(),
							event.getNativeEvent().getClientY());
					contextMenu.show();
				}
			}, ContextMenuEvent.getType());
		}
	}

	protected MenuBar createContextMenu() {
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

	@Override
	public void onSave(T entity) {
		if (entity != null) {
			List<T> entities = model.getHandler().getList();
			if (entities.contains(entity)) {
				int index = entityIndex(entity);
				entities.remove(index);
				entities.add(index, entity);
			} else {
				entities.add(ZERO, entity);
			}
			getGrid().getSelectionModel().setSelected(entity, true);
		}
	}

	@Override
	public void onDelete(T entity) {
		if (entity != null) {
			int index = model.getHandler().getList().indexOf(entity);
			model.getHandler().getList().remove(entity);
			int size = model.getHandler().getList().size();
			if (size > ZERO) {
				if (size == index) {
					index = size - ONE;
				}
				T selected = model.getHandler().getList().get(index);
				getGrid().getSelectionModel().setSelected(selected, true);
			}
		}
	}

	private int entityIndex(T entity) {
		return model.getHandler().getList().indexOf(entity);
	}

	protected EzeeEntityFilter<T> resolveFilter() {
		if (toolBar != null) {
			return toolBar.resolveFilter();
		}
		return new EzeeEmptyFilter<T>();
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

	public final DockLayoutPanel getMain() {
		return main;
	}

	public final EzeeGridModel<T> getModel() {
		return model;
	}

	public abstract void deleteEntity();

	public abstract void newEntity();

	public abstract void editEntity();

	public abstract String getGridClass();
}