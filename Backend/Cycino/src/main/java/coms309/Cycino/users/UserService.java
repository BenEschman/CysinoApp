package coms309.Cycino.users;

import coms309.Cycino.login.LoginInfo;
import coms309.Cycino.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UsersRepository userRepository;

    public User getUser(String firstName, String lastName){

        List<User> users = new ArrayList<>(userRepository.findAll());
        for(coms309.Cycino.users.User u : users){
            if(u.getContact()[0].equalsIgnoreCase(firstName) && u.getContact()[1].equalsIgnoreCase(lastName)){
                return u;
            }
        }
        return null;
    }

    public User getUser(Long uid){
        List<User> users = new ArrayList<>(userRepository.findAll());
        for(coms309.Cycino.users.User u : users){
            if (u.getId() == uid){
                return u;
            }
        }
        return null;
    }

    public boolean create(User user){
        System.out.println(user);
        if(!containsUser(user)) {
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean containsUser(String firstName, String lastName){
        return getUser(firstName, lastName) != null;
    }

    public boolean containsUser(User user){
        List<User> users = new ArrayList<>(userRepository.findAll());
        for(coms309.Cycino.users.User u : users){
            if(u.equals(user)){
                return true;
            }
        }
        return false;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }
}
