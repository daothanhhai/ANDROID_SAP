package com.example.erp_tuyen.loginsap;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.gson.Gson;

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

//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
import java.util.List;
//import retrofit2.Call;
//import retrofit2.http.GET;
public class activity_dondathangtdv extends AppCompatActivity {


    //GridView grdoituong;
    //GridView grdonhang;
    LinearLayout layout;
    EditText edhovaten, eddiachi, edngaydonhang, edtenvt, edgiagoc, edchietkhau,ednoidung,eddgdhtdv;
    EditText edsoluong, eddongia, edthanhtien,edcodetl2,edgiatrithuong;
    AutoCompleteTextView atmadtdhtdv, atmavtdhtdv,atctkmdhtdv,athopdongdhtdv;
    Button btthem, btxoa, btluu, bttinhkm;

    ArrayList<DonDatHang> arrayDonDatHang;
    ArrayList<String> arraymadt = new ArrayList<String>();
    ArrayList<String> arraymavt = new ArrayList<String>();
    ArrayList<String> arraymalsp = new ArrayList<String>();
    ArrayList<String> arraymahopdong = new ArrayList<String>();

    DonDatHangAdapter adapter;
    private MainActivity context;
    ResultSet resultSet;
    String madt,machietkhau,chietkhau, thuegtgt="0",noidung,diengiai;
    String mavt,malsp, bgid, mathue, dvt, giagoc="0",dongia="0",thanhtien="0",thanhtiengn="0",mavtgtc,codekmle,soluong;
    String giagoccu="0";
    String ctkm_ck,diachi,paymentId,paymentId1;
    int checkkm=0;
    int soluongcode=0;
    int demmathue=0;
    String mathuecodinh,thuegtgtcodinh="0",chietkhaucodinh="0";

    float tongtienhang=0,tongtienthue=0,tongcong=0;
    Double giatricode=0.0,tonggiatricode=0.0,ckkm=0.0,giatrithuong=0.0,giatrikm=0.0;
    //Double tiencodekmle_A=0.0,tiencodekmle_B=0.0,tiencodekmsi_A=0.0,tiencodekmsi_B=0.0,tiencodekmkhac=0.0;
    //Double tiencodekm3=0.0,tiencodekm5=0.0,tiencodekm6=0.0,tiencodekm10=0.0,tiencodekm15=0.0,tiencodekm20=0.0,tiencodekm25=0.0;
    double tonggiatrithuong = 0.0,tonggiatrikm=0.0;
    String ctkm,phanloaicp="";
    String tkno,tkco,tkco2,giatbtt="0",makm="",mathue_km="";
    CheckBox chkm;

    Float total1;
    String total;
    float tienhang = 0, thue = 0,tienhangdong=0; // thêm tiền hàng từng dòng

    float tienhanggn1 = 0;
    float tienhanggn2 = 0;

    float tienhanggn1_1=0;
    float tienhanggn2_1=0;
    float tongcombo=0;
    float tlgnn1=0;
    float tlgnn2=0;

    float tienhangxoa=0;
    float tientruocthue=0;

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
    private ArrayList<String> data16 = new ArrayList<String>();

    private ArrayList<String> datattgn = new ArrayList<String>();

    DonDatHangTDVAdapter adapterdondathangtdv;
    ArrayList<DonDatHangTDV> arrayDonDatHangTDV;
    ListView lvdhtdv;


    TextView tvtienhang,tvtienhanggn1,tvtienhanggn2,tvtln1,tvtln2,tvtongcbgn,tvtonggtkm,tvgiatricode, tvthue, tvtongcong;
    String IdUser,dvcs,dvcs1,makho,soseri,username, macbnv;
    String madtvt,tendtvt,diachivt,chietkhauvt; // Đối tượng được checkin viếng thăm
    String sohopdong="",bizdocid="";

//    EditText t5;
//    TextView t6,t7;

    public int vitridong=0;
    private static final long INTERVAL = 60*60 * 1000;

    private Handler handler = new Handler();
    private Runnable popupRunnable = new Runnable() {
        @Override
        public void run() {
            CreatepopupUpwindow(); // Gọi hàm hiển thị popup
            handler.postDelayed(this, INTERVAL); // Lặp lại sau mỗi 3 phút
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dondathangtdv);
        layout = findViewById(R.id.dondathangtdv);//popup
        getSupportActionBar().setTitle("Đơn đặt Hàng OPC");

        anhxa();

        ImageView imgmadtdhtdv = (ImageView) findViewById(R.id.imgmdtdhtdv);
        ImageView imgmavtdhtdv = (ImageView) findViewById(R.id.imgmvtdhtdv);
        ImageView imgctkmdhtdv = (ImageView) findViewById(R.id.imagectkmdhtdv);
        ImageView imagehopdongdhtdv=(ImageView) findViewById(R.id.imghopdongdhtdv);

        
        Intent intent=getIntent();
        IdUser=intent.getStringExtra("IdUser");
        dvcs=intent.getStringExtra("Ma_Dvcs");
        username = intent.getStringExtra("USERNAME");
        macbnv = intent.getStringExtra("Ma_CbNv");
        madtvt=intent.getStringExtra("MADTVT");
        tendtvt=intent.getStringExtra("TENDTVT");
        diachivt=intent.getStringExtra("DIACHIVT");
        chietkhauvt=intent.getStringExtra("CHIETKHAUVT");
        //taothongtinCN();


        arrayDonDatHangTDV=new ArrayList<>();
        adapterdondathangtdv=new DonDatHangTDVAdapter(activity_dondathangtdv.this,R.layout.dong_dondathangtdv,arrayDonDatHangTDV);

        lvdhtdv.setAdapter(adapterdondathangtdv);

        AdapterGr = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ArrMaVt);

        // Load tự động mã đối tượng vào texbox
        //Load tự động mã vật tư vào texbox
        try {

            selectdoituong();
            selectmavt();
            selectctkmvt();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraymadt);
        atmadtdhtdv.setAdapter(adapter1);

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraymavt);
        atmavtdhtdv.setAdapter(adapter2);

        final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraymalsp);
        atctkmdhtdv.setAdapter(adapter3);

        final ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraymahopdong);
        athopdongdhtdv.setAdapter(adapter4);

        String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        edngaydonhang.setText(date_n);


        imgmadtdhtdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atmadtdhtdv.showDropDown();

            }
        });
        imgmavtdhtdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atmavtdhtdv.showDropDown();
            }
        });

        imgctkmdhtdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atctkmdhtdv.showDropDown();
            }
        });

        if(madtvt!=null)
        {
            atmadtdhtdv.setText(madtvt);
            madt = atmadtdhtdv.getText().toString();

            try {
                selecttendoituong();

                //selecthopdong();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


        atmadtdhtdv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                madt = atmadtdhtdv.getText().toString();
                if (madt.equals("")) {
                    madt = "";
                } else {

                    madt = atmadtdhtdv.getText().toString().substring(0, 7);
                    madt=madt.trim();
                }


                try {
                    selecttendoituong();
                    //selecthopdong();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });


        imagehopdongdhtdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                athopdongdhtdv.showDropDown();

            }
        });

        athopdongdhtdv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sohopdong = athopdongdhtdv.getText().toString();

                try {
                    selectbizdocid();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });


        atmavtdhtdv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mavt = atmavtdhtdv.getText().toString();
                if(mavt != "")
                {
                    mavt=mavt.substring(0,7);
                    mavt=mavt.trim();
                    // Lấy CTKM theo mã vật tư chọn
                    try {
                        selectctkmvt();

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

                // Không có hợp đồng
                if(sohopdong.equals("")) {
                    try {
                        selecttenvattu();
                        //selectbanggia();
                        //selectbgdetail();
                        dongia();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    try {
                        selecttenvattu();
                        //selectbanggia();
                        //selectbghopdong();
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
                    edchietkhau.setText("100");
                    eddongia.setText("0");
                    edthanhtien.setText("0");
                }
                else
                {
                    checkkm=0;
                    //edgiagoc.setText(giagoc);
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

                Double sl,dg,tt,ttgn;  // bổ sung ttgn = thành tiền giá nguyên

                ctkm_ck= atctkmdhtdv.getText().toString();
                if(ctkm_ck.equals("CK01"))
                {
                    try {
                        selectckkm();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    String giatrickkm= String.valueOf(ckkm);
                    edchietkhau.setText(giatrickkm);

                    Double ck_KM =  (Float.parseFloat(giagoc) * ckkm)/100;
                    dongia= String.valueOf(Float.parseFloat(giagoc) - ck_KM);
                    eddongia.setText(String.format("%,.0f", Float.valueOf(dongia)));
                }


                if(ctkm_ck.equals("KM_HO") || ctkm_ck.equals("KM25_00") || ctkm_ck.equals("KM25_08")


                        || ctkm_ck.equals("HCM_TUSSIDAY") || ctkm_ck.equals("HCD")
                        || ctkm_ck.equals("KM_AIRSAT_10")
                        || ctkm_ck.equals("KM_Fucoidan") || ctkm_ck.equals("KM_TAITRO_12")

                        || ctkm_ck.equals("BH_FUCOIDAN")|| ctkm_ck.equals("BH_SANOLIN")
                        || ctkm_ck.equals("GG1_DX992")|| ctkm_ck.equals("GG2_DX992")
                        || ctkm_ck.equals("TG_MULTI_T12")|| ctkm_ck.equals("TG_KTT_T12")
                        || ctkm_ck.equals("TG_DKD_T12") || ctkm_ck.equals("GG_DX21")
                        || ctkm_ck.equals("GG_DX31") || ctkm_ck.equals("GG_DX145")
                        || ctkm_ck.equals("GG_DX21_VIP1") || ctkm_ck.equals("GG_DX21_VIP2")

                        || ctkm_ck.equals("KM_DX21_VIP1") || ctkm_ck.equals("KM_DX21_VIP2")
                        || ctkm_ck.equals("KM_MULTI") || ctkm_ck.equals("GG_DX49")
                        || ctkm_ck.equals("KM_MULTI_VIP1") || ctkm_ck.equals("KM_MULTI_VIP2")


                        || ctkm_ck.equals("KM_DX166") || ctkm_ck.equals("KM_DX22_VIP")
                        || ctkm_ck.equals("QTSK_OTC") || ctkm_ck.equals("KM_TW25_08")
                        || ctkm_ck.equals("KM_TW25_09") || ctkm_ck.equals("KM_TW25_09_VIP")
                        || ctkm_ck.equals("KM_DX236_03_VIP1") || ctkm_ck.equals("KM_DX236_03_VIP2")
                        || ctkm_ck.equals("KO_KM_VIP") || ctkm_ck.equals("GG_DX22_UD1")
                        || ctkm_ck.equals("GG_DX22_UD2") || ctkm_ck.equals("GG_DX22_UD3")

                        || ctkm_ck.equals("KM_TW25_12") || ctkm_ck.equals("KM_TW25_15")
                        || ctkm_ck.equals("HCD_CNTG_09") || ctkm_ck.equals("GG_DX328")
                        || ctkm_ck.equals("KM_MULTIOPC_3006") || ctkm_ck.equals("KM_TW25_17")
                        || ctkm_ck.equals("KM_TW25_18") || ctkm_ck.equals("KM_TW25_19")
                        || ctkm_ck.equals("KHM_GT")
                )
                {
                    try {
                        selectckkm();

                        if(ctkm_ck.equals("KM_MULTIOPC_3006") || ctkm_ck.equals("KM_TW25_17") || ctkm_ck.equals("KM_TW25_18") || ctkm_ck.equals("KM_TW25_19") || ctkm_ck.equals("HCD"))
                        {
                            if(paymentId.equals("1012"))
                            {
                                paymentId1="Z026";
                            }
                            if(paymentId.equals("1008"))
                            {
                                paymentId1="Z029";
                            }
                            if(paymentId.equals("1013"))
                            {
                                paymentId1="1004";
                            }
                            if(paymentId.equals("1014"))
                            {
                                paymentId1="1005";
                            }

                            if(paymentId.equals("1013"))
                            {
                                paymentId1="1004";
                            }

                            if(paymentId.equals("1015"))
                            {
                                paymentId1="1006";
                            }
                            if(paymentId.equals("1018"))
                            {
                                paymentId1="1003";
                            }
                            if(paymentId.equals("Z002"))
                            {
                                paymentId1="Z027";
                            }
                            if(paymentId.equals("Z007"))
                            {
                                paymentId1="1007";
                            }
                            if(paymentId.equals("Z008"))
                            {
                                paymentId1="Z012";
                            }
                            if(paymentId.equals("Z009"))
                            {
                                paymentId1="1003";
                            }
                            if(paymentId.equals("Z005"))
                            {
                                paymentId1="1001";
                            }
                        }
                        else {
                            paymentId1=paymentId;
                        }

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    String giatrickkm= String.valueOf(ckkm);
                    edchietkhau.setText(giatrickkm);

                    Double ck_KM=  (Float.parseFloat(giagoc) * ckkm)/100;
                    dongia= String.valueOf(Float.parseFloat(giagoc) - ck_KM);
                    eddongia.setText(String.format("%,.0f", Float.valueOf(dongia)));
                }


                if(

                        ctkm_ck.equals("847_OTC")
                )
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

                if(ctkm_ck.equals("CK05") || ctkm_ck.equals("CK12"))
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

                if(ctkm_ck.equals("KM_UD8"))
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

                if(ctkm_ck.equals("KM_UD10"))
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
                    edchietkhau.setText(String.format("%,.0f", Float.valueOf(100)));
                    eddongia.setText(String.format("%,.0f", Float.valueOf(0)));
                    dongia="0";
                }

                if (soluong.length() > 0) {
                    sl = Double.parseDouble(soluong);
                    dg= Double.parseDouble(dongia);
                    tt = sl * dg;
                    ttgn = sl * Double.parseDouble(giagoc);
                    
                    thanhtien= String.valueOf(tt);
                    thanhtiengn=String.valueOf(ttgn);

                    edthanhtien.setText(String.format("%,.0f", Double.valueOf(String.valueOf(tt))));
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


                LoadingBar=new ProgressDialog(activity_dondathangtdv.this);
                LoadingBar.setTitle("SAP - ANDROID");
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


        lvdhtdv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vitridong=position;
            }
        });

//        bttinhkm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    tinhkhuyenmai();
//                } catch (SQLException throwables) {
//                    throwables.printStackTrace();
//                }
//            }
//        });
//        CreatepopupUpwindow();//goi ham view popup
//        handler.postDelayed(popupRunnable, INTERVAL);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Hủy lịch trình khi activity bị hủy
        handler.removeCallbacks(popupRunnable);
    }

    private void CreatepopupUpwindow(){
        LayoutInflater inflater = (LayoutInflater)  getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_popup_background, null);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = 1600;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView,width,height,focusable);
        layout.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(layout, Gravity.CENTER,0,0);
            }
        });


    }
    void anhxa()
    {
        atmadtdhtdv = (AutoCompleteTextView) findViewById(R.id.actmdtdhtdv);
        atmavtdhtdv = (AutoCompleteTextView) findViewById(R.id.actmavtdhtdv);
        atctkmdhtdv = (AutoCompleteTextView) findViewById(R.id.actctkmdhtdv);
        athopdongdhtdv = (AutoCompleteTextView) findViewById(R.id.acthopdongdhtdv);
        chkm = (CheckBox) findViewById(R.id.checkkmdhtdv);

        ImageView imgmadtdhtdv = (ImageView) findViewById(R.id.imgmdtdhtdv);
        ImageView imgmavtdhtdv = (ImageView) findViewById(R.id.imgmvtdhtdv);
        ImageView imgctkmdhtdv = (ImageView) findViewById(R.id.imagectkmdhtdv);
        ImageView imagehopdongdhtdv=(ImageView) findViewById(R.id.imghopdongdhtdv);
        edhovaten = (EditText) findViewById(R.id.edhvtdhtdv);
        eddiachi = (EditText) findViewById(R.id.eddcdhtdv);
        ednoidung=(EditText) findViewById(R.id.ednddhtdv);
        eddgdhtdv = (EditText) findViewById(R.id.eddgdhtdv);
        edngaydonhang = (EditText) findViewById(R.id.edngayctdhtdv);
        edtenvt = (EditText) findViewById(R.id.edtvtdhtdv);
        edgiagoc = (EditText) findViewById(R.id.edgiagocdhtdv);
        edchietkhau = (EditText) findViewById(R.id.edchietkhaudhtdv);
        edsoluong = (EditText) findViewById(R.id.edsoluongdhtdv);
        eddongia = (EditText) findViewById(R.id.eddongiadhtdv);
        edthanhtien = (EditText) findViewById(R.id.edthanhtiendhtdv);
        edcodetl2=(EditText) findViewById(R.id.edcodetl2dhtdv);
        edgiatrithuong=(EditText) findViewById(R.id.edgiatrithuong);

        btthem = (Button) findViewById(R.id.btthemdhtdv);
        btxoa = (Button) findViewById(R.id.btxoadhtdv);
        btluu = (Button) findViewById(R.id.btluudhtdv);

        tvtienhang = (TextView) findViewById(R.id.tvtienhangddhtdv);
        tvtienhanggn1 = (TextView) findViewById(R.id.tvtienhanggn1ddhtdv);
        tvtienhanggn2 = (TextView) findViewById(R.id.tvtienhanggn2ddhtdv);
        tvtln1=(TextView) findViewById(R.id.tvtlgn1ddhtdv);
        tvtln2=(TextView) findViewById(R.id.tvtlgn2ddhtdv);
        tvtongcbgn=(TextView) findViewById(R.id.tvtongcomboddhtdv);

        tvtonggtkm=(TextView) findViewById(R.id.tvtonggtkmddhtdv);
        tvthue = (TextView) findViewById(R.id.tvthueddhtdv);
        tvtongcong = (TextView) findViewById(R.id.tvtongcongddhtdv);
        tvgiatricode=(TextView) findViewById(R.id.tvgtcode);
        lvdhtdv=(ListView) findViewById(R.id.lvddhtdv);
        bttinhkm=(Button) findViewById(R.id.bttinhkmdhtdv);
    }

    public void dongia() {

        if (chietkhau.equals("") || chietkhau.isEmpty()) {
            chietkhau = "0";
        }
        float ck, gg,dg;
        if(giagoc.equals("")){
            giagoc="0";
        }
        gg = Float.parseFloat(giagoc);
        ck = Float.parseFloat(chietkhau);

//        if(mavt.equals("1003745")){
//            ck=0;
//            edchietkhau.setText(String.format("%,.0f", Float.valueOf(ck)));
//        }

        dg = gg - (gg * ck) / 100;
        dongia= String.valueOf(dg);
        eddongia.setText(String.format("%,.0f", Float.valueOf(dg)));

    }

    public void selectdoituong() throws SQLException {

//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectDiskReads()
//                .detectDiskWrites()
//                .detectNetwork()   // or .detectAll() for all detectable problems
//                .penaltyLog()
//                .build());


        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
            arraymadt.clear();
            try {

                PreparedStatement statement = con.prepareStatement("EXEC usp_DmDtTdv_SAP '','" + macbnv + "'");
                resultSet = statement.executeQuery();

                for (int i = 0; i <= resultSet.getRow(); i++) {
                    if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                    {

                        arraymadt.add(resultSet.getString("Ma_Dt") + ' ' + resultSet.getString("Ten_Dt"));
                    }
                }


                con.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }


        }

    }




    public void selecttendoituong() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
            //String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());


            try {
                PreparedStatement statement = con.prepareStatement("EXEC usp_LookupDiscount_SAP '" + madt + "'");
                resultSet = statement.executeQuery();
                int dem = 0;
                for (int i = 0; i <= resultSet.getRow(); i++) {
                    if (resultSet.next()) {
                        dem++;
                        machietkhau = resultSet.getString("CK");
                        chietkhau = resultSet.getString("CK_OPC");
                        chietkhaucodinh = resultSet.getString("CK_OPC");

                        diachi = resultSet.getString("Dia_Chi");
                        eddiachi.setText(diachi);

                        edchietkhau.setText(String.format("%.0f", Float.valueOf(chietkhau)));

                        paymentId = resultSet.getString("Payment_ID");
                        paymentId1 = resultSet.getString("Payment_ID");

                    }
                }
                if (dem == 0) {
                    chietkhau = "0";
                    edchietkhau.setText(String.format("%.0f", Float.valueOf(chietkhau)));
                }

                //adapter.notifyDataSetChanged();
                con.close();
            }
                catch (SQLException e) {
                e.printStackTrace();
                }
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


            String query = "SELECT BizDocId FROM B30BizDoc WHERE DocumentNo=N'"+ sohopdong +"' AND FinishedDate > GETDATE() AND Ma_Dt='"+ madt +"' ";

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
        try {
            PreparedStatement statement = con.prepareStatement("EXEC usp_LookupPrice_SAP '" + mavt + "'");
            resultSet = statement.executeQuery();


            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next()) {
                    //String tenvt = resultSet.getString("Ten_Vt");
                    //String gia = resultSet.getString("Gia");
                    //String loaitp=resultSet.getString("LoaiTp");
                    edtenvt.setText(resultSet.getString("Ten_Vt"));
                    //edgiagoc.setText(gia);
                    giagoc = resultSet.getString("gia");
                    edgiagoc.setText(String.format("%,.0f", Float.valueOf(giagoc)));
                    mathue = resultSet.getString("Ma_Thue");


                    if (mathue.equals("R05")) {
                        thuegtgt = "0.05";
                        mathue_km = "R05";
                    }

                    if (mathue.equals("R08")) {
                        thuegtgt = "0.08";
                        mathue_km = "R08";
                    }

                    if (mathue.equals("R10")) {
                        thuegtgt = "0.1";
                        mathue_km = "R10";
                    }

                }
            }

            if (demmathue == 0) {
                mathuecodinh = mathue;
                thuegtgtcodinh = thuegtgt;
                //demmathue++;
            }


            con.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        }
    }


    public void selectmavt() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
        try {
            PreparedStatement statement = con.prepareStatement("EXEC usp_PriceList_SAP"); // Lấy giá trực tiếp từ SAP
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {

                    arraymavt.add(resultSet.getString("Ma_Vt") + " " + resultSet.getString("Ten_Vt"));
                }
            }
            //adapter.notifyDataSetChanged();
            con.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
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
            PreparedStatement statement = con.prepareStatement("EXEC usp_LookupPrice_SAP '"+ mavt +"'");
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    giagoc = resultSet.getString("Gia");
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
        try {
            PreparedStatement statement = con.prepareStatement("EXEC usp_MaLoaiKM_Android");
            resultSet = statement.executeQuery();
            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next()) {
                    malsp = resultSet.getString("Ma_LSP");
                    arraymalsp.add(malsp);
                }
            }


            con.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        }
    }


    public void selectctkmvt() throws SQLException {
        Connection con = SERVER.Connect();
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            arraymalsp.clear();

            try {
                PreparedStatement statement = con.prepareStatement("EXEC usp_MaLoaiKMVt_Android '" + mavt + "'");
                resultSet = statement.executeQuery();
                for (int i = 0; i <= resultSet.getRow(); i++) {
                    if (resultSet.next()) {
                        malsp = resultSet.getString("Ma_Km");
                        arraymalsp.add(malsp);
                    }
                }

                final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.dong_don_hang, R.id.textViewmadt, arraymalsp);
                atctkmdhtdv.setAdapter(adapter3);


                con.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }


    private void xoaluoi(){
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
            data16.remove(0);

        }
    }


    private void delete() {

        AlertDialog.Builder dialogXoa= new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa Tất cả Lưới này không ?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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
                    data16.remove(0);
                    datattgn.remove(0);
                }
                tienhang=0;
                tvtienhang.setText("");
                tvtienhanggn1.setText("");
                tvtienhanggn2.setText("");
                tvthue.setText("");
                tvtongcong.setText("");
                tongtienhang= Float.valueOf(0);
                tongtienthue= Float.valueOf(0);
                tongcong= Float.valueOf(0);
                giatricode=0.0;
                tonggiatricode=0.0;
//                tiencodekmle_A=0.0;
//                tiencodekmsi_A=0.0;
//                tiencodekmle_B=0.0;
//                tiencodekmsi_B=0.0;
//                tiencodekmkhac=0.0;
                soluongcode=0;
                demmathue=0;
                tienhanggn1_1=0;
                tienhanggn2_1=0;
                arrayDonDatHangTDV.clear();
                adapterdondathangtdv.notifyDataSetChanged();

            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }

    private void them() {

        String productname = atmavtdhtdv.getText().toString();
        String chietkhau = edchietkhau.getText().toString();

        mavtgtc=atmavtdhtdv.getText().toString();
        mavtgtc=mavtgtc.substring(0,7);
        String km="0";
        giatbtt="0";

        if(chkm.isChecked()) {
            km="1";
            giatbtt="1";
            makm="P28";
            giagoc="0";

            dongia="0";
            thanhtien="0";
            thanhtiengn="0";
        }

        ctkm=atctkmdhtdv.getText().toString();

        String qty = edsoluong.getText().toString();
        //String qty = soluong;
        String gg    = giagoc;
        String price = dongia;
        double ttgtc= Float.parseFloat(thanhtien);



        String tt = thanhtien;
        String ttgn=thanhtiengn;

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
        data15.add(String.valueOf(mathuecodinh));
        data16.add(String.valueOf(chietkhau));

        datattgn.add(String.valueOf(ttgn));

        arrayDonDatHangTDV.add(new DonDatHangTDV(productname,km,ctkm,String.format("%,.0f", Float.valueOf(String.valueOf(giatricode))),qty,eddongia.getText().toString(),chietkhau,edthanhtien.getText().toString()));
        adapterdondathangtdv.notifyDataSetChanged();

        total= tt;
        total1= Float.valueOf(total);



        tvtienhang.setText(String.format("%,.0f",Float.valueOf(0)));
        tvtienhanggn1.setText(String.format("%,.0f",Float.valueOf(0)));
        tvtienhanggn2.setText(String.format("%,.0f",Float.valueOf(0)));

        tvthue.setText(String.format("%,.0f", Float.valueOf(0)));
        tvtongcong.setText(String.format("%,.0f", Float.valueOf(0)));
        tienhang=0;
        tienhangdong=0;

        tienhanggn1=0;
        tienhanggn2=0;


        for(int i=0;i<data.size();i++)
        {
            tienhang = tienhang + Float.parseFloat(data6.get(i));
            tienhangdong = Float.parseFloat(data6.get(i));


        }

        // Thêm tổng tiền nhóm 1 và nhóm 2

        if(mavtgtc.equals("1003653") || mavtgtc.equals("1003651")
                || mavtgtc.equals("1003664") || mavtgtc.equals("1003663")
                || mavtgtc.equals("1003671") || mavtgtc.equals("1003691")
                || mavtgtc.equals("1003708") || mavtgtc.equals("1003640")
        )
        {

            tienhanggn1 = tienhanggn1 + (Float.parseFloat(qty) * Float.parseFloat(gg));
            tienhanggn1_1 = tienhanggn1_1 + tienhanggn1;
        }


        if(mavtgtc.equals("1003689")
                || mavtgtc.equals("1003650") || mavtgtc.equals("1003674")
                || mavtgtc.equals("1003648") || mavtgtc.equals("1003637")
                || mavtgtc.equals("1003645") || mavtgtc.equals("1003681")

                || mavtgtc.equals("1003676") || mavtgtc.equals("1003656")
                || mavtgtc.equals("1003654") || mavtgtc.equals("1003662")
                || mavtgtc.equals("1003661") || mavtgtc.equals("1003690")

                || mavtgtc.equals("1003655") || mavtgtc.equals("1003659")
                || mavtgtc.equals("1003734") || mavtgtc.equals("1003675")
                || mavtgtc.equals("1003701") || mavtgtc.equals("1003668")
        )
        {

            tienhanggn2 = tienhanggn2 + (Float.parseFloat(qty) * Float.parseFloat(gg));


            tienhanggn2_1 = tienhanggn2_1 + tienhanggn2;
        }


        tvtienhanggn1.setText(String.format("%,.0f",Float.valueOf(tienhanggn1_1)));

        tvtienhanggn2.setText(String.format("%,.0f",Float.valueOf(tienhanggn2_1)));

        tongcombo = tienhanggn1_1 + tienhanggn2_1;
        tlgnn1 = (tienhanggn1_1/tongcombo)*100;
        tlgnn2 = (tienhanggn2_1/tongcombo) * 100;

        tvtongcbgn.setText(String.format("%,.0f",Float.valueOf(tongcombo)));

        tvtln1.setText(String.format("%,.2f",Float.valueOf(tlgnn1))+""+"%");
        tvtln2.setText(String.format("%,.2f",Float.valueOf(tlgnn2))+""+"%");

        //datatyletdv.add(String.format("%,.2f", Float.valueOf(tyle))+""+"%");


        tongtienhang=tienhang;
        tvtienhang.setText(String.format("%,.0f",Float.valueOf(tienhang)));
        thue = tienhangdong * Float.parseFloat(thuegtgtcodinh);
        tongtienthue= tongtienthue + thue;
        tvthue.setText(String.format("%,.0f", Float.valueOf(tongtienthue)));
        tongcong = tienhang + tongtienthue;
        tvtongcong.setText(String.format("%,.0f", Float.valueOf(tongcong)));


        atmavtdhtdv.setText("");
        edsoluong.setText("");
        edgiagoc.setText("");
        eddongia.setText("");

        edthanhtien.setText("");
        atmavtdhtdv.requestFocus();
        chkm.setChecked(false);
        atctkmdhtdv.setText("");
        edtenvt.setText("");

        edchietkhau.setText(String.format("%.0f", Float.valueOf(chietkhaucodinh)));

        tonggiatricode+=giatricode;
        giatricode=0.0;
        edcodetl2.setText("");

        phanloaicp="";
        giagoccu=giagoc;
        giagoc="0";

        tonggiatrithuong = tonggiatrithuong + giatrithuong;
        edgiatrithuong.setText(String.format("%,.0f", Double.valueOf(tonggiatrithuong)));

        tonggiatrikm = tonggiatrikm + giatrikm;
        tvtonggtkm.setText(String.format("%,.0f", Double.valueOf(tonggiatrikm)));

    }



    public void selectvtgtc() throws SQLException {
        Connection con = SERVER.Connect();
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            String query = "SELECT Code_KM_1 FROM tbl_KhaiBaoKM_SAP WHERE Ma_Vt=N'"+ mavtgtc +"' ";
            //trên đây là câu truy vấn
            Statement stmt = con.createStatement(); //blah blah blah
            resultSet = stmt.executeQuery(query); //thực thi và trả về một cục ResultSet, nó là gì thì Google, tui chịu

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())
                {
                    codekmle = resultSet.getString("Code_KM_1");

                }
            }


            con.close();
        }
    }
    // Lấy Giá trị Code Khuyến Mãi
    private void selectgiatricode() throws SQLException {


//        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
//        if (con == null) {
//            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
//        } else {
//
//            PreparedStatement statement = con.prepareStatement("EXEC usp_GiaTriCode_Android " +
//                    "'" + madt + "' ,'100000020'," + tiencodekm3 + ","+ tiencodekm5 +","+ tiencodekm6 +"," +
//                    ""+ tiencodekm10 +","+ tiencodekm15 +","+ tiencodekm20 +","+ tiencodekm25 +","+ tiencodekm30 +",'" + ctkm + "',"+ soluongcode +"  ");
//            resultSet = statement.executeQuery();
//
//            for (int i = 0; i <= resultSet.getRow(); i++) {
//                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
//                {
//                    giatricode = Double.valueOf(resultSet.getString("Gia_Tri_Code"));
//                }
//            }
//        }
    }
    // Lấy chiết khấu khuyến mãi
    private void selectckkm() throws SQLException {

        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            try {
                tientruocthue = Float.parseFloat(soluong) * Float.parseFloat(dongia);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }


            PreparedStatement statement = con.prepareStatement("EXEC usp_ChietKhauKM_Android " +
                    "'" + madt + "' ,'"+ mavt +"' ," +
                    "'"+ soluong +"','" + ctkm_ck + "' ," + chietkhaucodinh + ","+ tientruocthue +" ,'"+ machietkhau +"' ");
            resultSet = statement.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    ckkm = Double.valueOf(resultSet.getString("CK_KM"));
                }
            }

            float thanhtiengianguyen=0;
            thanhtiengianguyen=Float.valueOf(soluong)*Float.valueOf(dongia);

            PreparedStatement statement1 = con.prepareStatement("EXEC usp_GiaTriThuong_Android " +
                    "'" + madt + "' ,'"+ mavt +"' ," +
                    "'"+ soluong +"','" + ctkm_ck + "' ," + chietkhaucodinh + ","+ thanhtiengianguyen +" ");
            resultSet = statement1.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    giatrithuong = Double.valueOf(resultSet.getString("GIA_TRI_THUONG"));


                }
            }


            PreparedStatement statement2 = con.prepareStatement("EXEC usp_TongGiaTriKM_Android " +
                    "'" + madt + "' ,'"+ mavt +"' ," +
                    "'"+ soluong +"','" + ctkm_ck + "' ," + chietkhaucodinh + ","+ thanhtiengianguyen +" ");
            resultSet = statement2.executeQuery();

            for (int i = 0; i <= resultSet.getRow(); i++) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    giatrikm = Double.valueOf(resultSet.getString("Gia_Tri_KM"));


                }
            }
        }
    }

    public void tinhkhuyenmai() throws SQLException {
        try {
            Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
            if (con == null) {
                Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
            } else {
                // Lấy tổng tiền hàng chưa tính KM để tính KM
                Double tongcongtienhangKM1 = 0.0;
                Double tongcongtienhangKM2 = 0.0;
                Double tongcongtienhangKM3 = 0.0;
                Double tongcongtienhangKM4 = 0.0;
                Double tongcongtienhangKM5 = 0.0;
                Double tongcongtienhangKM6 = 0.0;
                Double tongcongtienhangKM7 = 0.0;
                Double tongcongtienhangKM8 = 0.0;
                String xmavt = ""; // Lấy mã vật tư để tính KM;
                int checkkm = 0;
                String makm1 = "";  // KM_5
                String makm2 = ""; // KM_3
                String makm3 = ""; // KM_6
                String makm4 = ""; // KM_10
                String makm5 = ""; // KM_15
                String makm6 = ""; // KM_20
                String makm7 = ""; // KM_25
                String makm8 = ""; // KM_25



                String tenvt = "";
                String km = "";
                String ctkm = "";
                Double soluong = 0.0;
                Double tienhangkm = 0.0;
                Double thue = 0.0;
                Double tongcong = 0.0;
                Double ckkm1=0.0;
                Double ckkm2=0.0;
                Double ckkm3=0.0;
                Double ckkm4=0.0;
                Double ckkm5=0.0;
                Double ckkm6=0.0;
                Double ckkm7=0.0;
                Double ckkm8=0.0;
                for (int i = 0; i < data.size(); i++) {
                    // Tìm mã vật tư có áp dụng KM hay ko ?
                    xmavt = data.get(i).substring(0, 7);
                    xmavt = xmavt.trim();

                    PreparedStatement statement1 = con.prepareStatement("EXEC usp_CheckKM_Android " +
                            "'" + xmavt + "' ");

                    resultSet = statement1.executeQuery();
                    for (int j = 0; j <= resultSet.getRow(); j++) {
                        if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                        {
                            checkkm = Integer.parseInt((resultSet.getString("Ma_Km1")));

                            if (data2.get(i).equals("GG_DX21_VIP") && checkkm == 1) {
                                tongcongtienhangKM1 = tongcongtienhangKM1 + Double.parseDouble(data6.get(i));

                                makm1 = data2.get(i);
                            }
                            if (data2.get(i).equals("GG_DX21_VIP") && checkkm == 2) {
                                tongcongtienhangKM2 = tongcongtienhangKM2 + Double.parseDouble(data6.get(i));

                                makm2 = data2.get(i);
                            }

                            if (data2.get(i).equals("GG_DX21_VIP") && checkkm == 3) {
                                tongcongtienhangKM3 = tongcongtienhangKM3 + Double.parseDouble(data6.get(i));

                                makm3 = data2.get(i);
                            }

                            if (data2.get(i).equals("KM_10") && checkkm == 4) {
                                tongcongtienhangKM4 = tongcongtienhangKM4 + Double.parseDouble(data6.get(i));

                                makm4 = data2.get(i);
                            }

                            if (data2.get(i).equals("KM_15") && checkkm == 5) {
                                tongcongtienhangKM5 = tongcongtienhangKM5 + Double.parseDouble(data6.get(i));

                                makm5 = data2.get(i);
                            }

                            if (data2.get(i).equals("KM_20") && checkkm == 6) {
                                tongcongtienhangKM6 = tongcongtienhangKM6 + Double.parseDouble(data6.get(i));

                                makm6 = data2.get(i);
                            }

                            if (data2.get(i).equals("KM_25") && checkkm == 7) {
                                tongcongtienhangKM7 = tongcongtienhangKM7 + Double.parseDouble(data6.get(i));

                                makm7 = data2.get(i);
                            }

                            if (data2.get(i).equals("KM_30") && checkkm == 8) {
                                tongcongtienhangKM8 = tongcongtienhangKM8 + Double.parseDouble(data6.get(i));

                                makm8 = data2.get(i);
                            }

                        }
                    }
                }

                PreparedStatement statement2 = con.prepareStatement("EXEC usp_TinhKM_Android" +
                        "'" + madt + "'," + chietkhaucodinh + ",'" + makm1 + "','" + makm2 + "','" + makm3 + "','" + makm4 + "','" + makm5 + "','" + makm6 + "','" + makm7 + "','" + makm8 + "'," + tongcongtienhangKM1 + "," + tongcongtienhangKM2 + "," + tongcongtienhangKM3 + "," + tongcongtienhangKM4 + "," + tongcongtienhangKM5 + "," + tongcongtienhangKM6 + "," + tongcongtienhangKM7 + "," + tongcongtienhangKM8 + " ");

                resultSet = statement2.executeQuery();

                for (int j = 0; j <= resultSet.getRow(); j++) {
                    if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                    {
                        ckkm1 = Double.valueOf(resultSet.getString("KM1"));
                        ckkm2 = Double.valueOf(resultSet.getString("KM2"));
                        ckkm3 = Double.valueOf(resultSet.getString("KM3"));
                        ckkm4 = Double.valueOf(resultSet.getString("KM4"));
                        ckkm5 = Double.valueOf(resultSet.getString("KM5"));
                        ckkm6 = Double.valueOf(resultSet.getString("KM6"));
                        ckkm7 = Double.valueOf(resultSet.getString("KM7"));
                        ckkm8 = Double.valueOf(resultSet.getString("KM8"));
                    }
                }

                for (int k = 0; k < data.size(); k++) {

                    if (data2.get(k).equals("GG_DX21_VIP") && ckkm1 > 0) {

                        data16.set(k, String.valueOf(ckkm1));
                        chietkhau = data16.get(k);
                        tenvt = data.get(k);
                        km = data1.get(k);
                        ctkm = data2.get(k);
                        soluong = Double.valueOf(data4.get(k));

                        Double giagoc = Double.valueOf(data7.get(k));
                        Double giakm = (giagoc * ckkm1) / 100;
                        Double gia = giagoc - giakm;
                        Double tt = soluong * gia;
                        tienhangkm = tienhangkm + tt;
                        //arrayDonDatHangTDV.add(new DonDatHangTDV(tenvt, km, ctkm, String.format("%,.0f", Float.valueOf(String.valueOf(giatricode))), String.format("%,.0f", Double.valueOf(String.valueOf(soluong))), String.format("%,.0f", Double.valueOf(String.valueOf(gia))), chietkhau, String.format("%,.0f", Double.valueOf(String.valueOf(tt)))));

                        arrayDonDatHangTDV.set(k, new DonDatHangTDV(tenvt, km, ctkm, String.format("%,.0f", Float.valueOf(String.valueOf(giatricode))), String.format("%,.0f", Double.valueOf(String.valueOf(soluong))), String.format("%,.0f", Double.valueOf(String.valueOf(gia))), chietkhau, String.format("%,.0f", Double.valueOf(String.valueOf(tt)))));

                        adapterdondathangtdv.notifyDataSetChanged();
                    }


                        if(data2.get(k).equals("GG_DX21_VIP") && ckkm2 > 0)
                        {
                            data16.set(k, String.valueOf(ckkm2));
                            chietkhau = data16.get(k);
                            tenvt = data.get(k);
                            km = data1.get(k);
                            ctkm = data2.get(k);
                            soluong = Double.valueOf(data4.get(k));

                            Double giagoc = Double.valueOf(data7.get(k));
                            Double giakm = (giagoc * ckkm2) / 100;
                            Double gia = giagoc - giakm;
                            Double tt = soluong * gia;
                            tienhangkm = tienhangkm + tt;
                            //arrayDonDatHangTDV.add(new DonDatHangTDV(tenvt, km, ctkm, String.format("%,.0f", Float.valueOf(String.valueOf(giatricode))), String.format("%,.0f", Double.valueOf(String.valueOf(soluong))), String.format("%,.0f", Double.valueOf(String.valueOf(gia))), chietkhau, String.format("%,.0f", Double.valueOf(String.valueOf(tt)))));

                            arrayDonDatHangTDV.set(k, new DonDatHangTDV(tenvt, km, ctkm, String.format("%,.0f", Float.valueOf(String.valueOf(giatricode))), String.format("%,.0f", Double.valueOf(String.valueOf(soluong))), String.format("%,.0f", Double.valueOf(String.valueOf(gia))), chietkhau, String.format("%,.0f", Double.valueOf(String.valueOf(tt)))));

                            adapterdondathangtdv.notifyDataSetChanged();
                        }
                    if(data2.get(k).equals("GG_DX21_VIP") && ckkm3 > 0)
                    {
                        data16.set(k, String.valueOf(ckkm3));
                        chietkhau = data16.get(k);
                        tenvt = data.get(k);
                        km = data1.get(k);
                        ctkm = data2.get(k);
                        soluong = Double.valueOf(data4.get(k));

                        Double giagoc = Double.valueOf(data7.get(k));
                        Double giakm = (giagoc * ckkm3) / 100;
                        Double gia = giagoc - giakm;
                        Double tt = soluong * gia;
                        tienhangkm = tienhangkm + tt;


                        arrayDonDatHangTDV.set(k, new DonDatHangTDV(tenvt, km, ctkm, String.format("%,.0f", Float.valueOf(String.valueOf(giatricode))), String.format("%,.0f", Double.valueOf(String.valueOf(soluong))), String.format("%,.0f", Double.valueOf(String.valueOf(gia))), chietkhau, String.format("%,.0f", Double.valueOf(String.valueOf(tt)))));

                        adapterdondathangtdv.notifyDataSetChanged();
                    }

                    if(data2.get(k).equals("KM_10"))
                    {
                        data16.set(k, String.valueOf(ckkm4));
                        chietkhau = data16.get(k);
                        tenvt = data.get(k);
                        km = data1.get(k);
                        ctkm = data2.get(k);
                        soluong = Double.valueOf(data4.get(k));

                        Double giagoc = Double.valueOf(data7.get(k));
                        Double giakm = (giagoc * ckkm4) / 100;
                        Double gia = giagoc - giakm;
                        Double tt = soluong * gia;
                        tienhangkm = tienhangkm + tt;


                        arrayDonDatHangTDV.set(k, new DonDatHangTDV(tenvt, km, ctkm, String.format("%,.0f", Float.valueOf(String.valueOf(giatricode))), String.format("%,.0f", Double.valueOf(String.valueOf(soluong))), String.format("%,.0f", Double.valueOf(String.valueOf(gia))), chietkhau, String.format("%,.0f", Double.valueOf(String.valueOf(tt)))));

                        adapterdondathangtdv.notifyDataSetChanged();
                    }

                    if(data2.get(k).equals("KM_15"))
                    {
                        data16.set(k, String.valueOf(ckkm5));
                        chietkhau = data16.get(k);
                        tenvt = data.get(k);
                        km = data1.get(k);
                        ctkm = data2.get(k);
                        soluong = Double.valueOf(data4.get(k));

                        Double giagoc = Double.valueOf(data7.get(k));
                        Double giakm = (giagoc * ckkm5) / 100;
                        Double gia = giagoc - giakm;
                        Double tt = soluong * gia;
                        tienhangkm = tienhangkm + tt;


                        arrayDonDatHangTDV.set(k, new DonDatHangTDV(tenvt, km, ctkm, String.format("%,.0f", Float.valueOf(String.valueOf(giatricode))), String.format("%,.0f", Double.valueOf(String.valueOf(soluong))), String.format("%,.0f", Double.valueOf(String.valueOf(gia))), chietkhau, String.format("%,.0f", Double.valueOf(String.valueOf(tt)))));

                        adapterdondathangtdv.notifyDataSetChanged();
                    }

                    if(data2.get(k).equals("KM_20"))
                    {
                        data16.set(k, String.valueOf(ckkm6));
                        chietkhau = data16.get(k);
                        tenvt = data.get(k);
                        km = data1.get(k);
                        ctkm = data2.get(k);
                        soluong = Double.valueOf(data4.get(k));

                        Double giagoc = Double.valueOf(data7.get(k));
                        Double giakm = (giagoc * ckkm6) / 100;
                        Double gia = giagoc - giakm;
                        Double tt = soluong * gia;
                        tienhangkm = tienhangkm + tt;


                        arrayDonDatHangTDV.set(k, new DonDatHangTDV(tenvt, km, ctkm, String.format("%,.0f", Float.valueOf(String.valueOf(giatricode))), String.format("%,.0f", Double.valueOf(String.valueOf(soluong))), String.format("%,.0f", Double.valueOf(String.valueOf(gia))), chietkhau, String.format("%,.0f", Double.valueOf(String.valueOf(tt)))));

                        adapterdondathangtdv.notifyDataSetChanged();
                    }

                    if(data2.get(k).equals("KM_25"))
                    {
                        data16.set(k, String.valueOf(ckkm7));
                        chietkhau = data16.get(k);
                        tenvt = data.get(k);
                        km = data1.get(k);
                        ctkm = data2.get(k);
                        soluong = Double.valueOf(data4.get(k));

                        Double giagoc = Double.valueOf(data7.get(k));
                        Double giakm = (giagoc * ckkm7) / 100;
                        Double gia = giagoc - giakm;
                        Double tt = soluong * gia;
                        tienhangkm = tienhangkm + tt;


                        arrayDonDatHangTDV.set(k, new DonDatHangTDV(tenvt, km, ctkm, String.format("%,.0f", Float.valueOf(String.valueOf(giatricode))), String.format("%,.0f", Double.valueOf(String.valueOf(soluong))), String.format("%,.0f", Double.valueOf(String.valueOf(gia))), chietkhau, String.format("%,.0f", Double.valueOf(String.valueOf(tt)))));

                        adapterdondathangtdv.notifyDataSetChanged();
                    }

                    if(data2.get(k).equals("KM_30"))
                    {
                        data16.set(k, String.valueOf(ckkm8));
                        chietkhau = data16.get(k);
                        tenvt = data.get(k);
                        km = data1.get(k);
                        ctkm = data2.get(k);
                        soluong = Double.valueOf(data4.get(k));

                        Double giagoc = Double.valueOf(data7.get(k));
                        Double giakm = (giagoc * ckkm8) / 100;
                        Double gia = giagoc - giakm;
                        Double tt = soluong * gia;
                        tienhangkm = tienhangkm + tt;


                        arrayDonDatHangTDV.set(k, new DonDatHangTDV(tenvt, km, ctkm, String.format("%,.0f", Float.valueOf(String.valueOf(giatricode))), String.format("%,.0f", Double.valueOf(String.valueOf(soluong))), String.format("%,.0f", Double.valueOf(String.valueOf(gia))), chietkhau, String.format("%,.0f", Double.valueOf(String.valueOf(tt)))));

                        adapterdondathangtdv.notifyDataSetChanged();
                    }


                }




                tvtienhang.setText(String.format("%,.0f", Double.valueOf(String.valueOf(tienhangkm))));
                thue = tienhangkm * Float.parseFloat(thuegtgtcodinh);
                tvthue.setText(String.format("%,.0f", Double.valueOf(thue)));
                tongcong = tienhangkm + thue;
                tvtongcong.setText(String.format("%,.0f", Double.valueOf(tongcong)));

                chietkhau = chietkhaucodinh;

            }


            //arrayDonDatHangTDV.clear();

        }

        catch (SQLException e) {
            e.printStackTrace();
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
                noidung=ednoidung.getText().toString();
                diengiai=eddgdhtdv.getText().toString();
                if(diachi.contains("'")){
                    diachi = diachi.replace("'","''");
                }
                String madt = atmadtdhtdv.getText().toString();
                if (madt.equals("")) {
                    madt = "";
                } if(dvcs.equals("A02")) {
                    madt = atmadtdhtdv.getText().toString().substring(0, 7);
                }
                else {
                    madt = atmadtdhtdv.getText().toString().substring(0, 7);
                }
                madt=madt.trim();

                String banggia = bgid;

                String tongtien = String.valueOf(tongcong);
                String tongcongtienhang = String.valueOf(tongtienhang);
                String thue = String.valueOf(tongtienthue);



                PreparedStatement statement1 = con.prepareStatement("EXEC usp_Inserttblhoadon_SAP " +
                        "'"+ dvcs +"','"+ madt +"',N'"+ noidung +"',"+ tongtien +" ");
                resultSet = statement1.executeQuery();
                //statement.executeUpdate();

                for (int i = 0; i <= resultSet.getRow(); i++) {
                    if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                    {
                        Stt = resultSet.getString("Stt");
                    }
                }


                for (int i = 0; i < data.size(); i++) {
                    String mavt = data.get(i);
                    mavt=mavt.substring(0,7);

                    String khuyenmai=data1.get(i);
                    String khuyenmaiLMH=data2.get(i);

                    String giatricodeTL=data3.get(i);

                    String soluong = data4.get(i);
                    String gia = data5.get(i);
                    String thanhtien = data6.get(i);
                    String giagoc1=data7.get(i);

                    String Phan_Loai_CP = data12.get(i);
                    if(khuyenmai.equals("1")) {
                        Phan_Loai_CP=khuyenmaiLMH;
                    }
                    //String don_vi_tinh=data13.get(i);
                    String ma_km=data14.get(i);
                    String ma_thue=data15.get(i);
                    String chiet_khau = data16.get(i);

                    giagoc1 = giagoc1.replace(",", "");
                    gia = gia.replace(",", "");
                    thanhtien = thanhtien.replace(",", "");
                    double thanhtien1 = Math.round(Float.parseFloat(thanhtien)); // làm mất giá bị lẻ

                    thue = thue.replace(",", "");
                    giatricodeTL=giatricodeTL.replace(",","");

                    Float thuedong;
                    thuedong= Float.valueOf(thanhtien) * Float.valueOf(thuegtgtcodinh);
                    int builinorder=i+1;

                    PreparedStatement statement = con.prepareStatement("EXEC usp_InserttblhoadonCt_SAP " +
                            "'"+ dvcs +"','"+ Stt +"','"+ mavt +"',"+ soluong +","+khuyenmai+ ","+ gia +",'"+ khuyenmaiLMH +"',"+ chiet_khau +","+ giatricodeTL +" ");
                    //resultSet = statement.executeQuery();
                    statement.executeUpdate();

                }

                PreparedStatement statement = con.prepareStatement("EXEC usp_InsertSaleOrder1_SAP " +
                        "'"+ dvcs +"','"+ ngayct +"','"+ Stt +"',N'"+ madt +"',N'"+ paymentId1 +"'");

                statement.executeUpdate();

                Toast.makeText(activity_dondathangtdv.this, "Đã lưu Thành Công !", Toast.LENGTH_SHORT).show();
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

            String query = "SELECT MAX(Id),(MAX(Id)+1) AS IdM FROM tbl_HoaDon_SAP WHERE Ma_Dvcs='"+ dvcs +"'";
            //trên đây là câu truy vấn
            Statement stmt = con.createStatement(); //blah blah blah
            resultSet = stmt.executeQuery(query); //thực thi và trả về một cục ResultSet, nó là gì thì Google, tui chịu

            for(int i = 0; i<=resultSet.getRow();i++ )
            {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    Id = resultSet.getString("IdM");

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
                    if(Id.length()==6)
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
            soseri="CM/21E";
        }
        if(dvcs.equals("A03"))
        {
            dvcs1="2N301";
            makho="TP2N3-01";
            soseri="1C22TCT";

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
            soseri="1C22TMD";
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


    public void DialogxoaDDH(final int vitri, final String mavtxoa){
        AlertDialog.Builder dialogXoa= new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa dòng sản phẩm '"+ mavtxoa.substring(0,9) +"' này không ?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                arrayDonDatHangTDV.remove(vitri);

                adapterdondathangtdv.notifyDataSetChanged();

                data.remove(vitri);
                data1.remove(vitri);
                data2.remove(vitri);
                data3.remove(vitri);
                data4.remove(vitri);
                data5.remove(vitri);
                data6.remove(vitri);
                data7.remove(vitri);
                data8.remove(vitri);
                data9.remove(vitri);
                data10.remove(vitri);
                data11.remove(vitri);
                data12.remove(vitri);
                data13.remove(vitri);
                data14.remove(vitri);
                data15.remove(vitri);
                data16.remove(vitri);
                tienhangxoa=0;
                for (int i = 0; i < data.size(); i++) {

                    total = data6.get(i);
                    total1= Float.valueOf(total);
                    String ggg=data7.get(i);

                    tienhangxoa = tienhangxoa + total1;

                    tongtienhang=tienhangxoa;
                    tvtienhang.setText(String.format("%,.0f",Float.valueOf(tienhangxoa)));
                    thue = tienhangxoa * Float.parseFloat(thuegtgtcodinh);
                    tongtienthue=thue;
                    tvthue.setText(String.format("%,.0f", Float.valueOf(thue)));
                    tongcong = tienhangxoa + thue;
                    tvtongcong.setText(String.format("%,.0f", Float.valueOf(tongcong)));

                    tienhang=tienhangxoa;



                }
                edgiatrithuong.setText(String.format("%,.0f",Float.valueOf(0)));

            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();

    }


    public void DialogsuaDDH(final int vitri, final String mavtcu,final String kmcu,final String ctkmcu,final String gtccu,final  String soluongcu,final String dgcu,final String ttcu){


        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_suasoluongdonhang);
        final EditText editsuaslddh=(EditText) dialog.findViewById(R.id.edsuasldhtdv);
        Button btsuaslddh=(Button) dialog.findViewById(R.id.btcnsldhtdv);
        Button buttonthoatslddh=(Button) dialog.findViewById(R.id.btthoatsldhtdv);

        editsuaslddh.setText(soluongcu);

        btsuaslddh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vitridong=vitri;
                String mavt=mavtcu;
                String mavtgn=mavt.substring(0,7);
                String km=kmcu;
                String ctkm=ctkmcu;
                String dongiacu=dgcu;
                String dgn = data7.get(vitridong);
                String gtc=gtccu;
                String soluongmoi=editsuaslddh.getText().toString().trim();
                Float soluong= Float.valueOf(soluongmoi);
                dongiacu=dongiacu.replace(".","");

                Float dg= Float.valueOf(dongiacu);

                Float ttmoi=soluong*dg;
                String tt= String.valueOf(ttmoi);
                tt=String.format("%,.0f",Float.valueOf(tt));

                data6.set(vitridong, String.valueOf(ttmoi));
                data4.set(vitridong,String.valueOf(soluongmoi));




                String tienhanggn1_Cu = (String) tvtienhanggn1.getText();
                tienhanggn1_Cu=tienhanggn1_Cu.replace(".","");
                String tienhanggn2_Cu = (String) tvtienhanggn2.getText();
                tienhanggn2_Cu=tienhanggn2_Cu.replace(".","");

                    if (mavtgn.equals("1003653") || mavtgn.equals("1003651")
                            || mavtgn.equals("1003664") || mavtgn.equals("1003663")
                            || mavtgn.equals("1003671") || mavtgn.equals("1003691")
                            || mavtgn.equals("1003708")
                    ) {

                        Float ttgncu1 = Float.valueOf(soluongcu) * Float.valueOf(dgn);
                        Float ttgnmoi1= soluong * Float.valueOf(dgn);// mới thêm
                        Float ttgntg1= ttgnmoi1 - ttgncu1;


                        float tienhanggn1_moi=0;
                        float tienhanggn1_1_moi=0;


                        tienhanggn1_moi = Float.parseFloat(tienhanggn1_Cu) + ttgntg1;

                        tvtienhanggn1.setText(String.format("%,.0f",Float.valueOf(tienhanggn1_moi)));

                    }


                    if (mavtgn.equals("1003689") || mavtgn.equals("1003640")
                            || mavtgn.equals("1003650") || mavtgn.equals("1003674")
                            || mavtgn.equals("1003648") || mavtgn.equals("1003637")
                            || mavtgn.equals("1003645") || mavtgn.equals("1003681")

                            || mavtgn.equals("1003676") || mavtgn.equals("1003656")
                            || mavtgn.equals("1003654") || mavtgn.equals("1003662")
                            || mavtgn.equals("1003661") || mavtgn.equals("1003690")

                            || mavtgn.equals("1003655") || mavtgn.equals("1003659")
                            || mavtgn.equals("1003734") || mavtgn.equals("1003675")
                            || mavtgn.equals("1003701") || mavtgn.equals("1003668")
                    ) {


                        Float ttgncu2 = Float.valueOf(soluongcu) * Float.valueOf(dgn);
                        Float ttgnmoi2= soluong * Float.valueOf(dgn);// mới thêm
                        Float ttgntg2= ttgnmoi2 - ttgncu2;


                        float tienhanggn2_moi=0;
                        float tienhanggn2_1_moi=0;


                        tienhanggn2_moi = Float.parseFloat(tienhanggn2_Cu) + ttgntg2;

                        tvtienhanggn2.setText(String.format("%,.0f",Float.valueOf(tienhanggn2_moi)));
                    }

                String tienhanggn1_1_moi= tvtienhanggn1.getText().toString();
                tienhanggn1_1_moi=tienhanggn1_1_moi.replace(".","");

                String tienhanggn2_1_moi= tvtienhanggn2.getText().toString();
                tienhanggn2_1_moi=tienhanggn2_1_moi.replace(".","");

                float tongcombo_moi=0;
                float tlgnn1_moi=0;
                float tlgnn2_moi=0;

                tongcombo_moi = Float.parseFloat(tienhanggn1_1_moi) + Float.parseFloat(tienhanggn2_1_moi);
                tlgnn1_moi = (Float.valueOf(tienhanggn1_1_moi)/tongcombo_moi)*100;
                tlgnn2_moi = (Float.valueOf(tienhanggn2_1_moi)/tongcombo_moi) * 100;

                tvtongcbgn.setText(String.format("%,.0f",Float.valueOf(tongcombo_moi)));

                tvtln1.setText(String.format("%,.2f",Float.valueOf(tlgnn1_moi))+""+"%");
                tvtln2.setText(String.format("%,.2f",Float.valueOf(tlgnn2_moi))+""+"%");



                arrayDonDatHangTDV.set(vitridong, new DonDatHangTDV(mavt,km,ctkm,gtc,soluongmoi,dgcu,chietkhau,tt));
                adapterdondathangtdv.notifyDataSetChanged();
                float tienhangsua=0;
                float tongtienhangsua=0;
                String totalsua="0";
                float total1sua=0;
                float thuesua=0;
                float tongtienthuesua=0;
                float tongcongsua=0;

                for(int i=0;i<data.size();i++)
                {
                      totalsua=data6.get(i);
                      total1sua=Float.valueOf(totalsua);
                      tienhangsua=tienhangsua+total1sua;
                      tongtienhangsua=tienhangsua;
                      tvtienhang.setText(String.format("%,.0f",Float.valueOf(tienhangsua)));

                      thuesua = tienhangsua * Float.parseFloat(thuegtgt);
                      tongtienthuesua=thuesua;
                      tvthue.setText(String.format("%,.0f", Float.valueOf(thuesua)));
                      tongcongsua = tienhangsua + thuesua;
                      tvtongcong.setText(String.format("%,.0f", Float.valueOf(tongcongsua)));

                }

                dialog.dismiss();

                edgiatrithuong.setText(String.format("%,.0f",Float.valueOf(0)));

            }
        });
        dialog.show();


    }

}