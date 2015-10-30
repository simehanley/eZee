package com.ezee.web.common.ui.utils;

import static com.google.gwt.event.dom.client.KeyCodes.KEY_BACKSPACE;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_DELETE;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_TAB;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.TextBox;

public final class EzeeTextBoxUtils {

	private EzeeTextBoxUtils() {
	}

	/** numeric key press handler **/
	public static final class NumericKeyPressHandler implements KeyPressHandler {

		@Override
		public void onKeyPress(final KeyPressEvent event) {
			if (!Character.isDigit(event.getCharCode()) && !isAllowableKeyPress(event)) {
				((TextBox) event.getSource()).cancelKey();
			}
		}

		private boolean isAllowableKeyPress(final KeyPressEvent event) {
			int code = event.getNativeEvent().getKeyCode();
			return (code == KEY_BACKSPACE || code == KEY_DELETE || code == KEY_TAB || event.getCharCode() == '.'
					|| event.getCharCode() == '-');
		}
	}
}
