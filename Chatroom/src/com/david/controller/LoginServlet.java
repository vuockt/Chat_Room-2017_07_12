package com.david.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.david.model.MemberDAO;
import com.david.model.MemberVO;

@WebServlet("/Login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		
		//接收資料
		String name = request.getParameter("name");
		String pw = request.getParameter("pw");
		
		//查詢此帳密
		MemberDAO dao = new MemberDAO();		
		MemberVO membervo = dao.login(name, pw);
		
		if (membervo == null){
			//無此帳號登入失敗
			request.setAttribute("errorMsg", "無此帳號");
			
			//轉交畫面
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		}else{
			//登入成功
			session.setAttribute("LoginOK", membervo);
			
			//轉交畫面
			RequestDispatcher rd = request.getRequestDispatcher("chatroom.jsp");
			rd.forward(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
