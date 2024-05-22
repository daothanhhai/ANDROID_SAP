package com.example.erp_tuyen.loginsap;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Gravity;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.common.api.GoogleApiClient;


public class MainActivity extends Activity {

    EditText edtUserName, edtPaddword;
    AutoCompleteTextView atdvcs;
    ImageView imdvcs;
    Button btnLogin,btnthoat;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean savelogin;
    CheckBox savelogincheckbox;


    ArrayList<SinhVien> arraySinhVien;

    ArrayList<String> ArrDvCs = new ArrayList<>();

    SinhVienAdapter adapter;

    ProgressBar pgbLoading;

    TextView tvResult;
    ResultSet resultSet;

    Dialog myDialog;
    RelativeLayout layout;
    final int CHAN_PW_CODE=4; //Code dùng để gửi gói Intent qua Activity_User
    public String Iduser,username,hoten,macbnv,chucdanh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.main_layout);//popup
        myDialog = new Dialog(this);

        btnLogin=(Button)this.findViewById(R.id.btnLogin);
        btnthoat =(Button)this.findViewById(R.id.btnThoat);
        edtUserName=(EditText)this.findViewById(R.id.edtUserName);
        edtPaddword=(EditText)this.findViewById(R.id.edtPassword);
        pgbLoading=(ProgressBar)this.findViewById(R.id.pgbLoading);
        tvResult=(TextView)this.findViewById(R.id.tvResult);

        atdvcs=(AutoCompleteTextView) findViewById(R.id.autoviewdvcs);
        imdvcs=(ImageView) findViewById(R.id.imagedvcs);


        sharedPreferences=getSharedPreferences("loginref",MODE_PRIVATE);
        savelogincheckbox=(CheckBox) findViewById(R.id.cbrmbpass);
        editor=sharedPreferences.edit();


        ArrDvCs.add("A01");
        ArrDvCs.add("A02");
        ArrDvCs.add("A03");
        ArrDvCs.add("A04");
        ArrDvCs.add("A05");
        ArrDvCs.add("A06");
        ArrDvCs.add("A07");
        ArrDvCs.add("A08");
        ArrDvCs.add("A09");
        ArrDvCs.add("A10");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ArrDvCs);
        atdvcs.setAdapter(adapter);

        imdvcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atdvcs.showDropDown();
            }
        });



        pgbLoading.setVisibility(View.GONE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 DoLogin  doLogin = new DoLogin();
                 doLogin.execute("");//Thực thi doLogin.doInBackground();


            }
        });
        savelogin=sharedPreferences.getBoolean("savelogin",true);
        if(savelogin==true){
           edtUserName.setText(sharedPreferences.getString("username",null));
           edtPaddword.setText(sharedPreferences.getString("password",null));

           atdvcs.setText(sharedPreferences.getString("dvcs",null));
        }

        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startActivity(startMain);
                finish();
            }
        });

//        CreatepopupUpwindow();//goi ham view popup



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
                popupWindow.showAtLocation(layout,Gravity.CENTER,0,0);
            }
        });


 }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//Hàm này sẽ xử lý nếu khi mình gửi Intent là startActivityForResult, nhấn F8, bỏ qua cái này, xem xuống dưới trước
        if (requestCode == CHAN_PW_CODE) { //Kiểm tra xem code trả về có đúng ban đầu không này không
            // Phài chắc chắn là thành công mới gửi
            if (resultCode == RESULT_OK) {
                String rs=data.getStringExtra("PASSWORD");
                Toast.makeText(this,"Mật khẩu đã đổi thành "+ rs ,Toast.LENGTH_LONG).show();
                edtPaddword.setText(rs);
                btnLogin.setVisibility(View.VISIBLE);
            }
        }
    }

    //---------------------------------------------------------------------------------------------
    public class DoLogin extends AsyncTask<String,String,String>
    {

        String z = ""; //cái này dùng để hứng kết quả khi chạy hàm truy vấn tới SQL Server
        String hoten=""; //Cái này sẽ chứa họ tên đầy đủ khi truy vấn được
        Boolean isSuccess = false; //Biến nhận biết là có truy vấn thành công hay không

        String userid = edtUserName.getText().toString(); // biến cục bộ, xài cục bộ
        String password = edtPaddword.getText().toString(); // như trên
        String dvcs=atdvcs.getText().toString();



        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
        try {
                Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
                if (con == null) {
                    z = "Không thể kết nối với Server"; //Tiếng Việt :D
                } else {
                //String query = "SELECT * FROM view_user WHERE tendangnhap='" + userid + "' AND matkhau='" + HASH.md5(password) + "'";
                    String query = "SELECT * FROM view_user WHERE tendangnhap='" + userid + "' AND matkhau=N'" + password + "' AND Ma_DvCs=N'"+ dvcs +"' ";
                //trên đây là câu truy vấn
                    Statement stmt = con.createStatement(); //blah blah blah
                    resultSet = stmt.executeQuery(query); //thực thi và trả về một cục ResultSet, nó là gì thì Google, tui chịu

                    //ResultSet rs = SERVER.executeQuery(query);
                    if(resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                    {
                        hoten=resultSet.getString("hoten");//Hàm lấy giá trị của tên cột (trường: field name) truyền vào
                        Iduser=resultSet.getString("Id");
                        username=resultSet.getString("tendangnhap");
                        password=resultSet.getString("matkhau");
                        macbnv=resultSet.getString("Ma_CbNv");
                        chucdanh=resultSet.getString("Chuc_Danh");
                        dvcs=resultSet.getString("Ma_Dvcs");

                        z = "Hi, " + hoten; //Hey, i'm TONA
                        //tvResult.setText(hoten);
                        isSuccess=true; //Oánh dấu chủ quyền, làm dấu thôi, để biết là hàm nó chạy tới đây, xíu mình dùng biến này kiểm tra coi thử chạy tới đây hay không đó mà, chạy tới đây nghĩa là thành công rồi đó.
                        con.close();//Đấm vỡ mồm SERVER xong thì phải băng bó cho nó.
                    }
                    else
                    {
                        z = "Tài khoản không tồn tại !";//Lại Tiếng Việt
                        isSuccess = false; //Chạy tới đây là hỏng rồi
                    }
                }
            }
            catch (Exception ex)
            {
                isSuccess = false;
                z = "Lỗi !";
            }

            if(savelogincheckbox.isChecked()){
                editor.putBoolean("savelogin",true);
                editor.putString("username",userid);
                editor.putString("password",password);
                editor.putString("dvcs",dvcs);
                editor.commit();

            }
//            else {
//                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
//            }

            return z;//Trả về, thứ này chính là cái doInBackground(String... params), nó buộc mình phải trả về String, do tui khai báo là String thôi, nếu ban đầu khai kiểu khác thì nó sẽ bắt trả về kiểu khác, trong bài trước mình trả về null, vì mình chẳng cần bắt gì cả, chỉ chạy thôi.

        }

        @Override
        protected void onProgressUpdate(String... values) {
            //super.onProgressUpdate(values); //Thường dùng để thay đổi trạng thái tiến trình đang làm tới % blah blah, tui k xài
        }

        @Override
        protected void onPostExecute(String r) {// sau khi tiến trình kết thúc thì sẽ gọi tới hàm này
            pgbLoading.setVisibility(View.GONE);//Tắt cái cục xoay xoay đi
            Toast.makeText(MainActivity.this,r,Toast.LENGTH_SHORT).show(); // cái r chính là cái mà nó lấy từ cái hàm doInBackground(String... params), hàm này return z (String), nó sẽ quăng qua hàm này để thực hiện cái bên trong
            if(isSuccess) {//kiểm tra chủ quyền của mình có tới vị trí đánh dấu nãy không :D
                Toast.makeText(MainActivity.this,r,Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(MainActivity.this, Program_Bravo.class);//tạo ra một "gói" Intent gửi từ this đến UserActivity.class

                //setContentView(android.view.View);
                intent.putExtra("USERNAME",username);//nhét cái userid vô intent và đặt khóa là USERNAME
                intent.putExtra("PASSWORD",password);//như trên
                intent.putExtra("HOTEN",hoten);//như rứa
                intent.putExtra("IdUser",Iduser);//Gửi IdUser qua màn hình khác
                intent.putExtra("Ma_Dvcs",dvcs);
                intent.putExtra("Ma_CbNv",macbnv);
                intent.putExtra("Chuc_Danh",chucdanh);

                startActivityForResult(intent,CHAN_PW_CODE);//gửi đi, có đợi trả về, nếu trả về sẽ chạy cái hồi nãy nhấn F8
                //startActivity(intent); kiểu này sẽ không đợi trả về
                //intent
            }
        }
    }

    public void selectdata() throws SQLException {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {
            String query = "SELECT Ma_Sv,Ten_Sv FROM Sinh_Vien";
            //trên đây là câu truy vấn
            Statement stmt = con.createStatement(); //blah blah blah
            resultSet = stmt.executeQuery(query); //thực thi và trả về một cục ResultSet, nó là gì thì Google, tui chịu
            arraySinhVien.clear();
            if(resultSet.next())//nếu trong resultset không null thì sẽ trả về True
            {
                String masv=resultSet.getString("Ma_Sv");//Hàm lấy giá trị của tên cột (trường: field name) truyền vào
                String tensv=resultSet.getString("Ten_Sv");//Hàm lấy giá trị của tên cột (trường: field name) truyền vào
                arraySinhVien.add(new SinhVien(masv,tensv));

                con.close();
            }
            adapter.notifyDataSetChanged();

        }
    }





}

