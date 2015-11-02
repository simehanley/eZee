package com.ezee.web.common.exception;

/**
 * 
 * @author siborg
 *
 */
public class EzeeWebCommonException extends RuntimeException {

	private static final long serialVersionUID = 4680414058650967470L;

	public EzeeWebCommonException() {
		super();
	}

	public EzeeWebCommonException(String message, Throwable cause) {
		super(message, cause);
	}

	public EzeeWebCommonException(String message) {
		super(message);
	}

	public EzeeWebCommonException(Throwable cause) {
		super(cause);
	}
}
