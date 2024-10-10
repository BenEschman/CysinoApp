package coms309.Cycino.stats;

import coms309.Cycino.Games;
import coms309.Cycino.users.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserStatsController {

    @Autowired
    private UserStatsRepo userStatsRepo;

    @GetMapping("/stats/{game}")
    public UserStats getStats(@PathVariable String game, @RequestHeader("userId") Long userId){
       return userStatsRepo.findById(userId + game.toUpperCase()).orElse(null);
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
    public Map<String, Object> createStats(@PathVariable String game, @RequestHeader("userId") Long userId ){
        Map<String, Object> response = new HashMap<>();
        if(!userStatsRepo.existsById(userId + game.toUpperCase())){
            response.put("status", "200 ok");
            userStatsRepo.save(new UserStats(userId, Games.valueOf(game)));
            response.put("Id", userId + game.toUpperCase());
            return response;
        }
        response.put("status", "500");
        response.put("error", "user already exists");
        return response;
    }

    @DeleteMapping("/stats/delete/{game}")
    public Map<String, Object> delete(@PathVariable String game,@RequestHeader Long userId){
        Map<String, Object> response = new HashMap<>();
        if(userStatsRepo.existsById(userId + game.toUpperCase())){
            userStatsRepo.deleteById(userId + game.toUpperCase());
            response.put("status", "200 ok");
            return response;
        }
        response.put("status", "200 ok");
        response.put("error", "No user with that id");
        return response;
    }

    @PutMapping("/stats/update/{win}/{loss}")
    public void update(@PathVariable int win, @PathVariable int loss, @RequestHeader("userId") String userId){
        userStatsRepo.getReferenceById(userId).updateWins(win, loss);
    }
}
