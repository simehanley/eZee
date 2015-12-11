package com.ezee.web.common.ui.grid;

import com.ezee.model.entity.EzeeDatabaseEntity;

public interface EzeeGridModelListener<T extends EzeeDatabaseEntity> {
	void modelUpdated(T entity);
}