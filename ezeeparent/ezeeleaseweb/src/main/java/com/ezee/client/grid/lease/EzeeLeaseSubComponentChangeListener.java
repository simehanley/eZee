package com.ezee.client.grid.lease;

import com.ezee.model.entity.EzeeDateSortableDatabaseEntity;

public interface EzeeLeaseSubComponentChangeListener {

	void subComponentSaved(EzeeDateSortableDatabaseEntity entity);

	void subComponentDeleted(EzeeDateSortableDatabaseEntity entity);
}
