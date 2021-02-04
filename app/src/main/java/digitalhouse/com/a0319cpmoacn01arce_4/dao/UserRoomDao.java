package digitalhouse.com.a0319cpmoacn01arce_4.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import digitalhouse.com.a0319cpmoacn01arce_4.model.User;


@Dao
public interface UserRoomDao {

    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Query("select * from user where authid = ':authidUser'")
    List<User> findById();

    @Query("delete from scoreitem")
    void deleteAll();

}

