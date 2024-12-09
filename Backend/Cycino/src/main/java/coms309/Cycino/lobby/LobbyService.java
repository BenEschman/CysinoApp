package coms309.Cycino.lobby;

import coms309.Cycino.Enums;
import coms309.Cycino.Games.Blackjack.BlackjackService;
import coms309.Cycino.users.User;
import coms309.Cycino.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LobbyService {

    @Autowired
    private LobbyRepo repo;
    @Autowired
    private UserService userService;

    public Map<String, Object> startLobby(){
        Map<String, Object> response = new HashMap<>();
        Lobby l = new Lobby();
        repo.save(l);
        response.put("lobbyId", l.getLobbyId());
        response.put("status", "200 ok");
        return response;
    }

    public Map<String, Object> startLobby(Long u){

        Map<String, Object> response = new HashMap<>();
        User user = userService.getUser(u);
        Enums.Roles role = user.getRole();
        if(role.ordinal() == 0){
            response.put("status", 405);
            response.put("error", "permission not allowed");
            return response;
        }
        Lobby l = new Lobby(user);
        repo.save(l);
        response.put("lobbyId", l.getLobbyId());
        response.put("status", "200 ok");
        return response;
    }

    public Map<String, Object> startLobby(Collection<User> users){
        Map<String, Object> response = new HashMap<>();
        Lobby l = new Lobby(users);
        repo.save(l);
        response.put("lobbyId", l.getLobbyId());
        response.put("status", "200 ok");
        return response;
    }

    public Map<String, Object> delete(Long id, long userId){
        Map<String, Object> response = new HashMap<>();
        if(repo.findById(id).isEmpty()){
            response.put("status", "404 not found");
            return response;
        }
        Enums.Roles role = userService.getUser(userId).getRole();
        if(role.ordinal() < 2){
            response.put("status", 405);
            response.put("error", "permission not allowed");
            return response;
        }
        repo.deleteById(id);
        response.put("lobbyId", id);
        response.put("status", "200 ok");
        return response;
    }

    public Map<String, Object> addPlayer(Long userId, Long id){
        Map<String, Object> response = new HashMap<>();
        User u = userService.getUser(userId);
        Lobby l = repo.findById(id).orElse(null);
        if(l == null){
            response.put("status", "404 not found");
            response.put("error", "no lobby with that id");
            return response;
        }
        l.addPlayer(u);
        response.put("lobbyId", l.getLobbyId());
        response.put("status", "200 ok");
        repo.save(l);
        return response;
    }

    public Map<String, Object> removePlayer(long userId, Long id){
        Map<String, Object> response = new HashMap<>();
        User u = userService.getUser(userId);
        Enums.Roles role = u.getRole();
        if(role.ordinal() >= 2){
            response.put("status", 405);
            response.put("error", "permission not allowed");
            return response;
        }
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
        repo.save(l);
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
        Set<User> players = l.getPlayers();
        response.put("status", "200 ok");
        //response.put("lobby", l);
        response.put("players", players);
        return response;
    }

    public Lobby getLobby(Long id){
        return repo.findById(id).orElse(null);
    }

    public void updateGameId(Long id, Lobby l){
        l.setGameId(id);
        repo.save(l);
    }

}
