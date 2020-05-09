package com.yc.fresh.common.exception;

public class SCTokenExpiredRuntimeException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = -992414954170234917L;
	public SCTokenExpiredRuntimeException() {
		super();
	}

	public SCTokenExpiredRuntimeException(String message, Throwable cause, boolean enableSuppression,
                                          boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SCTokenExpiredRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SCTokenExpiredRuntimeException(String message) {
		super(message);
	}

	public SCTokenExpiredRuntimeException(Throwable cause) {
		super(cause);
	}
}
