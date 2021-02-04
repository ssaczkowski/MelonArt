package digitalhouse.com.a0319cpmoacn01arce_4.controller;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.com.a0319cpmoacn01arce_4.model.ScoreItem;
import digitalhouse.com.a0319cpmoacn01arce_4.util.ContextUtil;

public class ScoreItemController {


    public void updateScoreItemsDB(ArrayList<ScoreItem> scoreItems) {

        for(int i=0 ; i < scoreItems.size(); i ++) {
            ScoreItem scoreItem = scoreItems.get(i);
            ContextUtil.getDB().scoreItemRoomDao().insertScoreItem(new ScoreItem(scoreItem.getRanking(), scoreItem.getScore(), scoreItem.getUserName(), scoreItem.getImageFlag()));
        }
    }

    public  List<ScoreItem> getScoreItemsDB() {
        return ContextUtil.getDB().scoreItemRoomDao().getAllScoreItems();
    }


    public void insertScoreItemDB(ScoreItem scoreItem) {
        ContextUtil.getDB().scoreItemRoomDao().insertScoreItem(scoreItem);
    }

    public void deleteAll() {
        ContextUtil.getDB().scoreItemRoomDao().deleteAll();
    }



}
