package com.example.erp_tuyen.loginsap;

import android.content.Intent;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class activity_tongquangdoanhthu extends AppCompatActivity {

    AutoCompleteTextView atcnam,atcthang;
    ImageView imagenam,imagethang;
    TextView textViewtongdoanhthu,textViewsodonhang;
    TextView tvkhachhangtrongngay,tvdoanhthutrongngay,tvhoadontrongngay,tvdonhangtrongngay,tvdonhanghuytrongngay,tvdonhangtratrongngay;
    BarChart barChartdttuan;
    ArrayList<BarEntry> visitors=new ArrayList<>();
    ArrayList<String> Arrnam = new ArrayList<>();
    ArrayList<String> Arrthang = new ArrayList<>();

    ResultSet resultSet;
    String dvcs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tongquangdoanhthu);
        getSupportActionBar().setTitle("Thống Kê Tổng Quan");
        Intent intent=getIntent();
        dvcs=intent.getStringExtra("Ma_Dvcs");

        anhxa();

        try {
            loadtudong();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        addnam();
        addthang();



        imagenam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atcnam.showDropDown();
            }
        });

        imagethang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atcthang.showDropDown();
            }
        });





        atcthang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selecttongdoanhthu();
                    selectdtbieudo();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void addnam()
    {
        Arrnam.add("2017");
        Arrnam.add("2018");
        Arrnam.add("2019");
        Arrnam.add("2020");
        Arrnam.add("2021");
        Arrnam.add("2022");
        Arrnam.add("2023");
        Arrnam.add("2024");
        Arrnam.add("2025");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Arrnam);
        atcnam.setAdapter(adapter);
    }
    private void addthang()
    {
        Arrthang.add("01");
        Arrthang.add("02");
        Arrthang.add("03");
        Arrthang.add("04");
        Arrthang.add("05");
        Arrthang.add("06");
        Arrthang.add("07");
        Arrthang.add("08");
        Arrthang.add("09");
        Arrthang.add("10");
        Arrthang.add("11");
        Arrthang.add("12");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Arrthang);
        atcthang.setAdapter(adapter);
    }

    private void anhxa()
    {
        atcnam=(AutoCompleteTextView) findViewById(R.id.atcnam);
        imagenam=(ImageView) findViewById(R.id.imgnam);

        atcthang=(AutoCompleteTextView) findViewById(R.id.atcthang);
        imagethang=(ImageView) findViewById(R.id.imgthang);



        textViewtongdoanhthu=(TextView) findViewById(R.id.textViewtongdoanhthu);
        textViewsodonhang=(TextView) findViewById(R.id.textViewsodonhang);

        barChartdttuan=(BarChart) findViewById(R.id.bardttuan);

        tvkhachhangtrongngay=(TextView) findViewById(R.id.textviewkhtrongngay);
        tvdoanhthutrongngay=(TextView) findViewById(R.id.textviewdttrongngay);
        tvhoadontrongngay=(TextView) findViewById(R.id.textviewhdtrongngay);

        tvdonhangtrongngay=(TextView) findViewById(R.id.textviewdhtrongngay);
        tvdonhanghuytrongngay=(TextView) findViewById(R.id.textviewdhhtrongngay);
        tvdonhangtratrongngay=(TextView) findViewById(R.id.textviewdhttrongngay);
    }

    private void loadtudong() throws SQLException {
        String nam = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String thang = String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1);

        atcnam.setText(nam);
        atcthang.setText(thang);


        try {
            selecttongdoanhthu();
            selectdtbieudo();
            selectongquantrongngay();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selecttongdoanhthu() throws SQLException {
        String tongdoanhthu = "",tonghoadon="";
        String nam,thang;
        nam=atcnam.getText().toString();
        thang=atcthang.getText().toString();


        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
            int year = Calendar.getInstance().get(Calendar.YEAR);

            PreparedStatement statement = con.prepareStatement("EXEC usp_TongQuanDoanhThu_Android '"+ dvcs +"','"+ thang +"' ,'"+ nam +"'  " );
            resultSet = statement.executeQuery();

            for(int i = 0; i<=resultSet.getRow();i++ )
            {
                if (resultSet.next())
                {

                    tongdoanhthu = resultSet.getString("Tong_Doanh_Thu");
                    if(tongdoanhthu==null)
                    {
                        tongdoanhthu="0";
                    }

                    textViewtongdoanhthu.setText(String.format("\n\n" + " " + "%,.0f", Float.valueOf(tongdoanhthu)));


                    tonghoadon = resultSet.getString("Tong_Hoa_Don");
                    if(tonghoadon==null)
                    {
                        tonghoadon="0";
                    }
                    textViewsodonhang.setText(String.format("\n\n" + " " + "%,.0f", Float.valueOf(tonghoadon))+" "+"Hóa Đơn");


                }
            }

            con.close();
        }
    }
    public void selectdtbieudo() throws SQLException {
        String tongdoanhthu = "",tonghoadon="";
        String nam,thang,tuan;
        nam=atcnam.getText().toString();
        thang=atcthang.getText().toString();

        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
            int year = Calendar.getInstance().get(Calendar.YEAR);


            PreparedStatement statement = con.prepareStatement("EXEC usp_ChartDtNgay_Android '"+ dvcs +"','"+ thang +"' ,'"+ nam +"'  " );
            resultSet = statement.executeQuery();

            visitors=new ArrayList<>();

            visitors.clear();
            barChartdttuan.invalidate();
            barChartdttuan.clear();
            for(int i = 0; i<=resultSet.getRow();i++ )
            {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String ngay=resultSet.getString("Ngay_Ct");
                    tongdoanhthu = resultSet.getString("Tong_Doanh_Thu");
                    tonghoadon = resultSet.getString("Tong_Hoa_Don");


                    int ngay1= Integer.parseInt(ngay);
                    Float doanhthu1= Float.valueOf(tongdoanhthu);

                    visitors.add(new BarEntry(ngay1,doanhthu1));
                    visitors.add(new BarEntry(ngay1, Float.parseFloat(tonghoadon)));


                    //barChartdt.getXAxis().setValueFormatter(new IndexAxisValueFormatter(Collections.singleton(thoigian)));

                    barChartdttuan.getXAxis().setValueFormatter(new IndexAxisValueFormatter(Collections.singleton(ngay)));
                    barChartdttuan.getXAxis().setGranularity(1f);
                    barChartdttuan.getXAxis().setGranularityEnabled(true);
                }
            }

            BarDataSet barDataSet=new BarDataSet(visitors,"SL Hóa Đơn - Doanh Thu");
            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            barDataSet.setValueTextColor(Color.BLACK);
            barDataSet.setValueTextSize(10f);
            BarData barData=new BarData(barDataSet);
            barChartdttuan.setFitBars(true);
            barChartdttuan.setData(barData);
            barChartdttuan.getDescription().setText("");

            barChartdttuan.animateY(2000);
            barChartdttuan.animateX(2000);

            con.close();
        }
    }


    public void selectongquantrongngay() throws SQLException {
        String tongkh = "",tongdoanhthu="",tongsohd="";
        String tongdh = "",tongdhhuy="",tongdhtra="";
        String nam,thang;
        nam=atcnam.getText().toString();
        thang=atcthang.getText().toString();


        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
            int year = Calendar.getInstance().get(Calendar.YEAR);

            PreparedStatement statement = con.prepareStatement("EXEC usp_TongKHTrongNgay_Android '"+ dvcs +"'  " );
            resultSet = statement.executeQuery();

            for(int i = 0; i<=resultSet.getRow();i++ )
            {
                if (resultSet.next())
                {

                    tongkh = resultSet.getString("Tong_KH");
                    tongdoanhthu=resultSet.getString("Tong_Dt");
                    tongsohd=resultSet.getString("Tong_SoHd");

                    tongdh=resultSet.getString("Tong_Dh");
                    tongdhhuy=resultSet.getString("Tong_Dh_Huy");
                    tongdhtra=resultSet.getString("Tong_Dh_Tra");

                    tvkhachhangtrongngay.setText(String.format("\n\n" + " " + "%,.0f", Float.valueOf(tongkh))+ "\n" + "Khách mua hàng");

                    tvdoanhthutrongngay.setText(String.format("\n\n" + " " + "%,.0f", Float.valueOf(tongdoanhthu))+ "\n" + "Doanh thu");

                    tvhoadontrongngay.setText(String.format("\n\n" + " " + "%,.0f", Float.valueOf(tongsohd))+ "\n" + "Đơn hoàn thành");


                    tvdonhangtrongngay.setText(String.format("\n\n" + " " + "%,.0f", Float.valueOf(tongdh))+ "\n" + "Đơn đặt hàng" + "  " + "  ");

                    tvdonhanghuytrongngay.setText(String.format("\n\n" + " " + "%,.0f", Float.valueOf(tongdhhuy))+ "\n" + "Đơn hủy"+ "  " + " ");

                    tvdonhangtratrongngay.setText(String.format("\n\n" + " " + "%,.0f", Float.valueOf(tongdhtra))+ "\n" + "     "  + "Đơn trả"+ "     " + "   ");
                }
            }

            con.close();
        }
    }
}