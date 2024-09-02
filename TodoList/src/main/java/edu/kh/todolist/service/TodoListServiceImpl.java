package edu.kh.todolist.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.kh.todolist.dao.TodoListDao;
import edu.kh.todolist.dao.TodoListDaoImpl;
import edu.kh.todolist.dto.Todo;

import static edu.kh.todolist.common.JDBCTemplate.*;

public class TodoListServiceImpl implements TodoListService{
	
	// 필드
	private TodoListDao dao = new TodoListDaoImpl();
	
	// -------------------------------------------------------------
	
	@Override
	public List<Todo> selectAll() throws Exception {
		Connection conn = getConnection();
		
		List<Todo> todoList = dao.selectAll(conn);
		
		close(conn);
		
		return todoList;
	}
	
	// ----------------------------------------------------------------

	@Override
	public int todoAdd(String title, String detail) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.todoAdd(conn, title, detail);
		
		close(conn);
		
		return result;
	}
	
	// --------------------------------------------------------------------
	
	@Override
	public Todo todoDetail(int todoNo) throws Exception {
		Connection conn = getConnection();
		
		Todo todo = dao.todoDetail(conn, todoNo);
		
		close(conn);
		
		return todo;
	}
	
	// -------------------------------------------------------
	
	@Override
	public int todoComplete(int todoNo) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.todoComplete(conn, todoNo);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}
	
	// -----------------------------------------------------
	
	@Override
	public int todoUpdate(int todoNo, String title, String detail) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.todoUpdate(conn, todoNo, title, detail);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}
	
	// -----------------------------------------------
	
	@Override
	public int todoDelete(int todoNo) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.todoDelete(conn, todoNo);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}
}
