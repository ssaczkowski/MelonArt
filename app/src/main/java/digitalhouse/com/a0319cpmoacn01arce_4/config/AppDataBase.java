package digitalhouse.com.a0319cpmoacn01arce_4.config;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import digitalhouse.com.a0319cpmoacn01arce_4.dao.ScoreItemRoomDao;
import digitalhouse.com.a0319cpmoacn01arce_4.dao.UserRoomDao;
import digitalhouse.com.a0319cpmoacn01arce_4.model.ScoreItem;
import digitalhouse.com.a0319cpmoacn01arce_4.model.User;


@Database(entities = {ScoreItem.class, User.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract ScoreItemRoomDao scoreItemRoomDao();
    public abstract UserRoomDao userRoomDao();

    public static AppDataBase getInstanceDB(Context context){
        AppDataBase db = Room.databaseBuilder(context,AppDataBase.class, "database-grupo-cuatro2")
                .allowMainThreadQueries().build();
        return db;
    }
}

