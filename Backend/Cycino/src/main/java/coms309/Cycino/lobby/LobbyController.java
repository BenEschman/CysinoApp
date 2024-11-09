package coms309.Cycino.lobby;

import coms309.Cycino.users.User;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
=======
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
>>>>>>> 33-blackjack-game-view
import java.util.Map;

@RestController
public class LobbyController {

<<<<<<< HEAD
    @Autowired
=======
>>>>>>> 33-blackjack-game-view
    private LobbyService service;

    @PostMapping("/lobby/create")
    public Map<String, Object> startLobby(){
        return service.startLobby();
    }

<<<<<<< HEAD
    @PostMapping("/lobby/create1/{userId}")
    public Map<String, Object> startLobby(@PathVariable Long userId){
        return service.startLobby(userId);
=======
    @PostMapping("/lobby/create1")
    public Map<String, Object> startLobby(@RequestBody User u){
        return service.startLobby(u);
    }

    @PostMapping("/lobby/create2")
    public Map<String, Object> startLobby(@RequestBody Collection<User> users){
        return service.startLobby(users);
>>>>>>> 33-blackjack-game-view
    }

    @DeleteMapping("/lobby/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id){
        return service.delete(id);
    }

<<<<<<< HEAD
    @PutMapping("/lobby/add/{id}/{userId}")
    public Map<String, Object> addPlayer(@PathVariable Long userId, @PathVariable Long id){
        return service.addPlayer(userId, id);
    }

    @PutMapping("/lobby/remove/{id}/{userId}")
    public Map<String, Object> removePlayer(@PathVariable Long userId, @PathVariable Long id){
        return service.removePlayer(userId, id);
=======
    @PutMapping("/lobby/add")
    public Map<String, Object> addPlayer(@RequestBody User u, @PathVariable Long id){
        return service.addPlayer(u, id);
    }

    @PutMapping("/lobby/remove")
    public Map<String, Object> removePlayer(@RequestBody User u, @PathVariable Long id){
        return service.removePlayer(u, id);
>>>>>>> 33-blackjack-game-view
    }

    @GetMapping("/lobby/{id}")
    public Map<String, Object> getPlayers(@PathVariable Long id){
        return service.getPlayers(id);
    }
}
