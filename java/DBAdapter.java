package com.example.navidroohibroojeni.dbappnavid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by navidroohibroojeni on 3/19/16.
 */

// this agent is going to talk to database and our app

public class DBAdapter {

    public static final String DATABASE_NAME = "MYDB";
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_TABLE = "MyDBTable";

    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME =  "name";
    public static final String KEY_MAJORE = "majore";

    public static final int COL_ROWID = 0;
    public static final int COL_NAME = 1;
    public static final int COL_MAJORE = 2;

    public static final String[] All_KEYS = {KEY_ROWID, KEY_NAME, KEY_MAJORE };


    private Context context;
    private SQLiteDatabase db;
    private DatabaseHelper myDBHElper;


    DBAdapter(Context context){
        this.context = context;
        myDBHElper = new DatabaseHelper(context);
    }



    public DBAdapter open(){
        db = myDBHElper.getWritableDatabase();
        return this;

    }

    public void close(){
        myDBHElper.close();
    }

    public long insertRow(String name, String majore){

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, name);
        values.put(KEY_MAJORE, majore);

        long rowid = db.insert(DATABASE_TABLE, null, values);
        return rowid;
    }


public Cursor getAllRows(){
    Cursor cursor = db.query(DATABASE_TABLE, All_KEYS, null, null, null,null, null );

    if (cursor.getCount() > 0){
        cursor.moveToFirst();
    }
    return cursor;
}


    public void deleteAll(){
        Cursor cursor = getAllRows();
        int rowidx = cursor.getColumnIndexOrThrow(KEY_ROWID);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                deleteRow(cursor.getLong(rowidx));
            }while (cursor.moveToNext());
        }
    }

    public boolean deleteRow(long rowid){
        String whereStr = KEY_ROWID + "=" + rowid;
        return db.delete(DATABASE_TABLE, whereStr, null)>0;
    }


/*
    // get a particular row, in case you need it. just add a WHERE statement in the third query parameter
    public Cursor getRow(long rowid){
        String whereStr = KEY_ROWID + "="+ rowid;
        Cursor cursor = db.query(DATABASE_NAME, All_KEYS, whereStr, null, null,null, null );

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
        }
        return cursor;
    }

*/







    class DatabaseHelper extends SQLiteOpenHelper{


        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {

            String CREATE_DB_SQL = "CREATE TABLE "
                                    + DATABASE_TABLE + " ("
                                        + KEY_ROWID + " integer primary key autoincrement, "
                                        + KEY_NAME + " text not null, "
                                        + KEY_MAJORE + " text not null );";

            database.execSQL(CREATE_DB_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

            database.execSQL("DROP TABLE IS EXISTS " + DATABASE_TABLE);
            onCreate(database);
        }
    }










}
