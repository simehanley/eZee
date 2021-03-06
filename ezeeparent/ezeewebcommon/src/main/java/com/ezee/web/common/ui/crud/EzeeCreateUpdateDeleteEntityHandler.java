package com.ezee.web.common.ui.crud;

import com.ezee.model.entity.EzeeDatabaseEntity;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeCreateUpdateDeleteEntityHandler<T extends EzeeDatabaseEntity> {

	void onSave(T entity);

	void onDelete(T entity);
}