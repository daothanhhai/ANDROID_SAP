package com.example.erp_tuyen.loginsap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class activity_themsanpham extends AppCompatActivity {
    ImageView imageViewsanpham;
    EditText editTextmasanpham;
    Button buttonchonsanpha,buttonthemsanpham;
    private static final int IMAGE_PICK_CODE=1000;
    private static final int PERMISSION_CODE=1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themsanpham);
        anhxa();
        buttonchonsanpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED){
                        String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions,PERMISSION_CODE);

                    }
                    else {
                                pickImageFromGallery();

                    }
                }
                else {
                        pickImageFromGallery();
                }
            }
        });
    }
    private void pickImageFromGallery(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }
                else {
                    Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
                }
            }
        }

        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==RESULT_OK && requestCode==IMAGE_PICK_CODE){
            imageViewsanpham.setImageURI(data.getData());
        }
    }

    private void anhxa()
    {
        imageViewsanpham=(ImageView) findViewById(R.id.imagesanpham);
        editTextmasanpham=(EditText) findViewById(R.id.editTextmasanpham);
        buttonchonsanpha=(Button) findViewById(R.id.buttonchonsanpham);
        //buttonthemsanpham=(Button) findViewById(R.id.buttonthemsanpham);
    }
}
