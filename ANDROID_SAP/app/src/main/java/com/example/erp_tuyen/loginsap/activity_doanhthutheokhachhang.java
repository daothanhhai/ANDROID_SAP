package com.example.erp_tuyen.loginsap;

import android.content.Intent;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ImageView;
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
import java.util.ArrayList;
import java.util.Calendar;

public class activity_doanhthutheokhachhang extends AppCompatActivity {
    ResultSet resultSet;
    TextView txtdoanhthu;
    ImageView imagequy,imagenam;
    PieChart pieChartdt;
    GridView gridviewdtkh;
    AutoCompleteTextView atcquy,atcnam;
    String dvcs,macbnv,chucdanh,username;
    ArrayList<String> ArrQuy = new ArrayList<>();
    ArrayList<String> ArrNam = new ArrayList<>();
    ArrayList<PieEntry> visitors=new ArrayList<>();
    DoanhThuTheoKhachHangAdapter adaptertheodoituong;
    ArrayList<DoanhThuTheoDoiTuong> arrayDoanhThuTheoDoiTuong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanhthutheokhachhang);
        getSupportActionBar().setTitle("Doanh Thu Theo Quí");
        txtdoanhthu=(TextView) findViewById(R.id.textViewtongdoanhthu);
        imagequy=(ImageView) findViewById(R.id.imagequy);
        imagenam=(ImageView) findViewById(R.id.imagenamkh);
        atcquy=(AutoCompleteTextView) findViewById(R.id.atcquy);
        atcnam=(AutoCompleteTextView) findViewById(R.id.atcnamkh);
        pieChartdt=(PieChart) findViewById(R.id.bardt);
        gridviewdtkh=(GridView) findViewById(R.id.gridviewdtkh);

        Intent intent=getIntent();
        dvcs=intent.getStringExtra("Ma_Dvcs");
        macbnv=intent.getStringExtra("Ma_CbNv");
        chucdanh=intent.getStringExtra("Chuc_Danh");
        username=intent.getStringExtra("USERNAME");

        arrayDoanhThuTheoDoiTuong=new ArrayList<>();
        adaptertheodoituong=new DoanhThuTheoKhachHangAdapter(activity_doanhthutheokhachhang.this,R.layout.dong_doanh_thu_theo_khach_hang
                ,arrayDoanhThuTheoDoiTuong);

        gridviewdtkh.setAdapter(adaptertheodoituong);
        try {
            loadtudong();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrQuy.add("Quí 1");
        ArrQuy.add("Quí 2");
        ArrQuy.add("Quí 3");
        ArrQuy.add("Quí 4");


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ArrQuy);
        atcquy.setAdapter(adapter);

        addnam();
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ArrNam);
        atcnam.setAdapter(adapter1);

        imagequy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atcquy.showDropDown();
            }
        });
        imagenam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atcnam.showDropDown();
            }
        });

        atcquy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                try {

                    selecttongdoanhthu();
                    selecttyledt();
                    selectdoanhthukh();

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
            ArrNam.add(String.valueOf(i));
        }
    }

    public void selecttongdoanhthu() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
            String nam="";
            nam=atcnam.getText().toString();

            String quy=atcquy.getText().toString();
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

            CallableStatement cstmt = con.prepareCall("{call usp_DoanhThuTheoKH_Android(?,?,?,?,?,?)}");

            cstmt.setString(1, thang);
            cstmt.setString(2, nam);
            cstmt.setString(3, dvcs);
            cstmt.setString(4, username);
            cstmt.setString(5, "");
            cstmt.setInt(6, 0);

            cstmt.registerOutParameter(6, Types.FLOAT);

            cstmt.execute();
            cstmt.getMoreResults();

            Float tongdoanhthu = cstmt.getFloat(6);
                    txtdoanhthu.setText(String.format("Tổng Doanh Thu Trong Quý\n\n" + " " + "%,.0f", Float.valueOf(tongdoanhthu)));

            con.close();
        }
    }


    public void selecttyledt() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
            String nam="";
            nam=atcnam.getText().toString();

            String quy=atcquy.getText().toString();
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


            PreparedStatement statement = con.prepareStatement("EXEC usp_TLDoanhThuLoaiKH_Android '"+ thang +"' ,'"+ nam +"' " +
                    ",'"+ dvcs +"','"+ username +"','','0'" );
            resultSet = statement.executeQuery();

            visitors=new ArrayList<>();

            visitors.clear();
            pieChartdt.invalidate();
            pieChartdt.clear();
            for(int i = 0; i<=resultSet.getRow();i++ )
            {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String Ma_LKH=resultSet.getString("Ma_LKH");
                    String Ty_le = resultSet.getString("Ty_Le");

                    visitors.add(new PieEntry(Float.parseFloat(Ty_le),Ma_LKH));

                }
            }
            PieDataSet pieDataSet=new PieDataSet(visitors,"Tỉ Trọng Theo Loại KH");
            pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            pieDataSet.setValueTextColor(Color.BLACK);
            pieDataSet.setValueTextSize(16f);
            pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            PieData pieData=new PieData(pieDataSet);
            pieChartdt.setData(pieData);
            pieChartdt.getDescription().setEnabled(false);
            pieChartdt.setCenterText("% Tỉ lệ");
            pieChartdt.animateY(1000);

            con.close();
        }
    }



    public void selectdoanhthukh() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
            String nam="";
            nam=atcnam.getText().toString();

            String quy=atcquy.getText().toString();
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

            PreparedStatement statement = con.prepareStatement("EXEC usp_BaoCaoDoanhThuKhachHangTDV_SAP '"+ thang +"' ,'"+ nam +"' " +
                    ",'"+ dvcs +"','"+ username +"','','0'" );
            resultSet = statement.executeQuery();

            arrayDoanhThuTheoDoiTuong.clear();
            for(int i = 0; i<=resultSet.getRow();i++ )
            {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String Ma_Dt=resultSet.getString("Ma_Dt");
                    String Ten_Dt=resultSet.getString("Ten_Dt");
                    String Doanh_Thu = resultSet.getString("Doanh_Thu");
                    String Ty_Le = resultSet.getString("Ty_Le");
                    arrayDoanhThuTheoDoiTuong.add(new DoanhThuTheoDoiTuong(Ma_Dt,Ten_Dt,Doanh_Thu,Ty_Le));

                }
            }
            adaptertheodoituong.notifyDataSetChanged();
            con.close();

        }
    }

    private void loadtudong() throws SQLException {
        int thang = Calendar.getInstance().get(Calendar.MONTH) + 1;
        String nam = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        atcnam.setText(nam);
        if(thang==1 || thang==2 || thang==3) {
            atcquy.setText("Quí 1");
        }

        if(thang==4 || thang==5 || thang==6) {
            atcquy.setText("Quí 2");
        }

        if(thang==7 || thang==8 || thang==9) {
            atcquy.setText("Quí 3");
        }

        if(thang==10 || thang==11 || thang==12) {
            atcquy.setText("Quí 4");
        }
        try {
            selecttongdoanhthu();
            selecttyledt();
            selectdoanhthukh();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}