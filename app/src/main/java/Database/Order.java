package Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import Domain.OrderDomain;

@Dao
public interface Order {

    @Insert
    void InsertOrd(OrderDomain orderDomain);

    @Query("SELECT * FROM ords ")
    List<OrderDomain> getAll();
}
