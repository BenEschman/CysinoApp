package coms309.Cycino.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users") // escaping using double quotes for H2 SQL compatibility
public class User {

    @Id
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private String role;


    public User(){}

    public User(String id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }
    public User(String id, String username, String password, String first, String last){
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = first;
        this.lastName = last;
    }
    public User(String id, String username, String password, String first, String last, String phoneNumber){
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = first;
        this.lastName = last;
        this.phoneNumber = phoneNumber;
    }
    public User(String id, String username, String password, String first, String last, String phoneNumber, String role){
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = first;
        this.lastName = last;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public String getId(){
        return id;
    }
    public String[] getLogin(){
        return new String[]{username, password};
    }
    public String[] getContact(){
        return new String[]{firstName, lastName, phoneNumber};
    }

    public String getRole(){
        return role;
    }

    public void updateLogin(String[] login){
        this.username = login[0];
        this.password = login[1];
    }

    public void updateContact(String[] contact){
        this.firstName = contact[0];
        this.lastName = contact[1];
        this.phoneNumber = contact[2];
    }

    public void updateRole(String role){
        this.role = role;
    }
}
