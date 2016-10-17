package com.darshan.client.passbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

/**
 * Created by Administrator on 6/26/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    Context ctx;
    SQLiteDatabase db;
    public DatabaseHelper(Context context) {
        super(context, "PersonDB", null, 1);

        ctx=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

      db.execSQL("Create Table data (username text,serviceprovider text,email text,password text);");
    }

    public long insertData(String username,String serviceProvider,String email,String password)
    {
          db = this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put("username", username);
        contentValues.put("serviceprovider",serviceProvider);
        contentValues.put("email", email);
        contentValues.put("password",password);
        long id=db.insert("data",null,contentValues);

        return id;
    }

    public void deleteData()
    {
        db=this.getWritableDatabase();
        db.delete("data","1=1",null);

    }

    public boolean isEmpty() {
       db = this.getReadableDatabase();
       Cursor cursor = db.rawQuery("Select * from data", null);

        int icount = cursor.getCount();
        if (icount == 0) {
            return true;
        }


        return false;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
