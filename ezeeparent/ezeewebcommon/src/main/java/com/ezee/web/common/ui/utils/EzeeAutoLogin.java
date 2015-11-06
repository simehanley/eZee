package com.ezee.web.common.ui.utils;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static com.ezee.web.common.EzeeWebCommonConstants.REMEMBER_ME;
import static com.ezee.web.common.EzeeWebCommonConstants.REMEMBER_ME_USER;

import com.ezee.model.entity.EzeeUser;
import com.google.gwt.storage.client.Storage;

public final class EzeeAutoLogin {

	private final Storage storage = Storage.getLocalStorageIfSupported();

	public boolean doAutoLogin() {
		return isRememberMeSet() && isRememberUserNameSet();
	}

	public boolean isRememberMeSet() {
		return isSupported() && Boolean.valueOf(storage.getItem(REMEMBER_ME));
	}

	public boolean isRememberUserNameSet() {
		return isSupported() && hasLength(storage.getItem(REMEMBER_ME_USER));
	}

	public void setRememberMe() {
		if (isSupported()) {
			storage.setItem(REMEMBER_ME, "true");
		}
	}

	public void setRememberMeUser(final String username) {
		if (isSupported()) {
			storage.setItem(REMEMBER_ME_USER, username);
		}
	}

	public void unsetRememberMe() {
		if (isSupported()) {
			storage.removeItem(REMEMBER_ME);
		}
	}

	public void unsetRememberMeUser() {
		if (isSupported()) {
			storage.removeItem(REMEMBER_ME_USER);
		}
	}

	public String getRememberMeUserName() {
		if (isRememberUserNameSet()) {
			return storage.getItem(REMEMBER_ME_USER);
		}
		return null;
	}

	public void clearAutoLogin() {
		if (isSupported()) {
			storage.removeItem(REMEMBER_ME);
			storage.removeItem(REMEMBER_ME_USER);
		}
	}

	public boolean isSupported() {
		return storage != null;
	}

	public EzeeUser getAutoLoginUser() {
		if (doAutoLogin()) {
			return new EzeeUser(EMPTY_STRING, EMPTY_STRING, getRememberMeUserName(), EMPTY_STRING, EMPTY_STRING,
					EMPTY_STRING, EMPTY_STRING);
		}
		return null;
	}
}