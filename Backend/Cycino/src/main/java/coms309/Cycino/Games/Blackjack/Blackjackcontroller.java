package coms309.Cycino.Games.Blackjack;

import coms309.Cycino.Games.GameLogic.BlackJackLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Blackjackcontroller {

    @Autowired
    private BlackjackService bjs;

    @PostMapping("/blackjack/create/{lobby}")
    public Map<String, Object> createGame(){
        return bjs.createGame();
    }

}
