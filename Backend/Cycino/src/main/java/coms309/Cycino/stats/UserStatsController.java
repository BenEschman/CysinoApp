package coms309.Cycino.stats;

import coms309.Cycino.Games;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserStatsController {

    @Autowired
    private UserStatsRepo userStatsRepo;

    @GetMapping("/stats/{game}")
    public UserStats getStats(@PathVariable String game, @RequestHeader("userId") long userId){
       return userStatsRepo.getReferenceById(userId + Games.valueOf(game).ordinal());
    }

    @PostMapping("/stats/create/{game}")
    public boolean createStats(@PathVariable String game, @RequestHeader("userId") long userId ){
        if(!userStatsRepo.existsById(userId + Games.valueOf(game).ordinal())){
            userStatsRepo.save(new UserStats(userId, Games.valueOf(game)));
            return true;
        }
        return false;
    }

    @PutMapping("/stats/update/{win}/{loss}")
    public void update(@PathVariable int win, @PathVariable int loss, @RequestHeader("userId") long userId){
        userStatsRepo.getReferenceById(userId).updateWins(win, loss);
    }
}
