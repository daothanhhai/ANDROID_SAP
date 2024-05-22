package com.example.erp_tuyen.loginsap;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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

public class activity_thunokhachhang extends AppCompatActivity {
    String Id, Stt, IdCt, Rowid;
    String sttbk="";
    String sttcdhtt,SttGoc;
    String IdUser, dvcs1;

    ListView lvthunokhachhang;
    ArrayList<ThuNoKhachHang> arrayThuNoKhachHang;

    public static ArrayList<ThuNoKhachHang> arrayTraNoKhachHang;  // sử dụng đưa hóa đơn vào lúc check

    ThuNoKhachHangAdapter adapter;
    private MainActivity context;


    ResultSet resultSet;
    Button buttontnkhbc, buttonluutnkh;
    EditText edittextungaytnkh;
    EditText edittextdenngaytnkh;
    EditText editghichuttgh;

    AutoCompleteTextView atckhtnkh;
    CheckBox checkthunokh;


    int flag = 0;
    String dvcs, username, macbnv;
    String madt, tendt, makh;
    ImageView imgluubk, imgxoabk;

    ArrayList<String> arraymadt = new ArrayList<String>();



    int so=0;
    private ProgressDialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thunokhachhang);
        getSupportActionBar().setTitle("Check Thu Nợ KH");
        lvthunokhachhang = (ListView) findViewById(R.id.listviewtnkh);
        buttontnkhbc = (Button) findViewById(R.id.buttonbctnkh);
        buttonluutnkh = (Button) findViewById(R.id.buttonluutnkh);
        edittextungaytnkh = (EditText) findViewById(R.id.edittexttntnkh);
        edittextdenngaytnkh = (EditText) findViewById(R.id.edittextdntnkh);
        checkthunokh=(CheckBox) findViewById(R.id.checkthunokh);
        //checkthunokh=(CheckedTextView) findViewById(R.id.checkthunokh);
        lvthunokhachhang.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        ImageView imgtnkh = (ImageView) findViewById(R.id.imagetnkh);
        atckhtnkh = (AutoCompleteTextView) findViewById(R.id.acttnkh);
        editghichuttgh = (EditText) findViewById(R.id.editghichuttgh);


        Intent intent = getIntent();
        dvcs = intent.getStringExtra("Ma_Dvcs");
        username = intent.getStringExtra("USERNAME");
        macbnv = intent.getStringExtra("Ma_CbNv");
        IdUser=intent.getStringExtra("IdUser");
        sttbk=intent.getStringExtra("Sttbk");

        taothongtinCN();
        arrayThuNoKhachHang = new ArrayList<>();

        adapter = new ThuNoKhachHangAdapter(activity_thunokhachhang.this, R.layout.dong_thunokhachhang, arrayThuNoKhachHang);
        lvthunokhachhang.setAdapter(adapter);
            // Đoạn này là lấy tiêu đề của dòng dữ liệu làm cho listview
//        LayoutInflater inflater = getLayoutInflater();
//        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.dong_thunokhachhang, lvthunokhachhang, false);
//        lvthunokhachhang.addHeaderView(header, null, false);

        String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        edittextungaytnkh.setText(date_n);
        edittextdenngaytnkh.setText(date_n);

        buttontnkhbc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingBar = new ProgressDialog(activity_thunokhachhang.this);
                LoadingBar.setTitle("OPC - MOBILE");
                LoadingBar.setMessage("Đang tải dữ liệu.... usp_Kcd_CongNoOPC_PL5AnDroid");
                LoadingBar.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            selectcongno();
                            lvthunokhachhang.clearFocus();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        LoadingBar.dismiss();
                    }
                }, 50);
            }
        });

        edittextungaytnkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 0;
                chonngay();
            }
        });

        edittextdenngaytnkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                chonngay();
            }
        });

        try {
            selectdoituong();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraymadt);
        atckhtnkh.setAdapter(adapter1);



        imgtnkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atckhtnkh.showDropDown();
            }
        });

        atckhtnkh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                madt = atckhtnkh.getText().toString();

                //Toast.makeText(parent.getContext(), "OK" + a , Toast.LENGTH_SHORT).show();
                try {
                    selectdoituong();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonluutnkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoadingBar=new ProgressDialog(activity_thunokhachhang.this);
                LoadingBar.setTitle("OPC - MOBILE");
                LoadingBar.setMessage("Lưu đơn hàng");
                LoadingBar.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            luubangke();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        LoadingBar.dismiss();
                    }
                },100);

            }
        });

        lvthunokhachhang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> AdapterView, View view, int i, long id) {

                ThuNoKhachHang thuno=arrayThuNoKhachHang.get(i);
                if(thuno.getIschecked()){
                    thuno.setIschecked(false);
                }
                else {
                    thuno.setIschecked(true);
                }
                arrayThuNoKhachHang.set(i,thuno);
                adapter.updatelistview(arrayThuNoKhachHang);

            }
        });

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
                    edittextungaytnkh.setText(simpleDateFormat.format(calendar.getTime()));
                }
                if (flag == 1) {
                    edittextdenngaytnkh.setText(simpleDateFormat.format(calendar.getTime()));
                }
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    public void selectcongno() throws SQLException {

        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            String tungay = edittextungaytnkh.getText().toString();
            String denngay = edittextdenngaytnkh.getText().toString();
            makh = atckhtnkh.getText().toString();
            if (makh.equals("")) {
                makh = "";
            }
            else {
                if (dvcs.equals("A02")) {
                    makh = atckhtnkh.getText().toString().substring(0, 16);
                } else {
                    makh = atckhtnkh.getText().toString().substring(0, 13);
                }
            }


            PreparedStatement statement = con.prepareStatement("usp_Kcd_CongNoOPC_PL5AnDroid" +
                    "'" + tungay + "' ,'" + denngay + "','131','01','02','06','07','03','" + makh + "'" +
                    ",0,0,'" + dvcs + "',''," +
                    "'" + username + "',0,'','VND'");
            resultSet = statement.executeQuery();


            arrayThuNoKhachHang.clear();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {

                    String stthd = resultSet.getString("Stt_Hd");
                    String sohd = resultSet.getString("So_Ct");
                    String ngayhd = resultSet.getString("Ngay_Ct");
                    ngayhd = ngayhd.substring(0, 10);
                    String madt = resultSet.getString("Ma_Dt");
                    String tendt = resultSet.getString("Ten_Dt");
                    String tiengoc = resultSet.getString("Tien_Goc_Hd");
                    String tienno = resultSet.getString("Tong_Tien");
                    String thuno=resultSet.getString("Tong_Tien");
                    boolean ischecked=false;

                    arrayThuNoKhachHang.add(new ThuNoKhachHang(stthd,sohd, ngayhd, madt, tendt, tiengoc, tienno,ischecked,thuno,""));
                }
            }

            adapter.notifyDataSetChanged();

            con.close();
        }
    }

    public void selectdoituong() throws SQLException {
        Connection con = SERVER.Connect();
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            PreparedStatement statement = con.prepareStatement("EXEC usp_LookupKHGH_Android '"+ dvcs +"','"+ username +"','"+ macbnv +"' " );
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

    public void luubangke() throws SQLException {

        try {

            Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
            if (con == null) {
                Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
            } else {

                if (sttbk == null) {

                    String ngayct = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                    taostt();
                    String query = "INSERT INTO [B30Bk]([Ma_Dvcs],[Ma_Dvcs1],[Stt],[Ma_Ct],[Ngay_Ct]" +
                            ",[Ma_TDV],[Ma_Tte],[Ty_Gia]\n" +
                            ",[Tien_No_Nt],[Tien_No],[DocStatus],[Posted],[RowId_VoucherRegister]\n" +
                            ",[Stt_Ref],[IsActive],[CreatedBy],[ModifiedBy],[Stt_Android])\n" +
                            "VALUES('" + dvcs + "','" + dvcs1 + "','','BK','" + ngayct + "'" +
                            ",'" + macbnv + "','VND',1.00" +
                            ",0,0,1,1,''\n" +
                            ",'',1,'" + IdUser + "','" + IdUser + "','" + Stt + "')";
                    //trên đây là câu truy vấn

                    Statement stmt = con.createStatement();
                    PreparedStatement pstmt = con.prepareStatement(query);
                    int result = pstmt.executeUpdate();

                    String querystt = "SELECT Stt FROM B30Bk WHERE Stt_AnDroid=N'" + Stt + "'";
                    Statement stmtstt = con.createStatement();
                    resultSet = stmtstt.executeQuery(querystt);
                    for (int i = 0; i <= resultSet.getRow(); i++) {
                        if (resultSet.next()) {
                            SttGoc = resultSet.getString("Stt");
                        }
                    }


                    for (int i = 0; i < arrayTraNoKhachHang.size(); i++) {

                        //String sttcdhtt= String.valueOf(arrayTraNoKhachHang.get(i));

                        String sttcdhtt = arrayTraNoKhachHang.get(i).getStt();
                        String a = arrayTraNoKhachHang.get(i).getThuno();
                        String ghichu=arrayTraNoKhachHang.get(i).getGhichu();
                        a = a.replace(".", "");
                        Float thuno = Float.valueOf(a);

                        if (arrayTraNoKhachHang.get(i).getIschecked() == true) {
                            taorowid();
                            int builinorder = i + 1;

                            String query1 = "INSERT INTO [B30BkDeTail]([Ma_Dvcs],[Ma_Dvcs1],[Stt],[RowId],[BuiltinOrder],[Ma_Ct],[Ngay_Ct]" +
                                    ",[Ma_CbNv],[Tien_Nt],[Tien]\n" +
                                    ",[Tien_PMH],[Gia_Tri_Code],[Stt_Cd_Htt],[Dien_Giai]\n" +
                                    ",[IsActive],[CreatedBy],[ModifiedBy])\n" +
                                    "VALUES('" + dvcs + "','" + dvcs1 + "','" + SttGoc + "','','" + builinorder + "','BK','" + ngayct + "'" +
                                    ",'" + macbnv + "'," + thuno + "," + thuno + " " +
                                    ",0,0,'" + sttcdhtt + "',N'"+ ghichu +"'\n" +
                                    ",1,'" + IdUser + "','" + IdUser + "')";
                            //trên đây là câu truy vấn
                            Statement stmt1 = con.createStatement();
                            PreparedStatement pstmt1 = con.prepareStatement(query1);
                            int result1 = pstmt1.executeUpdate();
                        }

                    }

                    arrayTraNoKhachHang.clear();

                    con.close();
                }

                // Thêm dòng vào bảng kê có sẵn
                else{
                    String ngayct = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    for (int i = 0; i < arrayTraNoKhachHang.size(); i++) {

                        //String sttcdhtt= String.valueOf(arrayTraNoKhachHang.get(i));

                        String sttcdhtt = arrayTraNoKhachHang.get(i).getStt();
                        String a = arrayTraNoKhachHang.get(i).getThuno();
                        a = a.replace(".", "");
                        Float thuno = Float.valueOf(a);

                        if (arrayTraNoKhachHang.get(i).getIschecked() == true) {
                            taorowid();
                            int builinorder = i + 1;

                            String query1 = "INSERT INTO [B30BkDeTail]([Ma_Dvcs],[Ma_Dvcs1],[Stt],[RowId],[BuiltinOrder],[Ma_Ct],[Ngay_Ct]" +
                                    ",[Ma_CbNv],[Tien_Nt],[Tien]\n" +
                                    ",[Tien_PMH],[Gia_Tri_Code],[Stt_Cd_Htt]\n" +
                                    ",[IsActive],[CreatedBy],[ModifiedBy])\n" +
                                    "VALUES('" + dvcs + "','" + dvcs1 + "','" + sttbk + "','','" + builinorder + "','BK','" + ngayct + "'" +
                                    ",'" + macbnv + "'," + thuno + "," + thuno + " " +
                                    ",0,0,'" + sttcdhtt + "'\n" +
                                    ",1,'" + IdUser + "','" + IdUser + "')";
                            //trên đây là câu truy vấn
                            Statement stmt1 = con.createStatement();
                            PreparedStatement pstmt1 = con.prepareStatement(query1);
                            int result1 = pstmt1.executeUpdate();
                        }

                    }
                    arrayTraNoKhachHang.clear();
                    con.close();

                }

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

    public void taothongtinCN() {
        if (dvcs.equals("A01")) {
            dvcs1 = "1N101";

        }
        if (dvcs.equals("A02")) {
            dvcs1 = "2N101-01";

        }
        if (dvcs.equals("A03")) {
            dvcs1 = "2N301";


        }

        if (dvcs.equals("A04")) {
            dvcs1 = "2N302";

        }
        if (dvcs.equals("A05")) {
            dvcs1 = "2N201";

        }
        if (dvcs.equals("A06")) {
            dvcs1 = "2N202";

        }
        if (dvcs.equals("A07")) {
            dvcs1 = "2T101";

        }
        if (dvcs.equals("A08")) {
            dvcs1 = "2T102";

        }
        if (dvcs.equals("A09")) {
            dvcs1 = "2T103";

        }
        if (dvcs.equals("A10")) {
            dvcs1 = "2B101";

        }


    }

}