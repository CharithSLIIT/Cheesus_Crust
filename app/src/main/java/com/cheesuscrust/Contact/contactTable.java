package com.cheesuscrust.Contact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class contactTable extends SQLiteOpenHelper {

    //database name
    public static final String DATABASE_NAME = "cheesus.db";

    //table name
    public static final String TABLE_CONTACT = "contact";

    public static final String COLUMN_NAME_CON_ID = "con_id";
    public static final String COLUMN_NAME_CON_STATUS = "con_status";
    public static final String COLUMN_NAME_CON_DATE = "con_date";
    public static final String COLUMN_NAME_CON_FNAME = "con_fname";
    public static final String COLUMN_NAME_CON_LNAME = "con_lname";
    public static final String COLUMN_NAME_CON_EMAIL = "con_email";
    public static final String COLUMN_NAME_CON_REMAIL = "con_remail";
    public static final String COLUMN_NAME_CON_PHONE = "con_phone";
    public static final String COLUMN_NAME_CON_MSG = "con_msg";

    public contactTable(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
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

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +TABLE_CONTACT );
        onCreate(sqLiteDatabase);

    }

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
}


