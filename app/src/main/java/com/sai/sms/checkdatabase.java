package com.sai.sms;






import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
public class checkdatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CHECKing_CONTACT_PHONE";
    private static final String TABLE_CONTACTS = "alpABLE";
    private static final String TABLE_NAME = "UUTABLE";
    private static final String KEY_ID = "datta";
    private static final String KEY_NAME = "username";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_DES = "des";

    private static final String KEY_PH_NO = "phone_number";

    public checkdatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_NAME + " TEXT PRIMARY KEY"+"," + KEY_PHONE + " TEXT UNIQUE"+","+KEY_DES + " TEXT"+ ")";
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
    void scanmethodt(String contact,String phone,String dess) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact); // Contact Name
        values.put(KEY_PHONE, phone);
        values.put(KEY_DES, dess);

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }


    public String getphonebyname(String id) {
        String k="";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_PHONE}, KEY_NAME + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        k=cursor.getString(0);


        // return contact
        return k;
    }
    public String getdesbyphone(String id) {
        String k="";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_DES}, KEY_PHONE + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        k=cursor.getString(0);


        // return contact
        return k;
    }
    public String getnamebypho(String id) {
        String k="";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_NAME}, KEY_PHONE + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        k=cursor.getString(0);


        // return contact
        return k;
    }
    public String getphobyname(String id) {
        String k="";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_PHONE}, KEY_NAME + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        k=cursor.getString(0);


        // return contact
        return k;
    }
    public ArrayList<String> getphone(String id) {
        ArrayList<String> contactList = new ArrayList<String>();
        String k="";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_NAME}, KEY_DES + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                k = cursor.getString(0);
                // Adding contact to list
                contactList.add(k);
            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public ArrayList<String> getphones(String id) {
        ArrayList<String> contactList = new ArrayList<String>();
        String k="";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_PHONE}, KEY_DES + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                k = cursor.getString(0);
                // Adding contact to list
                contactList.add(k);
            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public int getcount(String id) {
        ArrayList<String> contactList = new ArrayList<String>();
        String k="";
        int i=0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_PHONE}, KEY_DES + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                k = cursor.getString(0);
                i++;
                // Adding contact to list
                contactList.add(k);
            } while (cursor.moveToNext());
        }
        return i;
    }
    public ArrayList<String> phone() {
        ArrayList<String> contactList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  "+KEY_PHONE+" FROM " + TABLE_CONTACTS;

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



    public ArrayList<String> getAllContactt() {
        ArrayList<String> contactList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  "+KEY_NAME+" FROM " + TABLE_CONTACTS;

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
    public Boolean updateContact(String oldname,String name,String pho,String desty) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_PHONE, pho);
        values.put(KEY_DES, desty);

        // updating row

        db.update(TABLE_CONTACTS, values, KEY_NAME + " = ?",
                new String[] { String.valueOf(oldname) });
        return true;
    }
}
