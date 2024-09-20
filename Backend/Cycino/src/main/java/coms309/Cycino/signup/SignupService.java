package coms309.Cycino.signup;

import coms309.Cycino.users.User;
import coms309.Cycino.users.UsersRepository;
import coms309.Cycino.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignupService {

    @Autowired
    private UserService userService;

    public boolean signup(User user){
        if(userService.containsUser(user.getLogin()[0])){
            return false;
        }
        userService.addUser(user);
        return true;
    }
}
