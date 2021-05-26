package com.rs.core.exception;

/**
 * 实体类校验异常
 */
public class ZACheckException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String error;

	public ZACheckException(String error) {
		super(error);
		this.error = error;
	}

	@Override
	public String toString() {
		return error;
	}

}
