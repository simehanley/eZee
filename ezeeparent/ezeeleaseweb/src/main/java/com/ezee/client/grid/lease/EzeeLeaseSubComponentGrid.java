package com.ezee.client.grid.lease;

import static com.ezee.common.EzeeCommonConstants.MINUS_ONE;
import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import com.ezee.model.entity.EzeeDateSortableDatabaseEntity;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.grid.EzeeGrid;

public abstract class EzeeLeaseSubComponentGrid<T extends EzeeDateSortableDatabaseEntity> extends EzeeGrid<T> {

	private int maxSortOrder;

	private EzeeLeaseSubComponentChangeListener listener;

	public EzeeLeaseSubComponentGrid(final EzeeEntityCache cache, final int pageSize, final boolean disableContextMenu,
			final EzeeLeaseSubComponentChangeListener listener) {
		super(cache, pageSize, disableContextMenu);
		init();
		this.listener = listener;
	}

	private void init() {
		getMain().setWidgetSize(filterpanel, ZERO_DBL);
	}

	@Override
	protected void init(final boolean disableContextMenu) {
		initFilter();
		initGrid();
		initContextMenu(disableContextMenu);
	}

	public void setData(final SortedSet<T> entities) {
		List<T> data = new ArrayList<T>(entities);
		maxSortOrder = isEmpty(entities) ? MINUS_ONE : entities.last().getOrder();
		setEntities(data);
	}

	public final List<T> getData() {
		return model.getHandler().getList();
	}

	@Override
	public void onSave(final T entity) {
		if (entity.getOrder() == null) {
			++maxSortOrder;
			entity.setOrder(maxSortOrder);
		}
		super.onSave(entity);
		listener.subComponentSaved(entity);
	}

	@Override
	public void onDelete(final T entity) {
		super.onDelete(entity);
		listener.subComponentDeleted(entity);
	}
}