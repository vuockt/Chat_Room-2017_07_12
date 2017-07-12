package com.david.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.david.model.MemberDAO;
import com.david.model.MemberVO;

@WebServlet("/Register.do")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegisterServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//�������
		String name = request.getParameter("registerName");
		String pw = request.getParameter("registerPw");

		//�I�smodel
		MemberDAO dao = new MemberDAO();
		MemberVO membervo = new MemberVO();
		membervo.setName(name);
		membervo.setPw(pw);
		int count = dao.register(membervo);
		
		if (count == 1){
			request.setAttribute("msg", "���U���\");
		}else
			request.setAttribute("msg", "���U����");
		
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
