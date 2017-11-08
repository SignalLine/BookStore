package com.chatone.bookStore.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.chatone.bookStore.domain.Order;
import com.chatone.bookStore.domain.OrderItem;
import com.chatone.bookStore.domain.Product;
import com.chatone.bookStore.domain.User;
import com.chatone.bookStore.service.OrderService;

public class OrderServlet extends BaseServlet {

	public void createOrderServlet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//封装order对象
		Order order = new Order();
		try {
			BeanUtils.populate(order, request.getParameterMap());
			order.setId(UUID.randomUUID().toString());
			order.setUser((User)request.getSession().getAttribute("user"));//把session对象中的用户信息保存
//					order.setOrdertime(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//获取sesison对象购物车数据
		Map<Product, String> cart = (Map<Product, String>) request.getSession().getAttribute("cart");
		
		//遍历购物车中的商品数据，添加到orderItem对象中，同时把多个orderItem添加到list集合中
		List<OrderItem> list = new ArrayList<OrderItem>();
		for(Product p: cart.keySet()){
			OrderItem oi = new OrderItem();
			oi.setP(p);
			oi.setBuynum(Integer.parseInt(cart.get(p)));
			oi.setOrder(order);
			
			list.add(oi);//把每个订单项添加到集合中
		}
		//把计划放到order集合中
		order.setOrderItems(list);
		//调用业务逻辑
		OrderService os = new OrderService();
		os.addOrder(order);
		
		//
		request.setAttribute("orderid", order.getId());
		request.setAttribute("money", order.getMoney());
		request.getRequestDispatcher("/pay.jsp").forward(request, response);
	}
	
	public void findOrderByUserId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		
		OrderService os = new OrderService();
		List<Order> orders = os.findOrdersByUserId(user.getId());
		
		request.setAttribute("orders", orders);
		request.setAttribute("count", orders.size());
		request.getRequestDispatcher("/orderlist.jsp").forward(request, response);
		
	}
	
	
	public void findOrderItemsByOrderId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String orderid = request.getParameter("orderid");
		
		OrderService os = new OrderService();
		Order order = os.findOrdersByOrderId(orderid);
		
		request.setAttribute("order", order);
		request.getRequestDispatcher("/orderInfo.jsp").forward(request, response);
	}
	
	
	public void createOrder4(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}


}
