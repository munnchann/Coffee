package Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import Domain.OrderDetailDomain;

@Database(entities = {OrderDetailDomain.class}, version = 2)
public abstract class Ord_Detail_Database extends RoomDatabase {
    private static final String DATABASE_NAME = "ord_detail";
    private static Ord_Detail_Database instance;

    public static synchronized Ord_Detail_Database getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),Ord_Detail_Database.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
    public abstract Ord_Detail_Dao ord_detail_dao();
}
