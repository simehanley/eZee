package com.ezee.client.grid;

import com.ezee.model.entity.EzeeDatabaseEntity;
import com.google.gwt.user.cellview.client.DataGrid;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeHasGrid<T extends EzeeDatabaseEntity> {

	DataGrid<T> getGrid();
}
