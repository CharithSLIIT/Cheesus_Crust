package com.cheesuscrust.User;

public class UserData {

    //User data variables
    private int user_id;
    private String user_fname, user_lname, user_address, user_phone, user_email;

    //UserData Singleton object
    private static UserData userData = null;

    //Private constructor
    private UserData(){}

    //getInstance() method
    public static UserData getInstance()
    {
        if (userData == null)
            userData = new UserData();

        return userData;
    }

    //set user id
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    //get user name
    String getUser_name() {
        return user_fname + " " +user_lname;
    }

    //Set user name
    public void setUser_name(String user_fname, String user_lname) {
        this.user_fname = user_fname;
        this.user_lname = user_lname;
    }

    //get user address
    String getUser_address() {
        return user_address;
    }

    //set user address
    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    //get user phone number
    String getUser_phone() {
        return user_phone;
    }

    //set user phone number
    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    //get user email address
    String getUser_email() {
        return user_email;
    }

    //set user email address
    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public int getUser_id() {
        return user_id;
    }
}
