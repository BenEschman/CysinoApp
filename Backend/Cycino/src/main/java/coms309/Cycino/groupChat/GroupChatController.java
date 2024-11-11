package coms309.Cycino.groupChat;

import coms309.Cycino.users.User;
import coms309.Cycino.users.UserService;
import coms309.Cycino.users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
public class GroupChatController {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    GroupChatRepository groupChatRepository;
    @Autowired
    UserService userService;

    @GetMapping("/directMessaging/{uid}/groupChats")
    public Set<GroupChat> groupChat(@PathVariable Long uid) {
        User user = userService.getUser(uid);
        return user.getGroupChats();
    }

    @PostMapping("directMessaging/{uid}/groupChats/create")
    public Map<String, Object> createGroupChat(@PathVariable Long uid, @RequestBody String name) {
        Map<String, Object> response = new HashMap<>();
        System.out.println(name);
        try {
            User user = userService.getUser(uid);
            GroupChat groupChat = new GroupChat();
            groupChat.setGroupName(name);
            user.addGroupChat(groupChat);
            groupChatRepository.save(groupChat);
            response.put("status", "200 OK");
        } catch(Exception e) {
            response.put("error", e.getMessage());
        }
        return response;
    }
}
