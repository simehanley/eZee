package com.ezee.common.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author siborg
 *
 */
public class EzeeException extends RuntimeException implements IsSerializable {

	private static final long serialVersionUID = -3684188044846475876L;

	public EzeeException() {
		super();
	}

	public EzeeException(String message, Throwable cause) {
		super(message, cause);
	}

	public EzeeException(String message) {
		super(message);
	}

	public EzeeException(Throwable cause) {
		super(cause);
	}
}