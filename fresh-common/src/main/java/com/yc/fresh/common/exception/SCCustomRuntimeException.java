package com.yc.fresh.common.exception;

public class SCCustomRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -992414954170234917L;
	public SCCustomRuntimeException() {
		super();
	}

	public SCCustomRuntimeException(String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SCCustomRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SCCustomRuntimeException(String message) {
		super(message);
	}

	public SCCustomRuntimeException(Throwable cause) {
		super(cause);
	}
}
