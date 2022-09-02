package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LogIn extends AppCompatActivity {
    private boolean passwordShowing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        final EditText usernameET=findViewById(R.id.usernameET);
        final EditText passwordET=findViewById(R.id.passwordET);
        final TextView signUpBtn=findViewById(R.id.signUpBtn);
        final ImageView passwordIcon=findViewById(R.id.passwordIcon);
        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking if password is showing or not
               if(passwordShowing)
               {
                   passwordShowing=false;
                   passwordET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
                   passwordIcon.setImageResource(R.drawable.password_show1);
               }
               else
               {
                   passwordShowing=true;
                   passwordET.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
                   passwordIcon.setImageResource(R.drawable.password_hide);
               }
               //move the cursor at last of the text
                passwordET.setSelection(passwordET.length());

            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this,SignUp.class));
            }
        });


    }
}