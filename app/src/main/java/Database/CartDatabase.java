package Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import Domain.CartDomain;

@Database(entities = {CartDomain.class}, version = 2)
public abstract class CartDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "cart";
    private static CartDatabase instance;

    public static synchronized CartDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),CartDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
    public abstract CartDao cartDao();
}
