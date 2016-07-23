package com.example.admin.amex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by admin on 7/23/2016.
 */
public class DataBaseAdapter {
    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table "+"LOGIN"+
            "( " +"ID"+" integer primary key autoincrement,"+ "USERNAME  text, USERID text,EMAIL text,PASSWORD text); ";
    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private HelperClass dbHelper;
    public  DataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new HelperClass(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public  DataBaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public void insertEntry(String userid,String userName, String email, String password)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("USERNAME", userName);
        newValues.put("USERID",userid);
        newValues.put("EMAIL",email);
        newValues.put("PASSWORD",password);

        // Insert the row into your table
        db.insert("LOGIN", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }
    public int deleteEntry(String UserID)
    {
        //String id=String.valueOf(ID);
        String where="USERID=?";
        int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{UserID}) ;
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }
    public String getSinlgeEntry(String userID)
    {
        Cursor cursor=db.query("LOGIN", null, " USERID=?", new String[]{userID}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }
    public String getEmail(String userID)
    {
        Cursor cursor=db.query("LOGIN", null, " USERID=?", new String[]{userID}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String email= cursor.getString(cursor.getColumnIndex("EMAIL"));
        cursor.close();
        return email;
    }
    public String getUserName(String userID)
    {
        Cursor cursor=db.query("LOGIN", null, " USERID=?", new String[]{userID}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String username= cursor.getString(cursor.getColumnIndex("USERNAME"));
        cursor.close();
        return username;
    }
    public void  updateEntry(String userName,String userid,String email, String password)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("USERNAME", userName);
        updatedValues.put("USERID",userid);
        updatedValues.put("EMAIL",email);
        updatedValues.put("PASSWORD",password);

        String where="USERID = ?";
        db.update("LOGIN",updatedValues, where, new String[]{userid});
    }
}
