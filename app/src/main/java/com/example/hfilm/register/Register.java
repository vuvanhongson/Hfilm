package com.example.hfilm.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hfilm.R;
import com.example.hfilm.data.model.SOAnswersResponse;
import com.example.hfilm.data.model.SOAnswersResponseLogin;
import com.example.hfilm.data.model.SOAnswersResponseRegister;
import com.example.hfilm.data.remote.ApiUtils;
import com.example.hfilm.data.remote.SOService;
import com.example.hfilm.login.Login;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    TextView tvScriptDK;
    TextView tvhoten, tvemail, tvmatkhau, tvrematkhau;
    EditText editHoten, editemail, editmatkhau, editrematkhau;
    Button btnDangKy;
    ImageView btnclose;
    String ten, email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Hide action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        anhxa();// ánh xạ tv

        xulyClick();

        loadTextViewScript();

    }

    public void loadTextViewScript() {
        tvScriptDK = findViewById(R.id.tvScriptDangky);

        // Khai báo SpannableString với text lấy từ string resource
        CharSequence exampleForSpannable = getText(R.string.ScriptDangky);
        SpannableString spannableString = new SpannableString(exampleForSpannable);

        // Làm cho string (3) có thể click được, đồng thời có màu xanh, khi click vào hiển thị Toast. Vị trí từ 128 đến 150
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Toasty.info(getApplication(), "Here is some info for you.", Toast.LENGTH_SHORT, true).show();
            }

        };
        spannableString.setSpan(clickableSpan, 50, 68, 0);

        // Làm chi string (4) có thể click được, đồng thời có màu đen, bự gấp rưỡi, khi click vào thì đến link. Vị trí từ 178 đến hết string
        spannableString.setSpan(new URLSpan("https://yellowcodebooks.com/"), 72, 88, 0);

        // Set màu đỏ cho string (1) (2). Vị trí từ 50 đến 68
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#fd6003")), 50, 68, 0);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#fd6003")), 72, 88, 0);
        //set underline cho string (1) (2)
        spannableString.setSpan(new UnderlineSpan(), 50, 68, 0);
        spannableString.setSpan(new UnderlineSpan(), 72, 88, 0);

        // Đoạn này làm cho ClickableSpans và URLSpans có thể hoạt động
        tvScriptDK.setMovementMethod(LinkMovementMethod.getInstance());

        // Set spannable text vào cho TextView
        tvScriptDK.setText(spannableString);

    }

    public void anhxa(){
        tvhoten = findViewById(R.id.tvchuadienhoten);
        tvemail = findViewById(R.id.tvchuadienemail);
        tvmatkhau = findViewById(R.id.tvchuadienmakhau);
        tvrematkhau = findViewById(R.id.tvchuaxacnhanmatkhau);

        //ánh xạ EditText
        editHoten = findViewById(R.id.editHoten);
        editemail = findViewById(R.id.editEmail);
        editmatkhau = findViewById(R.id.editMaKhau);
        editrematkhau = findViewById(R.id.editreMatKhau);

        btnDangKy = findViewById(R.id.btnDangky);
        btnclose = findViewById(R.id.btnexit);

    }

    public void visibility(EditText edittext, TextView textview){
        if(edittext.getText().length() <= 0)
        {
            textview.setVisibility(View.VISIBLE);
        }
        else
            textview.setVisibility(View.GONE);

    }

    public void isEmpty(){
        visibility(editHoten, tvhoten);
        visibility(editemail, tvemail);
        visibility(editmatkhau, tvmatkhau);
        visibility(editrematkhau, tvrematkhau);

    }

    public void TextChange(EditText edittext, TextView textView){
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                visibility(edittext, textView);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void xulyClick(){
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dang ky
                isEmpty();
                ten = String.valueOf(editHoten.getText());
                email =  String.valueOf(editemail.getText());
                pass = String.valueOf(editmatkhau.getText());
                loadAnswers();
            }
        });

        TextChange(editHoten, tvhoten);
        TextChange(editemail, tvemail);
        TextChange(editmatkhau, tvmatkhau);
        TextChange(editrematkhau, tvrematkhau);

        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
                builder.setTitle("Quay lại màn hình đăng nhập?");
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

    public void loadAnswers() {

        Call<SOAnswersResponseLogin> call = ApiUtils.getInstance()
                .getSOService()
                .postRegister(ten, email, pass);
        call.enqueue(new Callback<SOAnswersResponseLogin>() {
            @Override
            public void onResponse(Call<SOAnswersResponseLogin> call, Response<SOAnswersResponseLogin> response) {
                if(response.isSuccessful()) {
//                    response.body().getData());
                    Toasty.success(getApplication(), "Đăng ký thành công", Toast.LENGTH_SHORT, true).show();
                    Log.d("Register", "posts loaded from API");
                        if(response.body().getError() == false)
                        {onBackPressed();}
                        else{Toasty.warning(getApplication(), response.body().getMessage(), Toast.LENGTH_SHORT, true).show();}
                }else {
                    int statusCode  = response.code();
                    Toasty.warning(getApplication(), response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                    Log.d("Register", "posts FAIL from API");
                }
            }

            @Override
            public void onFailure(Call<SOAnswersResponseLogin> call, Throwable t) {
                Toasty.error(getApplication(), "Vui lòng điền đủ thông tin!", Toast.LENGTH_SHORT, true).show();
                Log.d("Register", "error loading from API");
            }
        });
    }

}