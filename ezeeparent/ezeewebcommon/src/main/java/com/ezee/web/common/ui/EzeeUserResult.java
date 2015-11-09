package com.ezee.web.common.ui;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;

import java.io.Serializable;

import com.ezee.model.entity.EzeeUser;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author siborg
 *
 */
public class EzeeUserResult implements Serializable, IsSerializable {

	private static final long serialVersionUID = 6044626147702683694L;

	protected EzeeUser user;

	protected String error;

	public EzeeUserResult() {
		this(null, EMPTY_STRING);
	}

	public EzeeUserResult(final EzeeUser user, final String error) {
		this.user = user;
		this.error = error;
	}

	public final EzeeUser getUser() {
		return user;
	}

	public void setUser(final EzeeUser user) {
		this.user = user;
	}

	public void setError(final String error) {
		this.error = error;
	}

	public final String getError() {
		return error;
	}
}
