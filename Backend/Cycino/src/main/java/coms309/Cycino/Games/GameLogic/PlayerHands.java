package coms309.Cycino.Games.GameLogic;

import coms309.Cycino.Games.Blackjack.BlackJack;
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
    private int score;
    private boolean stand = false;

    @ManyToOne
    @JoinColumn(name = "blackjack_id", nullable = false)
    private BlackJack blackJack;


    public PlayerHands(User player, BlackJack blackJack){
        this.player = player;
        this.blackJack = blackJack;
        hand = new ArrayList<>();
    }

    public int getScore(){
        score = 0;
        for(Card c: hand){
            score += c.getValue();
        }
        return score;
    }

    public ArrayList<Card> getHand(){
        return hand;
    }

    public void add(Card c){
        hand.add(c);
    }

    public User getPlayer(){
        return player;
    }

    public void flip(){
        stand = true;
    }

    public boolean stand(){
        return stand;
    }

}
