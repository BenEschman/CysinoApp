package coms309.Cycino.Games.GameLogic;

import coms309.Cycino.Games.poker.Evaluator;
import coms309.Cycino.Games.poker.Poker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PokerLogic {

    public static Map<PlayerHands, Double> finishHand(Poker poker){
        Map<String, Object> response = new HashMap<>();
        PlayerHands table;
        for(PlayerHands hand: poker.getHands()){
            if(hand.getPlayer() == null) {
                table = null;
            }
        }
        Map<PlayerHands, Double> temp = new HashMap<>();
        Map<PlayerHands, Double> winners = new HashMap<>();
        for(PlayerHands hand: poker.getHands()){
            double score = Evaluator.evaluate(hand);
            temp.put(hand, score);
        }
        double high = 0;
        for(double d: temp.values()){
            if(d > high)
                high = d;
        }
        for(PlayerHands hand: temp.keySet()){
            if(temp.get(hand) == high){
                winners.put(hand, high);
            }
        }
        return winners;
    }

    //private int getHand

}
