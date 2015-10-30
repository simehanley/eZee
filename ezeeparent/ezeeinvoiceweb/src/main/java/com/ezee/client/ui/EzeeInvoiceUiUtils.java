package com.ezee.client.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.model.entity.EzeeHasName;
import com.google.gwt.user.client.ui.ListBox;

/**
 * 
 * @author siborg
 *
 */
public final class EzeeInvoiceUiUtils {

	private EzeeInvoiceUiUtils() {
	}

	public static <K extends EzeeHasName> void loadEntities(final Class<K> clazz, final ListBox listBox,
			final EzeeInvoiceEntityCache cache) {
		Map<String, EzeeHasName> entities = cache.getEntities(clazz);
		List<String> entityNames = new ArrayList<>(entities.keySet());
		Collections.sort(entityNames);
		for (String key : entityNames) {
			listBox.addItem(key);
		}
	}
}