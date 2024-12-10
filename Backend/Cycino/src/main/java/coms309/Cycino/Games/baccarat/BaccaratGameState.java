package coms309.Cycino.Games.baccarat;

import coms309.Cycino.Games.GameLogic.Card;
import coms309.Cycino.Games.GameLogic.Deck;
import coms309.Cycino.Games.GameLogic.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.*;

public class BaccaratGameState {

    private Map<String, String> playerMoves = new HashMap<>();
    private boolean win = false;
    private boolean tie = false;
    private BaccaratDeck baccaratDeck = new BaccaratDeck();
    private String gameResult = "NONE";

    //private DeckService deckService = new DeckService();
    //private Deck deck;

    public BaccaratGameState(Collection<String> players) {
        for (String player : players) {
            this.playerMoves.put(player, "UNDECIDED");
        }
    }

    public void setPlayerMove(String player, String move) {
        playerMoves.put(player, move);
    }

    private boolean readyToDeal(){
        boolean response = true;
        Collection<String> moves = playerMoves.values();
        for (String move : moves) {
            if (move.equals("UNDECIDED")) {
                response = false;
            }
        }
        return response;
    }

    public void dealCards(){
        if (readyToDeal()) {
            baccaratDeck.resetGame();
            baccaratDeck.drawPlayer();
            baccaratDeck.drawBanker();
            baccaratDeck.drawPlayer();
            baccaratDeck.drawBanker();
            if (checkNatural()){
                // updateState / check for winner
                // updateState(coin);
                updateState();
            } else {
                // Draw third cards
                if (playerThirdCard()) {
                    baccaratDeck.drawPlayer();
                    if (bankerThirdCard()){
                        baccaratDeck.drawBanker();
                    }
                }
                updateState();
            }
        }
    }

    private boolean checkNatural(){
        boolean response = false;
        int playerHandValue = handValue(baccaratDeck.getPlayerCards());
        int bankerHandValue = handValue(baccaratDeck.getBankerCards());
        if (playerHandValue == 8 || playerHandValue == 9 || bankerHandValue == 8 || bankerHandValue == 9) {
            response = true;
        }
        return response;
    }

    private int handValue(ArrayList<BaccaratCard> hand) {
        int value = 0;
        for (int i = 0; i < hand.size(); i++) {
            value += hand.get(i).getValue();
        }
        while (value >= 10){
            value -= 10;
        }
        return value;
    }

    private boolean playerThirdCard(){
        boolean response = false;
        int playerHandValue = handValue(baccaratDeck.getPlayerCards());
        if (0 <= playerHandValue && playerHandValue < 6) {
            response = true;
        }
        return response;
    }

    private boolean bankerThirdCard(){
        boolean response = false;
        int playerThirdCard = baccaratDeck.getPlayerCards().get(2).getValue();
        int bankerHandValue = handValue(baccaratDeck.getBankerCards());
        if (0 <= bankerHandValue && bankerHandValue <= 2) {
            response = true;
        } else if (bankerHandValue == 3 && playerThirdCard != 8) {
            response = true;
        } else if (bankerHandValue == 4 && 2 <= playerThirdCard && playerThirdCard <= 7) {
            response = true;
        } else if (bankerHandValue == 5 && 4 <= playerThirdCard && playerThirdCard <= 7) {
            response = true;
        } else if (bankerHandValue == 6 && 6 <= playerThirdCard && playerThirdCard <= 7) {
            response = true;
        }
        return response;
    }

    private void updateState(){
        int playerScore = handValue(baccaratDeck.getPlayerCards());
        int bankerScore = handValue(baccaratDeck.getBankerCards());

        if (playerScore > bankerScore) {
            this.gameResult = "PLAYER";
            this.win = true;
            playerMoves.forEach((player, move) -> {
                if (move.equals("PLAYER")) {
                    playerMoves.put(player, "WIN");
                } else{
                    playerMoves.put(player, "LOST");
                }
            });
        } else if (playerScore < bankerScore) {
            this.gameResult = "BANKER";
            this.win = true;
            playerMoves.forEach((player, move) -> {
                if (move.equals("BANKER")) {
                    playerMoves.put(player, "WIN");
                } else{
                    playerMoves.put(player, "LOST");
                }
            });
        } else if (playerScore == bankerScore) {
            this.gameResult = "TIE";
            this.tie = true;
            playerMoves.forEach((player, move) -> {
                if (move.equals("TIE")) {
                    playerMoves.put(player, "WIN");
                } else{
                    playerMoves.put(player, "LOST");
                }
            });
        }
    }


    private void resetState(){
        playerMoves.forEach((player, move) -> {
            playerMoves.put(player, "UNDECIDED");
        });
        this.win = false;
        this.tie = false;
        this.gameResult = "NONE";
    }

    private String dealtCardsToString(){
        String playerCards = "";
        String bankerCards = "";
        for (int i=0; i < baccaratDeck.getPlayerCards().size(); i++) {
            playerCards += baccaratDeck.getPlayerCards().get(i).toString();
            playerCards += " ";
        }
        for (int i=0; i < baccaratDeck.getBankerCards().size(); i++) {
            bankerCards += baccaratDeck.getBankerCards().get(i).toString();
            bankerCards += " ";
        }
        return "\n" + "PLAYER " + baccaratDeck.getPlayerCards().size() + " " + playerCards +
                "\n" + "BANKER " + baccaratDeck.getBankerCards().size() + " " + bankerCards;
    }

    @Override
    public String toString() {
        String gameState = "";
        gameState += "\n" + "CALLS: ";
        for(Map.Entry<String, String> entry : playerMoves.entrySet()) {
            gameState += entry.getKey() + " " + entry.getValue() + " ";
        }
        gameState += "\n" + "GAMESTATE:" + " ";
        if (win || tie){
            gameState += "OVER";
        } else {
            gameState += "ONGOING";
        }
        gameState += "\n" + "GAMERESULT:" + " " + gameResult;
        if (win || tie){
            gameState += dealtCardsToString();
            resetState();
        } else{
            gameState += "\n" + "PLAYER " + "NONE";
            gameState += "\n" + "BANKER " + "NONE";
        }
        return gameState;
    }
}
