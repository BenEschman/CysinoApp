package coms309.Cycino.signup;

import coms309.Cycino.login.LoginInfo;
import coms309.Cycino.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignupController {


    @Autowired
    private SignupService signupService;


    @PostMapping("/signup/register")
    public boolean signup(@RequestBody LoginInfo user){
        return signupService.signup(user);
    }
}
