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

public class activity_khkhongpsdoanhthu extends AppCompatActivity {
    private ArrayList<String> datatendtkpsdt = new ArrayList<String>();
    private ArrayList<String> datangaydatcuoikpsdt = new ArrayList<String>();
    private ArrayList<String> data4 = new ArrayList<String>();

    ArrayList<String> arraymadt = new ArrayList<String>();

    ArrayList<String> arraymatdv = new ArrayList<String>();

    private TableLayout table;
    private TableRow row;
    TextView tvdtkpsdt,tvndckpsdt;
    int flag=0;
    String dvcs,macbnv,chucdanh,username;

    String madt, tendt,makh;
    String matdv,matdvlk,tentdv;
    AutoCompleteTextView atckpsdtdt,atckpsdttdv;
    ImageView imgkpsdtdt,imgkpsdttdv;

    ResultSet resultSet;
    Button buttonbcdtkpsdt;
    TableRow tbrdtkpsdt;
    private ProgressDialog LoadingBar;

    EditText edtndtkpsdt,eddndtkpsdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khkhongpsdoanhthu);
        getSupportActionBar().setTitle("Khách hàng không phát sinh doanh thu");
        Intent intent=getIntent();
        dvcs=intent.getStringExtra("Ma_Dvcs");
        macbnv=intent.getStringExtra("Ma_CbNv");
        chucdanh=intent.getStringExtra("Chuc_Danh");
        username=intent.getStringExtra("USERNAME");
        anhxa();
        String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        edtndtkpsdt.setText(date_n);
        eddndtkpsdt.setText(date_n);

        edtndtkpsdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                chonngay();
            }
        });
        eddndtkpsdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
                chonngay();
            }
        });

        try {
            selectdoituong();
            selectTDV();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraymadt);
        atckpsdtdt.setAdapter(adapter1);

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraymatdv);
        atckpsdttdv.setAdapter(adapter2);


        buttonbcdtkpsdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingBar=new ProgressDialog(activity_khkhongpsdoanhthu.this);
                LoadingBar.setTitle("OPC - APP - MOBILE");
                LoadingBar.setMessage("Đang tải dữ liệu.... usp_KhachHangKoPSDonHang_Android");
                LoadingBar.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            table.removeViews(1, Math.max(0, table.getChildCount() - 1));
                            selectdtkpsdt();
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
        edtndtkpsdt=(EditText) findViewById(R.id.editTexttnkhkpsdt);
        eddndtkpsdt=(EditText) findViewById(R.id.editTextdnkhkpsdt);
        tvdtkpsdt=(TextView) findViewById(R.id.tvdtkpsdt);
        tvndckpsdt=(TextView) findViewById(R.id.tvndckpsdt);
        buttonbcdtkpsdt=(Button) findViewById(R.id.buttonbckhkpsdt);
        table = (TableLayout) findViewById(R.id.tbkhkpsdt);
        tbrdtkpsdt=(TableRow) findViewById(R.id.tbrowkhkpsdt);
        atckpsdtdt=(AutoCompleteTextView) findViewById(R.id.actbckpsdtdt);
        atckpsdttdv=(AutoCompleteTextView) findViewById(R.id.actbckpsdttdv);
        imgkpsdtdt=(ImageView) findViewById(R.id.imagebckpsdtdt);
        imgkpsdttdv=(ImageView) findViewById(R.id.imagebckpsdttdv);
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
                    edtndtkpsdt.setText(simpleDateFormat.format(calendar.getTime()));
                }
                if(flag==1)
                {
                    eddndtkpsdt.setText(simpleDateFormat.format(calendar.getTime()));
                }
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }

    private void selectdtkpsdt() throws SQLException {

        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {


            String tungay=edtndtkpsdt.getText().toString();
            String denngay=eddndtkpsdt.getText().toString();


            makh = atckpsdtdt.getText().toString();
            if (makh.equals("")) {
                makh = "";

            } else {

                if (dvcs.equals("A02")) {
                    makh = atckpsdtdt.getText().toString().substring(0, 16);
                } else {
                    makh = atckpsdtdt.getText().toString().substring(0, 13);
                }
            }

            matdvlk = atckpsdttdv.getText().toString();
            if (matdvlk.equals("")) {
                matdvlk = "";

            } else {
                matdvlk = atckpsdttdv.getText().toString().substring(0, 8);
            }


            PreparedStatement statement = con.prepareStatement("EXEC usp_KhachHangKoPSDonHang_Android '"+ tungay +"' ,'"+ denngay +"'" +
                    ",'"+ dvcs +"','"+ macbnv +"','"+ matdvlk +"','"+ makh +"','"+ username +"' " );
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String doituong = resultSet.getString("Ten_Dt");

                    String ngayct = resultSet.getString("Lan_Dat_Cuoi");
                    if(ngayct != null) {
                        ngayct = ngayct.substring(0, 10);
                    }


                    datatendtkpsdt.add(doituong);
                    datangaydatcuoikpsdt.add(ngayct);

                    row = new TableRow(this);
                    TextView t1 = new TextView(this);
                    TextView t2 = new TextView(this);

                    t1.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t1.setWidth(300);
                    t1.setHeight(70);
                    t1.setTextSize(14);
                    t1.setTextColor(Color.BLUE);

                    t2.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t2.setHeight(70);
                    t2.setTextColor(Color.RED);


                    for (int j = 0; j < datatendtkpsdt.size(); j++) {
                        String pdt = datatendtkpsdt.get(j);
                        String pngayct = datangaydatcuoikpsdt.get(j);


                        t1.setText(pdt);

                        t2.setText(pngayct);

                    }
                    row.setBackground(ContextCompat.getDrawable(this,R.drawable.border));

                    row.addView(t1);
                    row.addView(t2);

                    table.addView(row);

                }
            }

            con.close();
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
}