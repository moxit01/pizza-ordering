package com.example.pizzaordering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        Context context = getApplicationContext();

        boolean option = deleteOrder(orderid);
        showorders();
        Toast toast = Toast.makeText(context,"Deleted!", Toast.LENGTH_SHORT);
        toast.show();

    }

    public Cursor getAllOrders() {
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


    public boolean deleteOrder(long id) {
        return mDb.delete(OrderContract.OrderEntry.TABLE_NAME, OrderContract.OrderEntry._ID + "=" + id, null) > 0;
    }

}