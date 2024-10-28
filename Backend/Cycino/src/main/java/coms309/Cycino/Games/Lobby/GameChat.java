package coms309.Cycino.Games.Lobby;

import ch.qos.logback.core.pattern.color.ANSIConstants;
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


import java.io.IOException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/chat/{lobby}/{username}")
@Component
public class GameChat {

    private static Map<Session, String> sessionUsername = new HashMap<>();
    private static Map<String, Session> usernameSession = new HashMap<>();

    private final Logger logger = LoggerFactory.getLogger(GameChat.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {

        // server side log
        logger.info("[onOpen] " + username);

        // Handle the case of a duplicate username
        if (usernameSession.containsKey(username)) {
            session.getBasicRemote().sendText("Username already exists");
            session.close();
        }
        else {
            // map current session with username
            sessionUsername.put(session, username);

            // map current username with session
            usernameSession.put(username, session);

            // send to the user joining in
            sendMessageToPArticularUser(username, ANSIConstants.GREEN_FG + "Welcome to the chat server, "+username);

            // send to everyone in the chat
            broadcast(ANSIConstants.GREEN_FG + "User: " + username + " has Joined the Chat");
        }
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {

        // get the username by session
        String username = sessionUsername.get(session);

        // server side log
        logger.info("[onMessage] " + username + ": " + message);

        // Direct message to a user using the format "@username <message>"
        if (message.startsWith("@")) {

            // split by space
            String[] split_msg =  message.split("\\s+");

            // Combine the rest of message
            StringBuilder actualMessageBuilder = new StringBuilder();
            for (int i = 1; i < split_msg.length; i++) {
                actualMessageBuilder.append(split_msg[i]).append(" ");
            }
            String destUserName = split_msg[0].substring(1);    //@username and get rid of @
            String actualMessage = actualMessageBuilder.toString();
            sendMessageToPArticularUser(destUserName, "[DM from " + username + "]: " + actualMessage);
            sendMessageToPArticularUser(username, "[DM from " + username + "]: " + actualMessage);
        }
        else { // Message to whole chat
            broadcast(ANSIConstants.CYAN_FG+  username + ": "+ ANSIConstants.DEFAULT_FG + message);
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {

        // get the username from session-username mapping
        String username = sessionUsername.get(session);

        // server side log
        logger.info("[onClose] " + username);

        // remove user from memory mappings
        sessionUsername.remove(session);
        usernameSession.remove(username);

        // send the message to chat
        broadcast(ANSIConstants.RED_FG + username + " disconnected");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {

        // get the username from session-username mapping
        String username = sessionUsername.get(session);

        // do error handling here
        logger.info("[onError]" + username + ": " + throwable.getMessage());
    }

    private void sendMessageToPArticularUser(String username, String message) {
        try {
            usernameSession.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("[DM Exception] " + e.getMessage());
        }
    }

    private void broadcast(String message) {
        sessionUsername.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.info("[Broadcast Exception] " + e.getMessage());
            }
        });
    }

}
