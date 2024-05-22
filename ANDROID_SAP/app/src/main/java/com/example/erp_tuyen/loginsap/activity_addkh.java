package com.example.erp_tuyen.loginsap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class activity_addkh extends AppCompatActivity {

    EditText makh,tenkh;
    Button luukh;
    ResultSet resultSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addkh);
        makh = (EditText) findViewById(R.id.edaddmakh);
        tenkh = (EditText) findViewById(R.id.edaddtenkh);
        luukh = (Button) findViewById(R.id.btluuaddkh);

        luukh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luudt();
            }
        });
    }

    void luudt()
    {
        try {
            Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
            if (con == null) {
                Toast.makeText(this, "Không thể kết nối", Toast.LENGTH_SHORT).show();
            } else {

                String tendt = tenkh.getText().toString();
                PreparedStatement statement = con.prepareStatement("EXEC usp_InsertKH_SAP " +
                        "'" + tendt + "'  ");
                //resultSet = statement.executeQuery();
                statement.executeUpdate();

            }
            Toast.makeText(activity_addkh.this, "Đã lưu Thành Công !", Toast.LENGTH_SHORT).show();

            con.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


    }
}