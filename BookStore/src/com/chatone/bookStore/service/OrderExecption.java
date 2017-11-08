package com.chatone.bookStore.service;

public class OrderExecption extends Exception {

	public OrderExecption() {
		super();
	}

	public OrderExecption(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public OrderExecption(String message, Throwable cause) {
		super(message, cause);
	}

	public OrderExecption(String message) {
		super(message);
	}

	public OrderExecption(Throwable cause) {
		super(cause);
	}
	
}
