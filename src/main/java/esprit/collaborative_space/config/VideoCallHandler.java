package esprit.collaborative_space.config;


import esprit.collaborative_space.entity.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class    VideoCallHandler extends TextWebSocketHandler {

    // Maps peerId to their WebSocket session
    private static final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("WebSocket connected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);

            switch (chatMessage.getType()) {
                case "register":
                    // Save peerId → session mapping
                    sessions.put(chatMessage.getFrom(), session);
                    System.out.println("Registered peerId: " + chatMessage.getFrom());
                    break;

                case "chat":
                    WebSocketSession receiverSession = sessions.get(chatMessage.getTo());
                    if (receiverSession != null && receiverSession.isOpen()) {
                        receiverSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
                        System.out.println("Forwarded chat from " + chatMessage.getFrom() + " to " + chatMessage.getTo());
                    } else {
                        System.out.println("User " + chatMessage.getTo() + " is not connected.");
                    }
                    break;

                default:
                    System.out.println("Unknown message type: " + chatMessage.getType());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.values().remove(session);
        System.out.println("WebSocket closed: " + session.getId());
    }

}