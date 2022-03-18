package com.example.hfilm.forgotpass;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.hfilm.Info.InfoActivity;
import com.example.hfilm.R;
import com.example.hfilm.login.Login;

public class ForgotPassActivity extends AppCompatActivity {

    Button btnForgot;
    ImageView imgDong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        //Hide action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btnForgot = findViewById(R.id.btnForgot);
        imgDong = findViewById(R.id.btnDong);

        ControlButton1();

    }

    private void ControlButton1() {

        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imgDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
                builder.setTitle("Bạn có muốn thoát app không ?");
                builder.setMessage("Hãy lựa chọn bên dưới để xác nhận");
                builder.setIcon(R.drawable.hinh5);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();

                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }


}
