package coms309.Cycino.follow;

import coms309.Cycino.users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService {
    @Autowired
    private FollowRepository followRepository;;
    @Autowired
    private UsersRepository userRepository;


}
