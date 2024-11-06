package coms309.Cycino.Games.Blackjack;

import coms309.Cycino.Games.GameLogic.BlackJackLogic;
import coms309.Cycino.lobby.Lobby;
import coms309.Cycino.lobby.LobbyService;
import coms309.Cycino.users.User;
import coms309.Cycino.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class BlackjackService {

    @Autowired
    private LobbyService lobbyService;
    @Autowired
    private BlackJackRepo blackJackRepo;
    @Autowired
    private UserService userService;

    public Map<String, Object> createGame(int decks, Long lobbyId){
        Map<String, Object> response = new HashMap<>();
        Lobby l = (Lobby) lobbyService.getPlayers(lobbyId).get("lobby");
        BlackJack blackJack = new BlackJack(decks, l);
        response.put("status", "200 ok");
        response.put("blackjack", blackJack);
        blackJackRepo.save(blackJack);
        return response;
    }

    public Map<String, Object> deleteGame(Long BlackJackid){
        Map<String, Object> response = new HashMap<>();
        blackJackRepo.deleteById(BlackJackid);
        return response;
    }

    public Map<String, Object> hit(Long BlackJackid, Long id){
        User user = userService.getUser(id);
        BlackJack blj = blackJackRepo.findById(BlackJackid).orElse(null);
        assert blj != null;
        return BlackJackLogic.hit(blj.getHand(user), blj.getCards());
    }

    public Map<String, Object> stand(Long BlackJackid, Long id){
        User user = userService.getUser(id);
        Map<String, Object> response = new HashMap<>();
        return response;
    }

    public Map<String, Object> dou(Long BlackJackid, Long id){
        User user = userService.getUser(id);
        Map<String, Object> response = new HashMap<>();
        return response;
    }

    public Map<String, Object> split(Long BlackJackid, Long id){
        User user = userService.getUser(id);
        Map<String, Object> response = new HashMap<>();
        return response;
    }

    public Map<String, Object> getHand(Long BlackJackid, Long id){
        User user = userService.getUser(id);
        Map<String, Object> response = new HashMap<>();
        BlackJack blj = blackJackRepo.findById(BlackJackid).orElse(null);
        assert blj != null;
        if(blj.getHand(user) == null){
            response.put("status", "404 not found");
            response.put("error", "That user is not in this game");
            return response;
        }
        response.put("status", "200 ok");
        response.put("hand", blj.getHands());
        return response;
    }

}
