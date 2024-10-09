package coms309.Cycino.follow;

import coms309.Cycino.users.User;
import coms309.Cycino.users.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FollowController {
    @Autowired
    private FollowService followService;
    @Autowired
    private UserService userService;

    @GetMapping("/users/{uid}/following")
    public List<Follow> getFollowingList(@PathVariable long uid) {
        return followService.getFollowingList(uid);
    }

    @PostMapping("/users/{uid}/follow")
    public boolean addToFollowList(@RequestBody Follow follow, @PathVariable long uid){
        return followService.addToFollowList(follow, uid);
    }

    @DeleteMapping("/users/{uid}/unfollow")
    public boolean removeFromFollowList(@RequestBody Follow follow, @PathVariable long uid){
        return followService.removeFromFollowList(follow, uid);
    }
}
