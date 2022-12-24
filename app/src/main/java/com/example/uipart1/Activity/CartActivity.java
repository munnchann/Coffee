package com.example.uipart1.Activity;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uipart1.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Adapter.CartListAdapter;
import Database.CartDatabase;
import Database.InforDatabase;
import Domain.CartDomain;
import Domain.InforUser;
import Domain.OrderDetailDomain;
import Domain.OrderDomain;
import Domain.Users;
import api.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    private TextView txtpay, btnbackpay, totalPrice, shipfee, subtotal, noproduct, quantityPro, txtpay2, txtmap, txtnameuser, txtsdt, txtaddress, txtvou;
    private ImageView btnnext, imgempty, add, minor, btnwriteaddress;
    private ConstraintLayout mainLayoutCart, payy;
    private RecyclerView rcvlistord;
    private Button btnord2, btnord;
    private ScrollView scrollview;
    private List<OrderDetailDomain> orderDetailDomainList;
    String totals = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        loadComponent();
        initView();
        noproduct = findViewById(R.id.noproduct);
        imgempty = findViewById(R.id.imgempty);
        subtotal = findViewById(R.id.totaltxt);
        addEvents();
        CalculateCart();
        orderDetailDomainList = new ArrayList<>();
        ShowInformation();
        loadData();
        CallApiGetUser();
    }

    public void loadComponent() {
        try {
            if (CartDatabase.getInstance(this).cartDao().getAllCart().size() == 0) {
                TextView noproduct = (TextView) findViewById(R.id.noproduct);
                ImageView imgempty = (ImageView) findViewById(R.id.imgempty);
                ScrollView scrollview = (ScrollView) findViewById(R.id.scrollview);
                noproduct.setVisibility(View.VISIBLE);
                imgempty.setVisibility(View.VISIBLE);
                scrollview.setVisibility(View.GONE);
            } else {
                loadData();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    public void loadData() {
        try {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rcvlistord = findViewById(R.id.rcvlistord);
            rcvlistord.setLayoutManager(layoutManager);
            List<CartDomain> cartDomains = CartDatabase.getInstance(this).cartDao().getAllCart();
            CartListAdapter cartListAdapter = new CartListAdapter(new CartListAdapter.IClickItem() {
                @Override
                public void deleteItem(CartDomain cartDomain) {
                    CartDatabase.getInstance(CartActivity.this).cartDao().deleteCart(cartDomain.getId());
                    //              CartDatabase.getInstance(CartActivity.this).cartDao().deleteItemCarts(cartDomain);
                    loadComponent();
                }

                @Override
                public void plusQty(CartDomain cartDomain) {
                    String name = cartDomain.getName();
                    Double price = cartDomain.getPrice();
                    String image = String.valueOf(cartDomain.getImage());
                    int quantity = cartDomain.getQuantity();
                    int id = cartDomain.getId();
                    Boolean check = CartDatabase.getInstance(CartActivity.this).cartDao().isexist(id);
                    try {
                        CartDomain cart = new CartDomain(id, name, price, image, quantity);

                        if (check == true && quantity <= 9) {
                            quantity = quantity + 1;
                            cart.setQuantity(quantity);
                            CartDatabase.getInstance(CartActivity.this).cartDao().updateCart(cart);
                            Toast.makeText(CartActivity.this, "Update sucess", Toast.LENGTH_SHORT).show();
                            loadData();
                        } else {
                            Toast.makeText(CartActivity.this, "Added unsucess! Over quantity", Toast.LENGTH_SHORT).show();
                            add.setEnabled(false);
                            quantityPro.setEnabled(false);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void minorQty(CartDomain cartDomain) {
                    String name = cartDomain.getName();
                    Double price = cartDomain.getPrice();
                    String image = String.valueOf(cartDomain.getImage());
                    int quantity = cartDomain.getQuantity();
                    int id = cartDomain.getId();
                    Boolean check = CartDatabase.getInstance(CartActivity.this).cartDao().isexist(id);
                    try {
                        CartDomain cart = new CartDomain(id, name, price, image, quantity);
                        if (check == true) {
                            if (quantity <= 1) {
                                minor.setEnabled(false);
                                quantityPro.setEnabled(false);
                            } else {
                                quantity = quantity - 1;
                                cart.setQuantity(quantity);
                                CartDatabase.getInstance(CartActivity.this).cartDao().updateCart(cart);
                                Toast.makeText(CartActivity.this, "Update sucsess", Toast.LENGTH_SHORT).show();
                                loadData();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            cartListAdapter.setData(cartDomains);
            rcvlistord.setAdapter(cartListAdapter);

            double sum = 0;
            for (int i = 0; i < cartDomains.size(); i++) {
                sum = sum + (cartDomains.get(i).getPrice() * cartDomains.get(i).getQuantity());
            }
            double ship = 2;
            double itemTotals = Math.round(sum + ship);

            subtotal.setText(String.valueOf(sum + "$"));
            shipfee.setText(String.valueOf(ship + "$"));
            totalPrice.setText(String.valueOf(itemTotals));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void addEvents() {
        /// Back
        btnbackpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });
        /// Choose payment method
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, PayActivity2.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });
        /// Order
        btnord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txtpay = (TextView) findViewById(R.id.txtpay);
                String payy = txtpay.getText().toString();
                TextView txtpay2 = (TextView) findViewById(R.id.txtpay2);
                String payment2 = txtpay2.getText().toString();
                if (payy.equals("Payment on delivery")) {
                    sendOrdMoney();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                }
                if (payment2.equals("Stripe")) {
                    totals = totalPrice.getText().toString();
                    Intent intent = new Intent(CartActivity.this, CheckOutActivity.class);
                    intent.putExtra("amountPrice", totals);
                    startActivity(intent);
                    sendPayOnl();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                }
            }
        });
        // Address
        btnwriteaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, AddressUser.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });
    }

    private void ShowInformation() {
        TextView txtpay = (TextView) findViewById(R.id.txtpay);
        txtpay.setText(getIntent().getStringExtra("money"));
        String payy = txtpay.getText().toString();
        TextView txtpay2 = (TextView) findViewById(R.id.txtpay2);
        txtpay2.setText(getIntent().getStringExtra("paypal"));
        String payment2 = txtpay2.getText().toString();
        if (payy.equals("") && payment2.equals("")) {
            btnord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
                    alertDialog.setTitle("Payment method null");
                    alertDialog.setIcon(R.drawable.ic_sharp_error_24);
                    alertDialog.setMessage("Please choose the payment method");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog builder = alertDialog.create();
                    builder.show();
                    btnord.setEnabled(false);
                }
            });

        }
        List<InforUser> infor = InforDatabase.getInstance(this).inforDao().getUser();
        for (int i = 0; i < infor.size(); i++) {
            TextView id = (TextView) findViewById(R.id.iduser);
            id.setText(String.valueOf(infor.get(i).getIdUser()));
        }

    }
    private void CallApiGetUser(){
        TextView id = (TextView) findViewById(R.id.iduser);
       int idUser =Integer.parseInt(id.getText().toString());
       if(idUser == 0){
           Intent intent = new Intent(CartActivity.this, LoginPageActivity.class);
           startActivity(intent);
           overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
       }
       ApiService.apiService.getUsersById(idUser).enqueue(new Callback<Users>() {
           @Override
           public void onResponse(Call<Users> call, Response<Users> response) {
               Users us = response.body();
               String userName = us.getFirst_name() + us.getLast_name();
               if(us!= null){
                   TextView sdt = (TextView) findViewById(R.id.txtsdt);
                   TextView name = (TextView) findViewById(R.id.txtnameuser);
                   TextView ad = (TextView) findViewById(R.id.txtaddress);
                   sdt.setText(us.getPhone());
                   name.setText(userName);
                   ad.setText(us.getAddress());
               }
           }

           @Override
           public void onFailure(Call<Users> call, Throwable t) {
               Toast.makeText(CartActivity.this, "onFailure" + t.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });
    }

    private void sendPayOnl() {
        TextView id = (TextView) findViewById(R.id.iduser);
        totals = totalPrice.getText().toString();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        String IdUser = String.valueOf(id.getText());
        int user_id = Integer.parseInt(IdUser);
        Double total = Double.parseDouble(totals);
        int payment_id = 1;
        String create_at = formattedDate;
        String ouput_status = "Success";
        String order_status = "Stripe";
        OrderDomain ord = new OrderDomain(user_id, total, payment_id, create_at, ouput_status, order_status);
        ApiService.apiService.sendOrd(ord).enqueue(new Callback<OrderDomain>() {
            @Override
            public void onResponse(Call<OrderDomain> call, Response<OrderDomain> response) {
                System.out.println("ok");
                OrderDomain order = response.body();
                System.out.println(order.toString());

                if (order != null) {
                    int ID = order.id;
                    for (CartDomain cartDomain : CartDatabase.getInstance(CartActivity.this).cartDao().getAllCart()) {
                        OrderDetailDomain ordDetail = new OrderDetailDomain();
                        ordDetail.order_id = ID;
                        ordDetail.product_id = cartDomain.id;
                        ordDetail.quanity = cartDomain.quantity;
                        ordDetail.discount_id = 1;
                        ordDetail.ori_price = cartDomain.price;
                        ordDetail.total = (cartDomain.price * cartDomain.quantity);
                        ordDetail.create_at = formattedDate;
                        ApiService.apiService.sendOrdDetail(ordDetail).enqueue(new Callback<OrderDetailDomain>() {
                            @Override
                            public void onResponse(Call<OrderDetailDomain> call, Response<OrderDetailDomain> response) {
                                OrderDetailDomain orderDetail = response.body();
                                if (orderDetail != null) {
                                    System.out.println("Success");
                                } else {
                                    System.out.println("Unsucess");
                                }
                            }

                            @Override
                            public void onFailure(Call<OrderDetailDomain> call, Throwable t) {
                                Toast.makeText(CartActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderDomain> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendOrdMoney() {
        TextView id = (TextView) findViewById(R.id.iduser);
        totals = totalPrice.getText().toString();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        String IdUser = String.valueOf(id.getText());
        int user_id = Integer.parseInt(IdUser);
        Double total = Double.parseDouble(totals);
        int payment_id = 1;
        String create_at = formattedDate;
        String ouput_status = "Success";
        String order_status = "Money";
        OrderDomain ord = new OrderDomain(user_id, total, payment_id, create_at, ouput_status, order_status);
        ApiService.apiService.sendOrd(ord).enqueue(new Callback<OrderDomain>() {
            @Override
            public void onResponse(Call<OrderDomain> call, Response<OrderDomain> response) {
                System.out.println("ok");
                OrderDomain order = response.body();
                System.out.println(order.toString());

                if (order != null) {
                    int ID = order.id;
                    for (CartDomain cartDomain : CartDatabase.getInstance(CartActivity.this).cartDao().getAllCart()) {
                        OrderDetailDomain ordDetail = new OrderDetailDomain();
                        ordDetail.order_id = ID;
                        ordDetail.product_id = cartDomain.id;
                        ordDetail.quanity = cartDomain.quantity;
                        ordDetail.discount_id = 1;
                        ordDetail.ori_price = cartDomain.price;
                        ordDetail.total = (cartDomain.price * cartDomain.quantity);
                        ordDetail.create_at = formattedDate;
                        ApiService.apiService.sendOrdDetail(ordDetail).enqueue(new Callback<OrderDetailDomain>() {
                            @Override
                            public void onResponse(Call<OrderDetailDomain> call, Response<OrderDetailDomain> response) {
                                OrderDetailDomain orderDetail = response.body();
                                if (orderDetail != null) {
                                    System.out.println("Success");
                                    Intent intent = new Intent(CartActivity.this, OrdConfirm.class);
                                    intent.putExtra("totals", totals);
                                    startActivity(intent);
                                } else {
                                    System.out.println("Unsucess");
                                }
                            }

                            @Override
                            public void onFailure(Call<OrderDetailDomain> call, Throwable t) {
                                Toast.makeText(CartActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderDomain> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void CalculateCart() {
        List<CartDomain> cartList = CartDatabase.getInstance(this).cartDao().getAllCart();
        double sum = 0;
        for (int i = 0; i < cartList.size(); i++) {
            sum = sum + (cartList.get(i).getPrice() * cartList.get(i).getQuantity());
        }
        double ship = 2;
        double itemTotals = Math.round(sum + ship);
        subtotal.setText(String.valueOf(sum + "$"));
        shipfee.setText(String.valueOf(ship + "$"));
        totalPrice.setText(String.valueOf(itemTotals));
    }

    private void initView() {
        btnbackpay = findViewById(R.id.btnbackcate);
        btnnext = findViewById(R.id.btnnext);
        mainLayoutCart = findViewById(R.id.mainLayoutCart);
        totalPrice = findViewById(R.id.totalPrice);
        shipfee = findViewById(R.id.setstt);
        payy = findViewById(R.id.payy);
        add = findViewById(R.id.add);
        minor = findViewById(R.id.minor);
        quantityPro = findViewById(R.id.quantityPro);
        btnord = findViewById(R.id.btnord);
        txtpay2 = findViewById(R.id.txtpay2);
        scrollview = findViewById(R.id.scrollview);
        txtaddress = findViewById(R.id.txtaddress);
        txtnameuser = findViewById(R.id.txtnameuser);
        txtsdt = findViewById(R.id.txtsdt);
        btnwriteaddress = findViewById(R.id.btnwriteaddress);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}