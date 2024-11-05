package coms309.Cycino.Games.GameLogic;

import coms309.Cycino.lobby.Lobby;
import coms309.Cycino.users.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlackJackLogic {

    public static Map<String, Object> hit(PlayerHands hand, Deck cards){
        Map<String, Object> result = new HashMap<>();
        int score = hand.getScore();
        if(score >= 21){
            result.put("error", "cannot hit. score >= 21");
            return result;
        }
        Card c = cards.draw();
        score = checkAce(hand.getHand(), score, c);
        if(checkSplit(hand.getHand(), c)){
            result.put("split", true);
        }
        hand.add(c);
        if(score > 21){
            result.put("result", "bust");
            return result;
        }

        if(score == 21){
            result.put("result", "blackjack");
            return result;
        }

        return result;
    }

//    public Map<String, Object> split(User u){
//        Map<String, Object> response = new HashMap<>();
//        Map<Long, ArrayList<Card>> temp = getHands(u.getId());
//        ArrayList<ArrayList<Card>> hands = (ArrayList<ArrayList<Card>>) temp.values();
//        int split = -1;
//        for(int i = 0; i < hands.size(); i++){
//            ArrayList<Card> hand = hands.get(i);
//            if(hand.size() == 2 && hand.get(1).getValue() == hand.get(0).getValue())
//                split = i;
//        }
//        if(split == -1){
//            response.put("error", "double not possible");
//            response.put("status", "500");
//            return response;
//        }
//
//        hand.keySet()}


    public static  Map<Long,ArrayList<Card>> getHands(Map<Long, ArrayList<Card>> hand , long id){
        Map<Long,ArrayList<Card>> hands = new HashMap<>();
        hands.put(id, hand.get(id));
        for(Long l: hand.keySet()){
            if(l / 10 == id)
                hands.put(l, hand.get(l));
        }
        return hands;
    }

    public static boolean checkSplit(ArrayList<Card> hand, Card c){
        if(hand.size() != 1)
            return false;
        if(hand.get(0).getValue() == c.getValue())
            return true;
        return false;
    }

    private static int checkAce(ArrayList<Card> hand, int score, Card c){
        if(c.getNumber().equals("ACE")){
            if(score <= 10)
                return score + 11;
            return score + 1;
        }
        if(score + c.getValue() > 21) {
            int aces = 0;
            int nas = 0;
            for (Card card : hand) {
                if (card.getNumber().equals("ACE")) {
                    aces++;
                } else
                    nas += card.getValue();
            }
            if(aces > 0 && nas + aces < score){
                return score + c.getValue() - 10;
            }
        }
        return score + c.getValue();
    }
}
