package com.ezee.web.common.ui.utils;

import static com.google.gwt.event.dom.client.KeyCodes.KEY_BACKSPACE;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_DELETE;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_TAB;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
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

	public static final class TextBoxFocusHandler implements FocusHandler {

		@Override
		public void onFocus(FocusEvent event) {
			if (event.getSource() instanceof TextBox) {
				selectAll((TextBox) event.getSource());
			}
		}

		public void selectAll(final TextBox textBox) {
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				@Override
				public void execute() {
					textBox.selectAll();
				}
			});
		}
	}
}
