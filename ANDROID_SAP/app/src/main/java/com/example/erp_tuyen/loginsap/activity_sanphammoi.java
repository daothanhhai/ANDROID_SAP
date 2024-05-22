package com.example.erp_tuyen.loginsap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class activity_sanphammoi extends AppCompatActivity {
    ListView lvsanphammoi;
    ImageView imageViewhinhspm;
    TextView textviewslspm;

    ArrayList<SanPhamMoi> arraySanPhamMoi;
    SanPhamMoiAdapter adapter;
    private MainActivity context;
    ResultSet resultSet;
    String dvcs,macbnv,chucdanh,username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanphammoi);
        getSupportActionBar().setTitle("Sản Phẩm Mới");
        lvsanphammoi=(ListView) findViewById(R.id.listviewspm);
        imageViewhinhspm=(ImageView) findViewById(R.id.imageviewspm);
        textviewslspm=(TextView) findViewById(R.id.textViewslspm);


        arraySanPhamMoi=new ArrayList<>();
        adapter=new SanPhamMoiAdapter(activity_sanphammoi.this,R.layout.dong_san_pham_moi,arraySanPhamMoi);
        lvsanphammoi.setAdapter(adapter);

        Intent intent=getIntent();
        dvcs=intent.getStringExtra("Ma_Dvcs");
        macbnv=intent.getStringExtra("Ma_CbNv");
        chucdanh=intent.getStringExtra("Chuc_Danh");
        username=intent.getStringExtra("USERNAME");

        try {
            selectsanphammoi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_giohang,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent5=new Intent(getApplicationContext(),activity_giohang.class);
                intent5.putExtra("USERNAME",username);
                intent5.putExtra("Ma_Dvcs",dvcs);
                startActivity(intent5);
        }

        return super.onOptionsItemSelected(item);
    }

    public void selectsanphammoi() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {


            PreparedStatement statement = con.prepareStatement("EXEC usp_SanPhamMoi_Android " );
            resultSet = statement.executeQuery();

            arraySanPhamMoi.clear();


            for(int i = 0; i<=resultSet.getRow();i++ )
            {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String masp = resultSet.getString("Ma_SP");
                    String tensp = resultSet.getString("Ten_SP");
                    String giasp = resultSet.getString("Gia_SP");
                    String image=resultSet.getString("image");

                    byte[] hinh= Base64.decode(image,Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(hinh, 0, hinh.length);

                    arraySanPhamMoi.add(new SanPhamMoi(masp,tensp,giasp,bitmap));

                }
            }

            adapter.notifyDataSetChanged();
            con.close();
        }
    }

    public void giohang()
    {
        Intent intent5=new Intent(activity_sanphammoi.this,activity_giohang.class);
        startActivity(intent5);
    }


}