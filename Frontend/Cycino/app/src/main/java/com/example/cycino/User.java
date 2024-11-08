package com.example.cycino;

public class User {
    private static String username ;
    private static String password ;
    private static String firstname;
    private static String lastname;
    private static int userID ;

    public User(String un, String pw, String fn, String ln, Integer uID) {
        username = un;
        password = pw;
        firstname = fn;
        lastname = ln;
        userID = uID;

    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        User.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        User.password = password;
    }

    public static String getFirstname() {
        return firstname;
    }

    public static void setFirstname(String firstname) {
        User.firstname = firstname;
    }

    public static String getLastname() {
        return lastname;
    }

    public static void setLastname(String lastname) {
        User.lastname = lastname;
    }

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        User.userID = userID;
    }
}
