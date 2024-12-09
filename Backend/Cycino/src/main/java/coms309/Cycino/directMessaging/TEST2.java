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
@ServerEndpoint("/test2/{user1}/{user2}")
@Component
public class TEST2 {

    // Store all socket session and their corresponding username
    // Two maps for the ease of retrieval by key
    private static Map<Session, String> sessionDmMap = new Hashtable<>();
    private static Map<String, Session> dmSessionMap = new Hashtable<>();

    // server side logger
    private final Logger logger = LoggerFactory.getLogger(DirectMessagingServer.class);

    /**
     * This method is called when a new WebSocket connection is established.
     *
     * @param session represents the WebSocket session for the connected user.
     * @param user1 username of user 1 specified in path parameter.
     * @param user2 username of user 2 specified in path parameter.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("user1") String user1, @PathParam("user2") String user2) throws IOException {
        String dm = user1+"_"+user2;
        // server side log
        logger.info("[onOpen] " + dm);

        // Handle the case of a duplicate username
        if (dmSessionMap.containsKey(dm)) {
            session.getBasicRemote().sendText("This dm connection is already opened somewhere else!");
            session.close();
        }
        else {
            // map current session with username
            sessionDmMap.put(session, dm);

            // map current username with session
            dmSessionMap.put(dm, session);

            // send to the user joining in
            sendMessageToParticularUser(dm, "Welcome to the chat server, "+ dm);

            // send to everyone in the chat
            broadcast("User: " + dm + " has Joined the Chat");
        }
    }

    /**
     * Handles incoming WebSocket messages from a client.
     *
     * @param session The WebSocket session representing the client's connection.
     * @param message The message received from the client.
     */
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {

        // get the dm by session
        String dm = sessionDmMap.get(session);
        // get user1 & user2
        String user1 = dm.split("_")[0];
        String user2 = dm.split("_")[1];

        // server side log
        logger.info("[onMessage] " + dm + ": " + message);

        // Direct message to a user using the dm & user1/user2 information
        String destination = user2+"_"+user1;
        sendMessageToParticularUser(destination, user1 + ": " + message);
        sendMessageToParticularUser(dm,user1 + ": " + message);
    }

    /**
     * Handles the closure of a WebSocket connection.
     *
     * @param session The WebSocket session that is being closed.
     */
    @OnClose
    public void onClose(Session session) throws IOException {

        // get the username from session-username mapping
        String username = sessionDmMap.get(session);

        // server side log
        logger.info("[onClose] " + username);

        // remove user from memory mappings
        sessionDmMap.remove(session);
        dmSessionMap.remove(username);

        // send the message to chat
        broadcast(username + " disconnected");
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
        String username = sessionDmMap.get(session);

        // do error handling here
        logger.info("[onError]" + username + ": " + throwable.getMessage());
    }

    /**
     * Sends a message to a specific user in the chat (DM).
     *
     * @param dm The dm connection of the recipient.
     * @param message  The message to be sent.
     */
    private void sendMessageToParticularUser(String dm, String message) {
        Session session = dmSessionMap.get(dm);
        if (session != null) {
            try {
                dmSessionMap.get(dm).getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.info("[DM Exception] " + e.getMessage());
            }
        }
        else {
            logger.info("[DM Exception] " + dm + ": " + message + " [ERROR]: dm session not found");
            // Let the sender know that the receiver is not connected to the chat at the moment.
            // This is because the receiver has yet to open the correct chat.
            String user1 = dm.split("_")[0];
            String user2 = dm.split("_")[1];
            sendMessageToParticularUser(user2+"_"+user1, "\""+user1+"\""+" is not currently connected to the chat");
        }
    }

    /**
     * Broadcasts a message to all users in the chat.
     *
     * @param message The message to be broadcasted to all users.
     */
    private void broadcast(String message) {
        sessionDmMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.info("[Broadcast Exception] " + e.getMessage());
            }
        });
    }
}