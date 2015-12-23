package com.ezee.web.common.localstorage;

import static com.ezee.common.string.EzeeStringUtils.hasLength;

import com.google.gwt.storage.client.Storage;

public class EzeeLocalStroage {

	private final Storage storage = Storage.getLocalStorageIfSupported();

	public boolean isSet(final String key) {
		if (isSupported()) {
			return hasLength(storage.getItem(key));
		}
		return false;
	}

	public String getValue(final String key) {
		if (isSupported()) {
			return storage.getItem(key);
		}
		return null;
	}

	public void setValue(final String key, final String value) {
		if (isSupported()) {
			storage.setItem(key, value);
		}
	}

	public void removeValue(final String key) {
		if (isSupported()) {
			storage.removeItem(key);
		}
	}

	public boolean isSupported() {
		return storage != null;
	}
}