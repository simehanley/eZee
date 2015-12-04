package com.ezee.client.grid;

import static com.ezee.client.EzeeInvoiceWebConstants.INVOICE_SERVICE;
import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_ENTER;
import static com.google.gwt.user.cellview.client.SimplePager.TextLocation.CENTER;
import static com.google.gwt.user.client.Event.ONCONTEXTMENU;
import static java.util.logging.Level.SEVERE;

import java.util.List;
import java.util.logging.Logger;

import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.client.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.model.entity.EzeeDatabaseEntity;
import com.ezee.model.entity.filter.EzeeEntityFilter;
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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SingleSelectionModel;

public abstract class EzeeGrid<T extends EzeeDatabaseEntity> extends Composite
		implements EzeeHasGrid<T>, EzeeCreateUpdateDeleteEntityHandler<T> {

	private static final Logger log = Logger.getLogger("EzeeGrid");

	protected static final int DEFAULT_PAGE_SIZE = 40;
	protected static final int DEFAULT_GRID_SIZE = 600;

	private static EzeeGridUiBinder uiBinder = GWT.create(EzeeGridUiBinder.class);

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

	protected EzeeInvoiceEntityCache cache;

	protected PopupPanel contextMenu;

	interface EzeeGridUiBinder extends UiBinder<Widget, EzeeGrid<?>> {
	}

	public EzeeGrid(final EzeeInvoiceEntityCache cache) {
		this.cache = cache;
		init();
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public final DataGrid<T> getGrid() {
		return grid;
	}

	protected T getSelected() {
		if (model.getHandler().getList().size() > ZERO) {
			return grid.getVisibleItem(grid.getKeyboardSelectedRow());
		}
		return null;
	}

	private void init() {
		initFilter();
		initGrid();
		loadEntities();
		initContextMenu();
	}

	protected void initGrid() {
		grid = new DataGrid<T>(DEFAULT_PAGE_SIZE, EzeeGwtOverridesResources.INSTANCE);
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

	private void setEntities(final List<T> entities) {
		model.getHandler().getList().clear();
		EzeeEntityFilter<T> filter = resolveFilter();
		for (T entity : entities) {
			if (filter.include(entity)) {
				model.getHandler().getList().add(entity);
			}
		}
		grid.redraw();
	}

	protected void loadEntities() {
		EzeeCursorUtils.showWaitCursor();
		INVOICE_SERVICE.getEntities(getGridClass(), new AsyncCallback<List<T>>() {

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
				entities.add(index, entity);
				entities.remove(++index);
			} else {
				entities.add(entity);
			}
		}
	}

	@Override
	public void onDelete(T entity) {
		if (entity != null) {
			model.getHandler().getList().remove(entity);
		}
	}

	private int entityIndex(T entity) {
		List<T> entities = model.getHandler().getList();
		for (int i = ZERO; i < entities.size(); i++) {
			if (entities.get(i).equals(entity)) {
				return i;
			}
		}
		return ZERO;
	}

	protected EzeeEntityFilter<T> resolveFilter() {
		return toolBar.resolveFilter();
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

	public abstract void deleteEntity();

	public abstract void newEntity();

	public abstract void editEntity();

	public abstract String getGridClass();
}