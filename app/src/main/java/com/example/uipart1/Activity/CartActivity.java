package com.example.uipart1.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.uipart1.BuildConfig;
import com.example.uipart1.R;
import com.nex3z.notificationbadge.NotificationBadge;
//import com.paypal.checkout.PayPalCheckout;
//import com.paypal.checkout.approve.Approval;
//import com.paypal.checkout.approve.OnApprove;
//import com.paypal.checkout.config.CheckoutConfig;
//import com.paypal.checkout.config.Environment;
//import com.paypal.checkout.config.SettingsConfig;
//import com.paypal.checkout.createorder.CreateOrder;
//import com.paypal.checkout.createorder.CreateOrderActions;
//import com.paypal.checkout.createorder.CurrencyCode;
//import com.paypal.checkout.createorder.OrderIntent;
//import com.paypal.checkout.createorder.UserAction;
//import com.paypal.checkout.order.Amount;
//import com.paypal.checkout.order.AppContext;
//import com.paypal.checkout.order.CaptureOrderResult;
//import com.paypal.checkout.order.OnCaptureComplete;
//import com.paypal.checkout.order.Order;
//import com.paypal.checkout.order.PurchaseUnit;
//import com.paypal.checkout.paymentbutton.PayPalButton;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.Console;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Adapter.CartListAdapter;
import Adapter.MenuApdater;
import Animation.TranslateAnimation;
import Database.CartDatabase;
import Database.Ord_Detail_Database;
import Database.OrderDatabase;
import Domain.CartDomain;
import Domain.OrderDetailDomain;
import Domain.OrderDomain;
import Paypal.Config;
import api.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    private TextView txtpay, btnbackpay, totalPrice, shipfee, subtotal, noproduct, quantityPro, txtpay2;
    private ImageView btnnext, imgempty, add, minor;
    private AHBottomNavigation ahBottomNavigation;
    private ConstraintLayout mainLayoutCart, payy;
    private RecyclerView rcvlistord;
    private Button btnord2, btnord;
    private NotificationBadge notification;
    private ScrollView scrollview;
    private List<OrderDetailDomain> orderDetailDomainList;
//    PayPalButton payPalButton;
    public static int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);
    String total = "";
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        loadComponent();
        initView();
//        ahBottomNavigation = findViewById(R.id.bottom_navigation);
        noproduct = findViewById(R.id.noproduct);
        imgempty = findViewById(R.id.imgempty);
        notification = findViewById(R.id.badge);
        subtotal = findViewById(R.id.totaltxt);
        addEvents();
        CalculateCart();
//        payPalButton = findViewById(R.id.payment_button_container);
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
//        setBottom();
        orderDetailDomainList = new ArrayList<>();

//        CheckoutConfig config = new CheckoutConfig(
//                 getApplication(),
//                 Config.PAYPAL_CLIENT_ID,
//                 Environment.SANDBOX,
//                String.format( "%s://paypalpay", "com.example.uipart1"),
//                CurrencyCode.USD,
//                UserAction.PAY_NOW,
//                new SettingsConfig(
//                         true,
//                        false
//                )
//        );
//        PayPalCheckout.setConfig(config);
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
//            if (CartDatabase.getInstance(this).cartDao().getAllCart().size() > 0) {
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
                        CartDomain cart = new CartDomain(id, name, price,image, quantity);

                            if (check == true && quantity <= 9) {
                                quantity = quantity + 1;
                                cart.setQuantity(quantity);
                                CartDatabase.getInstance(CartActivity.this).cartDao().updateCart(cart);
                                Toast.makeText(CartActivity.this, "Update sucess", Toast.LENGTH_SHORT).show();
                                loadData();
                            }else {
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
                        CartDomain cart = new CartDomain(id, name, price,image, quantity);
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

            // }
        } catch (Exception e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

//    public void setBottom() {
//        AHBottomNavigation ahBottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
//
//        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.shop, R.color.color_tab_1);
//        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.categories, R.color.color_tab_2);
//        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.gift_box, R.color.color_tab_3);
//        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.tab_4, R.drawable.settings, R.color.color_tab_4);
//        // Add items
//        ahBottomNavigation.addItem(item1);
//        ahBottomNavigation.addItem(item2);
//        ahBottomNavigation.addItem(item3);
//        ahBottomNavigation.addItem(item4);
//
//        ahBottomNavigation.setColored(true);
//        if (CartDatabase.getInstance(this).cartDao().getAllCart().size() > 0) {
//            rcvlistord.setOnTouchListener(new TranslateAnimation(this, ahBottomNavigation));
//            payy.setOnTouchListener(new TranslateAnimation(this, ahBottomNavigation));
//        } else {
//
//        }
//
//    }

    private void addEvents() {
        /// Back
        btnbackpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        /// Choose payment method
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, PayActivity.class);
                startActivity(intent);
            }
        });

        /// Order
        btnord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total = totalPrice.getText().toString();
                PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(total)), "USD", "Pay for coffee", PayPalPayment.PAYMENT_INTENT_SALE);
                Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
                startActivityForResult(intent, PAYPAL_REQUEST_CODE);
//                sendOrdPayPal();

//                payPalButton.setup(
//                        new CreateOrder() {
//                            @Override
//                            public void create(@NotNull CreateOrderActions createOrderActions) {
//                                ArrayList<PurchaseUnit> purchaseUnits = new ArrayList<>();
//                                purchaseUnits.add(
//                                        new PurchaseUnit.Builder()
//                                                .amount(
//                                                        new Amount.Builder()
//                                                                .currencyCode(CurrencyCode.USD)
//                                                                .value("10.00")
//                                                                .build()
//                                                )
//                                                .build()
//                                );
//                                Order order = new Order(
//                                        OrderIntent.CAPTURE,
//                                        new AppContext.Builder()
//                                                .userAction(UserAction.PAY_NOW)
//                                                .build(),
//                                        purchaseUnits
//                                );
//                                createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
//                            }
//                        },
//                        new OnApprove() {
//                            @Override
//                            public void onApprove(@NotNull Approval approval) {
//                                approval.getOrderActions().capture(new OnCaptureComplete() {
//                                    @Override
//                                    public void onCaptureComplete(@NotNull CaptureOrderResult result) {
//                                        Log.i("CaptureOrder", String.format("CaptureOrderResult: %s", result));
//                                    }
//                                });
//                            }
//                        }
//                );
            }
        });

        btnord2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sendOrdMoney();
            }
        });

    }

    private void ShowButton() {
        TextView txtpay = (TextView) findViewById(R.id.txtpay);
        txtpay.setText("Payment on delivery");
        txtpay.setText(getIntent().getStringExtra("money"));
        String payy = txtpay.getText().toString();

        if (payy == "Payment on delivery") {
            btnord2.setVisibility(View.VISIBLE);
            btnord.setVisibility(View.GONE);
        }else{
            btnord2.setVisibility(View.GONE);
            btnord.setVisibility(View.VISIBLE);
        }
    }
    private void HideButton(){
        TextView txtpay2 = (TextView) findViewById(R.id.txtpay2);
        txtpay2.setText("Pay Pal");
        txtpay2.setText(getIntent().getStringExtra("paypal"));
        String payy2 = txtpay2.getText().toString();

        if (payy2 == "PayPal") {
            btnord.setVisibility(View.VISIBLE);
            btnord2.setVisibility(View.GONE);
        }else{
            btnord2.setVisibility(View.VISIBLE);
            btnord.setVisibility(View.GONE);
        }
    }
//        private void sendOrdPayPal () {
//            total = totalPrice.getText().toString();
//            OrderDomain ord = new OrderDomain();
//            Calendar c = Calendar.getInstance();
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//            String formattedDate = df.format(c.getTime());
//
//            ord.user_id = 1;
//            ord.total = Double.parseDouble(total);
//            ord.create_at = formattedDate;
//            ord.ouput_status = "Success";
//            ord.order_status = "PayPal";
//            ord.shipInfo_id = 1;
//            ord.employee_id = 1;
//            OrderDatabase.getInstance(this).orderDao().InsertOrd(ord);
//            System.out.println(OrderDatabase.getInstance(this).orderDao().getAll());
//            int idOrd = ord.id;
//            ApiService.apiService.sendOrd(ord).enqueue(new Callback<OrderDomain>() {
//                @Override
//                public void onResponse(Call<OrderDomain> call, Response<OrderDomain> response) {
//                    String total1 = totalPrice.getText().toString();
//                    OrderDomain order = response.body();
//                    if (order != null) {
//                        Intent intent = new Intent(CartActivity.this, ConfirmPay.class);
//                        intent.putExtra("Total_Price", total1);
//                        startActivity(intent);
//                        int ID = order.id;
//                        for (CartDomain cartDomain : CartDatabase.getInstance(CartActivity.this).cartDao().getAllCart()) {
//                            OrderDetailDomain ordDetail = new OrderDetailDomain();
//                            ordDetail.order_id = ID;
//                            ordDetail.product_id = cartDomain.id;
//                            ordDetail.quanity = cartDomain.quantity;
//                            ordDetail.discount_id = 1;
//                            ordDetail.ori_price = cartDomain.price;
//                            ordDetail.total = (cartDomain.price * cartDomain.quantity);
//                            ordDetail.create_at = formattedDate;
//                            ApiService.apiService.sendOrdDetail(ordDetail).enqueue(new Callback<OrderDetailDomain>() {
//                                @Override
//                                public void onResponse(Call<OrderDetailDomain> call, Response<OrderDetailDomain> response) {
//                                    OrderDetailDomain orderDetail = response.body();
//                                    if (orderDetail != null) {
//                                        System.out.println("Success");
//                                    } else {
//                                        System.out.println("Unsucess");
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<OrderDetailDomain> call, Throwable t) {
//                                    Toast.makeText(CartActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<OrderDomain> call, Throwable t) {
//                    Toast.makeText(CartActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                }
//            });
//            for (CartDomain cartDomain : CartDatabase.getInstance(this).cartDao().getAllCart()) {
//                OrderDetailDomain ordDetail = new OrderDetailDomain();
//                ordDetail.order_id = idOrd;
//                ordDetail.product_id = cartDomain.id;
//                ordDetail.quanity = cartDomain.quantity;
//                ordDetail.discount_id = 1;
//                ordDetail.ori_price = cartDomain.price;
//                ordDetail.total = (cartDomain.price * cartDomain.quantity);
//                ordDetail.create_at = formattedDate;
//                Ord_Detail_Database.getInstance(this).ord_detail_dao().InsertOrd_Detail(ordDetail);
//                System.out.println(Ord_Detail_Database.getInstance(this).ord_detail_dao().getAll());
//                ApiService.apiService.sendOrdDetail(ordDetail).enqueue(new Callback<OrderDetailDomain>() {
//                    @Override
//                    public void onResponse(Call<OrderDetailDomain> call, Response<OrderDetailDomain> response) {
//                        OrderDetailDomain orderDetail = response.body();
//                        if (orderDetail != null) {
//                            System.out.println("Success");
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<OrderDetailDomain> call, Throwable t) {
//                        Toast.makeText(CartActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//
//        }

//        private void sendOrdMoney () {
//            total = totalPrice.getText().toString();
//            OrderDomain ord = new OrderDomain();
//            Calendar c = Calendar.getInstance();
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//            String formattedDate = df.format(c.getTime());
//            ord.user_id = 1;
//            ord.total = Double.parseDouble(total);
//            ord.create_at = formattedDate;
//            ord.ouput_status = "Success";
//            ord.order_status = "Unpay";
//            ord.shipInfo_id = 1;
//            ord.employee_id = 1;
//            ord.payment_id = 1;
//
//            OrderDatabase.getInstance(this).orderDao().InsertOrd(ord);
//            System.out.println(OrderDatabase.getInstance(this).orderDao().getAll());
//            int idOrd = ord.id;
//            ApiService.apiService.sendOrd(ord).enqueue(new Callback<OrderDomain>() {
//                @Override
//                public void onResponse(Call<OrderDomain> call, Response<OrderDomain> response) {
//                    OrderDomain order = response.body();
//
//                        String total1 = totalPrice.getText().toString();
//                    if (order != null) {
//                        Intent intent = new Intent(CartActivity.this, OrdConfirm.class);
//                        intent.putExtra("totals", total1);
//                        startActivity(intent);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<OrderDomain> call, Throwable t) {
//                    Toast.makeText(CartActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                }
//            });
//            for (CartDomain cartDomain : CartDatabase.getInstance(this).cartDao().getAllCart()) {
//                OrderDetailDomain ordDetail = new OrderDetailDomain();
//                ordDetail.order_id = idOrd;
//                ordDetail.product_id = cartDomain.id;
//                ordDetail.quanity = cartDomain.quantity;
//                ordDetail.discount_id = 1;
//                ordDetail.ori_price = cartDomain.price;
//                ordDetail.total = (cartDomain.price * cartDomain.quantity);
//                ordDetail.create_at = formattedDate;
//                Ord_Detail_Database.getInstance(this).ord_detail_dao().InsertOrd_Detail(ordDetail);
//                System.out.println(Ord_Detail_Database.getInstance(this).ord_detail_dao().getAll());
//                ApiService.apiService.sendOrdDetail(ordDetail).enqueue(new Callback<OrderDetailDomain>() {
//                    @Override
//                    public void onResponse(Call<OrderDetailDomain> call, Response<OrderDetailDomain> response) {
//                        OrderDetailDomain orderDetail = response.body();
//                        if (orderDetail != null) {
//                            System.out.println("Success");
//                        }else{
//                            System.out.println("Unsucess");
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<OrderDetailDomain> call, Throwable t) {
//                        Toast.makeText(CartActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//        }

        @Override
        public void onDestroy () {
            stopService(new Intent(this, PayPalService.class));
            super.onDestroy();
        }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            if (requestCode == PAYPAL_REQUEST_CODE) {
                if (requestCode == RESULT_OK) {
                    PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                    if (confirmation != null) {
                        try {
                            String paymentDetails = confirmation.toJSONObject().toString(4);
                            Log.i("paymentExample", paymentDetails);
                            startActivity(new Intent(this, PayActivity.class)
                                    .putExtra("PaymentDetails", paymentDetails)
                                    .putExtra("PaymentTotal", total)
                            );
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                        }
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
                Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();

            super.onActivityResult(requestCode, resultCode, data);
        }


        private void CalculateCart () {
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

        private void initView () {
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
            txtpay = findViewById(R.id.txtpay);
            txtpay2 = findViewById(R.id.txtpay2);
            btnord2 = findViewById(R.id.btnord2);
            scrollview = findViewById(R.id.scrollview);
        }


    }