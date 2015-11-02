package com.ezee.web.common.ui.login;

import com.ezee.model.entity.EzeeUser;
import com.ezee.web.common.ui.AbstractUserResult;

/**
 * 
 * @author siborg
 *
 */
public class EzeeLoginResult extends AbstractUserResult {

	private static final long serialVersionUID = 7375210136509791540L;

	public EzeeLoginResult() {
		super();
	}

	public EzeeLoginResult(EzeeUser user, String error) {
		super(user, error);
	}
}