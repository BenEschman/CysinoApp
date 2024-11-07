package coms309.Cycino.Games.GameLogic;

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

        if(score >= 21){
            result.put("error", "cannot hit. score >= 21");
            return result;
        }
        Card c = cards.draw();
        score = checkAce(hand.getHand(), score, c);
        hand.add(c);
        result.put("card", c);
        result.put("score", hand.getScore());
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

    public static void start(Set<PlayerHands> hands, BlackJack black){
       for(int i = 0; i < 2; i ++){
           for(PlayerHands hand: hands){
               Card c = black.getCards().draw();
               c.setDeck(null);
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
               hand.add(c);
=======
               checkAce(hand.getHand(), hand.getScore(), c);
               hand.add(c);

>>>>>>> fb22897a593f79f28db15feb863178a21fc992f6
=======
               checkAce(hand.getHand(), hand.getScore(), c);
               hand.add(c);

>>>>>>> 2c2459bc3ce318e346129b7e6dd1ed0d3138f307
=======
               checkAce(hand.getHand(), hand.getScore(), c);
               hand.add(c);

>>>>>>> b1d5e45cf85089300a3c78542073b0179e96731f
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
                c.setNumber(1);
                return score + c.getValue() - 10;
            }
        }
        return score + c.getValue();
    }

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
}
