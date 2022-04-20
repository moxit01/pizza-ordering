package com.example.pizzaordering;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OrderDbHelper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "orders.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public OrderDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    //order table creation
        final String SQL_CREATE_ORDER_TABLE = "CREATE TABLE IF NOT EXISTS " + OrderContract.OrderEntry.TABLE_NAME + " (" +
                OrderContract.OrderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                OrderContract.OrderEntry.COLUMN_TOTAL + " INTEGER NOT NULL, " +
                OrderContract.OrderEntry.COLUMN_DATE_TIME + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_ORDER_TABLE);

        //orderdetails table creation
        final String SQL_CREATE_ORDER_DETAIL_TABLE = "CREATE TABLE IF NOT EXISTS " + OrderDetailContract.OrderDetailEntry.TABLE_NAME + " (" +
                OrderDetailContract.OrderDetailEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                OrderDetailContract.OrderDetailEntry.COLUMN_ORDER_ID + " INTEGER NOT NULL, " +
                OrderDetailContract.OrderDetailEntry.COLUMN_TOPPING_PRICE + " INTEGER NOT NULL, " +
                OrderDetailContract.OrderDetailEntry.COLUMN_TOPPING_NAME + " TEXT NOT NULL" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_ORDER_DETAIL_TABLE);

    }





    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the DB_VERSION the table will be dropped.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + OrderContract.OrderEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + OrderDetailContract.OrderDetailEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
