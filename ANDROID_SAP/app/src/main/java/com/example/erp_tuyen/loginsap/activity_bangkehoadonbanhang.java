package com.example.erp_tuyen.loginsap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class activity_bangkehoadonbanhang extends AppCompatActivity {
    private ArrayList<String> datasoctbkhdbh = new ArrayList<String>();
    private ArrayList<String> datangayhdbkhdbh = new ArrayList<String>();
    private ArrayList<String> datadtbkhdbh = new ArrayList<String>();
    private ArrayList<String> datavtbkhdbh = new ArrayList<String>();
    private ArrayList<String> dataslbkhdbh = new ArrayList<String>();
    private ArrayList<String> datagiabkhdbh = new ArrayList<String>();
    private ArrayList<String> datatienbkhdbh = new ArrayList<String>();
    private ArrayList<String> datathuebkhdbh = new ArrayList<String>();
    private ArrayList<String> datatongtienbkhdbh = new ArrayList<String>();

    ArrayList<String> arraymadt = new ArrayList<String>();
    private TableLayout table;
    private TableRow row;

    EditText edtnbkhdbh,eddnbkhdbh;
    String madt, tendt,makh;
    AutoCompleteTextView atcdtbkhdbh;
    ImageView imgdtbkhdbh;

    TextView tvsoctbkhdbh,tvngayctbkhdbh,tvdtbkhdbh,tvvtbkhdbh,tvslbkhdbh,tvgiabkhdbh,tvtienbkhdbh;
    int flag=0;
    String dvcs,dvcs1,username, macbnv,IdUser;
    ResultSet resultSet;
    Button buttonbcbkhdbh;
    TableRow tbrbkhdbh;
    private ProgressDialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bangkehoadonbanhang);

        getSupportActionBar().setTitle("Bảng kê hóa đơn bán hàng");

        Intent intent=getIntent();
        dvcs=intent.getStringExtra("Ma_Dvcs");
        username = intent.getStringExtra("USERNAME");
        macbnv = intent.getStringExtra("Ma_CbNv");
        IdUser=intent.getStringExtra("IdUser");
        anhxa();
        String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        edtnbkhdbh.setText(date_n);
        eddnbkhdbh.setText(date_n);

        edtnbkhdbh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                chonngay();
            }
        });
        eddnbkhdbh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
                chonngay();
            }
        });

        try {
            selectdoituong();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraymadt);
        atcdtbkhdbh.setAdapter(adapter1);

        imgdtbkhdbh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atcdtbkhdbh.showDropDown();
            }
        });

        atcdtbkhdbh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                madt = atcdtbkhdbh.getText().toString();

                try {
                    selectdoituong();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonbcbkhdbh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoadingBar=new ProgressDialog(activity_bangkehoadonbanhang.this);
                LoadingBar.setTitle("SAP - ANDROID");
                LoadingBar.setMessage("Đang tải dữ liệu.... usp_BangKeHoaDonTDV_SAP");
                LoadingBar.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            table.removeViews(1, Math.max(0, table.getChildCount() - 1));
                            selectbkhdbh();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        LoadingBar.dismiss();
                    }
                },50);



            }
        });
    }

    void anhxa()
    {
        edtnbkhdbh=(EditText) findViewById(R.id.editTexttnbkhdbh);
        eddnbkhdbh=(EditText) findViewById(R.id.editTextdnbkhdbh);
        atcdtbkhdbh=(AutoCompleteTextView) findViewById(R.id.actdtbkhdbh);
        imgdtbkhdbh=(ImageView) findViewById(R.id.imagedtbkhdbh);
        buttonbcbkhdbh =(Button) findViewById(R.id.buttonbcbkhdbh);
        tvsoctbkhdbh=(TextView) findViewById(R.id.tvsoctbkhdbh);
        tvngayctbkhdbh=(TextView) findViewById(R.id.tvngayctbkhdbh);
        tvdtbkhdbh=(TextView) findViewById(R.id.tvdtbkhdbh);
        tvvtbkhdbh=(TextView) findViewById(R.id.tvvtbkhdbh);
        tvslbkhdbh=(TextView) findViewById(R.id.tvslbkhdbh);
        tvgiabkhdbh=(TextView) findViewById(R.id.tvgiabkhdbh);
        tvtienbkhdbh=(TextView) findViewById(R.id.tvtienbkhdbh);
        table = (TableLayout) findViewById(R.id.tbbkhdbh);
        tbrbkhdbh=(TableRow) findViewById(R.id.tbrowbkhdbh);

    }

    private  void chonngay()
    {
        final Calendar calendar=Calendar.getInstance();
        int ngay=calendar.get(calendar.DATE);
        int thang=calendar.get(calendar.MONTH);
        int nam= calendar.get(calendar.YEAR);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                if(flag==0) {
                    edtnbkhdbh.setText(simpleDateFormat.format(calendar.getTime()));
                }
                if(flag==1)
                {
                    eddnbkhdbh.setText(simpleDateFormat.format(calendar.getTime()));
                }
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }

    public void selectdoituong() throws SQLException {
        Connection con = SERVER.Connect();
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
        try {
            PreparedStatement statement = con.prepareStatement("EXEC usp_DmDtTdv_SAP '','" + macbnv + "' ");
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    madt = resultSet.getString("Ma_Dt");
                    tendt = resultSet.getString("Ten_Dt");
                    arraymadt.add(madt + ' ' + tendt);

                }
            }

            con.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        }
    }

    private void selectbkhdbh() throws SQLException {

        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {


            String tungay=edtnbkhdbh.getText().toString();
            String denngay=eddnbkhdbh.getText().toString();

            makh = atcdtbkhdbh.getText().toString();
            if (makh.equals("")) {
                makh = "";

            }
            else {

                    makh = atcdtbkhdbh.getText().toString().substring(0, 7);
                    makh=makh.trim();
            }

            try {
                PreparedStatement statement = con.prepareStatement("EXEC usp_BangKeHoaDonTDV_SAP '" + tungay + "' ,'" + denngay + "','" + macbnv + "','" + makh + "' ");
                resultSet = statement.executeQuery();

                for (int i = 0; i <= resultSet.getRow(); i++) {
                    if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                    {
                        String doituong = resultSet.getString("Ten_Dt");
                        String soct = resultSet.getString("So_Ct");
                        String ngayct = resultSet.getString("Ngay_Ct1");
                        if (ngayct != null) {
                            ngayct = ngayct.substring(0, 10);
                        }
                        String tenvt = resultSet.getString("Ten_Vt");
                        String soluong = resultSet.getString("So_Luong");
                        String gia = resultSet.getString("Gia");
                        String tien = resultSet.getString("Tong_Tien");
                        String thue = resultSet.getString("Thue");
                        String tongtien = resultSet.getString("Tong_Tien_TAX");

                        datasoctbkhdbh.add(soct);
                        datangayhdbkhdbh.add(ngayct);
                        datadtbkhdbh.add(doituong);
                        datavtbkhdbh.add(tenvt);


                        dataslbkhdbh.add(String.format("%,.0f", Float.valueOf(soluong)));
                        datagiabkhdbh.add(String.format("%,.0f", Float.valueOf(gia)));
                        datatienbkhdbh.add(String.format("%,.0f", Float.valueOf(tien)));
                        datathuebkhdbh.add(String.format("%,.0f", Float.valueOf(thue)));
                        datatongtienbkhdbh.add(String.format("%,.0f", Float.valueOf(tongtien)));


                        row = new TableRow(this);
                        TextView t1 = new TextView(this);
                        TextView t2 = new TextView(this);
                        TextView t3 = new TextView(this);
                        TextView t4 = new TextView(this);
                        TextView t5 = new TextView(this);
                        TextView t6 = new TextView(this);
                        TextView t7 = new TextView(this);
                        TextView t8 = new TextView(this);
                        TextView t9 = new TextView(this);


                        if (soct.equals("TC")) {
                            t1.setTextSize(20);
                            t1.setTextColor(Color.RED);
                            t2.setTextSize(20);
                            t2.setTextColor(Color.RED);
                            t3.setTextSize(20);
                            t3.setTextColor(Color.RED);
                            t4.setTextSize(20);
                            t4.setTextColor(Color.RED);
                            t5.setTextSize(20);
                            t5.setTextColor(Color.RED);
                            t6.setTextSize(20);
                            t6.setTextColor(Color.RED);
                            t7.setTextSize(20);
                            t7.setTextColor(Color.RED);
                            t8.setTextSize(20);
                            t8.setTextColor(Color.RED);
                            t9.setTextSize(20);
                            t9.setTextColor(Color.RED);
                        }


                        t1.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                        t1.setWidth(180);
                        t1.setHeight(70);
                        t2.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                        t2.setHeight(70);
                        t2.setWidth(200);
                        t3.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                        t3.setHeight(70);
                        t4.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                        t4.setHeight(70);
                        t5.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                        t5.setHeight(70);
                        t6.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                        t6.setHeight(70);

                        t7.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                        t7.setHeight(70);

                        t8.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                        t8.setHeight(70);
                        t9.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                        t9.setHeight(70);

                        for (int j = 0; j < datasoctbkhdbh.size(); j++) {
                            String psoct = datasoctbkhdbh.get(j);
                            String pngayct = datangayhdbkhdbh.get(j);
                            String ptendt = datadtbkhdbh.get(j);
                            String ptenvt = datavtbkhdbh.get(j);
                            String psoluong = dataslbkhdbh.get(j);
                            String pgia = datagiabkhdbh.get(j);
                            String ptien = datatienbkhdbh.get(j);
                            String pthue = datathuebkhdbh.get(j);
                            String ptongtien = datatongtienbkhdbh.get(j);

                            t1.setText(psoct);
                            t2.setText(pngayct);
                            t3.setText(ptendt);
                            t4.setText(ptenvt);
                            t5.setText(psoluong);
                            t6.setText(pgia);
                            t7.setText(ptien);
                            t8.setText(pthue);
                            t9.setText(ptongtien);


                        }
                        row.setBackground(ContextCompat.getDrawable(this, R.drawable.border));

                        row.addView(t1);
                        row.addView(t2);
                        row.addView(t3);
                        row.addView(t4);
                        row.addView(t5);
                        row.addView(t6);
                        row.addView(t7);
                        row.addView(t8);
                        row.addView(t9);
                        table.addView(row);

                    }
                }

                con.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
}