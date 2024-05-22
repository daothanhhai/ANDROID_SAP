package com.example.erp_tuyen.loginsap;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class activity_checkinviengthamkhachhang extends AppCompatActivity {

    private activity_checkinviengthamkhachhang context;

    ResultSet resultSet;
    String madt,tendt,diachi,dienthoai,chietkhau;
    TextView tvmkhcivt,tvtkhcivt,tvdckhcivt,tvsdtkhcivt,tvgiodtvt,tvphutdtvt,tvgiaydtvt;
    EditText ednoidung;
    String noidung;
    Button buttonchvtkh,buttonch1vtkh,buttoncovt,buttonddhcivtkh;
    ImageView imghinhvtkh;
    int REQUEST_CODE_CAMERA;
    int phut;

    String Id,IdUser, Stt, IdCt, Rowid;
    String username,dvcs,dvcs1,macbnv,hoten;

    private static final long INTERVAL = 60*60 * 1000;
    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;
    //Button stopStartButton;
    LinearLayout layout;
    boolean timerStarted = false;
    private Handler handler = new Handler();
    private Runnable popupRunnable = new Runnable() {
        @Override
        public void run() {
            CreatepopupUpwindow(); // Gọi hàm hiển thị popup
            handler.postDelayed(this, INTERVAL); // Lặp lại sau mỗi 60 phút
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkinviengthamkhachhang);

        getSupportActionBar().setTitle("Check In");
        final Intent intent=getIntent();
        madt=intent.getStringExtra("MADT");
        tendt=intent.getStringExtra("TENDT");
        diachi=intent.getStringExtra("DIACHI");
        dienthoai=intent.getStringExtra("DIENTHOAI");
        chietkhau=intent.getStringExtra("CHIETKHAU");
        IdUser=intent.getStringExtra("ID");
        dvcs=intent.getStringExtra("Ma_DvCs");
        macbnv=intent.getStringExtra("Ma_CbNv");
        hoten=intent.getStringExtra("HOTEN");
        username=intent.getStringExtra("USERNAME");

        anhxa();

        timer = new Timer();
        startTimer();

        tvmkhcivt.setText(madt);
        tvtkhcivt.setText(tendt);
        tvdckhcivt.setText(diachi);
        tvsdtkhcivt.setText(dienthoai);

        buttonchvtkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE_CAMERA);

            }
        });


        buttoncovt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    luuviengtham();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        buttonch1vtkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    luuhinhanh();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        buttonddhcivtkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent2 = new Intent(activity_checkinviengthamkhachhang.this, activity_dondathangtdv.class);
                    intent2.putExtra("IdUser", IdUser);//Gửi IdUser qua màn hình khác
                    intent2.putExtra("Ma_Dvcs", dvcs);
                    intent2.putExtra("Ma_CbNv", macbnv);
                    //intent2.putExtra("Chuc_Danh",chucdanh);
                    intent2.putExtra("USERNAME", username);
                    intent2.putExtra("MADTVT", madt);
                    intent2.putExtra("TENDTVT", tendt);
                    intent2.putExtra("DIACHIVT", diachi);
                    intent2.putExtra("CHIETKHAUVT", chietkhau);
                    startActivity(intent2);


            }
        });

        CreatepopupUpwindow();//goi ham view popup
        handler.postDelayed(popupRunnable, INTERVAL);

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
    void anhxa ()
    {
        tvmkhcivt=(TextView) findViewById(R.id.tvmkhcivt);
        tvtkhcivt=(TextView) findViewById(R.id.tvtkhcivt);
        tvdckhcivt=(TextView) findViewById(R.id.tvdccivt);
        tvsdtkhcivt=(TextView) findViewById(R.id.tvdtcivt);
        buttonchvtkh=(Button) findViewById(R.id.buttonchcivt);
        buttonch1vtkh=(Button) findViewById(R.id.buttonch1civt);
        buttoncovt=(Button) findViewById(R.id.buttoncovt);
        buttonddhcivtkh=(Button) findViewById(R.id.buttondhcivt);
        imghinhvtkh=(ImageView) findViewById(R.id.imagevtkh);

        tvgiodtvt=(TextView) findViewById(R.id.tvgiodtvt);
        ednoidung = (EditText) findViewById(R.id.edkskhcivt);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_CODE_CAMERA && resultCode==RESULT_OK && data !=null)
        {
            Bitmap bitmap= (Bitmap) data.getExtras().get("data");

            imghinhvtkh.getLayoutParams().height = 700;
            imghinhvtkh.getLayoutParams().width = 700;


            imghinhvtkh.setImageBitmap(bitmap);


        }
        super.onActivityResult(requestCode, resultCode, data);
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // TODO Auto-generated method stub
//        super.onActivityResult(requestCode, resultCode, data);
//        switch(requestCode){
//            case 0:
//                if(resultCode == RESULT_OK){
//                    Bundle extras = data.getExtras();
//                    Bitmap bitmap1 = (Bitmap) extras.get("data");
//                    imghinhvtkh.setImageBitmap(bitmap1);
//
//
//                    imghinhvtkh.getLayoutParams().height = 700;
//                    imghinhvtkh.getLayoutParams().width = 700;
//                }
//                break;
//            case 1:
//                if(resultCode == RESULT_OK) {
//                    Bundle extras = data.getExtras();
//                    Bitmap bitmap2 = (Bitmap) extras.get("data");
//                    imghinhvtkh.setImageBitmap(bitmap2);
//                    imghinhvtkh.getLayoutParams().height = 700;
//                    imghinhvtkh.getLayoutParams().width = 700;
//                }
//                break;
//        }
//    }






    public void luuviengtham() throws SQLException {
        try {
            Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
            if (con == null) {
                Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
            } else {
                noidung=ednoidung.getText().toString();
                taostt();
                taothongtinCN();
                String ngayvt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(new Date());

                timerTask.cancel();
                String image="";
            if(imghinhvtkh.getDrawable()==null) {
                image="";
            }
            else {

                @SuppressLint("WrongThread") BitmapDrawable bitmapDrawable = (BitmapDrawable) imghinhvtkh.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                image = Base64.encodeToString(bytes, Base64.DEFAULT);

            }

                String query = "INSERT INTO B30ViengThamKH(Ma_DvCs,Ma_DvCs1,Stt,Ma_Dt,Ten_Dt,Dia_Chi,Dien_Thoai,Ma_TDV" +
                        ",Ten_TDV,Ngay_Vieng_Tham,Noi_Dung,Ghi_Chu,So_Phut_Vieng_Tham,Hinh_Anh" +
                        ",Isactive,CreatedBy,CreatedAt,ModifiedBy,ModifiedAt) " +
                        "Values('"+ dvcs +"','"+ dvcs1 +"','"+ Stt +"','"+ madt +"',N'"+ tendt +"',N'"+ diachi +"','"+ dienthoai +"','"+ macbnv +"'" +
                        ",N'"+ hoten +"','"+ ngayvt +"',N'"+ noidung +"','','"+ phut +"','"+ image +"','1',"+ IdUser +",'',"+ IdUser +",'')";

                Statement stmt = con.createStatement();
                PreparedStatement pstmt = con.prepareStatement(query);
                int result = pstmt.executeUpdate();
                con.close();
            }
            Toast.makeText(this, "Đã check out thành công", Toast.LENGTH_SHORT).show();


        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public void luuhinhanh() throws SQLException {
        try {
            Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
            if (con == null) {
                Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
            } else {
                noidung=ednoidung.getText().toString();
                taostt();
                taothongtinCN();
                String ngayvt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(new Date());

                //timerTask.cancel();
                String image="";
                if(imghinhvtkh.getDrawable()==null) {
                    image="";
                }
                else {

                    @SuppressLint("WrongThread") BitmapDrawable bitmapDrawable = (BitmapDrawable) imghinhvtkh.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    image = Base64.encodeToString(bytes, Base64.DEFAULT);

                }

                String query = "INSERT INTO B30ViengThamKH(Ma_DvCs,Ma_DvCs1,Stt,Ma_Dt,Ten_Dt,Dia_Chi,Dien_Thoai,Ma_TDV" +
                        ",Ten_TDV,Ngay_Vieng_Tham,Noi_Dung,Ghi_Chu,So_Phut_Vieng_Tham,Hinh_Anh" +
                        ",Isactive,CreatedBy,CreatedAt,ModifiedBy,ModifiedAt) " +
                        "Values('"+ dvcs +"','"+ dvcs1 +"','"+ Stt +"','"+ madt +"',N'"+ tendt +"',N'"+ diachi +"','"+ dienthoai +"','"+ macbnv +"'" +
                        ",N'"+ hoten +"','"+ ngayvt +"',N'"+ noidung +"','','"+ phut +"','"+ image +"','1',"+ IdUser +",'',"+ IdUser +",'')";

                Statement stmt = con.createStatement();
                PreparedStatement pstmt = con.prepareStatement(query);
                int result = pstmt.executeUpdate();
                con.close();
            }
            Toast.makeText(this, "Đã lưu ảnh", Toast.LENGTH_SHORT).show();


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

            String query = "SELECT MAX(Id),(MAX(Id)+1) AS IdM FROM B30ViengThamKH WHERE Ma_Dvcs='"+ dvcs +"'";
            //trên đây là câu truy vấn
            Statement stmt = con.createStatement(); //blah blah blah
            resultSet = stmt.executeQuery(query); //thực thi và trả về một cục ResultSet, nó là gì thì Google, tui chịu

            for(int i = 0; i<=resultSet.getRow();i++ )
            {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    Id = resultSet.getString("IdM");

                    if(Id==null)
                    {
                        Stt=dvcs+"0000000000001";
                        return;
                    }

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

    public void taothongtinCN()
    {
        if(dvcs.equals("A01"))
        {
            dvcs1="1N101";

        }
        if(dvcs.equals("A02"))
        {
            dvcs1="2N101-01";

        }
        if(dvcs.equals("A03"))
        {
            dvcs1="2N301";

        }

        if(dvcs.equals("A04"))
        {
            dvcs1="2N302";

        }
        if(dvcs.equals("A05"))
        {
            dvcs1="2N201";

        }
        if(dvcs.equals("A06"))
        {
            dvcs1="2N202";

        }
        if(dvcs.equals("A07"))
        {
            dvcs1="2T101";

        }
        if(dvcs.equals("A08"))
        {
            dvcs1="2T102";

        }
        if(dvcs.equals("A09"))
        {
            dvcs1="2T103";

        }
        if(dvcs.equals("A10"))
        {
            dvcs1="2B101";

        }
    }


    public void resetTapped(View view)
    {
        AlertDialog.Builder resetAlert = new AlertDialog.Builder(this);
        resetAlert.setTitle("Reset Timer");
        resetAlert.setMessage("Are you sure you want to reset the timer?");
        resetAlert.setPositiveButton("Reset", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                if(timerTask != null)
                {
                    timerTask.cancel();
                    setButtonUI("START", R.color.colorAccent);
                    time = 0.0;
                    timerStarted = false;
                    tvgiodtvt.setText(formatTime(0,0,0));

                }
            }
        });

        resetAlert.setNeutralButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                //do nothing
            }
        });

        resetAlert.show();

    }

    public void startStopTapped(View view)
    {
        if(timerStarted == false)
        {
            timerStarted = true;
            setButtonUI("STOP", R.color.colorAccent);

            startTimer();
        }
        else
        {
            timerStarted = false;
            setButtonUI("START", R.color.colorAccent);

            timerTask.cancel();
        }
    }

    private void setButtonUI(String start, int color)
    {
        //stopStartButton.setText(start);
        //stopStartButton.setTextColor(ContextCompat.getColor(this, color));
    }

    private void startTimer()
    {
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        time++;
                        tvgiodtvt.setText(getTimerText());
                    }
                });
            }

        };
        timer.scheduleAtFixedRate(timerTask, 0 ,1000);
    }


    private String getTimerText()
    {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);
        return formatTime(seconds, minutes, hours);
    }

    private String formatTime(int seconds, int minutes, int hours)
    {
        phut=minutes;
        return String.format("%02d",hours) + " : " + String.format("%02d",minutes) + " : " + String.format("%02d",seconds);
    }


}