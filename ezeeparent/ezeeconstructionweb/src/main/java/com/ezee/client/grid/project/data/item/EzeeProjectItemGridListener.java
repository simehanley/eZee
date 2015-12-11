package com.ezee.client.grid.project.data.item;

import com.ezee.model.entity.project.EzeeProjectItem;

public interface EzeeProjectItemGridListener {

	void itemSelected(EzeeProjectItem item);

	void itemDeleted(EzeeProjectItem item);
}
