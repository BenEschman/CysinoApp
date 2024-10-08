package coms309.Cycino.follow;

import coms309.Cycino.users.User;
import coms309.Cycino.users.UserService;
import coms309.Cycino.users.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {
    @Autowired
    private FollowRepository followRepository;;
    @Autowired
    private UserService userService;

    public List<Follow> getFollowingList(Long uid) {
        User user = userService.getUser(uid);
        return followRepository.findByUser(user);
    }

    @Transactional
    public boolean addToFollowList(Follow follow, Long uid) {
        boolean ok = true;
        User user = userService.getUser(uid);
        for (Follow item : user.getFollowList()){
            if (item.getFollowingID() == follow.getFollowingID()) {
                ok = false;
                break;
            }
        }
        if (ok) {user.newFollow(follow);}
        return ok;
    }

    @Transactional
    public boolean removeFromFollowList(Follow follow, Long uid) {
        User user = userService.getUser(uid);
        for (Follow item : user.getFollowList()){
            if (item.getFollowingID() == follow.getFollowingID()) {
                user.removeFollow(item);
                followRepository.delete(item);
                return true;
            }
        }
        return false;
    }

}
