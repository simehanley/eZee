package com.ezee.server.exception;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceException extends RuntimeException {

	private static final long serialVersionUID = -1199185003369477537L;

	public EzeeInvoiceException() {
		super();
	}

	public EzeeInvoiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EzeeInvoiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public EzeeInvoiceException(String message) {
		super(message);
	}

	public EzeeInvoiceException(Throwable cause) {
		super(cause);
	}
}