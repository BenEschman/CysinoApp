package coms309.Cycino.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import coms309.Cycino.follow.follow;
import coms309.Cycino.login.LoginInfo;
import coms309.Cycino.login.LoginService;
import jakarta.persistence.*;
import coms309.Cycino.Roles;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users") // escaping using double quotes for H2 SQL compatibility
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Roles role = Roles.BEGINNER;
    private String userBiography;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_user_ID", referencedColumnName = "id")
    private List<follow> followList;

    @OneToOne
    @JsonIgnore
    private LoginInfo loginInfo;

    public User(){}

    public User(Long id, String firstName, String lastName, String phoneNumber, Roles role, String userBiography, List<follow> followList, LoginInfo loginInfo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.userBiography = userBiography;
        this.followList = followList;
        this.loginInfo = loginInfo;
    }
/*
    public User(String firstName, String lastName){
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

    public User(long id, String first, String last, String phoneNumber, String role, List<follow> followList){
        this.id = id;
        this.firstName = first;
        this.lastName = last;
        this.phoneNumber = phoneNumber;
        this.role = Roles.valueOf(role.toUpperCase());
        this.followList = followList;
    }
*/

    public List<follow> getFollowList() {
        return followList;
    }

    public void setFollowList(List<follow> followList) {
        this.followList = followList;
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

    public String getUserBiography() {return userBiography;}
    public void setUserBiography(String userBiography) {this.userBiography = userBiography;}

    public String[] getContact(){
        return new String[]{firstName, lastName, phoneNumber};
    }
    public void updateRole(String role){
        this.role = Roles.valueOf(role.toUpperCase());
    }

    public void newFollow(follow follow){
        List<follow> newFollowList = getFollowList();
        newFollowList.add(follow);
        setFollowList(newFollowList);
        System.out.println(this.followList);
    }
}
