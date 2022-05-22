package com.esprit.pidev;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "Login.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("create Table users (username Text primary key , password Text)");

    }
//drop database during upgrade appplication and new database is going to create with DBHelper constructor
    @Override
    public void onUpgrade(SQLiteDatabase myDB, int oldVersion, int newVersion) {
        myDB.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String username , String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password", password);
        //store the values
        long result = myDB.insert("users",null,contentValues);
        // -1 registration failed
        if (result == -1){
            return false;
        }else {
            return true;
        }

    }

    public Boolean checkUserName(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ?",new String[] {username});

        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }

    }

    public Boolean checkUserNamePassword(String username , String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ? and password = ? ",new String[] {username,password});

        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }

    }
}
