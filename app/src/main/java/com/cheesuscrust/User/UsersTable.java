package com.cheesuscrust.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UsersTable extends SQLiteOpenHelper {

    //Database Name
    public static final String DATABASE_NAME = "cheesus.db";

    //Table Name
    public static final String TABLE_USER = "user";

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

    public UsersTable(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Create user Table
        String sql_create_userTable =
                "CREATE TABLE " +TABLE_USER +" ( " +
                        COLUMN_NAME_USER_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_NAME_USER_FNAME +" TEXT," +
                        COLUMN_NAME_USER_LNAME + " TEXT," +
                        COLUMN_NAME_USER_EMAIL +" TEXT," +
                        COLUMN_NAME_USER_PHONE + " TEXT," +
                        COLUMN_NAME_USER_ADDRESS +" TEXT," +
                        COLUMN_NAME_USER_PASSWORD + " TEXT," +
                        COLUMN_NAME_USER_POINTS +" TEXT," +
                        COLUMN_NAME_USER_TYPE + " TEXT" +
                        ")";

        //Execute the query
        sqLiteDatabase.execSQL(sql_create_userTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor getUserData(String email)
    {
        //Connect the database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String query = "SELECT * FROM " +TABLE_USER +" WHERE " +COLUMN_NAME_USER_EMAIL +" = ?";
        String[] selectionArgs = {email};

        //Get results to a Cursor object
        return sqLiteDatabase.rawQuery(query, selectionArgs);
    }

    //Login function
    public int loginFunction(String email, String password)
    {
        //Connect the database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //Validate the login credentials
        String query = "SELECT * FROM " +TABLE_USER +" WHERE " +COLUMN_NAME_USER_EMAIL +" = ?";
        String[] selectionArgs = {email};

        //Get results to a Cursor object
        Cursor result = sqLiteDatabase.rawQuery(query, selectionArgs);

        if(result.getCount() == 0)
        {
            //No emails found
            return -1;
        }

        String databasePassword;

        while(result.moveToNext())
        {
            databasePassword = result.getString(6);

            if(!databasePassword.equals(password))
            {
                //password is not correct
                return 0;
            }

            else
            {
                //Login credentials are correct

                //Create UserData Singleton object to store user data
                UserData data = UserData.getInstance();

                //Store user data
                data.setUser_id(result.getInt(0));
                data.setUser_name(result.getString(1), result.getString(2));
                data.setUser_email(result.getString(3));
                data.setUser_phone(result.getString(4));
                data.setUser_address(result.getString(5));

                return 1;
            }
        }

        //Error
        return -2;
    }

    //Insert data to the table
    public int insertData(String fname, String lname, String email, String phone, String address, String password, String points, String type)
    {
        //Connect the database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //Validate the email
        String query = "SELECT * FROM " +TABLE_USER +" WHERE " +COLUMN_NAME_USER_EMAIL +" = ?";
        String[] selectionArgs = {email};

        //Get results to a Cursor object
        Cursor checkEmailCursor = sqLiteDatabase.rawQuery(query, selectionArgs);

        //Check whether an existing email exists
        if(checkEmailCursor.getCount() != 0)
        {
            return 0;
        }

        //If not insert data to the database
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_USER_FNAME, fname);
        contentValues.put(COLUMN_NAME_USER_LNAME, lname);
        contentValues.put(COLUMN_NAME_USER_EMAIL, email);
        contentValues.put(COLUMN_NAME_USER_PHONE, phone);
        contentValues.put(COLUMN_NAME_USER_ADDRESS, address);
        contentValues.put(COLUMN_NAME_USER_PASSWORD, password);
        contentValues.put(COLUMN_NAME_USER_POINTS, points);
        contentValues.put(COLUMN_NAME_USER_TYPE, type);

        long result = sqLiteDatabase.insert(TABLE_USER, null, contentValues);

        if(result == -1)
            return -1;
        else
            return 1;
    }

    //Get user data from the database
    public Cursor getData(String email)
    {
        //Connect the database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //Validate the email
        String query = "SELECT * FROM " +TABLE_USER +" WHERE " +COLUMN_NAME_USER_EMAIL +" = ?";
        String[] selectionArgs = {email};

        //Get results to a Cursor object
        Cursor result = sqLiteDatabase.rawQuery(query, selectionArgs);

        //Return the cursor object
        return result;
    }

    //Update user phone number
    public boolean updateUserPhone(String email, String phone)
    {
        //Connect the database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //Content Values
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_USER_PHONE, phone);

        //Where Clause
        String whereClause = COLUMN_NAME_USER_EMAIL +" = ?";
        String whereArgs[] = {email};

        //Execute the update function
        int isUpdated = sqLiteDatabase.update(TABLE_USER, contentValues, whereClause, whereArgs);

        if(isUpdated > 0)
            return true;

        else
            return false;

    }

    //Update user Email Address
    public int updateUserEmail(String currentEmail, String email)
    {
        //Connect the database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //Validate the email
        String query = "SELECT * FROM " +TABLE_USER +" WHERE " +COLUMN_NAME_USER_EMAIL +" = ?";
        String[] selectionArgs = {email};

        //Get results to a Cursor object
        Cursor checkEmailCursor = sqLiteDatabase.rawQuery(query, selectionArgs);

        //Check whether an existing email exists
        if(checkEmailCursor.getCount() != 0)
        {
            return -1;
        }

        //Content Values
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_USER_EMAIL, email);

        //Where Clause
        String whereClause = COLUMN_NAME_USER_EMAIL +" = ?";
        String[] whereArgs = {currentEmail};

        //Execute the update function
        int isUpdated = sqLiteDatabase.update(TABLE_USER, contentValues, whereClause, whereArgs);

        if(isUpdated > 0)
            return 1;

        else
            return 0;
    }

    //Update user address
    public boolean updateUserAddress(String email, String address)
    {
        //Connect the database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //Content Values
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_USER_ADDRESS, address);

        //Where Clause
        String whereClause = COLUMN_NAME_USER_EMAIL +" = ?";
        String[] whereArgs = {email};

        //Execute the update function
        int isUpdated = sqLiteDatabase.update(TABLE_USER, contentValues, whereClause, whereArgs);

        if(isUpdated > 0)
            return true;

        else
            return false;
    }

    //Update user password
    public int updateUserPassword(String email, String currentPassword, String newPassword)
    {
        //Connect the database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //Check the password
        String query = "SELECT * FROM " +TABLE_USER +" WHERE " +COLUMN_NAME_USER_EMAIL +" = ?";
        String[] selectionArgs = {email};

        //Get results to a Cursor object
        Cursor checkPasswordCursor = sqLiteDatabase.rawQuery(query, selectionArgs);

        while(checkPasswordCursor.moveToNext())
        {
            String databasePassword = checkPasswordCursor.getString(6);

            if(!databasePassword.equals(currentPassword))
            {
                //password is not correct
                return -1;
            }
        }

        //Content Values
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_USER_PASSWORD, newPassword);

        //Where Clause
        String whereClause = COLUMN_NAME_USER_EMAIL +" = ?";
        String[] whereArgs = {email};

        //Execute the update function
        int isUpdated = sqLiteDatabase.update(TABLE_USER, contentValues, whereClause, whereArgs);

        if(isUpdated > 0)
            return 1;

        else
            return 0;
    }

    //Delete user account
    public boolean deleteUser(String email)
    {
        //Connect the database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String whereClause = COLUMN_NAME_USER_EMAIL +" = ?";
        String[] whereArgs = {email};

        int result = sqLiteDatabase.delete(TABLE_USER, whereClause, whereArgs);

        if(result > 0)
            return true;

        else
            return false;
    }
}
