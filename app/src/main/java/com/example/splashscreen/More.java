package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

public class More extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);


        //to remove actionbar and make full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //BOTTOM MENU

        bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.more);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.more:

                                return true;
                            case R.id.orders:
                                startActivity(new Intent(getApplicationContext(),Orders.class));
                                overridePendingTransition(0,0);
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


        //name and phone show using session
        TextView name = findViewById(R.id.name);
        TextView mobile = findViewById(R.id.mobile);
       AppCompatButton logOutBtn=findViewById(R.id.logout);
        SessionManager sessionManager = new SessionManager(this);
        HashMap<String,String>userDetails=sessionManager.getUserInfo();
        name.setText(userDetails.get(SessionManager.KEY_NAME));
        mobile.setText("+88"+userDetails.get(SessionManager.KEY_MOBILE));
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.LogoutUser();
                name.setText("");
                mobile.setText("");
                Toast.makeText(More.this, "You are logged out", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(More.this, LogIn.class);
                startActivity(intent);
            }
        });




    }
}