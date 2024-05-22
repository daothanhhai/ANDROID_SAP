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

public class activity_congnotheokhachhang extends AppCompatActivity {

    private ArrayList<String> datakhcnkh = new ArrayList<String>();
    private ArrayList<String> datasodudaucnkh = new ArrayList<String>();
    private ArrayList<String> datasophatsinhcnkh = new ArrayList<String>();
    private ArrayList<String> datatranocnkh = new ArrayList<String>();
    private ArrayList<String> datasoducuoicnkh = new ArrayList<String>();

    ArrayList<String> arraymadt = new ArrayList<String>();

    private ArrayList<String> data4 = new ArrayList<String>();
    private TableLayout table;
    private TableRow row;

    EditText edtncnkh,eddncnkh;
    AutoCompleteTextView atcdtcnkh;
    ImageView imgbccnkh;
    TextView tvdtcnkh,tvsodudaucnkh,tvsophatsinhcnkh,tvtranocnkh,tvsoducuoicnkh;
    int flag=0;
    String dvcs,dvcs1,username, macbnv,IdUser;
    String madt, tendt,makh;
    ResultSet resultSet;
    Button buttonbccnkh;
    TableRow tbrcnkh;
    HorizontalScrollView horizontalScrollView;
    private ProgressDialog LoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congnotheokhachhang);
        getSupportActionBar().setTitle("Báo cáo công nợ");
        Intent intent=getIntent();
        dvcs=intent.getStringExtra("Ma_Dvcs");
        username = intent.getStringExtra("USERNAME");
        macbnv = intent.getStringExtra("Ma_CbNv");
        IdUser=intent.getStringExtra("IdUser");
        taothongtinCN();
        anhxa();
        String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        edtncnkh.setText(date_n);
        eddncnkh.setText(date_n);


        try {
            selectdoituong();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraymadt);
        atcdtcnkh.setAdapter(adapter1);


        imgbccnkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atcdtcnkh.showDropDown();
            }
        });

        atcdtcnkh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                madt = atcdtcnkh.getText().toString();

                try {
                    selectdoituong();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });





        buttonbccnkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingBar = new ProgressDialog(activity_congnotheokhachhang.this);
                LoadingBar.setTitle("BRAVO - ANDROID");
                LoadingBar.setMessage("Đang tải dữ liệu.... usp_Kcd_SoTongHopCongNo_Android");
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
        edtncnkh=(EditText) findViewById(R.id.edittexttncnkh);
        eddncnkh=(EditText) findViewById(R.id.edittextdncnkh);
        atcdtcnkh=(AutoCompleteTextView) findViewById(R.id.actcnkh);
        imgbccnkh=(ImageView) findViewById(R.id.imagecnkh);
        tvdtcnkh=(TextView) findViewById(R.id.tvdtcnkh);
        tvsodudaucnkh=(TextView) findViewById(R.id.tvsodudaucnkh);
        tvsophatsinhcnkh=(TextView) findViewById(R.id.tvsophatsinhcnkh);
        tvtranocnkh=(TextView) findViewById(R.id.tvtranocnkh);
        tvsoducuoicnkh=(TextView) findViewById(R.id.tvsoducuoicnkh);
        buttonbccnkh=(Button) findViewById(R.id.buttonbccnkh);
        tbrcnkh=(TableRow) findViewById(R.id.tbrcnkh);
        table = (TableLayout) findViewById(R.id.tbcnkh);
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
                    edtncnkh.setText(simpleDateFormat.format(calendar.getTime()));
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

            String tungay=edtncnkh.getText().toString();
            String denngay=eddncnkh.getText().toString();
            //String madt=atcdtcnkh.getText().toString();

            makh = atcdtcnkh.getText().toString();
            if (makh.equals("")) {
                makh = "";

            }
            else {
                if (dvcs.equals("A02")) {
                    makh = atcdtcnkh.getText().toString().substring(0, 16);
                } else {
                    makh = atcdtcnkh.getText().toString().substring(0, 13);
                }
            }

            PreparedStatement statement = con.prepareStatement("EXEC usp_Kcd_SoTongHopCongNo_Android " +
                    "'"+ tungay +"','"+ denngay +"','131',N'"+ makh +"',0,0 ,'"+ dvcs +"','',N'"+ username +"','','VND','' " );
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String tendt = resultSet.getString("Ten_Dt");
                    String sodunodau=resultSet.getString("Du_No1");
                    String sophatsinhno = resultSet.getString("Ps_No");
                    String sotrano = resultSet.getString("Ps_Co");
                    String soducuoi = resultSet.getString("Du_No2");

                    datakhcnkh.add(tendt);


                    datasodudaucnkh.add(String.format("%,.0f", Float.valueOf(sodunodau)));
                    datasophatsinhcnkh.add(String.format("%,.0f", Float.valueOf(sophatsinhno)));
                    datatranocnkh.add(String.format("%,.0f", Float.valueOf(sotrano)));
                    datasoducuoicnkh.add(String.format("%,.0f", Float.valueOf(soducuoi)));

                    row = new TableRow(this);
                    TextView t1 = new TextView(this);
                    TextView t2 = new TextView(this);
                    TextView t3 = new TextView(this);
                    TextView t4 = new TextView(this);
                    TextView t5 = new TextView(this);

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


                    for (int j = 0; j < datakhcnkh.size(); j++) {
                        String pdt = datakhcnkh.get(j);
                        String psdd = datasodudaucnkh.get(j);
                        String psps = datasophatsinhcnkh.get(j);
                        String pstn = datatranocnkh.get(j);
                        String psdc = datasoducuoicnkh.get(j);


                        t1.setText(pdt);
                        t2.setText(psdd);
                        t3.setText(psps);
                        t4.setText(pstn);
                        t5.setText(psdc);

                    }
                    row.setBackground(ContextCompat.getDrawable(this,R.drawable.border));

                    row.addView(t1);
                    row.addView(t2);
                    row.addView(t3);
                    row.addView(t4);
                    row.addView(t5);

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