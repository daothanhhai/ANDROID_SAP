package com.example.erp_tuyen.loginsap;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class activity_banggia extends AppCompatActivity {
    ListView lvbanggia;
    ArrayList<BangGia> arrayBangGia;
    BangGiaAdapter adapter;
    private MainActivity context;
    ResultSet resultSet;
    EditText edtimkiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banggia);
        getSupportActionBar().setTitle("Bảng Giá OPC");
        lvbanggia=(ListView) findViewById(R.id.listviewbanggia);



        arrayBangGia=new ArrayList<>();
        adapter=new BangGiaAdapter(activity_banggia.this,R.layout.dong_bang_gia,arrayBangGia);
        lvbanggia.setAdapter(adapter);


        try {
            selectdoanhthu();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void selectdoanhthu() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {


            String query = "SELECT Description,Gia_Nt FROM B30BGDetail WHERE BGId = (SELECT MAX(BGId) FROM B30BG WHERE IsActive=1) " +
                    "AND Gia_Nt > 0 ORDER BY Description";
            Statement stmt = con.createStatement();
            resultSet = stmt.executeQuery(query);

            arrayBangGia.clear();

            for(int i = 0; i<=resultSet.getRow();i++ )
            {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {

                    String tenvt = resultSet.getString("Description");
                    String gia = resultSet.getString("Gia_Nt");
                    arrayBangGia.add(new BangGia(tenvt,gia));

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