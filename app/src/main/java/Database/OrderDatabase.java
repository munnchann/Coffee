package Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import Domain.CartDomain;
import Domain.OrderDomain;

@Database(entities = {OrderDomain.class}, version = 2)
public abstract class OrderDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "ords";
    private static OrderDatabase instance;

    public static synchronized OrderDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),OrderDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
    public abstract Order orderDao();
}
