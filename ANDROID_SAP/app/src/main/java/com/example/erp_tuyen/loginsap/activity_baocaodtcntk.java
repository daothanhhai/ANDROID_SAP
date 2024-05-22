package com.example.erp_tuyen.loginsap;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
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

public class activity_baocaodtcntk extends AppCompatActivity {

    private ArrayList<String> datacndtcntk = new ArrayList<String>();
    private ArrayList<String> datakhopcdtcntk = new ArrayList<String>();
    private ArrayList<String> datathopcdtcntk = new ArrayList<String>();
    private ArrayList<String> dataptkhopcdtcntk = new ArrayList<String>();


    private ArrayList<String> datakhcondtcntk = new ArrayList<String>();
    private ArrayList<String> datathcondtcntk = new ArrayList<String>();
    private ArrayList<String> dataptkhcondtcntk = new ArrayList<String>();

    private ArrayList<String> datakhsuidtcntk = new ArrayList<String>();
    private ArrayList<String> datathsuidtcntk = new ArrayList<String>();
    private ArrayList<String> dataptkhsuidtcntk = new ArrayList<String>();

    private ArrayList<String> datamlkhdtcntk = new ArrayList<String>();

    ArrayList<String> arraymadt = new ArrayList<String>();

    private ArrayList<String> data4 = new ArrayList<String>();
    private TableLayout table;
    private TableRow row;

    EditText edtndtcntk,eddndtcntk;
    AutoCompleteTextView atcdtdtcntk;
    ImageView imgbcdtcntk;

    int flag=0;
    String dvcs,dvcs1,username, macbnv,IdUser;
    String madt, tendt,makh;
    ResultSet resultSet;
    Button buttonbcdtcntk;
    TableRow tbrdtcntk;
    HorizontalScrollView horizontalScrollView;
    private ProgressDialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baocaodtcntk);

        getSupportActionBar().setTitle("Báo cáo công nợ chi tiết");
        Intent intent=getIntent();
        dvcs=intent.getStringExtra("Ma_Dvcs");
        username = intent.getStringExtra("USERNAME");
        macbnv = intent.getStringExtra("Ma_CbNv");
        IdUser=intent.getStringExtra("IdUser");
        taothongtinCN();
        anhxa();
        String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        edtndtcntk.setText(date_n);
        eddndtcntk.setText(date_n);


        edtndtcntk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 0;
                chonngay();
            }
        });

        eddndtcntk.setOnClickListener(new View.OnClickListener() {
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
        atcdtdtcntk.setAdapter(adapter1);

        imgbcdtcntk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atcdtdtcntk.showDropDown();
            }
        });

        atcdtdtcntk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                madt = atcdtdtcntk.getText().toString();

                try {
                    selectdoituong();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonbcdtcntk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingBar = new ProgressDialog(activity_baocaodtcntk.this);
                LoadingBar.setTitle("OPC - MOBILE");
                LoadingBar.setMessage("Đang tải dữ liệu.... usp_Vth_BC_BHCNTK_CN");
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
        edtndtcntk=(EditText) findViewById(R.id.edittexttndtcntk);
        eddndtcntk=(EditText) findViewById(R.id.edittextdndtcntk);
        atcdtdtcntk=(AutoCompleteTextView) findViewById(R.id.actdtcntk);
        imgbcdtcntk=(ImageView) findViewById(R.id.imagedtcntk);

        buttonbcdtcntk=(Button) findViewById(R.id.buttonbcdtcntk);
        tbrdtcntk=(TableRow) findViewById(R.id.tbrdtcntk);
        table = (TableLayout) findViewById(R.id.tbdtcntk);
        horizontalScrollView=(HorizontalScrollView) findViewById(R.id.progress_horizontal);
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
                    edtndtcntk.setText(simpleDateFormat.format(calendar.getTime()));
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

            String tungay=edtndtcntk.getText().toString();
            String denngay=eddndtcntk.getText().toString();
            //String madt=atcdtcnkh.getText().toString();

            makh = atcdtdtcntk.getText().toString();
            if (makh.equals("")) {
                makh = "";

            } else {
                makh = atcdtdtcntk.getText().toString().substring(0, 13);
            }

            PreparedStatement statement = con.prepareStatement("usp_Vth_BC_BHCNTK_CN" +
                    "'" + tungay + "' ,'" + denngay + "','','','','','','',''" +
                    ",'','','','',0,0,'"+ dvcs +"',''  " +
                    ",'" + username + "','VND','','',0 ");
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String tencn = resultSet.getString("Ten_DvCs1");
                    String khopc=resultSet.getString("Doanh_Thu_K");
                    String thopc=resultSet.getString("Doanh_Thu");
                    String tlopc=resultSet.getString("Ty_Le_OPC");

                    String khcon=resultSet.getString("Doanh_Thu_K_Con");
                    String thcon=resultSet.getString("Doanh_Thu_Con");
                    String tlcon=resultSet.getString("Ty_Le_Con");

                    String khsui=resultSet.getString("Doanh_Thu_K_Sui");
                    String thsui=resultSet.getString("Doanh_Thu_Sui_Tong");
                    String tlsui=resultSet.getString("Ty_Le_Sui");




                    datacndtcntk.add(tencn);

                    datakhopcdtcntk.add(String.format("%,.0f", Float.valueOf(khopc)));
                    datathopcdtcntk.add(String.format("%,.0f", Float.valueOf(thopc)));
                    dataptkhopcdtcntk.add(String.format("%,.0f", Float.valueOf(tlopc)));

                    datakhcondtcntk.add(String.format("%,.0f", Float.valueOf(khcon)));
                    datathcondtcntk.add(String.format("%,.0f", Float.valueOf(thcon)));
                    dataptkhcondtcntk.add(String.format("%,.0f", Float.valueOf(tlcon)));

                    datakhsuidtcntk.add(String.format("%,.0f", Float.valueOf(khsui)));
                    datathsuidtcntk.add(String.format("%,.0f", Float.valueOf(thsui)));
                    dataptkhsuidtcntk.add(String.format("%,.0f", Float.valueOf(tlsui)));



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
                    TextView t10 = new TextView(this);




                    t1.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t1.setWidth(200);
                    t1.setHeight(100);
                    t2.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t2.setHeight(100);
                    t3.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t3.setHeight(100);
                    t4.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t4.setHeight(100);
                    t5.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t5.setHeight(100);


                    for (int j = 0; j < datacndtcntk.size(); j++) {
                        String pcn = datacndtcntk.get(j);
                        String pkhopc = datakhopcdtcntk.get(j);
                        String pthopc = datathopcdtcntk.get(j);
                        String ptlopc = dataptkhopcdtcntk.get(j);

                        String pkhcon = datakhcondtcntk.get(j);
                        String pthcon = datathcondtcntk.get(j);
                        String ptlcon = dataptkhcondtcntk.get(j);

                        String pkhsui = datakhsuidtcntk.get(j);
                        String pthsui = datathsuidtcntk.get(j);
                        String ptlsui = dataptkhsuidtcntk.get(j);




                        t1.setText(pcn);

                        t2.setText(pkhopc);
                        t3.setText(pthopc);
                        t4.setText(ptlopc);

                        t5.setText(pkhcon);
                        t6.setText(pthcon);
                        t7.setText(ptlcon);

                        t8.setText(pkhsui);
                        t9.setText(pthsui);
                        t10.setText(ptlsui);



                    }
                    row.setBackground(ContextCompat.getDrawable(this,R.drawable.border));

                    row.addView(t1);
                    row.addView(t2);
                    row.addView(t3);
                    row.addView(t4);
                    row.addView(t5);
                    row.addView(t6);
                    row.addView(t7);
                    row.addView(t8);
                    row.addView(t9);
                    row.addView(t10);
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