package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class newOrder extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    String name;
    myorderadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
       // BOTTOM MENU

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


       getSupportFragmentManager().beginTransaction().replace(R.id.orwrapper,new fragmentNewOrder()).commit();


//        //session value
//        SessionManager sessionManager = new SessionManager(this);
//        HashMap<String,String> userDetails=sessionManager.getUserInfo();
//        name=SessionManager.KEY_NAME;
//        //recycleview
//        recyclerView=findViewById(R.id.recycleview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        FirebaseRecyclerOptions<orderModel> options =
//                new FirebaseRecyclerOptions.Builder<orderModel>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("orders").orderByChild("Customer_name").equalTo(name), orderModel.class)
//                        .build();
//        adapter= new myorderadapter(options);
//        recyclerView.setAdapter(adapter);

    }



}