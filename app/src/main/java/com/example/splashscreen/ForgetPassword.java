package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgetPassword extends AppCompatActivity {
    DatabaseReference root = FirebaseDatabase.getInstance().getReferenceFromUrl("https://splashscreen-69bdd-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        final EditText mobileET = findViewById(R.id.mobileET);
        final AppCompatButton SubmitBtn=findViewById(R.id.submitBtn);

        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Mobile = mobileET.getText().toString().trim();

                if(TextUtils.isEmpty(Mobile) || Mobile.length()<11)
                {
                    mobileET.setError("Valid Mobile number  is required");
                    return;

                }

                root.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //Check if phone number is not registered before
                        if(snapshot.hasChild(Mobile))
                        {

                            Intent intent = new Intent(ForgetPassword.this, OTPVerification.class);
                            intent.putExtra("mobile",Mobile);
                            intent.putExtra("whatToDo","updateData");
                            startActivity(intent);
                            finish();


                        }
                        else
                        {
                            Toast.makeText(ForgetPassword.this,"No user with this phone number",Toast.LENGTH_SHORT).show();
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
