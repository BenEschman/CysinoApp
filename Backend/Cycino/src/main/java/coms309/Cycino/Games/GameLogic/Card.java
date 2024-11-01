package coms309.Cycino.Games.GameLogic;

import coms309.Cycino.Enums;

public class Card {

    private final String value;
    private final Enums.SUIT suit;
    private int number;


    public Card(String number, Enums.SUIT suit){
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
}
