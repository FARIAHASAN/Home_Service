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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
    private Spinner spinner;

    ArrayAdapter<String> arrayAdapter;
    String[] usertype={"Customer","Service provider"};
    String userType="Customer";

    DatabaseReference root = FirebaseDatabase.getInstance().getReferenceFromUrl("https://splashscreen-69bdd-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        //spinner work
        spinner=findViewById(R.id.user_spinner);
        arrayAdapter=new ArrayAdapter<String>(LogIn.this, android.R.layout.simple_spinner_dropdown_item,usertype);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //   Toast.makeText(SignUp.this,"You selected"+parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                        userType= (String) parent.getItemAtPosition(position);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

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
                String whichUserType;
                if(userType.equals("Customer"))
                {
                    whichUserType = "customer";

                }
                else
                {
                    whichUserType = "serviceProvider";
                }
                root.child(whichUserType).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //Check if phone number is not registered before
                        if(snapshot.hasChild(Mobile))
                        {

                            final String getPassword =snapshot.child(Mobile).child("password").getValue(String.class);
                            final String getMobile =snapshot.child(Mobile).child("mobile").getValue(String.class);
                            final String getName =snapshot.child(Mobile).child("name").getValue(String.class);
                            final String getEmail =snapshot.child(Mobile).child("email").getValue(String.class);
                            if(getPassword.equals(Password)) {
                                //session create
                                SessionManager session= new SessionManager(LogIn.this);
                                session.createLoginSession(getName,getMobile,getEmail);


                                Toast.makeText(LogIn.this, "Hello "+getName+" !", Toast.LENGTH_SHORT).show();

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