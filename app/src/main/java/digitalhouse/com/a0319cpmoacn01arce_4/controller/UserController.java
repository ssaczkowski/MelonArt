package digitalhouse.com.a0319cpmoacn01arce_4.controller;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.com.a0319cpmoacn01arce_4.model.User;
import digitalhouse.com.a0319cpmoacn01arce_4.util.ContextUtil;

public class UserController {

    public void updateUsersDB(ArrayList<User> users) {

        for(int i=0 ; i < users.size(); i ++) {
            User user = users.get(i);
            ContextUtil.getDB().userRoomDao().insertUser(new User( user.getId(), user.getAuthID(), user.getName(), user.getImageProfile()));
        }
    }

    public List<User> getUserDB() {
        return ContextUtil.getDB().userRoomDao().getAllUsers();
    }

    public void insertUserDB(User user) {
        ContextUtil.getDB().userRoomDao().insertUser(user);
    }

}
