package coms309.Cycino.follow;

import coms309.Cycino.users.User;
import coms309.Cycino.users.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FollowController {
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private UserService userService;

    @Transactional
    @PostMapping("/users/{uid}/follow")
    public boolean update(@RequestBody Follow follow, @PathVariable long uid){
        User user = userService.getUser(uid);
        user.newFollow(follow);
        return true;
    }
}
