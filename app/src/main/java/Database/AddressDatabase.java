package Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;

import Domain.Address;

@Database(entities = {Address.class}, version = 2)
public abstract class AddressDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "address";
    private static AddressDatabase instance;

    public static synchronized AddressDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AddressDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
    public abstract AddressDao addressDao();
}
