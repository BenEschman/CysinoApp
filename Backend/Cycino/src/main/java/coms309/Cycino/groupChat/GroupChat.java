package coms309.Cycino.groupChat;

import jakarta.persistence.*;
import coms309.Cycino.users.User;

import java.util.HashSet;
import java.util.Set;

@Entity
public class GroupChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;

    @ManyToMany(mappedBy = "groupChats")
    private Set<User> users = new HashSet<>();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
