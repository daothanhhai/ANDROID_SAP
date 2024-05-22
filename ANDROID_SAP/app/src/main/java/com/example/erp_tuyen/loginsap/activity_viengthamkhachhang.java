package com.example.erp_tuyen.loginsap;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.Toast;
import android.os.Handler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class activity_viengthamkhachhang extends AppCompatActivity {
    private static final long INTERVAL = 60*60 * 1000;
    ListView lvkhachhangvt;
    public static ArrayList<VienThamKhachHang> arrayKhachHangvt;
    ViengThamKhachHangAdapter adapter;

    LinearLayout layout;
    ResultSet resultSet;
    public static String id,dvcs,macbnv,chucdanh,username,hoten,chietkhau;
    EditText edtimkiemkhvt;
    SearchView svvtkh;
    private Handler handler = new Handler();
    private Runnable popupRunnable = new Runnable() {
        @Override
        public void run() {
            CreatepopupUpwindow(); // Gọi hàm hiển thị popup
            handler.postDelayed(this, INTERVAL); // Lặp lại sau mỗi 3 phút
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viengthamkhachhang);
        layout = findViewById(R.id.viengthamitem);//popup
        getSupportActionBar().setTitle("Viếng thăm khách hàng");


        Intent intent=getIntent();
        id=intent.getStringExtra("IdUser");
        dvcs=intent.getStringExtra("Ma_Dvcs");
        macbnv=intent.getStringExtra("Ma_CbNv");
        chucdanh=intent.getStringExtra("Chuc_Danh");
        username=intent.getStringExtra("USERNAME");
        hoten=intent.getStringExtra("HOTEN");

        lvkhachhangvt=(ListView) findViewById(R.id.listviewvtkhachhang);
        //edtimkiemkhvt=(EditText) findViewById(R.id.edittimkiemvtkh);
        //svvtkh=(SearchView) findViewById(R.id.svvtkh);

        arrayKhachHangvt=new ArrayList<>();
        adapter=new ViengThamKhachHangAdapter(activity_viengthamkhachhang.this,R.layout.dong_viengthamkhachhang,arrayKhachHangvt);
        lvkhachhangvt.setAdapter(adapter);

        try {
            selectdata();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        CreatepopupUpwindow();//goi ham view popup
//        handler.postDelayed(popupRunnable, INTERVAL);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Hủy lịch trình khi activity bị hủy
        handler.removeCallbacks(popupRunnable);
    }

    private void CreatepopupUpwindow(){
        LayoutInflater inflater = (LayoutInflater)  getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_popup_background, null);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = 1600;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView,width,height,focusable);
        layout.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(layout, Gravity.CENTER,0,0);
            }
        });


    }
    public void selectdata() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            PreparedStatement statement = con.prepareStatement("EXEC usp_AccountForEmployee_SAP '','"+ macbnv +"'");
            resultSet = statement.executeQuery();

            arrayKhachHangvt.clear();

            for(int i = 0; i<=resultSet.getRow();i++ )
            {
                if (resultSet.next())
                {
                    //String makh = resultSet.getString("Ma_Dt");
                    //String tenkh = resultSet.getString("Ten_Dt");
                    //String diachi = resultSet.getString("Dia_Chi");
                    //String dienthoai = resultSet.getString("Dien_Thoai");
                    chietkhau=resultSet.getString("Chiet_Khau");
                    arrayKhachHangvt.add(new VienThamKhachHang(resultSet.getString("Ma_Dt"), resultSet.getString("Ten_Dt"),resultSet.getString("Dia_Chi"),resultSet.getString("Dien_Thoai")));

                }
            }

            adapter.notifyDataSetChanged();
            con.close();
        }
    }

    public void timkiem() throws SQLException
    {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        String query;
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {


            if(edtimkiemkhvt.getText().toString().equals("")) {
                PreparedStatement statement = con.prepareStatement("EXEC usp_DoiTuongTDV_Android '"+ macbnv +"'");
                resultSet = statement.executeQuery();


            }
            if  (edtimkiemkhvt.getText().toString() !=""){
                PreparedStatement statement = con.prepareStatement("EXEC usp_DoiTuongTDV_Android " +
                        "'"+ macbnv +"',N'"+ edtimkiemkhvt.getText().toString() +"'");
                resultSet = statement.executeQuery();

            }

            arrayKhachHangvt.clear();

            for(int i = 0; i<=resultSet.getRow();i++ )
            {
                if (resultSet.next())
                {

                    String makh = resultSet.getString("Ma_Dt");
                    String tenkh = resultSet.getString("Ten_Dt");
                    String diachi = resultSet.getString("Dia_Chi");
                    String dienthoai = resultSet.getString("Dien_Thoai");

                    arrayKhachHangvt.add(new VienThamKhachHang(makh, tenkh,diachi,dienthoai));

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