package com.chatone.bookStore.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chatone.bookStore.execption.UserException;
import com.chatone.bookStore.service.UserService;

public class ActiveServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//获取激活码
		String activeCode = request.getParameter("activeCode");
		UserService us = new UserService();
		
		try {
			us.activeUser(activeCode);
			//可以跳回登录页面
			
		} catch (UserException e) {
			e.printStackTrace();
			//用户提示失败信息
			response.getWriter().write(e.getMessage());
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
