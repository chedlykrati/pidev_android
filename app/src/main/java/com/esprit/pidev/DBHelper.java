package com.esprit.pidev;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME  = "BookLibrary.db";
    private static final String TABLE_NAME = "my_library";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "book_title";
    private static final String COLUMN_AUTHOR = "book_author";
    private static final String COLUMN_PAGES = "book_pages";
    private final Context context;

   DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null,1);
        this.context = context;


    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("create Table users (username Text primary key , password Text)");
        //myDB.execSQL("create Table my_library (book_id Integer primary key  , book_title Text , book_author Text , book_pages Integer)");

        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_AUTHOR + " TEXT, " +
                COLUMN_PAGES + " INTEGER);";
        myDB.execSQL(query);


    }
//drop database during upgrade appplication and new database is going to create with DBHelper constructor
    @Override
    public void onUpgrade(SQLiteDatabase myDB, int oldVersion, int newVersion) {
        myDB.execSQL("drop Table if exists users");
        myDB.execSQL("drop Table if exists " + TABLE_NAME);
        onCreate(myDB);
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


    //public Boolean addBook(String title , String author , Integer pages)
     void addBook(String title , String author , Integer pages){

        SQLiteDatabase myDB = this.getWritableDatabase();
        //store data from our application
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE , title);
        cv.put(COLUMN_AUTHOR,author);
        cv.put(COLUMN_PAGES,pages);
        long result = myDB.insert(TABLE_NAME,null,cv);
        if(result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            //return false;
        }else {
            Toast.makeText(context, "Added Successfully !", Toast.LENGTH_SHORT).show();
            //return true;
        }

    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME ;
        SQLiteDatabase myDB = this.getReadableDatabase();
        //create cursor object
        Cursor cursor = null ;
        if (myDB != null ){
            //restore rawQuery inside our cursor
            cursor = myDB.rawQuery(query,null);

        }
        //cursor will return all data store in database
        return cursor;
    }

    void updateData(String row_id ,String title , String author , String pages){
       //to write in DataBase Table
       SQLiteDatabase db = this.getWritableDatabase();
       //Content Values cv object to store values inside this object
       ContentValues cv = new ContentValues();
       cv.put(COLUMN_TITLE, title);
       cv.put(COLUMN_AUTHOR, author);
       cv.put(COLUMN_PAGES, pages);
       //update TABLE_NAME with those values store in cv and their id equals with row_id
       long result = db.update(TABLE_NAME , cv , "_id=?" , new String[]{row_id} );
       //result == -1 means there is no data
       if (result == -1){
           Toast.makeText(context, "Failed to update .", Toast.LENGTH_SHORT).show();
       } else {
           Toast.makeText(context, "Successfully Updated ! ", Toast.LENGTH_SHORT).show();
       }
    }



    void deleteOneRow(String row_id){
       SQLiteDatabase db = this.getWritableDatabase();
       long result = db.delete(TABLE_NAME,"_id=?", new String[]{row_id});

       if(result == -1){

           Toast.makeText(context, "Failed to Delete .", Toast.LENGTH_SHORT).show();
       } else {
           Toast.makeText(context, "Successfully Deleted .", Toast.LENGTH_SHORT).show();
       }
    }

    void deleteAllData(){
       SQLiteDatabase db = this.getWritableDatabase();
       db.execSQL("DELETE FROM " + TABLE_NAME);
    }


}
