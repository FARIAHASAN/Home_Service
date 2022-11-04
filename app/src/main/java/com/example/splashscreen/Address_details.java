package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

public class Address_details extends AppCompatActivity {
   String Address,Flat,House,ServicePrice,ProviderName, ProviderPhone, ServiceType ,OptionalInstruction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        ImageView backpage = findViewById(R.id.backpage);
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
       Address = getIntent().getStringExtra("address");
        ServicePrice = getIntent().getStringExtra("ServicePrice");
        ProviderName  = getIntent().getStringExtra("ProviderName");
        ProviderPhone  = getIntent().getStringExtra("ProviderPhone");
     ServiceType  = getIntent().getStringExtra("ServiceType");

        //name and phone show using session
        TextView name = findViewById(R.id.name);
        TextView mobile = findViewById(R.id.mobile);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String,String> userDetails=sessionManager.getUserInfo();
        name.setText(userDetails.get(SessionManager.KEY_NAME));

        mobile.setText("+88"+userDetails.get(SessionManager.KEY_MOBILE));



        TextView AddressField = findViewById(R.id.addressfield);
        AddressField.setText(Address);
        TextInputEditText flat = findViewById(R.id.flatET);
        TextInputEditText house= findViewById(R.id.houseET);
        TextInputEditText OptionalIns= findViewById(R.id.optionalInsET);
        flat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Flat= s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
      house.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               House= s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
OptionalIns.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        OptionalInstruction=s.toString();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
});
      AppCompatButton btn = findViewById(R.id.continue_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Address_details.this, OrderDetails.class);
                intent.putExtra("address",Address);
                intent.putExtra("flat",Flat);
                intent.putExtra("house",House);
                intent.putExtra("ServicePrice",ServicePrice);
                intent.putExtra("ProviderName", ProviderName);
                intent.putExtra("ProviderPhone",  ProviderPhone );
                intent.putExtra("ServiceType", ServiceType);
                intent.putExtra("OptionalInstruction",OptionalInstruction);

                startActivity(intent);

            }
        });



    }
}