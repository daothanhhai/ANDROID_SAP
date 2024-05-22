package com.example.erp_tuyen.loginsap;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.graphics.Color;

public class activity_doanhthutrinhduocvien extends AppCompatActivity {
    private ArrayList<String> datatdv = new ArrayList<String>();
    private ArrayList<String> datadttdv = new ArrayList<String>();
    private ArrayList<String> datakhtdv = new ArrayList<String>();
    private ArrayList<String> datatyletdv = new ArrayList<String>();
    private ArrayList<String> data4 = new ArrayList<String>();
    private TableLayout table;
    private TableRow row;
    ResultSet resultSet;
    TextView tvtdv,tvdttdv,tvkhtdv,tvtltdv;
    EditText edtntdv,eddntdv;
    Button buttonbctdv;
    PieChart pieChartdttdv;
    ArrayList<PieEntry> visitors=new ArrayList<>();
    int flag=0;
    String dvcs,macbnv,chucdanh,username;
    private ProgressDialog LoadingBar;
    Float Tong_TH_OPC,Tong_TH_Con,Tong_TH_HD2,Tong_TH_Phien,Tong_TH_25,Tong_TH_ComBo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanhthutrinhduocvien);
        getSupportActionBar().setTitle("Doanh Thu TDV");
        Intent intent=getIntent();
        dvcs=intent.getStringExtra("Ma_Dvcs");
        macbnv=intent.getStringExtra("Ma_CbNv");
        chucdanh=intent.getStringExtra("Chuc_Danh");
        username=intent.getStringExtra("USERNAME");
        anhxa();
        String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        edtntdv.setText(date_n);
        eddntdv.setText(date_n);

        edtntdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                chonngay();
            }
        });
        eddntdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
                chonngay();
            }
        });

        buttonbctdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingBar=new ProgressDialog(activity_doanhthutrinhduocvien.this);
                LoadingBar.setTitle("SAP - ANDROID");
                LoadingBar.setMessage("Đang tải dữ liệu.... usp_BaoCaoDoanhThuTDV_SAP");
                LoadingBar.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            table.removeViews(1, Math.max(0, table.getChildCount() - 1));
                            selectdoanhthutdv();
                            selecttyledt();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        LoadingBar.dismiss();
                    }
                },500);
            }
        });

    }

    private void anhxa()
    {
        tvtdv=(TextView) findViewById(R.id.tvtdv);
        tvdttdv=(TextView) findViewById(R.id.tvdoanhthutdv);
        tvkhtdv=(TextView) findViewById(R.id.tvkhtdv);
        tvtltdv=(TextView) findViewById(R.id.tvtltdv);
        table = (TableLayout) findViewById(R.id.tbdttdv);
        edtntdv=(EditText) findViewById(R.id.editTexttntdv);
        eddntdv=(EditText) findViewById(R.id.editTextdntdv);
        buttonbctdv=(Button) findViewById(R.id.buttonbctdv);
        pieChartdttdv=(PieChart) findViewById(R.id.bardttdv);
    }

    private void selectdoanhthutdv() throws SQLException {

        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            String tungay=edtntdv.getText().toString();
            String denngay=eddntdv.getText().toString();

            PreparedStatement statement = con.prepareStatement("usp_BaoCaoDoanhThuTDV_SAP" +
                    "'"+ tungay +"' ,'"+ denngay +"' ,'"+ macbnv +"' " );
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String thoigian = resultSet.getString("Ghi_Chu");
                    String tentdv=resultSet.getString("Ten_TDV");
                    String doanhthu = resultSet.getString("Doanh_Thu");
                    String kehoach = resultSet.getString("Tong_Cong_K");
                    String tyle = resultSet.getString("Ty_Le_Dt");


                    datatdv.add(tentdv);
                    if(doanhthu==null)
                    {
                        doanhthu="0";
                    }
                    if(kehoach==null)
                    {
                        kehoach="0";
                    }

                    if(tyle==null)
                    {
                        tyle="0";
                    }


                    datadttdv.add(String.format("%,.0f", Float.valueOf(doanhthu)));
                    datakhtdv.add(String.format("%,.0f", Float.valueOf(kehoach)));
                    datatyletdv.add(String.format("%,.2f", Float.valueOf(tyle))+""+"%");

                    row = new TableRow(this);
                    TextView t1 = new TextView(this);
                    TextView t2 = new TextView(this);
                    TextView t3 = new TextView(this);
                    TextView t4 = new TextView(this);


                    if(tentdv.equals("Doanh_Thu"))
                    {
                        t1.setTextSize(20);
                        t1.setTextColor(Color.RED);
                        t2.setTextSize(20);
                        t2.setTextColor(Color.RED);
                        t3.setTextSize(20);
                        t3.setTextColor(Color.RED);
                        t4.setTextSize(20);
                        t4.setTextColor(Color.RED);

                    }

                    t1.setBackground(ContextCompat.getDrawable(this,R.drawable.border));

                    t2.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t2.setGravity(Gravity.RIGHT);
                    t3.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t3.setGravity(Gravity.RIGHT);
                    t4.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t4.setGravity(Gravity.RIGHT);




                    for (int j = 0; j < datatdv.size(); j++) {
                        String ptg = datatdv.get(j);
                        String pdt = datadttdv.get(j);
                        String pkh = datakhtdv.get(j);
                        String ptl = datatyletdv.get(j);

                        t1.setText(ptg);
                        t2.setText(pdt);
                        t3.setText(pkh);
                        t4.setText(ptl);
                    }
                    row.setBackground(ContextCompat.getDrawable(this,R.drawable.border));

                    row.addView(t1);
                    row.addView(t2);
                    row.addView(t3);
                    row.addView(t4);
                    table.addView(row);

                }

            }

            con.close();
        }

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
                    edtntdv.setText(simpleDateFormat.format(calendar.getTime()));
                }
                if(flag==1)
                {
                    eddntdv.setText(simpleDateFormat.format(calendar.getTime()));
                }
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }


    public void selecttyledt() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
            String tungay=edtntdv.getText().toString();
            String denngay=eddntdv.getText().toString();


            try {

//                "'"+ tungay +"' ,'"+ denngay +"','','','','','','','','','','','','',0,0,0,0,'"+ dvcs +"','"+ dvcs +"','' ," +
//                        "'"+ username +"','','','',0,0,0,0 " );
                CallableStatement cstmt = con.prepareCall("{call usp_RpDoanhThuChart_SAP(?,?,?,?,?,?,?,?,?)}");

                cstmt.setString(1, tungay);
                cstmt.setString(2, denngay);
                cstmt.setString(3, macbnv);

                cstmt.setInt(4, 0);
                cstmt.setInt(5, 0);
                cstmt.setInt(6, 0);
                cstmt.setInt(7, 0);
                cstmt.setInt(8, 0);
                cstmt.setInt(9, 0);


                cstmt.registerOutParameter(4, Types.FLOAT);
                cstmt.registerOutParameter(5, Types.FLOAT);
                cstmt.registerOutParameter(6, Types.FLOAT);
                cstmt.registerOutParameter(7, Types.FLOAT);
                cstmt.registerOutParameter(8, Types.FLOAT);
                cstmt.registerOutParameter(9, Types.FLOAT);

                cstmt.execute();
                cstmt.getMoreResults();

                Tong_TH_OPC = cstmt.getFloat(4);
                Tong_TH_Con = cstmt.getFloat(5);
                Tong_TH_HD2 = cstmt.getFloat(6);
                Tong_TH_Phien = cstmt.getFloat(7);
                Tong_TH_25 = cstmt.getFloat(8);
                Tong_TH_ComBo = cstmt.getFloat(9);

                cstmt.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
            visitors = new ArrayList<>();

            visitors.clear();
            pieChartdttdv.invalidate();
            pieChartdttdv.clear();

            visitors.add(new PieEntry(Float.parseFloat(String.valueOf(Tong_TH_OPC)), "OPC"));

            visitors.add(new PieEntry(Float.parseFloat(String.valueOf(Tong_TH_Con)), "Cồn"));
            visitors.add(new PieEntry(Float.parseFloat(String.valueOf(Tong_TH_HD2)), "HD2"));
            visitors.add(new PieEntry(Float.parseFloat(String.valueOf(Tong_TH_Phien)), "Phiến"));
            visitors.add(new PieEntry(Float.parseFloat(String.valueOf(Tong_TH_25)), "25"));
            visitors.add(new PieEntry(Float.parseFloat(String.valueOf(Tong_TH_ComBo)), "ComBo"));


            PieDataSet pieDataSet=new PieDataSet(visitors,":Tỉ Trọng Theo Loại SP");

            pieDataSet.setColors(Color.GREEN,Color.RED,Color.BLUE,Color.BLACK,Color.YELLOW,Color.GRAY);

            pieDataSet.setValueTextColor(Color.BLACK);
            pieDataSet.setValueTextSize(14f);

            pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

            PieData pieData=new PieData(pieDataSet);
            pieChartdttdv.setData(pieData);
            pieChartdttdv.getDescription().setEnabled(false);

            pieChartdttdv.setCenterText("% Tỉ lệ");
            pieChartdttdv.animateY(1000);


            con.close();
        }
    }
}