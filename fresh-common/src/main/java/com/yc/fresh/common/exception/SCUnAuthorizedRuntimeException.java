package com.yc.fresh.common.exception;


public class SCUnAuthorizedRuntimeException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4911269373228999774L;

	public SCUnAuthorizedRuntimeException() {
		super();
	}

	public SCUnAuthorizedRuntimeException(String message, Throwable cause, boolean enableSuppression,
                                          boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SCUnAuthorizedRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SCUnAuthorizedRuntimeException(String message) {
		super(message);
	}

	public SCUnAuthorizedRuntimeException(Throwable cause) {
		super(cause);
	}

}
