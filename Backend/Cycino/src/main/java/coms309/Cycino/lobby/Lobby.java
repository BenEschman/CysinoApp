package coms309.Cycino.lobby;

import coms309.Cycino.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Entity
public class Lobby {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long lobbyId;
    private ArrayList<User> players = new ArrayList<User>();

    public Lobby(){

    }
    public Lobby(User u){
        players.add(u);
    }

    public Lobby(Collection<User> users){
        players.addAll(users);
    }

    public void addPlayer(User u){
        players.add(u);
    }

    public void removePlayer(User u){
        players.remove(u);
    }

    public long getLobbyId(){
        return lobbyId;
    }

    public ArrayList<User> getPlayers(){
        return players;
    }


}
