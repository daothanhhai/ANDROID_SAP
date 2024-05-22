package com.example.erp_tuyen.loginsap;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class activity_khachhang extends AppCompatActivity {
    ListView lvkhachhang;
    ArrayList<KhachHang> arrayKhachHang;
    KhachHangAdapter adapter;
    private MainActivity context;
    ResultSet resultSet;
    String dvcs,macbnv,chucdanh,username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khachhang);
        getSupportActionBar().setTitle("Khách Hàng");

        Intent intent=getIntent();

        dvcs=intent.getStringExtra("Ma_Dvcs");
        macbnv=intent.getStringExtra("Ma_CbNv");
        chucdanh=intent.getStringExtra("Chuc_Danh");
        username=intent.getStringExtra("USERNAME");

        lvkhachhang=(ListView) findViewById(R.id.listviewkhachhang);


        arrayKhachHang=new ArrayList<>();
        adapter=new KhachHangAdapter(activity_khachhang.this,R.layout.dong_khach_hang,arrayKhachHang);
        lvkhachhang.setAdapter(adapter);

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

            PreparedStatement statement = con.prepareStatement("EXEC usp_AccountForEmployee_SAP '','"+ macbnv +"' " );
            resultSet = statement.executeQuery();

            arrayKhachHang.clear();

            for(int i = 0; i<=resultSet.getRow();i++ )
            {
                if (resultSet.next())
                {
                    String makh = resultSet.getString("Ma_Dt");
                    String tenkh = resultSet.getString("Ten_Dt");
                    String diachi = resultSet.getString("Dia_Chi");
//                    String dienthoai = resultSet.getString("Dien_Thoai");
//                    String cktt=resultSet.getString("Han_TT_CKTT");
//                    String cktm=resultSet.getString("Chiet_Khau");
//                    String tendt = resultSet.getString("Doi_Tac");
                    //arrayKhachHang.add(new KhachHang(makh, tenkh,diachi,dienthoai,cktt,cktm,tendt));
                    arrayKhachHang.add(new KhachHang(makh, tenkh,diachi,"","","",""));

                }
            }

            adapter.notifyDataSetChanged();
            con.close();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_timkiem,menu);
        MenuItem item=menu.findItem(R.id.menutiemkiemkh);
        SearchView searchView=(SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}