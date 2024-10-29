package coms309.Cycino.lobby;

import coms309.Cycino.users.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LobbyController {

    private LobbyService service;

    @PostMapping("/lobby/create")
    public Map<String, Object> startLobby(){
        return service.startLobby();
    }

    @PostMapping("/lobby/create1")
    public Map<String, Object> startLobby(@RequestBody User u){
        return service.startLobby(u);
    }

    @PostMapping("/lobby/create2")
    public Map<String, Object> startLobby(@RequestBody Collection<User> users){
        return service.startLobby(users);
    }

    @DeleteMapping("/lobby/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id){
        return service.delete(id);
    }

    @PutMapping("/lobby/add")
    public Map<String, Object> addPlayer(@RequestBody User u, @PathVariable Long id){
        return service.addPlayer(u, id);
    }

    @PutMapping("/lobby/remove")
    public Map<String, Object> removePlayer(@RequestBody User u, @PathVariable Long id){
        return service.removePlayer(u, id);
    }

    @GetMapping("/lobby/{id}")
    public Map<String, Object> getPlayers(@PathVariable Long id){
        return service.getPlayers(id);
    }
}
