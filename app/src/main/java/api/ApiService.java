package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import Domain.CartDomain;
import Domain.Category;
import Domain.MenuDomain;
import Domain.OrderDetailDomain;
import Domain.OrderDomain;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yy").create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:4000/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    @GET("category")
    Call<Category> getListCategory();
    @GET("product")
    Call<MenuDomain> getListProduct();
//    @GET("product/category/{category_id}")
//    Call<MenuDomain> getProductByCategory(@Path("category_id") int category_id);
//    @POST("bill")
//    Call<OrderDomain> sendOrd(@Body OrderDomain orderDomain);
//    @POST("ordDetail")
//    Call<OrderDetailDomain> sendOrdDetail(@Body OrderDetailDomain orderDetailDomain);
}
