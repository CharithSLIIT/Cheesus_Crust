package com.cheesuscrust.User;

public class UserData_Singleton {

    //User data variables
    private String user_fname, user_lname, user_address, user_phone, user_email, user_type;

    //UserData_Singleton Singleton object
    private static UserData_Singleton userData = null;

    //Private constructor
    private UserData_Singleton(){}

    //getInstance() method
    public static UserData_Singleton getInstance()
    {
        if (userData == null)
            userData = new UserData_Singleton();

        return userData;
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

    //get user type
    public String getUser_type() {
        return user_type;
    }

    //set user type
    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
