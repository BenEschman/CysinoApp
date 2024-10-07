package coms309.Cycino.stats;

import coms309.Cycino.Games;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserStatsController {

    @Autowired
    private UserStatsRepo userStatsRepo;

    @GetMapping("/stats/{game}")
    public UserStats getStats(@PathVariable String game, @RequestHeader("userId") long userId){
       return userStatsRepo.getReferenceById(userId + game);
    }

    @GetMapping("/stats/all/{game}")
    public List<UserStats> getStats(@PathVariable String game){
        List<UserStats> userStats = new ArrayList<>();
        for(UserStats u : userStatsRepo.findAll()){
            String id = u.getUserId();
            id = id.substring(id.length() - game.length());
            if(id.equalsIgnoreCase(game))
                userStats.add(u);
        }
        return userStats;
    }

    @PostMapping("/stats/create/{game}")
    public boolean createStats(@PathVariable String game, @RequestHeader("userId") long userId ){
        if(!userStatsRepo.existsById(userId + game)){
            userStatsRepo.save(new UserStats(userId, Games.valueOf(game)));
            return true;
        }
        return false;
    }

    @DeleteMapping("/stats/delete")
    public boolean delete(@RequestHeader String userId){
        if(userStatsRepo.existsById(userId)){
            userStatsRepo.deleteById(userId);
            return true;
        }
        return false;
    }

    @PutMapping("/stats/update/{win}/{loss}")
    public void update(@PathVariable int win, @PathVariable int loss, @RequestHeader("userId") String userId){
        userStatsRepo.getReferenceById(userId).updateWins(win, loss);
    }
}
