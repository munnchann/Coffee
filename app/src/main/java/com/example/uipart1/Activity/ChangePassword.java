package com.example.uipart1.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uipart1.R;

import java.util.List;

import Database.InforDatabase;
import Domain.InforUser;
import Domain.Users;
import api.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {

   EditText editTextTextPassword, newpassword, confirm_password;
   ImageView sendCheck, imgUp, imgChange;
    ConstraintLayout mainnew,mainconfirm;
    TextView idUserCheck, imgConfirm, imgNew;
    boolean isAllFieldsChecked = false;
    static ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
        setVisible();
        getIdUser();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void init(){
        confirm_password = findViewById(R.id.confirm_password);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        newpassword = findViewById(R.id.newpassword);
        sendCheck = findViewById(R.id.sendCheck);
        imgUp = findViewById(R.id.imgUp);
        mainnew = findViewById(R.id.mainnew);
        mainconfirm = findViewById(R.id.mainconfirm);
        idUserCheck = findViewById(R.id.idUserCheck);
        imgChange = findViewById(R.id.backChangePass);
        imgConfirm = findViewById(R.id.imgConfirm);
        imgNew = findViewById(R.id.imgNew);
        progressDialog = new ProgressDialog(ChangePassword.this);
        progressDialog.setMessage("Please Wait!");
    }
    private void getIdUser() {
        List<InforUser> infor = InforDatabase.getInstance(this).inforDao().getUser();
        for (int i = 0; i < infor.size(); i++) {
            idUserCheck.setText(String.valueOf(infor.get(i).getIdUser()));
        }
    }
    private void setVisible(){
        mainnew.setVisibility(View.GONE);
        mainconfirm.setVisibility(View.GONE);
        imgUp.setVisibility(View.GONE);
        imgConfirm.setVisibility(View.GONE);
        imgNew.setVisibility(View.GONE);
        sendCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextTextPassword.equals("")){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChangePassword.this);
                            alertDialog.setTitle("Old password null");
                            alertDialog.setIcon(R.drawable.ic_sharp_error_24);
                            alertDialog.setMessage("Enter old password first!");
                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog builder = alertDialog.create();
                            builder.show();
                }else{
                    CallApiPassword();
                }
            }
        });
        imgUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callChangePassword();
            }
        });

        imgChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePassword.this, setting.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });
    }
    private void CallApiPassword(){

        int id = Integer.parseInt(idUserCheck.getText().toString());
        String password = editTextTextPassword.getText().toString();
        Users s = new Users(id,password);
        ApiService.apiService.checkPassword(s).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                Users users = response.body();
                if(users!=null){
                    if(password.equals(users.password)){
                        mainnew.setVisibility(View.VISIBLE);
                        mainconfirm.setVisibility(View.VISIBLE);
                        imgUp.setVisibility(View.VISIBLE);
                        imgConfirm.setVisibility(View.VISIBLE);
                        imgNew.setVisibility(View.VISIBLE);
                        overridePendingTransition(R.anim.right_to_left, R.anim.right_to_left);
                    }else{
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChangePassword.this);
                        alertDialog.setTitle("Wrong password");
                        alertDialog.setIcon(R.drawable.ic_sharp_error_24);
                        alertDialog.setMessage("Try again!");
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog builder = alertDialog.create();
                        builder.show();
                        setVisible();
                    }
                }else{
                    System.out.println("Null");
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(ChangePassword.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callChangePassword() {
        isAllFieldsChecked = check();
        int id = Integer.parseInt(idUserCheck.getText().toString());
        String pass = newpassword.getText().toString();
        String passold = editTextTextPassword.getText().toString();
        Users us = new Users(id, pass);
        if (isAllFieldsChecked) {
            ApiService.apiService.changePassword(us).enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    Users users = response.body();
                    if (users != null) {
                        if(pass.equals(passold)){
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChangePassword.this);
                            alertDialog.setTitle("Looks like you are entering the old password");
                            alertDialog.setIcon(R.drawable.ic_baseline_warning_24);
                            alertDialog.setMessage("Enter new password");
                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog builder = alertDialog.create();
                            builder.show();
                        }else{
                            Intent intent = new Intent(ChangePassword.this, LoginPageActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                            Toast.makeText(ChangePassword.this, "Change Password Success", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    Toast.makeText(ChangePassword.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean check() {
        String confirm = confirm_password.getText().toString().trim();
        String new_pass = newpassword.getText().toString().trim();
        if (newpassword.length() == 0) {
            newpassword.setError("Password is required");
            return false;
        } else if (newpassword.length() < 6) {
            newpassword.setError("Password must be minimum 6 characters");
            return false;
        }
        if(!new_pass.equals(confirm)){
            confirm_password.setError("Password would not be matched");
            return false;
        }

        return true;
    }
}