package com.vaadHL.utl.data;

public class WrongObjectTypeException extends RuntimeException {
	private static final long serialVersionUID = 3327427610590194542L;

	public WrongObjectTypeException() {
		super();

	}

	public WrongObjectTypeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public WrongObjectTypeException(String message, Throwable cause) {
		super(message, cause);

	}

	public WrongObjectTypeException(String message) {
		super(message);

	}

	public WrongObjectTypeException(Throwable cause) {
		super(cause);

	}

}
