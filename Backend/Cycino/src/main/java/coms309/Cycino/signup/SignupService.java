package coms309.Cycino.signup;

import coms309.Cycino.login.LoginInfo;
import coms309.Cycino.login.LoginService;
import coms309.Cycino.users.User;
import coms309.Cycino.users.UsersRepository;
import coms309.Cycino.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignupService {

    @Autowired
    private LoginService loginService;

    public Long signup(LoginInfo user){
        if(loginService.containsUser(user.getUsername())){
            return null;
        }
        loginService.addUser(user);
        return user.getId();
    }
}
