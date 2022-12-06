package Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uipart1.Activity.MainActivity;
import com.example.uipart1.Activity.ProByCate;
import com.example.uipart1.R;

import java.util.List;

import Domain.CartDomain;
import Domain.Category;
import Domain.MenuDomain;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    public CategoryAdapter(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }
    public static final String IdKeys = "Id_key";
    public static final String NameKeys = "Name_key";
    private List<Category> categories;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       Category cate = categories.get(position);
       if(cate == null){
           return;
       }
       holder.categoryName.setText(cate.getName());
       holder.mainLayoutCate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               try {
                   Intent intent = new Intent(holder.itemView.getContext(), ProByCate.class);
                   Category category = categories.get(holder.getAdapterPosition());
                   intent.putExtra(IdKeys, category.getCategoryid());
                   intent.putExtra(NameKeys, category.getName());
                   holder.itemView.getContext().startActivity(intent);
               }catch (Exception e){
                   Log.d("Cannot get product", "Error");
                   e.printStackTrace();
               }
           }
       });
    }


    @Override
    public int getItemCount() {
       if(categories!=null)
           return categories.size();
       else
           return 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
      private final   TextView categoryName, txtidcate;
      private final   ImageView cateroryPic;
      private final   ConstraintLayout mainLayoutCate;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txtidcate = itemView.findViewById(R.id.txtidcate);
            categoryName= itemView.findViewById(R.id.categoryName);
            cateroryPic = itemView.findViewById(R.id.categoryPic);
            mainLayoutCate = itemView.findViewById(R.id.mainLayoutCate);
        }

    }
}
