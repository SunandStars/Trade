package com.example.dawn.car.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by XU_aa on 2015/9/9.
 */
public class MyOpenHelper extends SQLiteOpenHelper{

       public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //
    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.delete("BuyerDetails", null, null);
        db.execSQL("create table BuyerDetails( _id integer primary key autoincrement," +
                "address char(100)," +
                "model char(20)," +
                " company char(100)," +
                "orderNo char(100)," +
                "orderDate char(100)," +
                "accessory1 char(100)," +
                "accessory2 char(100)," +
                "accessory3 char(100)," +
                "accessory4 char(100)," +
                "accessorynum1 char(20)," +
                "accessorynum2 char(20)," +
                "accessorynum3 char(20)," +
                "accessorynum4 char(20)," +
                "accessoryremark1 char(100)," +
                "accessoryremark2 char(100)," +
                "accessoryremark3 char(100)," +
                "accessoryremark4 char(100)," +
                "earnest char(20)," +
                "demand char(100)," +
                "modelyear char(20))");
        ContentValues values = new ContentValues();
        values.put("address", "Test1");
        values.put("model", "Test1");
        values.put("company","Test1");
        values.put("orderNo","Test1");
        values.put("orderDate","Test1");
        values.put("accessory1","Test1");
        values.put("accessory2","Test1");
        values.put("accessory3","Test1");
        values.put("accessory4","Test1");
        values.put("accessorynum1","Test1");
        values.put("accessorynum2","Test1");
        values.put("accessorynum3","Test1");
        values.put("accessorynum4","Test1");
        values.put("accessoryremark1","Test1");
        values.put("accessoryremark2","Test1");
        values.put("accessoryremark3","Test1");
        values.put("accessoryremark4","Test1");
        values.put("earnest","Test1");
        values.put("demand","Test1");
        values.put("modelyear","Test1");


        db.insert("BuyerDetails", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}










