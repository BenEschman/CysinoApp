package coms309.Cycino.login;

import coms309.Cycino.users.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    public boolean checkInfo(String[] info){

        AtomicBoolean contained = new AtomicBoolean(false);
        userRepository.findAll().forEach((u) -> {
            if(u.getLogin()[0].equalsIgnoreCase(info[0]) && u.getLogin()[1].equals(info[1])){
                contained.set(true);
            };
        });
        return contained.get();
    }

}
