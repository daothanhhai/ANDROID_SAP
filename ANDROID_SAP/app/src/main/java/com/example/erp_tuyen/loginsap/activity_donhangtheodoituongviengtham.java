package com.example.erp_tuyen.loginsap;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class activity_donhangtheodoituongviengtham extends AppCompatActivity {
    ListView lvdonhangdtvt;
    ArrayList<DonHangTheoDoiTuongViengTham> arraydonhangdtvt;
    DonHangTheoDoiTuongViengThamAdapter adapter;

    ResultSet resultSet;
    String dvcs,macbnv,chucdanh,username,madt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donhangtheodoituongviengtham);

        Intent intent=getIntent();
        madt=intent.getStringExtra("MADT");
        //dvcs=intent.getStringExtra("Ma_Dvcs");
//        macbnv=intent.getStringExtra("Ma_CbNv");
//        chucdanh=intent.getStringExtra("Chuc_Danh");
//        username=intent.getStringExtra("USERNAME");

        lvdonhangdtvt=(ListView) findViewById(R.id.listviewvtdh);
        //edtimkiemkhvt=(EditText) findViewById(R.id.edittimkiemvtkh);

        arraydonhangdtvt=new ArrayList<>();
        adapter=new DonHangTheoDoiTuongViengThamAdapter(activity_donhangtheodoituongviengtham.this,R.layout.dong_donhangtheodoituongviengtham,arraydonhangdtvt);
        lvdonhangdtvt.setAdapter(adapter);

        try {
            selectdata();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectdata() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            String ngay = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            PreparedStatement statement = con.prepareStatement("EXEC usp_DoanhThuTheoKHViengTham_Android " +
                    "'"+ ngay +"','','','"+ madt +"',0");

            resultSet = statement.executeQuery();

            arraydonhangdtvt.clear();


            for(int i = 0; i<=resultSet.getRow();i++ )
            {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String soct = resultSet.getString("So_Ct");
                    String ngayct = resultSet.getString("Ngay_Ct");
                    if(ngayct != null) {
                        ngayct = ngayct.substring(0, 10);
                    }
                    String tinhtrang = resultSet.getString("Tinh_Trang");
                    String tongtien = resultSet.getString("Tong_Tien");

                    arraydonhangdtvt.add(new DonHangTheoDoiTuongViengTham(soct,ngayct,tongtien,tinhtrang));

                }
            }

            adapter.notifyDataSetChanged();
            con.close();
        }
    }
}