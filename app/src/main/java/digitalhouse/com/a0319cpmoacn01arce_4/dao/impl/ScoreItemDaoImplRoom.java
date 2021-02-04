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

import digitalhouse.com.a0319cpmoacn01arce_4.model.ScoreItem;
import digitalhouse.com.a0319cpmoacn01arce_4.model.ScoreItemList;


public class ScoreItemDaoImplRoom {

    public List<ScoreItem> getScorItems(Context context){

        List<ScoreItem> scoreItems = new ArrayList<>();

        AssetManager assetManager = context.getAssets();
        try {

            InputStream inputStream = assetManager.open("scoreitem.json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            Gson gson = new Gson();


            ScoreItemList list = gson.fromJson(bufferedReader, ScoreItemList.class);
            scoreItems = list.getScoreItems();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return scoreItems;

    }

}
