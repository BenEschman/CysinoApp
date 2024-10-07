package coms309.Cycino.users;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import coms309.Cycino.follow.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public List<User> getUsers(){
        return userService.getUsers();
    }


    @RequestMapping(method = RequestMethod.GET, path = "/users/{uid}")
    public User getUser(@PathVariable long uid){
        return userService.getUser(uid);
    }


    @PostMapping("/users/create")
    public boolean create(@RequestBody User user){
        return userService.create(user);
    }

    @Transactional
    @PostMapping("/users/{uid}/follow")
    public boolean update(@RequestBody follow follow, @PathVariable long uid){
        User user = userService.getUser(uid);
        user.newFollow(follow);
        return true;
    }

}
