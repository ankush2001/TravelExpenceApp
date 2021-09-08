package com.example.travelexpence.Login;

public class userHelperClass {

    public userHelperClass(){
    }

    String fullName,userName,password,email;
    public userHelperClass(String fullName, String password, String email) {
        this.fullName = fullName;
        this.password = password;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
