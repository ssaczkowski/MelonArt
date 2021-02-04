package digitalhouse.com.a0319cpmoacn01arce_4.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScoreItemList {

    @SerializedName("scoreitems")
    @Expose
    private List<ScoreItem> scoreItems = null;

    public List<ScoreItem> getScoreItems() {
        return scoreItems;
    }

    public void setScoreItems(List<ScoreItem> scoreItems) {
        this.scoreItems = scoreItems;
    }

}