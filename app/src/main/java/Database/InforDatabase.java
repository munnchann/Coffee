package Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import Domain.InforUser;

@Database(entities = {InforUser.class}, version = 2)
public abstract class InforDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "infor";
    private static InforDatabase instance;

    public static synchronized InforDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),InforDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
    public abstract InforDao inforDao();
}
