package coms309.Cycino.follow;

import jakarta.persistence.*;

@Entity
@Table(name = "follow")
public class follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //@Column(name = "follower")
    private long followingID;

    // Constructors
    public follow() {}
    public follow(long followingID) {
        this.followingID = followingID;
    }

    // Getters & Setters
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public long getFollowingID() {return followingID;}
    public void setFollowingID(long followingID) {this.followingID = followingID;}
}
