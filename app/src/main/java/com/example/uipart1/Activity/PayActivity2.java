package com.example.uipart1.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.uipart1.R;

public class PayActivity2 extends AppCompatActivity {
    TextView btnstr, btnmn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay2);
        ChoosePayment();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }

    private void ChoosePayment(){
        btnmn = (TextView) findViewById(R.id.btnmn);
        btnstr = (TextView) findViewById(R.id.btnstr);
        btnmn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String payByMoney = btnmn.getText().toString();
              Intent intent = new Intent(PayActivity2.this, CartActivity.class);
              intent.putExtra("money", payByMoney);
              startActivity(intent);
          }
      });

        btnstr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String payByOnl = btnstr.getText().toString();
                Intent intent = new Intent(PayActivity2.this, CartActivity.class);
                intent.putExtra("paypal", payByOnl);
                startActivity(intent);
            }
        });
    }
}