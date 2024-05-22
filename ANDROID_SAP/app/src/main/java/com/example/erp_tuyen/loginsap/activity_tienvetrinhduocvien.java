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

public class activity_tienvetrinhduocvien extends AppCompatActivity {
    private ArrayList<String> datatvtdv = new ArrayList<String>();
    private ArrayList<String> datatienvetdv = new ArrayList<String>();
    private ArrayList<String> datakhtvtdv = new ArrayList<String>();
    private ArrayList<String> datatyletvtdv = new ArrayList<String>();
    private ArrayList<String> data4 = new ArrayList<String>();
    private TableLayout table;
    private TableRow row;
    ResultSet resultSet;
    TextView tvtvtdv,tvtienvetdv,tvkhtvtdv,tvtltvtdv;
    EditText edtntvtdv,eddntvtdv;
    Button buttonbctvtdv;
    PieChart pieCharttvtdv;
    ArrayList<PieEntry> visitors=new ArrayList<>();
    int flag=0;
    Float Tong_TH_OPC,Tong_TH_Con,Tong_TH_HD2,Tong_TH_Phien;
    String dvcs,macbnv,chucdanh,username;

    private ProgressDialog LoadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienvetrinhduocvien);
        getSupportActionBar().setTitle("Tiền Về TDV");
        Intent intent=getIntent();
        dvcs=intent.getStringExtra("Ma_Dvcs");
        macbnv=intent.getStringExtra("Ma_CbNv");
        chucdanh=intent.getStringExtra("Chuc_Danh");
        username=intent.getStringExtra("USERNAME");

        taothongtinCN();
        anhxa();


        String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        edtntvtdv.setText(date_n);
        eddntvtdv.setText(date_n);

        edtntvtdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                chonngay();
            }
        });
        eddntvtdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
                chonngay();
            }
        });
        buttonbctvtdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LoadingBar=new ProgressDialog(activity_tienvetrinhduocvien.this);
                LoadingBar.setTitle("SAP - ANDROID");
                LoadingBar.setMessage("Đang tải dữ liệu.... usp_BaoCaoTienVeTDV_SAP");
                LoadingBar.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            table.removeViews(1, Math.max(0, table.getChildCount() - 1));
                            selecttienvetdv();
                            //selecttyledt();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        LoadingBar.dismiss();
                    }
                },5000);
            }
        });
    }

    private void anhxa()
    {
        tvtvtdv=(TextView) findViewById(R.id.tvtvtdv);
        tvtienvetdv=(TextView) findViewById(R.id.tvtienvetdv);
        tvkhtvtdv=(TextView) findViewById(R.id.tvkhtvtdv);
        tvtltvtdv=(TextView) findViewById(R.id.tvtltvtdv);
        table = (TableLayout) findViewById(R.id.tbtvtdv);
        edtntvtdv=(EditText) findViewById(R.id.editTexttntvtdv);
        eddntvtdv=(EditText) findViewById(R.id.editTextdntvtdv);
        buttonbctvtdv=(Button) findViewById(R.id.buttonbctvtdv);
        pieCharttvtdv=(PieChart) findViewById(R.id.bartvtdv);



    }

    private void selecttienvetdv() throws SQLException {



        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {


            String tungay=edtntvtdv.getText().toString();
            String denngay=eddntvtdv.getText().toString();

            PreparedStatement statement = con.prepareStatement("EXEC usp_BaoCaoTienVeTDV_SAP " +
                    "'"+ tungay +"' ,'"+ denngay +"','' ,'"+ macbnv +"','"+ dvcs +"' " );
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {


                    String tentdv=resultSet.getString("Ten_TDV");
                    String tienve = resultSet.getString("Tien_Thu_TT");
                    //String kehoach = resultSet.getString("Tong_Khoan");
                    //String tyle = resultSet.getString("Ty_Le");


                    datatvtdv.add(tentdv);

                    datatienvetdv.add(String.format("%,.0f", Float.valueOf(tienve)));
                    datakhtvtdv.add(String.format("%,.0f", Float.valueOf(0)));
                    datatyletvtdv.add(String.format("%,.2f", Float.valueOf(0))+""+"%");

                    row = new TableRow(this);
                    TextView t1 = new TextView(this);
                    TextView t2 = new TextView(this);
                    TextView t3 = new TextView(this);
                    TextView t4 = new TextView(this);

                    t1.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t2.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t3.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t4.setBackground(ContextCompat.getDrawable(this,R.drawable.border));


                    for (int j = 0; j < datatvtdv.size(); j++) {
                        String ptg = datatvtdv.get(j);
                        String pdt = datatienvetdv.get(j);
                        String pkh = datakhtvtdv.get(j);
                        String ptl = datatyletvtdv.get(j);

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
                    edtntvtdv.setText(simpleDateFormat.format(calendar.getTime()));
                }
                if(flag==1)
                {
                    eddntvtdv.setText(simpleDateFormat.format(calendar.getTime()));
                }
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }


    public void selecttyledt() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        }
        else {
            String tungay = edtntvtdv.getText().toString();
            String denngay = eddntvtdv.getText().toString();

            try {


                CallableStatement cstmt = con.prepareCall("{call usp_Kcd_BaoCaoTienVe_AnDroid(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

                cstmt.setString(1, tungay);
                cstmt.setString(2, denngay);
                cstmt.setString(3, "131");
                cstmt.setString(4, "");
                cstmt.setString(5, "");
                cstmt.setInt(6, 1);
                cstmt.setInt(7, 0);
                cstmt.setInt(8, 0);
                cstmt.setString(9, dvcs);
                cstmt.setString(10, "");
                cstmt.setString(11, username);
                cstmt.setInt(12, 1);
                cstmt.setString(13, dvcs);
                cstmt.setString(14, "");
                cstmt.setString(15, "");
                cstmt.setInt(16, 0);
                cstmt.setInt(17, 0);
                cstmt.setInt(18, 0);
                cstmt.setInt(19, 0);


                cstmt.registerOutParameter(16, Types.FLOAT);
                cstmt.registerOutParameter(17, Types.FLOAT);
                cstmt.registerOutParameter(18, Types.FLOAT);
                cstmt.registerOutParameter(19, Types.FLOAT);

                cstmt.execute();
                cstmt.getMoreResults();

                 Tong_TH_OPC = cstmt.getFloat(16);
                 Tong_TH_Con = cstmt.getFloat(17);
                 Tong_TH_HD2 = cstmt.getFloat(18);
                 Tong_TH_Phien = cstmt.getFloat(19);

                cstmt.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }

        }

                visitors = new ArrayList<>();

                visitors.clear();
                pieCharttvtdv.invalidate();
                pieCharttvtdv.clear();


                visitors.add(new PieEntry(Float.parseFloat(String.valueOf(Tong_TH_OPC)), "OPC"));
                visitors.add(new PieEntry(Float.parseFloat(String.valueOf(Tong_TH_Con)), "Cồn"));
                visitors.add(new PieEntry(Float.parseFloat(String.valueOf(Tong_TH_HD2)), "HD2"));
                visitors.add(new PieEntry(Float.parseFloat(String.valueOf(Tong_TH_Phien)), "Phiến"));


                PieDataSet pieDataSet = new PieDataSet(visitors, "Tỉ Trọng Theo Loại SP");

                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

                pieDataSet.setValueTextColor(Color.BLACK);
                pieDataSet.setValueTextSize(14f);

                pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

                PieData pieData = new PieData(pieDataSet);
                pieCharttvtdv.setData(pieData);
                pieCharttvtdv.getDescription().setEnabled(false);

                pieCharttvtdv.setCenterText("% Tỉ lệ");
                pieCharttvtdv.animateY(1000);


                con.close();


            }


    public void taothongtinCN()
    {
        if(dvcs.equals("A01"))
        {
            dvcs="OPC";

        }
        if(dvcs.equals("A02"))
        {
            dvcs="OPC_TP";


        }
        if(dvcs.equals("A03"))
        {
            dvcs="OPC_CT";



        }

        if(dvcs.equals("A04"))
        {
            dvcs="OPC_TG";


        }
        if(dvcs.equals("A05"))
        {
            dvcs="OPC_MD";


        }
        if(dvcs.equals("A06"))
        {
            dvcs="OPC_VT";


        }
        if(dvcs.equals("A07"))
        {
            dvcs="OPC_NT";


        }
        if(dvcs.equals("A08"))
        {
            dvcs="OPC_DN";


        }
        if(dvcs.equals("A09"))
        {
            dvcs="OPC_NA";


        }
        if(dvcs.equals("A10"))
        {
            dvcs="OPC_HN";


        }
    }


        }


