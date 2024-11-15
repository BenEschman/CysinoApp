package coms309.Cycino.Games.poker;

import coms309.Cycino.Games.Game;
import coms309.Cycino.Games.GameLogic.Deck;
import coms309.Cycino.Games.GameLogic.PlayerHands;
import coms309.Cycino.lobby.Lobby;
import coms309.Cycino.users.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Poker extends Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Deck cards;

    @OneToMany(mappedBy = "blackJack", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<PlayerHands> hands = new HashSet<>();

    @Column(name = "`order`")
    private ArrayList<Long> order = new ArrayList<>();

    @OneToOne
    private Lobby lobby;

    public Poker(){
    }

    public Poker(Lobby l, Deck d){
        super(l,d);
    }

}
