package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class LogIn extends AppCompatActivity {
    private boolean passwordShowing = false;

    DatabaseReference root = FirebaseDatabase.getInstance().getReferenceFromUrl("https://splashscreen-69bdd-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        final EditText mobileET = findViewById(R.id.mobileET);
        final EditText passwordET=findViewById(R.id.passwordET);
        final AppCompatButton SignInBtn=findViewById(R.id.signInBtn);
        final ImageView passwordIcon=findViewById(R.id.passwordIcon);
        final TextView ForgetPassword = findViewById(R.id.forgetPasswordBtn);
        final TextView SignUpBtn = findViewById(R.id.signUpBtn);
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
        ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, ForgetPassword.class);
                startActivity(intent);


            }
        });
        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, SignUp.class);
                startActivity(intent);
            }
        });
        SignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Mobile = mobileET.getText().toString().trim();
                String Password = passwordET.getText().toString().trim();
                if(TextUtils.isEmpty(Mobile) || Mobile.length()<11)
                {
                    mobileET.setError("Valid Mobile number  is required");
                    return;

                }
                if(TextUtils.isEmpty(Password))
                {
                    passwordET.setError("Password is required");
                    return;

                }
                root.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //Check if phone number is not registered before
                        if(snapshot.hasChild(Mobile))
                        {

                            final String getPassword =snapshot.child(Mobile).child("password").getValue(String.class);
                            if(getPassword.equals(Password)) {

                                Toast.makeText(LogIn.this, "You have logged in", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LogIn.this, HomeScreen.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(LogIn.this,"Please Enter correct password.",Toast.LENGTH_SHORT).show();
                            }





                        }
                        else
                        {
                            Toast.makeText(LogIn.this,"You don't have an account.please Register first",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



    }
}