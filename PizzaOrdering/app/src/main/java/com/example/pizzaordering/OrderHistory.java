package com.example.pizzaordering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pizzaordering.OrderAdapter;
import com.example.pizzaordering.OrderContract;
import com.example.pizzaordering.OrderDbHelper;
import com.example.pizzaordering.OrderDetailContract;

public class OrderHistory extends AppCompatActivity {

    private OrderAdapter mAdapter;
    private SQLiteDatabase mDb;
    TextView headingtv;
    Cursor cursor;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = getSharedPreferences("PIZZAAPP", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        headingtv = findViewById(R.id.headingtv);
        RecyclerView waitlistRecyclerView;
        waitlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_orders_list_view);
        waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        OrderDbHelper dbHelper = new OrderDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
        cursor = getAllOrders();
        headingtv.setText("Your Orders (" + cursor.getCount() + ")");
        mAdapter = new OrderAdapter(this, cursor);
        waitlistRecyclerView.setAdapter(mAdapter);
    }


    public void showorders() {
        cursor = getAllOrders();
        mAdapter.setmCursor(cursor);
        mAdapter.notifyDataSetChanged();
        headingtv.setText("Your Orders (" + cursor.getCount() + ")");
    }

    public void delete(final long orderid) {

        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete this order?");

        builder.setPositiveButton(android.R.string.yes, (dialog, which) -> {
            boolean option = deleteOrder(orderid);
            showorders();
            dialog.dismiss();
        });

        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog,
                                int which)
            {

                dialog.cancel();
            }

        });


    }

    private Cursor getAllOrders() {
        return mDb.query(
                OrderContract.OrderEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public String getToppings(long orderid) {
        Cursor cursor = mDb.query(
                OrderDetailContract.OrderDetailEntry.TABLE_NAME,
                null,
                OrderDetailContract.OrderDetailEntry.COLUMN_ORDER_ID + "=" + orderid,
                null,
                null,
                null,
                null
        );

        String top = "";
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                String name = cursor.getString(cursor.getColumnIndexOrThrow(OrderDetailContract.OrderDetailEntry.COLUMN_ORDER_ID));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow(OrderDetailContract.OrderDetailEntry.COLUMN_TOPPING_PRICE));

                top += name + "($" + price + "), ";
            }
        }
        cursor.close();

        return top;
    }


    private boolean deleteOrder(long id) {
        return mDb.delete(OrderContract.OrderEntry.TABLE_NAME, OrderContract.OrderEntry._ID + "=" + id, null) > 0;
    }

}