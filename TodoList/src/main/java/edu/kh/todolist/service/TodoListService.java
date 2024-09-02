package edu.kh.todolist.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import edu.kh.todolist.dto.Todo;

public interface TodoListService {
	
	/** 할 일 전체 조회
	 * 
	 * @return todoList
	 * 	 
	 * @throws Exception
	 */
	List<Todo> selectAll() throws Exception;

	/** 할 일 추가
	 * 
	 * @param title
	 * @param detail
	 * 
	 * @return result
	 * 
	 * @throws Exception 
	 */
	int todoAdd(String title, String detail) throws Exception;

	/** 할 일 상세 조회
	 * 
	 * @param todoNo
	 * 
	 * @return todo
	 * 
	 * @throws Exception
	 */
	Todo todoDetail(int todoNo) throws Exception;

	/** 완료 여부 변경
	 * 
	 * @param todoNo
	 * 
	 * @return result
	 * 
	 * @throws Exception
	 */
	int todoComplete(int todoNo) throws Exception;

	/** 할 일 수정
	 * 
	 * @param todoNo
	 * @param title
	 * @param detail
	 * 
	 * @return result
	 * 
	 * @throws Exception
	 */
	int todoUpdate(int todoNo, String title, String detail) throws Exception;

	/** 할 일 삭제
	 * 
	 * @param todoNo
	 * 
	 * @return result
	 * 
	 * @throws Exception
	 */
	int todoDelete(int todoNo) throws Exception;



}
