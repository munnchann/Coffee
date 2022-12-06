package Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import Domain.OrderDetailDomain;
import Domain.OrderDomain;
@Dao
public interface Ord_Detail_Dao {
    @Insert
    void InsertOrd_Detail(OrderDetailDomain orderDetailDomain);

    @Query("SELECT * FROM ord_detail ")
    List<OrderDetailDomain> getAll();
}
