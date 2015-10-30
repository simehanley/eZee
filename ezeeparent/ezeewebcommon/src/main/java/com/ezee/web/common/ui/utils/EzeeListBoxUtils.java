package com.ezee.web.common.ui.utils;

import static com.ezee.common.EzeeCommonConstants.ZERO;

import com.google.gwt.user.client.ui.ListBox;

/**
 * 
 * @author siborg
 *
 */
public final class EzeeListBoxUtils {

	private EzeeListBoxUtils() {
	}

	public static <K extends Enum<K>> void loadEnums(final Enum<K>[] ezeeEnum, final ListBox listBox) {
		if (ezeeEnum != null && ezeeEnum.length > ZERO) {
			for (Enum<K> e : ezeeEnum) {
				listBox.addItem(e.name());
			}
		}
	}

	public static int getItemIndex(final String value, final ListBox listBox) {
		if (value != null) {
			for (int i = ZERO; i < listBox.getItemCount(); i++) {
				if (value.equals(listBox.getItemText(i))) {
					return i;
				}
			}
		}
		return ZERO;
	}
}
