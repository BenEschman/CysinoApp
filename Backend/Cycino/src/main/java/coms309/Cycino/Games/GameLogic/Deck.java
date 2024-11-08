package coms309.Cycino.Games.GameLogic;

import coms309.Cycino.Enums;
<<<<<<< HEAD
import coms309.Cycino.Games.Blackjack.BlackJack;
import jakarta.persistence.*;

import java.util.*;

@Entity
public class Deck {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "deck", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Card> cards = new HashSet<>();

    @OneToOne
    private BlackJack blackJack;

    public Deck(){

    }

    public Set<Card> getCards(){
        return cards;
    }

    public ArrayList<Card> shuffle(){
        System.out.println(cards);
        ArrayList<Card> cardList = new ArrayList<>(cards);
        Collections.shuffle(cardList);
        return cardList;
    }

    public void resetDeck(){
        cards.removeAll(cards);
    }

    public void addCard(Card c){
        cards.add(c);
    }

    public Card draw(){
        ArrayList<Card> cards = shuffle();
        Card c = (Card) cards.remove(0);
        Collections.shuffle(cards);
        this.cards.remove(c);
        return c;
    }

    public void setBlackJack(BlackJack blackJack){
        this.blackJack = blackJack;
    }

=======

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> cards = new ArrayList<>();

    public Deck(int decks){
        for(int i = 0; i < decks; i++){
            cards.addAll(fill());
        }
        shuffle();
    }

    public Deck(){
        resetDeck();
    }

    public ArrayList<Card> getCards(){
        return cards;
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public void resetDeck(){
        cards = fill();
    }

    private ArrayList<Card> fill(){
        ArrayList<Card> list = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            for(int j = 2; j < 11; j++){
                list.add(new Card(j + "", Enums.SUIT.values()[i]));
            }
            for(int j = 0; i < 4; i++){
                list.add(new Card(Enums.VALUE.values()[j].toString(), Enums.SUIT.values()[i]));
            }
        }
        return list;
    }

    public Card draw(){
        return cards.remove(0);
    }
>>>>>>> 33-blackjack-game-view
}
