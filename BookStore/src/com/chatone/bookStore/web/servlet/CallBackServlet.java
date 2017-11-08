package com.chatone.bookStore.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chatone.bookStore.service.OrderExecption;
import com.chatone.bookStore.service.OrderService;
import com.chatone.bookStore.util.PaymentUtil;

public class CallBackServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String p1_MerId = request.getParameter("p1_MerId");//商户编号
		String r0_Cmd = request.getParameter("r0_Cmd");//业务类型
		String r1_Code = request.getParameter("r1_Code");//1 支付成功
		String r2_TrxId = request.getParameter("r2_TrxId");//易宝支付交易流水号
		String r3_Amt = request.getParameter("r3_Amt");//支付金额
		String r4_Cur = request.getParameter("r4_Cur");//交易币种
		String r5_Pid = request.getParameter("r5_Pid");//商品名称
		String r6_Order = request.getParameter("r6_Order");//商户订单号
		String r7_Uid = request.getParameter("r7_Uid");//易宝支付会员ID
		String r8_MP = request.getParameter("r8_MP");//商户扩展信息
		String r9_BType = request.getParameter("r9_BType");//交易结果返回类型 1 浏览器重定向  2服务器点对点通信
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";//密钥
		String hmac = request.getParameter("hmac");//支付网关发来的加密验证码
		
		boolean isOk = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType, keyValue); 
		if(!isOk){
			out.print("支付数据可能被篡改，请联系客服");
		}else{
			//修改订单状态
			if("1".equals(r1_Code)){
				//支付成功
				if("2".equals(r9_BType)){//不反复发送
					out.print("success");
				}
				
				//修改订单状态
				out.print("支付成功， 已准备发货...");
				OrderService os = new OrderService();
				try {
					os.modifyOrderState(r6_Order);
				} catch (OrderExecption e) {
					System.out.println(e.getMessage());
				}
				//重定向到成功页面
				response.sendRedirect(request.getContextPath() + "/paysuccess.jsp");
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
