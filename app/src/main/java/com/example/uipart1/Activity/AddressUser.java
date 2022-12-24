package com.example.uipart1.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uipart1.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.AddressUserAdapter;
import Database.AddressDatabase;
import Database.CartDatabase;
import Domain.Address;
import Domain.CartDomain;

public class AddressUser extends AppCompatActivity {
    TextView edtAddress;
    RecyclerView rcvAddress;
    ImageView imgsend, backimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_user);
        edtAddress = findViewById(R.id.edtAddress);
        imgsend = findViewById(R.id.imgsend);
        rcvAddress = findViewById(R.id.rcvAddress);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        backimg = findViewById(R.id.backimg);
        setAnimation(R.anim.layout_anim_right_to_left);
        imgsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getView();
            }
        });
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressUser.this, CartActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });
    }

    private void getView(){
        String name = String.valueOf(edtAddress.getText());
        Boolean check = AddressDatabase.getInstance(this).addressDao().isexist(name);
        try{
            if(check == false){
                Address ad = new Address(name);
                AddressDatabase.getInstance(this).addressDao().InsertAddress(ad);
                setAnimation(R.anim.layout_anim_right_to_left);
            }else{
              //  Toast.makeText(this, "Exist address", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddressUser.this);
                alertDialog.setTitle("Address already exist");
                alertDialog.setIcon(R.drawable.ic_baseline_warning_24);
                alertDialog.setMessage("Add another address");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog builder = alertDialog.create();
                builder.show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void setAnimation(int animResource){
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(AddressUser.this, animResource);
        rcvAddress.setLayoutAnimation(layoutAnimationController);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcvAddress = findViewById(R.id.rcvAddress);
        rcvAddress.setLayoutManager(layoutManager);
        List<Address> addresses = AddressDatabase.getInstance(this).addressDao().getAllAddress();
        AddressUserAdapter addressUserAdapter = new AddressUserAdapter(addresses);
        rcvAddress.setAdapter(addressUserAdapter);
    }
}