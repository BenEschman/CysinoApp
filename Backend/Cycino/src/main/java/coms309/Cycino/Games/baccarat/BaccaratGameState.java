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
    private BaccaratDeck baccaratDeck = new BaccaratDeck();

    //private DeckService deckService = new DeckService();
    //private Deck deck;

    public BaccaratGameState(Collection<String> players) {
        for (String player : players) {
            this.playerMoves.put(player, "UNDECIDED");
        }
    }

    public void testDraw(){
        baccaratDeck.drawPlayer();
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
            } else {
                // Draw third cards
                if (playerThirdCard()) {baccaratDeck.drawPlayer();
                    if (bankerThirdCard()){
                        baccaratDeck.drawBanker();
                    }
                }
            }
            updateState(coin);
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

    private void updateState(String coin){
        playerMoves.forEach((player, move) -> {
            if (move.equals(coin)) {
                playerMoves.put(player, "IN");
            } else{
                playerMoves.put(player, "LOST");
            }
        });
        checkWinner();
    }

    private void checkWinner(){
        int standingPlayers = 0;
        Collection<String> moves = playerMoves.values();
        for (String move : moves) {
            if (move.equals("IN")) {
                standingPlayers++;
            }
        }
        if (standingPlayers == 1) {
            playerMoves.forEach((player, move) -> {
                if (move.equals("IN")) {
                    playerMoves.put(player, "WIN");
                    this.win = true;
                }
            });
        } else {
            playerMoves.forEach((player, move) -> {
                if (move.equals("IN")) {
                    playerMoves.put(player, "UNDECIDED");
                }
            });
        }
    }

    private void resetState(){
        playerMoves.forEach((player, move) -> {
            playerMoves.put(player, "UNDECIDED");
        });
        this.win = false;
    }

    @Override
    public String toString() {
        String gameState = "";
        if (win){
            for(Map.Entry<String, String> entry : playerMoves.entrySet()) {
                if (entry.getValue().equals("WIN")) {
                    gameState += " " + entry.getKey() + " " + entry.getValue();
                }
            }
            resetState();
        }
        else{
            for(Map.Entry<String, String> entry : playerMoves.entrySet()) {
                gameState += " " + entry.getKey() + " " + entry.getValue();
            }
        }
        return gameState;
    }
}
