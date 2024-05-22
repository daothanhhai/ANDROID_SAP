package com.example.erp_tuyen.loginsap;

import android.content.Intent;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class activity_doanhthutheothang extends AppCompatActivity {

    AutoCompleteTextView atcquythang,atcquynam;
    ImageView imagequythang,imagequynam;
    ArrayList<String> ArrQuythang = new ArrayList<>();
    ArrayList<String> ArrQuynam = new ArrayList<>();
    ResultSet resultSet;
    TextView txttongdoanhthutheothang,txttongkhoantheoquy,txttongtyletheoquy;
    String dvcs,macbnv,chucdanh,username;

    BarChart barChartdt;
    ArrayList<BarEntry> visitors=new ArrayList<>();

    private ArrayList<String> datatg = new ArrayList<String>();
    private ArrayList<String> datadt = new ArrayList<String>();
    private ArrayList<String> datakh = new ArrayList<String>();
    private ArrayList<String> datatyle = new ArrayList<String>();
    private ArrayList<String> data4 = new ArrayList<String>();
    private TableLayout table;
    private TableRow row;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanhthutheothang);
        getSupportActionBar().setTitle("Doanh Thu Tổng Hợp Quí");
        atcquythang=(AutoCompleteTextView) findViewById(R.id.atcquythang);
        imagequythang=(ImageView) findViewById(R.id.imgquythang);
        atcquynam=(AutoCompleteTextView) findViewById(R.id.atcquynamthang);
        imagequynam=(ImageView) findViewById(R.id.imgquynamthang);
        table = (TableLayout) findViewById(R.id.tbdttt);
        txttongdoanhthutheothang=(TextView) findViewById(R.id.textViewdoanhthutheothang);
        txttongkhoantheoquy=(TextView) findViewById(R.id.textViewkhoantheoquy);
        txttongtyletheoquy=(TextView) findViewById(R.id.textViewtongtylequy);
        barChartdt=(BarChart) findViewById(R.id.bardt);



        Intent intent=getIntent();
        dvcs=intent.getStringExtra("Ma_Dvcs");
        macbnv=intent.getStringExtra("Ma_CbNv");
        chucdanh=intent.getStringExtra("Chuc_Danh");
        username=intent.getStringExtra("USERNAME");
        
        try {
            loadtudong();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrQuythang.add("Quí 1");
        ArrQuythang.add("Quí 2");
        ArrQuythang.add("Quí 3");
        ArrQuythang.add("Quí 4");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ArrQuythang);
        atcquythang.setAdapter(adapter);
        addnam();
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ArrQuynam);
        atcquynam.setAdapter(adapter1);

        imagequythang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atcquythang.showDropDown();
            }
        });

        imagequynam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atcquynam.showDropDown();
            }
        });

        atcquythang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    table.removeViews(1, Math.max(0, table.getChildCount() - 1));
                    
                    selectdoanhthutheothang();
                    selectdtbieudo();
                    selecttongdoanhthu();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void addnam()
    {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for(int i=2013;i<=year;i++)
        {
            ArrQuynam.add(String.valueOf(i));
        }
    }

    public void selecttongdoanhthu() throws SQLException {
        String tongdoanhthu = "",tongkhoan="";
        Float tongtyle;
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
            String nam="";
            nam=atcquynam.getText().toString();

            String quy=atcquythang.getText().toString();
            String thang="";
            if(quy.equals("Quí 1"))
            {
                thang="1";
            }
            if(quy.equals("Quí 2"))
            {
                thang="4";
            }
            if(quy.equals("Quí 3"))
            {
                thang="7";
            }
            if(quy.equals("Quí 4"))
            {
                thang="10";
            }

            CallableStatement cstmt = con.prepareCall("{call usp_DoanhThuTheoThang_Android(?,?,?,?,?,?,?)}");

            cstmt.setString(1, thang);
            cstmt.setString(2, nam);
            cstmt.setString(3, dvcs);
            cstmt.setString(4, username);
            cstmt.setString(5, "");
            cstmt.setInt(6, 0);
            cstmt.setInt(7, 0);

            cstmt.registerOutParameter(6, Types.FLOAT);
            cstmt.registerOutParameter(7, Types.FLOAT);

            cstmt.execute();
            cstmt.getMoreResults();


                    tongdoanhthu = String.valueOf(cstmt.getFloat(6));
                    if(tongdoanhthu== null)
                    {
                        tongdoanhthu="0";
                    }

                    txttongdoanhthutheothang.setText(String.format("\n\n" + " " + "%,.0f", Float.valueOf(tongdoanhthu)));
                    txttongdoanhthutheothang.setBackground(ContextCompat.getDrawable(this,R.drawable.border));

                    tongkhoan = String.valueOf(cstmt.getFloat(7));
                    if(tongkhoan==null)
                    {
                        tongkhoan="0";
                    }
                    txttongkhoantheoquy.setText(String.format("\n\n" + " " + "%,.0f", Float.valueOf(tongkhoan)));
                    txttongkhoantheoquy.setBackground(ContextCompat.getDrawable(this,R.drawable.border));


            con.close();
        }
        tongtyle= (Float.valueOf(tongdoanhthu)/Float.valueOf(tongkhoan))*100;

        txttongtyletheoquy.setText(String.format("\n\n" + "" + "%,.2f", Float.valueOf(tongtyle)));

        

    }
    public void selectdtbieudo() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
            String nam="";
            nam=atcquynam.getText().toString();

            String quy=atcquythang.getText().toString();
            String thang="";
            if(quy.equals("Quí 1"))
            {
                thang="1";
            }
            if(quy.equals("Quí 2"))
            {
                thang="4";
            }
            if(quy.equals("Quí 3"))
            {
                thang="7";
            }
            if(quy.equals("Quí 4"))
            {
                thang="10";
            }

            PreparedStatement statement = con.prepareStatement("EXEC usp_DTBieuDoTheoThang_Android '"+ thang +"' ,'"+ nam +"' ,'"+ dvcs +"' " );
            resultSet = statement.executeQuery();

            visitors=new ArrayList<>();

            visitors.clear();
            barChartdt.invalidate();
            barChartdt.clear();
            for(int i = 0; i<=resultSet.getRow();i++ )
            {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String thangdt=resultSet.getString("Thang");
                    String doanhthu = resultSet.getString("Doanh_Thu");
                    String kehoach = resultSet.getString("Ke_Hoach");
                    String tyle = resultSet.getString("Ty_Le");

                    int thangdt1= Integer.parseInt(thangdt);
                    Float doanhthu1= Float.valueOf(doanhthu);

                    visitors.add(new BarEntry(thangdt1,doanhthu1));
                    //barChartdt.getXAxis().setValueFormatter(new IndexAxisValueFormatter(Collections.singleton(thoigian)));

                    barChartdt.getXAxis().setValueFormatter(new IndexAxisValueFormatter(Collections.singleton(thangdt)));
                    barChartdt.getXAxis().setGranularity(1f);
                    barChartdt.getXAxis().setGranularityEnabled(true);
                }
            }

            BarDataSet barDataSet=new BarDataSet(visitors,"Tỉ Trọng Theo Loại KH");
            barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            barDataSet.setValueTextColor(Color.BLACK);
            barDataSet.setValueTextSize(10f);
            BarData barData=new BarData(barDataSet);
            barChartdt.setFitBars(true);
            barChartdt.setData(barData);
            barChartdt.getDescription().setText("");

            barChartdt.animateY(2000);
            barChartdt.animateX(2000);

            con.close();
        }
    }


    private void selectdoanhthutheothang() throws SQLException {

        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            String nam="";
            nam=atcquynam.getText().toString();

            String quy=atcquythang.getText().toString();
            String thang="";
            if(quy.equals("Quí 1"))
            {
                thang="1";
            }
            if(quy.equals("Quí 2"))
            {
                thang="4";
            }
            if(quy.equals("Quí 3"))
            {
                thang="7";
            }
            if(quy.equals("Quí 4"))
            {
                thang="10";
            }

            PreparedStatement statement = con.prepareStatement("EXEC usp_DoanhThuTheoThang_Android '"+ thang +"' ,'"+ nam +"' " +
                    ",'"+ dvcs +"','"+ username +"','','0' " );
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String thoigian = resultSet.getString("Thoi_Gian");
                    String doanhthu = resultSet.getString("Doanh_Thu");
                    String kehoach = resultSet.getString("Ke_Hoach");
                    String tyle = resultSet.getString("Ty_Le");

                    datatg.add(thoigian);

                    datadt.add(String.format("%,.0f", Float.valueOf(doanhthu)));
                    datakh.add(String.format("%,.0f", Float.valueOf(kehoach)));
                    datatyle.add(String.format("%,.2f", Float.valueOf(tyle)));

                    row = new TableRow(this);
                    TextView t1 = new TextView(this);
                    TextView t2 = new TextView(this);
                    TextView t3 = new TextView(this);
                    TextView t4 = new TextView(this);

                    t1.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t2.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t3.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t4.setBackground(ContextCompat.getDrawable(this,R.drawable.border));


                    for (int j = 0; j < datatg.size(); j++) {
                        String ptg = datatg.get(j);
                        String pdt = datadt.get(j);
                        String pkh = datakh.get(j);
                        String ptl = datatyle.get(j);

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

    private void loadtudong() throws SQLException {
        int thang = Calendar.getInstance().get(Calendar.MONTH)+1;
        String nam = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        atcquynam.setText(nam);
        if(thang==1 || thang==2 || thang==3) {
            atcquythang.setText("Quí 1");
        }

        if(thang==4 || thang==5 || thang==6) {
            atcquythang.setText("Quí 2");
        }

        if(thang==7 || thang==8 || thang==9) {
            atcquythang.setText("Quí 3");
        }

        if(thang==10 || thang==11 || thang==12) {
            atcquythang.setText("Quí 4");
        }
        try {
            selecttongdoanhthu();
            selectdtbieudo();
            selectdoanhthutheothang();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}