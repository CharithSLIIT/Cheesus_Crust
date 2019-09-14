package com.cheesuscrust.Product;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "cheesus.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //newvid
    public void queryData(String sql){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + UsersMaster.Users.TABLE_NAME + " (" +
                        UsersMaster.Users.COLUMN_NAME_P_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        UsersMaster.Users.COLUMN_NAME_P_NAME + " TEXT," +
                        UsersMaster.Users.COLUMN_NAME_P_DESCRIPTION + " TEXT," +
                        UsersMaster.Users.COLUMN_NAME_P_S_price + " STRING," +
                        UsersMaster.Users.COLUMN_NAME_P_M_price + " STRING," +
                        UsersMaster.Users.COLUMN_NAME_P_L_price + " STRING," +
                        UsersMaster.Users.COLUMN_NAME_P_TYPE + " TEXT," +
                        UsersMaster.Users.COLUMN_NAME_P_IMG + " BLOB)";

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+UsersMaster.Users.TABLE_NAME);
        onCreate(db);
    }


    public boolean insertData(String name, String desc, String sprice, String mprice, String lprice, String type, byte[] image) throws SQLException {
        Log.i("first1", "image byte is " +image);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UsersMaster.Users.COLUMN_NAME_P_NAME,name);
        contentValues.put(UsersMaster.Users.COLUMN_NAME_P_DESCRIPTION,desc);
        contentValues.put(UsersMaster.Users.COLUMN_NAME_P_S_price,sprice);
        contentValues.put(UsersMaster.Users.COLUMN_NAME_P_M_price,mprice);
        contentValues.put(UsersMaster.Users.COLUMN_NAME_P_L_price,lprice);
        contentValues.put(UsersMaster.Users.COLUMN_NAME_P_TYPE,type);
        contentValues.put(UsersMaster.Users.COLUMN_NAME_P_IMG,image);
        long result = db.insert(UsersMaster.Users.TABLE_NAME, null, contentValues);
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
