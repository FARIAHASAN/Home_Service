package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.android.material.navigation.NavigationView;

public class HomeScreen extends AppCompatActivity {
    DrawerLayout drawerLayout ;
    NavigationView navigationView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        //  Sidemenu
       // setContentView(toolbar);
        RelativeLayout seniorId = findViewById(R.id.Seniorid);
        seniorId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(HomeScreen.this,SeniorCarePage.class);
                startActivity(intent);

            }
        });


        //to remove actionbar and make full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //**
        //ANOTHER WAY
               ActionBar actionbar = getSupportActionBar();
              actionbar.show();
               actionbar.setDisplayShowTitleEnabled(false);
        //**
       // change action_bar colour
      //  actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.main_colour)));

    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.manu_layout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if (id==R.id.login_menu)
        {
            Intent intent= new Intent(HomeScreen.this,LogIn.class);
            startActivity(intent);

        }
        return true;
    }

}