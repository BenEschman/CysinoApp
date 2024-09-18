package coms309.Cycino.users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService = new UserService();

    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public List<User> getUsers(){
        return userService.getUsers();
    }

}
