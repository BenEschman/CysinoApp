package coms309.Cycino.Games.Blackjack;

import coms309.Cycino.Games.GameLogic.BlackJackLogic;
import coms309.Cycino.Games.GameLogic.HandsRepo;
import coms309.Cycino.Games.GameLogic.PlayerHands;
import coms309.Cycino.lobby.Lobby;
import coms309.Cycino.lobby.LobbyService;
import coms309.Cycino.users.User;
import coms309.Cycino.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class BlackjackService {
    @Autowired
    private HandsRepo repo;

    @Autowired
    private LobbyService lobbyService;
    @Autowired
    private BlackJackRepo blackJackRepo;
    @Autowired
    private UserService userService;

    public ArrayList<PlayerHands> saveRepo(Lobby l, BlackJack bj){
        ArrayList<PlayerHands> hands = new ArrayList<>();
        for (User player : l.getPlayers()) {
            PlayerHands hand = new PlayerHands(player, bj);
            repo.save(hand);
            hands.add(hand);
        }
        PlayerHands hand = new PlayerHands(null, bj);
        hands.add(hand);
        repo.save(hand);
        return hands;
    }

    public Map<String, Object> createGame(int decks, Long lobbyId){
        Map<String, Object> response = new HashMap<>();
        Lobby l = (Lobby) lobbyService.getLobby(lobbyId);
        BlackJack blackJack = new BlackJack(decks, l);
        blackJack.setHands((Set) saveRepo(l, blackJack));
        response.put("status", "200 ok");
        response.put("blackjack", blackJack);
        blackJackRepo.save(blackJack);
        return response;
    }

    public Map<String, Object> deleteGame(Long lobbyId){
        Map<String, Object> response = new HashMap<>();
        Lobby l = lobbyService.getLobby(lobbyId);
        if(l == null){
            response.put("status", "404 not found");
            response.put("error", "no lobby with that id");
            return response;
        }
        BlackJack blj = blackJackRepo.findById(l.getId()).orElse(null);
        if(blj == null){
            response.put("status", "404 not found");
            response.put("error", "game not started yet");
            return response;
        }
        blackJackRepo.delete(blj);
        for(PlayerHands playerHands: blj.getHands()){
            repo.delete(playerHands);
        }
        blj.setHands((Set) saveRepo(l, blj));
        response.put("status", "200 ok");
        return response;
    }

    public Map<String, Object> hit(Long lobbyId, Long id){
        Map<String, Object> response = new HashMap<>();
        User user = userService.getUser(id);
        Lobby l = lobbyService.getLobby(lobbyId);
        if(l == null){
            response.put("status", "404 not found");
            response.put("error", "no lobby with that id");
            return response;
        }
        BlackJack blj = blackJackRepo.findById(l.getId()).orElse(null);
        if(blj == null){
            response.put("status", "404 not found");
            response.put("error", "game not started yet");
            return response;
        }
        return BlackJackLogic.hit(blj.getHand(user), blj.getCards());
    }

    public Map<String, Object> stand(Long lobbyId, Long id){
        User user = userService.getUser(id);
        Map<String, Object> response = new HashMap<>();
        Lobby l = lobbyService.getLobby(lobbyId);
        if(l == null){
            response.put("status", "404 not found");
            response.put("error", "no lobby with that id");
            return response;
        }
        BlackJack blj = blackJackRepo.findById(l.getId()).orElse(null);
        if(blj == null){
            response.put("status", "404 not found");
            response.put("error", "game not started yet");
            return response;
        }
        return BlackJackLogic.stand(blj.getHand(user));
    }

    public Map<String, Object> dou(Long lobbyId, Long id){
        User user = userService.getUser(id);
        Map<String, Object> response = new HashMap<>();
        Lobby l = lobbyService.getLobby(lobbyId);
        if(l == null){
            response.put("status", "404 not found");
            response.put("error", "no lobby with that id");
            return response;
        }
        BlackJack blj = blackJackRepo.findById(l.getId()).orElse(null);
        if(blj == null){
            response.put("status", "404 not found");
            response.put("error", "game not started yet");
            return response;
        }
        return BlackJackLogic.doubleBJ(blj.getHand(user),blj.getCards() ,user);
    }

    public Map<String, Object> split(Long lobbyId, Long id){
        User user = userService.getUser(id);
        Map<String, Object> response = new HashMap<>();
        Lobby l = lobbyService.getLobby(lobbyId);
        if(l == null){
            response.put("status", "404 not found");
            response.put("error", "no lobby with that id");
            return response;
        }
        BlackJack blj = blackJackRepo.findById(l.getId()).orElse(null);
        if(blj == null){
            response.put("status", "404 not found");
            response.put("error", "game not started yet");
            return response;
        }
        return BlackJackLogic.doubleBJ(blj.getHand(user), blj.getCards(),user);
    }

    public Map<String, Object> getHand(Long lobbyId, Long id){
        Map<String, Object> response = new HashMap<>();
        Lobby l = lobbyService.getLobby(lobbyId);
        if(l == null){
            response.put("status", "404 not found");
            response.put("error", "no lobby with that id");
            return response;
        }
        BlackJack blj = blackJackRepo.findById(l.getId()).orElse(null);
        if(blj == null){
            response.put("status", "404 not found");
            response.put("error", "game not started yet");
            return response;
        }
        User user = userService.getUser(id);
        if(blj.getHand(user) == null){
            response.put("status", "404 not found");
            response.put("error", "That user is not in this game");
            return response;
        }
        response.put("status", "200 ok");
        response.put("hand", blj.getHands());
        return response;
    }

    public Map<String, Object> start(Long lobbyId){
        Map<String, Object> response = new HashMap<>();
        Lobby l = lobbyService.getLobby(lobbyId);
        if(l == null){
            response.put("status", "404 not found");
            response.put("error", "no lobby with that id");
            return response;
        }
        BlackJack bj = blackJackRepo.findById(l.getId()).orElse(null);
        if(bj == null){
            response.put("status", "404 not found");
            response.put("error", "game not started yet");
            return response;
        }
        BlackJackLogic.start(bj.getHands(), bj);
        response.put("status", "200 ok");
        return response;
    }

}
