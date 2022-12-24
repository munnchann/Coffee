package com.example.uipart1.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.uipart1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import Adapter.CategoryAdapter;
import Adapter.MenuApdater;
import Animation.TranslateAnimation;
import Database.CartDatabase;
import Domain.Category;
import Domain.MenuDomain;
import api.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewCategoryList;
    private RecyclerView recyclerViewFoodList;
    private List<MenuDomain> ListMenu;
    private List<Category> categoryList;
    private TextView txtTimeStamp;
    private AHBottomNavigation ahBottomNavigation;
    private NotificationBadge notification;
    private ImageView btnsearch, avatarShop;
    private SearchView searchView;
    private MenuApdater menuApdater;
    private LinearLayout homeBtn, Btncategory, settingBtn;
    private CardView app_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewCategory();
        recyclerViewFoodList();
        ListMenu = new ArrayList<>();
        categoryList = new ArrayList<>();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        callApiCategory();
//        ahBottomNavigation = findViewById(R.id.bottom_navigation);
        notification = findViewById(R.id.badge);
        btnsearch = findViewById(R.id.btnsearch);
        menuApdater = new MenuApdater(ListMenu);
        avatarShop = findViewById(R.id.avatarShop);
        settingBtn = findViewById(R.id.settingBtn);
        Btncategory = findViewById(R.id.Btncategory);
        homeBtn = findViewById(R.id.homeBtn);
        app_bar = findViewById(R.id.cardView);
//        setNaviGationBottom();
        callApiGetProduct();
        setTime();
        CartBtn();
        if(CartDatabase.getInstance(this).cartDao().getAllCart() != null){
            notification.setText(String.valueOf(CartDatabase.getInstance(this).cartDao().getAllCart().size()));
        }
        StartAnim();
        setBottomNavigation();
    }

    private void StartAnim(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                avatarShop.animate().rotationBy(360).withEndAction(this).setDuration(10000)
                        .setInterpolator(new LinearInterpolator()).start();
            }
        };

        avatarShop.animate().rotationBy(360).withEndAction(runnable).setDuration(10000)
                .setInterpolator(new LinearInterpolator()).start();
    }

    // Category recyclerview
    private void recyclerViewCategory() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewCategoryList = findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager((gridLayoutManager));
        recyclerViewCategoryList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL) {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                for (int i = 0; i < recyclerViewCategoryList.getItemDecorationCount(); i++) {
                    if (recyclerViewCategoryList.getItemDecorationAt(i) instanceof DividerItemDecoration)
                        recyclerViewCategoryList.removeItemDecorationAt(i);
                }
            }
        });
    }

    // Product recyclerview
    private void recyclerViewFoodList() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewFoodList = findViewById(R.id.recyclerViewFoods);
        recyclerViewFoodList.setLayoutManager(gridLayoutManager);
        recyclerViewFoodList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL) {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                for (int i = 0; i < recyclerViewFoodList.getItemDecorationCount(); i++) {
                    if (recyclerViewFoodList.getItemDecorationAt(i) instanceof DividerItemDecoration)
                        recyclerViewFoodList.removeItemDecorationAt(i);
                }
            }
        });
    }

    // api call category
    private void callApiCategory() {

        ApiService.apiService.getListCategory().enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                categoryList = response.body().getListCategory();
                CategoryAdapter categoryAdapter = new CategoryAdapter(categoryList);
                recyclerViewCategoryList.setAdapter(categoryAdapter);

            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                Toast.makeText(MainActivity.this, "onFailure" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    // call api product
    private void callApiGetProduct() {

        ApiService.apiService.getListProduct().enqueue(new Callback<MenuDomain>() {
            @Override
            public void onResponse(Call<MenuDomain> call, Response<MenuDomain> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ListMenu = response.body().getListData();
                    MenuApdater menuApdater = new MenuApdater(ListMenu);
                    recyclerViewFoodList.setAdapter(menuApdater);

                }

            }

            @Override
            public void onFailure(Call<MenuDomain> call, Throwable t) {
                Toast.makeText(MainActivity.this, "onFailure" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    // set time all day
    private void setTime() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        txtTimeStamp = findViewById(R.id.txttimestamp);
        if (timeOfDay >= 0 && timeOfDay < 12) {
            txtTimeStamp.setText("Good Morning");
        } else if (timeOfDay >= 12 && timeOfDay < 18) {
            txtTimeStamp.setText("Good Afternoon");
        } else if (timeOfDay >= 18 && timeOfDay < 21) {
            txtTimeStamp.setText("Good Evening");
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            txtTimeStamp.setText("Good Night");
        }
    }

    // Navigation Bottom
    private void setNaviGationBottom() {
        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.shop, R.color.color_tab_1);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.categories, R.color.color_tab_2);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.gift_box, R.color.color_tab_3);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.tab_4, R.drawable.settings, R.color.color_tab_4);
        // Add items
        ahBottomNavigation.addItem(item1);
        ahBottomNavigation.addItem(item2);
        ahBottomNavigation.addItem(item3);
        ahBottomNavigation.addItem(item4);

        ahBottomNavigation.setColored(true);
        recyclerViewCategoryList.setOnTouchListener(new TranslateAnimation(this, ahBottomNavigation));
        recyclerViewFoodList.setOnTouchListener(new TranslateAnimation(this, ahBottomNavigation));

        if(CartDatabase.getInstance(this).cartDao().getAllCart() != null){
            notification.setText(String.valueOf(CartDatabase.getInstance(this).cartDao().getAllCart().size()));
        }
    }
    private void CartBtn() {
        FloatingActionButton floatingActionButton = findViewById(R.id.btnCart);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });
    }
    private void setBottomNavigation(){
        Btncategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, setting.class);
                startActivity(intent);
            }
        });
        recyclerViewCategoryList.setOnTouchListener(new TranslateAnimation(this, app_bar));
        recyclerViewFoodList.setOnTouchListener(new TranslateAnimation(this, app_bar));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                menuApdater.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                menuApdater.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

}