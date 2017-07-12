package test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public TestServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//登入測試
//		MemberDAO dao = new MemberDAO();
//		MemberVO membervo = dao.login("先河", "123");
//		String name = membervo.getName();
		
		//註冊測試
//		MemberDAO dao2 = new MemberDAO();
//		MemberVO membervo2 = new MemberVO();
//		membervo2.setName("多多");
//		membervo2.setPw("456");
//		int count = dao2.register(membervo2);
//		System.out.println(count);
		
		//處理Json資料
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
