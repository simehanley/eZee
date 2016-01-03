package com.ezee.web.common.ui.utils;

import static com.google.gwt.event.dom.client.KeyCodes.KEY_TAB;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class EzeeRichTextAreaUtils {

	public static final class TabKeyPressHandler implements KeyPressHandler {

		private final Widget[] next;

		private final Widget[] previous;

		public TabKeyPressHandler(final Widget[] previous, final Widget[] next) {
			this.next = next;
			this.previous = previous;
		}

		@Override
		public void onKeyPress(final KeyPressEvent event) {
			if (isTabKeyPress(event)) {
				event.preventDefault();
				event.stopPropagation();
				if (event.isAltKeyDown() || event.isShiftKeyDown()) {
					if (previous != null) {
						setFocus(previous);
					}
				} else {
					if (next != null) {
						setFocus(next);
					}
				}
			}
		}

		private boolean isTabKeyPress(final KeyPressEvent event) {
			int code = event.getNativeEvent().getKeyCode();
			return (code == KEY_TAB);
		}

		private void setFocus(final Widget[] widgets) {
			for (Widget widget : widgets) {
				if (widget instanceof FocusWidget) {
					FocusWidget focusWidget = (FocusWidget) widget;
					if (focusWidget.isEnabled()) {
						focusWidget.setFocus(true);
						break;
					}
				} else if (widget instanceof DateBox) {
					DateBox dateBox = (DateBox) widget;
					if (dateBox.isEnabled()) {
						dateBox.setFocus(true);
						break;
					}
				} else if (widget instanceof Button) {
					Button button = (Button) widget;
					if (button.isEnabled()) {
						button.setFocus(true);
						break;
					}
				}
			}
		}
	}
}