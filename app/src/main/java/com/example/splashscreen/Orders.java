package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Orders extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    String name;
    myorderadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        //BOTTOM MENU

        bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.orders);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.more:
                                startActivity(new Intent(getApplicationContext(),More.class));
                                overridePendingTransition(0,0);
                                return true;
                            case R.id.orders:

                                return true;
                            case R.id.home:
                                startActivity(new Intent(getApplicationContext(),HomeScreen.class));
                                overridePendingTransition(0,0);
                                return true;
                        }
                        return false;
                    }
                }
        );


        getSupportFragmentManager().beginTransaction().replace(R.id.orwrapper,new order_fragment()).commit();

    }



}