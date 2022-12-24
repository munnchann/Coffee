package Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import Domain.Address;

@Dao
public interface AddressDao {
    @Insert
    void InsertAddress(Address address);

    @Query("SELECT * from address")
    List<Address> getAllAddress();

    @Query("SELECT EXISTS(SELECT * FROM address WHERE address_user =:address_user)")
    Boolean isexist(String address_user);
}
