package coms309.Cycino.stats;

import coms309.Cycino.Games;
import coms309.Cycino.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserStats {

    @Id
    private long userId;
    private String game;
    private int wins;
    private int losses;
    private double percentage;
    private double net;

    public UserStats(){}

    public UserStats(long userId, Games game){
        this.userId = userId + game.ordinal();
    }

    public void addGame(String game){
        this.game = game;
    }

    public void setWinsLosses(int wins, int losses){
        this.wins = wins;
        this.losses = losses;
    }

    public void updateWins(int w, int l){
        wins += w;
        losses += l;
    }

    private void update(){
        this.percentage = ((double)wins)/losses;
    }
}
