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
		
		//�������
		String name = request.getParameter("name");
		String pw = request.getParameter("pw");
		
		//�d�ߦ��b�K
		MemberDAO dao = new MemberDAO();		
		MemberVO membervo = dao.login(name, pw);
		
		if (membervo == null){
			//�L���b���n�J����
			request.setAttribute("errorMsg", "�L���b��");
			
			//���e��
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		}else{
			//�n�J���\
			session.setAttribute("LoginOK", membervo);
			
			//���e��
			RequestDispatcher rd = request.getRequestDispatcher("chatroom.jsp");
			rd.forward(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
