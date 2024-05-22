package com.example.erp_tuyen.loginsap;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class activity_sanphamchinh extends AppCompatActivity {
    ListView lvsanphamchinh;
    ImageView imageViewhinh,imagetesthinh;
    Button buttontesthin,button3;
    ArrayList<SanPhamChinh> arraySanPhamChinh;
    SanPhamChinhAdapter adapter;
    private MainActivity context;
    ResultSet resultSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanphamchinh);
        lvsanphamchinh=(ListView) findViewById(R.id.liviewsanphamchinh);
        imageViewhinh=(ImageView) findViewById(R.id.imageviewhinh);


        imagetesthinh=(ImageView) findViewById(R.id.imagesanpham);
        buttontesthin=(Button) findViewById(R.id.buttontesthinh);
        button3=(Button) findViewById(R.id.button3);

        buttontesthin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new activity_sanphamchinh.sendImage().execute("");


            }
        });


        arraySanPhamChinh=new ArrayList<>();
        adapter=new SanPhamChinhAdapter(activity_sanphamchinh.this,R.layout.dong_san_pham_chinh,arraySanPhamChinh);
        lvsanphamchinh.setAdapter(adapter);


        try {
            selectsanphamchinh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public class sendImage extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings)  {
            Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
            String s="false";

            @SuppressLint("WrongThread") BitmapDrawable bitmapDrawable=(BitmapDrawable) imagetesthinh.getDrawable();
            Bitmap bitmap=bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
            byte[] bytes=byteArrayOutputStream.toByteArray();
            String image= Base64.encodeToString(bytes,Base64.DEFAULT);
            String query = "INSERT INTO Test(Ma_Vt,image) VALUES('TP02-0047','"+ image +"')";
            //String query = "INSERT INTO Test(Ma_Vt,image) VALUES('TP02-0021','"+ imageViewToByte(imagetesthinh) +"')";

            //trên đây là câu truy vấn
            Statement stmt = null; //blah blah blah
            try {
                stmt = con.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                stmt.executeUpdate(query);
                s="true";

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return s;
        }
    }

    private byte[] imageViewToByte(ImageView image)
    {
        Bitmap bitmap=((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray=stream.toByteArray();
        return  byteArray;
    }



    public void selectsanphamchinh() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
            //String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());

            String query = "\n" +
                    "SELECT bg.Ma_Vt As Ma_SP,bg.Description AS Ten_SP\n" +
                    ",bg.Gia_Nt AS Gia_SP\n" +
                    ",T.image \n" +
                    "FROM B30BGDetail bg INNER JOIN Test T ON bg.Ma_Vt=T.Ma_Vt\n" +
                    "WHERE BGId='A0100000001088BG' \n" +
                    "AND Gia_Nt > 0 AND Loai_SP=''";
            //trên đây là câu truy vấn
            Statement stmt = con.createStatement(); //blah blah blah
            resultSet = stmt.executeQuery(query); //thực thi và trả về một cục ResultSet, nó là gì thì Google, tui chịu

            arraySanPhamChinh.clear();


            for(int i = 0; i<=resultSet.getRow();i++ )
            {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String masp = resultSet.getString("Ma_SP");
                    String tensp = resultSet.getString("Ten_SP");
                    String giasp = resultSet.getString("Gia_SP");
                    String image=resultSet.getString("image");


                    byte[] hinh=Base64.decode(image,Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(hinh, 0, hinh.length);

                    arraySanPhamChinh.add(new SanPhamChinh(masp,tensp,giasp,bitmap));


                }
            }

            adapter.notifyDataSetChanged();
            con.close();
        }
    }


}