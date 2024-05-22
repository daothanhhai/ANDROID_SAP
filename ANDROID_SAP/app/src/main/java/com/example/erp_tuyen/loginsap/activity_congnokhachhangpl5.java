package com.example.erp_tuyen.loginsap;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
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

public class activity_congnokhachhangpl5 extends AppCompatActivity {

    private ArrayList<String> datakhcnpl5 = new ArrayList<String>();
    private ArrayList<String> datasoctcnpl5 = new ArrayList<String>();
    private ArrayList<String> datangayctcnpl5 = new ArrayList<String>();
    private ArrayList<String> datatdvcnpl5 = new ArrayList<String>();
    private ArrayList<String> datatongtiencnpl5 = new ArrayList<String>();

    ArrayList<String> arraymadt = new ArrayList<String>();

    private ArrayList<String> data4 = new ArrayList<String>();
    private TableLayout table;
    private TableRow row;

    EditText edtncnpl5,eddncnpl5;
    AutoCompleteTextView atcdtcnpl5;
    ImageView imgbccnpl5;
    TextView tvtccnpl5;

    int flag=0;
    String dvcs,dvcs1,username, macbnv,IdUser;
    String madt, tendt,makh;
    ResultSet resultSet;
    Button buttonbccnpl5;
    TableRow tbrcnpl5;
    HorizontalScrollView horizontalScrollView;
    private ProgressDialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congnokhachhangpl5);

        getSupportActionBar().setTitle("Báo cáo công nợ chi tiết");
        Intent intent=getIntent();
        dvcs=intent.getStringExtra("Ma_Dvcs");
        username = intent.getStringExtra("USERNAME");
        macbnv = intent.getStringExtra("Ma_CbNv");
        IdUser=intent.getStringExtra("IdUser");
        taothongtinCN();
        anhxa();
        String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        edtncnpl5.setText(date_n);
        eddncnpl5.setText(date_n);


        edtncnpl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 0;
                chonngay();
            }
        });

        eddncnpl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                chonngay();
            }
        });

        try {
            selectdoituong();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraymadt);
        atcdtcnpl5.setAdapter(adapter1);

        imgbccnpl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atcdtcnpl5.showDropDown();
            }
        });

        atcdtcnpl5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                madt = atcdtcnpl5.getText().toString();

                try {
                    selectdoituong();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonbccnpl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingBar = new ProgressDialog(activity_congnokhachhangpl5.this);
                LoadingBar.setTitle("OPC - MOBILE");
                LoadingBar.setMessage("Đang tải dữ liệu.... usp_RpCongNoTDV_SAP");
                LoadingBar.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            table.removeViews(1, Math.max(0, table.getChildCount() - 1));
                            selectcongnokh();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        LoadingBar.dismiss();
                    }
                }, 5000);
            }
        });



    }


    void anhxa()
    {
        edtncnpl5=(EditText) findViewById(R.id.edittexttncnpl5);
        eddncnpl5=(EditText) findViewById(R.id.edittextdncnpl5);
        atcdtcnpl5=(AutoCompleteTextView) findViewById(R.id.actcnpl5);
        imgbccnpl5=(ImageView) findViewById(R.id.imagecnpl5);

        buttonbccnpl5=(Button) findViewById(R.id.buttonbccnpl5);
        tbrcnpl5=(TableRow) findViewById(R.id.tbrcnpl5);
        table = (TableLayout) findViewById(R.id.tbcnpl5);
        horizontalScrollView=(HorizontalScrollView) findViewById(R.id.progress_horizontal);
        tvtccnpl5=(TextView) findViewById(R.id.tvtongcongcnpl5);

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
                    edtncnpl5.setText(simpleDateFormat.format(calendar.getTime()));
                }

            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }


    private void selectcongnokh() throws SQLException {

        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            String tungay=edtncnpl5.getText().toString();
            String denngay=eddncnpl5.getText().toString();
            double tongcongnopl5=0;

            //String madt=atcdtcnkh.getText().toString();

            makh = atcdtcnpl5.getText().toString();
            if (makh.equals("")) {
                makh = "";

            }
            else {
                if (dvcs.equals("A02")) {
                    makh = atcdtcnpl5.getText().toString().substring(0, 8);
                } else {
                    makh = atcdtcnpl5.getText().toString().substring(0, 8);
                }
            }

            try {

                PreparedStatement statement = con.prepareStatement("usp_RpCongNoTDV_SAP" +
                        " '" + tungay + "' ,'" + denngay + "','" + makh + "'" +
                        ",'" + macbnv + "' ");
                resultSet = statement.executeQuery();


                for (int i = 0; i <= resultSet.getRow(); i++) {
                    if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                    {
                        String tendt = resultSet.getString("Ten_Dt");
                        String soct = resultSet.getString("So_Ct");
                        String ngayct = resultSet.getString("Ngay_Ct");

                        if (ngayct != null) {
                            ngayct = ngayct.substring(0, 10);
                        }

                        String tdv = resultSet.getString("Ten_TDV");
                        String tongtien = resultSet.getString("Cong_No");

                        datakhcnpl5.add(tendt);
                        datasoctcnpl5.add(soct);
                        datangayctcnpl5.add(ngayct);
                        datatdvcnpl5.add(tdv);
                        datatongtiencnpl5.add(String.format("%,.0f", Float.valueOf(tongtien)));

                        row = new TableRow(this);
                        TextView t1 = new TextView(this);
                        TextView t2 = new TextView(this);
                        TextView t3 = new TextView(this);
                        TextView t4 = new TextView(this);
                        TextView t5 = new TextView(this);

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
                        }


                        t1.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                        t1.setWidth(200);
                        t1.setHeight(100);
                        t2.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                        t2.setHeight(100);
                        t3.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                        t3.setHeight(100);
                        t4.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                        t4.setHeight(100);
                        t5.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                        t5.setHeight(100);
                        //double tongcongnopl5=0;

                        for (int j = 0; j < datakhcnpl5.size(); j++) {
                            String pdt = datakhcnpl5.get(j);
                            String psoct = datasoctcnpl5.get(j);
                            String pngayct = datangayctcnpl5.get(j);
                            String ptdv = datatdvcnpl5.get(j);
                            String ptongtien = datatongtiencnpl5.get(j);
                            //double price = Double.parseDouble(ptongtien.replaceAll(",", ""));
                            //tongcongnopl5 = tongcongnopl5 + Double.valueOf(tongtien);
                            t1.setText(pdt);
                            t2.setText(psoct);
                            t3.setText(pngayct);
                            t4.setText(ptdv);
                            t5.setText(ptongtien);

                        }
                        row.setBackground(ContextCompat.getDrawable(this, R.drawable.border));

                        row.addView(t1);
                        row.addView(t2);
                        row.addView(t3);
                        row.addView(t4);
                        row.addView(t5);

                        table.addView(row);

                        //String tongcn= String.valueOf(tongcongnopl5);
                        //tvtccnpl5.setText(String.format("%,.0f", Float.valueOf(tongcn)));


                    }


                }


                con.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }


        }


    }

    public void taothongtinCN()
    {
        if(dvcs.equals("A01"))
        {
            dvcs1="1N101";

        }
        if(dvcs.equals("A02"))
        {
            dvcs1="2N101-01";


        }
        if(dvcs.equals("A03"))
        {
            dvcs1="2N301";



        }

        if(dvcs.equals("A04"))
        {
            dvcs1="2N302";


        }
        if(dvcs.equals("A05"))
        {
            dvcs1="2N201";


        }
        if(dvcs.equals("A06"))
        {
            dvcs1="2N202";


        }
        if(dvcs.equals("A07"))
        {
            dvcs1="2T101";


        }
        if(dvcs.equals("A08"))
        {
            dvcs1="2T102";


        }
        if(dvcs.equals("A09"))
        {
            dvcs1="2T103";


        }
        if(dvcs.equals("A10"))
        {
            dvcs1="2B101";


        }
    }

    public void selectdoituong() throws SQLException {
        Connection con = SERVER.Connect();
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            PreparedStatement statement = con.prepareStatement("EXEC usp_LookupKH_Android '"+ dvcs +"','"+ username +"','"+ macbnv +"' " );
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
    }
}