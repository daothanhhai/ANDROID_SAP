package com.example.erp_tuyen.loginsap;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
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

public class activity_baocaoviengthamkhachhang extends AppCompatActivity {

    private ArrayList<String> datakhbcvtkh = new ArrayList<String>();

    private ArrayList<String> datatdvbcvtkh = new ArrayList<String>();
    private ArrayList<String> datavtbcvtkh = new ArrayList<String>();
    private ArrayList<String> datadhbcvtkh = new ArrayList<String>();
    private ArrayList<String> datahabcvtkh = new ArrayList<String>();
    private ArrayList<String> datadtbcvtkh = new ArrayList<String>();

    private ArrayList<String> datatbhbcvtkh = new ArrayList<String>();

    ArrayList<String> arraymadt = new ArrayList<String>();

    ArrayList<String> arraymatdv = new ArrayList<String>();

    ArrayList<String> arraymatbh = new ArrayList<String>();

    EditText edtnbcvtkh,eddnbcvtkh;

    int flag=0;
    String dvcs,macbnv,chucdanh,username;

    String madt, tendt,makh;
    String matdv,matdvlk,tentdv;
    String matbh,matbhlk,tentbh;
    ResultSet resultSet;
    Button buttonbcvtkh;
    TableRow tbrbcvtkh;
    private ProgressDialog LoadingBar;

    private ArrayList<String> data4 = new ArrayList<String>();
    private TableLayout table;
    private TableRow row;

    AutoCompleteTextView atcbcvtkh,atctdvbcvtkh1,atctbhbcvtkh1;
    ImageView imgbcvtkh,imgtdvbcvtkh1,imgtbhbcvtkh1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baocaoviengthamkhachhang);

        getSupportActionBar().setTitle("Báo Cáo Viếng Thăm Khách hàng");
        Intent intent=getIntent();
        dvcs=intent.getStringExtra("Ma_Dvcs");
        macbnv=intent.getStringExtra("Ma_CbNv");
        chucdanh=intent.getStringExtra("Chuc_Danh");
        username=intent.getStringExtra("USERNAME");
        anhxa();

        String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        edtnbcvtkh.setText(date_n);
        eddnbcvtkh.setText(date_n);

        edtnbcvtkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                chonngay();
            }
        });
        eddnbcvtkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
                chonngay();
            }
        });

        try {
            selectdoituong();
            selectTDV();
            selectTuyenBH();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraymadt);
        atcbcvtkh.setAdapter(adapter1);

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraymatdv);
        atctdvbcvtkh1.setAdapter(adapter2);

        final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraymatbh);
        atctbhbcvtkh1.setAdapter(adapter3);

        imgbcvtkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atcbcvtkh.showDropDown();
            }
        });

        atcbcvtkh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                madt = atcbcvtkh.getText().toString();

                try {
                    selectdoituong();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        imgtdvbcvtkh1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atctdvbcvtkh1.showDropDown();
            }
        });

        atctdvbcvtkh1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                matdvlk = atctdvbcvtkh1.getText().toString().substring(0,8);
                try {
                    selectTDV();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        imgtbhbcvtkh1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atctbhbcvtkh1.showDropDown();
            }
        });

        atctbhbcvtkh1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                matbhlk=atctbhbcvtkh1.getText().toString().substring(0,8);
                try {
                    selectTuyenBH();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });



        buttonbcvtkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingBar=new ProgressDialog(activity_baocaoviengthamkhachhang.this);
                LoadingBar.setTitle("OPC - MOBILE");
                LoadingBar.setMessage("Đang tải dữ liệu.... usp_BaoCaoViengThamKH_Android");
                LoadingBar.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            table.removeViews(1, Math.max(0, table.getChildCount() - 1));
                            selectgsdhtdv();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        LoadingBar.dismiss();
                    }
                },50);
            }
        });



    }
    void anhxa(){
        edtnbcvtkh=(EditText) findViewById(R.id.editTexttnbcvtkh);
        eddnbcvtkh=(EditText) findViewById(R.id.editTextdnbcvtkh);
        buttonbcvtkh=(Button) findViewById(R.id.buttonbcvtkh);
        table = (TableLayout) findViewById(R.id.tbbcvtkh);
        tbrbcvtkh=(TableRow) findViewById(R.id.tbrowbcvtkh);
        imgbcvtkh = (ImageView) findViewById(R.id.imagebcvtkh);
        atcbcvtkh=(AutoCompleteTextView) findViewById(R.id.actbcvtkh);

        imgtdvbcvtkh1=(ImageView) findViewById(R.id.imagetdvvtkh1);
        atctdvbcvtkh1=(AutoCompleteTextView) findViewById(R.id.acttdvvtkh1);

        imgtbhbcvtkh1 = (ImageView) findViewById(R.id.imagetbhvtkh1);
        atctbhbcvtkh1 = (AutoCompleteTextView) findViewById(R.id.acttbhvtkh1);

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
                    edtnbcvtkh.setText(simpleDateFormat.format(calendar.getTime()));
                }
                if(flag==1)
                {
                    eddnbcvtkh.setText(simpleDateFormat.format(calendar.getTime()));
                }
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }

    private void selectgsdhtdv() throws SQLException {

        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {


            String tungay=edtnbcvtkh.getText().toString();
            String denngay=eddnbcvtkh.getText().toString();

            makh = atcbcvtkh.getText().toString();
            if (makh.equals("")) {
                makh = "";

            } else {

                if (dvcs.equals("A02")) {
                    makh = atcbcvtkh.getText().toString().substring(0, 7);
                } else {
                    makh = atcbcvtkh.getText().toString().substring(0, 7);
                }
            }

            matdvlk = atctdvbcvtkh1.getText().toString();
            if (matdvlk.equals("")) {
                matdvlk = "";

            } else {
                matdvlk = atctdvbcvtkh1.getText().toString().substring(0, 8);
            }

            matbhlk=atctbhbcvtkh1.getText().toString();
            if(matbhlk.equals("")){
                matbhlk="";
            }
            else {
                matbhlk=atctbhbcvtkh1.getText().toString().substring(0,8);
            }

            try {
                PreparedStatement statement = con.prepareStatement("EXEC usp_BaoCaoViengThamKH_Android '" + tungay + "' ,'" + denngay + "'" +
                        ",'" + dvcs + "','" + username + "','" + macbnv + "','" + makh + "','" + matdvlk + "','" + matbhlk + "' ");
                resultSet = statement.executeQuery();

                for (int i = 0; i <= resultSet.getRow(); i++) {
                    if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                    {
                        String khachhang = resultSet.getString("Ten_Dt");

                        String tdv = resultSet.getString("Ten_TDV");

                        String viengtham = resultSet.getString("Vieng_Tham");

                        String donhang = resultSet.getString("Don_Hang");

                        String hinhanh = resultSet.getString("Hinh_Anh");

                        String doanhthu = resultSet.getString("Doanh_Thu");

                        datakhbcvtkh.add(khachhang);
                        datatdvbcvtkh.add(tdv);
                        datavtbcvtkh.add(viengtham);
                        datadhbcvtkh.add(donhang);
                        datahabcvtkh.add(hinhanh);

                        datadtbcvtkh.add(String.format("%,.0f", Float.valueOf(doanhthu)));


                        row = new TableRow(this);
                        TextView t1 = new TextView(this);
                        TextView t2 = new TextView(this);
                        TextView t3 = new TextView(this);
                        TextView t4 = new TextView(this);

                        TextView t5 = new TextView(this);
                        TextView t6 = new TextView(this);


                        t1.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                        t1.setWidth(300);
                        t1.setHeight(70);
                        t1.setTextSize(14);
                        t1.setTextColor(Color.MAGENTA);

                        t2.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                        t2.setHeight(70);
                        t2.setTextColor(Color.BLACK);

                        t3.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                        t3.setHeight(70);
                        t3.setWidth(40);
                        t3.setTextColor(Color.BLACK);
                        t3.setBackgroundColor(Color.GREEN);
                        t3.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

                        t4.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                        t4.setHeight(70);
                        t4.setTextColor(Color.BLACK);
                        t4.setBackgroundColor(Color.CYAN);
                        t4.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

                        t5.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                        t5.setHeight(70);
                        t5.setTextColor(Color.BLACK);
                        t5.setBackgroundColor(Color.YELLOW);
                        t5.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

                        t6.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                        t6.setHeight(70);
                        t6.setTextColor(Color.BLACK);
                        t6.setBackgroundColor(Color.WHITE);
                        t6.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);


                        for (int j = 0; j < datakhbcvtkh.size(); j++) {
                            String ptkh = datakhbcvtkh.get(j);
                            String pttdv = datatdvbcvtkh.get(j);
                            String ptvt = datavtbcvtkh.get(j);
                            String pdonhang = datadhbcvtkh.get(j);
                            String phinhanh = datahabcvtkh.get(j);
                            String pdoanhthu = datadtbcvtkh.get(j);

                            t1.setText(ptkh);
                            t2.setText(pttdv);
                            t3.setText(ptvt);
                            t4.setText(pdonhang);
                            t5.setText(phinhanh);
                            t6.setText(pdoanhthu);

                        }
                        row.setBackground(ContextCompat.getDrawable(this, R.drawable.border));

                        row.addView(t1);
                        row.addView(t2);

                        row.addView(t3);
                        row.addView(t4);

                        row.addView(t5);
                        row.addView(t6);

                        table.addView(row);

                    }
                }

                con.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    public void selectdoituong() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            try {
                PreparedStatement statement = con.prepareStatement("EXEC usp_DmDtTdv_SAP '','" + macbnv + "'");
                resultSet = statement.executeQuery();

                for (int i = 0; i <= resultSet.getRow(); i++) {
                    if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                    {
                        String madt = resultSet.getString("Ma_Dt");
                        String tendt = resultSet.getString("Ten_Dt");

                        arraymadt.add(madt + ' ' + tendt);
                    }
                }
                //adapter.notifyDataSetChanged();
                con.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void selectTDV() throws SQLException {
        Connection con = SERVER.Connect();
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            PreparedStatement statement = con.prepareStatement("EXEC usp_LookupTDV_Android '"+ dvcs +"','"+ username +"','"+ macbnv +"' " );
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    matdv = resultSet.getString("Ma_CbNv");
                    tentdv = resultSet.getString("Ten_CbNv");
                    arraymatdv.add(matdv + ' ' + tentdv);

                }
            }

            con.close();
        }
    }

    public void selectTuyenBH() throws SQLException {
        Connection con = SERVER.Connect();
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            PreparedStatement statement = con.prepareStatement("EXEC usp_LookupTUYENBH_Android '"+ dvcs +"','"+ username +"','"+ macbnv +"' " );
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    matbh = resultSet.getString("Ma_TuyenBanHang");
                    tentbh = resultSet.getString("Ten_TuyenBanHang");
                    arraymatbh.add(matbh + ' ' + tentbh);

                }
            }

            con.close();
        }
    }

}