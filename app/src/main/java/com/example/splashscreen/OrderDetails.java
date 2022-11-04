package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCCustomerInfoInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCEMITransactionInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCProductInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCShipmentInfoInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization;
import com.sslwireless.sslcommerzlibrary.model.response.SSLCTransactionInfoModel;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCCurrencyType;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCSdkType;
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz;
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.SSLCTransactionResponseListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class OrderDetails extends AppCompatActivity implements SSLCTransactionResponseListener {

    TextView success,fail,cancel ;
    String Address,Flat,House,ServicePrice,customer_name,customer_Phone,ProviderName, ProviderPhone, ServiceType,OptionalInstruction;
    int price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        success=findViewById(R.id.success);
       fail=findViewById(R.id.fail);
        cancel=findViewById(R.id.cancel);
// order infos

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String,String> userDetails=sessionManager.getUserInfo();
      customer_name=  userDetails.get(SessionManager.KEY_NAME);
      customer_Phone=userDetails.get(SessionManager.KEY_MOBILE);

 Address = getIntent().getStringExtra("address");
 ServicePrice = getIntent().getStringExtra("ServicePrice");
        ProviderName  = getIntent().getStringExtra("ProviderName");
        ProviderPhone  = getIntent().getStringExtra("ProviderPhone");
        ServiceType  = getIntent().getStringExtra("ServiceType");
        OptionalInstruction  = getIntent().getStringExtra("OptionalInstruction");


 price =Integer.parseInt( ServicePrice);
 Flat = getIntent().getStringExtra("flat");
 House = getIntent().getStringExtra("house");


        final SSLCommerzInitialization sslCommerzInitialization = new SSLCommerzInitialization ("food62069868707c8","food62069868707c8@ssl", price, SSLCCurrencyType.BDT,"", "yourProductType", SSLCSdkType.TESTBOX);
        final SSLCCustomerInfoInitializer customerInfoInitializer = new SSLCCustomerInfoInitializer("customer name", "customer email",
                Address, "dhaka", "1214", "Bangladesh", "phoneNumber");
        final SSLCProductInitializer productInitializer = new SSLCProductInitializer ("food", "food",
                new SSLCProductInitializer.ProductProfile.TravelVertical("Travel", "10",
                        "A", "12", "Dhk-Syl"));
        final SSLCShipmentInfoInitializer shipmentInfoInitializer = new SSLCShipmentInfoInitializer ("Courier",
                2, new SSLCShipmentInfoInitializer.ShipmentDetails("AA","Address 1",
                "Dhaka","1000","BD"));
        //final SSLCEMITransactionInitializer emiTransactionInitializer = new SSLCEMITransactionInitializer(1);
        IntegrateSSLCommerz
                .getInstance(OrderDetails.this)
                .addSSLCommerzInitialization(sslCommerzInitialization)
               .addCustomerInfoInitializer(customerInfoInitializer)
                .addProductInitializer(productInitializer)
                .buildApiCall(this);
        AppCompatButton btn = findViewById(R.id.backtohome);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetails.this, HomeScreen.class);

                startActivity(intent);
            }
        });





    }

    @Override
    public void transactionSuccess(SSLCTransactionInfoModel sslcTransactionInfoModel) {

          success.setText(customer_name+"Your transaction is Successful!");
        //firebase database

        DatabaseReference root = FirebaseDatabase.getInstance().getReference("orders");
        HashMap<String, String> userMap = new HashMap<>();

        userMap.put("Address",Address);
        userMap.put("Customer_name", customer_name);
        userMap.put("Customer_Phone", customer_Phone);
        userMap.put("ServicePrice", ServicePrice);
        userMap.put("Flat",Flat);
        userMap.put("House",House);
        userMap.put("ProviderName",ProviderName);
        userMap.put("ProviderPhone",ProviderPhone);
        userMap.put("ServiceType",ServiceType);
        userMap.put("OptionalInstruction",OptionalInstruction );
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String currentTime = sdf.format(new Date());
        String key = ProviderName+currentTime;
        root.child(key).setValue(userMap);



        //success.setText(sslcTransactionInfoModel.getAPIConnect()+"....."+ServicePrice);
        //Toast.makeText(OrderDetails.this,Flat+"  "+House,Toast.LENGTH_SHORT).show();





    }

    @Override
    public void transactionFail(String s) {
        fail.setText(s);

    }

    @Override
    public void merchantValidationError(String s) {
       cancel.setText(s);
    }
}