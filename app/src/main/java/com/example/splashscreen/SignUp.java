package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {
    private boolean passwordShowing = false;
    private boolean conPasswordShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        final EditText email = findViewById(R.id.emailET);
        final EditText mobile = findViewById(R.id.mobileET);
        final EditText  password = findViewById(R.id.passwordET);
        final EditText  conPassword = findViewById(R.id.conPassET);
        final ImageView passwordIcon = findViewById(R.id.passwordIcon);
        final ImageView  conPasswordIcon = findViewById(R.id.conPasswordIcon);
        final AppCompatButton signUpBtn = findViewById(R.id.signUpBtn);
        final TextView signInBtn = findViewById(R.id.signInBtn);

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
                if(!mobile.getText().toString().trim().isEmpty()) {
                    if(mobile.getText().toString().trim().length()==11) {
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+88"+ mobile.getText().toString(), 60, TimeUnit.SECONDS, SignUp.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                              Toast.makeText(SignUp.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                        //opening OTP Varification activity along with mobile and Email
                                        Intent intent = new Intent(SignUp.this, OTPVerification.class);
                                        intent.putExtra("mobile", mobile.getText().toString());
                                        intent.putExtra("backendotp",backendotp);

                                        startActivity(intent);
                                    }
                                }

                        );


                        final String getMobileTXT = mobile.getText().toString();
                        final String getEmailTXT = email.getText().toString();


                    }
                    else
                        Toast.makeText(SignUp.this,"Please enter correct number",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(SignUp.this,"Please enter Mobile number",Toast.LENGTH_SHORT).show();

            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   finish();
            }
        });

    }
}