package Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uipart1.Activity.AddressUser;
import com.example.uipart1.Activity.CartActivity;
import com.example.uipart1.R;

import java.util.List;

import Domain.Address;

public class AddressUserAdapter extends RecyclerView.Adapter<AddressUserAdapter.ViewHolder>{
    List<Address> addressList;

    public AddressUserAdapter(List<Address> addressList) {
        this.addressList = addressList;
    }

    @NonNull
    @Override
    public AddressUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_layout,parent,false);
        return new AddressUserAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressUserAdapter.ViewHolder holder, int position) {
        Address ad = addressList.get(position);
        holder.txtuser_address.setText(ad.getAddress_user());
        holder.txtuser_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), CartActivity.class);
                Address address = addressList.get(holder.getAdapterPosition());
                intent.putExtra("address", address.getAddress_user());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(addressList!=null){
            return addressList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtuser_address;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtuser_address = itemView.findViewById(R.id.txtuser_address);
        }
    }
}
