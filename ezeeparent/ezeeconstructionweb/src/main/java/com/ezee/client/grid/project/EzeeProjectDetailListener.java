package com.ezee.client.grid.project;

import com.ezee.model.entity.project.EzeeProject;

public interface EzeeProjectDetailListener {
	void detailSaved(EzeeProject project);
	void detailClosed(EzeeProject project);
}