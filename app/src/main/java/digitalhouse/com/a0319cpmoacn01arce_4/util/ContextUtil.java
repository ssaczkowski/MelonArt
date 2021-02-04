package digitalhouse.com.a0319cpmoacn01arce_4.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;

import androidx.room.Room;

import com.google.firebase.auth.FirebaseAuth;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import digitalhouse.com.a0319cpmoacn01arce_4.R;
import digitalhouse.com.a0319cpmoacn01arce_4.config.AppDataBase;
import digitalhouse.com.a0319cpmoacn01arce_4.controller.ScoreItemController;
import digitalhouse.com.a0319cpmoacn01arce_4.controller.UserController;
import digitalhouse.com.a0319cpmoacn01arce_4.model.ScoreItem;
import digitalhouse.com.a0319cpmoacn01arce_4.model.User;
import digitalhouse.com.a0319cpmoacn01arce_4.view.homePage.MainActivity;

public class ContextUtil {

    private static ContextUtil contextUtil;
    private static AppDataBase db;

    private static MediaPlayer mp;

    private  static ProgressDialog progress;


    public static Context context;

    public static User currentUser;

    public static ScoreItemController scoreItemController;
    public static UserController userController;

    public static ContextUtil getInstance(Context ctx) {
        if(contextUtil == null){
            return contextUtil = new ContextUtil(ctx);
        }
        return contextUtil;
    }

    private ContextUtil(Context ctx) {
        context = ctx;
        db = Room.databaseBuilder(context,AppDataBase.class, "database-grupo-cuatro2")
                .allowMainThreadQueries().build();
        scoreItemController = new ScoreItemController();
        userController = new UserController();
    }

    public static void insertUser(User user) {
        userController.insertUserDB(user);
    }

    public static void insertScoreItem(ScoreItem scoreItem) {

        scoreItemController.insertScoreItemDB(scoreItem);
    }

    public static boolean existsScore() {

        List<ScoreItem> scoreItemsDB = scoreItemController.getScoreItemsDB();
        for (int i = 0 ; i < scoreItemsDB.size() ; i++) {
            if(scoreItemsDB.get(i).getUserName().equals(getNameByAuthId())){
                return  true;
            }
        }
        return  false;
    }

    public static ScoreItem getScoreItem() {
        List<ScoreItem> scoreItemsDB = scoreItemController.getScoreItemsDB();
        for (int i = 0 ; i < scoreItemsDB.size() ; i++) {
            if(scoreItemsDB.get(i).getUserName().equals(getNameByAuthId())){
                return  scoreItemsDB.get(i);
            }
        }
        return null;
    }

    public static ArrayList<ScoreItem> getScoreList(){
        ArrayList<ScoreItem> valueList = new ArrayList<ScoreItem>( scoreItemController.getScoreItemsDB());
        return revertList(organizedByScoreList(valueList));
    }

    public static String getNameByAuthId() {
            return FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

    }

    public static String getImageCurrentUser() {
        return String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl());
    }

    public static void showLoading(Context context, String title, String message, Boolean isCancelable){
        progress = new ProgressDialog(context);
        progress.setTitle(title);
        progress.setMessage(message);
        progress.setCancelable(isCancelable);
        progress.show();
    }

    public static void dismissLoading(){
        if(progress!= null){
            progress.dismiss();
        }
    }

    public static void incrementScore(int value){
        ScoreItem scoreItem = getScoreItem();
        int newValue = scoreItem.getScore() + value;
        scoreItem.setScore(newValue);

        deleteAndUpdateScoreItem(scoreItem);

    }

    private static void deleteAndUpdateScoreItem(ScoreItem scoreItem) {

        ArrayList<ScoreItem> currentListBD = getScoreList();
        for (int i = 0 ; i < currentListBD.size();i++){
            if(currentListBD.get(i).getUserName().equals(scoreItem.getUserName())){
                currentListBD.set(i,scoreItem);
            }
        }
        scoreItemController.deleteAll();
        fillScoreItemsBD(currentListBD);

    }

    private static void fillScoreItemsBD(ArrayList<ScoreItem> list) {
        for(int i = 0 ; i < list.size(); i ++){
            scoreItemController.insertScoreItemDB(list.get(i));
        }
    }

    public static void decrementScore(int value){

        ScoreItem scoreItem = getScoreItem();
        int newValue = scoreItem.getScore() - value;

        if(newValue >= 0) {
            scoreItem.setScore(newValue);

            deleteAndUpdateScoreItem(scoreItem);
        }else {
            newValue = 0 ;

            scoreItem.setScore(newValue);

            deleteAndUpdateScoreItem(scoreItem);
        }

    }



    public static ArrayList<ScoreItem> revertList(ArrayList<ScoreItem> list) {
        ArrayList<ScoreItem> listreturn = new ArrayList<>();
        for (int i = list.size()-1 ; i >= 0 ;i--){
            listreturn.add(list.get(i));
        }

        return listreturn;
    }

    public static ArrayList<ScoreItem> organizedByScoreList(ArrayList<ScoreItem> list) {
        Collections.sort(list, new Comparator<ScoreItem>() {
            //Collator collator = Collator.getInstance();

            public int compare(ScoreItem o1, ScoreItem o2) {
                return (o1.getScore() - o2.getScore());
            }
        });
        return list;
    }

    
    public static boolean isNetConnected(Context context) {
        if(context != null) {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

            return activeNetworkInfo != null && activeNetworkInfo.isConnected();

        }return true;
    }

    public static AppDataBase getDB() {
        return AppDataBase.getInstanceDB(context);
    }

    public static String getUserId() {
        if(FirebaseAuth.getInstance().getCurrentUser() != null ) {
            return FirebaseAuth.getInstance().getCurrentUser().getUid();
        }else{
            return "";
        }
    }

    public static User getCurrentUser(){
        return currentUser;
    }


    public static void startSound(Context context,int sound) {
        mp = MediaPlayer.create(context, sound);

        if(mp == null) {
        } else {
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mediaplayer) {
                    mediaplayer.stop();
                    mediaplayer.release();
                }
            });
            mp.start();

        }
    }

    public static void startSoundDelay(Context context,int sound,int miliseconds) {
        new Handler().postDelayed(() -> {
        mp = MediaPlayer.create(context, sound);

        if(mp == null) {
        } else {
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mediaplayer) {
                    mediaplayer.stop();
                    mediaplayer.release();
                }
            });
            mp.start();

        }

        }, miliseconds);
    }




    public static void stopSound() {
        if (mp != null)
            mp.stop();
    }

    public static boolean isLoading() {
        return progress.isShowing();
    }
}
