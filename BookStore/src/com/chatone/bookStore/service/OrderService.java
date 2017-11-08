package com.chatone.bookStore.service;

import java.sql.SQLException;
import java.util.List;

import com.chatone.bookStore.dao.OrderDao;
import com.chatone.bookStore.dao.OrderItemDao;
import com.chatone.bookStore.dao.ProductDao;
import com.chatone.bookStore.domain.Order;
import com.chatone.bookStore.util.ManagerThreadLocal;

public class OrderService {
	OrderDao orderDao = new OrderDao();
	OrderItemDao orderItemDao = new OrderItemDao();
	ProductDao productDao = new ProductDao();
	
	public void addOrder(Order order){
		try {
			ManagerThreadLocal.startTransacation();
			orderDao.addOrder(order);
			orderItemDao.addOrderItem(order);
			productDao.updateProductNum(order);
			
			ManagerThreadLocal.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			ManagerThreadLocal.rollback();
		}
	}

	public List<Order> findOrdersByUserId(int id) {
		try {
			return orderDao.findOrders(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Order findOrdersByOrderId(String orderid) {
		try {
			return orderDao.findOrdersByOrderId(orderid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void modifyOrderState(String r6_Order) throws OrderExecption {
		try {
			orderDao.modifyOrderState(r6_Order);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new OrderExecption("修改失败");
		}
	}
}
