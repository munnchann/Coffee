package com.example.uipart1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.uipart1.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PayPalActivity extends AppCompatActivity {
    TextView totaltxt,setstt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal);
        totaltxt = findViewById(R.id.totaltxt);
        setstt = findViewById(R.id.setstt);
        Intent intent = getIntent();
        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentTotal"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showDetails(JSONObject response, String paymentTotal) {
        try{
            totaltxt.setText(response.getString(String.format(paymentTotal,"$")));
            setstt.setText(response.getString("state"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}