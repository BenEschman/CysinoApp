package coms309.Cycino.Games.baccarat;

import java.util.ArrayList;
import java.util.Random;

public class BaccaratDeck {
    private final ArrayList<BaccaratCard> playerCards = new ArrayList<>();
    private final ArrayList<BaccaratCard> bankerCards = new ArrayList<>();
    private int playerCardNumber;
    private int bankerCardNumber;
    private final String[] cardNames = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private final String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
    private ArrayList<BaccaratCard> deck = new ArrayList<>();
    private final Random random = new Random();
    private int numberOfDecks = 1;

    public BaccaratDeck() {
        generateDeck();
    }
    private void generateDeck(){
        while(numberOfDecks > 0){
            for (int i = 0; i < cardNames.length; i++) {
                for (int j = 0; j < suits.length; j++) {
                    deck.add(new BaccaratCard(cardNames[i], suits[j], i+1));
                }
            }
            numberOfDecks--;
        }

    }
    public ArrayList<BaccaratCard> getPlayerCards() {
        return playerCards;
    }

    public ArrayList<BaccaratCard> getBankerCards() {
        return bankerCards;
    }

    public void drawPlayer(){
        int randomNumber = random.nextInt(deck.size());
        playerCards.add(deck.get(randomNumber));
        deck.remove(randomNumber);
    }

    public void drawBanker(){
        int randomNumber = random.nextInt(deck.size());
        playerCards.add(deck.get(randomNumber));
        deck.remove(randomNumber);
    }
    public void resetGame(){
        deck.clear();
        generateDeck();
        playerCards.clear();
        bankerCards.clear();
    }
}
