package edu.kh.jdbc.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dto.User;

// DAO(Data Access Object) : 데이터가 저장된 곳에 접근하는 용도의 객체
// -> DB에 접근하여 JAva에서 원하는 결과를 얻기 위해
//	  SQL을 수행하고 결과 반환 받는 역할

public class UserDao {
	
	// 필드
	// - DB 접근 관련한 JDBC 객체 참조형 변수를 미리 선언
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	
	/**
	 * 전달 받은 Connection을 이용해 DB에 접근하여
	 * 전달 받은 아이디와 일치하는 User 정보 조회
	 * 
	 * @param connn : Service에서 생성한 Connection 객체
	 * @param input : View에서 입력 받은 아이디
	 * 
	 * @return user : 생성된 User 또는 null
	 */
	public User selectId(Connection conn, String inputId) {
		
		User user = null; // 결과 저장용 변수
		
		try {
			// SQL 작성
			String sql = "SELECT * FROM TB_USER WHERE USER_ID = ?";
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ?(placeholder)에 알맞은 값 대입
			pstmt.setString(1, inputId);
			
			// SQL 수행 후 결과 반환 받기
			rs = pstmt.executeQuery();
			
			// 조회 결과가 있을 경우
			// -> 중복되는 아이디가 없을 경우
			//	  1행만 조회되기 때문에 while보다 if를 사용하는게 효과적
			if(rs.next()) {
				
				// 각 컬럼의 값 얻어오기
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				
				// java.sql.Date 활용
				Date enrollDate = rs.getDate("ENROLL_DATE");
				
				// 조회된 컬럼값을 이용해 User 객체 생성
				user = new User(userNo, userId, userPw, userName, enrollDate.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			// 사용한 JDBC 객체 자원 반환(close)
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
			
			// Connection 객체는 Service에서 close!!!!!
		}
		
		return user;
	}


	/**
	 * User 등록 
	 * 
	 * @param conn : DB 연결 정보가 담겨있는 객체
	 * @param user : 입력 받은 id, pw, name
	 * 
	 * @return result : INSERT 결과 행의 개수
	 * 
	 * @throws Exception : 발생하는 예외 모두 service로 던짐
	 */
	public int insertUser(Connection conn, User user) throws Exception{
		
		// SQL 수행 중 발생하는 예외를
		// catch로 처리하지 않고, throws를 이용해서 호출부로 던져 처리
		// -> catch문이 필요없다!!
		
		// 1. 결과 저장용 변수 선언
		int result = 0;
		
		try {
			// 2. SQL 작성
			String sql = """
					 INSERT INTO TB_USER
					 VALUES(SEQ_USER_NO.NEXTVAL, ?, ?, ?, DEFAULT)
					""";
			
			// 3. PreparedStatement 생성
			pstmt = conn.prepareStatement(sql);
			
			// 4. ?(placeholder)에 알맞은 값 대입
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserPw());
			pstmt.setString(3, user.getUserName());
			
			// 5. SQL(INSERT) 수행(excuteUpdate) 후 결과(삽입된 행의 개수, int) 반환 받기
			result = pstmt.executeUpdate();
		}finally {
			// 6. 사용한 JDBC 객체 자원 반환(close)
			close(pstmt);
		}
		
		// 7. 결과 저장용 변수에 저장된 값을 반환
		return result;
	}


	/**
	 * User 전체 조회 
	 * 
	 * @param conn : : DB 연결 정보가 담겨있는 객체
	 * 
	 * @return userList : 조회된 User가 담긴 List
	 * 
	 * @throws Exception : 발생하는 예외 모두 service로 던짐
	 */
	public List<User> selectAll(Connection conn) throws Exception{
		
		// 1. 결과 저장용 변수 선언
		//	-> List 같은 컬렉션을 반환하는 경우에는
		// 	   변수 선언 시 객체도 같이 생성해두자!
		List<User> userList = new ArrayList<User>();
		
		try {
			// 2. SQL 작성
			String sql = """
					SELECT
						USER_NO,
						USER_ID,
						USER_PW,
						USER_NAME,
						TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE
					FROM TB_USER
					ORDER BY USER_NO ASC
					""";
			
			// 3. Statement 생성
			stmt = conn.createStatement();
			
			// 4. SQL(SELECT) 수행(excuteQuery) 후 결과(ResultSet) 반환 받기
			rs = stmt.executeQuery(sql);
			
			// 5. 조회 결과(ResultSet)를 커서를 이용해서 1행씩 접근하여 컬럼 값 얻어오기
			
			/* 몇 행이 조회될지 모른다 -> while
			 * 무조건 1행이 조회된다   -> if
			 */
			
			// rs.next() : 커서를 1행 이동
			// 이동된 행에 데이터가 있으면 true, 없으면 false
			while(rs.next()) {
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				
				String enrollDate = rs.getString("ENROLL_DATE");
				// - java.sql.Date 타입으로 값을 저장하지 않는 이유!
				//  -> TO_CHAR()를 이용해서 문자열로 변환했기 때문!
				
				// ** 조회된 값을 userList에 추가 **
				//	-> User 객체를 생성해 조회된 값을 담고 userList에 추가
				User user = new User(userNo, userId, userPw, userName, enrollDate);
				userList.add(user);
				
				// ResultSet을 List에 옮겨 담는 이유
				// 1. List가 사용이 편해서
				// 	  -> 호환되는 곳도 많음(jsp, thymeleaf 등)
				// 2. 사용된 ResultSet은 DAO에서 close되기 때문
			}
		}finally {
			// 6. JDBCTemple 객체 반환
			close(rs);
			close(stmt);
		}
		
		// 7. 조회 결과가 담긴 List 반환
		return userList;
	}

	/**
	 * User 중 이름에 검색어가 포함된 User 조회 
	 * 
	 * @param conn : DB 연결 정보가 담겨있는 객체
	 * @param keyword : 검색어
	 * 
	 * @return searchList : 조회된 User가 담긴 List
	 * 
	 * @throws Exception : 발생하는 예외 모두 service로 던짐
	 */
	public List<User> selectName(Connection conn, String keyword) throws Exception{
		
		List<User> searchList = new ArrayList<User>();
		
		try {
			String sql = """
					SELECT
						USER_NO,
						USER_ID,
						USER_PW,
						USER_NAME,
						TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE
					FROM TB_USER
					WHERE USER_NAME LIKE '%' || ? || '%'
					ORDER BY USER_NO ASC
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				User user = new User(userNo, userId, userPw, userName, enrollDate);
				
				searchList.add(user);
			}
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return searchList;
	}


	/**
	 * USER_NO를 입력 받아 일치하는 User 조회
	 * 
	 * @param conn : DB 연결 정보가 담겨있는 객체
	 * @param input : 입력 받은 USER_NO
	 * 
	 * @return user : 조회된 User
	 * 
	 * @throws Exception : 발생하는 예외 모두 service로 던짐
	 */
	public User selectUser(Connection conn, int input) throws Exception{
		
		User user = null;
		
		try {
			String sql = """
					SELECT
						USER_NO,
						USER_ID,
						USER_PW,
						USER_NAME,
						TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE
					FROM TB_USER
					WHERE USER_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, input);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				user = new User(userNo, userId, userPw, userName, enrollDate);
			}
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return user;
	}


	/**
	 * USER_NO를 입력 받아 일치하는 User 삭제
	 * 
	 * @param conn : DB 연결 정보가 담겨있는 객체
	 * @param input : 입력 받은 USER_NO
	 * 
	 * @return result : : 삭제된 행의 개수
	 * @throws Exception : 발생하는 예외 모두 service로 던짐
	 */
	public int deleteUser(Connection conn, int input) throws Exception{
		int result = 0;
		
		try {
			String sql = """
					DELETE
					FROM TB_USER
					WHERE USER_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, input);
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		
		return result;
	}


	/**
	 * 입력 받은 ID, PW가 일치하는 User의 USER_NO 조회
	 * 
	 * @param conn : DB 연결 정보가 담겨있는 객체
	 * @param userId : 입력 받은 ID
	 * @param userPw : 입력 받은 PW
	 * 
	 * @return userNo : ID,PW가 일치하는 User의 USER_NO
	 * 
	 * @throws Exception : 발생하는 예외 모두 service로 던짐
	 */
	public int selectUser(Connection conn, String userId, String userPw) throws Exception{
		
		int userNo = 0;
		
		try {
			String sql = """
					SELECT USER_NO
					FROM TB_USER
					WHERE USER_ID = ? AND USER_PW = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				userNo = rs.getInt("USER_NO");
			}
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return userNo;
	}


	/**
	 * USER_NO가 일치하는 User의 이름 수정
	 * 
	 * @param conn : DB 연결 정보가 담겨있는 객체
	 * @param userNo : ID,PW가 일치하는 User의 USER_NO
	 * @param userName : 입력 받은 수정할 이름
	 * 
	 * @return result : 수정된 행의 개수
	 * 
	 * @throws Exception : 발생하는 예외 모두 service로 던짐
	 */
	public int updateName(Connection conn, int userNo, String userName) throws Exception{
		
		int result = 0;
		
		try {
			String sql = """
					UPDATE TB_USER
					SET USER_NAME = ?
					WHERE USER_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setInt(2, userNo);
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		
		return result;
	}


	/**
	 * 입력 받은 ID 중복 확인
	 * 
	 * @param conn : DB 연결 정보가 담겨있는 객체
	 * @param userId : 입력 받은 ID
	 * 
	 * @return count : : 중복이면 1, 아니면 0
	 * 
	 * @throws Exception : 발생하는 예외 모두 service로 던짐
	 */
	public int idCheck(Connection conn, String userId) throws Exception{
		
		int count = 0;
		
		try {
			String sql = """
					SELECT COUNT(*)
					FROM TB_USER
					WHERE USER_ID = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1); // 조회된 컬럼 순서를 이용해 컬럼 값 얻어오기
				// count = rs.getInt("COUNT(*)");
			}
			
		}finally {
			close(rs);
			close(pstmt);
		}
		return count;
	}
}



