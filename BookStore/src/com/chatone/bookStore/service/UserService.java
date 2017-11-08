package com.chatone.bookStore.service;

import java.sql.SQLException;

import com.chatone.bookStore.dao.UserDao;
import com.chatone.bookStore.domain.User;
import com.chatone.bookStore.execption.UserException;
import com.chatone.bookStore.util.SendJMail;

public class UserService {
	
	UserDao ud = new UserDao();
	
	/**
	 * 注册用户
	 * 
	 * @param user
	 * @throws UserException 
	 */
	public void regist(User user) throws UserException {
		try {
			ud.addUser(user);//用户注册
			
			String emailMsg = "注册成功，请<a href='http://www.prouct.com/activeServlet?activeCode="+user.getActiveCode() +"'>激活</a>后登录";
			SendJMail.sendMail(user.getEmail(), emailMsg);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("注册失败!");
		}
	}

	public void activeUser(String activeCode) throws UserException {
		try {
			User user = ud.findUserByActiveCode(activeCode);
			
			if(user != null){
				//激活用户
				ud.activeCode(activeCode);
				return;
			}
			throw new UserException("激活失败！");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("激活失败！");
		}
	}

	public User login(String username, String password) throws UserException {
		User user = null;
		try {
			user = ud.findUserByUsernameAndPassword(username,password);
			if(user == null){
				throw new UserException("用户名或密码错误！");
			}
			if(user.getState() == 0){
				throw new UserException("用户未激活！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("用户名或密码错误！");
		}
		
		return user;
	}

	public User findUserById(String id) throws UserException {
		User user = null;
		try {
			user = ud.findUserById(id);
			if(user == null){
				throw new UserException("查找用户失败！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("查找用户失败！");
		}
		return user;
	}

	public void modifyUser(User user) throws UserException {
		try {
			ud.modifyUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("修改用户信息失败！");
		}
	}
	
	

}
