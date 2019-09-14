package com.cheesuscrust.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.DashPathEffect;

import androidx.annotation.Nullable;

public class Cheesus_Crust_Db extends SQLiteOpenHelper {

    //Database Name
    public static final String DATABASE_NAME = "cheesus.db";

    //Table Names
    public static final String TABLE_USER = "user";
    public static final String TABLE_CONTACT = "contact";
    public static final String TABLE_NAME = "product";

    //Column names of the user Table
    public static final String COLUMN_NAME_USER_ID = "user_id";
    public static final String COLUMN_NAME_USER_FNAME = "user_fname";
    public static final String COLUMN_NAME_USER_LNAME = "user_lname";
    public static final String COLUMN_NAME_USER_EMAIL = "user_email";
    public static final String COLUMN_NAME_USER_PHONE = "user_phone";
    public static final String COLUMN_NAME_USER_ADDRESS = "user_address";
    public static final String COLUMN_NAME_USER_PASSWORD = "user_password";
    public static final String COLUMN_NAME_USER_POINTS = "user_points";
    public static final String COLUMN_NAME_USER_TYPE = "user_type";

    //Column names of the contact Table
    public static final String COLUMN_NAME_CON_ID = "con_id";
    public static final String COLUMN_NAME_CON_STATUS = "con_status";
    public static final String COLUMN_NAME_CON_DATE = "con_date";
    public static final String COLUMN_NAME_CON_FNAME = "con_fname";
    public static final String COLUMN_NAME_CON_LNAME = "con_lname";
    public static final String COLUMN_NAME_CON_EMAIL = "con_email";
    public static final String COLUMN_NAME_CON_REMAIL = "con_remail";
    public static final String COLUMN_NAME_CON_PHONE = "con_phone";
    public static final String COLUMN_NAME_CON_MSG = "con_msg";

    //Column names of the product Table
    public static final String COLUMN_NAME_P_ID = "p_id";
    public static final String COLUMN_NAME_P_NAME = "p_name";
    public static final String COLUMN_NAME_P_DESCRIPTION = "p_description";
    public static final String COLUMN_NAME_P_S_price = "p_s_price";
    public static final String COLUMN_NAME_P_M_price = "p_m_price";
    public static final String COLUMN_NAME_P_L_price = "p_l_price";
    public static final String COLUMN_NAME_P_TYPE = "p_type";
    public static final String COLUMN_NAME_P_IMG = "p_img";

    public Cheesus_Crust_Db(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
