package com.web.contact_studentname_studentid_android.Utiles;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLHelperClass extends SQLiteOpenHelper {
    public static final String dbName = "Database";
    public static final int dbVersion = '1';
    private static final String TAG = "SQLHelperClass";

    public SQLHelperClass(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE DETAILS(_id INTEGER PRIMARY KEY AUTOINCREMENT,studentFirstName TEXT,studentLastName TEXT,studentEmail TEXT,studentPhone TEXT,studentAddress TEXT)";
        sqLiteDatabase.execSQL(sql);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        onCreate(sqLiteDatabase);
    }

    public Cursor getAlldata() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM DETAILS", null);
        Log.e(TAG, "getAlldata: " + res.toString());
        return res;

    }

    public Cursor deleteData() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("delete from DETAILS", null);
        Log.e(TAG, "getAlldata: " + res.toString());
        return res;
    }

    public Integer deleteTitle(String id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete("DETAILS", "_id=?", new String[]{id});

    }

}
