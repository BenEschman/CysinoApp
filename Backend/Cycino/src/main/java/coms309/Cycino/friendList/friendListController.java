package coms309.Cycino.friendList;
import coms309.Cycino.users.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/friendList")
@RestController
public class friendListController {
    @Autowired
    private friendListRepository friendListRepository;

    @GetMapping("/all")
    public List<friendList> getAll() {
        return friendListRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public Optional<friendList> getUser(@PathVariable long id) {
        return friendListRepository.findById(id);
    }

    @GetMapping("/user")
    public Optional<friendList> getUser(@RequestBody User user) {
        return friendListRepository.findById(user.getId());
    }

    @PostMapping("/add")
    public String addFriend(@RequestBody friendList friend) {
        System.out.println("Received data: " + friend.toString());
        friendListRepository.save(friend);
        return "Data sent: " + friend.toString();
    }
}
