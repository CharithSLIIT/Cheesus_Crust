package com.cheesuscrust.User;

class Users {

    //User data variables
    private String user_fname, user_lname, user_address, user_phone, user_email, position;

    Users(String user_fname, String user_lname, String user_address, String user_phone, String user_email) {
        this.user_fname = user_fname;
        this.user_lname = user_lname;
        this.user_address = user_address;
        this.user_phone = user_phone;
        this.user_email = user_email;
    }

    Users(String user_fname, String user_lname, String user_address, String user_phone, String user_email, String position) {
        this.user_fname = user_fname;
        this.user_lname = user_lname;
        this.user_address = user_address;
        this.user_phone = user_phone;
        this.user_email = user_email;
        this.position = position;
    }

    //get user name
    String getUser_name() {
        return user_fname + " " +user_lname;
    }

    //get user address
    String getUser_address() {
        return user_address;
    }

    //get user phone number
    String getUser_phone() {
        return user_phone;
    }

    //get user email address
    String getUser_email() {
        return user_email;
    }

    //get user position
    String getUser_position() {
        return position;
    }
}
