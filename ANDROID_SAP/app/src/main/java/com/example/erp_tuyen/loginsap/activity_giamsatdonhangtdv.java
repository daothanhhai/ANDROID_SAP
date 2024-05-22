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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class activity_giamsatdonhangtdv extends AppCompatActivity {

    private ArrayList<String> datatdvgsdhtdv = new ArrayList<String>();

    private ArrayList<String> datatongkhgsdhtdv = new ArrayList<String>();
    private ArrayList<String> datatongkhvtgsdhtdv = new ArrayList<String>();
    private ArrayList<String> datadhgsdhtdv = new ArrayList<String>();
    private ArrayList<String> datadhhtgsdhtdv = new ArrayList<String>();
    private ArrayList<String> datadtgsdhtdv = new ArrayList<String>();

    EditText edtngsdhtdv,eddngsdhtdv;

    int flag=0;
    String dvcs,macbnv,chucdanh,username;
    ResultSet resultSet;
    Button buttonbcgsdhtdv;
    TableRow tbrgsdhtdv;
    private ProgressDialog LoadingBar;

    private ArrayList<String> data4 = new ArrayList<String>();
    private TableLayout table;
    private TableRow row;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giamsatdonhangtdv);
        getSupportActionBar().setTitle("Giám sát đơn hàng theo TDV");
        Intent intent=getIntent();
        dvcs=intent.getStringExtra("Ma_Dvcs");
        macbnv=intent.getStringExtra("Ma_CbNv");
        chucdanh=intent.getStringExtra("Chuc_Danh");
        username=intent.getStringExtra("USERNAME");
        anhxa();


        String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        edtngsdhtdv.setText(date_n);
        eddngsdhtdv.setText(date_n);

        edtngsdhtdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                chonngay();
            }
        });
        eddngsdhtdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
                chonngay();
            }
        });

        buttonbcgsdhtdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingBar=new ProgressDialog(activity_giamsatdonhangtdv.this);
                LoadingBar.setTitle("BRAVO - ANDROID");
                LoadingBar.setMessage("Đang tải dữ liệu.... usp_QLDonHangTheoTDV_Android");
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

    void anhxa()
    {
        edtngsdhtdv=(EditText) findViewById(R.id.editTexttngsdhtdv);
        eddngsdhtdv=(EditText) findViewById(R.id.editTextdngsdhtdv);


        buttonbcgsdhtdv=(Button) findViewById(R.id.buttonbcgsdhtdv);
        table = (TableLayout) findViewById(R.id.tbgsdhtdv);
        tbrgsdhtdv=(TableRow) findViewById(R.id.tbrowgsdhtdv);
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
                    edtngsdhtdv.setText(simpleDateFormat.format(calendar.getTime()));
                }
                if(flag==1)
                {
                    eddngsdhtdv.setText(simpleDateFormat.format(calendar.getTime()));
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


            String tungay=edtngsdhtdv.getText().toString();
            String denngay=eddngsdhtdv.getText().toString();

            PreparedStatement statement = con.prepareStatement("EXEC usp_QLDonHangTheoTDV_Android '"+ tungay +"' ,'"+ denngay +"'" +
                    ",'"+ dvcs +"','"+ username +"','"+ macbnv +"' " );
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String tdv = resultSet.getString("Ten_TDV");

                    String tongkh=resultSet.getString("Tong_KH");

                    String tongkhvt=resultSet.getString("Tong_KH_VT");

                    String donhang = resultSet.getString("Don_Hang");

                    String donhanght = resultSet.getString("Don_Hang_HT");

                    String doanhthu = resultSet.getString("Doanh_Thu");




                    datatdvgsdhtdv.add(tdv);
                    datatongkhgsdhtdv.add(tongkh);
                    datatongkhvtgsdhtdv.add(tongkhvt);
                    datadhgsdhtdv.add(donhang);
                    datadhhtgsdhtdv.add(donhanght);

                    datadtgsdhtdv.add(String.format("%,.0f", Float.valueOf(doanhthu)));



                    row = new TableRow(this);
                    TextView t1 = new TextView(this);
                    TextView t2 = new TextView(this);
                    TextView t3 = new TextView(this);
                    TextView t4 = new TextView(this);

                    TextView t5 = new TextView(this);
                    TextView t6 = new TextView(this);


                    t1.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t1.setWidth(300);
                    t1.setHeight(70);
                    t1.setTextSize(14);
                    t1.setTextColor(Color.MAGENTA);

                    t2.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t2.setHeight(70);
                    t2.setTextColor(Color.BLACK);

                    t3.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t3.setHeight(70);
                    t3.setTextColor(Color.BLACK);

                    t4.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t4.setHeight(70);
                    t4.setTextColor(Color.BLACK);

                    t5.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t5.setHeight(70);
                    t5.setTextColor(Color.BLACK);

                    t6.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t6.setHeight(70);
                    t6.setTextColor(Color.BLACK);


                    for (int j = 0; j < datatdvgsdhtdv.size(); j++) {
                        String ptdv = datatdvgsdhtdv.get(j);
                        String ptkh = datatongkhgsdhtdv.get(j);
                        String ptkhvt = datatongkhvtgsdhtdv.get(j);
                        String pdonhang = datadhgsdhtdv.get(j);
                        String pdonhanght = datadhhtgsdhtdv.get(j);
                        String pdoanhthu = datadtgsdhtdv.get(j);

                        t1.setText(ptdv);
                        t2.setText(ptkh);
                        t3.setText(ptkhvt);
                        t4.setText(pdonhang);
                        t5.setText(pdonhanght);
                        t6.setText(pdoanhthu);

                    }
                    row.setBackground(ContextCompat.getDrawable(this,R.drawable.border));

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
        }


    }
}