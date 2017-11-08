package com.chatone.bookStore.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chatone.bookStore.domain.User;

public class MyAccount extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//从session中取出user对象
		User user = (User) request.getSession().getAttribute("user");
		//判断user是否为null
		if(user == null){
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}else{
			String path = "/myAccount.jsp";
			
			if("admin".equals(user.getRole())){
				//管理员 
				path = "/admin/login/home.jsp";
			}
			
			request.getRequestDispatcher(path).forward(request, response);
			
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
