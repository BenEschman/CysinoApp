package coms309.Cycino.Games.GameLogic;

import coms309.Cycino.users.User;
import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
public class PlayerHands {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private User player;
    private ArrayList<Card> hand;

    @ManyToOne
    @JoinColumn(name = "blackjack_id", nullable = false)
    private BlackJack blackJack;

}
