package coms309.Cycino.follow;

import coms309.Cycino.users.User;
import coms309.Cycino.users.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, Object> addToFollowList(@RequestBody Follow follow, @PathVariable long uid){
        Map<String, Object> result = new HashMap<>();
        if (followService.addToFollowList(follow, uid)){
            result.put("status", "200 OK");
        } else {
            result.put("status", "400 Bad Request");
        }
        return result;
    }

    @DeleteMapping("/users/{uid}/unfollow")
    public Map<String, Object> removeFromFollowList(@RequestBody Follow follow, @PathVariable long uid){
        Map<String, Object> result = new HashMap<>();
        if (followService.removeFromFollowList(follow, uid)){
            result.put("status", "200 OK");
        } else {
            result.put("status", "400 Bad Request");
        }
        return result;
    }
}
