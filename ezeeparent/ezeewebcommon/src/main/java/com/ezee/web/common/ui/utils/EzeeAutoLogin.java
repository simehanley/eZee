package com.ezee.web.common.ui.utils;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.web.common.EzeeWebCommonConstants.LOCAL_STORAGE;
import static com.ezee.web.common.EzeeWebCommonConstants.REMEMBER_ME;
import static com.ezee.web.common.EzeeWebCommonConstants.REMEMBER_ME_USER;

import com.ezee.model.entity.EzeeUser;

public final class EzeeAutoLogin {

	public boolean doAutoLogin() {
		return isRememberMeSet() && isRememberUserNameSet();
	}

	public boolean isRememberMeSet() {
		return isSupported() && LOCAL_STORAGE.isSet(REMEMBER_ME);
	}

	public boolean isRememberUserNameSet() {
		return isSupported() && LOCAL_STORAGE.isSet(REMEMBER_ME_USER);
	}

	public void setRememberMe() {
		if (isSupported()) {
			LOCAL_STORAGE.setValue(REMEMBER_ME, "true");
		}
	}

	public void setRememberMeUser(final String username) {
		if (isSupported()) {
			LOCAL_STORAGE.setValue(REMEMBER_ME_USER, username);
		}
	}

	public void unsetRememberMe() {
		if (isSupported()) {
			LOCAL_STORAGE.removeValue(REMEMBER_ME);
		}
	}

	public void unsetRememberMeUser() {
		if (isSupported()) {
			LOCAL_STORAGE.removeValue(REMEMBER_ME_USER);
		}
	}

	public String getRememberMeUserName() {
		if (isRememberUserNameSet()) {
			return LOCAL_STORAGE.getValue(REMEMBER_ME_USER);
		}
		return null;
	}

	public void clearAutoLogin() {
		if (isSupported()) {
			LOCAL_STORAGE.removeValue(REMEMBER_ME);
			LOCAL_STORAGE.removeValue(REMEMBER_ME_USER);
		}
	}

	public boolean isSupported() {
		return LOCAL_STORAGE.isSupported();
	}

	public EzeeUser getAutoLoginUser() {
		if (doAutoLogin()) {
			return new EzeeUser(EMPTY_STRING, EMPTY_STRING, getRememberMeUserName(), EMPTY_STRING, EMPTY_STRING,
					EMPTY_STRING, EMPTY_STRING);
		}
		return null;
	}
}