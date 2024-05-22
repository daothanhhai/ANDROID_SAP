package com.example.erp_tuyen.loginsap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class activity_xacnhantheodoigh extends AppCompatActivity {


    String Id, Stt,SttGoc, IdCt, Rowid;
    String IdUser,dvcs,username, macbnv,dvcs1,makho,soseri,mabp;
    AutoCompleteTextView atctgghnvgh,atcptggh;
    ImageView imgtgghgh,imgptggh;
    String stttdgh;
    Button btlpghgh,btghxnpgh;
    CheckBox cbxntggh;
    String checkxn;
    ResultSet resultSet;
    ArrayList<String> arraypgh = new ArrayList<String>();

    private ArrayList<String> datasoct = new ArrayList<String>();
    private ArrayList<String> datangayct = new ArrayList<String>();
    private ArrayList<String> datadoituong = new ArrayList<String>();
    private ArrayList<String> datanhanviengh = new ArrayList<String>();
    private ArrayList<String> datatienhd = new ArrayList<String>();

    private TableLayout table;
    private TableRow row;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xacnhantheodoigh);
        getSupportActionBar().setTitle("Giao Hàng Xác Nhận");
        atctgghnvgh = (AutoCompleteTextView) findViewById(R.id.actnvghtggh);
        imgtgghgh=(ImageView) findViewById(R.id.imagenvghtggh);
        atcptggh = (AutoCompleteTextView) findViewById(R.id.actptggh);
        imgptggh = (ImageView) findViewById(R.id.imageptggh);
        btlpghgh=(Button) findViewById(R.id.btloadpghgh);
        btghxnpgh=(Button) findViewById(R.id.btluughxnpgh);
        cbxntggh=(CheckBox) findViewById(R.id.cbxntggh);

        table = (TableLayout) findViewById(R.id.tbtggh);

        Intent intent=getIntent();
        IdUser=intent.getStringExtra("IdUser");
        dvcs=intent.getStringExtra("Ma_Dvcs");
        username = intent.getStringExtra("USERNAME");
        macbnv = intent.getStringExtra("Ma_CbNv");

        atctgghnvgh.setText(macbnv);

        taothongtinCN();

        try {
            selectphieutghg();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraypgh);
        atcptggh.setAdapter(adapter1);

        btlpghgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    table.removeViews(1, Math.max(0, table.getChildCount() - 1));
                    selectsohdgh();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        imgptggh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atcptggh.showDropDown();
            }
        });

        btghxnpgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ghxnpgh();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

    }

    public void selectphieutghg() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            String ngayct = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            PreparedStatement statement = con.prepareStatement("EXEC usp_XacNhanTheoGioiGH_Android '"+ ngayct +"','"+ macbnv +"'");
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String stt = resultSet.getString("Stt");
                    String soct = resultSet.getString("So_Ct");
                    String ghxn = resultSet.getString("Gh_Xn");

                    arraypgh.add(stt + ' ' + '|' + ' ' + soct + ' '+ ghxn + ' ');
                }
            }


        }
    }


    public void selectsohdgh() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {


            stttdgh = atcptggh.getText().toString();
            checkxn = atcptggh.getText().toString();
            if(stttdgh != "")
            {
                stttdgh=stttdgh.substring(0,16);
                checkxn=checkxn.substring(32,33);
            }


            if(checkxn.equals("0"))
            {
                cbxntggh.setChecked(false);
            }
            if(checkxn.equals("1"))
            {
                cbxntggh.setChecked(true);
            }


            PreparedStatement statement = con.prepareStatement("usp_LoadTheoDoiGH_Android" + "'"+ stttdgh +"' " );
            resultSet = statement.executeQuery();


            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String stt = resultSet.getString("Stt_Cd_Htt");
                    String soct = resultSet.getString("So_Ct");
                    String ngayct =resultSet.getString("Ngay_Ct");
                    ngayct=ngayct.substring(0,10);
                    String tendt = resultSet.getString("Ten_Dt");
                    String tongtien = resultSet.getString("Tien_Nt");


                    datasoct.add(soct);
                    datangayct.add(ngayct);
                    datadoituong.add(tendt);
                    datatienhd.add(String.format("%,.0f", Float.valueOf(tongtien)));

                    row = new TableRow(this);
                    TextView t1 = new TextView(this);
                    TextView t2 = new TextView(this);
                    TextView t3 = new TextView(this);
                    TextView t4 = new TextView(this);

                    if(tendt.equals("Tong_Cong"))
                    {
                        t3.setTextSize(20);
                        t3.setTextColor(Color.RED);
                        t3.setTextSize(20);
                        t3.setTextColor(Color.RED);
                        t4.setTextSize(20);
                        t4.setTextColor(Color.RED);

                    }

                    t1.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t1.setWidth(80);
                    t1.setHeight(100);
                    t2.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t2.setGravity(Gravity.LEFT);
                    t2.setWidth(80);
                    t2.setHeight(100);
                    t3.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t3.setGravity(Gravity.LEFT);
                    t3.setWidth(250);
                    t3.setHeight(100);
                    t4.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t4.setGravity(Gravity.RIGHT);
                    t1.setWidth(80);
                    t4.setHeight(100);

                    for (int j = 0; j < datasoct.size(); j++) {
                        String psoct = datasoct.get(j);
                        String pngayct = datangayct.get(j);
                        String pkh = datadoituong.get(j);
                        String ptienhd = datatienhd.get(j);

                        t1.setText(psoct);
                        t2.setText(pngayct);
                        t3.setText(pkh);
                        t4.setText(ptienhd);
                    }
                    row.setBackground(ContextCompat.getDrawable(this,R.drawable.border));

                    row.addView(t1);
                    row.addView(t2);
                    row.addView(t3);
                    row.addView(t4);
                    table.addView(row);

                }
            }


            con.close();
        }
    }


    public void ghxnpgh() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
            int xn=0;
            if(cbxntggh.isChecked())
            {
                xn=1;
            }
            else {
                xn=0;
            }

            String query = "UPDATE B30Bk Set Gh_Xn='"+ xn +"' WHERE Stt = '"+ stttdgh +"'";
            //trên đây là câu truy vấn
            Statement stmt = con.createStatement(); //blah blah blah
            //resultSet = stmt.executeQuery(query); //thực thi và trả về một cục ResultSet, nó là gì thì Google, tui chịu
            stmt.executeUpdate(query);

            if(xn==1)
            Toast.makeText(this, "Đã xác nhận", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Bỏ xác nhận", Toast.LENGTH_SHORT).show();

            con.close();
        }
    }



    public void luubangke() throws SQLException {

        try {
            Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
            if (con == null) {
                Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
            } else {
                String ngayct = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                taostt();
                taorowid();
                PreparedStatement statement = con.prepareStatement("EXEC usp_InsertB30Bk_Android " +
                        "'" + dvcs + "' ,'"+ dvcs1 +"','"+ ngayct +"',N' ," +
                        ",'"+ IdUser +"','"+ IdUser +"','"+ Stt +"' ");

                statement.executeUpdate();

                String querystt = "SELECT Stt FROM B30bk WHERE Stt_AnDroid=N'"+ Stt +"'";
                Statement stmtstt = con.createStatement();
                resultSet = stmtstt.executeQuery(querystt);

                for (int i = 0; i <= resultSet.getRow(); i++) {
                    if (resultSet.next())
                    {
                        SttGoc = resultSet.getString("Stt");
                    }
                }

                for(int i=0;i<datasoct.size();i++) {
                    taorowid();
                    int builinorder = i + 1;
                    String tienhd = datatienhd.get(i);

                    PreparedStatement statement1 = con.prepareStatement("EXEC usp_InsertB30BkDetail_Android " +
                            "'" + dvcs + "' ,'"+ dvcs1 +"','BK','"+ ngayct +"','"+ macbnv +"' ," +
                            ","+ tienhd+","+tienhd+",'"+ IdUser +"','"+ IdUser +"','"+ Stt +"' ");

                    statement1.executeUpdate();


                }

                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void taostt() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            String query = "SELECT MAX(Id),(MAX(Id)+1) AS IdM FROM B30Bk WHERE Ma_Dvcs='" + dvcs + "'";
            //trên đây là câu truy vấn
            Statement stmt = con.createStatement(); //blah blah blah
            resultSet = stmt.executeQuery(query); //thực thi và trả về một cục ResultSet, nó là gì thì Google, tui chịu

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    Id = resultSet.getString("IdM");

                    if (Id.length() == 6) {
                        Stt = dvcs + "0000000" + Id;
                    }

                    if (Id.length() == 7) {
                        Stt = dvcs + "000000" + Id;
                    }
                    if (Id.length() == 8) {
                        Stt = dvcs + "00000" + Id;
                    }
                    if (Id.length() == 9) {
                        Stt = dvcs + "0000" + Id;
                    }
                    if (Id.length() == 10) {
                        Stt = dvcs + "000" + Id;
                    }
                    if (Id.length() == 11) {
                        Stt = dvcs + "00" + Id;
                    }
                    if (Id.length() == 12) {
                        Stt = dvcs + "0" + Id;
                    }
                    if (Id.length() == 13) {
                        Stt = dvcs + Id;
                    }

                }
            }

            con.close();
        }
    }


    public void taorowid() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            String query = "SELECT MAX(Id),(MAX(Id)+1) AS IdCt FROM B30BkDetail WHERE Ma_Dvcs='" + dvcs + "'";
            //trên đây là câu truy vấn
            Statement stmt = con.createStatement(); //blah blah blah
            resultSet = stmt.executeQuery(query); //thực thi và trả về một cục ResultSet, nó là gì thì Google, tui chịu

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    IdCt = resultSet.getString("IdCt");
                    if (IdCt.length() == 7) {
                        Rowid = dvcs + "0000" + IdCt + "HD";
                    }
                    if (IdCt.length() == 8) {
                        Rowid = dvcs + "000" + IdCt + "HD";
                    }
                    if (IdCt.length() == 9) {
                        Rowid = dvcs + "00" + IdCt + "HD";
                    }
                    if (IdCt.length() == 10) {
                        Rowid = dvcs + "0" + IdCt + "HD";
                    }
                    if (IdCt.length() == 11) {
                        Rowid = dvcs + IdCt + "HD";
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
            makho="TP1N1-02";
        }
        if(dvcs.equals("A02"))
        {
            dvcs1="2N101-01";
            makho="TP2N1-01";
            soseri="CM/21E";
            mabp="B3-684";
        }
        if(dvcs.equals("A03"))
        {
            dvcs1="2N301";
            makho="TP2N3-01";
            soseri="CT/21E";
            mabp="B3-540";
        }

        if(dvcs.equals("A04"))
        {
            dvcs1="2N302";
            makho="TP2N3-02";
            soseri="TG/20E";
        }
        if(dvcs.equals("A05"))
        {
            dvcs1="2N201";
            makho="TP2N2-01";
            soseri="MD/21E";
        }
        if(dvcs.equals("A06"))
        {
            dvcs1="2N202";
            makho="TP2N2-02";
            soseri="VT/20E";
        }
        if(dvcs.equals("A07"))
        {
            dvcs1="2T101";
            makho="TP2T1-01";
            soseri="NT/20E";
        }
        if(dvcs.equals("A08"))
        {
            dvcs1="2T102";
            makho="TP2T1-02";
            soseri="DN/20E";
        }
        if(dvcs.equals("A09"))
        {
            dvcs1="2T103";
            makho="TP2T1-03";
            soseri="NA/20E";
        }
        if(dvcs.equals("A10"))
        {
            dvcs1="2B101";
            makho="TP2B1-01";
            soseri="HN/20E";
        }
    }
}