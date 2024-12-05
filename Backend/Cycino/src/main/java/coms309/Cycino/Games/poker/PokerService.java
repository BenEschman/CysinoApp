package coms309.Cycino.Games.poker;

import coms309.Cycino.Games.Blackjack.BlackJack;
import coms309.Cycino.Games.GameLogic.*;
import coms309.Cycino.lobby.Lobby;
import coms309.Cycino.lobby.LobbyService;
import coms309.Cycino.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Service
public class PokerService {

    @Autowired
    private LobbyService lobbyService;
    @Autowired
    private DeckService deckService;
    @Autowired
    private PokerRepo repo;
    @Autowired
    private HandsRepo handsRepo;

    public Map<String, Object> createPoker(Long id){
        Map<String, Object> response = new HashMap<>();
        Lobby l = (Lobby) lobbyService.getLobby(id);
        System.out.println(l.getPlayers());
        Poker p = new Poker(l, deckService.start(1));
        repo.save(p);
        lobbyService.updateGameId(p.getId(), l);
        p.setHands(saveRepo(l, p));
        repo.save(p);
        p.setOrder();
        repo.save(p);
        response.put("id", p.getId());
        return response;
    }

    public HashSet<PlayerHands> saveRepo(Lobby l, Poker p){
        HashSet<PlayerHands> hands = new HashSet<>();
        for (User player : l.getPlayers()) {
            PlayerHands hand = new PlayerHands(player, p);
            hands.add(hand);
            handsRepo.save(hand);
        }
        PlayerHands hand = new PlayerHands(p);
        hands.add(hand);
        handsRepo.save(hand);
        return hands;
    }

    public Map<String, Object> getEval(long id){
        Map<String, Object> response = new HashMap<>();
        Lobby l = (Lobby) lobbyService.getLobby(id);
        Poker p = repo.findById(id).orElse(null);
        assert p != null;
        Map<PlayerHands, Double> evalHands = PokerLogic.finishHand(p);
        for(PlayerHands hand: evalHands.keySet()){
            response.put(hand.getPlayer().getId() + "", evalHands.get(hand));
        }
        response.put("status", "200 ok");
        return response;
    }

    public Map<String, Object> startGame(long id){
        Map<String, Object> response = new HashMap<>();
        Lobby l = (Lobby) lobbyService.getLobby(id);
        Poker p = repo.findById(l.getId()).orElse(null);
        PlayerHands dealer = null;
        for(int i = 0; i < 2; i++){
            assert p != null;
            for(PlayerHands hand: p.getHands()){
                if(hand.getPlayer() == null){
                    dealer = hand;
                    continue;
                }
                Card c = p.getCards().draw();
                hand.add(c);
            }
        }
        for(int i = 0; i < 3; i ++){
            Card c = p.getCards().draw();

            assert dealer != null;
            dealer.add(c);
        }
        for(PlayerHands hands: p.getHands()){
            handsRepo.save(hands);
        }
        response.put("status", "200 ok");
        return response;
    }

    public Map<String, Object> getHands(long id){
        Map<String, Object> response = new HashMap<>();
        Lobby l = lobbyService.getLobby(id);
        Poker p = repo.findById(l.getId()).orElse(null);
        assert p != null;
        System.out.println(p.getHands());
        for(PlayerHands hand: p.getHands()){
            if(hand.getPlayer() == null){
                response.put("null", hand.getHand());
                continue;
            }
            response.put(hand.getPlayer().getId() + "", hand.getHand());
        }
        response.put("status", "200 ok");
        return response;
    }

    public Map<String, Object> finish(long id){
        Map<String, Object> response = new HashMap<>();
        Lobby l = lobbyService.getLobby(id);
        Poker p = repo.findById(l.getId()).orElse(null);
        for(PlayerHands hand: p.getHands()){
            if(hand.getPlayer() == null)
                hand.add(p.getCards().draw());
        }
        Map<PlayerHands, Double> winners = PokerLogic.finishHand(p);
        System.out.println(winners);
        for(PlayerHands hand: winners.keySet()){
            if(hand.getPlayer() == null){
                continue;
            }
            response.put(hand.getPlayer().getId()+"", winners.get(hand));
        }
        return response;
    }
}
