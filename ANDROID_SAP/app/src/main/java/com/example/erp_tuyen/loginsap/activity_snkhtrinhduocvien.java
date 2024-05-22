package com.example.erp_tuyen.loginsap;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class activity_snkhtrinhduocvien extends AppCompatActivity {

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
    private ArrayList<String> dataopctdv = new ArrayList<String>();
    private ArrayList<String> dataopcbdtdv = new ArrayList<String>();
    private ArrayList<String> datahd2tdv = new ArrayList<String>();
    private ArrayList<String> datadoanhthutgcttdv = new ArrayList<String>();
    private ArrayList<String> datagiatriquatangsnkhtdv = new ArrayList<String>();
    private ArrayList<String> datagiatritoidaudtdv = new ArrayList<String>();
    private ArrayList<String> datathongtincskhhltdv = new ArrayList<String>();
    private ArrayList<String> datagiatriquatangtktdv = new ArrayList<String>();
    private ArrayList<String> datagiatritoidaudtktdv = new ArrayList<String>();

    private ArrayList<String> data4 = new ArrayList<String>();
    private TableLayout table;
    private TableRow row;
    ResultSet resultSet;
    TextView tvdoituongsnkhtdv,tvloaikhddsnkhtdv,tvloaikhtdsnkhtdv,tvtinhtpsnkhtdv,tvtrinhduocviensnkhtdv
            ,tvdoitacsnkhtdv,tvsinhnhatsnkhtdv,tvgioitinhsnkhtdv,tvdidongsnkhtdv,tvhopdongsnkhtdv,tvopcsnkhtdv
            ,tvopcbdsnkhtdv,tvhd2snkhtdv,tvdtthamgiactsnkhtdv,tvgtquatangsnkhtdv,tvgttoidadhudsnkhtdv
            ,tvthongtincskhsnkhtdv,tvgtquatangsnkhtktdv,tvgttoidadhudsnkhtktdv;
    EditText edtnsnkhtdv,eddnsnkhtdv;
    Button buttonbcsnkhtdv;
    PieChart pieChartsnkhtdv;
    ArrayList<PieEntry> visitors=new ArrayList<>();
    int flag=0;

    String dvcs,macbnv,chucdanh,username;

    private ProgressDialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snkhtrinhduocvien);
        getSupportActionBar().setTitle("Sinh nhật khách hàng");
        Intent intent=getIntent();
        dvcs=intent.getStringExtra("Ma_Dvcs");
        macbnv=intent.getStringExtra("Ma_CbNv");
        chucdanh=intent.getStringExtra("Chuc_Danh");
        username=intent.getStringExtra("USERNAME");
        anhxa();

        String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        edtnsnkhtdv.setText(date_n);
        eddnsnkhtdv.setText(date_n);

        edtnsnkhtdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                chonngay();
            }
        });
        eddnsnkhtdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
                chonngay();
            }
        });

        buttonbcsnkhtdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingBar=new ProgressDialog(activity_snkhtrinhduocvien.this);
                LoadingBar.setTitle("BRAVO - ANDROID");
                LoadingBar.setMessage("Đang tải dữ liệu.... usp_Vth_SinhNhatKhachHang_Android");
                LoadingBar.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            table.removeViews(1, Math.max(0, table.getChildCount() - 1));
                            selectsnkhtdv();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        LoadingBar.dismiss();
                    }
                },5000);
            }
        });
    }
    private void anhxa()
    {
        table=(TableLayout) findViewById(R.id.tbsnkhtdv);

        tvdoituongsnkhtdv=(TextView) findViewById(R.id.tvdtsnkhtdv);
        tvloaikhddsnkhtdv=(TextView) findViewById(R.id.tvlkhddsnkhtdv);
        tvloaikhtdsnkhtdv=(TextView) findViewById(R.id.tvlkhtdsnkhtdv);
        tvtinhtpsnkhtdv=(TextView) findViewById(R.id.tvtinhtpsnkhtdv);
        tvtrinhduocviensnkhtdv=(TextView) findViewById(R.id.tvtdvsnkhtdv);
        tvdoitacsnkhtdv=(TextView) findViewById(R.id.tvdoitacsnkhtdv);
        tvsinhnhatsnkhtdv=(TextView) findViewById(R.id.tvsinhnhatsnkhtdv);
        tvgioitinhsnkhtdv=(TextView) findViewById(R.id.tvgioitinhsnkhtdv);
        tvdidongsnkhtdv=(TextView) findViewById(R.id.tvdidongsnkhtdv);
        tvhopdongsnkhtdv=(TextView) findViewById(R.id.tvhopdongsnkhtdv);
        tvopcsnkhtdv=(TextView) findViewById(R.id.tvopcsnkhtdv);
        tvopcbdsnkhtdv=(TextView) findViewById(R.id.tvopcbdsnkhtdv);
        tvhd2snkhtdv=(TextView) findViewById(R.id.tvhoaduocsnkhtdv);
        tvdtthamgiactsnkhtdv=(TextView) findViewById(R.id.tvdtthamgiactsnkhtdv);
        tvgtquatangsnkhtdv=(TextView) findViewById(R.id.tvgtquatangsnkhtdv);
        tvgttoidadhudsnkhtdv=(TextView) findViewById(R.id.tvgttoidasnkhtdv);
        tvthongtincskhsnkhtdv=(TextView) findViewById(R.id.tvthongtincskhsnkhtdv);
        tvgtquatangsnkhtktdv=(TextView) findViewById(R.id.tvgtquatangtksnkhtdv);
        tvgttoidadhudsnkhtktdv=(TextView) findViewById(R.id.tvgttoidatksnkhtdv);
        edtnsnkhtdv=(EditText) findViewById(R.id.editTexttnsnkhtdv);
        eddnsnkhtdv=(EditText) findViewById(R.id.editTextdnsnkhtdv);
        buttonbcsnkhtdv=(Button) findViewById(R.id.buttonbcsnkhtdv);


    }

    private  void chonngay()
    {
        final Calendar calendar=Calendar.getInstance();
        int ngay=calendar.get(calendar.DATE);
        int thang=calendar.get(calendar.MONTH);
        int nam= calendar.get(calendar.YEAR);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                if(flag==0) {
                    edtnsnkhtdv.setText(simpleDateFormat.format(calendar.getTime()));
                }
                if(flag==1)
                {
                    eddnsnkhtdv.setText(simpleDateFormat.format(calendar.getTime()));
                }
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }

    private void selectsnkhtdv() throws SQLException {



        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {


            String tungay=edtnsnkhtdv.getText().toString();
            String denngay=eddnsnkhtdv.getText().toString();

            PreparedStatement statement = con.prepareStatement("EXEC usp_Vth_SinhNhatKhachHang_Android " +
                    "'"+ tungay +"' ,'"+ denngay +"','','','','','',''" +
                    ",'','','','HD','111','112','',0,'"+ dvcs +"' ,'"+ username +"','','' " );
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {


                    String tendt=resultSet.getString("Ten_Dt");
                    String loaikh = resultSet.getString("Loai_KH");
                    String maloaiKhtd = resultSet.getString("Ma_LKH_TanDuoc");
                    String tinhtp = resultSet.getString("Ten_Tinh_Thanh");
                    String tentdv = resultSet.getString("Ten_TDV");
                    String tendoitac = resultSet.getString("Ten_Doi_Tac");
                    String ngaysinhnhat = resultSet.getString("Ngay_Sinh_Nhat");
                    ngaysinhnhat=ngaysinhnhat.substring(0,10);
                    String gioitinh = resultSet.getString("Gender");
                    String didong = resultSet.getString("Tel");
                    String hopdong = resultSet.getString("So_Hop_Dong");
                    String tienopc = resultSet.getString("Tien_OPC");
                    String tienopcbd = resultSet.getString("Tien_Con");
                    String tienhd2 = resultSet.getString("Tien_Sui");
                    String doanhthutgct = resultSet.getString("Tong_Tien");
                    String gtquatangsnkh=resultSet.getString("Thuong_PMH");
                    String gttoidadhudsnkh=resultSet.getString("Thuong_DHOPC");
                    String thongtincskhsnkh=resultSet.getString("CSKH_HopLe");

                    String gtquatangsnkhtk=resultSet.getString("Thuong_DHOPC_TK");
                    String gttoidadhudsnkhtk=resultSet.getString("Thuong_DHOPC");


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
                    dataopctdv.add(String.format("%,.0f",Float.valueOf(tienopc)));

                    dataopcbdtdv.add(String.format("%,.0f",Float.valueOf(tienopcbd)));

                    datahd2tdv.add(String.format("%,.0f",Float.valueOf(tienhd2)));
                    datadoanhthutgcttdv.add(String.format("%,.0f",Float.valueOf(doanhthutgct)));
                    datagiatriquatangsnkhtdv.add(String.format("%,.0f",Float.valueOf(gtquatangsnkh)));
                    datagiatritoidaudtdv.add(String.format("%,.0f",Float.valueOf(gttoidadhudsnkh)));
                    datathongtincskhhltdv.add(thongtincskhsnkh);
                    datagiatriquatangtktdv.add(String.format("%,.0f",Float.valueOf(gtquatangsnkhtk)));
                    datagiatritoidaudtktdv.add(String.format("%,.0f",Float.valueOf(gttoidadhudsnkhtk)));


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


                    t1.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t1.setWidth(300);
                    t1.setHeight(70);



                    t2.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t2.setHeight(70);
                    t3.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t3.setHeight(70);
                    t4.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t4.setHeight(70);
                    t5.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t5.setHeight(70);
                    t6.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t6.setWidth(210);
                    t6.setHeight(70);
                    t7.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t7.setHeight(70);
                    t8.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t8.setHeight(70);

                    t9.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t9.setHeight(70);
                    t10.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t10.setHeight(70);
                    t11.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t11.setHeight(70);
                    t12.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t12.setHeight(70);

                    t13.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t13.setHeight(70);
                    t14.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t14.setHeight(70);
                    t15.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t15.setHeight(70);
                    t16.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t16.setHeight(70);
                    t17.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t17.setHeight(70);
                    t18.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t18.setHeight(70);
                    t19.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
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
                        String ptienopc = dataopctdv.get(j);
                        String ptienopcbd = dataopcbdtdv.get(j);
                        String ptienhd2 = datahd2tdv.get(j);
                        String pdoanhthutgct = datadoanhthutgcttdv.get(j);
                        String pgtquatang = datagiatriquatangsnkhtdv.get(j);
                        String pgttoida = datagiatritoidaudtdv.get(j);
                        String pthongtinhople = datathongtincskhhltdv.get(j);
                        String pgtquatangtk=datagiatriquatangtktdv.get(j);
                        String pgttoidatk=datagiatritoidaudtktdv.get(j);


                        t1.setText(pdoituong);

                        t1.setMaxLines(10);
                        t1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                                TableRow.LayoutParams.WRAP_CONTENT,1.0f));


                        t2.setText(ploaikhdd);
                        t3.setText(ploaikhtd);
                        t4.setText(ptinhtp);

                        t5.setText(ptrinhduocvien);
                        t6.setText(pdoitac);
                        t7.setText(psinhnhat);
                        t8.setText(pgioitinh);
                        t9.setText(pdidong);
                        t10.setText(phopdong);
                        t11.setText(ptienopc);
                        t12.setText(ptienopcbd);
                        t13.setText(ptienhd2);
                        t14.setText(pdoanhthutgct);
                        t15.setText(pgtquatang);
                        t16.setText(pgttoida);
                        t17.setText(pthongtinhople);
                        t18.setText(pgtquatangtk);
                        t19.setText(pgttoidatk);
                    }
                    row.setBackground(ContextCompat.getDrawable(this,R.drawable.border));

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
}