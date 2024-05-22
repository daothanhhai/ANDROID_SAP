package com.example.erp_tuyen.loginsap;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class activity_bknoptiengh extends AppCompatActivity {

    String IdUser,dvcs,username, macbnv,dvcs1,makho,soseri,mabp;
    EditText edngaybkgh,edtnbktmgh;
    TextView tvsohdbktmgh,tvngayhdbktmgh,tvtongcongbktmgh;
    AutoCompleteTextView atcbkntgh,atctdghgh,atcsohdbkgh;
    ImageView imgtgghgh,imgsohdbkgh;
    Button btloadhdgh,btthemhdgh,btluuhdgh,btlmbkgh;
    float tongcong;
    ResultSet resultSet;
    String stttdgh,tienhd,sttcdhtt;
    ListView lvbkntgh;

    private ProgressDialog LoadingBar;

    ArrayList<String> arraypgh = new ArrayList<String>();
    ArrayList<String> arraysohdgh = new ArrayList<String>();

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
    BangKeNopTienGHAdapter adapterbangkenoptienGH;
    ArrayList<BangKeNopTienGH> arrayBangkenoptienGH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bknoptiengh);
        getSupportActionBar().setTitle("Bảng kê tiền mặt giao hàng");

        atcbkntgh=(AutoCompleteTextView) findViewById(R.id.actnvghbkgh);
        atctdghgh=(AutoCompleteTextView) findViewById(R.id.acttdghgh);
        atcsohdbkgh=(AutoCompleteTextView) findViewById(R.id.actsohdbkgh);
        edngaybkgh=(EditText) findViewById(R.id.edngaybkgh);
        btloadhdgh=(Button) findViewById(R.id.btlhdbkgh);
        imgtgghgh = (ImageView) findViewById(R.id.imagetdghgh);
        imgsohdbkgh=(ImageView) findViewById(R.id.imageshdbkgh);
        tvsohdbktmgh=(TextView) findViewById(R.id.tvsohdbktmgh);
        tvngayhdbktmgh=(TextView) findViewById(R.id.tvngayhdbktmgh);
        edtnbktmgh=(EditText) findViewById(R.id.edtnbktmgh);
        tvtongcongbktmgh=(TextView) findViewById(R.id.tvtongcongbkgh);
        btthemhdgh=(Button) findViewById(R.id.btthembkgh);
        btlmbkgh =(Button) findViewById(R.id.btlmbkgh);
        lvbkntgh = (ListView) findViewById(R.id.lvbkgh);

        Intent intent=getIntent();
        IdUser=intent.getStringExtra("IdUser");
        dvcs=intent.getStringExtra("Ma_Dvcs");
        username = intent.getStringExtra("USERNAME");
        macbnv = intent.getStringExtra("Ma_CbNv");

        atcbkntgh.setText(macbnv);

        taothongtinCN();

        arrayBangkenoptienGH=new ArrayList<>();
        adapterbangkenoptienGH=new BangKeNopTienGHAdapter(activity_bknoptiengh.this,R.layout.dong_bknoptiengh,arrayBangkenoptienGH);
        lvbkntgh.setAdapter(adapterbangkenoptienGH);

        String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        edngaybkgh.setText(date_n);


        try {
            selectphieutghg();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraypgh);
        atctdghgh.setAdapter(adapter1);


        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraysohdgh);
        atcsohdbkgh.setAdapter(adapter2);


        edngaybkgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonngay();
            }
        });

        imgtgghgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atctdghgh.showDropDown();
            }
        });

        imgsohdbkgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atcsohdbkgh.showDropDown();
            }
        });



        btloadhdgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingBar = new ProgressDialog(activity_bknoptiengh.this);
                LoadingBar.setTitle("OPC - MOBILE");
                LoadingBar.setMessage("Đang tải dữ liệu.... vB30Ct_HD");
                LoadingBar.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            selectsohdgh();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        LoadingBar.dismiss();
                    }
                }, 50);
            }
        });


        atcsohdbkgh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selecthd();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        btthemhdgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                them();
            }
        });

        btlmbkgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (adapter2 != null) {
                    atcsohdbkgh.setText("");
                    arraysohdgh.clear();
                    adapter2.clear();
                    adapter2.notifyDataSetChanged();
                }


            }
        });
    }

    private void chonngay() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(calendar.DATE);
        int thang = calendar.get(calendar.MONTH);
        int nam = calendar.get(calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                edngaybkgh.setText(simpleDateFormat.format(calendar.getTime()));

            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }


    public void selectphieutghg() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {


            String query = "SELECT Stt,So_Ct FROM vB30BK_G1_Edit WHERE Ma_NvGh = '"+ macbnv +"' ORDER BY Ngay_Ct DESC";
            //trên đây là câu truy vấn
            Statement stmt = con.createStatement(); //blah blah blah
            resultSet = stmt.executeQuery(query); //thực thi và trả về một cục ResultSet, nó là gì thì Google, tui chịu


            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String stt = resultSet.getString("Stt");
                    String soct = resultSet.getString("So_Ct");

                    arraypgh.add(stt + ' ' + '|' + ' ' + soct + ' ');
                }
            }
            //adapter.notifyDataSetChanged();
            con.close();
        }
    }

    public void selectsohdgh() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraysohdgh);
            atcsohdbkgh.setAdapter(adapter2);

            stttdgh = atctdghgh.getText().toString();
            if(stttdgh != "")
            {

                stttdgh=stttdgh.substring(0,16);

            }

            String query = "SELECT bk.Stt_Cd_Htt,ct.So_Ct,ct.Ma_Dt,dt.Ten_Dt,bk.Tien_Nt FROM B30BkDetail bk\n" +
                    "JOIN B30Ct ct ON ct.Stt = bk.Stt_Cd_Htt\n" +
                    "JOIN B20DmDt dt ON ct.Ma_Dt=dt.Ma_Dt\n" +
                    "WHERE bk.stt = '"+ stttdgh +"' \n" +
                    "ORDER BY bk.Ngay_Ct DESC";
            //trên đây là câu truy vấn
            Statement stmt = con.createStatement(); //blah blah blah
            resultSet = stmt.executeQuery(query); //thực thi và trả về một cục ResultSet, nó là gì thì Google, tui chịu


            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String stt = resultSet.getString("Stt_Cd_Htt");
                    String soct = resultSet.getString("So_Ct");
                    String tendt = resultSet.getString("Ten_Dt");
                    String tongtien = resultSet.getString("Tien_Nt");
                    String tien =String.format("%,.0f", Float.valueOf(tongtien));
                    arraysohdgh.add(stt + ' ' + '|' + ' ' + soct + ' '+ '|' + ' ' + tendt + ' '+ '|' + ' ' + tien + ' ');

                }
            }


            con.close();
        }
    }


    public void selecthd() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            sttcdhtt = atcsohdbkgh.getText().toString();
            if(sttcdhtt != "")
            {
                sttcdhtt=sttcdhtt.substring(0,16);
            }

            String query = "SELECT bk.Stt_Cd_Htt,ct.So_Ct,ct.Ngay_Ct,ct.Ma_Dt,dt.Ten_Dt,(bk.Tien_Nt - ISNULL(Ct.Gia_Tri_Code,0)) Tien_Nt  FROM B30BkDetail bk \n" +
                    "JOIN B30Ct ct ON ct.Stt = bk.Stt_Cd_Htt \n" +
                    "JOIN B20DmDt dt ON ct.Ma_Dt=dt.Ma_Dt \n" +
                    "WHERE bk.Stt_Cd_Htt = '"+ sttcdhtt +"' AND bk.Ma_Ct='G1' AND bk.Stt_Cd_Htt NOT IN (SELECT Stt_Cd_Htt FROM B30BkDetail WHERE Ma_Ct IN ('BK','TG')) \n" +
                    "ORDER BY bk.Ngay_Ct DESC";
            //trên đây là câu truy vấn
            Statement stmt = con.createStatement(); //blah blah blah
            resultSet = stmt.executeQuery(query); //thực thi và trả về một cục ResultSet, nó là gì thì Google, tui chịu


            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String stt = resultSet.getString("Stt_Cd_Htt");
                    String soct = resultSet.getString("So_Ct");
                    String ngayct = resultSet.getString("Ngay_Ct");
                    ngayct=ngayct.substring(0,10);
                    String tien = resultSet.getString("Tien_Nt");
                    tienhd=tien;
                    tvsohdbktmgh.setText(soct);
                    tvngayhdbktmgh.setText(ngayct);
                    edtnbktmgh.setText(tien);
                }
            }
            //adapter.notifyDataSetChanged();
            con.close();
        }
    }


    private void them() {

        String stt = sttcdhtt;
        String soct = tvsohdbktmgh.getText().toString();
        String ngayct = tvngayhdbktmgh.getText().toString();
        String tn = edtnbktmgh.getText().toString();
        data.add(stt);
        data1.add(soct);
        data2.add(ngayct);
        data3.add(tn);
        data4.add(tienhd);


        arrayBangkenoptienGH.add(new BangKeNopTienGH(stt,soct,ngayct,String.format("%,.0f", Float.valueOf(String.valueOf(tn))),String.format("%,.0f", Float.valueOf(String.valueOf(tienhd)))));
        adapterbangkenoptienGH.notifyDataSetChanged();

        tongcong=0;

        for(int i=0;i<data.size();i++)
        {
            tongcong = tongcong + Float.parseFloat(data3.get(i));

        }

        tvtongcongbktmgh.setText(String.format("%,.0f",Float.valueOf(tongcong)));


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