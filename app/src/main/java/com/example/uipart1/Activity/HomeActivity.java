package com.example.uipart1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uipart1.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Animation.TranslateAnimation;

public class HomeActivity extends AppCompatActivity {

    ImageView back_Button;
    ImageView switchToSettingActivity, img_setting_btn;
    RecyclerView rcvbtns;
    RecyclerView rcvnews;
    ArrayList<String> dataSource;
    ArrayList<String> dataSource1;
    LinearLayoutManager linearLayoutManager;
    RVAdapter rvAdapter;
//    BtnAdapter btnAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        rcvnews = findViewById(R.id.rcv_news);
//        rcvbtns =findViewById(R.id.rcv_btns);

        //data source
        dataSource = new ArrayList<>();
        dataSource.add("Sales 10%");
        dataSource.add("Sales 20%");
        dataSource.add("Sales 30%");
        dataSource.add("Sales 40%");
        dataSource.add("Sales 50%");
        dataSource.add("Sales 60%");
        //data source 1

        dataSource1 = new ArrayList<>();
        dataSource1.add("Food");
        dataSource1.add("Drink");
        dataSource1.add("Special");
        dataSource1.add("Sales");

        linearLayoutManager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rvAdapter = new RVAdapter(dataSource);
        rcvnews.setLayoutManager(linearLayoutManager);
        rcvnews.setAdapter(rvAdapter);
//        btnAdapter = new BtnAdapter(dataSource1);
//        rcvbtns.setLayoutManager(linearLayoutManager);
//        rcvbtns.setAdapter(btnAdapter);

        //switch activity
        back_Button = findViewById(R.id.back_button);
        back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        switchToSettingActivity = findViewById(R.id.img_category);
        switchToSettingActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities5();
            }
        });
        img_setting_btn = findViewById(R.id.img_setting_btn);
        img_setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivityIntent = new Intent(HomeActivity.this, setting.class);
                startActivity(switchActivityIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
            }
        });
    }

    private void switchActivities5() {
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
    }

    class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyHolder> {
        ArrayList<String> data;

        public RVAdapter(ArrayList<String> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.news_banner, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
//            holder.imgnews.setImageResource(Integer.parseInt(data.get(position)));
            holder.tvnews.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {

            ImageView imgnews;
            TextView tvnews;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
//                imgnews = itemView.findViewById(R.id.img_news);
                tvnews = itemView.findViewById(R.id.tv_news);
            }
        }

    }

}
//    class BtnAdapter extends RecyclerView.Adapter<BtnAdapter.MybtnHolder>{
//        ArrayList<String> data;
//        public BtnAdapter(ArrayList<String> data){
//            this.data = data;
//        }
//
//        @NonNull
//        @Override
//        public MybtnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.news_button, parent, false);
//            return new MybtnHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull MybtnHolder holder, int position) {
//            holder.btns.setText(data.get(position));
//        }
//
//        @Override
//        public int getItemCount() {
//            return data.size();
//        }
//
//        class MybtnHolder extends RecyclerView.ViewHolder{
//            AppCompatButton btns;
//
//            public MybtnHolder(@NonNull View itemView) {
//                super(itemView);
//                btns = itemView.findViewById(R.id.btn_s);
//            }
//        }
//
//    }

//    private List<FD> getListFD(){
//        List<FD> list = new ArrayList<>();
//
//        List<FoodAndDrink> listFoodAndDrink = new ArrayList<>();
//        listFoodAndDrink.add(new FoodAndDrink(R.drawable.brow_banner, "something1"));
//        listFoodAndDrink.add(new FoodAndDrink(R.drawable.sinhto, "something2"));
//        listFoodAndDrink.add(new FoodAndDrink(R.drawable.coffeecat_background1, "something3"));
//        listFoodAndDrink.add(new FoodAndDrink(R.drawable.oreo, "something4"));
//
