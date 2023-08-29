package com.sai.sms;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
public class important_database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "IMPORTANT_PHONE";
    private static final String TABLE_CONTACTS = "PPTABLE";
    private static final String TABLE_NAME = "UUTABLE";
    private static final String KEY_ID = "datta";
    private static final String KEY_NAME = "datta";

    private static final String KEY_PH_NO = "phone_number";

    public important_database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_NAME + " TEXT PRIMARY KEY" + ")";
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
    public void deleteContact(String contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_NAME+ " = ?",
                new String[] { contact });
        db.close();
    }

    // code to add the new contact
    void scanmethodt(String contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact); // Contact Name


        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }


    public ArrayList<String> getAllContactt() {
        ArrayList<String> contactList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String k="";
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                k = cursor.getString(0);
                // Adding contact to list
                contactList.add(k);
            } while (cursor.moveToNext());
        }
        return contactList;
    }
    // code to get all contacts in a list view
    public String getAllContacts() {

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String k = "";
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                k = k+cursor.getString(0);

                // Adding contact tolist

            } while (cursor.moveToNext());
        }

        // return contact list
        return k;
    }
}