package coms309.Cycino.friendList;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;

@Entity
@Table(name = "friend_list")
public class friendList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int friendOneId;
    private int friendTwoId;
    private String friendNickname;
    public friendList() {}

    public friendList(int friendOneId, int friendTwoId, String friendNickname) {
        this.friendOneId = friendOneId;
        this.friendTwoId = friendTwoId;
        this.friendNickname = friendNickname;
    }

    public int getFriendOneId() {
        return friendOneId;
    }

    public void setFriendOneId(int friendOneId) {
        this.friendOneId = friendOneId;
    }

    public int getFriendTwoId() {
        return friendTwoId;
    }

    public void setFriendTwoId(int friendTwoId) {
        this.friendTwoId = friendTwoId;
    }

    public String getFriendNickname() {
        return friendNickname;
    }

    public void setFriendNickname(String friendNickname) {
        this.friendNickname = friendNickname;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "friendList{" +
                "id=" + id +
                ", friendOneId=" + friendOneId +
                ", friendTwoId=" + friendTwoId +
                ", friendNickname='" + friendNickname + '\'' +
                '}';
    }
}
