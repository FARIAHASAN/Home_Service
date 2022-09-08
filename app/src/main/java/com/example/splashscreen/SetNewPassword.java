package com.example.splashscreen;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class SetNewPassword extends AppCompatActivity {
    private boolean passwordShowing = false;
    private boolean conPasswordShowing = false;

    FirebaseFirestore fStore ;
    String userId;
    FirebaseAuth fAuth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        final EditText  password = findViewById(R.id.passwordET);
        final EditText  conPassword = findViewById(R.id.conPassET);
        final ImageView passwordIcon = findViewById(R.id.passwordIcon);
        final ImageView  conPasswordIcon = findViewById(R.id.conPasswordIcon);
        final AppCompatButton UpdatePassSubmit = findViewById(R.id.updatePassSubmit);
        final String Mobile = getIntent().getStringExtra("mobile");



        fAuth=FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking if password is showing or not
                if(passwordShowing)
                {
                    passwordShowing=false;
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
                    passwordIcon.setImageResource(R.drawable.password_show1);
                }
                else
                {
                    passwordShowing=true;
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
                    passwordIcon.setImageResource(R.drawable.password_hide);
                }
                //move the cursor at last of the text
                password.setSelection(password.length());

            }
        });
        conPasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking if password is showing or not
                if(conPasswordShowing)
                {
                    conPasswordShowing=false;
                    conPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
                    conPasswordIcon.setImageResource(R.drawable.password_show1);
                }
                else
                {
                    conPasswordShowing=true;
                    conPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
                    conPasswordIcon.setImageResource(R.drawable.password_hide);
                }
                //move the cursor at last of the text
                conPassword.setSelection( conPassword.length());

            }
        });
        UpdatePassSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Password= password.getText().toString().trim();
                String ConPassword= conPassword.getText().toString().trim();
                if(TextUtils.isEmpty(Password))
                {
                    password.setError("Password is required");
                    return;

                }
                if(TextUtils.isEmpty(ConPassword))
                {
                    conPassword.setError("Confirm Password is required");
                    return;
                }
                if(Password.length()<6)
                {
                    password.setError("Password has less than 6 characters");
                    return;
                }

                if(!Password.equals(ConPassword))
                {

                    Toast.makeText(SetNewPassword.this, "Password and Confirm password doesn't match", Toast.LENGTH_SHORT).show();
                    return;



                }
                DatabaseReference root = FirebaseDatabase.getInstance().getReference("users");
                root.child(Mobile).child("password").setValue(ConPassword);
                Toast.makeText(SetNewPassword.this, "Password has been updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SetNewPassword.this, HomeScreen.class);
                startActivity(intent);
                finish();



            }
        });


    }
}