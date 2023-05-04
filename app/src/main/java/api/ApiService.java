package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Domain.Category;
import Domain.MenuDomain;
import Domain.OrderDetailDomain;
import Domain.OrderDomain;
import Domain.Users;
import Helper.Const;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

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
    @GET("product/category/{category_id}")
    Call<MenuDomain> getProductByCategory(@Path("category_id") int category_id);
    @POST("bill")
    Call<OrderDomain> sendOrd(@Body OrderDomain orderDomain);
    @POST("bill_detail")
    Call<OrderDetailDomain> sendOrdDetail(@Body OrderDetailDomain orderDetailDomain);
    @POST("users")
    Call<Users> RegistationUser(@Body Users users);
    @POST("users/check")
    Call<Users> CheckPhone(@Body Users users);
    @POST("users/login2")
    Call<Users> Login(@Body Users users);
    @POST("checkPass")
    Call<Users> changePassword(@Body Users users);

    @POST("check")
    Call<Users> checkPassword(@Body Users users);
    @Multipart
    @PATCH("users")
    Call<Users> updateUsers(@Part(Const.KEY_ID)RequestBody id,
                            @Part(Const.KEY_USERNAME)RequestBody first_name,
                            @Part(Const.KEY_LASTNAME)RequestBody last_name,
                            @Part(Const.KEY_EMAIL) RequestBody email,
                            @Part(Const.KEY_PHONE) RequestBody phone,
                            @Part MultipartBody.Part image,
                            @Part(Const.KEY_ADDRESS)RequestBody address);
    @GET("users/{id}")
    Call<Users> getUsersById(@Path("id") int id);
}
