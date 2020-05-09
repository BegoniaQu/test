package com.yc.fresh.common.exception;

public class SCTargetExistsRuntimeException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1337668122238085323L;

	public SCTargetExistsRuntimeException() {
		super();
	}

	public SCTargetExistsRuntimeException(String message, Throwable cause, boolean enableSuppression,
                                          boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SCTargetExistsRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SCTargetExistsRuntimeException(String message) {
		super(message);
	}

	public SCTargetExistsRuntimeException(Throwable cause) {
		super(cause);
	}

}
