package com.example.uipart1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.uipart1.R;

public class PayActivity extends AppCompatActivity {
    TextView btnpayonl, btnmoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        btnmoney = findViewById(R.id.btnmoney);
        btnpayonl = findViewById(R.id.btnpayonl);
        ChoosePayment();
    }

    private void ChoosePayment(){
        btnmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String payByMoney = btnmoney.getText().toString();
                Intent intent = new Intent(PayActivity.this, CartActivity.class);
                intent.putExtra("money", payByMoney);
                startActivity(intent);
            }
        });

        btnpayonl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String payByOnl = btnpayonl.getText().toString();
                Intent intent = new Intent(PayActivity.this, CartActivity.class);
                intent.putExtra("paypal", payByOnl);
                startActivity(intent);
            }
        });
    }

}