package digitalhouse.com.a0319cpmoacn01arce_4.dao.impl;


import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import digitalhouse.com.a0319cpmoacn01arce_4.model.User;
import digitalhouse.com.a0319cpmoacn01arce_4.model.UserList;

public class UserDaoImplRoom {

    public List<User> getArtists(Context context){

        List<User> users = new ArrayList<>();

        AssetManager assetManager = context.getAssets();
        try {

            InputStream inputStream = assetManager.open("user.json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            Gson gson = new Gson();


            UserList list = gson.fromJson(bufferedReader, UserList.class);
            users = list.getUsers();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;

    }
}
