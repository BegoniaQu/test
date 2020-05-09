package com.yc.fresh.common.exception;

public class SCTargetNotFoundRuntimeException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2459554788766202661L;

	public SCTargetNotFoundRuntimeException() {
		super();
	}

	public SCTargetNotFoundRuntimeException(String message, Throwable cause, boolean enableSuppression,
                                            boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SCTargetNotFoundRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SCTargetNotFoundRuntimeException(String message) {
		super(message);
	}

	public SCTargetNotFoundRuntimeException(Throwable cause) {
		super(cause);
	}


}
