package coms309.Cycino.Games.GameLogic;

import coms309.Cycino.lobby.Lobby;
import coms309.Cycino.users.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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


    public BlackJack(Lobby l){
        cards = new Deck(6);
    }

    public BlackJack(int decks, Lobby l){
        cards = new Deck(decks);
    }

}
