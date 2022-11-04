package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private int progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to remove actionbar and make full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //**
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressbarId);
        Thread thread = new Thread(new  Runnable() {
            @Override
            public void run() {
               DoWork();
               gotoHomepage();
            }




        });
        thread.start();
    }
    public void DoWork() {

            for(progress=25; progress<=100;progress=progress+25)
            {     try {
                Thread.sleep(500);
                progressBar.setProgress(progress);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            }


    }
    public void gotoHomepage() {

        startActivity(new Intent(MainActivity.this,LogIn.class));



    }


}