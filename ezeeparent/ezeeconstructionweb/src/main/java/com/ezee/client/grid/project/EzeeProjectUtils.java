package com.ezee.client.grid.project;

import static com.ezee.common.EzeeCommonConstants.MINUS_ONE;
import static com.ezee.common.EzeeCommonConstants.ZERO;

import com.ezee.model.entity.project.EzeeProject;
import com.google.gwt.user.client.ui.TabLayoutPanel;

public final class EzeeProjectUtils {

	private EzeeProjectUtils() {
	}

	public static int getIndexOfProject(final EzeeProject project, final TabLayoutPanel tab) {
		for (int i = ZERO; i < tab.getWidgetCount(); i++) {
			if (tab.getWidget(i) instanceof EzeeProjectDetail) {
				EzeeProjectDetail detail = (EzeeProjectDetail) tab.getWidget(i);
				if (detail.getProject().equals(project)) {
					return i;
				}
			}
		}
		return MINUS_ONE;
	}
}