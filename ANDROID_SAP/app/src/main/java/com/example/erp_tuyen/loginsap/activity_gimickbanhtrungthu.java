package com.example.erp_tuyen.loginsap;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class activity_gimickbanhtrungthu extends AppCompatActivity {
    private ArrayList<String> datadoituongtdv = new ArrayList<String>();
    private ArrayList<String> dataloaikhddtdv = new ArrayList<String>();
    private ArrayList<String> dataloaikhtdtdv = new ArrayList<String>();
    private ArrayList<String> datatinhtptdv = new ArrayList<String>();
    private ArrayList<String> datatrinhduocvientdv = new ArrayList<String>();
    private ArrayList<String> datadoitactdv = new ArrayList<String>();
    private ArrayList<String> datasinhnhattdv = new ArrayList<String>();
    private ArrayList<String> datagioitinhtdv = new ArrayList<String>();
    private ArrayList<String> datadidongtdv = new ArrayList<String>();
    private ArrayList<String> datahopdongtdv = new ArrayList<String>();

    private ArrayList<String> dataopctdv = new ArrayList<String>();  //opc
    private ArrayList<String> dataopcbdtdv = new ArrayList<String>(); // opcbd
    private ArrayList<String> datahd2tdv = new ArrayList<String>(); // hd2

    private ArrayList<String> dataphientdv = new ArrayList<String>(); // phiến
    private ArrayList<String> datadttttdv = new ArrayList<String>();
    private ArrayList<String> datathuongtdv = new ArrayList<String>();

    private ArrayList<String> datacskhhltdv = new ArrayList<String>();
    private ArrayList<String> datathuongtktdv = new ArrayList<String>();
    private ArrayList<String> datagsbhtdv = new ArrayList<String>();


    ArrayList<String> arraymadt = new ArrayList<String>();

    private ArrayList<String> data4 = new ArrayList<String>();
    private TableLayout table;
    private TableRow row;
    ResultSet resultSet;
    HorizontalScrollView horizontalScrollView;

    EditText edtngmbtttdv, eddngmbtttdv;
    Button buttonbcgmbtttdv;
    TableRow tbrgmbtt;
    int flag = 0;

    String dvcs,dvcs1,makho, macbnv, chucdanh, username;

    AutoCompleteTextView atckhgmbtttdv;
    ImageView imggmbtttdv;
    String madt,tendt,makh;

    private ProgressDialog LoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gimickbanhtrungthu);

        getSupportActionBar().setTitle("GIMICK BÁNH TRUNG THU");
        Intent intent = getIntent();
        dvcs = intent.getStringExtra("Ma_Dvcs");
        macbnv = intent.getStringExtra("Ma_CbNv");
        chucdanh = intent.getStringExtra("Chuc_Danh");
        username = intent.getStringExtra("USERNAME");


        anhxa();

        String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        edtngmbtttdv.setText(date_n);
        eddngmbtttdv.setText(date_n);

        edtngmbtttdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                chonngay();
            }
        });

        eddngmbtttdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
                chonngay();
            }
        });

        try {
            selectdoituong();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraymadt);
        atckhgmbtttdv.setAdapter(adapter1);

        imggmbtttdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atckhgmbtttdv.showDropDown();
            }
        });

        atckhgmbtttdv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                madt = atckhgmbtttdv.getText().toString();

                try {
                    selectdoituong();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });


        buttonbcgmbtttdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingBar=new ProgressDialog(activity_gimickbanhtrungthu.this);
                LoadingBar.setTitle("OPC-MOBILE");
                LoadingBar.setMessage("Đang tải dữ liệu.... usp_Vth_TangQuaGimick");
                LoadingBar.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            table.removeViews(1, Math.max(0, table.getChildCount() - 1));
                            selectgimickaomua();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        LoadingBar.dismiss();
                    }
                },5000);
            }
        });


    }

    private void anhxa() {

        table=(TableLayout) findViewById(R.id.tbgmbtttdv);

        edtngmbtttdv = (EditText) findViewById(R.id.editTexttngmbtttdv);
        eddngmbtttdv = (EditText) findViewById(R.id.editTextdngmbtttdv);
        buttonbcgmbtttdv = (Button) findViewById(R.id.buttonbcgmbtttdv);
        atckhgmbtttdv=(AutoCompleteTextView) findViewById(R.id.actkhgmbtttdv);
        imggmbtttdv=(ImageView) findViewById(R.id.imagegmbtttdv);

        tbrgmbtt=(TableRow) findViewById(R.id.tbrowgmbtttdv);



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
                if (flag == 0) {
                    edtngmbtttdv.setText(simpleDateFormat.format(calendar.getTime()));
                }
                if (flag == 1) {
                    eddngmbtttdv.setText(simpleDateFormat.format(calendar.getTime()));
                }
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }


    private void selectgimickaomua() throws SQLException {


        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {


            String tungay = edtngmbtttdv.getText().toString();
            tungay="2021/01/01";
            String denngay = eddngmbtttdv.getText().toString();
            denngay="2021/06/30";
            taothongtinCN();

            makh = atckhgmbtttdv.getText().toString();
            if (makh.equals("")) {
                makh = "";

            } else {
                makh = atckhgmbtttdv.getText().toString().substring(0, 13);
            }

            PreparedStatement statement = con.prepareStatement("EXEC usp_Vth_TangQuaGimick " +
                    "'" + tungay + "' ,'" + denngay + "','','','','','"+ makh +"','','','','','HD','111','112','',0" +
                    ",'"+ dvcs +"','"+ username +"','VND',0,0,0,0,0,0,0,0,1,0,0,0,'' ");
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {


                    String tendt = resultSet.getString("Ten_Dt");
                    String loaikh = resultSet.getString("Ma_LKH");
                    String maloaiKhtd = resultSet.getString("Ma_LKH_TanDuoc");
                    String tinhtp = resultSet.getString("Ten_Tinh_Thanh");
                    String tentdv = resultSet.getString("Ten_TDV");
                    String tendoitac = resultSet.getString("Ten_Doi_Tac");
                    String ngaysinhnhat = resultSet.getString("Ngay_Sinh");
                    if(ngaysinhnhat !=null) {
                        ngaysinhnhat = ngaysinhnhat.substring(0, 10);
                    }
                    String gioitinh = resultSet.getString("GenDer");
                    String didong = resultSet.getString("Di_Dong");
                    String hopdong = resultSet.getString("So_Hop_Dong");


                    String doanhthuopc=resultSet.getString("Doanh_Thu_OPC");
                    String doanhthucon=resultSet.getString("Doanh_Thu_Con");
                    String doanhthusui=resultSet.getString("Doanh_Thu_Sui");
                    String doanhthuphien=resultSet.getString("Doanh_Thu_Phien");
                    String doanhthutinhthuong=resultSet.getString("T1_T6");
                    String thuongquatet=resultSet.getString("Thuong_BanhTT_T1_T6");
                    String cskhhople=resultSet.getString("CSKH_HopLe");
                    String thuongquatettk=resultSet.getString("Thuong_BanhTrungThu_TK");
                    String tennvgs=resultSet.getString("Ten_NVGS");


                    datadoituongtdv.add(tendt);
                    dataloaikhddtdv.add(loaikh);
                    dataloaikhtdtdv.add(maloaiKhtd);
                    datatinhtptdv.add(tinhtp);
                    datatrinhduocvientdv.add(tentdv);
                    datadoitactdv.add(tendoitac);
                    datasinhnhattdv.add(ngaysinhnhat);
                    datagioitinhtdv.add(gioitinh);
                    datadidongtdv.add(didong);
                    datahopdongtdv.add(hopdong);


                    dataopctdv.add(String.format("%,.0f", Float.valueOf(doanhthuopc)));

                    dataopcbdtdv.add(String.format("%,.0f", Float.valueOf(doanhthucon)));

                    datahd2tdv.add(String.format("%,.0f", Float.valueOf(doanhthusui)));
                    dataphientdv.add(String.format("%,.0f", Float.valueOf(doanhthuphien)));
                    datadttttdv.add(String.format("%,.0f", Float.valueOf(doanhthutinhthuong)));
                    datathuongtdv.add(String.format("%,.0f", Float.valueOf(thuongquatet)));
                    datacskhhltdv.add(cskhhople);
                    datathuongtktdv.add(String.format("%,.0f", Float.valueOf(thuongquatettk)));
                    datagsbhtdv.add(tennvgs);


                    row = new TableRow(this);
                    TextView t1 = new TextView(this);

                    TextView t2 = new TextView(this);
                    TextView t3 = new TextView(this);
                    TextView t4 = new TextView(this);
                    TextView t5 = new TextView(this);
                    TextView t6 = new TextView(this);
                    TextView t7 = new TextView(this);
                    TextView t8 = new TextView(this);
                    TextView t9 = new TextView(this);
                    TextView t10 = new TextView(this);
                    TextView t11 = new TextView(this);
                    TextView t12 = new TextView(this);
                    TextView t13 = new TextView(this);
                    TextView t14 = new TextView(this);
                    TextView t15 = new TextView(this);
                    TextView t16 = new TextView(this);
                    TextView t17 = new TextView(this);
                    TextView t18 = new TextView(this);
                    TextView t19 = new TextView(this);






                    t1.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t1.setWidth(300);
                    t1.setHeight(70);
                    t2.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t2.setHeight(70);
                    t3.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t3.setHeight(70);
                    t4.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t4.setHeight(70);
                    t5.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t5.setHeight(70);
                    t6.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t6.setWidth(210);
                    t6.setHeight(70);
                    t7.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t7.setHeight(70);
                    t8.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t8.setHeight(70);

                    t9.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t9.setHeight(70);
                    t10.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t10.setHeight(70);
                    t11.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t11.setHeight(70);
                    t12.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t12.setHeight(70);

                    t13.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t13.setHeight(70);
                    t14.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t14.setHeight(70);
                    t15.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t15.setHeight(70);
                    t16.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t16.setHeight(70);
                    t17.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t17.setHeight(70);
                    t18.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t18.setHeight(70);
                    t19.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t19.setHeight(70);





                    for (int j = 0; j < datadoituongtdv.size(); j++) {
                        String pdoituong = datadoituongtdv.get(j);
                        String ploaikhdd = dataloaikhddtdv.get(j);
                        String ploaikhtd = dataloaikhtdtdv.get(j);
                        String ptinhtp = datatinhtptdv.get(j);
                        String ptrinhduocvien = datatrinhduocvientdv.get(j);
                        String pdoitac = datadoitactdv.get(j);
                        String psinhnhat = datasinhnhattdv.get(j);
                        String pgioitinh = datagioitinhtdv.get(j);
                        String pdidong = datadidongtdv.get(j);
                        String phopdong = datahopdongtdv.get(j);


                        String pdtopc=dataopctdv.get(j);
                        String pdtcon=dataopcbdtdv.get(j);
                        String pdtsui=datahd2tdv.get(j);
                        String pdtphien=dataphientdv.get(j);
                        String pdttt=datadttttdv.get(j);
                        String pthuongaomua=datathuongtdv.get(j);
                        String pcskhhl=datacskhhltdv.get(j);
                        String pthuongaomuatk=datathuongtktdv.get(j);
                        String pnvgs=datagsbhtdv.get(j);




                        t1.setText(pdoituong);

                        t2.setText(ploaikhdd);
                        t3.setText(ploaikhtd);
                        t4.setText(ptinhtp);

                        t5.setText(ptrinhduocvien);
                        t6.setText(pdoitac);
                        t7.setText(psinhnhat);
                        t8.setText(pgioitinh);
                        t9.setText(pdidong);
                        t10.setText(phopdong);

                        t11.setText(pdtopc);
                        t12.setText(pdtcon);
                        t13.setText(pdtsui);
                        t14.setText(pdtphien);
                        t15.setText(pdttt);
                        t16.setText(pthuongaomua);
                        t17.setText(pcskhhl);
                        t18.setText(pthuongaomuatk);
                        t19.setText(pnvgs);


                    }
                    row.setBackground(ContextCompat.getDrawable(this, R.drawable.border));

                    row.addView(t1);
                    row.addView(t2);
                    row.addView(t3);
                    row.addView(t4);

                    row.addView(t5);
                    row.addView(t6);
                    row.addView(t7);
                    row.addView(t8);

                    row.addView(t9);
                    row.addView(t10);
                    row.addView(t11);
                    row.addView(t12);

                    row.addView(t13);
                    row.addView(t14);
                    row.addView(t15);
                    row.addView(t16);

                    row.addView(t17);
                    row.addView(t18);
                    row.addView(t19);



                    table.addView(row);


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

        }
        if(dvcs.equals("A03"))
        {
            dvcs1="2N301";
            makho="TP2N3-01";


        }

        if(dvcs.equals("A04"))
        {
            dvcs1="2N302";
            makho="TP2N3-02";

        }
        if(dvcs.equals("A05"))
        {
            dvcs1="2N201";
            makho="TP2N2-01";

        }
        if(dvcs.equals("A06"))
        {
            dvcs1="2N202";
            makho="TP2N2-02";

        }
        if(dvcs.equals("A07"))
        {
            dvcs1="2T101";
            makho="TP2T1-01";

        }
        if(dvcs.equals("A08"))
        {
            dvcs1="2T102";
            makho="TP2T1-02";

        }
        if(dvcs.equals("A09"))
        {
            dvcs1="2T103";
            makho="TP2T1-03";

        }
        if(dvcs.equals("A10"))
        {
            dvcs1="2B101";
            makho="TP2B1-01";

        }
    }

    public void selectdoituong() throws SQLException {
        Connection con = SERVER.Connect();
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            PreparedStatement statement = con.prepareStatement("EXEC usp_LookupKH_Android '"+ dvcs +"','"+ username +"','"+ macbnv +"' " );
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    madt = resultSet.getString("Ma_Dt");
                    tendt = resultSet.getString("Ten_Dt");
                    arraymadt.add(madt + ' ' + tendt);

                }
            }

            con.close();
        }
    }
}