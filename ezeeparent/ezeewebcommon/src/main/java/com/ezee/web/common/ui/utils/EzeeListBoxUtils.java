package com.ezee.web.common.ui.utils;

import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.common.string.EzeeStringUtils.hasLength;

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
		if (hasLength(value)) {
			for (int i = ZERO; i < listBox.getItemCount(); i++) {
				if (value.equals(listBox.getItemText(i))) {
					return i;
				}
			}
		}
		return ZERO;
	}

	public static void setValue(final String value, final ListBox listBox) {
		if (hasLength(value)) {
			int index = getItemIndex(value, listBox);
			if (index >= ZERO) {
				listBox.setSelectedIndex(index);
			}
		}
	}
}
