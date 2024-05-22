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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SinhVienActivity extends AppCompatActivity {
    ListView lvsinhvien;
    ArrayList<SinhVien> arraySinhVien;
    SinhVienAdapter adapter;
    private MainActivity context;
    ResultSet resultSet;
    String dvcs,macbnv,chucdanh,username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sinh_vien);
        Intent intent=getIntent();

        dvcs=intent.getStringExtra("Ma_Dvcs");
        macbnv=intent.getStringExtra("Ma_CbNv");
        chucdanh=intent.getStringExtra("Chuc_Danh");
        username=intent.getStringExtra("USERNAME");

        lvsinhvien=(ListView) findViewById(R.id.listviewsinhvien);

        arraySinhVien=new ArrayList<>();
        adapter=new SinhVienAdapter(SinhVienActivity.this,R.layout.dong_sinh_vien,arraySinhVien);
        lvsinhvien.setAdapter(adapter);

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
            String query = "SELECT Ma_Dt,Ten_Dt FROM B20DmDt WHERE Ma_TDV LIKE '%"+ macbnv +"%'";
            //trên đây là câu truy vấn
            Statement stmt = con.createStatement(); //blah blah blah
            resultSet = stmt.executeQuery(query);
            arraySinhVien.clear();

         for(int i = 0; i<=resultSet.getRow();i++ )
            {
                if (resultSet.next())
                {
                    String masv = resultSet.getString("Ma_Dt");//Hàm lấy giá trị của tên cột (trường: field name) truyền vào
                    String tensv = resultSet.getString("Ten_Dt");//Hàm lấy giá trị của tên cột (trường: field name) truyền vào
                    arraySinhVien.add(new SinhVien(masv, tensv));

                }
            }

            adapter.notifyDataSetChanged();
            con.close();
        }
    }

    @Override
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