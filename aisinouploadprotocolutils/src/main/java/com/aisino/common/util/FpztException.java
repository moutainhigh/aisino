package com.aisino.common.util;

public class FpztException extends Exception {
	private static final long serialVersionUID = 1L;
	public int exceptionCode;
	public FpztException() {
		super();
	}
	public FpztException(int exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	
	public FpztException(int exceptionCode,String msg){
		super(msg);
		this.exceptionCode = exceptionCode;
	}
	public FpztException(String msg) {
		super(msg);
	}

	public FpztException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public FpztException(Throwable cause) {
		super(cause);
	}
	
	public int getExceptionCode(){
		return this.exceptionCode;
	}
}
