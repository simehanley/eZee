package com.ezee.client.css;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeInvoiceDefaultResources extends ClientBundle {

	public static EzeeInvoiceDefaultResources INSTANCE = GWT.create(EzeeInvoiceDefaultResources.class);

	interface EzeeInvoiceDefaultCss extends CssResource {

		String lightgray();

		String lightblue();

		String mediumblue();

		String green();

		String greenforeground();

		String lightorange();

		String lightorangeforeground();

		String header();

		String headerLabel();

		String footer();

		String footerLabel();

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

		String gwtRichTextAreaMedium();

		String gwtDateBoxSmall();

		String gwtCheckBoxSmall();

		String gwtFileUploadLarge();
		
		String gwtTextBoxMediumEmail();
	}

	@Source("EzeeInvoiceDefault.css")
	EzeeInvoiceDefaultCss css();
}
