package com.yc.fresh.common.exception;

public class SCServiceRuntimeException extends RuntimeException {


	/**
	 *
	 */
	private static final long serialVersionUID = -4123613548582838434L;

	public SCServiceRuntimeException() {
		super();
	}

	public SCServiceRuntimeException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SCServiceRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SCServiceRuntimeException(String message) {
		super(message);
	}

	public SCServiceRuntimeException(Throwable cause) {
		super(cause);
	}
}
