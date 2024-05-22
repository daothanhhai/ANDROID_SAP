package com.example.erp_tuyen.loginsap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class activity_chitietbknoptiengh extends AppCompatActivity {

    String IdUser,dvcs,username, macbnv,dvcs1,makho,soseri,mabp;
    ResultSet resultSet;
    AutoCompleteTextView atcctbknvgh,atcctbksobk;
    ImageView imgctbknvgh;
    Button btctbknt;
    String sttbk;

    ArrayList<String> arraysobk = new ArrayList<String>();

    private ArrayList<String> datasoct = new ArrayList<String>();
    private ArrayList<String> datangayct = new ArrayList<String>();
    private ArrayList<String> datadoituong = new ArrayList<String>();

    private ArrayList<String> datatienhd = new ArrayList<String>();

    private TableLayout table;
    private TableRow row;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietbknoptiengh);
        getSupportActionBar().setTitle("Chi tiết bảng kê nộp tiền");
        atcctbknvgh=(AutoCompleteTextView) findViewById(R.id.actctbkntgh);
        atcctbksobk=(AutoCompleteTextView) findViewById(R.id.actctbkntsobk);
        imgctbknvgh=(ImageView) findViewById(R.id.imagectbkntgh);
        btctbknt=(Button) findViewById(R.id.btctbkntsobk);
        table = (TableLayout) findViewById(R.id.tbctbknt);
        Intent intent=getIntent();
        IdUser=intent.getStringExtra("IdUser");
        dvcs=intent.getStringExtra("Ma_Dvcs");
        username = intent.getStringExtra("USERNAME");
        macbnv = intent.getStringExtra("Ma_CbNv");

        atcctbknvgh.setText(macbnv);

        taothongtinCN();

        try {
            selectsobk();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraysobk);
        atcctbksobk.setAdapter(adapter1);

        btctbknt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    selectsohdbk();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

    }


    public void selectsobk() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            String ngayct = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            PreparedStatement statement = con.prepareStatement("EXEC usp_BangKeNopTien_Android '"+ dvcs +"','"+ macbnv +"'");
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String stt = resultSet.getString("Stt");
                    String soct = resultSet.getString("So_Ct");


                    arraysobk.add(stt + ' ' + '|' + ' ' + soct + ' ');
                }
            }


        }
    }



    public void selectsohdbk() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {


            sttbk = atcctbksobk.getText().toString();

            if(sttbk != "")
            {
                sttbk=sttbk.substring(0,16);

            }


            PreparedStatement statement = con.prepareStatement("usp_LoadDetailBK_Android" + "'"+ sttbk +"' " );
            resultSet = statement.executeQuery();


            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String stt = resultSet.getString("Stt_Cd_Htt");
                    String soct = resultSet.getString("So_Ct");
                    String ngayct =resultSet.getString("Ngay_Ct");
                    ngayct=ngayct.substring(0,10);
                    String tendt = resultSet.getString("Ten_Dt");
                    String tongtien = resultSet.getString("Tien_Nt");


                    datasoct.add(soct);
                    datangayct.add(ngayct);
                    datadoituong.add(tendt);
                    datatienhd.add(String.format("%,.0f", Float.valueOf(tongtien)));

                    row = new TableRow(this);
                    TextView t1 = new TextView(this);
                    TextView t2 = new TextView(this);
                    TextView t3 = new TextView(this);
                    TextView t4 = new TextView(this);

                    if(tendt.equals("Tong_Cong"))
                    {
                        t3.setTextSize(20);
                        t3.setTextColor(Color.RED);
                        t3.setTextSize(20);
                        t3.setTextColor(Color.RED);
                        t4.setTextSize(20);
                        t4.setTextColor(Color.RED);

                    }

                    t1.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t1.setWidth(80);
                    t1.setHeight(100);
                    t2.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t2.setGravity(Gravity.LEFT);
                    t2.setWidth(80);
                    t2.setHeight(100);
                    t3.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t3.setGravity(Gravity.LEFT);
                    t3.setWidth(250);
                    t3.setHeight(100);
                    t4.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t4.setGravity(Gravity.RIGHT);
                    t1.setWidth(80);
                    t4.setHeight(100);

                    for (int j = 0; j < datasoct.size(); j++) {
                        String psoct = datasoct.get(j);
                        String pngayct = datangayct.get(j);
                        String pkh = datadoituong.get(j);
                        String ptienhd = datatienhd.get(j);

                        t1.setText(psoct);
                        t2.setText(pngayct);
                        t3.setText(pkh);
                        t4.setText(ptienhd);
                    }
                    row.setBackground(ContextCompat.getDrawable(this,R.drawable.border));

                    row.addView(t1);
                    row.addView(t2);
                    row.addView(t3);
                    row.addView(t4);
                    table.addView(row);

                }
            }


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
            mabp="B3-684";
        }
        if(dvcs.equals("A03"))
        {
            dvcs1="2N301";
            makho="TP2N3-01";
            soseri="CT/21E";
            mabp="B3-540";
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