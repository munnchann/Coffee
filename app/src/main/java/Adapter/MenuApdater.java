package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.uipart1.Activity.CartActivity;
import com.example.uipart1.Activity.MainActivity;
import com.example.uipart1.Activity.ShowDetailProduct;
import com.example.uipart1.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Database.CartDatabase;
import Domain.CartDomain;
import Domain.MenuDomain;
import api.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuApdater extends RecyclerView.Adapter<MenuApdater.ViewHolder> implements Filterable {


    public MenuApdater(List<MenuDomain> menuDomains) {
        this.menuDomains = menuDomains;
        this.listProduct = menuDomains;
        notifyDataSetChanged();
    }

    private List<MenuDomain> menuDomains;
    private List<MenuDomain> listProduct;

    public static final String ImgKey = "key_img";
    public static final String ProductKey = "key_product_name";
    public static final String PriceKey = "key_product_price";
    public static final String DescKey = "key_desc_stock";
    public static final String IdKey = "Id_key";


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_product, parent, false);
        return new MenuApdater.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String baseUrl = "http://172.16.10.123:4000/image/";
      //  String baseUrl = "http://10.0.2.2:4000/image/";
        MenuDomain carts = menuDomains.get(position);
        holder.name_pro.setText(menuDomains.get(position).getName_pro());
        holder.price_product.setText(String.valueOf(menuDomains.get(position).getPrice()) + '$');
        Picasso picasso = new Picasso.Builder(holder.itemView.getContext())
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        Log.e("PICASSO", uri.toString(), exception);
                    }
                })
                .build();

        picasso.load(baseUrl + menuDomains.get(position).getImage())
                .into(holder.img_product);
        holder.mainLayoutShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(holder.itemView.getContext(), ShowDetailProduct.class);

                    //Getting the requested book from the list
                    MenuDomain book = menuDomains.get(holder.getAdapterPosition());

                    //Adding book details to intent
                    intent.putExtra(ProductKey, book.getName_pro());
                    intent.putExtra(PriceKey, book.getPrice());
                    intent.putExtra(ImgKey, book.getImage());
                    intent.putExtra(IdKey, book.getId());
                    intent.putExtra(DescKey,book.getDesciption());
                    //Starting another activity to show book details
                    holder.itemView.getContext().startActivity(intent);
                } catch (Exception e) {
                    Log.d("Cannot get product", "Error");
                    e.printStackTrace();
                }

            }
        });


    }


    @Override
    public int getItemCount() {
        if (menuDomains != null) {
            return menuDomains.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()) {
                    menuDomains = listProduct;
                } else {
                    List<MenuDomain> list = new ArrayList<>();
                    for (MenuDomain menu : listProduct) {
                        if (menu.getName_pro().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(menu);
                        }
                    }
                    menuDomains = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = menuDomains;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                menuDomains = (List<MenuDomain>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name_pro;
        private final TextView price_product;
        private final ImageView img_product;
        private final ConstraintLayout mainLayoutShow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_pro = itemView.findViewById(R.id.name_pro);
            price_product = itemView.findViewById(R.id.price_product);
            img_product = itemView.findViewById(R.id.img_product);
            mainLayoutShow = itemView.findViewById(R.id.mainLayoutShow);

        }
    }
}
