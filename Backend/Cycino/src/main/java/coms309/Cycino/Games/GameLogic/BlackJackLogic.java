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

    public Map<String, Object> hit(User u){
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