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
    private LoginRepository loginRepository;

    public boolean checkInfo(String[] info){

        if(containsUser(info[0])){
            return getUser(info[0]).getPassword().equals(info[1]);
        }
        return false;
    }

    public LoginInfo getUser(String username){

        List<LoginInfo> users = new ArrayList<>(loginRepository.findAll());
        for(LoginInfo u : users){
            if(u.getUsername().equalsIgnoreCase(username)){
                return u;
            }
        }
        return null;
    }

//    public coms309.Cycino.users.User makeUser(String username){
//
//        return new User("10", "BenEsch", "BEschman3905!", "Ben", "Eschman", "815-528-3105", "Admin" );
//
//    }
    public boolean containsUser(LoginInfo user){
        List<LoginInfo> users = new ArrayList<>(loginRepository.findAll());
        for(LoginInfo u : users){
            if(u.equals(user)){
                return true;
            }
        }
        return false;
    }
    public boolean containsUser(String username){
        List<LoginInfo> users = new ArrayList<>(loginRepository.findAll());
        for(LoginInfo u : users){
            if(u.getUsername().equalsIgnoreCase(username)){
                return true;
            }
        }
        return false;
    }

    public void addUser(LoginInfo user){
        loginRepository.save(user);
    }

    public boolean setUser(String username, User user){
        LoginInfo login = getUser(username);
        login.setUser(user);
        return true;
    }

}
