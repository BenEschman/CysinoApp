package coms309.Cycino.Games.Lobby;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/chat/{lobby}/{username}")
@Component
public class GameChat {

    private static final Map<Long, Map<Session, String>> lobbySessions = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(GameChat.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("lobby") Long lobby, @PathParam("username") String username) throws IOException {
        logger.info("[onOpen] " + username + " joined lobby: " + lobby);

        lobbySessions.putIfAbsent(lobby, new HashMap<>());

        Map<Session, String> sessions = lobbySessions.get(lobby);

        if (sessions.containsValue(username)) {
            session.getBasicRemote().sendText("Username already exists in this lobby");
            session.close();
        } else {

            sessions.put(session, username);
            sendMessageToUser(session, "Welcome to the chat server, " + username);
            broadcast(lobby, "User: " + username + " has joined the chat");
        }
    }

    @OnMessage
    public void onMessage(Session session, @PathParam("lobby") Long lobby, String message) throws IOException {
        String username = lobbySessions.get(lobby).get(session);
        logger.info("[onMessage] " + username + " in lobby " + lobby + ": " + message);


        if (message.startsWith("@")) {
            String[] splitMessage = message.split("\\s+", 2);
            if (splitMessage.length > 1) {
                String destUsername = splitMessage[0].substring(1); // Get username after '@'
                String actualMessage = splitMessage[1];
                sendDirectMessage(lobby, username, destUsername, "[DM from " + username + "]: " + actualMessage);
            }
        } else {
            broadcast(lobby, username + ": " + message);
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("lobby") Long lobby) throws IOException {
        String username = lobbySessions.get(lobby).get(session);
        logger.info("[onClose] " + username + " left lobby: " + lobby);


        lobbySessions.get(lobby).remove(session);


        if (lobbySessions.get(lobby).isEmpty()) {
            lobbySessions.remove(lobby);
        }


        broadcast(lobby, username + " disconnected");
    }

    @OnError
    public void onError(Session session, Throwable throwable, @PathParam("lobby") Long lobby) {
        String username = lobbySessions.get(lobby).get(session);
        logger.info("[onError] " + username + " in lobby " + lobby + ": " + throwable.getMessage());
    }

    private void sendDirectMessage(Long lobby, String fromUsername, String toUsername, String message) {
        Map<Session, String> sessions = lobbySessions.get(lobby);

        if (sessions != null) {
            sessions.forEach((session, username) -> {
                if (username.equals(toUsername) || username.equals(fromUsername)) {
                    try {
                        session.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        logger.info("[DM Exception] " + e.getMessage());
                    }
                }
            });
        }
    }

    private void broadcast(Long lobby, String message) {
        Map<Session, String> sessions = lobbySessions.get(lobby);

        if (sessions != null) {
            sessions.forEach((session, username) -> {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    logger.info("[Broadcast Exception] " + e.getMessage());
                }
            });
        }
    }

    private void sendMessageToUser(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("[Send Exception] " + e.getMessage());
        }
    }

    public class ImageEncoder {

        public static String encodeImageToBase64(File imageFile) throws IOException {
            BufferedImage image = ImageIO.read(imageFile);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        }
    }
}
