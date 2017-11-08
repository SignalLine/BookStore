package com.chatone.bookStore.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chatone.bookStore.domain.Product;
import com.chatone.bookStore.service.ProductService;

public class AddCartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String id = request.getParameter("id");
		
		ProductService ps = new ProductService();
		
		Product b = ps.findBookById(id);
		//从session中的购物车取出
		HttpSession session = request.getSession();
		Map<Product,String> cart = (Map<Product, String>) session.getAttribute("cart");
		int num = 1;
		//如何第一次访问，没有购物车对象，创建一个对象
		if(cart == null){
			cart = new HashMap<Product, String>();
		}
		
		//查看当前计划中是否存在这本书，如果有 +1 
		if(cart.containsKey(b)){
			num = Integer.parseInt(cart.get(b)) + 1;
		}
		//把图书放到购物车中
		cart.put(b,num+"");
		//把cart对象放回到session中
		session.setAttribute("cart", cart);
		
		out.print("<a href='" + request.getContextPath() + "/pageServlet'>继续购物</a>，<a href='" + request.getContextPath() + "/cart.jsp'>查看购物车</a>");
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
