package com.example.erp_tuyen.loginsap;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class activity_giohang extends AppCompatActivity {

    ListView lvgiohang;
    ImageView imageViewhinhgiohang;
    TextView textviewslgiohang,textviewthongbaogiohang,textviewgiatrigiohang;
    Button buttonthanhtoangiohang,buttontieptucmuahang;

    public static ArrayList<GioHang> arrayGioHang;
    GioHangAdapter adapter;
    private MainActivity context;
    ResultSet resultSet;
    String Id, Stt, IdCt, Rowid;
    String username,dvcs,dvcs1;
    long tongtien=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        getSupportActionBar().setTitle("Giỏ Hàng OPC");
        lvgiohang=(ListView) findViewById(R.id.listviewgiohang);
        imageViewhinhgiohang=(ImageView) findViewById(R.id.imageviewgiohang);
        textviewslgiohang=(TextView) findViewById(R.id.textViewslspgh);
        textviewthongbaogiohang=(TextView) findViewById(R.id.textviewthongbao);
        textviewgiatrigiohang=(TextView) findViewById(R.id.textviewgiatrigiohang);
        buttonthanhtoangiohang=(Button) findViewById(R.id.buttonthanhtoangiohang);
        buttontieptucmuahang=(Button) findViewById(R.id.buttontieptucmuahang);

        Intent intent=getIntent();
        username=intent.getStringExtra("USERNAME");
        dvcs=intent.getStringExtra("Ma_Dvcs");

        //arrayGioHang=new ArrayList<>();
        if(arrayGioHang != null) {
            adapter = new GioHangAdapter(activity_giohang.this, R.layout.dong_gio_hang, arrayGioHang);
            lvgiohang.setAdapter(adapter);
        }

        checkdata();
        evenUltil();

        buttonthanhtoangiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    luudonhang();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private void checkdata()
    {
        if(activity_giohang.arrayGioHang !=null)
        {
            adapter.notifyDataSetChanged();
            textviewthongbaogiohang.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.VISIBLE);
        }
        else {
            //adapter.notifyDataSetChanged();
            textviewthongbaogiohang.setVisibility(View.VISIBLE);
            lvgiohang.setVisibility(View.INVISIBLE);

        }
    }
    private void evenUltil()
    {
        if(arrayGioHang !=null) {

            for (int i = 0; i < activity_giohang.arrayGioHang.size(); i++) {
                tongtien += activity_giohang.arrayGioHang.get(i).getGiasp();
            }
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            textviewgiatrigiohang.setText(decimalFormat.format(tongtien) + "Đ");
        }
    }


    public void luudonhang() throws SQLException {
        try {
            Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
            if (con == null) {
                Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
            } else {

                taostt();
                taothongtinCN();
                String masp;
                int soluong;
                Float gia;
                //String ngaygh = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());

                String ngaygh = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                String matdv="";
                String querytdv = "SELECT Ma_TDV FROM B20DmDt WHERE Ma_Dt =N'" + username + "'";
                Statement stmttdv = con.createStatement();
                resultSet = stmttdv.executeQuery(querytdv);

                for (int i = 0; i <= resultSet.getRow(); i++) {
                    if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                    {
                         matdv = resultSet.getString("Ma_TDV");

                    }
                }

                String query = "INSERT INTO B30GhCt(Ma_DvCs,Ma_DvCs1,Stt,Ngay_Gio_Hang,Ma_Dt,Tong_Tien,Ma_TDV,IsActive) " +
                        "Values('"+ dvcs +"','"+ dvcs1 +"','"+ Stt +"','"+ ngaygh +"','"+ username +"',"+ tongtien +",'"+ matdv +"','1')";

                Statement stmt = con.createStatement();
                PreparedStatement pstmt = con.prepareStatement(query);
                int result = pstmt.executeUpdate();

                for(int i=0;i<activity_giohang.arrayGioHang.size();i++)
                {
                    masp= activity_giohang.arrayGioHang.get(i).getMasp();
                    soluong= activity_giohang.arrayGioHang.get(i).getSoluong();
                    gia= activity_giohang.arrayGioHang.get(i).getGiasp();
                    String query1 = "INSERT INTO B30GhCtVt(Ma_DvCs,Ma_DvCs1,Stt,Ngay_Gio_Hang,Ma_Vt,So_Luong,Gia,Ma_TDV) " +
                            "Values('"+ dvcs +"','"+ dvcs1 +"','" + Stt + "','"+ ngaygh +"','"+ masp +"',"+ soluong +","+ gia +",'"+ matdv +"')";
                    Statement stmt1 = con.createStatement();

                    PreparedStatement pstmt1 = con.prepareStatement(query1);
                    int result1 = pstmt1.executeUpdate();

                }

                con.close();
            }
            Toast.makeText(this, "Đã lưu vào giỏ hàng của OPC", Toast.LENGTH_SHORT).show();
            arrayGioHang.clear();
            adapter.notifyDataSetChanged();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void taostt() throws SQLException
    {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            String query = "SELECT MAX(Id),(MAX(Id)+1) AS IdM FROM B30GhCt WHERE Ma_Dvcs='"+ dvcs +"'";
            //trên đây là câu truy vấn
            Statement stmt = con.createStatement(); //blah blah blah
            resultSet = stmt.executeQuery(query); //thực thi và trả về một cục ResultSet, nó là gì thì Google, tui chịu

            for(int i = 0; i<=resultSet.getRow();i++ )
            {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    Id = resultSet.getString("IdM");

                    if(Id==null)
                    {
                        Stt=dvcs+"0000000000001";
                        return;
                    }

                    if(Id.length()==1)
                    {
                        Stt=dvcs+"000000000000"+Id;
                    }

                    if(Id.length()==2)
                    {
                        Stt=dvcs+"00000000000"+Id;
                    }
                    if(Id.length()==3)
                    {
                        Stt=dvcs+"0000000000"+Id;
                    }
                    if(Id.length()==4)
                    {
                        Stt=dvcs+"000000000"+Id;
                    }
                    if(Id.length()==5)
                    {
                        Stt=dvcs+"00000000"+Id;
                    }

                    if(Id.length()==5)
                    {
                        Stt=dvcs+"0000000"+Id;
                    }

                    if(Id.length()==7)
                    {
                        Stt=dvcs+"000000"+Id;
                    }
                    if(Id.length()==8)
                    {
                        Stt=dvcs+"00000"+Id;
                    }
                    if(Id.length()==9)
                    {
                        Stt=dvcs+"0000"+Id;
                    }
                    if(Id.length()==10)
                    {
                        Stt=dvcs+"000"+Id;
                    }
                    if(Id.length()==11)
                    {
                        Stt=dvcs+"00"+Id;
                    }
                    if(Id.length()==12)
                    {
                        Stt=dvcs+"0"+Id;
                    }
                    if(Id.length()==13)
                    {
                        Stt=dvcs+Id;
                    }



                }
            }

            con.close();
        }
    }

    public void taothongtinCN()
    {
        if(dvcs.equals("A01"))
        {
            dvcs1="1N101";

        }
        if(dvcs.equals("A02"))
        {
            dvcs1="2N101-01";

        }
        if(dvcs.equals("A03"))
        {
            dvcs1="2N301";

        }

        if(dvcs.equals("A04"))
        {
            dvcs1="2N302";

        }
        if(dvcs.equals("A05"))
        {
            dvcs1="2N201";

        }
        if(dvcs.equals("A06"))
        {
            dvcs1="2N202";

        }
        if(dvcs.equals("A07"))
        {
            dvcs1="2T101";

        }
        if(dvcs.equals("A08"))
        {
            dvcs1="2T102";

        }
        if(dvcs.equals("A09"))
        {
            dvcs1="2T103";

        }
        if(dvcs.equals("A10"))
        {
            dvcs1="2B101";

        }
    }


}