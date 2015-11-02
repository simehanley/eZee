package com.ezee.web.common.ui.register;

import com.ezee.model.entity.EzeeUser;
import com.ezee.web.common.ui.AbstractUserResult;

/**
 * 
 * @author siborg
 *
 */
public class EzeeRegisterResult extends AbstractUserResult {

	private static final long serialVersionUID = 790420344556739341L;

	public EzeeRegisterResult() {
		super();
	}

	public EzeeRegisterResult(final EzeeUser user, final String error) {
		super(user, error);
	}
}