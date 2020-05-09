package com.yc.fresh.common.exception;

public class SCRedisRuntimeException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 66958891076369466L;

	public SCRedisRuntimeException() {
		super();
	}

	public SCRedisRuntimeException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SCRedisRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SCRedisRuntimeException(String message) {
		super(message);
	}

	public SCRedisRuntimeException(Throwable cause) {
		super(cause);
	}

}
