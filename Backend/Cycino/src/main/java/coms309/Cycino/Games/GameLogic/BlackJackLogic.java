package coms309.Cycino.Games.GameLogic;

import coms309.Cycino.lobby.Lobby;
import coms309.Cycino.users.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlackJackLogic {

    private Deck cards;

    private Map<Long, Integer> players = new HashMap<>();
    private Map<Long, ArrayList<Card>> hand = new HashMap<>();

    public BlackJackLogic(Lobby l){
        cards = new Deck(6);
        for(User u: l.getPlayers()){
            players.put(u.getId(), 0);
        }
    }

    public BlackJackLogic(int decks, Lobby l){
        cards = new Deck(decks);
        for(User u: l.getPlayers()){
            players.put(u.getId(), 0);
        }
    }

    public Map<String, Object> ht(User u){
        Map<String, Object> result = new HashMap<>();
        int score = players.get(u.getId());
        if(score >= 21){
            result.put("error", "cannot hit. score >= 21");
            return result;
        }
        Card c = cards.draw();
        score = checkAce(hand.get(u.getId()), score, c);
        if(checkSplit(hand.get(u.getId()), c)){
            result.put("split", true);
        }
        hand.get(u.getId()).add(c);
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


    public Map<Long,ArrayList<Card>> getHands(long id){
        Map<Long,ArrayList<Card>> hands = new HashMap<>();
        hands.put(id, hand.get(id));
        for(Long l: hand.keySet()){
            if(l / 10 == id)
                hands.put(l, hand.get(l));
        }
        return hands;
    }

    public boolean checkSplit(ArrayList<Card> hand, Card c){
        if(hand.size() != 1)
            return false;
        if(hand.get(0).getValue() == c.getValue())
            return true;
        return false;
    }

    private int checkAce(ArrayList<Card> hand, int score, Card c){
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
