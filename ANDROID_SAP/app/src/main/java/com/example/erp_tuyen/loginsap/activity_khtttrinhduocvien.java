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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class activity_khtttrinhduocvien extends AppCompatActivity {

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
    private ArrayList<String> datagtopcbd = new ArrayList<String>();   // giảm trừ cồn 500ML
    private ArrayList<String> datatgtvopc = new ArrayList<String>();   // Tăng giảm tiền về OPC
    private ArrayList<String> datatgtvopcbd = new ArrayList<String>();
    private ArrayList<String> datatgtvhd2 = new ArrayList<String>();


    private ArrayList<String> datatienvetinhthuongttdv = new ArrayList<String>();
    private ArrayList<String> datadoanhthuspmtdv = new ArrayList<String>();
    private ArrayList<String> datadoanhthuspmttdv = new ArrayList<String>();
    private ArrayList<String> datatylethuongtheomuctdv = new ArrayList<String>();
    private ArrayList<String> datatylethuongspmtdv = new ArrayList<String>();
    private ArrayList<String> datatylethuongspmttdv = new ArrayList<String>();

    private ArrayList<String> datatylethuongtctdv = new ArrayList<String>();
    private ArrayList<String> datagiatritrathuongtdv = new ArrayList<String>();
    private ArrayList<String> datagiatriqtttdv = new ArrayList<String>();
    private ArrayList<String> datasoluongqtttdv = new ArrayList<String>();
    private ArrayList<String> datathongtincskhhopletdv = new ArrayList<String>();
    private ArrayList<String> datatylethuongtheomuctktdv = new ArrayList<String>();
    private ArrayList<String> datatylethuongspmtktdv = new ArrayList<String>();
    private ArrayList<String> datatylethuongspmttktdv = new ArrayList<String>();
    private ArrayList<String> datatylethuongtctktdv = new ArrayList<String>();
    private ArrayList<String> datagiatritrathuongtktdv = new ArrayList<String>();
    private ArrayList<String> datagiatriqtttktdv = new ArrayList<String>();
    private ArrayList<String> datasoluongqtttktdv = new ArrayList<String>();


    private ArrayList<String> data4 = new ArrayList<String>();
    private TableLayout table;
    private TableRow row;
    ResultSet resultSet;

    TextView tvdoituongkhtttdv, tvloaikhddkhtttdv, tvloaikhtdkhtttdv, tvtinhtpkhtttdv, tvtrinhduocvienkhtttdv, tvdoitackhtttdv, tvsinhnhatkhtttdv, tvgioitinhkhtttdv, tvdidongkhtttdv, tvhopdongkhtttdv, tvopckhtttdv, tvopcbdkhtttdv, tvhd2khtttdv, tvtvtinhthuongkhtttdv, tvdtspmkhtttdv, tvdtspmtkhtttdv, tvtlttheomuckhtttdv, tvtltspmkhtttdv, tvtltspmtkhtttdv, tvtlttckhtttdv, tvgtttkhtttdv, tvgtqttkhtttdv, tvslqttkhtttdv, tvttcskhhoplekhtttdv, tvtlttheomuctkkhtttdv, tvtltspmtkkhtttdv, tvtltspmttkkhtttdv, tvtlttongcongtkkhtttdv, tvgttttkkhtttdv, tvgtqtttkkhtttdv, tvsoluongqtttkkhtttdv;
    EditText edtnkhtttdv, eddnkhtttdv;
    Button buttonbckhtttdv;
    int flag = 0;

    String dvcs,dvcs1,makho, macbnv, chucdanh, username;

    private ProgressDialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khtttrinhduocvien);
        getSupportActionBar().setTitle("Khách Hàng Thân Thiết OPC");
        Intent intent = getIntent();
        dvcs = intent.getStringExtra("Ma_Dvcs");
        macbnv = intent.getStringExtra("Ma_CbNv");
        chucdanh = intent.getStringExtra("Chuc_Danh");
        username = intent.getStringExtra("USERNAME");

        anhxa();

        String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        edtnkhtttdv.setText(date_n);
        eddnkhtttdv.setText(date_n);

        edtnkhtttdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                chonngay();
            }
        });

        eddnkhtttdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
                chonngay();
            }
        });

        buttonbckhtttdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingBar=new ProgressDialog(activity_khtttrinhduocvien.this);
                LoadingBar.setTitle("BRAVO - ANDROID");
                LoadingBar.setMessage("Đang tải dữ liệu.... usp_Vth_BaoCaoTienVeTheo_Dt_Mar");
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

    private void anhxa() {

        table=(TableLayout) findViewById(R.id.tbkhtttdv);
        tvdoituongkhtttdv = (TextView) findViewById(R.id.tvdtkhtttdv);
        tvloaikhddkhtttdv = (TextView) findViewById(R.id.tvlkhddkhtttdv);
        tvloaikhtdkhtttdv = (TextView) findViewById(R.id.tvlkhtdkhtttdv);
        tvtinhtpkhtttdv = (TextView) findViewById(R.id.tvtinhtpkhtttdv);
        tvtrinhduocvienkhtttdv = (TextView) findViewById(R.id.tvtdvkhtttdv);
        tvdoitackhtttdv = (TextView) findViewById(R.id.tvdoitackhtttdv);
        tvsinhnhatkhtttdv = (TextView) findViewById(R.id.tvsinhnhatkhtttdv);
        tvgioitinhkhtttdv = (TextView) findViewById(R.id.tvgioitinhkhtttdv);
        tvdidongkhtttdv = (TextView) findViewById(R.id.tvdidongkhtttdv);
        tvhopdongkhtttdv = (TextView) findViewById(R.id.tvhopdongkhtttdv);
        tvopckhtttdv = (TextView) findViewById(R.id.tvopckhtttdv);
        tvopcbdkhtttdv = (TextView) findViewById(R.id.tvopcbdkhtttdv);
        tvhd2khtttdv = (TextView) findViewById(R.id.tvhoaduockhtttdv);

        tvtvtinhthuongkhtttdv = (TextView) findViewById(R.id.tvtienvettkhtttdv);
        tvdtspmkhtttdv = (TextView) findViewById(R.id.tvdtspmkhtttdv);
        tvdtspmtkhtttdv = (TextView) findViewById(R.id.tvdtspmtkhtttdv);
        tvtlttheomuckhtttdv = (TextView) findViewById(R.id.tvtlttheomuckhtttdv);
        tvtltspmkhtttdv = (TextView) findViewById(R.id.tvtltspmtkkhtttdv);
        tvtltspmtkhtttdv = (TextView) findViewById(R.id.tvtlttheospmkhtttdv);

        tvtlttckhtttdv = (TextView) findViewById(R.id.tvtlttongcongkhtttdv);
        tvgtttkhtttdv = (TextView) findViewById(R.id.tvgtttkhtttdv);
        tvgtqttkhtttdv = (TextView) findViewById(R.id.tvgtqtttkkhtttdv);
        tvslqttkhtttdv = (TextView) findViewById(R.id.tvslqtttkkhtttdv);
        tvttcskhhoplekhtttdv = (TextView) findViewById(R.id.tvttcskhhlkhtttdv);
        tvtlttheomuctkkhtttdv = (TextView) findViewById(R.id.tvtlttheomuctkkhtttdv);
        tvtltspmtkkhtttdv = (TextView) findViewById(R.id.tvtltspmtkkhtttdv);
        tvtltspmttkkhtttdv = (TextView) findViewById(R.id.tvtltspmttkkhtttdv);
        tvtlttongcongtkkhtttdv = (TextView) findViewById(R.id.tvtlttctkkhtttdv);

        tvgttttkkhtttdv = (TextView) findViewById(R.id.tvgttttkkhtttdv);
        tvgtqtttkkhtttdv = (TextView) findViewById(R.id.tvgtqtttkkhtttdv);
        tvsoluongqtttkkhtttdv = (TextView) findViewById(R.id.tvsoluongqttkhtttdv);

        edtnkhtttdv = (EditText) findViewById(R.id.editTexttnkhtttdv);
        eddnkhtttdv = (EditText) findViewById(R.id.editTextdnkhtttdv);
        buttonbckhtttdv = (Button) findViewById(R.id.buttonbckhtttdv);


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
                    edtnkhtttdv.setText(simpleDateFormat.format(calendar.getTime()));
                }
                if (flag == 1) {
                    eddnkhtttdv.setText(simpleDateFormat.format(calendar.getTime()));
                }
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void selectsnkhtdv() throws SQLException {


        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {


            String tungay = edtnkhtttdv.getText().toString();
            tungay="2020/12/01";
            String denngay = eddnkhtttdv.getText().toString();
            denngay="2021/03/31";
            taothongtinCN();

            PreparedStatement statement = con.prepareStatement("EXEC usp_Vth_BaoCaoTienVeTheo_Dt_Mar " +
                    "'" + tungay + "' ,'" + denngay + "','"+ dvcs +"','"+ dvcs1 +"','"+ username +"','','',''" +
                    ",'VND','','','','','',0,0,0,'' ");
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {


                    String tendt = resultSet.getString("Ten_Dt");
                    String loaikh = resultSet.getString("Loai_KH");
                    String maloaiKhtd = resultSet.getString("Loai_KH_Tan_Duoc");
                    String tinhtp = resultSet.getString("Ten_Tinh_Thanh");
                    String tentdv = resultSet.getString("Ten_TDV");
                    String tendoitac = resultSet.getString("Ten_Doi_Tac");
                    String ngaysinhnhat = resultSet.getString("Ngay_Sinh_Nhat");
                    if(ngaysinhnhat !=null) {
                        ngaysinhnhat = ngaysinhnhat.substring(0, 10);
                    }
                    String gioitinh = resultSet.getString("Gender");
                    String didong = resultSet.getString("Tel");
                    String hopdong = resultSet.getString("So_Hop_Dong");
                    String tienopc = resultSet.getString("Tien_OPC_Sau_CKTT");
                    String tienopcbd = resultSet.getString("Tien_Con_Sau_CKTT");
                    String tienhd2 = resultSet.getString("Tien_Sui_Sau_CKTT");
                    String tienveconmax500ml=resultSet.getString("Tien_Ve_Con_MAX_500Ml");
                    String tanggiamtvopc=resultSet.getString("Tang_Giam_TV_OPC");
                    String tanggiamtvcon=resultSet.getString("Tang_Giam_TV_Con");
                    String tanggiamtvsui=resultSet.getString("Tang_Giam_TV_Sui");
                    String tienvett=resultSet.getString("Dong_Duoc");
                    String doanhthuspm=resultSet.getString("DoanhThu_SPM");
                    String doanhthuspmt=resultSet.getString("DoanhThu_SPMT");
                    String tlttheomuc=resultSet.getString("Ty_Le_Dong_Duoc");
                    String tltspm=resultSet.getString("TyLeThuong_SPM");
                    String tltspmt=resultSet.getString("TyLeThuong_SPMT");
                    String tlttongcong=resultSet.getString("TyLeThuong_TC");
                    String gttrathuong=resultSet.getString("Thuong_Dong_Duoc");
                    String gttqtt=resultSet.getString("GiaTriThuong_QTT");
                    String sltqtt=resultSet.getString("SoLuongThuong_QTT");
                    String ttcskhhl=resultSet.getString("ThongTin_CSKHHopLe");
                    String tlttheomuctk=resultSet.getString("TyLeThuong_TK");
                    String tltspmtk=resultSet.getString("TyLeThuong_SPM_TK");
                    String tltspmttk=resultSet.getString("TyLeThuong_SPMT_TK");
                    String tlttctk=resultSet.getString("TyLeThuong_TC_TK");
                    String gttttk=resultSet.getString("GiaTriThuong_TK");
                    String gttqtttk=resultSet.getString("GiaTriThuong_QTT_TK");
                    String sltqtttk=resultSet.getString("SoLuongThuong_QTT_TK");

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
                    dataopctdv.add(String.format("%,.0f", Float.valueOf(tienopc)));

                    dataopcbdtdv.add(String.format("%,.0f", Float.valueOf(tienopcbd)));

                    datahd2tdv.add(String.format("%,.0f", Float.valueOf(tienhd2)));
                    datagtopcbd.add(String.format("%,.0f", Float.valueOf(tienveconmax500ml)));
                    datatgtvopc.add(String.format("%,.0f", Float.valueOf(tanggiamtvopc)));
                    datatgtvopcbd.add(String.format("%,.0f", Float.valueOf(tanggiamtvcon)));
                    datatgtvhd2.add(String.format("%,.0f", Float.valueOf(tanggiamtvsui)));

                    datatienvetinhthuongttdv.add(String.format("%,.0f", Float.valueOf(tienvett)));
                    datadoanhthuspmtdv.add(String.format("%,.0f", Float.valueOf(doanhthuspm)));
                    datadoanhthuspmttdv.add(String.format("%,.0f", Float.valueOf(doanhthuspmt)));
                    datatylethuongtheomuctdv.add(String.format("%,.0f", Float.valueOf(tlttheomuc)));
                    datatylethuongspmtdv.add(String.format("%,.0f", Float.valueOf(tltspm)));
                    datatylethuongspmttdv.add(String.format("%,.0f", Float.valueOf(tltspmt)));
                    datatylethuongtctdv.add(String.format("%,.0f", Float.valueOf(tlttongcong)));
                    datagiatritrathuongtdv.add(String.format("%,.0f", Float.valueOf(gttrathuong)));

                    datagiatriqtttdv.add(String.format("%,.0f", Float.valueOf(gttqtt)));
                    datasoluongqtttdv.add(String.format("%,.0f", Float.valueOf(sltqtt)));
                    datathongtincskhhopletdv.add(ttcskhhl);

                    datatylethuongtheomuctktdv.add(String.format("%,.0f", Float.valueOf(tlttheomuctk)));
                    datatylethuongspmtktdv.add(String.format("%,.0f", Float.valueOf(tltspmtk)));
                    datatylethuongspmttktdv.add(String.format("%,.0f", Float.valueOf(tltspmttk)));
                    datatylethuongtctktdv.add(String.format("%,.0f", Float.valueOf(tlttctk)));
                    datagiatritrathuongtktdv.add(String.format("%,.0f", Float.valueOf(gttttk)));
                    datagiatriqtttktdv.add(String.format("%,.0f", Float.valueOf(gttqtttk)));
                    datasoluongqtttktdv.add(String.format("%,.0f", Float.valueOf(sltqtttk)));


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


                    TextView t20 = new TextView(this);

                    TextView t21 = new TextView(this);
                    TextView t22 = new TextView(this);
                    TextView t23 = new TextView(this);
                    TextView t24 = new TextView(this);
                    TextView t25 = new TextView(this);
                    TextView t26 = new TextView(this);
                    TextView t27 = new TextView(this);
                    TextView t28 = new TextView(this);
                    TextView t29 = new TextView(this);
                    TextView t30 = new TextView(this);
                    TextView t31 = new TextView(this);
                    TextView t32 = new TextView(this);
                    TextView t33 = new TextView(this);
                    TextView t34 = new TextView(this);
                    TextView t35 = new TextView(this);



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

                    t20.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t20.setHeight(70);

                    t21.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t21.setHeight(70);

                    t22.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t22.setHeight(70);
                    t23.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t23.setHeight(70);
                    t24.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t24.setHeight(70);

                    t25.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t25.setHeight(70);

                    t26.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t26.setHeight(70);
                    t27.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t27.setHeight(70);
                    t28.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t28.setHeight(70);
                    t29.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t29.setHeight(70);
                    t30.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t30.setHeight(70);
                    t31.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t31.setHeight(70);
                    t32.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t32.setHeight(70);
                    t33.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t33.setHeight(70);
                    t34.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t34.setHeight(70);
                    t35.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    t35.setHeight(70);


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
                        String ptiengtopcbd=datagtopcbd.get(j);
                        String ptgtvopc=datatgtvopc.get(j);
                        String ptgtvopcbd=datatgtvopcbd.get(j);
                        String ptgtvhd2=datatgtvhd2.get(j);
                        String ptvtt=datatienvetinhthuongttdv.get(j);
                        String pdtspm=datadoanhthuspmtdv.get(j);
                        String pdtspmt=datadoanhthuspmttdv.get(j);
                        String ptlttm=datatylethuongtheomuctdv.get(j);
                        String ptltspm=datatylethuongspmtdv.get(j);
                        String ptltspmt=datatylethuongspmttdv.get(j);  // lỗi khúc này
                        String ptlttc=datatylethuongtctdv.get(j);
                        String pgttt=datagiatritrathuongtdv.get(j);
                        String pgtqtt=datagiatriqtttdv.get(j);
                        String pslqtt=datasoluongqtttdv.get(j);
                        String pttcskhhl=datathongtincskhhopletdv.get(j);
                        String ptlttmtk=datatylethuongtheomuctktdv.get(j);
                        String ptltspmtk=datatylethuongspmtktdv.get(j);
                        String ptltspmttk=datatylethuongspmttktdv.get(j);
                        String ptlttctk=datatylethuongtctktdv.get(j);
                        String pgttttk=datagiatritrathuongtktdv.get(j);
                        String pgtqtttk=datagiatriqtttktdv.get(j);
                        String pslqtttk=datasoluongqtttktdv.get(j);



                        t1.setText(pdoituong);

//                        t1.setMaxLines(10);
//                        t1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
//                                TableRow.LayoutParams.WRAP_CONTENT, 1.0f));


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
                        t14.setText(ptiengtopcbd);
                        t15.setText(ptgtvopc);
                        t16.setText(ptgtvopcbd);
                        t17.setText(ptgtvhd2);
                        t18.setText(ptvtt);
                        t19.setText(pdtspm);
                        t20.setText(pdtspmt);
                        t21.setText(ptlttm);
                        t22.setText(ptltspm);
                        t23.setText(ptltspmt);
                        t24.setText(ptlttc);
                        t25.setText(pgttt);
                        t26.setText(pgtqtt);
                        t27.setText(pslqtt);
                        t28.setText(pttcskhhl);
                        t29.setText(ptlttmtk);
                        t30.setText(ptltspmtk);
                        t31.setText(ptltspmttk);
                        t32.setText(ptlttctk);
                        t33.setText(pgttttk);
                        t34.setText(pgtqtttk);
                        t35.setText(pslqtttk);

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

                    row.addView(t20);
                    row.addView(t21);
                    row.addView(t22);
                    row.addView(t23);
                    row.addView(t24);
                    row.addView(t25);

                    row.addView(t26);
                    row.addView(t27);
                    row.addView(t28);

                    row.addView(t29);
                    row.addView(t30);
                    row.addView(t31);
                    row.addView(t32);
                    row.addView(t33);
                    row.addView(t34);
                    row.addView(t35);
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
}