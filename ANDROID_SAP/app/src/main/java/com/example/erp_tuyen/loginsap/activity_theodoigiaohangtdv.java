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

public class activity_theodoigiaohangtdv extends AppCompatActivity {
    private ArrayList<String> datadtghtdv = new ArrayList<String>();
    private ArrayList<String> datasohdghtdv = new ArrayList<String>();
    private ArrayList<String> datangayhdghtdv = new ArrayList<String>();
    private ArrayList<String> datangaydhghtdv = new ArrayList<String>();
    private ArrayList<String> datangayhtdhtdv = new ArrayList<String>();
    private ArrayList<String> datangayghtdv = new ArrayList<String>();

    ArrayList<String> arraymadt = new ArrayList<String>();

    private ArrayList<String> data4 = new ArrayList<String>();
    private TableLayout table;
    private TableRow row;

    EditText edtnghtdv,eddnghtdv;
    String madt, tendt,makh;
    AutoCompleteTextView atcdttdgh;
    ImageView imgdttdgh;

    TextView tvdtghtdv,tvhdghtdv,tvngayhdghtdv,tvngaydhghtdv,tvngayhtdhghtdv,tvngayghtdv;
    int flag=0;
    String dvcs,dvcs1,username, macbnv,IdUser;
    ResultSet resultSet;
    Button buttonbcghtdv;
    TableRow tbrghtdv;
    private ProgressDialog LoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theodoigiaohangtdv);
        getSupportActionBar().setTitle("Theo dõi giao hàng TDV");
        Intent intent=getIntent();
        dvcs=intent.getStringExtra("Ma_Dvcs");
        username = intent.getStringExtra("USERNAME");
        macbnv = intent.getStringExtra("Ma_CbNv");
        IdUser=intent.getStringExtra("IdUser");
        anhxa();
        String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        edtnghtdv.setText(date_n);
        eddnghtdv.setText(date_n);

        edtnghtdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                chonngay();
            }
        });
        eddnghtdv.setOnClickListener(new View.OnClickListener() {
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
        atcdttdgh.setAdapter(adapter1);

        imgdttdgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atcdttdgh.showDropDown();
            }
        });

        atcdttdgh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                madt = atcdttdgh.getText().toString();

                try {
                    selectdoituong();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonbcghtdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LoadingBar=new ProgressDialog(activity_theodoigiaohangtdv.this);
                LoadingBar.setTitle("BRAVO - ANDROID");
                LoadingBar.setMessage("Đang tải dữ liệu.... usp_TheoDoiHoaDonGiaoHang_Android");
                LoadingBar.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            table.removeViews(1, Math.max(0, table.getChildCount() - 1));
                            selectghtdv();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        LoadingBar.dismiss();
                    }
                },50);
            }
        });

        tbrghtdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TableRow t = (TableRow) v;
//                TextView firstTextView = (TextView) t.getChildAt(0);
//                TextView secondTextView = (TextView) t.getChildAt(1);
//                String firstText = firstTextView.getText().toString();
//                String secondText = secondTextView.getText().toString();
//
//                Toast.makeText(activity_theodoigiaohangtdv.this, secondText, Toast.LENGTH_SHORT).show();


            }
        });
    }

    void anhxa()
    {
        edtnghtdv=(EditText) findViewById(R.id.editTexttnghtdv);
        eddnghtdv=(EditText) findViewById(R.id.editTextdnghtdv);
        tvdtghtdv=(TextView) findViewById(R.id.tvdtghtdv);
        tvhdghtdv=(TextView) findViewById(R.id.tvhdghtdv);
        tvngayhdghtdv=(TextView) findViewById(R.id.tvngayghtdv);
        tvngaydhghtdv=(TextView) findViewById(R.id.tvndhghtdv);
        tvngayhtdhghtdv=(TextView) findViewById(R.id.tvnhtdhghtdv);
        tvngayghtdv=(TextView) findViewById(R.id.tvnghtdv);
        buttonbcghtdv=(Button) findViewById(R.id.buttonbcghtdv);
        table = (TableLayout) findViewById(R.id.tbghtdv);
        tbrghtdv=(TableRow) findViewById(R.id.tbrowghtdv);
        atcdttdgh=(AutoCompleteTextView) findViewById(R.id.actdttdgh);
        imgdttdgh=(ImageView) findViewById(R.id.imagedttdgh);
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
                    edtnghtdv.setText(simpleDateFormat.format(calendar.getTime()));
                }
                if(flag==1)
                {
                    eddnghtdv.setText(simpleDateFormat.format(calendar.getTime()));
                }
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }

    private void selectghtdv() throws SQLException {

        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {


            String tungay=edtnghtdv.getText().toString();
            String denngay=eddnghtdv.getText().toString();

            makh = atcdttdgh.getText().toString();
            if (makh.equals("")) {
                makh = "";

            }
            else {
                if (dvcs.equals("A02")) {
                    makh = atcdttdgh.getText().toString().substring(0, 16);
                } else {
                    makh = atcdttdgh.getText().toString().substring(0, 13);
                }
            }

            PreparedStatement statement = con.prepareStatement("EXEC usp_TheoDoiHoaDonGiaoHang_Android '"+ tungay +"' ,'"+ denngay +"','"+ makh +"','"+ dvcs +"' " );
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String doituong = resultSet.getString("Ten_Dt");
                    String soct=resultSet.getString("So_Ct");
                    String ngayct = resultSet.getString("Ngay_Ct");
                    if(ngayct != null) {
                        ngayct = ngayct.substring(0, 10);
                    }
                    String ngaydh = resultSet.getString("Ngay_Tao_Don_Hang");
                    if(ngaydh !=null) {
                        ngaydh=ngaydh.substring(0,10);
                    }
                    String ngayhtdh = resultSet.getString("Ngay_Hoan_Thien_DH");
                    if(ngayhtdh != null) {
                        ngayhtdh = ngayhtdh.substring(0, 10);
                    }
                    String ngaygh=resultSet.getString("Ngay_Giao_Hang");
                    if(ngaygh !=null) {
                        ngaygh = ngaygh.substring(0, 10);
                    }

                    datadtghtdv.add(doituong);
                    datasohdghtdv.add(soct);
                    datangayhdghtdv.add(ngayct);
                    datangaydhghtdv.add(ngaydh);
                    datangayhtdhtdv.add(ngayhtdh);
                    datangayghtdv.add(ngaygh);

//                    datadttdv.add(String.format("%,.0f", Float.valueOf(doanhthu)));
//                    datakhtdv.add(String.format("%,.0f", Float.valueOf(kehoach)));
//                    datatyletdv.add(String.format("%,.2f", Float.valueOf(tyle)));

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
                    t2.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t2.setHeight(70);
                    t3.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t3.setHeight(70);
                    t4.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t4.setHeight(70);
                    t5.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t5.setHeight(70);
                    t6.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t6.setHeight(70);

                    for (int j = 0; j < datadtghtdv.size(); j++) {
                        String pdt = datadtghtdv.get(j);
                        String psoct = datasohdghtdv.get(j);
                        String pngayct = datangayhdghtdv.get(j);
                        String pngaydh = datangaydhghtdv.get(j);

                        String pnngayhtdh = datangayhtdhtdv.get(j);
                        String pngaygh =datangayghtdv.get(j);

                        t1.setText(pdt);
                        t2.setText(psoct);
                        t3.setText(pngayct);
                        t4.setText(pngaydh);
                        t5.setText(pnngayhtdh);
                        t6.setText(pngaygh);
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