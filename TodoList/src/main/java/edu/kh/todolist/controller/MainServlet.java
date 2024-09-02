package edu.kh.todolist.controller;

import java.io.IOException;
import java.util.List;

import edu.kh.todolist.dto.Todo;
import edu.kh.todolist.service.TodoListService;
import edu.kh.todolist.service.TodoListServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


// "/main" 요청을 매핑하여 처리하는 Servlet
@WebServlet("/main")
public class MainServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			TodoListService service = new TodoListServiceImpl();
			
			List<Todo> todoList = service.selectAll();
			
			req.setAttribute("todoList", todoList);
			
			int countComplete = 0;
			for(Todo todo : todoList) {
				if(todo.getComplete() == 1) countComplete++;
			}
			
			req.setAttribute("countComplete", countComplete);
			
			String path = "/WEB-INF/views/main.jsp";
			
			req.getRequestDispatcher(path).forward(req, resp);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
