package com.example.erp_tuyen.loginsap;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
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

public class activity_tonkhotrinhduocvien extends AppCompatActivity {
    private ArrayList<String> datavttktdv = new ArrayList<String>();
    private ArrayList<String> datadvttktdv = new ArrayList<String>();
    private ArrayList<String> datasolotktdv = new ArrayList<String>();
    private ArrayList<String> datanhandungtktdv = new ArrayList<String>();
    private ArrayList<String> datasltktdv = new ArrayList<String>();


    private ArrayList<String> data4 = new ArrayList<String>();
    private TableLayout table;
    private TableRow row;

    EditText edtntktdv;
    AutoCompleteTextView atcvttktdv;
    TextView tvvttktdv,tvdvttktdv,tvsolotktdv,tvhandungtktdv,tvsoluongtktdv;
    int flag=0;
    String dvcs,dvcs1,makho;
    ResultSet resultSet;
    Button buttonbctktdv;
    TableRow tbrtktdv;
    HorizontalScrollView horizontalScrollView;
    private ProgressDialog LoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tonkhotrinhduocvien);
        getSupportActionBar().setTitle("Báo cáo tồn Kho");
        Intent intent=getIntent();
        dvcs=intent.getStringExtra("Ma_Dvcs");
        taothongtinCN();
        anhxa();
        String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        edtntktdv.setText(date_n);




        buttonbctktdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoadingBar=new ProgressDialog(activity_tonkhotrinhduocvien.this);
                LoadingBar.setTitle("BRAVO - ANDROID");
                LoadingBar.setMessage("Đang tải dữ liệu.... usp_Vcd_BaoCaoTonKho_Android");
                LoadingBar.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            table.removeViews(1, Math.max(0, table.getChildCount() - 1));
                            selecttktdv();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        LoadingBar.dismiss();
                    }
                },5000);
            }
        });


    }

    void anhxa()
    {
        edtntktdv=(EditText) findViewById(R.id.editTextdntktdv);
        atcvttktdv=(AutoCompleteTextView) findViewById(R.id.actvtktdv);

        tbrtktdv=(TableRow) findViewById(R.id.tbrowtktdv);
        table = (TableLayout) findViewById(R.id.tbtktdv);
        buttonbctktdv=(Button) findViewById(R.id.buttonbctktdv);
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
                    edtntktdv.setText(simpleDateFormat.format(calendar.getTime()));
                }

            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }

    private void selecttktdv() throws SQLException {

        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {


            String tungay=edtntktdv.getText().toString();
            String mavt=atcvttktdv.getText().toString();

            PreparedStatement statement = con.prepareStatement("EXEC usp_Vcd_BaoCaoTonKho_Android " +
                    "'"+ tungay +"','"+ makho +"',N'"+ mavt +"','',0,0 ,'"+ dvcs +"',N'Admin','','' " );
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String vattu = resultSet.getString("Ten_Vt");
                    String dvt=resultSet.getString("Dvt");
                    String solo = resultSet.getString("So_Lo");
                    String handung = resultSet.getString("ExpiredDate");
                    if(handung !=null)
                    {
                        handung=handung.substring(0,10);
                    }

                    String soluong = resultSet.getString("Ton_Cuoi");

                    datavttktdv.add(vattu);
                    datadvttktdv.add(dvt);
                    datasolotktdv.add(solo);
                    datanhandungtktdv.add(handung);
                    datasltktdv.add(String.format("%,.0f", Float.valueOf(soluong)));


                    row = new TableRow(this);
                    TextView t1 = new TextView(this);
                    TextView t2 = new TextView(this);
                    TextView t3 = new TextView(this);
                    TextView t4 = new TextView(this);
                    TextView t5 = new TextView(this);

                    t1.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t1.setWidth(200);
                    t1.setHeight(50);
                    t2.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t2.setWidth(70);
                    t2.setHeight(50);
                    t3.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t3.setHeight(50);
                    t4.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t4.setHeight(50);
                    t5.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t5.setHeight(50);



                    for (int j = 0; j < datavttktdv.size(); j++) {
                        String pvt = datavttktdv.get(j);
                        String pdvt = datadvttktdv.get(j);
                        String psolo = datasolotktdv.get(j);
                        String phandung = datanhandungtktdv.get(j);

                        String psoluong = datasltktdv.get(j);


                        t1.setText(pvt);
                        t1.setTypeface(null, Typeface.BOLD);
                        t2.setText(pdvt);
                        t3.setText(psolo);
                        t4.setText(phandung);
                        t5.setText(psoluong);

                        t5.setGravity(Gravity.RIGHT);


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
            makho="TP1N1-02";
        }
        if(dvcs.equals("A02"))
        {
            dvcs1="2N101-01";
            makho="TP2N1-01";

        }
        if(dvcs.equals("A03"))
        {
            dvcs1="2N301";
            makho="TP2N3-01";


        }

        if(dvcs.equals("A04"))
        {
            dvcs1="2N302";
            makho="TP2N3-02";

        }
        if(dvcs.equals("A05"))
        {
            dvcs1="2N201";
            makho="TP2N2-01";

        }
        if(dvcs.equals("A06"))
        {
            dvcs1="2N202";
            makho="TP2N2-02";

        }
        if(dvcs.equals("A07"))
        {
            dvcs1="2T101";
            makho="TP2T1-01";

        }
        if(dvcs.equals("A08"))
        {
            dvcs1="2T102";
            makho="TP2T1-02";

        }
        if(dvcs.equals("A09"))
        {
            dvcs1="2T103";
            makho="TP2T1-03";

        }
        if(dvcs.equals("A10"))
        {
            dvcs1="2B101";
            makho="TP2B1-01";

        }
    }


}