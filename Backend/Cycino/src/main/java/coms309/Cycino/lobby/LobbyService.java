package coms309.Cycino.lobby;

import coms309.Cycino.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class LobbyService {

    private LobbyRepo repo;

    public Map<String, Object> startLobby(){
        Map<String, Object> response = new HashMap<>();
        Lobby l = new Lobby();
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
        return response;
    }

    public Map<String, Object> startLobby(Collection<User> users){
        Map<String, Object> response = new HashMap<>();
        Lobby l = new Lobby(users);
        response.put("lobbyId", l.getLobbyId());
        response.put("status", "200 ok");
        repo.save(l);
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

    public Map<String, Object> addPlayer(User u, Long id){
        Map<String, Object> response = new HashMap<>();
        Lobby l = repo.findById(id).orElse(null);
        if(l == null){
            response.put("status", "404 not found");
            response.put("error", "no lobby with that id");
            return response;
        }
        l.addPlayer(u);
        response.put("lobbyId", l.getLobbyId());
        response.put("status", "200 ok");
        return response;
    }

    public Map<String, Object> removePlayer(User u, Long id){
        Map<String, Object> response = new HashMap<>();
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
        ArrayList<User> players = l.getPlayers();
        response.put("status", "200 ok");
        response.put("players", players);
        return response;
    }

}
