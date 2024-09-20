package coms309.Cycino.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UsersRepository userRepository;

    public User getUser(String username){

        List<User> users = new ArrayList<>(userRepository.findAll());
        for(coms309.Cycino.users.User u : users){
            if(u.getLogin()[0].equalsIgnoreCase(username)){
                return u;
            }
        }
        return null;
    }

    public boolean containsUser(String username){
        List<User> users = new ArrayList<>(userRepository.findAll());
        for(coms309.Cycino.users.User u : users){
            if(u.getLogin()[0].equalsIgnoreCase(username)){
                return true;
            }
        }
        return false;
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

    public void addUser(User user){
        userRepository.save(user);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

}
