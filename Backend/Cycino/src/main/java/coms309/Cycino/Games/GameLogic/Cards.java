package coms309.Cycino.Games.GameLogic;

import java.util.ArrayList;
import java.util.Collections;

public class Cards {

    private ArrayList<String> cards = new ArrayList<>();

    public enum SUIT{
        HEARTS,
        DIAMONDS,
        SPADES,
        CLUBS
    }

    public enum VALUE{
        JACK,
        QUEEN,
        KING,
        ACE

    }

    public Cards(){
        resetDeck();
    }

    public ArrayList<String> getCards(){
        return cards;
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public void resetDeck(){
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            for(int j = 2; j < 11; j++){
                temp.add(j + " of " + SUIT.values()[i].toString());
            }
            for(int j = 0; i < 4; i++){
                temp.add(VALUE.values()[j].toString() + " of " + SUIT.values()[i].toString());
            }
        }
        cards = temp;
    }

}
