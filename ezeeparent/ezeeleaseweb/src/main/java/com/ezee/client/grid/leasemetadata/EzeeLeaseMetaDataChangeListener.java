package com.ezee.client.grid.leasemetadata;

import com.ezee.model.entity.lease.EzeeLeaseMetaData;

public interface EzeeLeaseMetaDataChangeListener {

	void metaDataSaved(EzeeLeaseMetaData metaData);

	void metaDataDeleted(EzeeLeaseMetaData metaData);
}