package edu.kh.todolist.controller;

import java.io.IOException;

import edu.kh.todolist.dto.Todo;
import edu.kh.todolist.service.TodoListService;
import edu.kh.todolist.service.TodoListServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 할 일 상세 조회 요청 처리 Servlet
 */
@WebServlet("/todo/detail")
public class DetailServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int todoNo = Integer.parseInt(req.getParameter("todoNo"));
		
		try {
			TodoListService service = new TodoListServiceImpl();
			
			Todo todo = service.todoDetail(todoNo);
			
			req.setAttribute("todo", todo);
			
			String path = "/WEB-INF/views/detail.jsp";
			req.getRequestDispatcher(path).forward(req, resp);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
