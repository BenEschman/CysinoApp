package coms309.Cycino.Games.Blackjack;

import coms309.Cycino.Games.GameLogic.Deck;
import coms309.Cycino.Games.GameLogic.HandsRepo;
import coms309.Cycino.Games.GameLogic.PlayerHands;
import coms309.Cycino.lobby.Lobby;
import coms309.Cycino.users.User;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
public class BlackJack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Deck cards;

    @OneToMany(mappedBy = "blackJack", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<PlayerHands> hands = new HashSet<>();

    @OneToOne
    private Lobby lobby;

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    public BlackJack(){
=======
    private Long gameHist;
>>>>>>> 47a8e53f595138108cedba48c49442e76e6cd5ae
=======
    private Long gameHist;
>>>>>>> 3c1ad4b9af669384c9c557ef14089f9628fb63e4
=======
   // private Long gameHist;

    public BlackJack(){
>>>>>>> fb22897a593f79f28db15feb863178a21fc992f6

    public BlackJack(){

    }

    public BlackJack(Lobby l, Deck d, Long gameHist){
        cards = d;
        lobby = l;
        this.gameHist = gameHist;

    }

    public Long getGameHist(){
        return gameHist;
<<<<<<< HEAD
    }

    public BlackJack(Lobby l, Deck d){
        cards = d;
        lobby = l;
=======
    private Long gameHist;
>>>>>>> 47a8e53f595138108cedba48c49442e76e6cd5ae

    public BlackJack(){

    }

    public BlackJack(Lobby l, Deck d, Long gameHist){
        cards = d;
        lobby = l;
        this.gameHist = gameHist;

    }

    public Long getGameHist(){
        return gameHist;
=======
>>>>>>> 3c1ad4b9af669384c9c557ef14089f9628fb63e4
    }

    public BlackJack(Lobby l, Deck d){
        cards = d;
        lobby = l;
        //this.gameHist = gameHist;

    }

//    public Long getGameHist(){
//        return gameHist;
//    }

    public Set<PlayerHands> getHands() {
        return hands;
    }

    public PlayerHands getHand(User u){
        System.out.println(hands);
        for(PlayerHands hand: hands){
            if(hand.getPlayer() == null)
                continue;
            if(hand.getPlayer().getId() == u.getId() && !hand.stand())
                return hand;
        }
        return null;
    }

    public Deck getCards(){
        return cards;
    }

    public void addHand(PlayerHands hand){
        hands.add(hand);
    }

    public void setHands(Set<PlayerHands> hands){
        this.hands.addAll(hands);
    }

    public long getId(){
        return id;
    }

    public void deleteHands(){
        hands.clear();
        cards = null;
    }
}
