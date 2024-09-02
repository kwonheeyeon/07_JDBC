package edu.kh.todolist.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class JDBCTemplate {
	
	/* 필드 */
	
	private static Connection conn = null;
	
	/* 메서드 */
	
	/**
	 * 호출 시 Connection 객체를 생성해서 반환하는 메서드
	 * + AutoCommit false
	 * 
	 * @return conn
	 */
	public static Connection getConnection() {
		
		try {
			if(conn != null && !conn.isClosed()) {
				return conn; 
			}

			Properties prop = new Properties();
			

			
			String filePath = JDBCTemplate.class.getResource("/edu/kh/todolist/sql/driver.xml").getPath();

			
			prop.loadFromXML(new FileInputStream(filePath));
			
			
			// 3. prop에 저장된 값(driver.xml에서 읽어온 값)을 이용해
			//    Connection 객체 생성하기
			
			// prop.getProperty("KEY") : KEY가 일치하는 Value를 반환
			Class.forName(prop.getProperty("driver"));
			
			String url = prop.getProperty("url");
			String userName = prop.getProperty("userName");
			String password = prop.getProperty("password");
			
			conn = DriverManager.getConnection(url, userName, password);
			
			// 만들어진 Connection에 AutoCommit 끄기
			conn.setAutoCommit(false);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	// ------------------------------------------------------------------------------
	
	/*	트랜잭션 제어 처리 메서드(commit, rollback) */
	
	/**
	 * 전달 받은 Connection에서 수행한 SQL을 COMMIT하는 메서드
	 * 
	 * @param conn
	 */
	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 전달 받은 Connection에서 수행한 SQL을 ROLLACK하는 메서드
	 * 
	 * @param conn
	 */
	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// ---------------------------------------------------------------------------------
	
	/**
	 * 전달 받은 Connection을 close(자원 반환)하는 메서드
	 * 
	 * @param conn
	 */
	public static void close(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 전달 받은 Statement를 close(자원 반환)하는 메서드
	 * + PreparedStatement도 close 처리 가능!!
	 * 	 왜?? PreparedStatement가 Statement의 자식이기 때문에!!
	 *   (다형성 업캐스팅)
	 *   
	 * @param stmt
	 */
	public static void close(Statement stmt) {
		try {
			if(stmt != null && !stmt.isClosed()) stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 전달 받은 ResultSet을 close(자원 반환)하는 메서드
	 * 
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		try {
			if(rs != null && !rs.isClosed()) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
}
