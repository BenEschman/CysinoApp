package coms309.Cycino.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users") // escaping using double quotes for H2 SQL compatibility
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String role;


    public User(){}

    public User(String id, String firstName, String lastName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public User(String id, String first, String last, String phoneNumber){
        this.id = id;
        this.firstName = first;
        this.lastName = last;
        this.phoneNumber = phoneNumber;
    }
    public User(String id, String first, String last, String phoneNumber, String role){
        this.id = id;
        this.firstName = first;
        this.lastName = last;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public String getId(){
        return id;
    }

    public String[] getContact(){
        return new String[]{firstName, lastName, phoneNumber};
    }

    public String getRole(){
        return role;
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
