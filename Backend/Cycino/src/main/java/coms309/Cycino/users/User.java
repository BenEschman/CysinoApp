package coms309.Cycino.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import coms309.Cycino.login.LoginInfo;
import coms309.Cycino.login.LoginService;
import jakarta.persistence.*;
import coms309.Cycino.Roles;

@Entity
@Table(name = "users") // escaping using double quotes for H2 SQL compatibility
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Roles role = Roles.BEGINNER;

    @OneToOne
    @JsonIgnore
    private LoginInfo loginInfo;

    public User(){}

    public User( String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(long id, String firstName, String lastName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public User(long id, String first, String last, String phoneNumber){
        this.id = id;
        this.firstName = first;
        this.lastName = last;
        this.phoneNumber = phoneNumber;
    }
    public User(long id, String first, String last, String phoneNumber, String role){
        this.id = id;
        this.firstName = first;
        this.lastName = last;
        this.phoneNumber = phoneNumber;
        this.role = Roles.valueOf(role.toUpperCase());
    }

    public long getId(){
        return id;
    }

    public String getFirstName(){
        return firstName;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Roles getRole(){
        return role;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String[] getContact(){
        return new String[]{firstName, lastName, phoneNumber};
    }
    public void updateRole(String role){
        this.role = Roles.valueOf(role.toUpperCase());
    }
}
