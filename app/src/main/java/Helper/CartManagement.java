package Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.uipart1.Activity.ShowDetailProduct;

import java.util.ArrayList;

import Domain.MenuDomain;

public class CartManagement {
    private Context context;
    private TinyDB tinyDB;


    public CartManagement(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }
    public void insertOrd(MenuDomain item){
        ArrayList<MenuDomain> listMenu = getListCart();
        boolean existAlready = false;
        int n = 0;
        for(int i = 0; i< listMenu.size(); i++){
            if(listMenu.get(i).getName().equals(item.getName())){
                existAlready = true;
                n = i;
                break;
            }
        }

        if(existAlready){
            listMenu.get(n).setNumberInCart(item.getNumberInCart());
        }else {
            listMenu.add(item);
        }
        tinyDB.putListObject("CartList", listMenu);
        Toast.makeText(context, "Added To Your Cart", Toast.LENGTH_SHORT).show();
    }

    private ArrayList<MenuDomain> getListCart() {
        return tinyDB.getListObject("CartList");
    }
}
