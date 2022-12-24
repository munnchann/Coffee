package com.example.uipart1.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uipart1.R;

import java.util.ArrayList;
import java.util.List;

import Domain.Users;
import api.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpPageActivity extends AppCompatActivity {

    Button switchToLoginPageActivity;
    EditText edtlastname, edtfirstname, edtemail, edtphone, edtpassword, edittextAddress;
    boolean isAllFieldsChecked = false;
    List<Users> mlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        edtfirstname = findViewById(R.id.edtfirstname);
        edtlastname = findViewById(R.id.edtlastname);
        edtemail = findViewById(R.id.edtemail);
        edtphone = findViewById(R.id.edtphone);
        edtpassword = findViewById(R.id.edtpassword);
        edittextAddress = findViewById(R.id.edittextAddress);
        mlist = new ArrayList<>();
        switchToLoginPageActivity = findViewById(R.id.buttonSignUpPage);
        switchToLoginPageActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities();
            }
        });
    }

    private void switchActivities() {
        isAllFieldsChecked = check();
        String phones = edtphone.getText().toString();
        Users check = new Users(phones);
        if (isAllFieldsChecked && validation(edtemail)) {
            ApiService.apiService.CheckPhone(check).enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    Users users1 = response.body();
                    System.out.println(users1.toString());
                    if (users1 != null) {
                        if (phones.equals(users1.phone)) {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(SignUpPageActivity.this);
                            alertDialog.setTitle("Phone already sign up");
                            alertDialog.setIcon(R.drawable.ic_sharp_error_24);
                            alertDialog.setMessage("Try another phone number");
                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog builder = alertDialog.create();
                            builder.show();
                        }else {
                            CallSignUp();
                        }
                    }else{
                        CallSignUp();
                    }


                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    Toast.makeText(SignUpPageActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void CallSignUp() {
        String first_name = edtfirstname.getText().toString();
        String last_name = edtlastname.getText().toString();
        String email = edtemail.getText().toString();
        String phone = edtphone.getText().toString();
        String password = edtpassword.getText().toString();
        String address = edittextAddress.getText().toString();
        Users users = new Users(first_name, last_name, email, phone, password, address);
        ApiService.apiService.RegistationUser(users).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                Users user = response.body();
                if (user != null) {
                    Intent switchActivityIntent = new Intent(SignUpPageActivity.this, LoginPageActivity.class);
                    startActivity(switchActivityIntent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                } else {
                    Toast.makeText(SignUpPageActivity.this, "Cannot sign up account", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(SignUpPageActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean check() {
        if (edtfirstname.length() == 0) {
            edtfirstname.setError("First name is required");
        }
        if (edtlastname.length() == 0) {
            edtlastname.setError("Last name is required");
            return false;
        }
        if (edtphone.length() > 10) {
            edtphone.setError("Phone must be maximum 10 number");
            return false;
        } else if (edtphone.length() < 10) {
            edtphone.setError("Phone must be 10 number");
            return false;
        } else if (edtphone.length() == 0) {
            edtphone.setError("Phone is required");
            return false;
        }
        if (edtpassword.length() == 0) {
            edtpassword.setError("Password is required");
            return false;
        } else if (edtpassword.length() < 6) {
            edtpassword.setError("Password must be minimum 6 characters");
            return false;
        }
        if(edittextAddress.length() == 0){
            edittextAddress.setError("Address is required");
            return false;
        }

        return true;
    }

    private boolean validation(EditText emails) {
        String emailInput = emails.getText().toString();
        if (!emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            return true;
        } else {
            // email.setError("Invalid email");
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(SignUpPageActivity.this);
            alertDialog.setTitle("Invalid email");
            alertDialog.setIcon(R.drawable.ic_baseline_warning_24);
            alertDialog.setMessage("Try again");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog builder = alertDialog.create();
            builder.show();
            return false;
        }

    }

}