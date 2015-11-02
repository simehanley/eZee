package com.ezee.web.common.ui.utils;

import static com.google.gwt.dom.client.Style.Cursor.DEFAULT;
import static com.google.gwt.dom.client.Style.Cursor.POINTER;
import static com.google.gwt.dom.client.Style.Cursor.WAIT;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.RootPanel;

public final class EzeeCursorUtils {

	private EzeeCursorUtils() {
	}

	public static void showPointerCursor() {
		getStyle().setCursor(POINTER);
	}
	
	public static void showWaitCursor() {
		getStyle().setCursor(WAIT);
	}

	public static void showDefaultCursor() {
		getStyle().setCursor(DEFAULT);
	}

	private static Style getStyle() {
		return RootPanel.getBodyElement().getStyle();
	}
}