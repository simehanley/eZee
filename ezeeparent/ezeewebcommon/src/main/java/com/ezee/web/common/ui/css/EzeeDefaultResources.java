package com.ezee.web.common.ui.css;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeDefaultResources extends ClientBundle {

	public static EzeeDefaultResources INSTANCE = GWT.create(EzeeDefaultResources.class);

	interface EzeeDefaultCss extends CssResource {

		String lightgray();

		String lightblue();

		String mediumblue();

		String green();

		String greenforeground();

		String lightorange();

		String lightorangeforeground();

		String white();

		String black();

		String header();

		String headerLabel();

		String footer();

		String footerLabel();

		String footerLabelUnderline();

		String navigation();

		String navigationLink();

		String toolbar();

		String gwtLabelBigCentered();

		String gwtLabelBig();

		String gwtLabelMedium();

		String gwtLabelSmall();

		String gwtTextBoxSmall();

		String gwtTextBoxMedium();

		String gwtTextBoxLarge();

		String gwtListBoxSmall();

		String gwtListBoxMedium();

		String gwtPanelBorder();

		String gwtButton();

		String gwtPushButton();

		String gwtRichTextAreaMedium();

		String gwtDateBoxSmall();

		String gwtCheckBoxSmall();

		String gwtCheckBoxSmallWithBorder();

		String gwtFileUploadLarge();

		String gwtTextBoxMediumEmail();

		String error();

		String gwtStackPanelLink();

		String gwtLink();
	}

	@Source("EzeeDefault.css")
	EzeeDefaultCss css();
}
