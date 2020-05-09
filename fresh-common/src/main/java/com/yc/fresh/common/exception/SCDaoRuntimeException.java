package com.yc.fresh.common.exception;

public class SCDaoRuntimeException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4123613548582838434L;

	public SCDaoRuntimeException() {
		super();
	}

	public SCDaoRuntimeException(String message, Throwable cause, boolean enableSuppression,
                                 boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SCDaoRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SCDaoRuntimeException(String message) {
		super(message);
	}

	public SCDaoRuntimeException(Throwable cause) {
		super(cause);
	}
}
