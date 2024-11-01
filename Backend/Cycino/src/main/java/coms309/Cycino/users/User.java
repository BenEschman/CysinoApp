package coms309.Cycino.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import coms309.Cycino.follow.Follow;
import coms309.Cycino.login.LoginInfo;
import coms309.Cycino.stats.UserStats;
import jakarta.persistence.*;
import coms309.Cycino.Enums;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Enumerated(EnumType.STRING)
    private Enums.Roles role = Enums.Roles.BEGINNER;
    private String userBiography;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_user_ID", referencedColumnName = "id")
    private List<Follow> followList;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private List<UserStats> userStatslist;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private LoginInfo loginInfo;

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private Set<UserStats> userStats = new HashSet<>();

    public User(){}

    public User(Long id, String firstName, String lastName, String phoneNumber, Enums.Roles role, String userBiography, List<Follow> followList, LoginInfo loginInfo) {
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

    public List<Follow> getFollowList() {
        return followList;
    }
    public void setFollowList(List<Follow> followList) {
        this.followList = followList;
    }
    public void newFollow(Follow follow){
        this.followList.add(follow);
    }
    public void removeFollow(Follow follow){
        this.followList.remove(follow);
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

    public Enums.Roles getRole(){
        return role;
    }

    public void setRole(Enums.Roles role){
        this.role = role;
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
        this.role = Enums.Roles.valueOf(role.toUpperCase());
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role=" + role +
                ", userBiography='" + userBiography + '\'' +
                ", loginInfo=" + loginInfo +
                ", id=" + id +
                '}';
    }
    public void setId(long id){
        this.id=id;
    }
}
