package coms309.Cycino.login;

import coms309.Cycino.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login/submit")
    public boolean checkInfo(@RequestBody String[] info){
        return loginService.checkInfo(info);
    }

    @GetMapping("/login/{username}")
    public LoginInfo getUser(@PathVariable String username){
        return loginService.getUser(username);
    }

    @GetMapping("/login/contains/{username}")
    public boolean containsUser(@PathVariable String username){
        return loginService.containsUser(username);
    }

    @GetMapping("/login/contains")
    public boolean containsUser(@RequestBody LoginInfo user){
        return loginService.containsUser(user);
    }
//    @GetMapping("/new/{username}")
//    public User makeUser(@PathVariable String username){
//        return loginService.makeUser(username);
//    }


}
