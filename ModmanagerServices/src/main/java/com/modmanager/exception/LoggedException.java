package com.modmanager.exception;

/**
 * An exception that already has been logged, but wasn't shown to the user jet.
 */
public class LoggedException extends RuntimeException {
	
	
	public LoggedException(final String message) {
		super(message);
	}

	public LoggedException(final Throwable cause) {
		super(cause);
	}

	public LoggedException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public LoggedException(final String message, final Throwable cause,
			final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}