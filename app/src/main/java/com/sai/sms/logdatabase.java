package com.sai.sms;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;


public class logdatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LOGIN";
    private static final String TABLE_CONTACTS = "UTABLE";
    private static final String KEY_ID = "data";
    private static final String KEY_NAME = "user";
    private static final String KEY_PASS = "pass";
    private static final String KEY_PH_NO = "phone_number";

    public logdatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" +KEY_NAME +" TEXT"+","+KEY_PASS +" TEXT PRIMARY KEY" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    public Boolean updateContact(String us,String pass) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_PASS, pass);

        // updating row

        db.update(TABLE_CONTACTS, values, KEY_NAME + " = ?",
                new String[] { String.valueOf(us) });
        return true;
    }

    // code to add the new contact
    public Boolean scanmethod(String u,String p) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, u); // Contact Name
        values.put(KEY_PASS, p); // Contact Name


        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
        return true;
    }



    // code to get all contacts in a list view
    public String getAllContacts() {

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String k="";
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                k=cursor.getString(1);

                // Adding contact tolist

            } while (cursor.moveToNext());
        }

        // return contact list
        return k;
    }
    public String getContact(String id) {
        String k="";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_PASS}, KEY_NAME + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        k=cursor.getString(0);


        // return contact
        return k;
    }







}