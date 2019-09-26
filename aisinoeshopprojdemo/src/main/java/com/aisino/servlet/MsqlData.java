package com.aisino.servlet;

import com.aisino.common.Constants;

import java.sql.Connection;
import java.sql.DriverManager;

public class MsqlData {
	public static Connection getConn(){
		String drier = "com.mysql.jdbc.Driver";
		Connection con = null;
		try{
			Class.forName(drier).newInstance();
			con = DriverManager.getConnection(Constants.url, Constants.user, Constants.password);
		}catch (Exception e) {
		}
		return con;
	}
	public static void main(String[] args) {
		System.out.println(getConn());
	}
}
