package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPVerification extends AppCompatActivity {
    private final TextWatcher textWatcher =new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length()>0){
                if(selectedETPosition==0){
                    selectedETPosition=1;
                    showKeyboard(otpEt2);
                }
                else if(selectedETPosition==1){
                    selectedETPosition=2;
                    showKeyboard(otpEt3);
                }
                else if(selectedETPosition==2){
                    selectedETPosition=3;
                    showKeyboard(otpEt4);
                }
                else if(selectedETPosition==3){
                    selectedETPosition=4;
                    showKeyboard(otpEt5);
                }
                else if(selectedETPosition==4){
                    selectedETPosition=5;
                    showKeyboard(otpEt6);
                }
            }
        }
    };


    private EditText otpEt1,otpEt2,otpEt3,otpEt4,otpEt5,otpEt6;
    private TextView resendBtn;
    String getotpbackend;

    //true after every 60seconds
    private boolean resendEnable=false;

    //resend times in seconds
    private int resendTime = 60;

    private int selectedETPosition=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        otpEt1=findViewById(R.id.otpET1);
        otpEt2=findViewById(R.id.otpET2);
        otpEt3=findViewById(R.id.otpET3);
        otpEt4=findViewById(R.id.otpET4);
        otpEt5=findViewById(R.id.otpET5);
        otpEt6=findViewById(R.id.otpET6);

        resendBtn=findViewById(R.id.resendBtn);
        final Button verifyBtn =findViewById(R.id.verifyBtn);
        final TextView otpEmail =findViewById(R.id.otpEmail);
        final TextView otpMobile =findViewById(R.id.otpMobile);


        //getting email and mobile from register activity through intent
        final String getEmail =getIntent().getStringExtra("email");
        final String getMobile =getIntent().getStringExtra("mobile");
        getotpbackend=getIntent().getStringExtra("backendotp");


        //setting email and mobile to Textview
        otpEmail.setText(getEmail);
        otpMobile.setText(getMobile);

        otpEt1.addTextChangedListener(textWatcher);
        otpEt2.addTextChangedListener(textWatcher);
        otpEt3.addTextChangedListener(textWatcher);
        otpEt4.addTextChangedListener(textWatcher);
        otpEt5.addTextChangedListener(textWatcher);
        otpEt6.addTextChangedListener(textWatcher);

        //default open keyboard at otpEt1
        showKeyboard(otpEt1);

        //start resend count down timer
        startCountDownTimer();
        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resendEnable){


                    //start new resend count down timer
                    startCountDownTimer();
                    //handle your resend code here
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+88"+ getMobile, 60, TimeUnit.SECONDS, OTPVerification.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    Toast.makeText(OTPVerification.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                 getotpbackend=newbackendotp;
                                    Toast.makeText(OTPVerification.this,"OTP sent successfully",Toast.LENGTH_SHORT).show();
                                }
                            }

                    );

                }
            }
        });

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String generateOtp=otpEt1.getText().toString()+otpEt2.getText().toString()+otpEt3.getText().toString()+otpEt4.getText().toString()+otpEt5.getText().toString()+otpEt6.getText().toString();
                if(generateOtp.length()==6){
                    //handle your otp varification here
                    PhoneAuthCredential  phoneAuthCredential = PhoneAuthProvider.getCredential(getotpbackend,generateOtp);
                    FirebaseAuth.getInstance().signInWithCredential( phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {    Toast.makeText(OTPVerification.this,"OTP  verified",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(OTPVerification.this,"Enter the correct OTP",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
                else {
                    Toast.makeText(OTPVerification.this,"Please enter all numbers",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void showKeyboard(EditText otpET){
        otpET.requestFocus();

        InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpET, InputMethodManager.SHOW_IMPLICIT);
    }

    private void startCountDownTimer(){

        resendEnable=false;
        resendBtn.setTextColor(Color.parseColor("#99000000"));

       // new CountDownTimer(resendTime*60,100){

            //public void onTick(long mill)
        //}
        new CountDownTimer(resendTime*1000,1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {
                resendBtn.setText("Resend Code ("+(millisUntilFinished/1000)+")");
            }

            @Override
            public void onFinish() {

                resendEnable=true;
                resendBtn.setText("Resend Code");
                resendBtn.setTextColor(getResources().getColor(R.color.primary));

            }
        }.start();
    }



    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL) {

               if (selectedETPosition == 5){
                selectedETPosition = 4;
                showKeyboard(otpEt5);
            }

              else if (selectedETPosition == 4){
                selectedETPosition = 3;
                showKeyboard(otpEt4);
            }
            else if (selectedETPosition == 3){
                selectedETPosition = 2;
            showKeyboard(otpEt3);
                     }
        else if (selectedETPosition == 2) {
            selectedETPosition = 1;
            showKeyboard(otpEt2);
              }
        else if (selectedETPosition == 1) {
            selectedETPosition = 0;
            showKeyboard(otpEt1);
        }
        return true;
    }
    else {
        return super.onKeyUp(keyCode, event);
    }


    }
}