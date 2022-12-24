package com.example.uipart1.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.uipart1.R;

public class setting extends AppCompatActivity {
    RelativeLayout switchToEditProfileActivity, rlvPass;
    ImageView img_back_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_setting);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        switchToEditProfileActivity = findViewById(R.id.edtProfile);
        rlvPass = findViewById(R.id.rlvPass);
        img_back_home = findViewById(R.id.img_back_home);
        switchToEditProfileActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities6();
            }
        });
        rlvPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(setting.this, ChangePassword.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });

        img_back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(setting.this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });
    }

    private void switchActivities6() {
        Intent switchActivityIntent = new Intent(this, profile_edit.class);
        startActivity(switchActivityIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
}