package coms309.Cycino.Games.GameLogic;

<<<<<<< HEAD
import coms309.Cycino.Games.Blackjack.BlackJack;
import coms309.Cycino.lobby.Lobby;
import coms309.Cycino.users.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BlackJackLogic {

    public static Map<String, Object> hit(PlayerHands hand, Deck cards){
        Map<String, Object> result = new HashMap<>();
        int score = hand.getScore();
        hand.split(false);

=======
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
>>>>>>> 33-blackjack-game-view
        if(score >= 21){
            result.put("error", "cannot hit. score >= 21");
            return result;
        }
        Card c = cards.draw();
<<<<<<< HEAD
        score = checkAce(hand.getHand(), score, c);
        hand.add(c);
        result.put("card", c);
        result.put("score", hand.getScore());
=======
        score = checkAce(hand.get(u.getId()), score, c);
        if(checkSplit(hand.get(u.getId()), c)){
            result.put("split", true);
        }
        hand.get(u.getId()).add(c);
>>>>>>> 33-blackjack-game-view
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

<<<<<<< HEAD
    public static void start(Set<PlayerHands> hands, BlackJack black){
       for(int i = 0; i < 2; i ++){
           for(PlayerHands hand: hands){
               Card c = black.getCards().draw();
               c.setDeck(null);
               checkAce(hand.getHand(), hand.getScore(), c);
               hand.add(c);

                //c.setPlayerHand(hand);
           }
       }
        for(PlayerHands hand: hands){
            checkSplit(hand);
        }

    }


    public static void checkSplit(PlayerHands hand){
        if(hand.getHand().size() == 2 && hand.getHand().get(0).getValue() == hand.getHand().get(1).getValue()){
            hand.split(true);
        }
    }

    private static int checkAce(ArrayList<Card> hand, int score, Card c){
        if(c.getNumber().equals("ACE")){
            if(score <= 10)
                return score + 11;
            c.setNumber(1);
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
>>>>>>> 33-blackjack-game-view
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
<<<<<<< HEAD
                c.setNumber(1);
=======
>>>>>>> 33-blackjack-game-view
                return score + c.getValue() - 10;
            }
        }
        return score + c.getValue();
    }
<<<<<<< HEAD

    public static Map<String, Object> stand(PlayerHands hand){
        Map<String, Object> response = new HashMap<>();
        hand.stand();
        response.put("status", "200 ok");
        response.put("hand", hand);
        response.put("score", hand.getScore());
        if(hand.getScore() > 21)
            response.put("bust", true);
        else response.put("bust", false);
        return response;
    }

    public static Map<String, Object> doubleBJ(PlayerHands hand, Deck deck, User user){
        Map<String, Object> response = new HashMap<>();
        if(hand.getBet() > user.getChips()){
            response.put("status", "500");
            response.put("error", "not enough chips");
            return response;
        }
        user.addChips(-(hand.getBet()));
        hand.addBet(hand.getBet());
        Card c = deck.draw();
        hand.add(c);
        response = check(hand);
        response.put("card", c);
        hand.stand();
        return response;
    }

    public static Map<String, Object> split(PlayerHands hand, BlackJack blackJack, User user){
        Map<String, Object> response = new HashMap<>();
        if(!hand.getSplit()){
            response.put("status", "500");
            response.put("error", "cannot split");
            return response;
        }
        if(hand.getBet() > user.getChips()){
            response.put("status", "500");
            response.put("error", "not enough chips");
            return response;
        }
        PlayerHands temp = new PlayerHands(hand.getPlayer(), blackJack);
        temp.add(hand.splitHand());
        blackJack.addHand(temp);
        hand.split(false);
        response.put("hand", hand);
        response.put("hand1", temp);
        response.put("status", "200 ok");
        return response;
    }

    private static Map<String, Object> check(PlayerHands hand){
        Map<String, Object> response = new HashMap<>();
        response.put("status", "200 ok");
        if(hand.getScore() > 21){
            response.put("bust", true);
        } else response.put("bust", false);
        response.put("score", hand.getScore());
        return response;
    }
=======
>>>>>>> 33-blackjack-game-view
}
