package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class customer_profile extends AppCompatActivity {
    DatabaseReference root = FirebaseDatabase.getInstance().getReferenceFromUrl("https://splashscreen-69bdd-default-rtdb.firebaseio.com/");
    //dialog textview
    String  newEmail,newName,newGender,newDOB,gender_from_spinner;

    ArrayAdapter<String> arrayAdapter;
    String[] genderType={"Male","Female","Other"};
    private Spinner gender_spinner;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        //to remove actionbar and make full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        ImageView backpage = findViewById(R.id.backpage);
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //name,email and phone show using session
        TextView name = findViewById(R.id.name);
        TextView mobile = findViewById(R.id.mobile);
        TextView email = findViewById(R.id.email);
        TextView gender = findViewById(R.id.gender);
        TextView DOB = findViewById(R.id.dob);



        SessionManager sessionManager = new SessionManager(this);
        HashMap<String,String> userDetails=sessionManager.getUserInfo();
        //unique key
        String unique_phnNo= userDetails.get(SessionManager.KEY_MOBILE);
        //retrive gender and dob from database
        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                newGender =snapshot.child(unique_phnNo).child("gender").getValue(String.class);
                newDOB =snapshot.child(unique_phnNo).child("DateOfBirth").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //assigning values in textview
        name.setText(userDetails.get(SessionManager.KEY_NAME));
        email.setText(userDetails.get(SessionManager.KEY_EMAIL));
        gender.setText("newGender");
        DOB.setText(newDOB);

        //UPDATE NAME
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inflate the custom_dialog view
                View view = getLayoutInflater().inflate(R.layout.custom_dialog_name,null);

                AlertDialog.Builder builder= new AlertDialog.Builder(customer_profile.this);
                builder.setView(view);
                //builder.setTitle("Enter New Name:");
                builder.setCancelable(false);
                AlertDialog  dialog;
                newName=userDetails.get(SessionManager.KEY_NAME);
                EditText Ename = view.findViewById(R.id.newName);

                Ename.setText(name.getText());
                dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                Button update, cancel;
                update = view.findViewById(R.id.update);
                cancel = view.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();

                    }
                });
              update.setOnClickListener(new View.OnClickListener() {
                    @Override


                    public void onClick(View v) {
                       newName =Ename.getText().toString().trim();

                        if(TextUtils.isEmpty(newName))
                        {
                            Ename.setError("Name is required");
                            return;

                        }
                        //update user name code
                        else {
                            root.child("customer").child(unique_phnNo).child("name").setValue(newName);
                            sessionManager.updateName(newName);
                            name.setText(newName);


                            dialog.cancel();

                            Toast.makeText(customer_profile.this,"Your Name is updated",Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });

   //GENDER UPDATE
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inflate the custom_dialog view
                View view = getLayoutInflater().inflate(R.layout.custom_dialog_gender,null);
                //gender spinner work
                gender_spinner=findViewById(R.id.gendertype);
                arrayAdapter=new ArrayAdapter<String>(customer_profile.this, android.R.layout.simple_spinner_dropdown_item,genderType);
                gender_spinner.setAdapter(arrayAdapter);
                gender_spinner.setOnItemSelectedListener(
                        new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                //   Toast.makeText(SignUp.this,"You selected"+parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                                gender_from_spinner= (String) parent.getItemAtPosition(position);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        }
                );
                AlertDialog.Builder builder= new AlertDialog.Builder(customer_profile.this);
                builder.setView(view);
                //builder.setTitle("Enter New Name:");
                builder.setCancelable(false);
                AlertDialog  dialog;

                EditText Egender = view.findViewById(R.id.gendertype);

                Egender.setText(gender.getText());
                dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                Button update, cancel;
                update = view.findViewById(R.id.update);
                cancel = view.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();

                    }
                });
                update.setOnClickListener(new View.OnClickListener() {
                    @Override


                    public void onClick(View v) {


                        //update user name code


                            root.child("customer").child(unique_phnNo).child("name").setValue(newGender);
                            sessionManager.updateName(newName);
                            name.setText(newName);


                            dialog.cancel();

                            Toast.makeText(customer_profile.this,"Your Name is updated",Toast.LENGTH_SHORT).show();


                    }
                });

            }
        });

        //EMAIL UPDATE
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  newEmail=userDetails.get(SessionManager.KEY_EMAIL);
                //Inflate the custom_dialog view
                View view = getLayoutInflater().inflate(R.layout.custom_dialog_email,null);


                AlertDialog.Builder builder= new AlertDialog.Builder(customer_profile.this);
                builder.setView(view);
                //builder.setTitle("Enter New Name:");
                builder.setCancelable(false);
                AlertDialog  dialog;

                EditText EmailtobeChange = view.findViewById(R.id.newEmail);

                EmailtobeChange.setText(email.getText());
                dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                Button update, cancel;
                update = view.findViewById(R.id.update);
                cancel = view.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();

                    }
                });
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        newEmail =EmailtobeChange.getText().toString();
                        if(TextUtils.isEmpty(newEmail) || !(Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()))
                        {
                            EmailtobeChange.setError("Valid Email Address is required");
                            return;
                        }
                        //update user mail
                        else {

                            root.child("customer").child(unique_phnNo).child("email").setValue(newEmail);

                            sessionManager.updateMail(newEmail);


                           email.setText(newEmail);
                            dialog.cancel();

                            Toast.makeText(customer_profile.this,"Your Email is updated",Toast.LENGTH_SHORT).show();

                        }

                    }
                });


            }
        });


    }
}