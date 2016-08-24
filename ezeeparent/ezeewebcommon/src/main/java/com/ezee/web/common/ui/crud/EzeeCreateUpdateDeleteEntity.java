package com.ezee.web.common.ui.crud;

import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.create;

import com.ezee.model.entity.EzeeDatabaseEntity;
import com.ezee.model.entity.EzeeUser;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.dialog.EzeeDialog;

/**
 * 
 * @author siborg
 *
 */
public abstract class EzeeCreateUpdateDeleteEntity<T extends EzeeDatabaseEntity> extends EzeeDialog {

	protected static final int NEW_HEADER_INDEX = 0;
	protected static final int EDIT_HEADER_INDEX = 1;
	protected static final int DELETE_HEADER_INDEX = 2;
	protected static final int VIEW_HEADER_INDEX = 3;

	protected T entity;

	protected final EzeeUser user;

	protected final EzeeEntityCache cache;

	protected final EzeeCreateUpdateDeleteEntityType type;

	protected final EzeeCreateUpdateDeleteEntityHandler<T> handler;

	protected final String[] headers;

	public EzeeCreateUpdateDeleteEntity(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<T> handler, final String[] headers) {
		this(cache, handler, null, create, headers);
	}

	public EzeeCreateUpdateDeleteEntity(final EzeeEntityCache cache, EzeeCreateUpdateDeleteEntityHandler<T> handler,
			final T entity, final EzeeCreateUpdateDeleteEntityType type, final String[] headers) {
		this(null, cache, handler, entity, type, headers);
	}

	public EzeeCreateUpdateDeleteEntity(final EzeeUser user, final EzeeEntityCache cache,
			EzeeCreateUpdateDeleteEntityHandler<T> handler, final T entity, final EzeeCreateUpdateDeleteEntityType type,
			final String[] headers) {
		super(false, false);
		this.user = user;
		this.cache = cache;
		this.handler = handler;
		this.entity = entity;
		this.type = type;
		this.headers = headers;
	}

	public final T getEntity() {
		return entity;
	}

	protected abstract void initialise();

	protected abstract void bind();
}