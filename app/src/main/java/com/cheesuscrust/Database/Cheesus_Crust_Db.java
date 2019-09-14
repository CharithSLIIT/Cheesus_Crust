package com.cheesuscrust.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.DashPathEffect;
import android.util.Log;

import androidx.annotation.Nullable;

import com.cheesuscrust.User.UserData;

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

        //Create contact Table
        //create table
        String sql_create_contact =
                "CREATE TABLE " + TABLE_CONTACT + "(" +
                        COLUMN_NAME_CON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_NAME_CON_STATUS + " TEXT," +
                        COLUMN_NAME_CON_DATE + " TEXT," +
                        COLUMN_NAME_CON_FNAME + " TEXT," +
                        COLUMN_NAME_CON_LNAME + " TEXT," +
                        COLUMN_NAME_CON_EMAIL + " TEXT," +
                        COLUMN_NAME_CON_REMAIL + " TEXT," +
                        COLUMN_NAME_CON_PHONE + " TEXT," +
                        COLUMN_NAME_CON_MSG + " TEXT" +
                        " ) ";

        //EXECUTE THE QUERY
        sqLiteDatabase.execSQL(sql_create_contact);

        //Create product Table
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_P_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_NAME_P_NAME + " TEXT," +
                        COLUMN_NAME_P_DESCRIPTION + " TEXT," +
                        COLUMN_NAME_P_S_price + " STRING," +
                        COLUMN_NAME_P_M_price + " STRING," +
                        COLUMN_NAME_P_L_price + " STRING," +
                        COLUMN_NAME_P_TYPE + " TEXT," +
                        COLUMN_NAME_P_IMG + " BLOB)";

        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        //user Table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_USER);
        onCreate(sqLiteDatabase);

        //contact Table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_CONTACT);
        onCreate(sqLiteDatabase);

        //product Table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    //user Table functions
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


    //contact Table Functions
    //insert data in to the table
    public boolean insertData(String date, String fname, String lname, String email, String remail, String phone,  String msg) {

        //connect the database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //If not insert data to the database
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_CON_DATE, date);
        contentValues.put(COLUMN_NAME_CON_FNAME, fname);
        contentValues.put(COLUMN_NAME_CON_LNAME, lname);
        contentValues.put(COLUMN_NAME_CON_EMAIL, email);
        contentValues.put(COLUMN_NAME_CON_REMAIL, remail);
        contentValues.put(COLUMN_NAME_CON_PHONE, phone);
        contentValues.put(COLUMN_NAME_CON_MSG, msg);
        contentValues.put(COLUMN_NAME_CON_STATUS, "Pending");

        long result = sqLiteDatabase.insert(TABLE_CONTACT, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }


    public boolean updateInquiry(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_CON_STATUS, "Complete");

        //where clause
        String whereClause =COLUMN_NAME_CON_ID +" = ?";
        String[] whereArgs = {Integer.toString(id)};

        //execute the update function
        int isUpdated = sqLiteDatabase.update(TABLE_CONTACT,contentValues,whereClause, whereArgs);

        if(isUpdated > 0)
        {
            return true;
        }

        else
        {
            return false;
        }
    }

    public Cursor getAllInquries(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String query = "SELECT * FROM " +TABLE_CONTACT;

        Cursor result = sqLiteDatabase.rawQuery(query, null);

        return result;


    }

    public boolean deleteInquiry(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String whereClause = COLUMN_NAME_CON_ID +" = ?";
        String[] whereArgs = {Integer.toString(id)};

        int result = sqLiteDatabase.delete(TABLE_CONTACT, whereClause, whereArgs);

        if(result > 0)
            return true;

        else
            return false;
    }


    //product Table Functions
    public boolean insertData(String name, String desc, String sprice, String mprice, String lprice, String type, byte[] image) throws SQLException {
        Log.i("first1", "image byte is " +image);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_P_NAME,name);
        contentValues.put(COLUMN_NAME_P_DESCRIPTION,desc);
        contentValues.put(COLUMN_NAME_P_S_price,sprice);
        contentValues.put(COLUMN_NAME_P_M_price,mprice);
        contentValues.put(COLUMN_NAME_P_L_price,lprice);
        contentValues.put(COLUMN_NAME_P_TYPE,type);
        contentValues.put(COLUMN_NAME_P_IMG,image);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public void updateData(int id, String name, String desc, String sprice, String mprice, String lprice, byte[] image) throws SQLException{
        Log.i("dataid", "ID in a string is " + image);
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "UPDATE product SET p_name = ?, p_description = ?, p_s_price = ?, p_m_price = ?, p_l_price = ?, p_img = ? WHERE p_id = ?";

        SQLiteStatement statement = db.compileStatement(sql);
        Log.i("dataid", "ID in a string is " + statement);
        statement.bindString(1, name);
        statement.bindString(2, desc);
        statement.bindString(3, sprice);
        statement.bindString(4, mprice);
        statement.bindString(5, lprice);
        statement.bindBlob(6,image);
        statement.bindDouble(7, (double)id);

        statement.execute();
        db.close();


    }

    public void deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "DELETE FROM product WHERE p_id = ?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        db.close();
    }

    //newvid commented "public Cursor getAllData()" and retyped getData
//    public Cursor getAllData(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("SELECT * FROM " + UsersMaster.Users.TABLE_NAME, null);
//        return res;
//    }

    public Cursor getData(String sql){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(sql, null);
    }
}
