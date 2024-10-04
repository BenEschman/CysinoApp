package coms309.Cycino.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @PutMapping("/users/update/{id}")
    public boolean create(@RequestBody User user, @PathVariable long id){
        return userService.create(user, id);
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable long id){
        return userService.getUser(id);
    }

}
