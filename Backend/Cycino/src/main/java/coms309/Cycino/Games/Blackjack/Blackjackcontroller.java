package coms309.Cycino.Games.Blackjack;

import coms309.Cycino.Games.GameLogic.BlackJackLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Blackjackcontroller {

    @Autowired
    private BlackjackService bjs;

    @PostMapping("/blackjack/create/{lobby}")
    public Map<String, Object> createGame(@PathVariable Long lobby){
        return bjs.createGame(6, lobby);
    }
    @PostMapping("/blackjack/create/{lobby}/{deck}")
    public Map<String, Object> createGame(@PathVariable int deck, @PathVariable Long lobby){
        return bjs.createGame(deck, lobby);
    }

    @PutMapping("/blackjack/{lobby}/hit/{userId}")
    public Map<String, Object> hit(@PathVariable Long lobby, @PathVariable Long userId){
        return bjs.hit(lobby, userId);
    }
    @PutMapping("/blackjack/{lobby}/stand")
    public Map<String, Object> stand(@PathVariable Long lobby, @PathVariable Long userId){
        return bjs.stand(lobby, userId);
    }

    @PutMapping("/blackjack/{lobby}/double")
    public Map<String, Object> doubleBlackJack(@PathVariable Long lobby, @PathVariable Long userId){
        return bjs.dou(lobby, userId);
    }
    @PutMapping("/blackjack/{lobby}/split")
    public Map<String, Object> Split(@PathVariable Long lobby, @PathVariable Long userId){
        return bjs.split(lobby, userId);
    }

    @DeleteMapping("/blackjack/delete/{lobby}")
    public Map<String, Object> delete(Long lobby){
        return bjs.deleteGame(lobby);
    }

    @GetMapping("/blackjack/getHand/{lobby}/{userId}")
    public Map<String, Object> getHand(@PathVariable Long lobby, @PathVariable Long userId){
        return bjs.getHand(lobby, userId);
    }


}
