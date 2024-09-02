package edu.kh.todolist.controller;

import java.io.IOException;

import edu.kh.todolist.service.TodoListService;
import edu.kh.todolist.service.TodoListServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/todo/add")
public class TodoAddServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String title = req.getParameter("title");
		String detail = req.getParameter("detail");
		
		try {
			
			TodoListService service = new TodoListServiceImpl();
			
			int result = service.todoAdd(title, detail);
			
			String message = null;
			
			if(result > 0) {	
				message = "추가 성공";
			}else {
				message = "추가 실패";
			}
			
			HttpSession session = req.getSession();
			
			session.setAttribute("message", message);
			
			resp.sendRedirect("/main");
						
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
