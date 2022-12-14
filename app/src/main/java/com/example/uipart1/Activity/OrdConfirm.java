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

import java.util.ArrayList;
import java.util.List;

import Database.CartDatabase;
import Domain.CartDomain;

public class OrdConfirm extends AppCompatActivity {
    private TextView wait, ordSucess, txtamount, txtTotal, textView12;
    private Button btnBackHome;
    List<CartDomain> cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ord_confirm);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        wait = (TextView) findViewById(R.id.wait);
        ordSucess = (TextView) findViewById(R.id.ordSucess);
        btnBackHome = (Button) findViewById(R.id.btnBackHome);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        textView12 = (TextView) findViewById(R.id.textView12);
        txtamount = (TextView) findViewById(R.id.txtamount);
        cart = new ArrayList<>();
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrdConfirm.this, HomeActivity.class);
                CartDatabase.getInstance(OrdConfirm.this).cartDao().deleteAll();
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });
        txtTotal.setText(getIntent().getStringExtra("totals"));
    }

}