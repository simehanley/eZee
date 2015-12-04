package com.ezee.web.common.ui.grid;

import static com.google.gwt.event.dom.client.KeyCodes.KEY_ENTER;

import com.ezee.model.entity.EzeeDatabaseEntity;
import com.ezee.model.entity.filter.EzeeEntityFilter;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Composite;

/**
 * 
 * @author siborg
 *
 */
public abstract class EzeeGridToolbar<T extends EzeeDatabaseEntity> extends Composite {

	protected final EzeeGrid<T> grid;

	public EzeeGridToolbar(final EzeeGrid<T> grid) {
		this.grid = grid;
	}

	public class EzeeToolbarKeyPressHandler implements KeyPressHandler {

		@Override
		public void onKeyPress(KeyPressEvent event) {
			if (event.getNativeEvent().getKeyCode() == KEY_ENTER) {
				grid.loadEntities();
			}
		}
	}

	public class EzeeToolbarClearHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			clearToolbar();
			grid.loadEntities();
		}
	}

	public class EzeeToolbarRefreshHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			grid.loadEntities();
		}
	}

	public class EzeeToolbarValueChangeHandler implements ValueChangeHandler<Boolean> {

		@Override
		public void onValueChange(ValueChangeEvent<Boolean> event) {
			grid.loadEntities();
		}
	}

	public abstract void init();

	public abstract void clearToolbar();

	public abstract EzeeEntityFilter<T> resolveFilter();
}