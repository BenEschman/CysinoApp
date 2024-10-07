package coms309.Cycino.stats;

import coms309.Cycino.Games;
import coms309.Cycino.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserStats {

    @Id
    private String userId;
    private String game;
    private int wins;
    private int losses;
    private double percentage;
    private double net;

    public UserStats(){}

    public UserStats(long userId, Games game){
        this.userId = userId + game.toString();
        this.game = game.toString();
    }

    public void setWinsLosses(int wins, int losses){
        this.wins = wins;
        this.losses = losses;
    }

    public void updateWins(int w, int l){
        wins += w;
        losses += l;
        update();
    }

    private void update(){
        this.percentage = ((double)wins)/losses;
    }

    public int getWins(){
        return wins;
    }
    public int getLosses(){
        return losses;
    }

    public double getNet() {
        return net;
    }

    public double getPercentage() {
        return percentage;
    }

    public String getUserId() {
        return userId;
    }

    public String getGame() {
        return game;
    }
}
