package coms309.Cycino.login;

import coms309.Cycino.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login/submit")
    public Map<String, Object> checkInfo(@RequestBody LoginInfo info){
        return loginService.checkInfo(info);
    }

    @GetMapping("/login/{id}")
    public LoginInfo getUser(@PathVariable long id){
        return loginService.getUser(id);
    }

    @GetMapping("/login/contains/{username}")
    public Map<String, Object> containsUser(@PathVariable String username){
        return loginService.containsUser(username);
    }

    @PostMapping("/login/setUser/{username}")
    public Map<String, Object> setUser(@RequestBody User user, String username){
        return loginService.setUser(username, user);
    }

    @DeleteMapping("/login/delete/{id}")
    public Map<String, Object> deleteUser(@PathVariable long id){
        return loginService.deleteUser(id);
    }

}
