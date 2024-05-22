package com.example.erp_tuyen.loginsap;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class activity_giamsatviengthamkhachhang extends AppCompatActivity {

    private ArrayList<String> datangayvtgsvtkh = new ArrayList<String>();
    private ArrayList<String> datakhgsvtkh = new ArrayList<String>();

    private ArrayList<String> datadiachigsvtkh = new ArrayList<String>();
    private ArrayList<String> datavtgsvtkh = new ArrayList<String>();
    private ArrayList<String> datatdvgsvtkh = new ArrayList<String>();
    private ArrayList<String> dataspvtgsvtkh = new ArrayList<String>();
    private ArrayList<String> datahinhanhgsvtkh = new ArrayList<String>();

    private ArrayList<Bitmap> datahinhgsvtkh=new ArrayList<>();

    private ArrayList<String> datanoidunggsvtkh=new ArrayList<>();

    ArrayList<String> arraymadt = new ArrayList<String>();

    ArrayList<String> arraymatdv = new ArrayList<String>();

    EditText edtngsvtkh,eddngsvtkh;

    int flag=0;
    String dvcs,macbnv,chucdanh,username;

    String madt, tendt,makh;
    String matdv,tentdv,matdvlk;
    ResultSet resultSet;
    Button buttongsvtkh;
    TableRow tbrgsvtkh;
    private ProgressDialog LoadingBar;

    private ArrayList<String> data4 = new ArrayList<String>();
    private TableLayout table;
    private TableRow row;

    AutoCompleteTextView atcgsvtkh,atctdvvtkh;
    ImageView imggsvtkh,imgtdvvtkh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giamsatviengthamkhachhang);
        getSupportActionBar().setTitle("Giám sát viếng thăm KH");
        Intent intent=getIntent();
        dvcs=intent.getStringExtra("Ma_Dvcs");
        macbnv=intent.getStringExtra("Ma_CbNv");
        chucdanh=intent.getStringExtra("Chuc_Danh");
        username=intent.getStringExtra("USERNAME");
        anhxa();


        String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        edtngsvtkh.setText(date_n);
        eddngsvtkh.setText(date_n);

        edtngsvtkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                chonngay();
            }
        });
        eddngsvtkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
                chonngay();
            }
        });

        try {
            selectdoituong();
            selectTDV();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraymadt);
        atcgsvtkh.setAdapter(adapter1);

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraymatdv);
        atctdvvtkh.setAdapter(adapter2);

        imggsvtkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atcgsvtkh.showDropDown();
            }
        });

        atcgsvtkh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                madt = atcgsvtkh.getText().toString();

                try {
                    selectdoituong();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        imgtdvvtkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atctdvvtkh.showDropDown();
            }
        });

        atctdvvtkh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                matdvlk = atctdvvtkh.getText().toString().substring(0,8);
                try {
                    selectTDV();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        buttongsvtkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingBar=new ProgressDialog(activity_giamsatviengthamkhachhang.this);
                LoadingBar.setTitle("SAP - ANDROID");
                LoadingBar.setMessage("Đang tải dữ liệu.... usp_GiamSatViengThamKH_Android");
                LoadingBar.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            table.removeViews(1, Math.max(0, table.getChildCount() - 1));
                            selectgsdhtdv();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        LoadingBar.dismiss();
                    }
                },50);
            }
        });



    }

    void anhxa(){
        edtngsvtkh=(EditText) findViewById(R.id.editTexttngsvtkh);
        eddngsvtkh=(EditText) findViewById(R.id.editTextdngsvtkh);
        buttongsvtkh=(Button) findViewById(R.id.buttongsvtkh);
        table = (TableLayout) findViewById(R.id.tbgsvtkh);
        tbrgsvtkh=(TableRow) findViewById(R.id.tbrowgsvtkh);
        imggsvtkh = (ImageView) findViewById(R.id.imagegsvtkh);
        atcgsvtkh=(AutoCompleteTextView) findViewById(R.id.actgsvtkh);
        imgtdvvtkh=(ImageView) findViewById(R.id.imagetdvvtkh);
        atctdvvtkh=(AutoCompleteTextView) findViewById(R.id.acttdvvtkh);


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
                    edtngsvtkh.setText(simpleDateFormat.format(calendar.getTime()));
                }
                if(flag==1)
                {
                    eddngsvtkh.setText(simpleDateFormat.format(calendar.getTime()));
                }
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }

    public static Bitmap getZoomedImage(Bitmap originalImage, float scale) {
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalImage, (int)(20*scale), (int)(20*scale), false);
        return Bitmap.createBitmap(
                scaledBitmap,
                (scaledBitmap.getWidth()-originalImage.getWidth())/2,
                (scaledBitmap.getHeight()-originalImage.getHeight())/2,
                originalImage.getWidth(),
                originalImage.getHeight());
    }
    

    private void selectgsdhtdv() throws SQLException {

        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {


            String tungay=edtngsvtkh.getText().toString();
            String denngay=eddngsvtkh.getText().toString();

            makh = atcgsvtkh.getText().toString();
            if (makh.equals("")) {
                makh = "";

            }
            else {
                if (dvcs.equals("A02")) {
                    makh = atcgsvtkh.getText().toString().substring(0, 7);
                } else {
                    makh = atcgsvtkh.getText().toString().substring(0, 7);
                }
            }

            matdvlk = atctdvvtkh.getText().toString();
            if (matdvlk.equals("")) {
                matdvlk = "";

            } else {
                matdvlk = atctdvvtkh.getText().toString().substring(0, 8);
            }


            PreparedStatement statement = con.prepareStatement("EXEC usp_GiamSatViengThamKH_Android '"+ tungay +"' ,'"+ denngay +"'" +
                    ",'"+ dvcs +"','"+ username +"','"+ macbnv +"','"+ makh +"','" + matdvlk + "' " );
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    String ngayvt = resultSet.getString("Ngay_Vt");
                    if(ngayvt != null) {
                        ngayvt = ngayvt.substring(0, 10);
                    }
                    String khachhang = resultSet.getString("Ten_Dt");

                    String diachi=resultSet.getString("Dia_Chi");

                    String tdv=resultSet.getString("Ten_TDV");

                    String sophutviengtham = resultSet.getString("So_Phut_Vieng_Tham");

                    String hinhanh = resultSet.getString("Hinh_Anh");

                    byte[] hinh= Base64.decode(hinhanh,Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(hinh, 0, hinh.length);





                    String noidung=resultSet.getString("Noi_Dung");

                    datangayvtgsvtkh.add(ngayvt);
                    datakhgsvtkh.add(khachhang);
                    datadiachigsvtkh.add(diachi);
                    datatdvgsvtkh.add(tdv);
                    dataspvtgsvtkh.add(sophutviengtham);
                    datahinhgsvtkh.add(bitmap);
                    datanoidunggsvtkh.add(noidung);


                    row = new TableRow(this);
                    TextView t0=new TextView(this);
                    TextView t1 = new TextView(this);
                    TextView t2 = new TextView(this);
                    TextView t3 = new TextView(this);
                    TextView t4 = new TextView(this);
                    //TextView t5 = new TextView(this);
                    final ImageView t5= new ImageView(this);
                    TextView t6 = new TextView(this);

                    t0.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t0.setWidth(110);
                    t0.setHeight(100);
                    t0.setTextSize(14);
                    t0.setTextColor(Color.BLACK);   // Ngày vt


                    t1.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t1.setWidth(300);
                    t1.setHeight(100);
                    t1.setTextSize(14);
                    t1.setTextColor(Color.BLACK);  //khách hàng

                    t2.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t2.setWidth(300);
                    t2.setHeight(100);
                    t2.setTextColor(Color.BLACK);  // địa chỉ

                    t3.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t3.setHeight(100);
                    t3.setTextColor(Color.BLACK);  // TDV

                    t4.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t4.setHeight(100);
                    t4.setTextColor(Color.RED); // số phút
                    t4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    t4.setTextSize(15);

                    //t5.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
//                    t5.setMaxHeight(70);

                    //t5.setTextColor(Color.BLACK);
                    //android:scaleType="centerCrop"


                    t6.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
                    t6.setWidth(300);
                    t6.setHeight(100);
                    t6.setTextColor(Color.BLACK); // noi dung



                    for (int j = 0; j < datakhgsvtkh.size(); j++) {
                        String pngayvt=datangayvtgsvtkh.get(j);
                        String ptkh = datakhgsvtkh.get(j);
                        String ptdc = datadiachigsvtkh.get(j);
                        String pttdv = datatdvgsvtkh.get(j);
                        String ptspvt = dataspvtgsvtkh.get(j);
                        String ptndvt=datanoidunggsvtkh.get(j);
                        //String phinhanh = datahinhanhgsvtkh.get(j);

                        t0.setText(pngayvt);
                        t1.setText(ptkh);
                        t2.setText(ptdc);
                        t3.setText(pttdv);
                        t4.setText(ptspvt);
                        t5.setImageBitmap(bitmap);

                        t6.setText(ptndvt);

                    }
                    row.setBackground(ContextCompat.getDrawable(this,R.drawable.border));

                    row.addView(t0);
                    row.addView(t1);
                    row.addView(t2);

                    row.addView(t3);
                    row.addView(t4);

                    row.addView(t5);
                    row.addView(t6);

                    table.addView(row);



                }
            }

            con.close();
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

    public void selectTDV() throws SQLException {
        Connection con = SERVER.Connect();
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            PreparedStatement statement = con.prepareStatement("EXEC usp_LookupTDV_Android '"+ dvcs +"','"+ username +"','"+ macbnv +"' " );
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    matdv = resultSet.getString("Ma_CbNv");
                    tentdv = resultSet.getString("Ten_CbNv");
                    arraymatdv.add(matdv + ' ' + tentdv);

                }
            }

            con.close();
        }
    }
}