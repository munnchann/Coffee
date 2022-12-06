package Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Domain.CartDomain;

@Dao
public interface CartDao {

    @Insert
    void InsertCart(CartDomain cartDomain);

    @Query("SELECT * from cart")
    List<CartDomain> getAllCart();

    @Query("SELECT EXISTS(SELECT * FROM cart WHERE id =:id)")
    Boolean isexist(int id);

    @Query("SELECT * FROM cart WHERE id = :id")
    List<CartDomain> checkIdExists(int id);

    @Query("DELETE FROM cart WHERE id=:id")
    void deleteCart(int id);
    @Update
    void updateCart(CartDomain cartDomain);
    @Query("UPDATE cart SET quantity=:quantity WHERE id=:id")
    void updateQty(int quantity, int id);

    @Query("DELETE FROM cart")
    void deleteAll();
}
