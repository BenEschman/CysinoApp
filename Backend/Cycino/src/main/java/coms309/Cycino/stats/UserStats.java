package coms309.Cycino.stats;

import coms309.Cycino.GameEnums;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserStats {

    @Id
    private String userStatsId;

    private long userId;
    private String game;
    private int wins;
    private int losses;
    private double percentage;
    private double net;

    public UserStats(){}

    public UserStats(long userId, GameEnums game){
        this.userId = userId;
        this.userStatsId = userId + game.toString();
        this.game = game.toString();
    }

    public void setWinsLosses(int wins, int losses){
        this.wins = wins;
        this.losses = losses;
    }

    public void updateWins(int w, int l, int chips){
        wins += w;
        losses += l;
        net += chips;
        update();
    }

    private void update(){
        this.percentage = ((double)wins)/(losses+wins);
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

    public String getUserStatsId() {
        return userStatsId;
    }

    public long getUserId(){
        return userId;
    }

    public String getGame() {
        return game;
    }
}
