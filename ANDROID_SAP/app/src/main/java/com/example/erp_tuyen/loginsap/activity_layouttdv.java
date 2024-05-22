package com.example.erp_tuyen.loginsap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class activity_layouttdv extends AppCompatActivity {
    TextView tvdttntdv,tvdhmtdv,tvdhhtdv,tvdhttdv;
    String IdUser,dvcs,dvcs1,makho,soseri,username, macbnv;
    ResultSet resultSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layouttdv);
        getSupportActionBar().setTitle("TDV");
        tvdttntdv = (TextView) findViewById(R.id.tvdttheongaytdv);
        tvdhmtdv = (TextView) findViewById(R.id.tvdonhangmoitdv);

        Intent intent=getIntent();
        IdUser=intent.getStringExtra("IdUser");
        dvcs=intent.getStringExtra("Ma_Dvcs");
        username = intent.getStringExtra("USERNAME");
        macbnv = intent.getStringExtra("Ma_CbNv");

        taothongtinCN();

        try {
            selecttongdoanhthu();
            selectdonhangmoi();
            selectdonhanghuy();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void selecttongdoanhthu() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


            CallableStatement cstmt = con.prepareCall("{call usp_DoanhThuChiTietKH_Android(?,?,?,?,?,?)}");

            cstmt.setString(1, date_n);
            cstmt.setString(2, date_n);
            cstmt.setString(3, dvcs);
            cstmt.setString(4, username);
            cstmt.setString(5, "");
            cstmt.setInt(6, 0);

            cstmt.registerOutParameter(6, Types.FLOAT);

            cstmt.execute();
            cstmt.getMoreResults();

            Float tongdoanhthu = cstmt.getFloat(6);

            tvdttntdv.setText(String.format("%,.0f", Float.valueOf(tongdoanhthu)));
            con.close();
        }
    }

    public void selectdonhangmoi() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            String query = "SELECT COUNT(Stt) Don_Hang_Moi FROM B30Ct WHERE Ngay_Ct='"+ date_n +"' AND Ma_Dvcs='"+ dvcs +"' AND Ma_TDV = '"+ macbnv +"' AND IsActive=1 GROUP BY Ma_TDV";

            Statement stmt = con.createStatement();
            resultSet = stmt.executeQuery(query);

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())
                {
                    tvdhmtdv.setText(resultSet.getString("Don_Hang_Moi"));
                }
            }
            //adapter.notifyDataSetChanged();
            con.close();
        }
    }

    public void selectdonhanghuy() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            String query = "SELECT COUNT(Stt) Don_Hang_Huy FROM B30Ct WHERE Ngay_Ct='"+ date_n +"' AND Ma_Dvcs='"+ dvcs +"' AND Ma_TDV = '"+ macbnv +"' AND DocStatus=9 GROUP BY Ma_TDV";

            Statement stmt = con.createStatement();
            resultSet = stmt.executeQuery(query);

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())
                {
                    tvdhhtdv.setText(resultSet.getString("Don_Hang_Huy"));
                }
            }
            //adapter.notifyDataSetChanged();
            con.close();
        }
    }

    public void taothongtinCN()
    {
        if(dvcs.equals("A01"))
        {
            dvcs1="1N101";
            makho="TP1N1-02";
        }
        if(dvcs.equals("A02"))
        {
            dvcs1="2N101-01";
            makho="TP2N1-01";
            soseri="CM/21E";
        }
        if(dvcs.equals("A03"))
        {
            dvcs1="2N301";
            makho="TP2N3-01";
            soseri="CT/21E";

        }

        if(dvcs.equals("A04"))
        {
            dvcs1="2N302";
            makho="TP2N3-02";
            soseri="TG/20E";
        }
        if(dvcs.equals("A05"))
        {
            dvcs1="2N201";
            makho="TP2N2-01";
            soseri="MD/21E";
        }
        if(dvcs.equals("A06"))
        {
            dvcs1="2N202";
            makho="TP2N2-02";
            soseri="VT/20E";
        }
        if(dvcs.equals("A07"))
        {
            dvcs1="2T101";
            makho="TP2T1-01";
            soseri="NT/20E";
        }
        if(dvcs.equals("A08"))
        {
            dvcs1="2T102";
            makho="TP2T1-02";
            soseri="DN/20E";
        }
        if(dvcs.equals("A09"))
        {
            dvcs1="2T103";
            makho="TP2T1-03";
            soseri="NA/20E";
        }
        if(dvcs.equals("A10"))
        {
            dvcs1="2B101";
            makho="TP2B1-01";
            soseri="HN/20E";
        }
    }
}