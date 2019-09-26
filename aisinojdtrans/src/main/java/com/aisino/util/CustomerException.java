package com.aisino.util;
/**
 * 
* Description: Customer Exception
* @author peterli  lichunhui1314@126.com
* @date 2013-12-2 下午7:35:27 
*
 */
public class CustomerException extends Exception {
	
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 3319213472052121088L;
	
	private String message;
	
	public CustomerException(String msg){
		this.message = msg;
	}
	
	public String toString(){
	  return message;
	}
	
	/**
	 * Description：get exception message
	 * @return exception message
	 * @see Throwable#getMessage()
	 */
	public String getMessage(){
		return message;
	}
}
