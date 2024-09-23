package coms309.Cycino.login;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class LoginInfo {

    @Id
    private long id;
    private String username;
    private String password;

    public LoginInfo(){}

    public LoginInfo(String username, String password){
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
