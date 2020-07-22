package kr.or.ns.websocket;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import kr.or.ns.service.ManagerService;
import kr.or.ns.service.ManagerServiceImpl;
import kr.or.ns.service.MemberService;
import kr.or.ns.service.MessageService;
import kr.or.ns.vo.Message;
import kr.or.ns.vo.Users;

@Configuration
public class ChatHandler extends TextWebSocketHandler {

	@Autowired
	private MemberService mservice;
	
	// private Map<String, WebSocketSession> users = new HashMap();
	List<HashMap<String, Object>> rls = new ArrayList(); // 웹소켓 세션을 담아둘 리스트 ---roomListSessions

	// 클라이언트와 연결 이후에 실행되는 메서드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		if (session != null) {
			boolean flag = false;
			
			 //★★★★
			 //Map<String, Object> map = session.getAttributes();
		     //String id = (String)map.get("userId");
			 //인터셉터에서 attributes.put() 한 모든 데이터를 map에 담은 뒤 필요한 데이터를
			 //키값을 이용하여 추출


			//getAttribute라는 private 함수 이용
			String ch_seq = getAttribute(session, "ch_seq");
			String user_id = getAttribute(session, "user_id");
			
			System.out.println("ch_seq : " + ch_seq);
			System.out.println("user_id : " + user_id);

			int idx = rls.size(); // 방의 사이즈를 조사한다.
			System.out.println("idx : " + idx);
			if (rls.size() > 0) {
				for (int i = 0; i < rls.size(); i++) {
					String rN = (String) rls.get(i).get("ch_seq");
					if (rN.equals(ch_seq)) {
						flag = true;
						idx = i;
						break;
					}
				}
			}


			if (flag) { // 존재하는 방이라면 세션만 추가한다.
				HashMap<String, Object> map = rls.get(idx);
				map.put(user_id, session);
				System.out.println("존재하는 방이면 세션 추가");
				sendAllSessionToMessage(map, user_id, user_id+"님이 접속되었습니다."); 
				//그 방에대한 전체 map / 접속한 사람의 user_id(키값) / 메시지
			} else { // 최초 생성하는 방이라면 방번호와 세션을 추가한다.
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("ch_seq", ch_seq);
				map.put(user_id, session);
				rls.add(map);
				System.out.println("최초 생성");

			}
			// 세션등록이 끝나면 발급받은 세션ID값의 메시지를 발송한다.
			//JSONObject obj = new JSONObject();
			//obj.put("type", "getId");
			//obj.put("user_id", user_id);
			//session.sendMessage(new TextMessage(obj.toJSONString()));
			
			//추가
			//sendAllSessionToMessage(session, user_id+"님이 접속되었습니다.");
			
		} else {
			System.out.println("afterConnectionEstablished()_session이 null");
		}
	}
	
	//메시지 보낸 자신을 제외한 나머지 연결된 세션에 메시지를 보냄
	private void sendAllSessionToMessage(HashMap<String, Object> map, String user_id, String message) {
		try {
			
			HashMap<String, Object> temp =new HashMap();
			temp.put("type", "allMessage");
			temp.put("msg", message);
			JSONObject obj = getJsonStringFromMap(temp);
			
			// 해당 방의 세션들만 찾아서 메시지를 발송해준다.
			for (String k : map.keySet()) {
				if (k.equals("ch_seq")) { // 다만 방번호일 경우에는 건너뛴다.
					continue;
				}

				WebSocketSession wss = (WebSocketSession) map.get(k);
				if (wss != null) {
					try {
						if(!k.equals(user_id)) { //key값은 user_id인데 자기자신을 제외한 모든사람들한테 메시지 전송
							wss.sendMessage(new TextMessage(obj.toJSONString()));
							System.out.println("나 뺴고 나머지한테 전송");
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}	
	
	

	// 클라이언트가 서버로 메시지를 전송했을 때 실행되는 메서드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		/*
		 * 2. 메시지 전송시 JSON파싱을 위해 message.getPayload()를 통해 받은 문자열을 만든 함수,
		 * jsonToObjectParser에 넣어서 JSONObject값으로 받아서 강제 문자열 형태로 보내주는 부분이 추가
		 */

		String rcvMsg = message.getPayload();
		JSONObject obj = JsonToObjectParser(rcvMsg);
		String rN = (String) obj.get("ch_seq");
		HashMap<String, Object> temp = new HashMap<String, Object>();
		if (rls.size() > 0) {
			for (int i = 0; i < rls.size(); i++) {
				String ch_seq = (String) rls.get(i).get("ch_seq"); // 세션리스트의 저장된 방번호를 가져와서
				if (ch_seq.equals(rN)) { // 같은값의 방이 존재한다면
					temp = rls.get(i); // 해당 방번호의 세션리스트의 존재하는 모든 object값을 가져온다.
					break;
				}
			}
			// 해당 방의 세션들만 찾아서 메시지를 발송해준다.
			for (String k : temp.keySet()) {
				if (k.equals("ch_seq")) { // 다만 방번호일 경우에는 건너뛴다.
					continue;
				}

				WebSocketSession wss = (WebSocketSession) temp.get(k);
				if (wss != null) {
					try {
						System.out.println("3단계 ");
						wss.sendMessage(new TextMessage(obj.toJSONString()));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	// 클라이언트와 연결을 끊었을 때 실행되는 메소드
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		/*
		 * sessionList.remove(session); System.out.println(("{} 연결 끊김" +
		 * session.getId()));
		 */
		
		String ch_seq = getAttribute(session, "ch_seq");
		String user_id = getAttribute(session, "user_id");
		
		
		if (session != null) {
			if (rls.size() > 0) { // 소켓이 종료되면 해당 세션값들을 찾아서 지운다.
				for (int i = 0; i < rls.size(); i++) {
					rls.get(i).remove(session.getId());
				}
			}
		} else {
			System.out.println("afterConnectionClosed()_session이 null");
		}
		 

	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		System.out.println(exception.getMessage());
	}

	// json형태의 문자열을 파라미터로 받아서 SimpleJson의 파서를 활용하여 JSONObject로 파싱처리를 해주는 함수
	private static JSONObject JsonToObjectParser(String jsonStr) {
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		try {
			obj = (JSONObject) parser.parse(jsonStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
    /**
     * Map을 json으로 변환한다.
     */
    public static JSONObject getJsonStringFromMap( HashMap<String, Object> map )
    {
        JSONObject jsonObject = new JSONObject();
        for( Map.Entry<String, Object> entry : map.entrySet() ) {
            String key = entry.getKey();
            Object value = entry.getValue();
            jsonObject.put(key, value);
        }
        
        return jsonObject;
    }
    

	public String getAttribute(WebSocketSession session, String parameter) {
		return (String) session.getAttributes().get(parameter);
	}
	


}
