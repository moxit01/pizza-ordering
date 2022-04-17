package com.example.pizzaordering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView toppinglv;
    ArrayList<Toppings> toppinglist;
    ArrayList<Boolean> selectedToppings;
    private SQLiteDatabase mDb;
    public int baseprice = 15;
    TextView total,choosetoppingtv,basepricetv;
    ToppingAdapter adapter;
    AlertDialog.Builder builder1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = getSharedPreferences("PIZZAAPP", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toppinglist = new ArrayList<>();
        selectedToppings = new ArrayList<>();
        choosetoppingtv = findViewById(R.id.choosetoppingtv);
        basepricetv=findViewById(R.id.basepricetv);
        total = findViewById(R.id.total);
        total.setText("TOTAL: $" + baseprice);
        toppinglist.add(new Toppings("Extra Cheese", 5));
        toppinglist.add(new Toppings("Peppers", 4));
        toppinglist.add(new Toppings("Onions", 3));
        toppinglist.add(new Toppings("broccoli", 8));
        toppinglist.add(new Toppings("Black olives", 4));
        toppinglist.add(new Toppings("Gorgonzola", 9));
        toppinglist.add(new Toppings("capsicum", 3));
        toppinglist.add(new Toppings("Pepperoni", 6));
        toppinglist.add(new Toppings("Extra cheese", 5));
        toppinglist.add(new Toppings("melon",10));

        selectedToppings.add(false);
        selectedToppings.add(false);
        selectedToppings.add(false);
        selectedToppings.add(false);
        selectedToppings.add(false);
        selectedToppings.add(false);
        selectedToppings.add(false);
        selectedToppings.add(false);
        selectedToppings.add(false);


        toppinglv = findViewById(R.id.toppinglist);
        adapter = new ToppingAdapter(toppinglist, selectedToppings, this);
        toppinglv.setAdapter(adapter);

        OrderDbHelper dbHelper = new OrderDbHelper(this);
        mDb = dbHelper.getWritableDatabase();


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.orders) {
            startActivity(new Intent(getApplicationContext(), OrderHistory.class));
        }
        return super.onOptionsItemSelected(item);

    }

    private void PlaceOrder(int total) {
        ContentValues cv = new ContentValues();
        cv.put(OrderContract.OrderEntry.COLUMN_TOTAL, total);
        long orderid = mDb.insert(OrderContract.OrderEntry.TABLE_NAME, null, cv);

        for (int i = 0; i < selectedToppings.size(); i++) {
            ContentValues cv1 = new ContentValues();
            cv.put(OrderDetailContract.OrderDetailEntry.COLUMN_TOPPING_NAME, toppinglist.get(i).getName());
            cv.put(OrderDetailContract.OrderDetailEntry.COLUMN_TOPPING_PRICE, toppinglist.get(i).getPrice());
            cv.put(OrderDetailContract.OrderDetailEntry.COLUMN_ORDER_ID, orderid);
            long orderdetailid = mDb.insert(OrderDetailContract.OrderDetailEntry.TABLE_NAME, null, cv);
        }


        builder1.setTitle("Confirmation");
        builder1.setMessage("Order Placed successfully?");

        builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                refresh();
                dialog.dismiss();
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void updateBasePrice() {
        total.setText("TOTAL: $" + baseprice);
    }

    public void placeOrderLogic(View view) {
        PlaceOrder(baseprice);
    }

    public void refresh() {

        adapter.notifyDataSetChanged();
        baseprice = 15;
        total.setText("TOTAL: $" + baseprice);
    }


}


