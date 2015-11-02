package com.ezee.web.common.ui.register;

import com.ezee.model.entity.EzeeUser;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeRegisterListener {

	void requestNewRegistration();

	void cancelRegistration();

	void registrationSuccess(EzeeUser user);
}
