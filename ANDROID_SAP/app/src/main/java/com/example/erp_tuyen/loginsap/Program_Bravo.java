package com.example.erp_tuyen.loginsap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.net.Uri;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Program_Bravo extends AppCompatActivity {
    private static final long INTERVAL = 3*60*60 * 1000;
    private static final int MAX_RUN_COUNT = 3; // Số lần tối đa
    private int runCount = 0; // Biến đếm
    Button btdmkh,btdmbc,btddh,btdoipass,btkhachhangonline,btwebsite;
    Button btdsddhtdv,btvtkhtdv,btddhtdv,btbkhdbh;
    String IdUser,username,password,hoten,dvcs,macnnv,chucdanh;
    RelativeLayout layout;
        private Handler handler = new Handler();
    private Runnable popupRunnable = new Runnable() {
        @Override
        public void run() {
            if (runCount < MAX_RUN_COUNT) {
                CreatepopupUpwindow(); // Gọi hàm hiển thị popup
                runCount++; // Tăng biến đếm
                handler.postDelayed(this, INTERVAL); // Lặp lại sau mỗi INTERVAL (60 phút)
            }
        }
    };


    private ProgressDialog LoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bravo_program);
        layout = findViewById(R.id.home_page);//popup
        getSupportActionBar().setTitle("OPC-MOBILE");

        Intent intent=getIntent();
        IdUser=intent.getStringExtra("IdUser");
        username=intent.getStringExtra("USERNAME");
        password=intent.getStringExtra("PASSWORD");
        hoten=intent.getStringExtra("HOTEN");
        dvcs=intent.getStringExtra("Ma_Dvcs");
        macnnv=intent.getStringExtra("Ma_CbNv");
        chucdanh=intent.getStringExtra("Chuc_Danh");

        BottomNavigationView navigationView=findViewById(R.id.bottom_nav);


        ANHXA();

        if(chucdanh.equals("KH"))
        {
            btdmkh.setVisibility(View.INVISIBLE);
            btdmbc.setVisibility(View.INVISIBLE);
            btddh.setVisibility(View.INVISIBLE);

        }

        btdmkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showmenudanhmuc();
            }
        });



        btdmbc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showmenu();
            }
        });

        btddh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showmenuddh();
            }
        });

        btkhachhangonline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showmenukhachangonline();
            }
        });

        btdoipass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Program_Bravo.this, UserActivity.class);
                intent.putExtra("USERNAME",username);
                intent.putExtra("PASSWORD",password);
                intent.putExtra("HOTEN",hoten);
                startActivity(intent);

            }
        });

        btwebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://opcpharma.info/home/Login_TDV";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);

            }
        });

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                        case R.id.action_home:
                            Toast.makeText(Program_Bravo.this, "Trang Home", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.action_tdv:
                            Toast.makeText(Program_Bravo.this, "trang TDV", Toast.LENGTH_SHORT).show();
                            Intent intent1=new Intent(Program_Bravo.this,activity_layouttdv.class);
                            intent1.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                            intent1.putExtra("Ma_Dvcs",dvcs);
                            intent1.putExtra("Ma_CbNv",macnnv);
                            intent1.putExtra("Chuc_Danh",chucdanh);
                            intent1.putExtra("USERNAME",username);
                            startActivity(intent1);
                            break;
                        case R.id.action_gh:
                            Intent intent2=new Intent(Program_Bravo.this,activity_layoutgh.class);
                            intent2.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                            intent2.putExtra("Ma_Dvcs",dvcs);
                            intent2.putExtra("Ma_CbNv",macnnv);
                            intent2.putExtra("Chuc_Danh",chucdanh);
                            intent2.putExtra("USERNAME",username);
                            startActivity(intent2);
                            break;
                }
                return true;
            }
        });


        btdsddhtdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Program_Bravo.this,activity_dsdondathang.class);
                intent1.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                intent1.putExtra("Ma_CbNv",macnnv);
                intent1.putExtra("Ma_Dvcs",dvcs);
                startActivity(intent1);
            }
        });

        btvtkhtdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7=new Intent(Program_Bravo.this,activity_viengthamkhachhang.class);
                intent7.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                intent7.putExtra("Ma_Dvcs",dvcs);
                intent7.putExtra("Ma_CbNv",macnnv);
                intent7.putExtra("Chuc_Danh",chucdanh);
                intent7.putExtra("USERNAME",username);
                intent7.putExtra("HOTEN",hoten);
                startActivity(intent7);
            }
        });

        btddhtdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent24=new Intent(Program_Bravo.this,activity_dondathangtdv.class);
                intent24.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                intent24.putExtra("Ma_Dvcs",dvcs);
                intent24.putExtra("Ma_CbNv",macnnv);
                intent24.putExtra("Chuc_Danh",chucdanh);
                intent24.putExtra("USERNAME",username);
                startActivity(intent24);
            }
        });

        btbkhdbh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent25=new Intent(Program_Bravo.this,activity_bangkehoadonbanhang.class);
                intent25.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                intent25.putExtra("Ma_Dvcs",dvcs);
                intent25.putExtra("Ma_CbNv",macnnv);
                intent25.putExtra("Chuc_Danh",chucdanh);
                intent25.putExtra("USERNAME",username);
                startActivity(intent25);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_baocao,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuspbc:
                    Intent intent=new Intent(Program_Bravo.this,activity_topsanpham.class);
                    startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void ANHXA()
    {
        btdmkh=(Button) findViewById(R.id.buttondmkh);

        btdmbc=(Button) findViewById(R.id.buttondmbc);
        btddh=(Button) findViewById(R.id.buttonddh);
        btdoipass=(Button) findViewById(R.id.buttondoipass);
        btkhachhangonline=(Button) findViewById(R.id.buttonkhachhangonline);
        btwebsite=(Button) findViewById(R.id.buttonwebsite);

        btdsddhtdv=(Button) findViewById(R.id.buttondsdhtdv);
        btvtkhtdv=(Button) findViewById(R.id.buttonvtkh);
        btddhtdv=(Button) findViewById(R.id.buttondhtdv);
        btbkhdbh=(Button) findViewById(R.id.buttonbkhdbh);
    }

    private void Showmenu()
    {
        PopupMenu popupMenu=new PopupMenu(this,btdmbc);
        popupMenu.getMenuInflater().inflate(R.menu.menu_baocao,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.menudtbh:
                        Intent intent=new Intent(Program_Bravo.this,activity_doanhthu.class);
                        intent.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent.putExtra("Ma_Dvcs",dvcs);
                        intent.putExtra("Ma_CbNv",macnnv);
                        intent.putExtra("Chuc_Danh",chucdanh);
                        intent.putExtra("USERNAME",username);
                        startActivity(intent);
                        break;
                    case R.id.menuspbc:
                        Intent intent1=new Intent(Program_Bravo.this,activity_topsanpham.class);
                        intent1.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent1.putExtra("Ma_Dvcs",dvcs);
                        intent1.putExtra("Ma_CbNv",macnnv);
                        intent1.putExtra("Chuc_Danh",chucdanh);
                        intent1.putExtra("USERNAME",username);

                        startActivity(intent1);
                        break;

                    case R.id.menuddh:
                        Intent intent2=new Intent(Program_Bravo.this,activity_dondathang.class);
                        intent2.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent2.putExtra("Ma_Dvcs",dvcs);
                        intent2.putExtra("Ma_CbNv",macnnv);
                        intent2.putExtra("Chuc_Danh",chucdanh);
                        intent2.putExtra("USERNAME",username);
                        startActivity(intent2);
                        break;

                    case R.id.menudttheokh:
                        Intent intent3=new Intent(Program_Bravo.this,activity_doanhthutheokhachhang.class);
                        intent3.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent3.putExtra("Ma_Dvcs",dvcs);
                        intent3.putExtra("Ma_CbNv",macnnv);
                        intent3.putExtra("Chuc_Danh",chucdanh);
                        intent3.putExtra("USERNAME",username);
                        startActivity(intent3);
                        break;

                    case R.id.menudttheothang:
                        Intent intent4=new Intent(Program_Bravo.this,activity_doanhthutheothang.class);
                        intent4.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent4.putExtra("Ma_Dvcs",dvcs);
                        intent4.putExtra("Ma_CbNv",macnnv);
                        intent4.putExtra("Chuc_Danh",chucdanh);
                        intent4.putExtra("USERNAME",username);
                        startActivity(intent4);
                        break;

                    case R.id.menutongquang:
                        Intent intent5=new Intent(Program_Bravo.this,activity_tongquangdoanhthu.class);
                        intent5.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent5.putExtra("Ma_Dvcs",dvcs);
                        intent5.putExtra("Ma_CbNv",macnnv);
                        intent5.putExtra("Chuc_Danh",chucdanh);
                        intent5.putExtra("USERNAME",username);
                        startActivity(intent5);
                        break;

                    case R.id.menubhtdv:
                        Intent intent6=new Intent(Program_Bravo.this,activity_doanhthutrinhduocvien.class);
                        intent6.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent6.putExtra("Ma_Dvcs",dvcs);
                        intent6.putExtra("Ma_CbNv",macnnv);
                        intent6.putExtra("Chuc_Danh",chucdanh);
                        intent6.putExtra("USERNAME",username);
                        startActivity(intent6);
                        break;

                    case R.id.menutdghtdv:
                        Intent intent7=new Intent(Program_Bravo.this,activity_theodoigiaohangtdv.class);
                        intent7.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent7.putExtra("Ma_Dvcs",dvcs);
                        startActivity(intent7);
                        break;

                    case R.id.menubctk:
                        Intent intent8=new Intent(Program_Bravo.this,activity_tonkhotrinhduocvien.class);
                        intent8.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent8.putExtra("Ma_Dvcs",dvcs);
                        startActivity(intent8);
                        break;

                    case R.id.menutvtdv:
                        Intent intent9=new Intent(Program_Bravo.this,activity_tienvetrinhduocvien.class);
                        intent9.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent9.putExtra("Ma_Dvcs",dvcs);
                        intent9.putExtra("Ma_CbNv",macnnv);
                        intent9.putExtra("Chuc_Danh",chucdanh);
                        intent9.putExtra("USERNAME",username);
                        startActivity(intent9);
                        break;

                    case R.id.menuctsnkh:
                        Intent intent10=new Intent(Program_Bravo.this,activity_snkhtrinhduocvien.class);
                        intent10.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent10.putExtra("Ma_Dvcs",dvcs);
                        intent10.putExtra("Ma_CbNv",macnnv);
                        intent10.putExtra("Chuc_Danh",chucdanh);
                        intent10.putExtra("USERNAME",username);
                        startActivity(intent10);
                        break;

                    case R.id.menubccnkh:
                        Intent intent11=new Intent(Program_Bravo.this,activity_congnotheokhachhang.class);
                        intent11.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent11.putExtra("Ma_Dvcs",dvcs);
                        intent11.putExtra("Ma_CbNv",macnnv);
                        intent11.putExtra("Chuc_Danh",chucdanh);
                        intent11.putExtra("USERNAME",username);
                        startActivity(intent11);
                        break;

                    case R.id.menuctkhtt:
                        Intent intent12=new Intent(Program_Bravo.this,activity_khtttrinhduocvien.class);
                        intent12.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent12.putExtra("Ma_Dvcs",dvcs);
                        intent12.putExtra("Ma_CbNv",macnnv);
                        intent12.putExtra("Chuc_Danh",chucdanh);
                        intent12.putExtra("USERNAME",username);
                        startActivity(intent12);
                        break;

                    case R.id.menubckhkpsdt:
                        Intent intent13=new Intent(Program_Bravo.this,activity_khkhongpsdoanhthu.class);
                        intent13.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent13.putExtra("Ma_Dvcs",dvcs);
                        intent13.putExtra("Ma_CbNv",macnnv);
                        intent13.putExtra("Chuc_Danh",chucdanh);
                        intent13.putExtra("USERNAME",username);
                        startActivity(intent13);
                        break;


                    case R.id.menugsdhtdv:
                        Intent intent14=new Intent(Program_Bravo.this,activity_giamsatdonhangtdv.class);
                        intent14.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent14.putExtra("Ma_Dvcs",dvcs);
                        intent14.putExtra("Ma_CbNv",macnnv);
                        intent14.putExtra("Chuc_Danh",chucdanh);
                        intent14.putExtra("USERNAME",username);
                        startActivity(intent14);
                        break;

                    case R.id.menubcvtkh:
                        Intent intent15=new Intent(Program_Bravo.this,activity_baocaoviengthamkhachhang.class);
                        intent15.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent15.putExtra("Ma_Dvcs",dvcs);
                        intent15.putExtra("Ma_CbNv",macnnv);
                        intent15.putExtra("Chuc_Danh",chucdanh);
                        intent15.putExtra("USERNAME",username);
                        startActivity(intent15);
                        break;

                    case R.id.menucntdv:
                        Intent intent16=new Intent(Program_Bravo.this,activity_congnokhachhangpl5.class);
                        intent16.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent16.putExtra("Ma_Dvcs",dvcs);
                        intent16.putExtra("Ma_CbNv",macnnv);
                        intent16.putExtra("Chuc_Danh",chucdanh);
                        intent16.putExtra("USERNAME",username);
                        startActivity(intent16);
                        break;

                    case R.id.menuctgimmickaomua:
                        Intent intent17=new Intent(Program_Bravo.this,activity_gimickaomua.class);
                        intent17.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent17.putExtra("Ma_Dvcs",dvcs);
                        intent17.putExtra("Ma_CbNv",macnnv);
                        intent17.putExtra("Chuc_Danh",chucdanh);
                        intent17.putExtra("USERNAME",username);
                        startActivity(intent17);
                        break;

                    case R.id.menugsvtkh:
                        Intent intent18=new Intent(Program_Bravo.this,activity_giamsatviengthamkhachhang.class);
                        intent18.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent18.putExtra("Ma_Dvcs",dvcs);
                        intent18.putExtra("Ma_CbNv",macnnv);
                        intent18.putExtra("Chuc_Danh",chucdanh);
                        intent18.putExtra("USERNAME",username);
                        startActivity(intent18);
                        break;

                    case R.id.menuctgimmickbutbi:
                        Intent intent19=new Intent(Program_Bravo.this,activity_gimickbutbi.class);
                        intent19.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent19.putExtra("Ma_Dvcs",dvcs);
                        intent19.putExtra("Ma_CbNv",macnnv);
                        intent19.putExtra("Chuc_Danh",chucdanh);
                        intent19.putExtra("USERNAME",username);
                        startActivity(intent19);
                        break;

                    case R.id.menuctgimmickquatanglich:
                        Intent intent20=new Intent(Program_Bravo.this,activity_gimickquatanglich.class);
                        intent20.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent20.putExtra("Ma_Dvcs",dvcs);
                        intent20.putExtra("Ma_CbNv",macnnv);
                        intent20.putExtra("Chuc_Danh",chucdanh);
                        intent20.putExtra("USERNAME",username);
                        startActivity(intent20);
                        break;

                    case R.id.menuctgimmickquatet:
                        Intent intent21=new Intent(Program_Bravo.this,activity_gimickquatet.class);
                        intent21.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent21.putExtra("Ma_Dvcs",dvcs);
                        intent21.putExtra("Ma_CbNv",macnnv);
                        intent21.putExtra("Chuc_Danh",chucdanh);
                        intent21.putExtra("USERNAME",username);
                        startActivity(intent21);
                        break;

                    case R.id.menuctgimmickquatetCNT:
                        Intent intent22=new Intent(Program_Bravo.this,activity_gimickquatetcnt.class);
                        intent22.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent22.putExtra("Ma_Dvcs",dvcs);
                        intent22.putExtra("Ma_CbNv",macnnv);
                        intent22.putExtra("Chuc_Danh",chucdanh);
                        intent22.putExtra("USERNAME",username);
                        startActivity(intent22);
                        break;

                    case R.id.menuctgimmickquabtt:
                        Intent intent23=new Intent(Program_Bravo.this,activity_gimickbanhtrungthu.class);
                        intent23.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent23.putExtra("Ma_Dvcs",dvcs);
                        intent23.putExtra("Ma_CbNv",macnnv);
                        intent23.putExtra("Chuc_Danh",chucdanh);
                        intent23.putExtra("USERNAME",username);
                        startActivity(intent23);
                        break;

                    case R.id.menubchtcn:
                        Intent intent24=new Intent(Program_Bravo.this,activity_baocaothaucn.class);
                        intent24.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent24.putExtra("Ma_Dvcs",dvcs);
                        intent24.putExtra("Ma_CbNv",macnnv);
                        intent24.putExtra("Chuc_Danh",chucdanh);
                        intent24.putExtra("USERNAME",username);
                        startActivity(intent24);
                        break;

                    case R.id.menubcdtcntk:
                        Intent intent25=new Intent(Program_Bravo.this,activity_baocaodtcntk.class);
                        intent25.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent25.putExtra("Ma_Dvcs",dvcs);
                        intent25.putExtra("Ma_CbNv",macnnv);
                        intent25.putExtra("Chuc_Danh",chucdanh);
                        intent25.putExtra("USERNAME",username);
                        startActivity(intent25);
                        break;



                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void Showmenuddh()
    {
        PopupMenu popupMenu=new PopupMenu(this,btdmbc);
        popupMenu.getMenuInflater().inflate(R.menu.menu_donhang,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.menuvtkh:
                        Intent intent7=new Intent(Program_Bravo.this,activity_viengthamkhachhang.class);
                        intent7.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent7.putExtra("Ma_Dvcs",dvcs);
                        intent7.putExtra("Ma_CbNv",macnnv);
                        intent7.putExtra("Chuc_Danh",chucdanh);
                        intent7.putExtra("USERNAME",username);
                        intent7.putExtra("HOTEN",hoten);
                        startActivity(intent7);
                        break;


                    case R.id.menudsddh:
                        Intent intent1=new Intent(Program_Bravo.this,activity_dsdondathang.class);
                        intent1.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent1.putExtra("Ma_CbNv",macnnv);
                        intent1.putExtra("Ma_Dvcs",dvcs);
                        startActivity(intent1);
                        break;


                    case R.id.menuddh:
                        Intent intent2=new Intent(Program_Bravo.this,activity_dondathang.class);
                        intent2.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent2.putExtra("Ma_Dvcs",dvcs);
                        intent2.putExtra("Ma_CbNv",macnnv);
                        intent2.putExtra("Chuc_Danh",chucdanh);
                        intent2.putExtra("USERNAME",username);
                        startActivity(intent2);
                        break;

                    case R.id.menuddhtdv:
                        Intent intent24=new Intent(Program_Bravo.this,activity_dondathangtdv.class);
                        intent24.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent24.putExtra("Ma_Dvcs",dvcs);
                        intent24.putExtra("Ma_CbNv",macnnv);
                        intent24.putExtra("Chuc_Danh",chucdanh);
                        intent24.putExtra("USERNAME",username);
                        startActivity(intent24);
                        break;


                    case R.id.menubktmtdv:
                        Intent intent25=new Intent(Program_Bravo.this,activity_bknoptientdv.class);
                        intent25.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent25.putExtra("Ma_Dvcs",dvcs);
                        intent25.putExtra("Ma_CbNv",macnnv);
                        intent25.putExtra("Chuc_Danh",chucdanh);
                        intent25.putExtra("USERNAME",username);
                        startActivity(intent25);
                        break;

                    case R.id.menubktmgh:
                        Intent intent26=new Intent(Program_Bravo.this,activity_bknoptiengh.class);
                        intent26.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent26.putExtra("Ma_Dvcs",dvcs);
                        intent26.putExtra("Ma_CbNv",macnnv);
                        intent26.putExtra("Chuc_Danh",chucdanh);
                        intent26.putExtra("USERNAME",username);
                        startActivity(intent26);
                        break;

                    case R.id.menuspchinh:
                        Intent intent3=new Intent(Program_Bravo.this,activity_sanphamchinh.class);
                        intent3.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        startActivity(intent3);
                        break;

                    case R.id.menutsp:
                        Intent intent4=new Intent(Program_Bravo.this,activity_themsanpham.class);
                        intent4.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        startActivity(intent4);
                        break;

                    case R.id.menuspm:
                        Intent intent5=new Intent(Program_Bravo.this,activity_sanphammoi.class);
                        intent5.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent5.putExtra("Ma_Dvcs",dvcs);
                        intent5.putExtra("Ma_CbNv",macnnv);
                        intent5.putExtra("Chuc_Danh",chucdanh);
                        intent5.putExtra("USERNAME",username);
                        startActivity(intent5);
                        break;



                    case R.id.menutnkh:
                        Intent intent6=new Intent(Program_Bravo.this,activity_thunokhachhang.class);
                        intent6.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent6.putExtra("Ma_Dvcs",dvcs);
                        intent6.putExtra("USERNAME",username);
                        intent6.putExtra("Ma_CbNv",macnnv);
                        startActivity(intent6);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void Showmenukhachangonline()
    {
        PopupMenu popupMenu=new PopupMenu(this,btkhachhangonline);
        popupMenu.getMenuInflater().inflate(R.menu.menu_khachhang,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.menuspchinh:
                        Intent intent4=new Intent(Program_Bravo.this,activity_sanphamchinh.class);
                        intent4.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent4.putExtra("USERNAME",username);
                        startActivity(intent4);
                        break;


                    case R.id.menuspm:
                        Intent intent5=new Intent(Program_Bravo.this,activity_sanphammoi.class);
                        intent5.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent5.putExtra("Ma_Dvcs",dvcs);
                        intent5.putExtra("Ma_CbNv",macnnv);
                        intent5.putExtra("Chuc_Danh",chucdanh);
                        intent5.putExtra("USERNAME",username);
                        startActivity(intent5);
                        break;

                    case R.id.menuspbd:


                        Intent intent6=new Intent(Program_Bravo.this,activity_sanphamboduong.class);
                        intent6.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent6.putExtra("Ma_Dvcs",dvcs);
                        intent6.putExtra("Ma_CbNv",macnnv);
                        intent6.putExtra("Chuc_Danh",chucdanh);
                        intent6.putExtra("USERNAME",username);
                        startActivity(intent6);
                        break;


                    case R.id.menuspdk:
                        Intent intent8=new Intent(Program_Bravo.this,activity_sanphamdieukinh.class);
                        intent8.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent8.putExtra("Ma_Dvcs",dvcs);
                        intent8.putExtra("Ma_CbNv",macnnv);
                        intent8.putExtra("Chuc_Danh",chucdanh);
                        intent8.putExtra("USERNAME",username);
                        startActivity(intent8);
                        break;

                    case R.id.menusphh:
                        Intent intent9=new Intent(Program_Bravo.this,activity_sanphamhohap.class);
                        intent9.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent9.putExtra("Ma_Dvcs",dvcs);
                        intent9.putExtra("Ma_CbNv",macnnv);
                        intent9.putExtra("Chuc_Danh",chucdanh);
                        intent9.putExtra("USERNAME",username);
                        startActivity(intent9);
                        break;

                    case R.id.menusptngd:
                        Intent intent10=new Intent(Program_Bravo.this,activity_sanphamthanhnhietgiaidoc.class);
                        intent10.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent10.putExtra("Ma_Dvcs",dvcs);
                        intent10.putExtra("Ma_CbNv",macnnv);
                        intent10.putExtra("Chuc_Danh",chucdanh);
                        intent10.putExtra("USERNAME",username);
                        startActivity(intent10);
                        break;

                    case R.id.menusptkc:
                        Intent intent11=new Intent(Program_Bravo.this,activity_sanphamthankinhco.class);
                        intent11.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent11.putExtra("Ma_Dvcs",dvcs);
                        intent11.putExtra("Ma_CbNv",macnnv);
                        intent11.putExtra("Chuc_Danh",chucdanh);
                        intent11.putExtra("USERNAME",username);
                        startActivity(intent11);
                        break;


                    case R.id.menuqcspm:
                        Intent intent7=new Intent(Program_Bravo.this,activity_prosure_spm.class);
                        startActivity(intent7);
                        break;


                }
                return false;
            }
        });
        popupMenu.show();
    }


    private void Showmenudanhmuc()
    {
        PopupMenu popupMenu=new PopupMenu(this,btdmkh);
        popupMenu.getMenuInflater().inflate(R.menu.menu_danhmuc,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.menudmkh:
                        Intent intent=new Intent(Program_Bravo.this,activity_khachhang.class);
                        intent.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent.putExtra("Ma_Dvcs",dvcs);
                        intent.putExtra("Ma_CbNv",macnnv);
                        intent.putExtra("Chuc_Danh",chucdanh);
                        intent.putExtra("USERNAME",username);
                        startActivity(intent);
                        break;


                    case R.id.menudmbg:
                        Intent intent1=new Intent(Program_Bravo.this,activity_banggia.class);
                        intent1.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent1.putExtra("USERNAME",username);
                        startActivity(intent1);
                        break;


                    case R.id.menudmdt:
                        Intent intent2=new Intent(Program_Bravo.this,activity_doitac.class);
                        intent2.putExtra("IdUser",IdUser);//Gửi IdUser qua màn hình khác
                        intent2.putExtra("Ma_Dvcs",dvcs);
                        intent2.putExtra("Ma_CbNv",macnnv);
                        intent2.putExtra("Chuc_Danh",chucdanh);
                        intent2.putExtra("USERNAME",username);
                        startActivity(intent2);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

}