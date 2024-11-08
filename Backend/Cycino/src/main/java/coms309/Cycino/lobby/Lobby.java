package coms309.Cycino.lobby;

import coms309.Cycino.users.User;
<<<<<<< HEAD
import jakarta.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
=======
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
>>>>>>> 33-blackjack-game-view

@Entity
public class Lobby {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long lobbyId;
<<<<<<< HEAD

    @OneToMany
    private Set<User> players = new HashSet<>();

    private Long gameId;
=======
    private ArrayList<User> players = new ArrayList<User>();
>>>>>>> 33-blackjack-game-view

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

<<<<<<< HEAD
    public Set<User> getPlayers(){
        return players;
    }

    public void setGameId(Long id){
        gameId = id;
    }

    public long getId(){
        return gameId;
    }

=======
    public ArrayList<User> getPlayers(){
        return players;
    }

>>>>>>> 33-blackjack-game-view

}
