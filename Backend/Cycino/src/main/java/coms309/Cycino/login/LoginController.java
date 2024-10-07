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

    @GetMapping("/login/submit")
    public String checkInfo(@RequestBody String[] info){
        return loginService.checkInfo(info);
    }

    @GetMapping("/login/{id}")
    public LoginInfo getUser(@PathVariable long id){
        return loginService.getUser(id);
    }

    @GetMapping("/login/contains/{username}")
    public boolean containsUser(@PathVariable String username){
        return loginService.containsUser(username);
    }

    @GetMapping("/login/contains")
    public boolean containsUser(@RequestBody LoginInfo user){
        return loginService.containsUser(user);
    }

    @PostMapping("/login/setUser/{username}")
    public boolean setUser(@RequestBody User user, String username){
        return loginService.setUser(username, user);
    }

    @DeleteMapping("/login/delete/{id}")
    public boolean deleteUser(@PathVariable long id){
        return loginService.deleteUser(id);
    }

}
