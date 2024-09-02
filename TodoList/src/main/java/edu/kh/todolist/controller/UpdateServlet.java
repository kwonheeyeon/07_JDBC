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

@WebServlet("/todo/update")
public class UpdateServlet extends HttpServlet {
	
	// 수정 화면 전환(GET 방식 요청 처리)
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int todoNo = Integer.parseInt(req.getParameter("todoNo"));
		
		try {
			TodoListService service = new TodoListServiceImpl();
			Todo todo = service.todoDetail(todoNo);
			
			req.setAttribute("todo", todo);
			
			String path = "/WEB-INF/views/update.jsp";
			req.getRequestDispatcher(path).forward(req, resp);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 할 일 제목/내용 수정(POST 방식 요청 처리)
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String title = req.getParameter("title");
		String detail = req.getParameter("detail");
		
		int todoNo = Integer.parseInt(req.getParameter("todoNo"));
		
		try {
			TodoListService service = new TodoListServiceImpl();
			
			int result = service.todoUpdate(todoNo, title, detail);
			
			String url = null;
			String message = null;
			
			if(result > 0) {
				url = "/todo/detail?todoNo=" + todoNo;
				message = "수정되었습니다";
			}else {
				url = "/todo/update?todoNo=" + todoNo;
				message = "수정 실패";
			}
			
			req.getSession().setAttribute("message", message);
			
			resp.sendRedirect(url);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
