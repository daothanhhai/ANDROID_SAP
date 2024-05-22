package com.example.erp_tuyen.loginsap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class activity_dsbknoptiengh extends AppCompatActivity {
    ListView lvdsbkntgh;
    ArrayList<DsBKNopTienGH> arrayDsBKnoptiengh;
    DsBkNopTienGHAdapter adapter;
    String IdUser,dvcs,username,macnnv;
    ResultSet resultSet;
    Button btcndsbkntgh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsbknoptiengh);
        getSupportActionBar().setTitle("Bảng kê nộp tiền mặt");

        Intent intent=getIntent();
        IdUser=intent.getStringExtra("IdUser");
        dvcs=intent.getStringExtra("Ma_Dvcs");
        username=intent.getStringExtra("USERNAME");
        macnnv=intent.getStringExtra("Ma_CbNv");
        lvdsbkntgh = (ListView) findViewById(R.id.lvdsbkntgh);
        //btcndsbkntgh = (Button) findViewById(R.id.btcapnhatbkntgh);

        arrayDsBKnoptiengh=new ArrayList<>();
        adapter=new DsBkNopTienGHAdapter(activity_dsbknoptiengh.this,R.layout.dong_dsbknoptiengh,arrayDsBKnoptiengh);
        lvdsbkntgh.setAdapter(adapter);

        try {
            selectbkntgh();
            lvdsbkntgh.clearFocus();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        lvdsbkntgh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                String sttbk;
                sttbk= String.valueOf(arrayDsBKnoptiengh.get(i).getStt());

                Intent intent6=new Intent(activity_dsbknoptiengh.this,activity_thunokhachhang.class);
                intent6.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                intent6.putExtra("Ma_Dvcs",dvcs);
                intent6.putExtra("Sttbk",sttbk);
                intent6.putExtra("USERNAME",username);
                intent6.putExtra("Ma_CbNv",macnnv);
                startActivity(intent6);
            }
        });



    }

    public void selectbkntgh() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
            //String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());

            PreparedStatement statement = con.prepareStatement("EXEC usp_danhsachBKNT_Android '"+ dvcs +"','"+ IdUser +"' " );
            resultSet = statement.executeQuery();
            arrayDsBKnoptiengh.clear();

            for(int i = 0; i<=resultSet.getRow();i++ )
            {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String stt=     resultSet.getString("Stt");
                    String ngayct = resultSet.getString("Ngay_Ct");
                    ngayct=ngayct.substring(0,10);
                    String nguoilap = resultSet.getString("Nguoi_Lap");
                    String tongtien = resultSet.getString("Tong_Tien");

                    arrayDsBKnoptiengh.add(new DsBKNopTienGH(stt,ngayct,nguoilap,tongtien));

                }
            }

            adapter.notifyDataSetChanged();
            con.close();
        }
    }
}