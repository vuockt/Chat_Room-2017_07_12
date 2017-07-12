<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
		
		<!-- css -->
		<link rel="stylesheet" href="css/index.css" type="text/css"/>
		
		<!-- js -->
		<script src="js/jquery-3.2.1.min.js"></script>
		
		<title>聊天室</title>
	</head>
	<body>
		<h2 style="margin-left:400px">聊天室</h2>
		<span id="status" style="margin-left:400px"> </span><span>${LoginOK.name}, 歡迎</span>
		<table id="page">
			<tr>
				<td style="height:480px">
					<!-- 聊天室內容區域 -->
					<textarea id="messagesArea" class="panel message-area" readonly ></textarea>
				</td>
				<td rowspan="2">
					<!-- 在線上成員列表 -->
					<input type="button" id="updateOnlineListButton" value="更新列表"/>
					<h3>在線上</h3>
					<ul id="onlineList">
					</ul>
					<p>點擊列表中的成員跟對方聊天吧!</p>
					<!-- 未上線成員列表 -->
<!-- 					<h3>未上線</h3> -->
<!-- 					<ul id="offlineList"> -->
<!-- 						<li>a</li> -->
<!-- 						<li>b</li> -->
<!-- 					</ul> -->
				</td>
			</tr>
			<tr>
				<td>
					<!-- 輸入區 -->
					<input type="text" id="inputMessage" style="width:300px">
					<input type="button" id="inputButton" value="送出訊息">
				</td>
			</tr>
		</table>
		
		<audio id="ring" src="mp3/Q.mp3" preload="auto"></audio>
		
	</body>
	<script>
		$(function(){
			
			var MyPoint = "/MyEchoServer";
		    var host = window.location.host;
		    var path = window.location.pathname;
		    var webCtx = path.substring(0, path.indexOf('/', 1));
		    var url = "ws://" + window.location.host + webCtx + MyPoint;
			
			
			var socket = null;
// 			var url = "ws://192.168.0.169:8081/Chatroom/MyEchoServer";
			var status = $("#status");
			var messagesArea = $("#messagesArea");
			var inputMessage = $("#inputMessage");
			var inputButton = $("#inputButton");
			var onlineList = $("#onlineList");
			var updateOnlineListButton = $("#updateOnlineListButton");
			var to = "";
			var ring = $("#ring");
			
			init();
			
			//ready後立刻要做的事
			function init(){
				inputMessage.focus();
				socket = new WebSocket(url);
				
				//綁定事件:open、close、error、message
				socket.onopen = function(evt) {
		            onOpen(evt)
		        };
				
		        socket.onclose = function(evt) {
		            onClose(evt)
		        };
				
		        socket.onmessage = function(evt) {
		            onMessage(evt)
		        };
				
		        socket.onerror = function(evt) {
		            onError(evt)
		        };
			}
			
			//成功建立連線，送出暱稱到server
			function onOpen(evt) {
				showStatus("聊天室已連線");
				sendName("${LoginOK.name}");
				getOnlineList();
			}
			
			//把名稱送到server端
			function sendName(name){
				var jsonObj = {"type" : "sendName", "name" : name};
		        socket.send(JSON.stringify(jsonObj));
			}
			
			//取得上線的人
			function getOnlineList(){
				var jsonObj = {"type" : "getOnlineList"};
		        socket.send(JSON.stringify(jsonObj));
			}
			
			//切開連線
			function onClose(evt) {
				showStatus("聊天室未連線");
			}
			
			//收到server端的message
			function onMessage(evt) {
				var jsonObj = JSON.parse(evt.data);
				if (jsonObj.type == "onlineList"){
					onlineList.empty();
					$.each(jsonObj.onlineList, function(i, data){
						
						var li = $("<li></li>").text(data).addClass("onlineMember");
						onlineList.append(li);
						
					});
				}else{
					showMessage(jsonObj.message.name + ":" + jsonObj.message.message);
				}
			}
			
			//點擊聊天室中的某個人
			onlineList.on("click", ".onlineMember", function(){
				to = $(this).text();
			});
			
			
			//更新online list
			updateOnlineListButton.click(function(){
				getOnlineList();
			});
			
			//發生錯誤			
			function onError(evt) {
				showStatus("聊天室發生錯誤");
			} 
			
			//使用者按下送出訊息
			inputButton.click(function(){
				sendMessage();
			});
			
			//按下enter
			inputMessage.keyup(function(event){
				
				var code = event.keyCode;
				if (code == 13)
					sendMessage();
			});
			
			//送訊息
			function sendMessage(){
				
				if (to == "")
					return;
				
				var message = inputMessage.val();
				var jsonObj = {   "type" : "sendMessage",
					           "message" : {"name" : "${LoginOK.name}", "message" : message}, 
					                "to" : to };
				
		        socket.send(JSON.stringify(jsonObj));
				inputMessage.val("");
			}
			
			//將訊息show在messagesArea中
			function showMessage(message){
				console.log(message);
				var time = getTime1();
				messagesArea.append(time).append(message).append("\r\n");
				document.getElementById('ring').play();
			}
			
			//取得時間
			function getTime1(){
				var date = new Date();
				var time = "(" + plusZero(date.getHours()) + ":" + plusZero(date.getMinutes()) + ")";
				return time;
			}
			
			//補0
			function plusZero(input){
				input = (input<10) ? 0+input : input;
				return input
			}
			
			
			//將連線狀態show在畫面最上方
			function showStatus(message){
				status.text("("+message+")");
			}
			
			
		})
	</script>
</html>