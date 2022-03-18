package com.example.hfilm.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hfilm.Info.InfoActivity;
import com.example.hfilm.MainActivity;
import com.example.hfilm.R;
import com.example.hfilm.data.model.SOAnswersResponse;
import com.example.hfilm.data.model.SOAnswersResponseLogin;
import com.example.hfilm.data.model.User;
import com.example.hfilm.data.remote.ApiUtils;
import com.example.hfilm.data.remote.SOService;
import com.example.hfilm.forgotpass.ForgotPassActivity;
import com.example.hfilm.register.Register;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    ImageView btnClose;
    Button btnLogin, btnquenmk;
    LoginButton btnLoginFace;
    EditText tvemail, tvpass;
    User user;
    TextView tvdangky;
    String email, pass;
//    private SOService mService;
    String prefnamelogin = "my_login";
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hide action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btnClose = findViewById(R.id.btnclose);
        btnLogin = findViewById(R.id.btnLogin);
        btnquenmk = findViewById(R.id.btnquenmk);
        tvemail = findViewById(R.id.tvEmail);
        tvpass = findViewById(R.id.tvPass);
        tvdangky = findViewById(R.id.tvDangKy);
        btnLoginFace = findViewById(R.id.login_button);

        btnLoginFace = (LoginButton) findViewById(R.id.login_button);
        //https://developers.facebook.com/docs/facebook-login/permissions#reference
        btnLoginFace.setReadPermissions("email");

        callbackManager = CallbackManager.Factory.create();


        ControlButton1();

        // Callback registration
        btnLoginFace.setReadPermissions("email");
        // If using in a fragment
//        btnLoginFace.setFragment(this);
        btnLoginFace.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
//                info.setText("User ID: " + loginResult.getAccessToken().getUserId() + "\n" + "Auth Token: " + loginResult.getAccessToken().getToken());
                Log.d("Login Facebook Success:" , "User ID: " + loginResult.getAccessToken().getUserId() + "\n" + "Auth Token: " + loginResult.getAccessToken().getToken());
                String fullname, facebookid;

                Profile profile = Profile.getCurrentProfile();
                if(profile != null) {
                    facebookid = profile.getId();
//                     String profileUrl = "https://graph.facebook.com/" + facebookid + "/picture?type=large"; 
                    fullname = profile.getName();

                    Log.d("Login Facebook" , facebookid );
                    Log.d("Login Facebook" , fullname );
                }

                GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {
                                if (response.getError() != null) {
                                    // handle error
                                } else {
//                                    String email = response.getJSONObject().get("email").toString();
                                    String email = me.optString("email");
                                    String id = me.optString("id");
                                    String name = me.optString("name");
                                    Log.d("Login Facebook 2" , email );
                                    Log.d("Login Facebook 2" , id );
                                    Log.d("Login Facebook 2" , name );
                                    // send email and id to your web server

                                    LoginFaccebook(name, id, id);
                                }
                            }
                        }).executeAsync();
                
            }

            @Override
            public void onCancel() {
//                info.setText("Login attempt canceled.");
                Log.d("Login Cancel: ", "Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException error) {
//                info.setText("Login attempt failed.");
                Log.d("Login Error", "Login attempt failed.");
            }
        });
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        }


    private void loadAnswer() {

        Call<SOAnswersResponseLogin> call = ApiUtils.getInstance()
                .getSOService()
                .postLogin(email, pass);
        call.enqueue(new Callback<SOAnswersResponseLogin>() {
//        mService.postLogin(email, pass).enqueue(new Callback<SOAnswersResponseLogin>() {
            @Override
            public void onResponse(Call<SOAnswersResponseLogin> call, Response<SOAnswersResponseLogin> response) {
                if(response.isSuccessful() && response.body().getData() != null){
                    savingPrefrefernces(response.body().getData());
                    Toasty.success(getApplication(), "Đăng nhập thành công!", Toast.LENGTH_SHORT, true).show();
                    finish();
                }
                else{
                    Toasty.error(getApplication(), "Sai mật khẩu!", Toast.LENGTH_SHORT, true).show();
                    Log.e("mbbd", " not Success");
                }
            }

            @Override
            public void onFailure(Call<SOAnswersResponseLogin> call, Throwable t) {
                Toasty.error(getApplication(), "Vui lòng điển đủ thông tin", Toast.LENGTH_SHORT, true).show();
                Log.e("mbbd", " not Success onFailure");
            }
        });

    }

    private void LoginFaccebook(String fullname, String email, String facebookid) {

        Call<SOAnswersResponseLogin> call = ApiUtils.getInstance()
                .getSOService()
                .postLoginFaceboook(fullname, email, facebookid);
        call.enqueue(new Callback<SOAnswersResponseLogin>() {
            //        mService.postLogin(email, pass).enqueue(new Callback<SOAnswersResponseLogin>() {
            @Override
            public void onResponse(Call<SOAnswersResponseLogin> call, Response<SOAnswersResponseLogin> response) {
                if(response.isSuccessful() && response.body().getData() != null){
                    savingPrefrefernces(response.body().getData());
                    Toasty.success(getApplication(), "Đăng nhập thành công!", Toast.LENGTH_SHORT, true).show();
                    finish();
                }
                else{
                    Toasty.error(getApplication(), "Sai mật khẩu!", Toast.LENGTH_SHORT, true).show();
                    Log.e("mbbd", " not Success");
                }
            }

            @Override
            public void onFailure(Call<SOAnswersResponseLogin> call, Throwable t) {
                Toasty.error(getApplication(), "Vui lòng điển đủ thông tin", Toast.LENGTH_SHORT, true).show();
                Log.e("mbbd", " not Success onFailure");
            }
        });

    }

    private void ControlButton1() {


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
                builder.setTitle("Bạn có muốn thoát đăng nhập không?");
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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email =tvemail.getText().toString();
                pass = tvpass.getText().toString();
                loadAnswer();
//                if(user != null)
//                {
////                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
////                    startActivity(intent);
//                    savingPrefrefernces();
//                    Toasty.success(getApplication(), "Đăng nhập thành công!", Toast.LENGTH_SHORT, true).show();
//                    finish();
//                }
//                else
//                {
//                    Toasty.error(getApplication(), "Sai mật khẩu!", Toast.LENGTH_SHORT, true).show();
//                }

            }
        });

        btnquenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPassActivity.class);
                startActivity(intent);
            }
        });

        tvdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

        btnLoginFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                printHashKey(getApplicationContext());//

            }
        });



    } // end controllButtom


    public void savingPrefrefernces(User user)
    {
        SharedPreferences pre = getSharedPreferences(prefnamelogin, MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
            editor.putBoolean("SaveLogin", true);
            editor.putString("email", tvemail.getText().toString());
            editor.putString("full_name", user.getFullName().toString());
            editor.putString("id", user.getId().toString());
            editor.putString("access_token", user.getAccessToken().toString());

        editor.commit(); // lưu dữ liệu thông qua commit();

//        pref.edit().putString(key, "value").apply()  lưu giá trị thông qua apply();

    } //end savingPrefrefernces()


    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("TAG", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("TAG", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("TAG", "printHashKey()", e);
        }
    }


}