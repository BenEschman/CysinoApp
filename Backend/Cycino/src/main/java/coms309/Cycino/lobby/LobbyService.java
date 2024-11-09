package coms309.Cycino.lobby;

import coms309.Cycino.users.User;
<<<<<<< HEAD
import coms309.Cycino.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
=======
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
>>>>>>> 33-blackjack-game-view

@Service
public class LobbyService {

<<<<<<< HEAD
    @Autowired
    private LobbyRepo repo;
    @Autowired
    private UserService userService;
=======
    private LobbyRepo repo;
>>>>>>> 33-blackjack-game-view

    public Map<String, Object> startLobby(){
        Map<String, Object> response = new HashMap<>();
        Lobby l = new Lobby();
<<<<<<< HEAD
        repo.save(l);
        response.put("lobbyId", l.getLobbyId());
        response.put("status", "200 ok");
        return response;
    }

    public Map<String, Object> startLobby(Long u){
        Map<String, Object> response = new HashMap<>();
        User user = userService.getUser(u);
        Lobby l = new Lobby(user);
        repo.save(l);
        response.put("lobbyId", l.getLobbyId());
        response.put("status", "200 ok");
=======
        response.put("lobbyId", l.getLobbyId());
        response.put("status", "200 ok");
        repo.save(l);
        return response;
    }

    public Map<String, Object> startLobby(User u){
        Map<String, Object> response = new HashMap<>();
        Lobby l = new Lobby(u);
        response.put("lobbyId", l.getLobbyId());
        response.put("status", "200 ok");
        repo.save(l);
>>>>>>> 33-blackjack-game-view
        return response;
    }

    public Map<String, Object> startLobby(Collection<User> users){
        Map<String, Object> response = new HashMap<>();
        Lobby l = new Lobby(users);
<<<<<<< HEAD
        repo.save(l);
        response.put("lobbyId", l.getLobbyId());
        response.put("status", "200 ok");
=======
        response.put("lobbyId", l.getLobbyId());
        response.put("status", "200 ok");
        repo.save(l);
>>>>>>> 33-blackjack-game-view
        return response;
    }

    public Map<String, Object> delete(Long id){
        Map<String, Object> response = new HashMap<>();
        if(repo.findById(id).isEmpty()){
            response.put("status", "404 not found");
            return response;
        }
        repo.deleteById(id);
        response.put("lobbyId", id);
        response.put("status", "200 ok");
        return response;
    }

<<<<<<< HEAD
    public Map<String, Object> addPlayer(Long userId, Long id){
        Map<String, Object> response = new HashMap<>();
        User u = userService.getUser(userId);
=======
    public Map<String, Object> addPlayer(User u, Long id){
        Map<String, Object> response = new HashMap<>();
>>>>>>> 33-blackjack-game-view
        Lobby l = repo.findById(id).orElse(null);
        if(l == null){
            response.put("status", "404 not found");
            response.put("error", "no lobby with that id");
            return response;
        }
        l.addPlayer(u);
        response.put("lobbyId", l.getLobbyId());
        response.put("status", "200 ok");
<<<<<<< HEAD
        repo.save(l);
        return response;
    }

    public Map<String, Object> removePlayer(long userId, Long id){
        Map<String, Object> response = new HashMap<>();
        User u = userService.getUser(userId);
=======
        return response;
    }

    public Map<String, Object> removePlayer(User u, Long id){
        Map<String, Object> response = new HashMap<>();
>>>>>>> 33-blackjack-game-view
        Lobby l = repo.findById(id).orElse(null);
        if(l == null){
            response.put("status", "404 not found");
            response.put("error", "no lobby with that id");
            return response;
        }
        if(!l.getPlayers().contains(u)){
            response.put("status", "404 not found");
            response.put("error", "user not in this lobby");
            return response;
        }
        l.removePlayer(u);
        response.put("lobbyId", l.getLobbyId());
        response.put("status", "200 ok");
<<<<<<< HEAD
        repo.save(l);
=======
>>>>>>> 33-blackjack-game-view
        return response;
    }

    public Map<String, Object> getPlayers(Long id){
        Map<String, Object> response = new HashMap<>();
        Lobby l = repo.findById(id).orElse(null);
        if(l == null){
            response.put("status", "404 not found");
            response.put("error", "no lobby with that id");
            return response;
        }
<<<<<<< HEAD
        Set<User> players = l.getPlayers();
        response.put("status", "200 ok");
        //response.put("lobby", l);
=======
        ArrayList<User> players = l.getPlayers();
        response.put("status", "200 ok");
>>>>>>> 33-blackjack-game-view
        response.put("players", players);
        return response;
    }

<<<<<<< HEAD
    public Lobby getLobby(Long id){
        return repo.findById(id).orElse(null);
    }

    public void updateGameId(Long id, Lobby l){
        l.setGameId(id);
        repo.save(l);
    }

=======
>>>>>>> 33-blackjack-game-view
}
