package serverEndPoint;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



@ServerEndpoint("/MyEchoServer")
public class MyEndPoint {

//private static final Set<Session> allSessions = Collections.synchronizedSet(new HashSet<Session>());
	private static Map<String, SessionNameVO> allSessions = new Hashtable<String, SessionNameVO>();
	
	//用來裝session和name
	class SessionNameVO{
		private Session session;
		private String name;
		public Session getSession() {
			return session;
		}
		public void setSession(Session session) {
			this.session = session;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}

	@OnOpen
	public void onOpen(Session userSession) throws IOException {
		
		SessionNameVO vo = new SessionNameVO();
		try {
			allSessions.put(userSession.getId().trim(), vo);
			System.out.println(userSession.getId() + ": 已連線");
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	@OnMessage
	public void onMessage(Session userSession, String data) {
		accessData(userSession, data);
	}
	
	private void accessData(Session userSession, String data){
		
		JSONObject obj = null;
		String type = null;
		
		try {
			obj = new JSONObject(data);
			type = obj.getString("type");

			if (type.trim().equals("sendName")){
				String name = obj.getString("name");
				setName(userSession, name);
			}else if(type.trim().equals("getOnlineList")){
				getOnlineList(userSession);
			}else{
				JSONObject message = obj.getJSONObject("message");
				String to = obj.getString("to");
				sendMessage(userSession, message, to);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	//將使用者名稱跟session放在一起
	private void setName(Session userSession, String name){
		
		SessionNameVO vo = new SessionNameVO();
		vo.setSession(userSession);
		vo.setName(name);
		
		allSessions.put(userSession.getId().trim(), vo);
	}
	
	//使用者要傳送訊息
	private void sendMessage(Session userSession, JSONObject message, String to){
		
		JSONObject obj = new JSONObject();
		try {
			obj.put("message", message);
			obj.put("type", "message");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		for (String id : allSessions.keySet()) {
			Session session = allSessions.get(id).getSession();
			if (session.isOpen() && allSessions.get(id).getName().trim().equals(to))
				session.getAsyncRemote().sendText(obj.toString());
		}
		userSession.getAsyncRemote().sendText(obj.toString());
		System.out.println("Message received: " + message);
	}
	
	//回傳online list
	private void getOnlineList(Session userSession){
		
		JSONArray array = new JSONArray();
		
		for (String id : allSessions.keySet()) {
			Session session = allSessions.get(id).getSession();
			if (session.isOpen()){
				array.put( allSessions.get(id).getName() );
			}
		}
		
		JSONObject obj = new JSONObject();
		try {
			obj.put("onlineList", array);
			obj.put("type", "onlineList");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		userSession.getAsyncRemote().sendText(obj.toString());
	}
	
	@OnError
	public void onError(Session userSession, Throwable e){
	}
	
	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		allSessions.remove(userSession);
		System.out.println(userSession.getId() + ": Disconnected: " + Integer.toString(reason.getCloseCode().getCode()));
	}

}
