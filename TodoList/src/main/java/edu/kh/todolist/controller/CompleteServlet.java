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

@WebServlet("/todo/complete")
public class CompleteServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int todoNo = Integer.parseInt(req.getParameter("todoNo"));
		
		try {
			TodoListService service = new TodoListServiceImpl();
			
			int result = service.todoComplete(todoNo);
			
			HttpSession session = req.getSession();
			if(result > 0) {
				session.setAttribute("message", "완료 여부가 변경되었습니다!");
				resp.sendRedirect("/todo/detail?todoNo=" + todoNo);
				
				return;
			}
			
			session.setAttribute("message", "완료 여부 변경 실패");
			
			resp.sendRedirect("/");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
