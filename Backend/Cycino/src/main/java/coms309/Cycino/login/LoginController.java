package coms309.Cycino.login;

import coms309.Cycino.users.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {


    private LoginService loginService;

    @PostMapping("/login/submit")
    public boolean checkInfo(@RequestBody String[] info){
        return loginService.checkInfo(info);
    }

    @GetMapping("/login/{username}")
    public User getUser(@PathVariable String username){
        return loginService.getUser(username);
    }

}
