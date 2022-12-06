package com.example.uipart1.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.uipart1.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.MenuApdater;
import Database.CartDatabase;
import Domain.CartDomain;
import Domain.MenuDomain;
import Helper.CartManagement;
import api.ApiService;
import api.Cart;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowDetailProduct extends AppCompatActivity {
    private TextView qty, addnote, total_price_detail, name_product, detail_product, pricetxt, idpro;
    private ImageView minor_img, add_img, img_pro, img_back, img_close;
    private Button btnadd;
    int qtyCart = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail_product);
        initView();
        getView();
        addEvents();
    }

    private void getView() {
        String baseUrl = "http://192.168.1.155:4000/api/product/";
        Intent intent = getIntent();
        name_product.setText(intent.getStringExtra(MenuApdater.ProductKey));
        pricetxt.setText(String.valueOf(intent.getDoubleExtra(MenuApdater.PriceKey, 0)));
        detail_product.setText(intent.getStringExtra(MenuApdater.DescKey));
//        Glide.with(getApplicationContext())
//                .load(baseUrl + intent.getStringExtra(MenuApdater.ImgKey))
//                .into(img_pro);
        qty.setText(String.valueOf(qtyCart));
        idpro.setText(String.valueOf(intent.getIntExtra(MenuApdater.IdKey, 0)));
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qtyCart == 9) {
                        qty.setEnabled(false);
                        qty.setText(String.valueOf(qtyCart));
                }else {
                    qtyCart = qtyCart + 1;
                    qty.setText(String.valueOf(qtyCart));
                }
            }
        });

        minor_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qtyCart > 1) {
                    qtyCart = qtyCart - 1;
                }
                qty.setText(String.valueOf(qtyCart));
            }
        });
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Clicked moderatorbutton");
                addCart();
            }
        });
    }

    private void addCart() {
        String name = String.valueOf(name_product.getText());
        Double price = Double.parseDouble(pricetxt.getText().toString());
//            String image = String.valueOf(img_pro.);
        int quantity = Integer.parseInt(qty.getText().toString());
        int id = Integer.parseInt(idpro.getText().toString());
        Boolean check = CartDatabase.getInstance(this).cartDao().isexist(id);
        try {
            if (check == false) {
                CartDomain cart = new CartDomain(id, name, price, quantity);
                CartDatabase.getInstance(this).cartDao().InsertCart(cart);
                Toast.makeText(this, "Added Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                List<CartDomain> cartDomainList = CartDatabase.getInstance(this).cartDao().getAllCart();

                for (int i = 0; i < cartDomainList.size(); i++) {
                    if(cartDomainList.get(i).getId() == id){

                        cartDomainList.get(i).setQuantity(quantity + cartDomainList.get(i).getQuantity());
                       System.out.println((quantity + cartDomainList.get(i).getQuantity()));
                    }
                    CartDatabase.getInstance(this).cartDao().updateQty(cartDomainList.get(i).getQuantity(), cartDomainList.get(i).getId());
                }

                Toast.makeText(this, "Added product again success", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    private void initView() {
        qty = findViewById(R.id.qty);
        addnote = findViewById(R.id.addnote);
        minor_img = findViewById(R.id.minor_img);
        add_img = findViewById(R.id.add_img);
        img_pro = findViewById(R.id.img_pro);
        detail_product = findViewById(R.id.detail_product);
        name_product = findViewById(R.id.name_product);
        pricetxt = findViewById(R.id.pricetxt);
        img_back = findViewById(R.id.img_back);
        img_close = findViewById(R.id.img_close);
        btnadd = findViewById(R.id.btnadd);
        idpro = findViewById(R.id.idpro);
    }

    private void addEvents() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}