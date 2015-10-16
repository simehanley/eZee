package com.ezee.client.grid;

import static com.google.gwt.user.client.Event.ONCONTEXTMENU;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.model.entity.EzeeFinancialEntity;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PopupPanel;

public abstract class EzeeFinancialEntityGrid<T extends EzeeFinancialEntity> extends EzeeGrid<T> {

	public EzeeFinancialEntityGrid(final EzeeInvoiceServiceAsync service) {
		super(service);
	}

	@Override
	public void initContextMenu() {
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

	private void deleteEntity() {
	}

	private void newEntity() {
	}

	private void editEntity() {
	}
}