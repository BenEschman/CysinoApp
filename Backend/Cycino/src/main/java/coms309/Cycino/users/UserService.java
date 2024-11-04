package coms309.Cycino.users;

import coms309.Cycino.login.LoginInfo;
import coms309.Cycino.login.LoginService;
import coms309.Cycino.stats.UserStatsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private UserStatsController statsController;

    public User getUser(String firstName, String lastName){

        List<User> users = new ArrayList<>(userRepository.findAll());
        for(coms309.Cycino.users.User u : users){
            if(u.getContact()[0].equalsIgnoreCase(firstName) && u.getContact()[1].equalsIgnoreCase(lastName)){
                return u;
            }
        }
        return null;
    }
    public User getUser(long id){

        List<User> users = new ArrayList<>(userRepository.findAll());
        for(coms309.Cycino.users.User u : users){
            if(u.getLoginInfo().getId() == id){
                return u;
            }
        }
        return null;
    }

    public boolean create(User user, long id){
        User oldUser = getUser(id);
        if(oldUser != null) {
            oldUser.setFirstName(user.getFirstName());
            oldUser.setLastName(user.getLastName());
            oldUser.setPhoneNumber(user.getPhoneNumber());
            oldUser.setUserBiography(user.getUserBiography());
            oldUser.setRole(user.getRole());
            userRepository.save(oldUser);
            return true;
        }
        return false;
    }

    public User create(LoginInfo loginInfo){
        User user = new User();
        user.setLoginInfo(loginInfo);
        userRepository.save(user);
        return user;
    }

    public void setId(long id, User user){
        user.setId(id);
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

    public boolean deleteUser(long id){
        if(getUser(id) != null) {
            userRepository.delete(getUser(id));
            return true;
        }
        return false;
    }

    public Map<String, Object> createUserStats(String game, Long id){
        return statsController.createStats(game, id);
    }
}
