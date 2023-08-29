package com.sai.sms;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class All_ImageHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SCA";
    private static final String TABLE_CONTACTS = "SCANTABL";
    private static final String KEY_ID = "data";
    private static final String KEY_NAME = "data";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_IMAGE = "image";

    public All_ImageHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" +KEY_PH_NO +" TEXT PRIMARY KEY" +","+ KEY_IMAGE + " BLOB"+ ")";
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

    // code to add the new contact
    void scanmethod(String contact,byte[] conta) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PH_NO, contact);
        values.put(KEY_IMAGE, conta);// Contact Name


        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
    public void deleteContact(String contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_PH_NO+ " = ?",
                new String[] { contact });
        db.close();
    }


    // code to get all contacts in a list view
    public ArrayList<String> getAllContacts() {
        ArrayList<String> contactList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String k="";
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                k=cursor.getString(0);

                // Adding contact tolist
                contactList.add(k);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public Bitmap getContact(String id) {
        String k="";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_IMAGE}, KEY_PH_NO + "=?",
                new String[] { id }, null, null, null, null);
        Bitmap bmp=null;
        while(cursor.moveToNext())
        {
            byte[] image = cursor.getBlob(0);
            bmp=BitmapFactory.decodeByteArray(image, 0 , image.length);
        }




        // return contact
        return bmp;
    }





}
