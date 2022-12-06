package com.example.aanaxee;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "anaxeeDetail";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "Anaxee";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String USER_NAME_COL = "email";
    private static final String EMAIL_COL = "address";
    private static final String CONTACT_COL = "contact";
    private static final String PASSWORD_COL = "password";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + USER_NAME_COL + " TEXT,"
                + EMAIL_COL + " TEXT,"
                + CONTACT_COL + " TEXT,"
                + PASSWORD_COL + " TEXT)";
        db.execSQL(query);
    }

    public boolean addNewUser(String userName, String userUserName, String userEmail, String useContact, String userPassword) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COL, userName);
        values.put(USER_NAME_COL, userUserName);
        values.put(EMAIL_COL, userEmail);
        values.put(CONTACT_COL, useContact);
        values.put(PASSWORD_COL, userPassword);

        db.insert(TABLE_NAME, null, values);
        db.close();
        return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
    