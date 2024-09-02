package edu.kh.todolist.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.todolist.common.JDBCTemplate;
import edu.kh.todolist.dto.Todo;

import static edu.kh.todolist.common.JDBCTemplate.*;

public class TodoListDaoImpl implements TodoListDao{
	
	// 필드
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	// 기본 생성자
	public TodoListDaoImpl() {
			
		try {
				
			String filePath = JDBCTemplate.class.getResource("/edu/kh/todolist/sql/sql.xml").getPath();
				
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));
				
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
		
	// ----------------------------------------------------------------------------------------
	
	@Override
	public List<Todo> selectAll(Connection conn) throws Exception {
		List<Todo> todoList = new ArrayList<Todo>();
		
		try {
			String sql = prop.getProperty("selectAll");
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int todoNo = rs.getInt("TODO_NO");
				String title = rs.getString("TITLE");
				String detail = rs.getString("DETAIL");
				int complete = rs.getInt("COMPLETE");
				String regDate = rs.getString("REG_DATE");
				
				Todo todo = new Todo(todoNo, title, detail, complete, regDate);
				
				todoList.add(todo);
			}
		}finally {
			close(rs);
			close(stmt);
		}
		
		return todoList;
	}
	
	// ---------------------------------------------------------------------------------------
	
	
	@Override
	public int todoAdd(Connection conn, String title, String detail) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("todoAdd");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, detail);
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		
		return result;
	}
	
	// -----------------------------------------------------------------------------------------
	
	@Override
	public Todo todoDetail(Connection conn, int todoNo) throws Exception {
		Todo todo = null;
		
		try {
			String sql = prop.getProperty("todoDetail");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, todoNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String title = rs.getString("TITLE");
				String detail = rs.getString("DETAIL");
				int complete = rs.getInt("COMPLETE");
				String regDate = rs.getString("REG_DATE");
				
				todo = new Todo(todoNo, title, detail, complete, regDate);
			
			}
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return todo;
	}
	
	// -----------------------------------------------------------------------
	
	@Override
	public int todoComplete(Connection conn, int todoNo) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("todoComplete");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, todoNo);
			
			result = pstmt.executeUpdate();

		}finally {
			close(pstmt);
		}
		
		return result;
	}
	
	// -----------------------------------------------------------------
	
	@Override
	public int todoUpdate(Connection conn, int todoNo, String title, String detail) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("todoUpdate");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, detail);
			pstmt.setInt(3, todoNo);
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		
		return result;
	}
	
	// --------------------------------------
	
	@Override
	public int todoDelete(Connection conn, int todoNo) throws Exception {
int result = 0;
		
		try {
			String sql = prop.getProperty("todoDelete");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, todoNo);
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		
		return result;
	}

}
