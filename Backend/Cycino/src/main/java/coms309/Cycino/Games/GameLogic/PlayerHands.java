package coms309.Cycino.Games.GameLogic;

import coms309.Cycino.Games.Blackjack.BlackJack;
import coms309.Cycino.users.User;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
public class PlayerHands implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "hand_id", nullable = true)
    private User player;
    @OneToMany
    private Set<Card> hand;
    private int score;
    private boolean stand = false;
    private double bet;
    private boolean split = false;
    private boolean dealer = false;

    @ManyToOne(optional = true) // Allow null
    @JoinColumn(name = "blackjack_id", nullable = true) // Allow null in the database
    private BlackJack blackJack;



    public PlayerHands(){

    }
    public PlayerHands(User player, BlackJack blackJack){
        this.player = player;
        this.blackJack = blackJack;
        hand = new HashSet<>();
    }

    public PlayerHands(BlackJack b){
        this.blackJack = b;
        hand = new HashSet<>();
        dealer = true;
    }

    public void setBlackJack(BlackJack bj){
        blackJack = bj;
    }

    public int getScore(){
        score = 0;
        for(Card c: hand){
            score += c.getValue();
        }
        return score;
    }

    public ArrayList<Card> getHand(){
        return new ArrayList<>(hand);
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

    public void addBet(double bet){
        this.bet = bet;
    }

    public double getBet(){
        return bet;
    }

    public void split(boolean split){
        this.split = split;
    }

    public boolean getSplit(){
        return split;
    }

    public Card splitHand(){
        ArrayList<Card> hand = new ArrayList<>(this.hand);
        Card c = hand.remove(1);
        this.hand.remove(c);
        return c;
    }

    public boolean isDealer(){
        return dealer;
    }
}
