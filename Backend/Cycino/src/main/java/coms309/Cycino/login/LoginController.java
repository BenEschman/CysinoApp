package coms309.Cycino.login;

import coms309.Cycino.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login/submit")
    public boolean checkInfo(@RequestBody String[] info){
        return loginService.checkInfo(info);
    }

    @GetMapping("/login/{username}")
    public User getUser(@PathVariable String username){
        return loginService.getUser(username);
    }

    @GetMapping("/new/{username}")
    public User makeUser(@PathVariable String username){
        return loginService.makeUser(username);
    }


}
