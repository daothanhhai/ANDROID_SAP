package com.example.erp_tuyen.loginsap;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class activity_doitac extends AppCompatActivity {
    ListView lvdoitac;
    ArrayList<DoiTac> arraydoitac;
    DoiTacAdapter adapter;
    private MainActivity context;
    ResultSet resultSet;
    String dvcs,macbnv,chucdanh,username;
    EditText edtimkiemdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doitac);
        getSupportActionBar().setTitle("Đối tác");

        Intent intent=getIntent();

        dvcs=intent.getStringExtra("Ma_Dvcs");
        macbnv=intent.getStringExtra("Ma_CbNv");
        chucdanh=intent.getStringExtra("Chuc_Danh");
        username=intent.getStringExtra("USERNAME");

        lvdoitac=(ListView) findViewById(R.id.listviewdoitac);
        edtimkiemdt=(EditText) findViewById(R.id.edittimkiemdt);

        arraydoitac=new ArrayList<>();
        adapter=new DoiTacAdapter(activity_doitac.this,R.layout.dong_doi_tac,arraydoitac);
        lvdoitac.setAdapter(adapter);

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
            String query = "SELECT dt.Ma_Dt,dt.Ten_Dt,dt.ContactCode,dt.ContactName,dt.Address\n" +
                    ",dt.Ngay_Sinh,dt.tel,kh.Ma_TDV \n" +
                    "FROM B20Contact dt\n" +
                    "LEFT OUTER JOIN B20DmDt kh ON dt.Ma_Dt=kh.Ma_Dt\n" +
                    "WHERE dt.IsActive=1 AND dt.Ma_Dt <> '' AND dt.Ten_Dt <> '' AND kh.Ma_TDV='" + macbnv + "' " +
                    "ORDER BY Ma_Dt";

            Statement stmt = con.createStatement();
            resultSet = stmt.executeQuery(query);
            arraydoitac.clear();

            for(int i = 0; i<=resultSet.getRow();i++ )
            {
                if (resultSet.next())
                {
                    String makh = resultSet.getString("Ma_Dt");
                    String tenkh = resultSet.getString("Ten_Dt");
                    String madt = resultSet.getString("ContactCode");
                    String tendt = resultSet.getString("ContactName");
                    String diachi = resultSet.getString("Address");
                    String ngaysinh = resultSet.getString("Ngay_Sinh");
                    if(ngaysinh != null)
                    {
                        ngaysinh=ngaysinh.substring(0,10);
                    }
                    String diethoai = resultSet.getString("tel");
                    arraydoitac.add(new DoiTac(makh, tenkh,madt,tendt,diachi,diethoai,ngaysinh));

                }
            }

            adapter.notifyDataSetChanged();
            con.close();
        }
    }
}