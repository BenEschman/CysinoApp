package coms309.Cycino.directMessaging;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import coms309.Cycino.users.User;
import coms309.Cycino.users.UserService;
import coms309.Cycino.users.UsersRepository;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller      // this is needed for this to be an endpoint to springboot
@ServerEndpoint(value = "/directMessaging/{userID}")  // this is Websocket url
public class DirectMessagingSocket {

    // cannot autowire static directly (instead we do it by the below
    // method
    private static MessageRepository msgRepo;
    private UserService userService;

    /*
     * Grabs the MessageRepository singleton from the Spring Application
     * Context.  This works because of the @Controller annotation on this
     * class and because the variable is declared as static.
     * There are other ways to set this. However, this approach is
     * easiest.
     */
    @Autowired
    public void setMessageRepository(MessageRepository repo) {
        msgRepo = repo;  // we are setting the static variable
    }

    // Store all socket session and their corresponding username.
    private static Map<Session, Long> sessionUserMap = new Hashtable<>();
    private static Map<Long, Session> userSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(DirectMessagingSocket.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("userID") Long user)
            throws IOException {

        // WORKING ON USING USER ID INSTEAD OF USERNAME; PERHAPS I SHOULD ONLY CARE ABOUT USERID,
        // SINCE IT IS THE ONLY THING GUARANTEED TO BE UNIQUE?
        //
        logger.info("Entered into Open");
        // store connecting user information
        sessionUserMap.put(session, user);
        userSessionMap.put(user, session);

        //Send chat history to the newly connected user
        sendMessageToPArticularUser(user, getChatHistory());

        // broadcast that new user joined
        String message = "User:" + user + " has Joined the Chat";
        broadcast(message);
    }


    @OnMessage
    public void onMessage(Session session, String message) throws IOException {

        // Handle new messages
        logger.info("Entered into Message: Got Message:" + message);
        Long user = sessionUserMap.get(session);

        // Direct message to a user using the format "@username <message>"
        if (message.startsWith("@")) {
            String destUsername = message.split(" ")[0].substring(1);

            // send the message to the sender and receiver
            sendMessageToPArticularUser(Long.parseLong(destUsername), "[DM] " + user + ": " + message);
            sendMessageToPArticularUser(user, "[DM] " + user + ": " + message);

        }
        else { // broadcast
            broadcast(user + ": " + message);
        }

        // Saving chat history to repository
        msgRepo.save(new Message(user, message));
    }


    @OnClose
    public void onClose(Session session) throws IOException {
        logger.info("Entered into Close");

        // remove the user connection information
        Long user = sessionUserMap.get(session);
        sessionUserMap.remove(session);
        userSessionMap.remove(user);

        // broadcase that the user disconnected
        String message = user + " disconnected";
        broadcast(message);
    }


    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        logger.info("Entered into Error");
        throwable.printStackTrace();
    }


    private void sendMessageToPArticularUser(Long user, String message) {
        try {
            userSessionMap.get(user).getBasicRemote().sendText(message);
        }
        catch (IOException e) {
            logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }


    private void broadcast(String message) {
        sessionUserMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            }
            catch (IOException e) {
                logger.info("Exception: " + e.getMessage().toString());
                e.printStackTrace();
            }

        });

    }


    // Gets the Chat history from the repository
    private String getChatHistory() {
        List<Message> messages = msgRepo.findAll();

        // convert the list to a string
        StringBuilder sb = new StringBuilder();
        if(messages != null && messages.size() != 0) {
            for (Message message : messages) {
                sb.append(message.getUid().toString() + ": " + message.getContent() + "\n");
            }
        }
        return sb.toString();
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
} // end of Class
