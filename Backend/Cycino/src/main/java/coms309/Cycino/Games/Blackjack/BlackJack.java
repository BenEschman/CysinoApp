package coms309.Cycino.Games.Blackjack;

import coms309.Cycino.Games.GameLogic.Deck;
import coms309.Cycino.Games.GameLogic.PlayerHands;
import coms309.Cycino.lobby.Lobby;
import coms309.Cycino.users.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Set;

@Entity
public class BlackJack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    private Deck cards;

    @OneToMany(mappedBy = "id")
    private Set<PlayerHands> hands;

    @OneToOne
    private Lobby lobby;

    public BlackJack(int decks, Lobby l){
        cards = new Deck(decks);
        for (User player : l.getPlayers()) {
            PlayerHands hand = new PlayerHands(player, this);
            hands.add(hand);
        }
    }

    public Set<PlayerHands> getHands() {
        return hands;
    }

    public PlayerHands getHand(User u){
        for(PlayerHands hand: hands){
            if(hand.getPlayer().getId() == u.getId() && !hand.stand())
                return hand;
        }
        return null;
    }

    public Deck getCards(){
        return cards;
    }
}
