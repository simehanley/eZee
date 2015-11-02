package com.ezee.web.common.ui.login;

import com.ezee.model.entity.EzeeUser;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeLoginListener {

	void loginSuccessful(EzeeUser user);
}