package com.ezee.web.common.ui.dialog;

import com.google.gwt.user.client.ui.DialogBox;

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
}