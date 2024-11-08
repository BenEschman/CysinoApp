package coms309.Cycino.directMessaging;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;

/**
 * Represents a WebSocket chat server for handling real-time communication
 * between users. Each user connects to the server using their unique
 * username.
 *
 * This class is annotated with Spring's `@ServerEndpoint` and `@Component`
 * annotations, making it a WebSocket endpoint that can handle WebSocket
 * connections at the "/chat/{username}" endpoint.
 *
 * Example URL: ws://localhost:8080/chat/username
 *
 * The server provides functionality for broadcasting messages to all connected
 * users and sending messages to specific users.
 */
@ServerEndpoint("/dm/{user1}/{user2}")
@Component
public class DirectMessagingServer {

    // Store all socket session and their corresponding username
    // Two maps for the ease of retrieval by key
    private static Map<Session, String> sessionUserMap = new Hashtable<>();
    private static Map<String, Session> userSessionMap = new Hashtable<>();

    // server side logger
    private final Logger logger = LoggerFactory.getLogger(DirectMessagingServer.class);

    /**
     * This method is called when a new WebSocket connection is established.
     *
     * @param session represents the WebSocket session for the connected user.
     * @param user1 username of user 1 specified in path parameter.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("user1") String user1) throws IOException {

        // server side log
        logger.info("[onOpen] " + user1);

        // Handle the case of a duplicate username
        if (userSessionMap.containsKey(user1)) {
            session.getBasicRemote().sendText("User is already connected");
            //session.close();
        }
        else {
            // map current session with username
            sessionUserMap.put(session, user1);

            // map current username with session
            userSessionMap.put(user1, session);

            // send to the user joining in
            sendMessageToParticularUser(user1, user1, "Welcome to the chat server, "+user1);
        }
    }

    /**
     * Handles incoming WebSocket messages from a client.
     *
     * @param session The WebSocket session representing the client's connection.
     * @param message The message received from the client.
     * @param user2 The recipient of the dm.
     */
    @OnMessage
    public void onMessage(Session session, String message, @PathParam("user2") String user2) throws IOException {
        // get the username by session
        String user1 = sessionUserMap.get(session);
        // server side log
        logger.info("[onMessage] " + user1 + ": " + message);
        // send message back to sender
        sendMessageToParticularUser(user1, user1, user1+": "+message);
        // send message to recipient
        sendMessageToParticularUser(user1, user2, user1+": "+message);
    }

    /**
     * Handles the closure of a WebSocket connection.
     *
     * @param session The WebSocket session that is being closed.
     */
    @OnClose
    public void onClose(Session session) throws IOException {

        // get the username from session-username mapping
        String user1 = sessionUserMap.get(session);

        // server side log
        logger.info("[onClose] " + user1);

        // remove user from memory mappings
        sessionUserMap.remove(session);
        userSessionMap.remove(user1);

        // send the message to chat
        broadcast(user1 + " disconnected");
    }

    /**
     * Handles WebSocket errors that occur during the connection.
     *
     * @param session   The WebSocket session where the error occurred.
     * @param throwable The Throwable representing the error condition.
     */
    @OnError
    public void onError(Session session, Throwable throwable) {

        // get the username from session-username mapping
        String username = sessionUserMap.get(session);

        // do error handling here
        logger.info("[onError]" + username + ": " + throwable.getMessage());
    }

    /**
     * Sends a message to a specific user in the chat (DM).
     *
     * @param recipient The username of the recipient.
     * @param message  The message to be sent.
     */
    private void sendMessageToParticularUser(String sender, String recipient, String message) throws IOException {
        // Checks if there is an active session for the recipient
        if (userSessionMap.get(recipient) != null) {
            try {
                userSessionMap.get(recipient).getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.info("[DM Exception] " + e.getMessage());
            }
        } else {
            logger.info("[DM Exception] " + recipient + " not connected");
            sendMessageToParticularUser(sender, sender,"[DM Exception] " + recipient + " not connected");
        }
    }

    /**
     * Broadcasts a message to all users in the chat.
     *
     * @param message The message to be broadcasted to all users.
     */
    private void broadcast(String message) {
        sessionUserMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.info("[Broadcast Exception] " + e.getMessage());
            }
        });
    }
}