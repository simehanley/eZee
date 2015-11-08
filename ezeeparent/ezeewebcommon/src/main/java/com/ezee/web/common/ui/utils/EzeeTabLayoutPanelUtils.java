package com.ezee.web.common.ui.utils;

import static com.ezee.common.EzeeCommonConstants.ZERO;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TabLayoutPanel;

/**
 * 
 * @author siborg
 *
 */
public final class EzeeTabLayoutPanelUtils {

	private EzeeTabLayoutPanelUtils() {
	}

	@SuppressWarnings("unchecked")
	public static <T extends IsWidget> T getFirstInstanceOf(final Class<T> clazz, final TabLayoutPanel tab) {
		for (int i = ZERO; i < tab.getWidgetCount(); i++) {
			if (tab.getWidget(i).getClass().equals(clazz)) {
				return (T) tab.getWidget(i);
			}
		}
		return null;
	}
}