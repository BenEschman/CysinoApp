package coms309.Cycino.Games.Blackjack;

import coms309.Cycino.Games.GameLogic.*;
import coms309.Cycino.lobby.Lobby;
import coms309.Cycino.lobby.LobbyService;
import coms309.Cycino.users.User;
import coms309.Cycino.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Autowired
    private DeckService ds;

    public HashSet<PlayerHands> saveRepo(Lobby l, BlackJack bj){
        HashSet<PlayerHands> hands = new HashSet<>();
        for (User player : l.getPlayers()) {
            PlayerHands hand = new PlayerHands(player, bj);
            hands.add(hand);
            repo.save(hand);
        }
        PlayerHands hand = new PlayerHands(bj);
        hands.add(hand);
        repo.save(hand);
        return hands;
    }

    public Map<String, Object> createGame(int decks, Long lobbyId){
        Map<String, Object> response = new HashMap<>();
        Lobby l = (Lobby) lobbyService.getLobby(lobbyId);
        Deck d = ds.start(decks);
        BlackJack blackJack = new BlackJack(l, d);
        blackJackRepo.save(blackJack);
        l.setGameId(blackJack.getId());
        blackJack.setHands(saveRepo(l, blackJack));
        System.out.println(blackJack.getHands());
        blackJackRepo.save(blackJack);
        System.out.println(blackJack.getHands());
        response.put("status", "200 ok");
        response.put("blackjack", blackJackRepo.findById(blackJack.getId()));
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
        for(PlayerHands hand: blj.getHands()){
            hand.setBlackJack(null);
        }
        repo.deleteAll(blj.getHands());
        ds.delete(blj.getCards());
        blj.deleteHands();
        blackJackRepo.save(blj);
        blackJackRepo.delete(blj);
        lobbyService.updateGameId(null, l);
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
        response = BlackJackLogic.hit(blj.getHand(user),blj.getCards());
        for(PlayerHands hand: blj.getHands()){
            repo.save(hand);
        }
        return response;
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
        response = BlackJackLogic.doubleBJ(blj.getHand(user),blj.getCards() ,user);
        for(PlayerHands hand: blj.getHands()){
            repo.save(hand);
        }
        return response;
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
        response =  BlackJackLogic.split(blj.getHand(user), blj,user);
        repo.save((PlayerHands) response.get("hand"));
        repo.save((PlayerHands) response.get("hand1"));
        blackJackRepo.save(blj);
        return response;
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
        PlayerHands hand = blj.getHand(user);
        int i = 0;
        for(Card c: hand.getHand()){
            response.put("card" + (i + 1), hand.getHand().get(i));
            i++;
        }
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
        for(PlayerHands hand: bj.getHands()){
            repo.save(hand);
        }
        response.put("status", "200 ok");
        return response;
    }

    public Map<String, Object> getDealer(Long lobbyId){
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
        for(PlayerHands hand: bj.getHands()){
            if(hand.getPlayer() == null) {
                int i = 0;
                for(Card c: hand.getHand()){
                    response.put("card" + (i + 1), hand.getHand().get(i));
                    i++;
                }
                response.put("score", hand.getScore());
            }
        }
        return response;
    }

    public Map<String, Object> getHands(Long lobbyId){
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
        response.put("status", "200 ok");
        System.out.println(blj.getId());
        System.out.println(blj.getHands());
        Set<PlayerHands> hands = blj.getHands();
        response.put("hands", hands);
        return response;
    }

    public Map<String, Object> finish(long lobbyId){
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
        response.put("status", "200 ok");
        PlayerHands playerHands = null;
        for(PlayerHands hand: blj.getHands()){
            if(hand.isDealer()) {
                playerHands = hand;
                break;
            }
        }
        while(playerHands.getScore() < 17){
            BlackJackLogic.hit(playerHands, blj.getCards());
            repo.save(playerHands);
        }
        response.put("dealer", playerHands.getHand());
        response.put("dscore", playerHands.getScore());
        for(PlayerHands hand: blj.getHands()){
            if(hand == playerHands)
                continue;
            if(hand.getScore() > 21 || hand.getScore() < playerHands.getScore()) response.put(hand.getPlayer().getId() + "", "lose");
            else if(hand.getScore() > playerHands.getScore())response.put(hand.getPlayer().getId() + "", "win");
            else if(hand.getScore() == playerHands.getScore()) response.put(hand.getPlayer().getId() + "", "push");
        }
        return response;
    }



}
