package com.example.uipart1.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uipart1.R;

import Database.CartDatabase;

public class ConfirmPay extends AppCompatActivity {
    private TextView ordPay, paypal, totalpay, dolorr, ship;
    private Button btnHomeBack;
    private ImageView imgSucess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pay);
        ordPay = (TextView) findViewById(R.id.ordPay);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        paypal = (TextView) findViewById(R.id.paypal);
        btnHomeBack = (Button) findViewById(R.id.btnHomeBack);
        totalpay = (TextView) findViewById(R.id.totalpay);
        ship = (TextView) findViewById(R.id.ship);
        dolorr = (TextView) findViewById(R.id.dolorr);
        imgSucess = (ImageView) findViewById(R.id.imgSucess);
        btnHomeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmPay.this, HomeActivity.class);
                CartDatabase.getInstance(ConfirmPay.this).cartDao().deleteAll();
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });
        totalpay.setText(getIntent().getStringExtra("paymentTotal"));
    }
}