package coms309.Cycino.Games.coinflip;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CoinFlipGameState {

    private Map<String, String> playerMoves = new HashMap<>();
    private boolean win = false;

    public CoinFlipGameState(Collection<String> players) {
        for (String player : players) {
            this.playerMoves.put(player, "UNDECIDED");
        }
    }
    public void setPlayerMove(String player, String move) {
        playerMoves.put(player, move);
    }
    private boolean readyToFlip(){
        boolean response = true;
        Collection<String> moves = playerMoves.values();
        for (String move : moves) {
            System.out.println(move);
            if (move.equals("UNDECIDED")) {
                System.out.println("THIS IS HAPPENING SHOULD CHANGE RESPONSE TO FALSE");
                response = false;
            }
        }
        System.out.println(response);
        return response;
    }

    public void flip(){
        String coin = "";
        if (readyToFlip()) {
            Random random = new Random();
            int randomNumber = random.nextInt(2);
            if (randomNumber == 0) {
                coin = "HEADS";
            } else if (randomNumber == 1) {
                coin = "TAILS";
            }
            updateState(coin);
        }
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
