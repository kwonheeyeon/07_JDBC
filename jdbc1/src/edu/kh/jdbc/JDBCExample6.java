package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JDBCExample6 {
	public static void main(String[] args) {
		
		// 아이디, 비밀번호를 입력 받아
		// 아이디, 비밀번호가 일치하는 사용자(TB_USER)의 이름을
		// 이름을 수정(UPDATE)
		
		// JDBC 객체 참조 변수 선언
		Connection conn = null;
		PreparedStatement pstmt = null;
		// ?(placeholder)에 값을 대입할 준비가 된 Statement
		
		try {
			// Connection 생성
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String userName = "KH_KHY";
			String password = "KH1234";
			
			conn = DriverManager.getConnection(url, userName, password);
			
			// AutoCommint 끄기
			conn.setAutoCommit(false);
			
			// SQL 작성
			Scanner sc = new Scanner(System.in);
			
			System.out.print("아이디 입력 : ");
			String id = sc.next();
			
			System.out.print("비밀번호 입력 : ");
			String pw = sc.next();
			
			System.out.print("수정할 이름 입력 : ");
			String name = sc.next();
			
			String sql = "UPDATE TB_USER SET USER_NAME = ? WHERE USER_ID = ? AND USER_PW = ?";
			
			// PrepareStatment 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ?에 알맞은 값 대입
			pstmt.setString(1, name);
			pstmt.setString(2, id);
			pstmt.setString(3, pw);
			
			// SQL 수행 후 결과 반환 받기
			// PreparedStatement를 이용하여 SQL 실행 시
			// 매개변수 자리에 아무것도 없어야 한다!!!
			int result = pstmt.executeUpdate();
			
			if(result > 0) { // 성공 시 "성공" 메시지 + COMMIT
				System.out.println("수정 성공!!!");
				conn.commit();
			}else { // 실패 시 "아이디 또는 비밀번호 불일치" 메시지 + ROLLBACK
				System.out.println("아이디 또는 비밀번호 불일치");
				conn.rollback();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				// 사용한 JDBC 객체 자원 반환
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
