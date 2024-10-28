package coms309.Cycino.Games.GameLogic;

import coms309.Cycino.Enums;

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
}
