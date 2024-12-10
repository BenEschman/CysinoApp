package coms309.Cycino.Games.coinflip;

import coms309.Cycino.login.LoginService;
import coms309.Cycino.users.ChipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class CoinFlipGameState {
    @Autowired
    ChipService chipService;
    @Autowired
    LoginService loginService;

    private Map<String, String> playerMoves = new HashMap<>();
    private Map<String, Integer> playerBets = new HashMap<>();
    private String coin = "NONE";
    private boolean gameOver = false;

    public CoinFlipGameState(Collection<String> players) {
        for (String player : players) {
            this.playerMoves.put(player, "UNDECIDED");
            this.playerBets.put(player, 0);
        }
    }

    public void setPlayerBets(String player, int bet) {
        playerBets.put(player, bet);
    }

    public void setPlayerMove(String player, String move) {
        playerMoves.put(player, move);
    }

    private boolean readyToFlip(){
        boolean response = true;
        Collection<String> moves = playerMoves.values();
        for (String move : moves) {
            if (move.equals("UNDECIDED")) {
                response = false;
            }
        }
        return response;
    }

    public void flip(){
        if (readyToFlip()) {
            Random random = new Random();
            int randomNumber = random.nextInt(2);
            if (randomNumber == 0) {
                coin = "HEADS";
            } else if (randomNumber == 1) {
                coin = "TAILS";
            }
            updateState();
        }
    }

    private void updateState(){
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
            gameOver = true;
            playerMoves.forEach((player, move) -> {
                if (move.equals("IN")) {
                    playerMoves.put(player, "WIN");
                }
            });
        } else if (standingPlayers == 0){
            gameOver = true;
            /*
            playerMoves.forEach((player, move) -> {
                playerMoves.put(player, "UNDECIDED");
            });
            */
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
        this.coin = "NONE";
        this.gameOver = false;
    }

    @Override
    public String toString() {
        String gameState = "";
        gameState += "\n" + "COIN: " + coin;
        gameState += "\n";
        gameState += "CALLS: ";
        for(Map.Entry<String, String> entry : playerMoves.entrySet()) {
            gameState += entry.getKey() + " " + entry.getValue() + " ";
        }
        gameState += "\n";
        gameState += "BETS: ";
        for(Map.Entry<String, Integer> entry : playerBets.entrySet()) {
            gameState += entry.getKey() + " " + entry.getValue() + " ";
        }
        gameState += "\n" + "GAME: ";
        if (gameOver){
            gameState +=  "OVER";
            /*
            for(Map.Entry<String, String> entry : playerMoves.entrySet()) {
                if (entry.getValue().equals("WIN")) {
                    gameState += " " + entry.getKey() + " " + entry.getValue();
                }
            }
            */
            resetState();
        }
        else{
            gameState += "ONGOING";
            /*
            for(Map.Entry<String, String> entry : playerMoves.entrySet()) {
                gameState += " " + entry.getKey() + " " + entry.getValue();
            }
            */
        }
        return gameState;
    }
}
