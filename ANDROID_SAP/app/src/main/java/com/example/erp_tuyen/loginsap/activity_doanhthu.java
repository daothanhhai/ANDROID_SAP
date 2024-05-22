package com.example.erp_tuyen.loginsap;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

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

public class activity_doanhthu extends AppCompatActivity {
    GridView grdoanhthu;
    ArrayList<DoanhThu> arrayDoanhThu;
    DoanhThuAdapter adapter;
    private MainActivity context;
    ResultSet resultSet;
    Button buttoncbc;
    EditText edittextungay;
    EditText edittextdenngay;
    Button bttongdoanhthu;
    int flag=0;
    String dvcs,macbnv,chucdanh,username;
    private ProgressDialog LoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanhthu);
        getSupportActionBar().setTitle("Doanh thu khách hàng");
        grdoanhthu=(GridView) findViewById(R.id.gridviewdoanhthu);
        buttoncbc=(Button) findViewById(R.id.buttonchaybaocao);
        edittextungay=(EditText) findViewById(R.id.editTexttungay);
        edittextdenngay=(EditText) findViewById(R.id.editTextdenngay) ;
        bttongdoanhthu=(Button) findViewById(R.id.buttontongdoanhthu);
        Intent intent=getIntent();
        dvcs=intent.getStringExtra("Ma_Dvcs");
        macbnv=intent.getStringExtra("Ma_CbNv");
        chucdanh=intent.getStringExtra("Chuc_Danh");
        username=intent.getStringExtra("USERNAME");


        arrayDoanhThu=new ArrayList<>();
        adapter=new DoanhThuAdapter(activity_doanhthu.this,R.layout.dong_doanh_thu,arrayDoanhThu);
        grdoanhthu.setAdapter(adapter);




        String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        edittextungay.setText(date_n);
        edittextdenngay.setText(date_n);

        buttoncbc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LoadingBar=new ProgressDialog(activity_doanhthu.this);
                LoadingBar.setTitle("ANDROID - SAP");
                LoadingBar.setMessage("Đang tải dữ liệu.... usp_BaoCaoDoanhThuKhachHangTDV_SAP");
                LoadingBar.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            selectdoanhthu();

                            selecttongdoanhthu();
                            grdoanhthu.clearFocus();


                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        LoadingBar.dismiss();
                    }
                },50);
            }
        });

        edittextungay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            flag=0;
            chonngay();
            }
        });

        edittextdenngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
                chonngay();
            }
        });
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
                    edittextungay.setText(simpleDateFormat.format(calendar.getTime()));
                }
                if(flag==1)
                {
                    edittextdenngay.setText(simpleDateFormat.format(calendar.getTime()));
                }
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }


    public void selectdoanhthu() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
            
            String tungay=edittextungay.getText().toString();
            String denngay=edittextdenngay.getText().toString();
            PreparedStatement statement = con.prepareStatement("EXEC usp_BaoCaoDoanhThuKhachHangTDV_SAP '"+ tungay +"' ,'"+ denngay +"' " +
                    ",'"+ macbnv +"'" );
            resultSet = statement.executeQuery();
            arrayDoanhThu.clear();

            for(int i = 0; i<=resultSet.getRow();i++ )
            {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {

                    String tendt = resultSet.getString("Ten_Dt");
                    String doanhthu = resultSet.getString("Doanh_Thu");
                    arrayDoanhThu.add(new DoanhThu(tendt,doanhthu));

                }
            }

            adapter.notifyDataSetChanged();

            con.close();
        }
    }

    public void selecttongdoanhthu() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            String tungay=edittextungay.getText().toString();
            String denngay=edittextdenngay.getText().toString();

            CallableStatement cstmt = con.prepareCall("{call usp_DoanhThuChiTietKH_Android(?,?,?,?,?,?)}");

            cstmt.setString(1, tungay);
            cstmt.setString(2, denngay);
            cstmt.setString(3, dvcs);
            cstmt.setString(4, username);
            cstmt.setString(5, "");
            cstmt.setInt(6, 0);

            cstmt.registerOutParameter(6, Types.FLOAT);

            cstmt.execute();
            cstmt.getMoreResults();

            Float tongdoanhthu = cstmt.getFloat(6);

            bttongdoanhthu.setText(String.format("Tổng Tiền:" + " " + "%,.0f", Float.valueOf(tongdoanhthu)));
            con.close();
        }
    }

}