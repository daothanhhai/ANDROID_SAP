package com.example.erp_tuyen.loginsap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_layoutgh extends AppCompatActivity {
    Button bttgghgh,btbkntgh,btbkntmgh,btctbkntgh,btaddkhsap;
    String IdUser,username,password,hoten,dvcs,macnnv,chucdanh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layoutgh);
        getSupportActionBar().setTitle("GIAO HÀNG");
        bttgghgh = (Button) findViewById(R.id.bttgghgh);
        btbkntgh = (Button) findViewById(R.id.btbkntgh);
        btbkntmgh = (Button) findViewById(R.id.btbkntghmoi);
        btctbkntgh = (Button) findViewById(R.id.btctbkntgh);
        btaddkhsap = (Button) findViewById(R.id.btaddkhsap);

        Intent intent=getIntent();
        IdUser=intent.getStringExtra("IdUser");
        username=intent.getStringExtra("USERNAME");
        password=intent.getStringExtra("PASSWORD");
        hoten=intent.getStringExtra("HOTEN");
        dvcs=intent.getStringExtra("Ma_Dvcs");
        macnnv=intent.getStringExtra("Ma_CbNv");
        chucdanh=intent.getStringExtra("Chuc_Danh");

        bttgghgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(activity_layoutgh.this,activity_xacnhantheodoigh.class);
                intent2.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                intent2.putExtra("Ma_Dvcs",dvcs);
                intent2.putExtra("Ma_CbNv",macnnv);
                intent2.putExtra("Chuc_Danh",chucdanh);
                intent2.putExtra("USERNAME",username);
                startActivity(intent2);
            }
        });

        btbkntgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent(activity_layoutgh.this,activity_dsbknoptiengh.class);
                intent3.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                intent3.putExtra("Ma_Dvcs",dvcs);
                intent3.putExtra("Ma_CbNv",macnnv);
                intent3.putExtra("Chuc_Danh",chucdanh);
                intent3.putExtra("USERNAME",username);
                startActivity(intent3);

            }
        });

        btbkntmgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4=new Intent(activity_layoutgh.this,activity_thunokhachhang.class);
                intent4.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                intent4.putExtra("Ma_Dvcs",dvcs);
                intent4.putExtra("USERNAME",username);
                intent4.putExtra("Ma_CbNv",macnnv);
                startActivity(intent4);
            }
        });

        btctbkntgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5=new Intent(activity_layoutgh.this,activity_chitietbknoptiengh.class);
                intent5.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                intent5.putExtra("Ma_Dvcs",dvcs);
                intent5.putExtra("USERNAME",username);
                intent5.putExtra("Ma_CbNv",macnnv);
                startActivity(intent5);
            }
        });

        btaddkhsap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6=new Intent(activity_layoutgh.this,activity_addkh.class);
//                intent5.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
//                intent5.putExtra("Ma_Dvcs",dvcs);
//                intent5.putExtra("USERNAME",username);
//                intent5.putExtra("Ma_CbNv",macnnv);
                startActivity(intent6);
            }
        });

    }
}