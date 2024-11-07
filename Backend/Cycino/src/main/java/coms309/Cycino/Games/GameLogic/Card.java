package coms309.Cycino.Games.GameLogic;

import coms309.Cycino.Enums;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String value = null;
    private Enums.SUIT suit = null;
    private int number;

    @ManyToOne
    private Deck deck;

    @ManyToOne
    private PlayerHands hand = null;


    public Card(){

    }
    public Card(String number, Enums.SUIT suit, Deck deck){
        this.deck = deck;
        value = number;
        this.suit = suit;
        try{
            this.number = Integer.parseInt(number);
        } catch(Exception e){
            this.number = 10;
        }
        if(number.equals(Enums.VALUE.ACE.toString()))
            this.number = 11;
    }

    @Override
    public String toString() {
        return value + " of " + suit.toString();
    }

    public int getValue() {
        return number;
    }

    public Enums.SUIT getSuit(){
        return suit;
    }

    public String getNumber(){
        return value;
    }

    public String img(){
        return value.toLowerCase() + "_of_" + suit.toString().toLowerCase() +".png";
    }

    public void setDeck(Deck d){
        this.deck = d;
    }

    public void setPlayerHand(PlayerHands hand){
        this.hand = hand;
    }

    public void setNumber(int i){
        number = i;
    }

}
