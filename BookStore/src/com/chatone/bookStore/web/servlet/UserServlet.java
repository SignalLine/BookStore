package com.chatone.bookStore.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.chatone.bookStore.domain.User;
import com.chatone.bookStore.execption.UserException;
import com.chatone.bookStore.service.UserService;

public class UserServlet extends BaseServlet {

	
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取表单数据
		String username = request.getParameter("username");
		String password = request.getParameter("password");
	
		UserService us = new UserService();
		try {
			User user = us.login(username,password);
			
			String path = "/index.jsp";
			
			if("admin".equals(user.getRole())){
				path = "/admin/login/home.jsp";
			}
			
			request.getSession().setAttribute("user", user);
			request.getRequestDispatcher(path).forward(request, response);
			
		} catch (UserException e) {
			e.printStackTrace();
			request.setAttribute("user_msg", e.getMessage());
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
	
	public void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//处理验证码
		String ckcode = request.getParameter("ckcode");
		String checkcode_session = (String) request.getSession().getAttribute("checkcode_session");
		
		if(!checkcode_session.equals(ckcode)){
			//如果两个验证码不一致，跳回初始页面
			request.setAttribute("ckcode_msg", "验证码错误！");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			
			return;
		}
		//获取表单数据
		
		User user = new User();
		try {
			BeanUtils.populate(user, request.getParameterMap());
			user.setActiveCode(UUID.randomUUID().toString());//手动设置激活码
			UserService us = new UserService();
			//调用业务逻辑
			us.regist(user);
			//分发转向
			//要求用户激活后才能登陆，所以不能把用户信息保存到session中
			
//					request.getSession().setAttribute("user", user);//把用户信息封装到session对象中
			request.getRequestDispatcher("/registersuccess.jsp").forward(request, response);
		} catch (UserException e) {
//					e.printStackTrace();
			request.setAttribute("user_msg",e.getMessage());
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();//使session销毁
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}
	public void findUserById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");

		UserService us = new UserService();
		try {
			User user = us.findUserById(id);
			
			request.setAttribute("u",user);
			request.getRequestDispatcher("/modifyuserinfo.jsp").forward(request, response);
			
		} catch (UserException e) {
			e.printStackTrace();
//			response.getWriter().write(e.getMessage());
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}
	}			
	public void modifyUserServlet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//封装表单数据
		User user = new User();
		try {
			BeanUtils.populate(user, request.getParameterMap());
			
			UserService us = new UserService();
			us.modifyUser(user);
			//成功
			request.getSession().invalidate();//注销用户
			response.sendRedirect(request.getContextPath() + "/modifyUserInfoSuccess.jsp");
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (UserException e) {
			e.printStackTrace();
			response.getWriter().write(e.getMessage());
		}
	}

}
