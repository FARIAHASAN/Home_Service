package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.HashMap;

public class service_specialization extends AppCompatActivity {
    EditText pdf;
    Button submitPdf;
    private Spinner spinner;
    String Service,pdfName,pdfUri,getEmail,getMobile,getPassword , getFullName,getDescription="hello";
    //for image upload
    ImageView img;
    Button browse, upload;
    Uri filepath;
    Bitmap bitmap;




    ArrayAdapter<String> arrayAdapter;
    String[] service={"Senior Care","Baby Sitting","Sign Language","Special Child","Cylinder Provider",
            "PhysioTherapy","Diabetic and pressure measure"};
    //DatabaseReference root1 = FirebaseDatabase.getInstance().getReference("serviceProvider");
   StorageReference storageReference,store_image;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_specialization);
        pdf=findViewById(R.id.pdf);
        submitPdf=findViewById(R.id.submitpdf);
        //database
        storageReference = FirebaseStorage.getInstance().getReference();
        store_image = FirebaseStorage.getInstance().getReference();

        databaseReference=FirebaseDatabase.getInstance().getReference("serviceProvider");


        getEmail =getIntent().getStringExtra("email");
       getMobile =getIntent().getStringExtra("mobile");
         getPassword =getIntent().getStringExtra("password");
        getFullName =getIntent().getStringExtra("fullname");

    // for adding description
      TextInputLayout DescriptionLayout = findViewById(R.id.descriptionTL);
        TextInputEditText DescriptionEdit = findViewById(R.id.descriptionET);
     DescriptionEdit.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

         }

         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {
             getDescription= s.toString();
         }

         @Override
         public void afterTextChanged(Editable s) {

         }
     });




        //spinner work
        spinner=findViewById(R.id.user_spinner);
        arrayAdapter=new ArrayAdapter<String>(service_specialization.this, android.R.layout.simple_spinner_dropdown_item,service);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //   Toast.makeText(SignUp.this,"You selected"+parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                        Service=(String)parent.getItemAtPosition(position);


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

//for image
        img=(ImageView)findViewById(R.id.imageView);
        //upload=(Button)findViewById(R.id.upload);
        browse=(Button)findViewById(R.id.browse);
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Dexter.withActivity(service_specialization.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Please select Image"),2);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();

            }
        });


        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
               startActivityForResult(Intent.createChooser(intent,"PDF FILE SELECT"),1);



            }

        });


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            submitPdf.setEnabled(true);
            pdf.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/")+1));
            submitPdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadPDFFileFirebase(data.getData(),filepath);
                }


            });

        }
        if(requestCode==2 && resultCode==RESULT_OK)
        {
            filepath=data.getData();
            try
            {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
            }catch (Exception ex)
            {

            }
        }

    }
    private void uploadPDFFileFirebase(Uri data,Uri data2) {
        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setTitle("File is loading");
        progressDialog.show();
        //for image extension
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        StorageReference reference=storageReference.child("Uploads/"+System.currentTimeMillis()+".pdf");
        StorageReference reference2=store_image.child("Images/"+System.currentTimeMillis()+"."+mime.getExtensionFromMimeType(cr.getType(data2)));

        reference.putFile(data).addOnSuccessListener(
                new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask= taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isComplete());
                        Uri uri = uriTask.getResult();
                       pdfName=pdf.getText().toString();
                       pdfUri=uri.toString();
                        reference2.putFile(data2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> uriTask1= taskSnapshot.getStorage().getDownloadUrl();
                                while(!uriTask1.isComplete());
                                Uri uri1 = uriTask1.getResult();
                                String image = uri1.toString();
                                String key = databaseReference.push().getKey();
                                HashMap<String, String> userMap = new HashMap<>();


                                userMap.put("name",getFullName);
                                userMap.put("email", getEmail);
                                userMap.put("mobile", getMobile);
                                userMap.put("password", getPassword);
                                userMap.put("pdfName",pdfName);
                                userMap.put("pdfUri",pdfUri);
                                userMap.put("Image",image);
                                userMap.put("Description",getDescription);
                                userMap.put("ServiceType",Service);
                                databaseReference.child(getMobile).setValue(userMap);
                                Toast.makeText(service_specialization.this,"Details are uploaded",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();


                                //go to homeScreen
                                Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        });

                    }
                }
        ).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                Double progrss = (100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("File Uploaded"+progrss.intValue()+"%");


            }
        });
    }



//    //NEW CODE
//    ProgressDialog dialog;
//
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//
//            // Here we are initialising the progress dialog box
//            dialog = new ProgressDialog(this);
//            dialog.setMessage("Uploading");
//
//            // this will show message uploading
//            // while pdf is uploading
//            dialog.show();
//            imageuri = data.getData();
//            final String timestamp = "" + System.currentTimeMillis();
//            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
//            final String messagePushID = timestamp;
//            Toast.makeText(service_specialization.this, imageuri.toString(), Toast.LENGTH_SHORT).show();
//
//            // Here we are uploading the pdf in firebase storage with the name of current time
//            final StorageReference filepath = storageReference.child(messagePushID + "." + "pdf");
//            Toast.makeText(service_specialization.this, filepath.getName(), Toast.LENGTH_SHORT).show();
//            filepath.putFile(imageuri).continueWithTask(new Continuation() {
//                @Override
//                public Object then(@NonNull Task task) throws Exception {
//                    if (!task.isSuccessful()) {
//                        throw task.getException();
//                    }
//                    return filepath.getDownloadUrl();
//                }
//            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    if (task.isSuccessful()) {
//                        // After uploading is done it progress
//                        // dialog box will be dismissed
//                        dialog.dismiss();
//                        Uri uri = task.getResult();
//                        String myurl;
//                        myurl = uri.toString();
//                        Toast.makeText(service_specialization.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
//                    } else {
//                        dialog.dismiss();
//                        Toast.makeText(service_specialization.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//    }
}