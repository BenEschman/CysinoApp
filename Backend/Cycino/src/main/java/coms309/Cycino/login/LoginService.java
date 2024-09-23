package coms309.Cycino.login;

import coms309.Cycino.users.User;
import coms309.Cycino.users.UsersRepository;
import coms309.Cycino.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class LoginService {

    @Autowired
    private UserService userService;

    public boolean checkInfo(String[] info){

        if(userService.containsUser(info[0])){
            return userService.getUser(info[0]).getLogin()[1].equals(info[1]);
        }
        return false;
    }

    public coms309.Cycino.users.User getUser(String username){

        return userService.getUser(username);

    }

    public coms309.Cycino.users.User makeUser(String username){

        return new User("10", "BenEsch", "BEschman3905!", "Ben", "Eschman", "815-528-3105", "Admin" );

    }

}
