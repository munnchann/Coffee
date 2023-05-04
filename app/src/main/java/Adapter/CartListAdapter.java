package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uipart1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Domain.CartDomain;



public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    private List<CartDomain> cartDomainList;
    private IClickItem iClickItem;

    public void setData(List<CartDomain> cartDomainList) {
        this.cartDomainList = cartDomainList;
        notifyDataSetChanged();
    }

    public CartListAdapter(IClickItem iClickItem) {
        this.iClickItem = iClickItem;
    }

    public interface IClickItem {
        void deleteItem(CartDomain cartDomain);

        void plusQty(CartDomain cartDomain);

        void minorQty(CartDomain cartDomain);
    }

    @NonNull
    @Override
    public CartListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_ord, parent, false);
        return new CartListAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListAdapter.ViewHolder holder, int position) {
        String baseUrl = "http://10.0.2.2:4000/image/";
      //  String baseUrl = "http://172.16.10.123:4000/image/";
        CartDomain carts = cartDomainList.get(position);
        holder.txtname_pro.setText(carts.getName());
        Picasso.with(holder.itemView.getContext())
                .load(baseUrl + carts.getImage())
                .into(holder.img_ord);
        holder.txtprice.setText(String.valueOf(Math.round(carts.getQuantity() * carts.getPrice())));
        holder.quantityPro.setText(String.valueOf(carts.getQuantity()));
        holder.txtid.setText(String.valueOf(carts.getId()));
        ///Plus Number
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItem.plusQty(carts);
                int quantity = carts.getQuantity() + 1;
                carts.setQuantity(quantity);
                notifyDataSetChanged();
            }
        });
        /// Minor Number
        holder.minor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItem.minorQty(carts);
                int quantity = carts.getQuantity() - 1;
                carts.setQuantity(quantity);
                notifyDataSetChanged();
            }
        });
        //Delete
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItem.deleteItem(carts);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (cartDomainList != null)
            return cartDomainList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtname_pro, txtprice, quantityPro, txtid, subtotal, totalPrice, shipfee;
        ImageView img_ord, add, minor, btndelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtid = itemView.findViewById(R.id.txtid);
            txtname_pro = itemView.findViewById(R.id.txtname_pro);
            txtprice = itemView.findViewById(R.id.txtprice);
            quantityPro = itemView.findViewById(R.id.quantityPro);
            img_ord = itemView.findViewById(R.id.img_ord);
            add = itemView.findViewById(R.id.add);
            minor = itemView.findViewById(R.id.minor);
            btndelete = itemView.findViewById(R.id.btndelete);
            subtotal = itemView.findViewById(R.id.totaltxt);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            shipfee = itemView.findViewById(R.id.setstt);
        }
    }
}
