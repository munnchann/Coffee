package com.example.uipart1.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uipart1.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import Database.InforDatabase;
import Domain.InforUser;
import Domain.Users;
import api.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPageActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    List<Users> mListUser;
    Button switchToHomeActivity;
    TextView switchToSignUpPageActivity, edt_email, edt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mListUser = new ArrayList<>();
        edt_password = findViewById(R.id.edt_password);
        edt_email = findViewById(R.id.edt_email);
        switchToHomeActivity = findViewById(R.id.buttonLoginPage);

        switchToHomeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallLogin();
            }
        });

        switchToSignUpPageActivity = findViewById(R.id.textViewSignUp);
        switchToSignUpPageActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities4();
            }
        });
    }


    private void CallLogin(){
        String sdt = edt_email.getText().toString();
        String pass = edt_password.getText().toString();
        Users users = new Users(sdt, pass);
        ApiService.apiService.Login(users).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                Users users = response.body();
                if (users != null) {
                    if (sdt.equals(users.phone)) {
                        int id = users.id;
                        String name = users.first_name;
                        String lastname = users.last_name;
                        String email = users.email;
                        InforUser inf = new InforUser(id,name,lastname,email,sdt,pass);
                        System.out.println(inf.toString());
                        InforDatabase.getInstance(LoginPageActivity.this).inforDao().InsertInformationUser(inf);
                        Intent switchActivityIntent = new Intent(LoginPageActivity.this, HomeActivity.class);
                        startActivity(switchActivityIntent);
                    } else{
                        System.out.println("Phone or password incorect");
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginPageActivity.this);
                        alertDialog.setTitle("Phone or password incorect");
                        alertDialog.setIcon(R.drawable.ic_sharp_error_24);
                        alertDialog.setMessage("Try again");
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog builder = alertDialog.create();
                        builder.show();
                    }

                }else{
                    System.out.println("Phone or password incorect");
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginPageActivity.this);
                    alertDialog.setTitle("Phone or password incorect");
                    alertDialog.setIcon(R.drawable.ic_sharp_error_24);
                    alertDialog.setMessage("Try again");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog builder = alertDialog.create();
                    builder.show();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(LoginPageActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void switchActivities4() {
        Intent switchActivityIntent = new Intent(this, SignUpPageActivity.class);
        startActivity(switchActivityIntent);
    }
//        tabLayout = findViewById(R.id.tabLayout);
//        viewPager = findViewById(R.id.view_pagelogin);
//
//        tabLayout.addTab(tabLayout.newTab().setText("Login"));
//        tabLayout.addTab(tabLayout.newTab().setText("Signup"));
//        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);


}

