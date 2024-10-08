package coms309.Cycino.follow;

import coms309.Cycino.users.User;
import jakarta.persistence.*;

@Entity
@Table(name = "follow")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //@Column(name = "follower")
    private long followingID;

    @ManyToOne
    @JoinColumn(name = "fk_user_ID")
    private User user;

    // Constructors
    public Follow() {}
    public Follow(long followingID) {
        this.followingID = followingID;
    }

    // Getters & Setters
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public long getFollowingID() {return followingID;}
    public void setFollowingID(long followingID) {this.followingID = followingID;}

    @Override
    public String toString() {
        return "Follow{" +
                "id=" + id +
                ", followingID=" + followingID +
                ", user=" + user +
                '}';
    }
}
