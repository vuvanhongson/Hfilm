package com.example.hfilm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hfilm.data.model.Datum;
import com.example.hfilm.data.model.SOAnswersResponse;
import com.example.hfilm.data.remote.ApiUtils;
import com.example.hfilm.data.remote.SOService;
import com.example.hfilm.forgotpass.ForgotPassActivity;
import com.example.hfilm.login.Login;
import com.example.hfilm.register.Register;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import kotlin.jvm.internal.MagicApiIntrinsics;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    String prefnamelogin = "my_login";
    private FilmAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SOService mService;
    TextView tvTitle;
    Boolean isScrolling = false;
    LinearLayout layouttt, layoutlogin;
    Button btndangxuat, btnlogin, btndangky;
    View viewline;
    int page = 1;
    int per_page = 10;
    int currentItem, totalItems, scrollOutItems;
    ProgressBar progressBar;
    RelativeLayout relativeLayout;
    LinearLayoutManager manager;
    Toolbar toolbar;
//    final ActionBar abar = getSupportActionBar();
    ImageView btnaccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hide action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        tvTitle = findViewById(R.id.toolbar_title);
        tvTitle.setText("HFILM");
        tvTitle.setTypeface(Typeface.DEFAULT_BOLD);

        progressBar = findViewById(R.id.progress);
//        relativeLayout = findViewById(R.id.LayoutScroll);

        mService = ApiUtils.getInstance().getSOService();
        mRecyclerView = (RecyclerView) findViewById(R.id.rc_film);
        mAdapter = new FilmAdapter(this, new ArrayList<Datum>(0), new FilmAdapter.PostItemListener() {

            @Override
            public void onPostClick(long id) {
//                Toast.makeText(MainActivity.this, "Post id is" + id, Toast.LENGTH_SHORT).show();
            }
        });

//        RecyclerView.LayoutManager
        manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        loadAnswers();


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItem = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();

                if(isScrolling && ((currentItem + scrollOutItems) == totalItems) && totalItems > 1)
                {
                    isScrolling = false;
                    fetchData();
//                    Toast.makeText(MainActivity.this, "đang xem trang số " + String.valueOf(page), Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnaccount = findViewById(R.id.btnAccount);


        CallClick();

    }

    public void Anhxa(View bottomSheetView ) {
        layoutlogin = bottomSheetView.findViewById(R.id.LayoutLogin);
        layouttt = bottomSheetView.findViewById(R.id.LayoutTT);
        btndangxuat = bottomSheetView.findViewById(R.id.btnDangxuat);
        btnlogin = bottomSheetView.findViewById(R.id.btnDangNhap);
        btndangky = bottomSheetView.findViewById(R.id.btnRegister);
        viewline = bottomSheetView.findViewById(R.id.ViewLine);
    }

    public void loadAnswers() {
//        String.valueOf(per_page)
        mService.getAnswers2(String.valueOf(page), "10" ).enqueue(new Callback<SOAnswersResponse>() {
            @Override
            public void onResponse(Call<SOAnswersResponse> call, Response<SOAnswersResponse> response) {

                if(response.isSuccessful()) {
                    mAdapter.updateAnswers(response.body().getData());
                    page = response.body().getPaging().getCurrentPage() + 1;
                    Log.d("MainActivity", "posts loaded from API");
                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<SOAnswersResponse> call, Throwable t) {

                Log.d("MainActivity", "error loading from API");

            }
        });
    }

    private  void fetchData(){
//        relativeLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mService.getAnswers2(String.valueOf(page),"10").enqueue(new Callback<SOAnswersResponse>() {
                    @Override
                    public void onResponse(Call<SOAnswersResponse> call, Response<SOAnswersResponse> response) {

                        if(response.isSuccessful()) {
//                            mRecyclerView.notifyItemInserted(rowsArrayList.size() - 1);
                            mAdapter.InsertAnswers(response.body().getData());
                            page = response.body().getPaging().getCurrentPage() + 1;
                            Log.d("MainActivity", "posts loaded from API");
                        }else {
                            int statusCode  = response.code();
                            // handle request errors depending on status code
                        }
                    }

                    @Override
                    public void onFailure(Call<SOAnswersResponse> call, Throwable t) {

                        Log.d("MainActivity", "error loading from API");

                    }

                });
                progressBar.setVisibility(View.GONE);
//                relativeLayout.setVisibility(View.GONE);
            }
        },2000);

    }

    public void CallClick(){
        btnaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        MainActivity.this, R.style.BottomSheepDialogTheme);
                View bottomSheetView = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_bottom_sheet, null);

                Anhxa(bottomSheetView);
                CheckViewLogin(bottomSheetView);

                if(checkLoginRemember() > 0) {
                    SharedPreferences pre = MainActivity.this.getSharedPreferences(prefnamelogin, MODE_PRIVATE);
                    String ten = pre.getString("full_name", "");
                    String id = pre.getString("id", "");
                    String email = pre.getString("email", "");
                    TextView tvName = bottomSheetView.findViewById(R.id.tvTenTK);
                    TextView tvEmail = bottomSheetView.findViewById(R.id.tvEmail);
                    TextView tvID = bottomSheetView.findViewById(R.id.tvID);
                    ImageView imgHinhSP = bottomSheetView.findViewById(R.id.imgHinhSP);
                    tvName.setText(ten);
                    tvEmail.setText(id);
                    tvID.setText(email);
                    imgHinhSP.setImageResource(R.drawable.hinh10);
                }

                Xulyxukien(bottomSheetDialog);

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });



    }

    public void Xulyxukien(BottomSheetDialog bottomSheetDialog) {

        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.cancel();
                SharedPreferences pre = MainActivity.this.getSharedPreferences(prefnamelogin, MODE_PRIVATE);
                pre.edit().clear().commit();
                Intent intent = new Intent(MainActivity.this, Login.class);
                MainActivity.this.startActivity(intent);
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.cancel();
                Intent intent = new Intent(MainActivity.this, Login.class);
                MainActivity.this.startActivity(intent);
            }
        });
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.cancel();
                Intent intent = new Intent(MainActivity.this, Register.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }

    public  int checkLoginRemember()
    {
        SharedPreferences pre = MainActivity.this.getSharedPreferences(prefnamelogin, MODE_PRIVATE);
        boolean chk = pre.getBoolean("SaveLogin", false);
        if(chk)
        {
            return  1;
        }
        else {
            return -1;
        }
    }

    public void CheckViewLogin(View bottomSheetView ){
        if(checkLoginRemember() < 0)
        {
            layoutlogin.setVisibility(bottomSheetView.VISIBLE);
            layouttt.setVisibility(bottomSheetView.GONE);
            viewline.setVisibility(bottomSheetView.GONE);
            btndangxuat.setVisibility(bottomSheetView.GONE);
        }
        else
        {
            layoutlogin.setVisibility(bottomSheetView.GONE);
            layouttt.setVisibility(bottomSheetView.VISIBLE);
            viewline.setVisibility(bottomSheetView.GONE);
            btndangxuat.setVisibility(bottomSheetView.VISIBLE);
        }
    }


}