package api;

import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

import Domain.CartDomain;
import Domain.MenuDomain;

public class Cart {
    public static List<CartDomain> items = new ArrayList<CartDomain>();

    public static void insert(CartDomain cartDomain){
        items.add(cartDomain);
    }

    public static void remove (MenuDomain menuDomain){
        int index = getIndex(menuDomain);
        items.remove(index);
    }

    public static  double total(){
        double sum = 0;
//        for(CartDomain cartDomain: items){
//            sum += cartDomain.getMenuDomain().getPrice() * cartDomain.getQuantity();
//        }
        for(int i = 0; i < items.size(); i++)
            sum = sum+(items.get(i).getPrice() * items.get(i).getQuantity());
        return  sum;
    }

    public static boolean isExists(MenuDomain menuDomain){
        return getIndex(menuDomain) != -1;
    }

    private static int getIndex(MenuDomain menuDomain){
        for (int i = 0; i< items.size(); i++){
            if(items.get(i).getId() == menuDomain.getId()){
                return i;
            }
        }
        return -1;
    }


}
