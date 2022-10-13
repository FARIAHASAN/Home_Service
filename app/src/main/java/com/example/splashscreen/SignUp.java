package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {
    private Spinner spinner;

    ArrayAdapter<String>arrayAdapter;
    private boolean passwordShowing = false;
    private boolean conPasswordShowing = false;
    String[] usertype={"Customer","Service provider"};

    FirebaseFirestore fStore ;
    String userId,userType="Customer";
    FirebaseAuth fAuth;


    DatabaseReference root = FirebaseDatabase.getInstance().getReferenceFromUrl("https://splashscreen-69bdd-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        //spinner work
        spinner=findViewById(R.id.user_spinner);
        arrayAdapter=new ArrayAdapter<String>(SignUp.this, android.R.layout.simple_spinner_dropdown_item,usertype);
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
        final EditText email = findViewById(R.id.emailET);
        final EditText mobile = findViewById(R.id.mobileET);
        final EditText  password = findViewById(R.id.passwordET);
        final EditText  conPassword = findViewById(R.id.conPassET);
        final ImageView passwordIcon = findViewById(R.id.passwordIcon);
        final ImageView  conPasswordIcon = findViewById(R.id.conPasswordIcon);
        final AppCompatButton signUpBtn = findViewById(R.id.signUpBtn);
        final TextView signInBtn = findViewById(R.id.signInBtn);
        final TextView fullName = findViewById(R.id.fullnameET);


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






        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final String Email= email.getText().toString().trim();
               String Password= password.getText().toString().trim();
               String ConPassword= conPassword.getText().toString().trim();
               String FullName = fullName.getText().toString().trim();
               String Mobile = mobile.getText().toString().trim();


                if(TextUtils.isEmpty(FullName))
                {
                    fullName.setError("FullName is required");
                    return;

                }
                if(TextUtils.isEmpty(Email) || !(Patterns.EMAIL_ADDRESS.matcher(Email).matches()))
                {
                    email.setError("Valid Email Address is required");
                    return;
                }

                if(TextUtils.isEmpty(Mobile) || Mobile.length()<11)
                {
                   mobile.setError("Valid Mobile number  is required");
                    return;

                }
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

                        Toast.makeText(SignUp.this, "Password and Confirm password doesn't match", Toast.LENGTH_SHORT).show();
                        return;



                }
                if(!mobile.getText().toString().trim().isEmpty()) {
                    if(mobile.getText().toString().trim().length()==11) {
                        //if(fAuth.getCurrentUser()!=null){
                           // startActivity(new Intent(getApplicationContext(),HomeScreen.class));
                           // finish();
                          // Toast.makeText(SignUp.this,"User already exists",Toast.LENGTH_SHORT).show();
                          // return;
                       // }
                         if(userType.equals("Customer")) {
                             root.child("customer").addListenerForSingleValueEvent(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                                     //Check if phone number is not registered before
                                     if (snapshot.hasChild(Mobile)) {
                                         Toast.makeText(SignUp.this, "User already exists", Toast.LENGTH_SHORT).show();

                                     } else {
                                         PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                                 "+88" + mobile.getText().toString(), 60, TimeUnit.SECONDS, SignUp.this,
                                                 new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                                     @Override
                                                     public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                                     }

                                                     @Override
                                                     public void onVerificationFailed(@NonNull FirebaseException e) {
                                                         Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                     }

                                                     @Override
                                                     public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {


                                                         //opening OTP Varification activity along with mobile and Email
                                                         Intent intent = new Intent(SignUp.this, OTPVerification.class);
                                                         intent.putExtra("mobile", Mobile);
                                                         intent.putExtra("email", Email);
                                                         intent.putExtra("password", Password);
                                                         intent.putExtra("fullname", FullName);
                                                         intent.putExtra("backendotp", backendotp);
                                                         intent.putExtra("whatToDo", "nothing");
                                                         intent.putExtra("userType", userType);


                                                         startActivity(intent);

                                                     }
                                                 }

                                         );


                                         final String getMobileTXT = mobile.getText().toString();
                                         final String getEmailTXT = email.getText().toString();
                                     }
                                 }

                                 @Override
                                 public void onCancelled(@NonNull DatabaseError error) {

                                 }
                             });
                         }

                        //ai porjonto
                        else
                         {
                             root.child("serviceProvider").addListenerForSingleValueEvent(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                                     //Check if phone number is not registered before
                                     if (snapshot.hasChild(Mobile)) {
                                         Toast.makeText(SignUp.this, "User already exists", Toast.LENGTH_SHORT).show();

                                     } else {
//                                         PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                                                 "+88" + mobile.getText().toString(), 60, TimeUnit.SECONDS, SignUp.this,
//                                                 new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                                                     @Override
//                                                     public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//
//                                                     }
//
//                                                     @Override
//                                                     public void onVerificationFailed(@NonNull FirebaseException e) {
//                                                         Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                                                     }
//
//                                                     @Override
//                                                     public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//
//
//
//                                                               //opening OTP Varification activity along with mobile and Email
//                                                               Intent intent = new Intent(SignUp.this, OTPVerification.class);
//                                                               intent.putExtra("mobile", Mobile);
//                                                               intent.putExtra("email", Email);
//                                                               intent.putExtra("password", Password);
//                                                               intent.putExtra("fullname", FullName);
//                                                               intent.putExtra("backendotp", backendotp);
//                                                               intent.putExtra("whatToDo", "nothing");
//                                                               intent.putExtra("userType", userType);
//
//
//                                                               startActivity(intent);
//
//
//                                                     }
//                                                 }
//
//                                         );


                                         Intent intent = new Intent(SignUp.this, service_specialization.class);
                                         intent.putExtra("mobile", Mobile);
                                         intent.putExtra("email",  Email);
                                         intent.putExtra("password",  Password);
                                         intent.putExtra("fullname", FullName);

                                         startActivity(intent);

                                         final String getMobileTXT = mobile.getText().toString();
                                         final String getEmailTXT = email.getText().toString();
                                     }
                                 }

                                 @Override
                                 public void onCancelled(@NonNull DatabaseError error) {

                                 }
                             });

                         }




                    }
                    //else
                       // Toast.makeText(SignUp.this,"Please enter correct number",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(SignUp.this,"Please enter Mobile number",Toast.LENGTH_SHORT).show();

            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   //finish();
                Intent intent = new Intent(SignUp.this, LogIn.class);
                startActivity(intent);
            }
        });

    }
}