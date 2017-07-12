<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
		
		<!-- css -->
		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
		
		<!-- js -->
		<script src="js/jquery-3.2.1.min.js"></script>
		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
		
		<title>登入聊天室</title>
	</head>
	<body>
		<h2>登入</h2>
		<form id="loginForm" action="Login.do">
			<table>
				<tr>
					<td>名稱:</td>
					<td><input type="text" id="name" name="name" /></td>
				</tr>
				<tr>
					<td>密碼:</td>
					<td><input type="text" id="pw" name="pw"/></td>
				</tr>
				<tr>
					<td><input type="button" id="registerButton" value="註冊"/></td>
					<td><input type="button" id="loginButton" value="登入"/></td>
				</tr>
				<p id="errorMsg">${errorMsg}${msg}</p>
			</table>
		</form>
		
		<!-- 註冊 -->
		<div id="dialog" title="註冊">
			<form id="registerForm" action="Register.do">
				<table>
					<tr>
						<td>名稱:</td>
						<td><input type="text" id="registerName" name="registerName" /></td>
					</tr>
					<tr>
						<td>密碼:</td>
						<td><input type="text" id="registerPw" name="registerPw"/></td>
					</tr>
					<tr>
						<td><input type="button" id="registerSumit" value="註冊"/></td>
					</tr>
				</table>
			</form>
		</div>
		
	</body>
	<script>
		$(function(){
			
			var registerButton = $("#registerButton");
			var loginButton = $("#loginButton");
			var name = $("#name");
			var pw = $("#pw");
			var errorMsg = $("#errorMsg");
			var loginForm = $("#loginForm");
			var registerForm = $("#registerForm");
			var registerSumit = $("#registerSumit");
			
			//按下登入
			loginButton.click(function(){
				
				if (name.val().trim() == "" || pw.val().trim() == ""){
					//輸入有誤
					errorMsg.text("請輸入名稱和密碼");
				}else{
					//進行登入
					loginForm.submit();
				}
			});
			
			//按下註冊
			registerButton.click(function(){
				$( "#dialog" ).dialog( "open" );
			});
			
			//初始化dialog
			$( "#dialog" ).dialog({
				  autoOpen: false
			});
			
			//註冊
			registerSumit.click(function(){
				
				registerForm.submit();
				
			});
		})
	
	</script>
</html>