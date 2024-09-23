package coms309.Cycino.users;

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

    public void addUser(User user){
        userRepository.save(user);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

}
