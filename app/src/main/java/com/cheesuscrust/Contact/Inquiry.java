package com.cheesuscrust.Contact;

public class Inquiry {
    private int contact_id;
    private String contact_date,contact_fname,contact_lname,contact_email,contact_remail,contact_phone,contact_msg,contact_status;

    public Inquiry(int id,String status,String date,String fname,String lname,String email,String remail,String phone,String msg){
        this.contact_id = id;
        this.contact_date = date;
        this.contact_fname = fname;
        this.contact_lname = lname;
        this.contact_email = email;
        this.contact_remail = remail;
        this.contact_phone = phone;
        this.contact_msg = msg;
        this.contact_status = status;

    }

    public int getId(){
        return contact_id;
    }

    String getFname(){
        return contact_fname;
    }

    String getLname(){
        return contact_lname;
    }

    public String getEmail(){
        return contact_email;
    }

    public String getRemail(){
        return contact_remail;
    }

    public String getPhone(){
        return contact_phone;
    }

    String getMsg(){
        return contact_msg;
    }

    String getStatus(){
        return contact_status;
    }

    String getDate(){
        return contact_date;
    }

}
