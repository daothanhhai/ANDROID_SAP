package com.example.erp_tuyen.loginsap;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class activity_baocaothaucn extends AppCompatActivity {
    ExpandableListView lvbctcn;
    private ExpandableListView expandableListView;
    private List<BaoCaoThauCNGroup> mListGroup;
    private Map<BaoCaoThauCNGroup, List<BaoCaoThauCN>> mListItem;
    private BaoCaoThauCNAdapter expandableListViewAdapter;




    Button btbctcn;
    EditText edittextungaybctcn;
    EditText edittextdenngaybctcn;
    ImageView imgbctcn;
    AutoCompleteTextView atcbctcn;

    ArrayList<BaoCaoThauCN> arraybaocaothaucn;
    ArrayList<BaoCaoThauCNGroup> arraybaocaothaucngroup;



    ArrayList<String> arraymadt = new ArrayList<String>();

    BaoCaoThauCNAdapter adapter;

    ResultSet resultSet;
    int flag = 0;
    public static String id,dvcs,macbnv,chucdanh,username,chietkhau;
    String madt, tendt, makh;
    String bizdocid,sohopdongthau,ngayhopdongthau,madtthau,tendtthau,mavtthau,tenvatthau,dvtthau,soluongttthau,dongiathau,thanhtienthau,soluongdgthau,soluongqdthau;
    SearchView svbctcn;
    private ProgressDialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baocaothaucn);

        Intent intent=getIntent();
        id=intent.getStringExtra("IdUser");
        dvcs=intent.getStringExtra("Ma_Dvcs");
        macbnv=intent.getStringExtra("Ma_CbNv");
        chucdanh=intent.getStringExtra("Chuc_Danh");
        username=intent.getStringExtra("USERNAME");

        expandableListView=findViewById(R.id.ExpandableListView);

        btbctcn=(Button) findViewById(R.id.buttonbctcn);
        edittextungaybctcn=(EditText) findViewById(R.id.edittexttnbctcn);
        edittextdenngaybctcn=(EditText) findViewById(R.id.edittextdnbctcn);
        imgbctcn=(ImageView) findViewById(R.id.imagebctcn);
        atcbctcn=(AutoCompleteTextView) findViewById(R.id.actbctcn);

        ViewGroup headerview=(ViewGroup) getLayoutInflater().inflate(R.layout.header_baocaothaucn,expandableListView,false);
        expandableListView.addHeaderView(headerview);


        arraybaocaothaucn=new ArrayList<>();
        arraybaocaothaucngroup=new ArrayList<>();


        String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        edittextungaybctcn.setText(date_n);
        edittextdenngaybctcn.setText(date_n);



        edittextungaybctcn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 0;
                chonngay();
            }
        });

        edittextdenngaybctcn.setOnClickListener(new View.OnClickListener() {
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
        atcbctcn.setAdapter(adapter1);

        imgbctcn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atcbctcn.showDropDown();
            }
        });

        atcbctcn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                madt = atcbctcn.getText().toString();

                try {
                    selectdoituong();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        btbctcn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if(atcbctcn.getText().toString().equals("")){
                    Toast.makeText(activity_baocaothaucn.this, "Đối tượng không được bỏ trống !", Toast.LENGTH_SHORT).show();
                }
                
                else {
                    LoadingBar = new ProgressDialog(activity_baocaothaucn.this);
                    LoadingBar.setTitle("OPC - MOBILE");
                    LoadingBar.setMessage("Đang tải dữ liệu.... usp_B30BizDoc_BaoCaoThauCN");
                    LoadingBar.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                selectdata();

                                mListItem=getmListItem();

                                mListGroup=new ArrayList<>(mListItem.keySet());

                                expandableListViewAdapter=new BaoCaoThauCNAdapter(mListGroup,mListItem);

                                expandableListView.setAdapter(expandableListViewAdapter);

                                //lvbctcn.clearFocus();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            LoadingBar.dismiss();
                        }
                    }, 50);
                }
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
                    edittextungaybctcn.setText(simpleDateFormat.format(calendar.getTime()));
                }
                if (flag == 1) {
                    edittextdenngaybctcn.setText(simpleDateFormat.format(calendar.getTime()));
                }
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }


    private Map<BaoCaoThauCNGroup,List<BaoCaoThauCN>> getmListItem(){
        Map<BaoCaoThauCNGroup, List<BaoCaoThauCN>> listMap=new HashMap<>();
        BaoCaoThauCNGroup groupObject=new BaoCaoThauCNGroup("","","","","");
        //List<BaoCaoThauCNGroup> groupObject=new ArrayList<>();
        List<BaoCaoThauCN> objectList=new ArrayList<>();

        for(int i=0;i<arraybaocaothaucngroup.size();i++) {
            String makhgroup = arraybaocaothaucngroup.get(i).getMakh();
            String tenkhgroup = arraybaocaothaucngroup.get(i).getTenkh();
            String sohdgroup = arraybaocaothaucngroup.get(i).getSohd();
            String ngayhdgroup = arraybaocaothaucngroup.get(i).getNgayhd();
            bizdocid=arraybaocaothaucngroup.get(i).getBizdocid();
            groupObject = new BaoCaoThauCNGroup(makhgroup, tenkhgroup, sohdgroup, ngayhdgroup,bizdocid);

            try {
                selectdatadetail();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            objectList=new ArrayList<>();

            for (int j = 0; j < arraybaocaothaucn.size(); j++) {

                String mavtgroup = arraybaocaothaucn.get(j).getMavt();
                String tenvtgroup = arraybaocaothaucn.get(j).getTenvt();
                String dvtgroup = arraybaocaothaucn.get(j).getDvt();
                String slttgroup = arraybaocaothaucn.get(j).getSltt();
                String dggroup = arraybaocaothaucn.get(j).getDgtt();
                String ttgroup = arraybaocaothaucn.get(j).getTttt();
                String sldggroup = arraybaocaothaucn.get(j).getSldg();
                String slqdgroup = arraybaocaothaucn.get(j).getSlqd();

                objectList.add(new BaoCaoThauCN(mavtgroup, tenvtgroup, dvtgroup, slttgroup, dggroup, ttgroup, sldggroup, slqdgroup));
            }
            listMap.put(groupObject,objectList);
        }

        //listMap.put(groupObject,objectList);
        return listMap;
    }

    public void selectdata() throws SQLException {

        String tungay=edittextungaybctcn.getText().toString();
        String denngay=edittextdenngaybctcn.getText().toString();

        makh = atcbctcn.getText().toString();
        if (makh.equals("")) {
            makh = "";

        }
        else {

            if (dvcs.equals("A02")) {
                makh = atcbctcn.getText().toString().substring(0, 16);
            } else {
                makh = atcbctcn.getText().toString().substring(0, 13);
            }
        }

        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
            PreparedStatement statement = con.prepareStatement("EXEC usp_B30BizDoc_BaoCaoThauCN " +
                    "'"+ tungay +"','"+ denngay +"','"+ username +"','"+ dvcs +"' " +
                    ",'"+ makh +"','VND','','' ");
            resultSet = statement.executeQuery();
            //arraybaocaothaucn.clear();
            arraybaocaothaucngroup.clear();

            for(int i = 0; i<=resultSet.getRow();i++ )
            {
                if (resultSet.next())
                {
                    madtthau = resultSet.getString("Ma_Dt");
                    tendtthau = resultSet.getString("Ten_Dt");
                    sohopdongthau = resultSet.getString("So_Hop_Dong");
                    ngayhopdongthau = resultSet.getString("Ngay_Hop_Dong");
                    bizdocid = resultSet.getString("BizDocid");

                    arraybaocaothaucngroup.add(new BaoCaoThauCNGroup(madtthau, tendtthau,sohopdongthau,ngayhopdongthau,bizdocid));
                }

            }

            //expandableListViewAdapter.notifyDataSetChanged();
            con.close();
        }
    }

    public void selectdatadetail() throws SQLException {

        String tungay=edittextungaybctcn.getText().toString();
        String denngay=edittextdenngaybctcn.getText().toString();
        makh = atcbctcn.getText().toString();
        if (makh.equals("")) {
            makh = "";

        } if(dvcs.equals("A02")) {
            makh = atcbctcn.getText().toString().substring(0, 16);
        }
        else {
            makh = atcbctcn.getText().toString().substring(0, 13);
        }

        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
            PreparedStatement statement = con.prepareStatement("EXEC usp_B30BizDoc_BaoCaoThauCN " +
                    "'"+ tungay +"','"+ denngay +"','"+ username +"','"+ dvcs +"' " +
                    ",'"+ makh +"','VND','','"+ bizdocid +"' ");
            resultSet = statement.executeQuery();
            arraybaocaothaucn.clear();
            //arraybaocaothaucngroup.clear();

            for(int i = 0; i<=resultSet.getRow();i++ )
            {
                if (resultSet.next())
                {
                    bizdocid = resultSet.getString("BizDocid");
//
                    mavtthau = resultSet.getString("Ma_Vt");
                    tenvatthau = resultSet.getString("Ten_Vt");
                    dvtthau = resultSet.getString("Dvt");
                    soluongttthau = resultSet.getString("So_Luong_Thau");
                    dongiathau    = resultSet.getString("Gia_Thau");
                    thanhtienthau = resultSet.getString("Tien_Thau");
                    soluongdgthau = resultSet.getString("So_Luong_Da_Giao");
                    soluongqdthau = resultSet.getString("So_Luong_Qui_Doi");

                    arraybaocaothaucn.add(new BaoCaoThauCN(mavtthau, tenvatthau,dvtthau,soluongttthau,dongiathau,thanhtienthau,soluongdgthau,soluongqdthau));


                }


            }

            //expandableListViewAdapter.notifyDataSetChanged();
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


}