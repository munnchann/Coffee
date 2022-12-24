package Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


import Domain.InforUser;

@Dao
public interface InforDao {

    @Insert
    void InsertInformationUser(InforUser inforUser);

    @Query("SELECT * from infor")
    List<InforUser> getUser();

    @Query("DELETE FROM infor")
    void deleteAll();
}
