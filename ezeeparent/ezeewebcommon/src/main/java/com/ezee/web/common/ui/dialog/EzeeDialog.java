package com.ezee.web.common.ui.dialog;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusWidget;

/**
 * 
 * @author siborg
 *
 */
public class EzeeDialog extends DialogBox {

	public EzeeDialog() {
		super();
	}

	public EzeeDialog(boolean autoHide, boolean modal, Caption captionWidget) {
		super(autoHide, modal, captionWidget);
	}

	public EzeeDialog(boolean autoHide, boolean modal) {
		super(autoHide, modal);
	}

	public EzeeDialog(boolean autoHide) {
		super(autoHide);
	}

	public EzeeDialog(Caption captionWidget) {
		super(captionWidget);
	}

	public void close() {
		this.hide(true);
	}

	public void setFocus(final FocusWidget widget) {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				widget.setFocus(true);
			}
		});
	}
}