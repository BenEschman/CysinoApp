package coms309.Cycino.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import coms309.Cycino.users.User;
import jakarta.persistence.*;

import java.util.Random;

@Entity
public class LoginInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;

    @OneToOne
    @JsonIgnore
    private User user;

    public LoginInfo(){}

    public LoginInfo(String username, String password){
        //id = new Random().nextLong(0, 999999999);
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public long getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
