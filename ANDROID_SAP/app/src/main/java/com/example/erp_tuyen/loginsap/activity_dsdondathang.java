package com.example.erp_tuyen.loginsap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class activity_dsdondathang extends AppCompatActivity {
    ListView lvdsdondathang,lvctddhtdv;

    ArrayList<DsDonDatHang> arrayDsDonDatHang;
    DsDonDatHangAdapter adapter;

    ArrayList<DsChiTietDonDatHang> arrayCtDsDonDatHang;
    DsChiTietDonDatHangAdapter adapterct;


    private MainActivity context;
    ResultSet resultSet;
    EditText edtimkiem;
    String IdUser,macbnv,dvcs;
    String soct;
    String mavtdialog,tenvtdialog,kmdialog,ctkmdialog,gtcdialog,soluongdialog="0",giadialog="0",ttdialog="0";
    int dem=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsdondathang);
        getSupportActionBar().setTitle("Danh Sách Đơn Hàng");
        lvdsdondathang=(ListView) findViewById(R.id.listviewDsDonHang);

        Intent intent=getIntent();
        IdUser=intent.getStringExtra("IdUser");
        macbnv=intent.getStringExtra("Ma_CbNv");
        dvcs=intent.getStringExtra("Ma_Dvcs");
        
        arrayDsDonDatHang=new ArrayList<>();
        adapter=new DsDonDatHangAdapter(activity_dsdondathang.this,R.layout.dong_dsdondathang,arrayDsDonDatHang);
        lvdsdondathang.setAdapter(adapter);

        arrayCtDsDonDatHang=new ArrayList<>();
        adapterct=new DsChiTietDonDatHangAdapter(activity_dsdondathang.this,R.layout.dialog_chitietdondathangtdv,arrayCtDsDonDatHang);



        try {
            selectdonhang();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void selectdonhang() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
            //String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
            try {
                PreparedStatement statement = con.prepareStatement("EXEC usp_SaleOrder_SAP '','" + macbnv + "' ");
                resultSet = statement.executeQuery();
                arrayDsDonDatHang.clear();

                for (int i = 0; i <= resultSet.getRow(); i++) {
                    if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                    {
                        soct = resultSet.getString("So_Ct");
                        String ngayct = resultSet.getString("Ngay_Ct");
                        ngayct = ngayct.substring(0, 10);
                        //String doituong = resultSet.getString("Ten_Dt");
                        //String tongtien = resultSet.getString("Tien");
                        //String tinhtrang = resultSet.getString("Trang_Thai");
                        arrayDsDonDatHang.add(new DsDonDatHang(soct, ngayct, resultSet.getString("Ten_Dt"), resultSet.getString("Tien"), resultSet.getString("Trang_Thai")));

                    }
                }

                adapter.notifyDataSetChanged();
                con.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void DialogxoaDDH(String tendt, final String stt){
        AlertDialog.Builder dialogXoa= new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn đình chỉ đơn của '"+ stt +"' này không ?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
                    if (con == null) {
                        Toast.makeText(context, "Không thể kết nối", Toast.LENGTH_SHORT).show();
                    } else {

                        PreparedStatement statement = con.prepareStatement("EXEC usp_CanCelSaleOrder_SAP " +
                                "'"+ stt +"' ");
                        //resultSet = statement.executeQuery();
                        statement.executeUpdate();

                        Toast.makeText(activity_dsdondathang.this, "Đã đình chỉ", Toast.LENGTH_SHORT).show();
                        selectdonhang();
                        con.close();
                    }


                }
                catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    selectdonhang();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        dialogXoa.show();

    }



    public void Dialogxemctddhtdv(final String stt) {
        soct=stt;
        try {
            selectchitietdonhang();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        View rowList = getLayoutInflater().inflate(R.layout.dialog_chitietdondathangtdv, null);
        alertDialog.setTitle("Mã Vt                     Gt/Code      SL      ĐG             TT");

        lvctddhtdv=rowList.findViewById(R.id.lvctddhtdv);

        lvctddhtdv.setAdapter(adapterct);
        adapterct.notifyDataSetChanged();
        alertDialog.setView(rowList);
        AlertDialog dialog = alertDialog.create();
        dialog.show();

    }


    public void selectchitietdonhang() throws SQLException {

        dem=0;

        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
            try {
                PreparedStatement statement = con.prepareStatement("EXEC usp_danhsachCtDDH_Android '" + soct + "'" +
                        ",'','" + macbnv + "' ");

                resultSet = statement.executeQuery();
                arrayCtDsDonDatHang.clear();

                for (int i = 0; i <= resultSet.getRow(); i++) {
                    if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                    {
                        mavtdialog = resultSet.getString("Ma_Vt");
                        tenvtdialog = resultSet.getString("Ten_Vt");
                        //kmdialog = resultSet.getString("Khuyen_Mai");
                        ctkmdialog = resultSet.getString("Khuyen_Mai_LMH");
                        gtcdialog = "0";
//                    if(gtcdialog==null)
//                    {
//                        gtcdialog="0";
//                    }
                        soluongdialog = resultSet.getString("So_Luong");
                        giadialog = resultSet.getString("Gia");
                        ttdialog = resultSet.getString("Tien");
                        arrayCtDsDonDatHang.add(new DsChiTietDonDatHang(mavtdialog, tenvtdialog, kmdialog, ctkmdialog, gtcdialog, soluongdialog, giadialog, ttdialog));
                        dem = i;

                    }
                }

                con.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




}