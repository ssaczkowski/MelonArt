package digitalhouse.com.a0319cpmoacn01arce_4.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ScoreItem {

    @PrimaryKey
    private Integer id;

    @ColumnInfo(name = "ranking")
    private String ranking;

    @ColumnInfo(name = "score")
    private int score;

    @ColumnInfo(name = "username")
    private String userName;

    @ColumnInfo(name = "imageflag")
    private String imageFlag;

    public ScoreItem(String ranking, int score, String userName, String imageFlag) {
        this.ranking = ranking;
        this.score = score;
        this.userName = userName;
        this.imageFlag = imageFlag;
        this.id = null;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageFlag() {
        return imageFlag;
    }

    public void setImageFlag(String imageFlag) {
        this.imageFlag = imageFlag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
