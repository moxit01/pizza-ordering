package com.example.pizzaordering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView toppinglv;
    public int baseprice = 15;
    TextView total,choosetoppingtv,basepricetv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        choosetoppingtv = findViewById(R.id.choosetoppingtv);
        basepricetv=findViewById(R.id.basepricetv);
        total = findViewById(R.id.total);
        total.setText("TOTAL: $" + baseprice);



        toppinglv = findViewById(R.id.toppinglist);

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



}