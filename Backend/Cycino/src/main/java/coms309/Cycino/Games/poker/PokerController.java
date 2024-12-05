package coms309.Cycino.Games.poker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class PokerController {

    @Autowired
    private PokerService service;

    @PostMapping("/poker/create/{lobby}")
    public Map<String, Object> createPoker(@PathVariable long lobby){
        return service.createPoker(lobby);
    }

    @GetMapping("/poker/eval/{lobby}")
    public Map<String, Object> getEval(@PathVariable long lobby){
        return service.getEval(lobby);
    }

    @PutMapping("poker/start/{lobby}")
    public Map<String, Object> start(@PathVariable long lobby){
        return service.startGame(lobby);
    }

    @GetMapping("poker/hands/{lobby}")
    public Map<String, Object> getHands(@PathVariable long lobby){
        return service.getHands(lobby);
    }

    @GetMapping("poker/finish/{lobby}")
    public Map<String, Object> finish(@PathVariable long lobby){
        return service.finish(lobby);
    }
}
