package com.example.erp_tuyen.loginsap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class activity_bknoptientdv extends AppCompatActivity {

    EditText edngaybk,edsobk,edldnbk;
    AutoCompleteTextView atcnlpbk,atcsohdbk,atcnvghbk;
    Button btthembk,btluubk,btlhdbk;
    ListView lvbktdv;
    String IdUser,dvcs,username, macbnv,dvcs1,makho,soseri,mabp;
    String sttcdhtt,tienhd;
    float tongcong;
    ResultSet resultSet;
    ImageView imgsohdbk,imgnvghbk;

    TextView tvsohdbktmtdv,tvngayhdbktmtdv,tvtongcongbktdv;
    EditText edtnhdbktmtdv;

    private ProgressDialog LoadingBar;

    ArrayList<String> arraysohd = new ArrayList<String>();
    ArrayList<String> arraynvgh = new ArrayList<String>();

    private ArrayList<String> data = new ArrayList<String>();
    private ArrayList<String> data1 = new ArrayList<String>();
    private ArrayList<String> data2 = new ArrayList<String>();
    private ArrayList<String> data3 = new ArrayList<String>();
    private ArrayList<String> data4 = new ArrayList<String>();
    private ArrayList<String> data5 = new ArrayList<String>();
    private ArrayList<String> data6 = new ArrayList<String>();
    private ArrayList<String> data7 = new ArrayList<String>();
    private ArrayList<String> data8 = new ArrayList<String>();
    private ArrayList<String> data9 = new ArrayList<String>();
    private ArrayList<String> data10 = new ArrayList<String>();
    private ArrayList<String> data11 = new ArrayList<String>();
    private ArrayList<String> data12 = new ArrayList<String>();
    private ArrayList<String> data13 = new ArrayList<String>();
    private ArrayList<String> data14 = new ArrayList<String>();
    private ArrayList<String> data15 = new ArrayList<String>();
    private ArrayList<String> data16 = new ArrayList<String>();
    BangKeNopTienTDVAdapter adapterbangkenoptienTDV;
    ArrayList<BangKeNopTienTDV> arrayBangkenoptienTDV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bknoptientdv);
        getSupportActionBar().setTitle("Bảng kê tiền mặt");
        edngaybk = (EditText) findViewById(R.id.edngaybktdv);
        edsobk = (EditText) findViewById(R.id.edsobktdv);
        edldnbk = (EditText) findViewById(R.id.edldnbktdv);
        atcnlpbk = (AutoCompleteTextView) findViewById(R.id.actnlpbktdv);
        atcsohdbk = (AutoCompleteTextView) findViewById(R.id.actsohdbktdv);
        atcnvghbk=(AutoCompleteTextView) findViewById(R.id.actnvghbk);
        btthembk = (Button) findViewById(R.id.btthembktdv);
        btluubk = (Button) findViewById(R.id.btluubktdv);
        btlhdbk = (Button) findViewById(R.id.btlhdbktdv);
        lvbktdv = (ListView) findViewById(R.id.lvbktdv);
        imgsohdbk=(ImageView) findViewById(R.id.imageshdbktdv);
        imgnvghbk=(ImageView) findViewById(R.id.imagenvghbk);

        tvsohdbktmtdv=(TextView) findViewById(R.id.tvsohdbktmtdv);
        tvngayhdbktmtdv=(TextView) findViewById(R.id.tvngayhdbktmtdv);
        edtnhdbktmtdv=(EditText) findViewById(R.id.edtnbktmtdv);
        tvtongcongbktdv=(TextView) findViewById(R.id.tvtongcongbktdv);



        Intent intent=getIntent();
        IdUser=intent.getStringExtra("IdUser");
        dvcs=intent.getStringExtra("Ma_Dvcs");
        username = intent.getStringExtra("USERNAME");
        macbnv = intent.getStringExtra("Ma_CbNv");

        taothongtinCN();


        arrayBangkenoptienTDV=new ArrayList<>();
        adapterbangkenoptienTDV=new BangKeNopTienTDVAdapter(activity_bknoptientdv.this,R.layout.dong_bknoptientdv,arrayBangkenoptienTDV);
        lvbktdv.setAdapter(adapterbangkenoptienTDV);


        String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        edngaybk.setText(date_n);

        try {
            selectnvgh();
            //selectsohd();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraysohd);
        atcsohdbk.setAdapter(adapter1);

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraynvgh);
        atcnvghbk.setAdapter(adapter2);

        imgsohdbk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atcsohdbk.showDropDown();
            }
        });

        imgnvghbk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atcnvghbk.showDropDown();
            }
        });

        atcsohdbk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                try {
                    selectsohd();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });



        atcnvghbk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectnvgh();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        btlhdbk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingBar = new ProgressDialog(activity_bknoptientdv.this);
                LoadingBar.setTitle("OPC - MOBILE");
                LoadingBar.setMessage("Đang tải dữ liệu.... vB30Ct_HD");
                LoadingBar.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            selectsohd();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        LoadingBar.dismiss();
                    }
                }, 50);
            }
        });

        atcsohdbk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sttcdhtt = atcsohdbk.getText().toString();
                if(sttcdhtt != "")
                {
                    sttcdhtt=sttcdhtt.substring(0,16);

                }
                try {
                    selecthoadon();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        btthembk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                them();
            }
        });

    }

    public void selectsohd() throws SQLException
    {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            String ngaybk = edngaybk.getText().toString();
            String manvgh = atcnvghbk.getText().toString().substring(0,8);


            String query = "SELECT Stt,So_Ct,Ngay_Ct,Tong_Tien FROM vB30Ct_HD WHERE Ma_Dvcs='"+ dvcs +"' " +
                    "AND Ngay_Ct<= '"+ ngaybk +"' AND Ma_NvGh = '"+ manvgh +"' ";
            //trên đây là câu truy vấn
            Statement stmt = con.createStatement(); //blah blah blah
            resultSet = stmt.executeQuery(query); //thực thi và trả về một cục ResultSet, nó là gì thì Google, tui chịu


            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String stt =resultSet.getString("Stt");
                    String soct = resultSet.getString("So_Ct");
                    String ngayct = resultSet.getString("Ngay_Ct");
                    if(ngayct!="") {
                        ngayct = ngayct.substring(0, 10);
                    }
                    String tongtien = resultSet.getString("Tong_Tien");
                    String tien =String.format("%,.0f", Float.valueOf(tongtien));
                    arraysohd.add(stt + ' ' + '|' + ' ' + soct + ' ' + '|' + ' ' + ngayct + ' ' + '|' + ' ' + tien );

                }
            }
            //adapter.notifyDataSetChanged();
            con.close();
        }
    }

    void selecthoadon() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            PreparedStatement statement = con.prepareStatement("EXEC usp_ThongTinHD_Android '"+ sttcdhtt +"'");
            resultSet = statement.executeQuery();


            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())
                {
                    String soct = resultSet.getString("So_Ct");
                    String ngayct=resultSet.getString("Ngay_Ct");
                    ngayct=ngayct.substring(0,10);
                    String tiennop=resultSet.getString("Tong_Tien");
                    tienhd=tiennop;
                    tvsohdbktmtdv.setText(soct);
                    tvngayhdbktmtdv.setText(ngayct);
                    edtnhdbktmtdv.setText(tiennop);

                }
            }
            con.close();
        }
    }


    public void selectnvgh() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {


            String query = "SELECT Ma_CbNv,Ten_CbNv FROM B20DmCbNv WHERE Ma_Bp = '"+ mabp +"'";
            //trên đây là câu truy vấn
            Statement stmt = con.createStatement(); //blah blah blah
            resultSet = stmt.executeQuery(query); //thực thi và trả về một cục ResultSet, nó là gì thì Google, tui chịu


            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String macbnv = resultSet.getString("Ma_CbNv");
                    String tencbnv = resultSet.getString("Ten_CbNv");
                    //arraymadt.add(madt + ' ' + tendt);
                    arraynvgh.add(macbnv + ' ' + tencbnv);
                }
            }
            //adapter.notifyDataSetChanged();
            con.close();
        }
    }



    private void them() {

        String stt = sttcdhtt;
        String soct = tvsohdbktmtdv.getText().toString();
        String ngayct = tvngayhdbktmtdv.getText().toString();
        String tn = edtnhdbktmtdv.getText().toString();
        data.add(stt);
        data1.add(soct);
        data2.add(ngayct);
        data3.add(tn);
        data4.add(tienhd);


        arrayBangkenoptienTDV.add(new BangKeNopTienTDV(stt,soct,ngayct,String.format("%,.0f", Float.valueOf(String.valueOf(tn))),String.format("%,.0f", Float.valueOf(String.valueOf(tienhd)))));
        adapterbangkenoptienTDV.notifyDataSetChanged();

        tongcong=0;

        for(int i=0;i<data.size();i++)
        {
            tongcong = tongcong + Float.parseFloat(data3.get(i));

        }
//        tongtienhang=tienhang;
        tvtongcongbktdv.setText(String.format("%,.0f",Float.valueOf(tongcong)));
//        thue = tienhang * Float.parseFloat(thuegtgtcodinh);
//        tongtienthue=thue;
//        tvthue.setText(String.format("%,.0f", Float.valueOf(thue)));
//        tongcong = tienhang + thue;
//        tvtongcong.setText(String.format("%,.0f", Float.valueOf(tongcong)));
//
//
//        atmavtdhtdv.setText("");
//        edsoluong.setText("");
//        edgiagoc.setText("");
//        eddongia.setText("");
//
//        edthanhtien.setText("");
//        atmavtdhtdv.requestFocus();
//        chkm.setChecked(false);
//        atctkmdhtdv.setText("");
//        edtenvt.setText("");
//
//        edchietkhau.setText(String.format("%.0f", Float.valueOf(chietkhaucodinh)));
//
//        tonggiatricode+=giatricode;
//        giatricode=0.0;
//        edcodetl2.setText("");
//
//        phanloaicp="";
//        giagoc="0";

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