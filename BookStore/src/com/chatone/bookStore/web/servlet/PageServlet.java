package com.chatone.bookStore.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chatone.bookStore.domain.PageBean;
import com.chatone.bookStore.service.ProductService;
/**
 * 分页
 * 
 * @author li
 *
 */
public class PageServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//导航按钮的查询条件
		String category = request.getParameter("category");
		if(category == null){
			category = "";
		}
		//初始化每页记录数
		int pageSize = 4;
		int currentPage = 1;
		String currPage = request.getParameter("currentPage");
		if(currPage != null && "".equals(currPage)){
			currentPage = Integer.parseInt(currPage);
		}
		
		ProductService bs = new ProductService();
		
		//分页查询
		PageBean pb = bs.findBooksPage(currentPage,pageSize,category);
		
		request.setAttribute("pb", pb);
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
