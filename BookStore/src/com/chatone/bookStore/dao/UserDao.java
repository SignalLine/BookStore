package com.chatone.bookStore.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.chatone.bookStore.domain.User;
import com.chatone.bookStore.util.C3P0Util;

public class UserDao {

	public void addUser(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		String sql = "INSERT INTO USER(username,PASSWORD,gender,email,telephone,introduce,activeCode,state,registTime) "
				+ "VALUES(?,?,?,?,?,?,?,?,?)";

		qr.update(sql, user.getUsername(), user.getPassword(),
				user.getGender(), user.getEmail(), user.getTelephone(),
				user.getIntroduce(), user.getActiveCode(), user.getState(),
				user.getRegistTime());
	}

	public User findUserByActiveCode(String activeCode) throws SQLException {
		
		String sql = "select * from USER where activeCode = ?";
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query(sql, new BeanHandler<User>(User.class),activeCode);
	}
	
	public void activeCode(String activeCode) throws SQLException{
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		qr.update("update user set state = 1 where activeCode = ?" ,activeCode);
	}

	public User findUserByUsernameAndPassword(String username, String password) throws SQLException {
		String sql = "select * from USER where username = ? and password= ? ";
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query(sql, new BeanHandler<User>(User.class),username,password);
	}

	public User findUserById(String id) throws SQLException {
		String sql = "select * from USER where id = ? ";
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query(sql, new BeanHandler<User>(User.class),id);
	}

	public void modifyUser(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		qr.update("update user set password=?,gender=?,telephone=? where id = ?" ,user.getPassword(),user.getGender(),user.getTelephone(),user.getId());
	}

}
