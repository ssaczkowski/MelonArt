package digitalhouse.com.a0319cpmoacn01arce_4.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import digitalhouse.com.a0319cpmoacn01arce_4.model.ScoreItem;


@Dao
public interface ScoreItemRoomDao {

    @Insert
    void insertScoreItem(ScoreItem scoreItem);

    @Query("SELECT * FROM scoreitem")
    List<ScoreItem> getAllScoreItems();

    @Query("select * from scoreitem where username = ':usernameScoreItem'")
    List<ScoreItem> findById();


    @Query("delete from scoreitem")
    void deleteAll();


}

