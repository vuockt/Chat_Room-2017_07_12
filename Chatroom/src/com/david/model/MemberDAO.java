package com.david.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MemberDAO {

	String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Chatroom";
	String userid = "sa";
	String passwd = "123456";
	private static final String LOGIN_STMT = "select * from member where name = ? and pw = ?";
	private static final String REGISTER_STMT = "insert into member values(?, ?)";

	private static DataSource ds = null;
	static {
	     try {
	          Context context = new InitialContext();
	          ds = (DataSource) context.lookup("java:comp/env/jdbc/Chatroom");
	     } catch (NamingException e) {
	          e.printStackTrace();
	     }
	}

	public MemberVO login(String name, String pw) {

		MemberVO membervo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(LOGIN_STMT);
			pstmt.setString(1, name);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				membervo = new MemberVO();
				membervo.setId(rs.getInt(1));
				membervo.setName(rs.getString(2));
				membervo.setPw(rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {e.printStackTrace();}
			try {
				pstmt.close();
			} catch (SQLException e) {e.printStackTrace();}
			try {
				con.close();
			} catch (SQLException e) {e.printStackTrace();}
		}
		
		return membervo;
	}

	//µù¥U
	public int register(MemberVO membervo) {

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(REGISTER_STMT);
			pstmt.setString(1, membervo.getName());
			pstmt.setString(2, membervo.getPw());
			
			count = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {e.printStackTrace();}
			try {
				con.close();
			} catch (SQLException e) {e.printStackTrace();}
		}
		
		return count;
	}
}
