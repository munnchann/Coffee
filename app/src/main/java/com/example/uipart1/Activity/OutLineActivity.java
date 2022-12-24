package com.example.uipart1.Activity;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.security.identity.AccessControlProfileId;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uipart1.R;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LoggingMXBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutLineActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;

//    private List<User> mListUser;
//    private User mUser;

    Button switchToLoginPageActivity;
    Button switchToSignUpPageActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_out_line);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


//        edtEmail = findViewById(R.id.edt_email);
//        edtPassword = findViewById(R.id.edt_password);
       btnLogin = findViewById(R.id.buttonLogin);

//        mListUser = new ArrayList<>();
//        getListUsers();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickLogin();
            }
        });

        switchToLoginPageActivity = findViewById(R.id.buttonLogin);
        switchToLoginPageActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities1();
            }
        });

        switchToSignUpPageActivity = findViewById(R.id.buttonSignUp);
        switchToSignUpPageActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities2();}
        });
    }

    private void clickLogin() {
        return;
    }

    private void switchActivities1() {
        Intent switchActivityIntent = new Intent(this, LoginPageActivity.class);
        startActivity(switchActivityIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    private void switchActivities2() {
        Intent switchActivityIntent = new Intent(this, SignUpPageActivity.class);
        startActivity(switchActivityIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

//    private void clickLogin() {
//        String strEmail = edtEmail.getText().toString().trim();
//        String strPassword = edtPassword.getText().toString().trim();
//
//        if (mListUser == null || mListUser.isEmpty()){
//            return;
//        }
//
//        boolean isHasUser = false;
//        for (User user : mListUser){
//            if (strEmail.equals(user.getEmail()) && strPassword.equals(user.getPassword())){
//                isHasUser = true;
//                mUser = user;
//                break;
//            }
//        }
//
//        if (isHasUser){
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////            Bundle bundle = new Bundle();
////            bundle.putSerializable("userObject", mUser);
////            intent.putExtra(bundle);
//            startActivity(intent);
//
//        } else {
//            Toast.makeText(LoginActivity.this,"Username or password not correct",Toast.LENGTH_SHORT).show();
//        }
//    }



//    private void getListUsers(){
//        LoginAPI.loginAPI.getListUsers("")
//                .enqueue(new Callback<List<User>>() {
//                    @Override
//                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                        mListUser = response.body();
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<User>> call, Throwable t) {
//                        Toast.makeText(LoginActivity.this,"Call API Error",Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
}