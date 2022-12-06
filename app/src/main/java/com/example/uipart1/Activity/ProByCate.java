package com.example.uipart1.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uipart1.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import Adapter.CategoryAdapter;
import Adapter.MenuApdater;
import Domain.Category;
import Domain.MenuDomain;
import api.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProByCate extends AppCompatActivity {
    RecyclerView rcvlistpro;
    TextView txtidcate, btnbackcate;
    private List<MenuDomain> ListMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_by_cate);
        ListMenu = new ArrayList<>();
        getInitView();
        CallApi();
        recyclerViewMenuList();
    }
     private void getInitView(){
        rcvlistpro = findViewById(R.id.rcvlistpro);
        txtidcate = findViewById(R.id.txtidcate);
         btnbackcate = findViewById(R.id.btnbackcate);
         btnbackcate.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(ProByCate.this, MainActivity.class);
                 startActivity(intent);
             }
         });
     }
    private void recyclerViewMenuList() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ProByCate.this, 2);
        rcvlistpro = findViewById(R.id.rcvlistpro);
        rcvlistpro.setLayoutManager(gridLayoutManager);
        rcvlistpro.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL) {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                for (int i = 0; i < rcvlistpro.getItemDecorationCount(); i++) {
                    if (rcvlistpro.getItemDecorationAt(i) instanceof DividerItemDecoration)
                        rcvlistpro.removeItemDecorationAt(i);
                }
            }
        });
    }
     private void CallApi(){
         Intent intent = getIntent();
         TextView txtidcate = (TextView) findViewById(R.id.txtidcate);
         TextView txtnamecate = (TextView) findViewById(R.id.txtnamecate);
         txtidcate.setText(String.valueOf(intent.getIntExtra(CategoryAdapter.IdKeys, 0)));
         txtnamecate.setText(intent.getStringExtra(CategoryAdapter.NameKeys));
         int idcate = Integer.parseInt(txtidcate.getText().toString());
         ApiService.apiService.getProductByCategory(idcate).enqueue(new Callback<MenuDomain>() {
             @Override
             public void onResponse(Call<MenuDomain> call, Response<MenuDomain> response) {
                 if (response.isSuccessful() && response.body() != null) {
                     ListMenu = response.body().getMenuDomainList();
                     MenuApdater menuApdater = new MenuApdater(ListMenu);
                     rcvlistpro.setAdapter(menuApdater);
                 }
             }

             @Override
             public void onFailure(Call<MenuDomain> call, Throwable t) {
                 Toast.makeText(ProByCate.this, "onFailure" + t.getMessage(), Toast.LENGTH_SHORT).show();
             }
         });
     }
}