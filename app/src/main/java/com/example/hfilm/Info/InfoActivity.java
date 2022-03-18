package com.example.hfilm.Info;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hfilm.R;
import com.example.hfilm.data.model.Datum;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;

import es.dmoral.toasty.Toasty;

public class InfoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    int REQUEST_VIDEO = 123;
    YouTubePlayerView vv;
    String API_KEY = "AIzaSyAdMo5Q54UwwCKMPCZY2GDhozh050w8_Pc";
    Datum item;
    TextView tvTen, tvTitle, tvScript, tvxemthem, tvluotxem, tvtheloai, tvdienvien, tvdaodien, tvmanufacturer, tvthoiluong, tvthich;
    ImageView imgFilm, imgthich;
    String idYT;
    String idF;
    String prefname = "my_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


        //Hide action bara
//        getActionBar().hide();

        vv = findViewById(R.id.videoYT);
        vv.initialize(API_KEY, this);
        tvTen = findViewById(R.id.tvTen);
        tvTitle = findViewById(R.id.toolbar_title);
        tvScript = findViewById(R.id.tvScript);
        tvxemthem = findViewById(R.id.tvXemthem);
        imgFilm = (ImageView) findViewById(R.id.imgfilm);
        tvluotxem = findViewById(R.id.tvluotxem);
        tvtheloai = findViewById(R.id.tvtheloai);
        tvdienvien = findViewById(R.id.tvdienvien);
        tvdaodien = findViewById(R.id.tvdaodien);
        tvmanufacturer = findViewById(R.id.tvmanufacturer);
        tvthoiluong = findViewById(R.id.tvthoiluong);
        tvthich = findViewById(R.id.tvthich);
        imgthich = findViewById(R.id.imgthich);

        Bundle bundle = getIntent().getExtras();

        item = (Datum) bundle.get("film");

        //LÁY ID FILM;
        idF = item.getId().toString();

        String s = item.getTitle();
        if(s.contains("/"))
        {
            String[] splits =  s.split("/");

            tvTitle.setText(splits[0]);

            String name = splits[1];
            name = name.toLowerCase();
            // bo khang trang
            if(name.substring(0,1).equals(" "))
                name = name.substring(1, name.length());
            //Viet hoa chu dau
            String w = name.substring(0,1).toUpperCase();
            String s2 = w + name.substring(1, name.length());
            //anh xa
            tvTen.setText(s2);
        }
        else
        {
            tvTen.setText(s);
            tvTitle.setText(s);
        }

        //in Script
        //cat chuoi
        String script = item.getDescription();
//        if(script.length() > 200)
//            script = script.substring(0, 180) + "...";
//        else
//        {
//            tvxemthem.setVisibility(View.GONE);
//        }
        tvScript.setText(script);

        if(tvScript.getMaxLines() < 4)
            tvxemthem.setVisibility(View.GONE);
        else
        {
            tvScript.setMaxLines(4);
        }



        //In hinh Film
        Picasso.with(getApplicationContext())
                .load(item.getImage())
                .placeholder(R.drawable.loading_image)
                .into(imgFilm);

        //luotxem
        tvluotxem.setText("Lượt xem: " + item.getViews().toString());

        //the loại
//      tvtheloai.setText(item.getCategory());
        // Khai báo SpannableString với text lấy từ string Category item
        SpannableString theloaiString = new SpannableString("Genrus: " + item.getCategory());
        // set Style BOLD cho tiêu đề
        theloaiString.setSpan(new StyleSpan(Typeface.BOLD), 0, 6, 6);
        // Set spannable text vào cho TextView
        tvtheloai.setText(theloaiString);

        //dien viên
//        tvdienvien.setText(item.getActor());
        // Khai báo SpannableString với text lấy từ string Category item
        SpannableString actorString = new SpannableString("Actor: " + item.getActor());
        // set Style BOLD cho tiêu đề
        actorString.setSpan(new StyleSpan(Typeface.BOLD), 0, 5, 0);
        // Set spannable text vào cho TextView
        tvdienvien.setText(actorString);

        //ddaojj diễn
//        tvdaodien.setText(item.getDirector());
        // Khai báo SpannableString với text lấy từ string Category item
        SpannableString daodienString = new SpannableString("Director: " + item.getDirector());
        // set Style BOLD cho tiêu đề
        daodienString.setSpan(new StyleSpan(Typeface.BOLD), 0, 8, 0);
        // Set spannable text vào cho TextView
        tvdaodien.setText(daodienString);

        //nhà xuất bản
//        tvmanufacturer.setText(item.getManufacturer());
        // Khai báo SpannableString với text lấy từ string Category item
        SpannableString facturerString = new SpannableString("Manufacturer: " + item.getManufacturer());
        // set Style BOLD cho tiêu đề
        facturerString.setSpan(new StyleSpan(Typeface.BOLD), 0, 12, 0);
        // Set spannable text vào cho TextView
        tvmanufacturer.setText(facturerString);

        //thời lượng phim
//        tvthoiluong.setText(item.getDuration() + " minute");
        // Khai báo SpannableString với text lấy từ string Category item
        SpannableString thoiluongString = new SpannableString("Thời lượng phim: " + item.getDuration() + " minute");
        // set Style BOLD cho tiêu đề
        thoiluongString.setSpan(new StyleSpan(Typeface.BOLD), 0, 16, 0);
        // Set spannable text vào cho TextView
        tvthoiluong.setText(thoiluongString);

        String link = item.getLink();
        String[] split = link.split("=");
        idYT = split[1];
        ControlClick();
        restoringPrefernces();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.cueVideo(idYT);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        if(youTubeInitializationResult.isUserRecoverableError())
        {
            youTubeInitializationResult.getErrorDialog(InfoActivity.this, REQUEST_VIDEO);
        }
        else
        {
            Toasty.error(this, "Error !!", Toast.LENGTH_SHORT, true).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_VIDEO)
        {
            vv.initialize(API_KEY, InfoActivity.this);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void ControlClick() {
        tvxemthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //in Script
                //cat chuoi
//                String script = item.getDescription();
//                tvScript.setText(script);
//                //Hide chu xem them
                tvScript.setEllipsize(null);
                tvxemthem.setVisibility(View.GONE);
                tvScript.setMaxLines(10);
            }
        });

        imgthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //thay trai tim
                savingPrefrefernces();
            }
        });
    }


    public  void restoringPrefernces()
    {
        SharedPreferences pre = getSharedPreferences(prefname, MODE_PRIVATE);
        boolean chk = pre.getBoolean(idF, false);
//        boolean chk2 = chkLuuMK.isChecked();
        if(chk)
        {
            CustomLike();;
        }
        else
            UnCustomLike();
    }

    public void savingPrefrefernces()
    {
        SharedPreferences pre = getSharedPreferences(prefname, MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        boolean chk = pre.getBoolean(idF, false);
        if(chk) // true
        {
            editor.putBoolean(idF, false);
            UnCustomLike();
//            editor.clear();
        }
        else
        {
            editor.putBoolean(idF, true);
            CustomLike();
        }
        editor.commit(); // lưu dữ liệu thông qua commit();

//        pref.edit().putString(key, "value").apply()  lưu giá trị thông qua apply();

    }

    public  int checkLoginRemember()
    {
        SharedPreferences pre = getSharedPreferences(prefname, MODE_PRIVATE);
        boolean chk = pre.getBoolean(idF, false);
        if(chk)
        {
            return  1;
        }
        return -1;
    }

    private void CustomLike() {
        //thay trai tim
        imgthich.setImageResource(R.drawable.ic_like_orange2x);
        tvthich.setText("Đã thích");

        tvthich.setTextColor(Color.parseColor("#fd6003"));
    }

    private void UnCustomLike() {
        //thay trai tim
        imgthich.setImageResource(R.drawable.ic_like2x);
        tvthich.setText("Thích");

        tvthich.setTextColor(Color.parseColor("#FFFFFFFF"));
    }

}