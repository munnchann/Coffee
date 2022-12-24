package com.example.uipart1.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uipart1.R;
import com.google.android.gms.common.api.Api;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import java.io.File;
import java.io.IOException;
import java.util.List;

import Database.InforDatabase;
import Domain.InforUser;
import Domain.Users;
import Helper.Const;
import Helper.RealPathUtil;
import api.ApiService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class profile_edit extends AppCompatActivity {
    private static final int MY_RESQUEST_CODE = 10;
    ImageView backBtn, log_out, img;
    EditText name, sdt, mail, ltname, address;
    TextView id, save, txtlogout;
    boolean isAllFieldsChecked = false;
    private Uri mUri;
    static ProgressDialog progressDialog;
    public static final String TAG = profile_edit.class.getName();
    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG, "onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            img.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        initView();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Events();
        getIdUser();
        GetUser();
    }

    private void Events() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile_edit.this, setting.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile_edit.this, LoginPageActivity.class);
                InforDatabase.getInstance(profile_edit.this).inforDao().deleteAll();
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });
        txtlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile_edit.this, LoginPageActivity.class);
                InforDatabase.getInstance(profile_edit.this).inforDao().deleteAll();
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    openGallery();
                    return;
                }
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    String[] permision = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permision, MY_RESQUEST_CODE);
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUri != null){
                    progressDialog.show();
                    CallUpdateUser();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_RESQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
    }

    private void initView() {
        backBtn = findViewById(R.id.backBtn);
        log_out = findViewById(R.id.log_out);
        img = findViewById(R.id.editImg);
        name = findViewById(R.id.editName);
        sdt = findViewById(R.id.editPhone);
        mail = findViewById(R.id.editEmail);
        id = findViewById(R.id.editid);
        save = findViewById(R.id.txtSave);
        ltname = findViewById(R.id.editLastname);
        address = findViewById(R.id.editAddress);
        txtlogout = findViewById(R.id.txtlogout);
        progressDialog = new ProgressDialog(profile_edit.this);
        progressDialog.setMessage("Please Wait...");
    }

    private void getIdUser() {
        List<InforUser> infor = InforDatabase.getInstance(this).inforDao().getUser();
        for (int i = 0; i < infor.size(); i++) {
            id.setText(String.valueOf(infor.get(i).getIdUser()));
        }
    }

    private void CallUpdateUser() {
        isAllFieldsChecked = check();
        int iduser = Integer.parseInt(id.getText().toString());
        String first_name = name.getText().toString();
        String email = mail.getText().toString();
        String phone = sdt.getText().toString();
        String last_name = ltname.getText().toString();
        String addressUser = address.getText().toString();
        RequestBody requestBodyId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(iduser));
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), first_name);
        RequestBody requestBodyLastName= RequestBody.create(MediaType.parse("multipart/form-data"), last_name);
        RequestBody requestBodyMail = RequestBody.create(MediaType.parse("multipart/form-data"), email);
        RequestBody requestBodyPhone= RequestBody.create(MediaType.parse("multipart/form-data"), phone);
        RequestBody requestBodyAddress= RequestBody.create(MediaType.parse("multipart/form-data"), addressUser);
        String strRealPath = RealPathUtil.getRealPath(this,mUri);
        Log.e("Coffee", strRealPath);
        File file = new File(strRealPath);
        RequestBody requestBodyImage = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part multipartImg = MultipartBody.Part.createFormData(Const.KEY_IMG, file.getName(), requestBodyImage);
        if (isAllFieldsChecked && validation(mail)) {
            ApiService.apiService.updateUsers(requestBodyId, requestBody, requestBodyLastName, requestBodyMail, requestBodyPhone, multipartImg, requestBodyAddress).enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    Users users = response.body();
                    if (users != null) {
                        progressDialog.dismiss();
                        GetUser();
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    Toast.makeText(profile_edit.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void GetUser() {
        int idus = Integer.parseInt(id.getText().toString());
        ApiService.apiService.getUsersById(idus).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                Users us = response.body();
                if (us != null) {
                    name.setText(us.getFirst_name());
                    mail.setText(us.getEmail());
                    sdt.setText(us.getPhone());
                    ltname.setText(us.getLast_name());
                    address.setText(us.getAddress());
                    String baseUrl = "http://172.16.10.123:4000/image/";
                    //  String baseUrl = "http://10.0.2.2:4000/image/";
                    if (us.image == null) {
                        Picasso picasso = new Picasso.Builder(getApplicationContext())
                                .listener(new Picasso.Listener() {
                                    @Override
                                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                        Log.e("PICASSO", uri.toString(), exception);
                                    }
                                })
                                .build();

                        picasso.load("https://i.pinimg.com/564x/04/f9/c4/04f9c49c98f868db86285ff3692e9cc3.jpg")
                                .into(img);
                    }
                    Picasso picasso = new Picasso.Builder(getApplicationContext())
                            .listener(new Picasso.Listener() {
                                @Override
                                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                    Log.e("PICASSO", uri.toString(), exception);
                                }
                            })
                            .build();

                    picasso.load(baseUrl + us.getImage())
                            .into(img);
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(profile_edit.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean check() {
        if (name.length() == 0) {
            name.setError("First name is required");
            return false;
        }
        if(ltname.length() == 0){
            ltname.setError("Last name is required");
            return false;
        }
        if (sdt.length() > 10) {
            sdt.setError("Phone must be maximum 10 number");
            return false;
        } else if (sdt.length() < 10) {
            sdt.setError("Phone must be 10 number");
            return false;
        } else if (sdt.length() == 0) {
            sdt.setError("Phone is required");
            return false;
        }
        if(address.length() == 0){
            address.setError("Address is required");
            return false;
        }

        return true;
    }

    private boolean validation(EditText email) {
        String emailInput = email.getText().toString();
        if (!emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            return true;
        } else {
            // email.setError("Invalid email");
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(profile_edit.this);
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