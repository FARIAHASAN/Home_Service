package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Orders extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
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
    }
}