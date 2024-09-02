package edu.kh.todolist.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import edu.kh.todolist.dto.Todo;

public interface TodoListDao {
	
	/** 할 일 전체 조회
	 * 
	 * @param conn
	 * 
	 * @return todoList
	 * 
	 * @throws Exception
	 */
	List<Todo> selectAll(Connection conn) throws Exception;

	/** 할 일 추가
	 * 
	 * @param conn
	 * @param title
	 * @param detail
	 * 
	 * @return return
	 * 
	 * @throws Exception
	 */
	int todoAdd(Connection conn, String title, String detail) throws Exception;

	/** 할 일 상세 조회
	 * 
	 * @param conn
	 * @param todoNo
	 * 
	 * @return todo
	 * 
	 * @throws Exception
	 */
	Todo todoDetail(Connection conn, int todoNo) throws Exception;

	/** 완료 여부 변경
	 * 
	 * @param conn
	 * @param todoNo
	 * 
	 * @return result
	 * 
	 * @throws Exception
	 */
	int todoComplete(Connection conn, int todoNo) throws Exception;

	/** 할 일 수정
	 * 
	 * @param conn
	 * @param todoNo
	 * @param title
	 * @param detail
	 * 
	 * @return result
	 * 
	 * @throws Exception
	 */
	int todoUpdate(Connection conn, int todoNo, String title, String detail) throws Exception;

	/** 할 일 삭제
	 * 
	 * @param conn
	 * @param todoNo
	 * 
	 * @return result
	 * 
	 * @throws Exception
	 */
	int todoDelete(Connection conn, int todoNo) throws Exception;


}
