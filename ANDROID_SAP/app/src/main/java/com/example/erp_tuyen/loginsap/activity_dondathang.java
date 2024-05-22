package com.example.erp_tuyen.loginsap;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class activity_dondathang extends AppCompatActivity {
    GridView grdoituong;
    //GridView grdonhang;
    EditText edhovaten, eddiachi, edngaydonhang, edtenvt, edgiagoc, edchietkhau;
    EditText edsoluong, eddongia, edthanhtien;
    AutoCompleteTextView atv1, atv2,atvctkm,acthopdong;
    Button btthem, btxoa, btluu;

    ArrayList<DonDatHang> arrayDonDatHang;
    ArrayList<String> arraymadt = new ArrayList<String>();
    ArrayList<String> arraymavt = new ArrayList<String>();
    ArrayList<String> arraymalsp = new ArrayList<String>();
    ArrayList<String> arraymahopdong = new ArrayList<String>();

    DonDatHangAdapter adapter;
    private MainActivity context;
    ResultSet resultSet;
    String madt, tendt, matdv, chietkhau, masothue, thuegtgt, hantt, hantt_cktt, malkh, malkh_tanduoc, maktcn, manvgs, manvgh, mavung;
    String mavt,malsp, bgid, mathue, dvt, giagoc="0",dongia="0",thanhtien="0",mavtgtc,codekmle,codekmsi,codekmkhac,soluong;
    String ctkm_ck;
    int checkkm=0;
    Float total1;
    Float tongtienhang,tongtienthue,tongcong;
    Double giatricode=0.0,tonggiatricode=0.0,ckkm=0.0;
    Double tiencodekmle_A=0.0,tiencodekmle_B=0.0,tiencodekmsi_A=0.0,tiencodekmsi_B=0.0,tiencodekmkhac=0.0;
    String ctkm,loaikh,phanloaicp="";
    String tkno,tkco,tkco2,giatbtt="0",makm="",mathue_km="";
    CheckBox chkm;

    String Id, Stt, IdCt, Rowid;
    private ProgressDialog LoadingBar;
    //add vào Gridview
    ArrayList<String> ArrMaVt = new ArrayList<String>();

    ArrayAdapter<String> AdapterGr;

    private ArrayList<String> data = new ArrayList<String>();
    private ArrayList<String> data1 = new ArrayList<String>();
    private ArrayList<String> data2 = new ArrayList<String>();
    private ArrayList<String> data3 = new ArrayList<String>();
    private ArrayList<String> data4 = new ArrayList<String>();
    private ArrayList<String> data5 = new ArrayList<String>();
    private ArrayList<String> data6 = new ArrayList<String>();
    private ArrayList<String> data7 = new ArrayList<String>();
    private ArrayList<String> data8 = new ArrayList<String>();
    private ArrayList<String> data9 = new ArrayList<String>();
    private ArrayList<String> data10 = new ArrayList<String>();
    private ArrayList<String> data11 = new ArrayList<String>();
    private ArrayList<String> data12 = new ArrayList<String>();
    private ArrayList<String> data13 = new ArrayList<String>();
    private ArrayList<String> data14 = new ArrayList<String>();
    private ArrayList<String> data15 = new ArrayList<String>();


    private TableLayout table;
    private TableRow row;

    TextView tvtienhang,tvgiatricode, tvthue, tvtongcong;
    String IdUser,dvcs,dvcs1,makho,soseri,username, macbnv;
    String madtvt,tendtvt,diachivt,chietkhauvt; // Đối tượng được checkin viếng thăm
    String sohopdong="",bizdocid="";

    EditText t5;
    TextView t6,t7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dondathang);
        getSupportActionBar().setTitle("Đơn đặt Hàng OPC");
        atv1 = (AutoCompleteTextView) findViewById(R.id.actv1);
        atv2 = (AutoCompleteTextView) findViewById(R.id.actv2);
        atvctkm=(AutoCompleteTextView) findViewById(R.id.actctkm);
        acthopdong=(AutoCompleteTextView) findViewById(R.id.acthopdong);
        chkm=(CheckBox) findViewById(R.id.checkkm);
        //TextView tv = (TextView) findViewById(R.id.tv);
        ImageView image = (ImageView) findViewById(R.id.image);
        ImageView image1 = (ImageView) findViewById(R.id.image1);
        ImageView imagectkm = (ImageView) findViewById(R.id.imagectkm);
        ImageView imagehopdong=(ImageView) findViewById(R.id.imagehopdong);
        edhovaten = (EditText) findViewById(R.id.editTexthovaten);
        eddiachi = (EditText) findViewById(R.id.editTextdiachi);
        edngaydonhang = (EditText) findViewById(R.id.editTextngaydonhang);
        edtenvt = (EditText) findViewById(R.id.editTexttenvt);
        edgiagoc = (EditText) findViewById(R.id.editTextgiagoc);
        edchietkhau = (EditText) findViewById(R.id.editTextchietkhau);
        edsoluong = (EditText) findViewById(R.id.editTextsoluong);
        eddongia = (EditText) findViewById(R.id.editTextdongia);
        edthanhtien = (EditText) findViewById(R.id.editTextthanhtien);
        btthem = (Button) findViewById(R.id.buttonadd);
        btxoa = (Button) findViewById(R.id.buttondelete);
        btluu = (Button) findViewById(R.id.buttonsave);
        //grdonhang = (GridView) findViewById(R.id.Gridviewdonhang);
        tvtienhang = (TextView) findViewById(R.id.textViewtienhang);
        tvthue = (TextView) findViewById(R.id.textViewthue);
        tvtongcong = (TextView) findViewById(R.id.textViewtongcong);
        tvgiatricode=(TextView) findViewById(R.id.tvgtcode);

        table = (TableLayout) findViewById(R.id.tb1);
        //t5=(EditText) findViewById(R.id.t2) ;
        row=(TableRow) findViewById(R.id.tbrow1);

        Intent intent=getIntent();
        IdUser=intent.getStringExtra("IdUser");
        dvcs=intent.getStringExtra("Ma_Dvcs");
        username = intent.getStringExtra("USERNAME");
        macbnv = intent.getStringExtra("Ma_CbNv");
        madtvt=intent.getStringExtra("MADTVT");
        tendtvt=intent.getStringExtra("TENDTVT");
        diachivt=intent.getStringExtra("DIACHIVT");
        chietkhauvt=intent.getStringExtra("CHIETKHAUVT");
        taothongtinCN();



        AdapterGr = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ArrMaVt);

        // Load tự động mã đối tượng vào texbox
        //Load tự động mã vật tư vào texbox
        try {
            selectdoituong();
            selectmavt();
            selectctkm();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraymadt);
        atv1.setAdapter(adapter1);

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraymavt);
        atv2.setAdapter(adapter2);

        final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraymalsp);
        atvctkm.setAdapter(adapter3);

        final ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraymahopdong);
        acthopdong.setAdapter(adapter4);

        String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        edngaydonhang.setText(date_n);


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atv1.showDropDown();

            }
        });
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atv2.showDropDown();
            }
        });

        imagectkm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atvctkm.showDropDown();
            }
        });

        if(madtvt!="")
        {
            atv1.setText(madtvt);
            madt = atv1.getText().toString();
            try {
                selecttendoituong();
                selecthopdong();
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            edhovaten.setText(tendtvt);
//            eddiachi.setText(diachivt);
//
//            edchietkhau.setText(String.format("%.0f", Float.valueOf(chietkhauvt)));
        }


        atv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                madt = atv1.getText().toString();
                if (madt.equals("")) {
                    madt = "";
                } else {
                    madt = atv1.getText().toString().substring(0, 13);
                }

                try {
                    selecttendoituong();
                    selecthopdong();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });


        imagehopdong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acthopdong.showDropDown();

            }
        });

        acthopdong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sohopdong = acthopdong.getText().toString();

                try {
                    selectbizdocid();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });


        atv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mavt = atv2.getText().toString();
                if(mavt != "")
                {
                    mavt=mavt.substring(0,9);
                }


                // Không có hợp đồng
                if(sohopdong.equals("")) {
                    try {
                        selecttenvattu();
                        selectbanggia();
                        selectbgdetail();
                        dongia();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    try {
                        selecttenvattu();
                        //selectbanggia();
                        selectbghopdong();
                        dongia();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        


        chkm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkkm=1;
                    edgiagoc.setText("0");
                    edchietkhau.setText("0");
                    eddongia.setText("0");
                }
                else
                {
                    checkkm=0;
                }
            }
        });


        edngaydonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonngay();
            }
        });

        edsoluong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                soluong = edsoluong.getText().toString();
                float sl, dg, tt;

                ctkm_ck= atvctkm.getText().toString();

                if(ctkm_ck.equals("CK01"))
                {
                    try {
                        selectckkm();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    String giatrickkm= String.valueOf(ckkm);
                    edchietkhau.setText(giatrickkm);

                    Double ck_KM=  (Float.parseFloat(giagoc) * ckkm)/100;
                    dongia= String.valueOf(Float.parseFloat(giagoc) - ck_KM);
                    eddongia.setText(String.format("%,.0f", Float.valueOf(dongia)));
                }

                if(ctkm_ck.equals("CK03"))
                {
                    try {
                        selectckkm();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    String giatrickkm= String.valueOf(ckkm);
                    edchietkhau.setText(giatrickkm);

                    Double ck_KM=  (Float.parseFloat(giagoc) * ckkm)/100;
                    dongia= String.valueOf(Float.parseFloat(giagoc) - ck_KM);
                    eddongia.setText(String.format("%,.0f", Float.valueOf(dongia)));
                }

                if(ctkm_ck.equals("CK05"))
                {
                    try {
                        selectckkm();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    String giatrickkm= String.valueOf(ckkm);
                    edchietkhau.setText(giatrickkm);

                    Double ck_KM=  (Float.parseFloat(giagoc) * ckkm)/100;
                    dongia= String.valueOf(Float.parseFloat(giagoc) - ck_KM);
                    eddongia.setText(String.format("%,.0f", Float.valueOf(dongia)));
                }

                if(checkkm==1)
                {
                    dongia="0";

                }

                if (soluong.length() > 0) {
                    sl = Float.parseFloat(soluong);
                    dg = Float.parseFloat(dongia);
                    tt = sl * dg;
                    thanhtien= String.valueOf(tt);
                    edthanhtien.setText(String.format("%,.0f", Float.valueOf(tt)));
                }
                if (soluong.length() == 0) {
                    edthanhtien.setText(String.format("%,.0f", Float.valueOf(0)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                them();
            }
        });

        btxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();


            }
        });

        btluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LoadingBar=new ProgressDialog(activity_dondathang.this);
                LoadingBar.setTitle("BRAVO - ANDROID");
                LoadingBar.setMessage("Lưu đơn hàng");
                LoadingBar.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            luudonhang();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        LoadingBar.dismiss();
                    }
                },100);
            }
        });




    }


    public void dongia() {

        if (chietkhau.equals("")) {
            chietkhau = "0";
        }
        float ck, gg,dg;
        if(giagoc.equals("")){
            giagoc="0";
        }
        gg = Float.parseFloat(giagoc);
        ck = Float.parseFloat(chietkhau);
        dg = gg - (gg * ck) / 100;
        dongia= String.valueOf(dg);
        eddongia.setText(String.format("%,.0f", Float.valueOf(dg)));

    }

    public void selectdoituong() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {


            PreparedStatement statement = con.prepareStatement("EXEC usp_DoiTuongTDV_Android '"+ macbnv +"'");
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String madt = resultSet.getString("Ma_Dt");
                    String tendt = resultSet.getString("Ten_Dt");
                    //arraymadt.add(madt);
                    arraymadt.add(madt + ' ' + tendt);
                }
            }
            //adapter.notifyDataSetChanged();
            con.close();
        }
    }

    public void selecttendoituong() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
            //String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());

            PreparedStatement statement = con.prepareStatement("EXEC usp_ThongTinDoiTuong_Android '"+ madt +"'");
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())
                {
                    tendt = resultSet.getString("Ten_Dt");
                    String diachi = resultSet.getString("Dia_Chi");
                    chietkhau = resultSet.getString("Chiet_Khau");
                    matdv = resultSet.getString("Ma_TDV");
                    hantt = resultSet.getString("Han_Tt");
                    hantt_cktt = resultSet.getString("Han_Tt_CKTT");
                    malkh = resultSet.getString("Ma_LKH");
                    malkh_tanduoc = resultSet.getString("Ma_LKH_TanDuoc");
                    maktcn = resultSet.getString("Ma_KtCn");
                    manvgs = resultSet.getString("Ma_NvGs");
                    manvgh = resultSet.getString("Ma_NvGh");
                    mavung = resultSet.getString("Ma_Vung");
                    masothue = resultSet.getString("Ma_So_Thue");
                    loaikh=resultSet.getString("Khuyen_Mai_OPC");
                    edhovaten.setText(tendt);
                    eddiachi.setText(diachi);
                    edchietkhau.setText(String.format("%.0f", Float.valueOf(chietkhau)));

                }
            }

            //adapter.notifyDataSetChanged();
            con.close();
        }
    }

    public void selecthopdong() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
            String ngayct = edngaydonhang.getText().toString();

            PreparedStatement statement = con.prepareStatement("EXEC usp_HopDongKhachHang_Android '"+ ngayct +"','"+ madt +"'");
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String sohopdong = resultSet.getString("DocumentNo");
                    //String tendt = resultSet.getString("Ten_Dt");
                    arraymahopdong.add(sohopdong);
                    //arraymadt.add(madt + ' ' + tendt);
                }
            }
            //adapter.notifyDataSetChanged();
            con.close();
        }
    }

    public void selectbizdocid() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {


            String query = "SELECT BizDocId FROM B30BizDoc WHERE DocumentNo=N'"+ sohopdong +"'";

            Statement stmt = con.createStatement();
            resultSet = stmt.executeQuery(query);

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())
                {
                    bizdocid = resultSet.getString("BizDocId");
                }
            }
            //adapter.notifyDataSetChanged();
            con.close();
        }
    }

    public void selecttenvattu() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            PreparedStatement statement = con.prepareStatement("EXEC usp_ThongTinVt_Android '"+ mavt +"'");
            resultSet = statement.executeQuery();


            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())
                {
                    String tenvt = resultSet.getString("Ten_Vt");
                    String loaitp=resultSet.getString("LoaiTp");
                    edtenvt.setText(tenvt);
                    mathue = resultSet.getString("Ma_Thue");
                    dvt = resultSet.getString("Dvt");
                    tkno=resultSet.getString("Tk_No");
                    tkco=resultSet.getString("Tk_Co");
                    tkco2=resultSet.getString("Tk_Co2");

                    if (mathue.equals("R05")) {
                        thuegtgt = "0.05";
                        mathue_km="R05";
                    }
                    if (mathue.equals("R10")) {
                        thuegtgt = "0.1";
                        mathue_km="R10";
                    }
                    // Nếu mã khuyến mãi thì set mã thuế bằng mã thuế ban đầu để lưu xuống bảng B30CtVt
                    if(mathue.equals("") && loaitp.equals("04") ){
                        mathue=mathue_km;
                    }


                }
            }


            con.close();
        }
    }


    public void selectmavt() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            String query = "SELECT vt.Ma_Vt,Vt.Ten_Vt FROM B20DmVt vt  WHERE (vt.Ma_Vt LIKE 'TP%' OR vt.Ma_Vt LIKE 'HH%' " +
                    "OR vt.Ma_Vt LIKE 'TD%' OR vt.Ma_Vt LIKE 'Code%')" +
                    "AND LEN(vt.Ma_Vt) > 4 ";
            //trên đây là câu truy vấn
            Statement stmt = con.createStatement(); //blah blah blah
            resultSet = stmt.executeQuery(query); //thực thi và trả về một cục ResultSet, nó là gì thì Google, tui chịu

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String mavt = resultSet.getString("Ma_Vt");
                    String tenvt = resultSet.getString("Ten_Vt");
                    //arraymavt.add(mavt);
                    arraymavt.add(mavt + " " + tenvt);
                }
            }
            //adapter.notifyDataSetChanged();
            con.close();
        }
    }


    private void chonngay() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(calendar.DATE);
        int thang = calendar.get(calendar.MONTH);
        int nam = calendar.get(calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                edngaydonhang.setText(simpleDateFormat.format(calendar.getTime()));

            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void selectbanggia() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            PreparedStatement statement = con.prepareStatement("EXEC usp_BangGia_Android");
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())
                {
                    bgid = resultSet.getString("BGId");
                }
            }

            con.close();
        }
    }

    public void selectbgdetail() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
                PreparedStatement statement = con.prepareStatement("EXEC usp_BangGiaDetail_Android '"+ bgid +"','"+ mavt +"'");
                resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    giagoc = resultSet.getString("Gia_Nt");
                    edgiagoc.setText(String.format("%,.0f", Float.valueOf(giagoc)));

                }
            }


            con.close();
        }
    }

    public void selectbghopdong() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
            PreparedStatement statement = con.prepareStatement("EXEC usp_BangGiaHopDong_Android '"+ bizdocid +"','"+ mavt +"'");
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    giagoc = resultSet.getString("Gia_Nt");
                    edgiagoc.setText(String.format("%,.0f", Float.valueOf(giagoc)));

                }
            }


            con.close();
        }
    }


    public void selectctkm() throws SQLException {
        Connection con = SERVER.Connect();
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            PreparedStatement statement = con.prepareStatement("EXEC usp_MaLoaiKM_Android");
            resultSet = statement.executeQuery();
            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())
                {
                    malsp = resultSet.getString("Ma_LSP");
                    arraymalsp.add(malsp);
                }
            }


            con.close();
        }
    }

    private void add() {
        String mavt = atv2.getText().toString();

        String soluong = edsoluong.getText().toString();
        String giagoc = edgiagoc.getText().toString();
        String chietkhau = edchietkhau.getText().toString();
        String dongia = eddongia.getText().toString();
        String thanhtien = edthanhtien.getText().toString();
        if (!mavt.isEmpty() && mavt.length() > 0) {
            AdapterGr.add(mavt);

            AdapterGr.add(soluong);
            //AdapterGr.add(giagoc);
            //AdapterGr.add(chietkhau);
            AdapterGr.add(dongia);
            AdapterGr.add(thanhtien);

            AdapterGr.notifyDataSetChanged();

        } else {
            Toast.makeText(getApplicationContext(), "Nothing To add", Toast.LENGTH_SHORT).show();
        }
    }
    // Xóa lưới đơn hàng
    private void delete() {
        table.removeViews(1, Math.max(0, table.getChildCount() - 1));

        int dem=data.size();
        for(int i=0;i<dem;i++)
        {
            data.remove(0);
            data1.remove(0);
            data2.remove(0);
            data3.remove(0);
            data4.remove(0);
            data5.remove(0);
            data6.remove(0);
            data7.remove(0);
            data8.remove(0);
            data9.remove(0);
            data10.remove(0);
            data11.remove(0);
            data12.remove(0);
            data13.remove(0);
            data14.remove(0);
            data15.remove(0);

        }

        tvtienhang.setText("");
        tvthue.setText("");
        tvtongcong.setText("");
        tongtienhang= Float.valueOf(0);
        tongtienthue= Float.valueOf(0);
        tongcong= Float.valueOf(0);
        giatricode=0.0;
        tiencodekmle_A=0.0;
        tiencodekmsi_A=0.0;
        tiencodekmle_B=0.0;
        tiencodekmsi_B=0.0;
        tiencodekmkhac=0.0;
    }

    private void them() {

        String productname = atv2.getText().toString();
        mavtgtc=atv2.getText().toString();
        mavtgtc=mavtgtc.substring(0,9);
        String km="0";

        if(chkm.isChecked()) {
            km="1";
            giatbtt="1";
            tkno="6416";
            makm="P28";

        }
        ctkm=atvctkm.getText().toString();

        String qty = edsoluong.getText().toString();
        String gg    = giagoc;
        String price = dongia;
        double ttgtc= Float.parseFloat(thanhtien);

        try {
            selectvtgtc();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(ctkm.equals("01A") && loaikh.equals("1")) {
            if (codekmle.equals("3")) {
                ttgtc=ttgtc*1.05;
                tiencodekmle_A = tiencodekmle_A + ttgtc;
            }
        }
        if(ctkm.equals("01B") && loaikh.equals("1")) {
            if (codekmle.equals("4")) {
                ttgtc=ttgtc*1.05;
                tiencodekmle_B = tiencodekmle_B + ttgtc;
            }
        }
        if(ctkm.equals("01A") && loaikh.equals("2")) {
            if (codekmsi.equals("3")) {
                ttgtc=ttgtc*1.1;
                tiencodekmsi_A = tiencodekmsi_A + ttgtc;
            }
        }
        if(ctkm.equals("01A") && loaikh.equals("2")) {
            if (codekmsi.equals("4")) {
                ttgtc=ttgtc*1.1;
                tiencodekmsi_B = tiencodekmsi_B + ttgtc;
            }
        }
        if(ctkm.equals("01C")) {
            if (codekmkhac.equals("5")) {
                ttgtc=ttgtc*1.05;
                tiencodekmkhac = tiencodekmkhac + ttgtc;
            }
        }
        if(ctkm.equals("01D")) {
            if (codekmle.equals("7")) {
                ttgtc=ttgtc*1.1;
                tiencodekmkhac = tiencodekmkhac + ttgtc;
            }
        }

        if(ctkm.equals("01E")) {
            if (codekmle.equals("6")) {
                ttgtc=ttgtc*1.1;
                tiencodekmkhac = tiencodekmkhac + ttgtc;
            }
        }

        if(mavtgtc.equals("CODE-TL1 "))
            try {
                selectgiatricode();
                thanhtien= String.valueOf(0);
                qty= String.valueOf(0);
                price= String.valueOf(0);
                gg= String.valueOf(0);
                phanloaicp=ctkm;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                }

        String tt = thanhtien;
        data.add(productname);
        data1.add(km);
        data2.add(ctkm);

        data3.add(String.format("%,.0f", Float.valueOf(String.valueOf(giatricode))));
        data4.add(String.valueOf(qty));
        data5.add(String.valueOf(price));
        data6.add(String.valueOf(tt));
        data7.add(String.valueOf(gg));
        data8.add(String.valueOf(tkno));
        data9.add(String.valueOf(tkco));
        data10.add(String.valueOf(tkco2));
        data11.add(String.valueOf(giatbtt));
        data12.add(String.valueOf(phanloaicp));
        data13.add(String.valueOf(dvt));
        data14.add(String.valueOf(makm));
        data15.add(String.valueOf(mathue));

        //TableLayout table = (TableLayout) findViewById(R.id.tb1);
        row = new TableRow(this);
        //TableRow row = new TableRow(this);
        TextView t1 = new TextView(this);
        TextView t2 = new TextView(this);
        TextView t3 = new TextView(this);
        TextView t4 = new TextView(this);
        //TextView t5 = new TextView(this);
        t5 = new EditText(this);
        t6 = new TextView(this);
        t7 = new TextView(this);


        t1.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
        t1.setHeight(60);
        t1.setWidth(250);
        t2.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
        t2.setHeight(60);
        t3.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
        t3.setHeight(60);
        t4.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
        t4.setHeight(60);
        t5.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
        t5.setHeight(60);
        t6.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
        t6.setHeight(60);
        t7.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
        t7.setHeight(60);
        //t8.setBackground(ContextCompat.getDrawable(this,R.drawable.border));

        float tienhang = 0, thue = 0;
        String total;

        for (int i = 0; i < data.size(); i++) {
            String pname = data.get(i);
            String pkm = data1.get(i);
            String qctkm = data2.get(i);
            String pgtc = data3.get(i);
            String qtyy=data4.get(i);
            String pdg=data5.get(i);
            total = data6.get(i);
            total1= Float.valueOf(total);
            String ggg=data7.get(i);


            t1.setText(pname);
            t2.setText(pkm);
            t3.setText(qctkm);
            t4.setText(pgtc);
            t5.setText(qtyy);
            t6.setText(eddongia.getText().toString());
            t7.setText(edthanhtien.getText().toString());
            //t8.setText(ggg);


            tienhang = tienhang + total1;
            tongtienhang=tienhang;
            tvtienhang.setText(String.format("%,.0f",Float.valueOf(tienhang)));
            thue = tienhang * Float.parseFloat(thuegtgt);
            tongtienthue=thue;
            tvthue.setText(String.format("%,.0f", Float.valueOf(thue)));
            tongcong = tienhang + thue;
            tvtongcong.setText(String.format("%,.0f", Float.valueOf(tongcong)));



        }
        row.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
        row.addView(t1);
        row.addView(t2);
        row.addView(t3);
        row.addView(t4);
        row.addView(t5);
        row.addView(t6);
        row.addView(t7);
        //row.addView(t8);
        table.addView(row);


        atv2.setText("");
        edsoluong.setText("");
        edgiagoc.setText("");
        eddongia.setText("");

        edthanhtien.setText("");
        atv2.requestFocus();
        chkm.setChecked(false);
        atvctkm.setText("");
        edtenvt.setText("");

        tonggiatricode+=giatricode;
        giatricode=0.0;
        phanloaicp="";
        giagoc="0";

        t5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                float sl, dg, tt;
                View a=row.getChildAt(start);
                TableRow row = (TableRow) a.getParent();
                // It's index
                int index = table.indexOfChild(row);

                soluong = String.valueOf(s);

                if(soluong.equals(""))
                {
                    soluong="0";
                }


                sl = Float.parseFloat(soluong);
                dg = Float.parseFloat(dongia);
                tt = sl * dg;

                t7.setText(String.format("%,.0f", Float.valueOf(tt)));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    public void selectvtgtc() throws SQLException {
        Connection con = SERVER.Connect();
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            String query = "SELECT Code_KM_Le,Code_KM_Si,Code_KM_Khac FROM B20DmVt WHERE Ma_Vt=N'"+ mavtgtc +"' ";
            //trên đây là câu truy vấn
            Statement stmt = con.createStatement(); //blah blah blah
            resultSet = stmt.executeQuery(query); //thực thi và trả về một cục ResultSet, nó là gì thì Google, tui chịu

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())
                {
                    codekmle = resultSet.getString("Code_KM_Le");
                    codekmsi = resultSet.getString("Code_KM_Si");
                    codekmkhac = resultSet.getString("Code_KM_Khac");

                }
            }


            con.close();
        }
    }
        // Lấy Giá trị Code Khuyến Mãi
    private void selectgiatricode() throws SQLException {


        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            PreparedStatement statement = con.prepareStatement("EXEC usp_GiaTriCode_Android " +
                    "'" + madt + "' ,'CODE-TL1','" + tiencodekmle_A + "','"+ tiencodekmsi_A +"','"+ tiencodekmle_B +"'," +
                    "'"+ tiencodekmsi_B +"','"+ tiencodekmkhac +"','" + ctkm + "'");
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {

                    giatricode = Double.valueOf(resultSet.getString("Gia_Tri_Code"));
                }
            }
        }
    }
        // Lấy chiết khấu khuyến mãi
    private void selectckkm() throws SQLException {

        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            PreparedStatement statement = con.prepareStatement("EXEC usp_ChietKhauKM_Android " +
                    "'" + madt + "' ,'"+ mavt +"' ," +
                    "'"+ soluong +"','" + ctkm_ck + "'");
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {

                    ckkm = Double.valueOf(resultSet.getString("CK_KM"));
                }
            }
        }
    }


    public void luudonhang() throws SQLException {
    try {


        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            String ngayct = edngaydonhang.getText().toString();
            String diachi = eddiachi.getText().toString();
            String madt = atv1.getText().toString();
            if (madt.equals("")) {
                madt = "";
            } else {
                madt = atv1.getText().toString().substring(0, 13);
            }
            String banggia = bgid;

            String tongtien = String.valueOf(tongcong);
            String tongcongtienhang = String.valueOf(tongtienhang);
            String thue = String.valueOf(tongtienthue);

            taostt();
            taorowid();

            String query = "INSERT INTO [B30Ct]([Ma_Dvcs],[Ma_Dvcs1],[Stt],[Ma_Ct],[Ngay_Ct],[So_Seri],[Ong_Ba],[Dia_Chi],[Ma_Dt],[Ma_TDV],[Ma_Tte],[Ty_Gia]\n" +
                    ",[So_Luong_Nhap],[Tien_No_Nt],[Tien_No],[Tk_Cong_No],[Han_Tt],[Han_Tt_CKTT],[DiscountPercent]\n" +
                    ",[DiscountAmount],[DiscountForeignCurrAmount],[Ngay_Dk_Gh],[DocStatus],[Da_Soan_Hang]\n" +
                    ",[Da_Kiem_Tra],[Da_Xuat_Hang],[Da_Giao_Hang],[Posted],[RowId_VoucherRegister]\n" +
                    ",[BGId],[Hinh_Thuc_Tt],[Stt_Ref],[Tong_Tien_Nt0],[Tong_Tien_Nt3],[Tong_Tien3],[Tk_No],[Ma_Vung]\n" +
                    ",[Stt_Nx],[Ma_Gd],[Ma_Kho],[Code_KM],[Gia_Tri_Code],[Gia_Tri_Code_Sau],[Ma_LKH]\n" +
                    ",[Ma_LKH_TanDuoc],[Hinh_Thuc_No],[Tong_Diem_TL],[IsActive],[CreatedBy],[ModifiedBy],[Ngay_Hang_Ve])\n" +
                    "VALUES('"+ dvcs +"','"+ dvcs1 +"','" + Stt + "','HD','" + ngayct + "','" + soseri + "','',N'" + diachi + "','" + madt + "','" + matdv + "','VND',1.00,0,'" + tongtien + "','" + tongtien + "',\n" +
                    "'1311'," + hantt + "," + hantt_cktt + ",0,0,0,'',1,0,0,0,0,1,'','" + banggia + "','','','" + tongcongtienhang + "','" + thue + "','" + thue + "','1311','A03-CT','','X131CN','"+ makho +"'\n" +
                    ",0,'"+ tonggiatricode +"',0,'" + malkh + "','" + malkh_tanduoc + "','NO',0,1,'" + IdUser + "','" + IdUser + "','')";
            //trên đây là câu truy vấn
            Statement stmt = con.createStatement(); //blah blah blah
            //resultSet = stmt.executeQuery(query); //thực thi và trả về một cục ResultSet, nó là gì thì Google, tui chịu
            PreparedStatement pstmt = con.prepareStatement(query);
            int result = pstmt.executeUpdate();


            for (int i = 0; i < data.size(); i++) {
                String mavt = data.get(i);
                mavt=mavt.substring(0,9);
                if(mavt.equals("CODE-TL1 "))
                {
                    mavt=mavt.substring(0,8);
                }
                String khuyenmai=data1.get(i);
                String khuyenmaiLMH=data2.get(i);
                String giatricodeTL=data3.get(i);

                String soluong = data4.get(i);
                String gia = data5.get(i);
                String thanhtien = data6.get(i);
                String giagoc1=data7.get(i);
                String tk_no=data8.get(i);
                String tk_co=data9.get(i);
                String tk_co2=data10.get(i);
                String gia_Tbtt=data11.get(i);
                String Phan_Loai_CP=data12.get(i);
                String don_vi_tinh=data13.get(i);
                String ma_km=data14.get(i);
                String ma_thue=data15.get(i);

                giagoc1 = giagoc1.replace(",", "");
                gia = gia.replace(",", "");
                thanhtien = thanhtien.replace(",", "");
                thue = thue.replace(",", "");
                giatricodeTL=giatricodeTL.replace(".","");

                Float thuedong;
                thuedong= Float.valueOf(thanhtien) * Float.valueOf(thuegtgt);
                int builinorder=i+1;


                String query1 = "INSERT INTO [B30CtVt]([Ma_Dvcs],[Ma_Dvcs1],[Stt],[RowId],[BuiltinOrder],[Ma_Ct],[Ngay_Ct]\n" +
                        ",[Ma_Gd],[Tk_No],[Tk_Co],[Tk_No2],[Tk_Co2],[Ma_Dt],[Ma_Km],[Ma_KtCn],[Ma_NvGs],[Lot_Size],[Ma_TDV],[Ma_NvGh],[Ma_Vung],[Ma_Kho]\n" +
                        ",[Ma_Vt],[Dvt],[Ty_Gia_Ht],[So_Luong],[Gia_Nt0],[Gia0],[Gia_Nt],[Gia],[Gia_Nt2],[Gia2],[Tien_Nt],[Tien],[Tien_Nt2]\n" +
                        ",[Tien2],[Ma_Thue],[Thue_Gtgt],[Ma_DtGtgt],[Ten_DtGtgt],[Tk_No3],[Tk_Co3],[Tien_Nt3],[Tien3],[Tien_Nt4],[Tien4]\n" +
                        ",[Thue_Ttdb],[Tien_Nt5],[Tien5],[Tien_Nt6],[Tien6],[He_So9],[So_Luong9],[Tien_Nt9],[Tien9],[EnvTaxLevel],[Tien_Nt10]\n" +
                        ",[Tien10],[Han_Tt],[Chiet_Khau],[DiscountForeignCurrAmount],[DiscountAmount],[RequestedGiftQuantity],[DeliveredGiftQuantity]\n" +
                        ",[Gia_Tb_Tt],[Ngay_Ct0],[Ten_Vt],[Ngay_Dung],[Ngay_Pb],[So_Lan_Pb],[Ky_Pb],[Ngay_Hong],[Ngay_Thanh_Ly],[Ngay_Thoi_Pb]\n" +
                        ",[PTrChietKhau],[Khuyen_Mai],[He_So_In],[Dvt_In],[So_Luong_In],[Stt_Ref],[RowId_Ref],[Ngay_KN],[CreatedBy],[ModifiedBy]\n" +
                        ",[Khuyen_Mai_LMH],[Phan_Loai_CP],[Gia_Tri_Code_TL],[Gia_Tri_Code_TS],[Gia_Usd],[Ty_Gia_Usd],[Thanh_Tien_Usd])\n" +
                        "VALUES('"+ dvcs +"','"+ dvcs1 +"','" + Stt + "','','" + builinorder + "','HD','" + ngayct + "','X131CN','"+ tk_no +"','"+ tk_co +"','1311','"+ tk_co2 +"','" + madt + "'\n" +
                        ",'"+ ma_km +"','" + maktcn + "','" + manvgs + "','0','" + matdv + "','" + manvgh + "','" + mavung + "','"+ makho +"','" + mavt + "',N'" + don_vi_tinh + "'\n" +
                        ",'0','" + soluong + "','" + giagoc1 + "','" + giagoc1 + "',0,0,'" + gia + "','" + gia + "',0,0,'" + thanhtien + "','" + thanhtien + "'\n" +
                        ",'" + ma_thue + "','" + thuegtgt + "','" + masothue + "',N'" + tendt + "','1311','33311','" + thuedong + "','" + thuedong + "',0,0,0,0,0,0,0,'1'\n" +
                        ",'" + soluong + "','" + thanhtien + "','" + thanhtien + "',0,0,0,0,0,0,0,0,0,'"+ gia_Tbtt +"',NULL,'',NULL,NULL,'','',NULL,NULL,NULL,'" + chietkhau + "'\n" +
                        ",'"+ khuyenmai +"','1',N'" + don_vi_tinh + "','" + soluong + "','','','','" + IdUser + "','" + IdUser + "','"+ khuyenmaiLMH +"','"+ Phan_Loai_CP +"','"+ giatricodeTL +"',0,0,0,0)";
                //trên đây là câu truy vấn
                Statement stmt1 = con.createStatement(); //blah blah blah
                //resultSet = stmt1.executeQuery(query1); //thực thi và trả về một cục ResultSet, nó là gì thì Google, tui chịu
                PreparedStatement pstmt1 = con.prepareStatement(query1);
                int result1 = pstmt1.executeUpdate();

            }

            delete();

            con.close();

        }
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

            String query = "SELECT MAX(Id),(MAX(Id)+1) AS IdM FROM B30Ct WHERE Ma_Dvcs='"+ dvcs +"'";
            //trên đây là câu truy vấn
            Statement stmt = con.createStatement(); //blah blah blah
            resultSet = stmt.executeQuery(query); //thực thi và trả về một cục ResultSet, nó là gì thì Google, tui chịu

            for(int i = 0; i<=resultSet.getRow();i++ )
            {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    Id = resultSet.getString("IdM");
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


    public void taorowid() throws SQLException
    {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            String query = "SELECT MAX(Id),(MAX(Id)+1) AS IdCt FROM B30CtVt WHERE Ma_Dvcs='"+ dvcs+"'";
            //trên đây là câu truy vấn
            Statement stmt = con.createStatement(); //blah blah blah
            resultSet = stmt.executeQuery(query); //thực thi và trả về một cục ResultSet, nó là gì thì Google, tui chịu

            for(int i = 0; i<=resultSet.getRow();i++ )
            {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    IdCt = resultSet.getString("IdCt");
                    if(IdCt.length()==7)
                    {
                        Rowid=dvcs+"0000"+IdCt+"HD";
                    }
                    if(IdCt.length()==8)
                    {
                        Rowid=dvcs+"000"+IdCt+"HD";
                    }
                    if(IdCt.length()==9)
                    {
                        Rowid=dvcs+"00"+IdCt+"HD";
                    }
                    if(IdCt.length()==10)
                    {
                        Rowid=dvcs+"0"+IdCt+"HD";
                    }
                    if(IdCt.length()==11)
                    {
                        Rowid=dvcs+IdCt+"HD";
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
            soseri="CM/20E";
        }
        if(dvcs.equals("A03"))
        {
            dvcs1="2N301";
            makho="TP2N3-01";
            soseri="CT/20E";

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
            soseri="MD/20E";
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