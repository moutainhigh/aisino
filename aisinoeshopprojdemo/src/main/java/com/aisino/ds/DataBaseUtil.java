package com.aisino.ds;

import java.sql.*;

public class DataBaseUtil {
	static Connection con;

	public static Connection getCon() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@192.168.4.114:1521:jxwlkpdb";
			con = DriverManager.getConnection(url, "shdzfpyys", "shdzfpyys");
			return con;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void close(Connection con, PreparedStatement pst, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (pst != null)
				pst.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DataBaseUtil.getCon();
	}
}
