package com.example.uipart1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.uipart1.R;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.List;
import java.util.concurrent.Delayed;

import Database.InforDatabase;
import Domain.InforUser;
import kotlinx.coroutines.Delay;

public class SplashActivity extends AppCompatActivity {

    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        logo = findViewById(R.id.logo);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        logo.startAnimation(animation);

        if(InforDatabase.getInstance(this).inforDao().getUser().size() > 0){
            Intent iHome = new Intent(SplashActivity.this, HomeActivity.class);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(iHome);
                }
            }, 4000);
        }else{
            Intent iHome = new Intent(SplashActivity.this, OutLineActivity.class);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(iHome);
                }
            }, 4000);
        }



    }
}