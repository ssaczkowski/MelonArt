package digitalhouse.com.a0319cpmoacn01arce_4.view.homePage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import digitalhouse.com.a0319cpmoacn01arce_4.R;
import digitalhouse.com.a0319cpmoacn01arce_4.controller.CancionController;
import digitalhouse.com.a0319cpmoacn01arce_4.model.Cancion;
import digitalhouse.com.a0319cpmoacn01arce_4.model.ScoreItem;
import digitalhouse.com.a0319cpmoacn01arce_4.util.CallBackGeneric;
import digitalhouse.com.a0319cpmoacn01arce_4.util.ContextUtil;
import digitalhouse.com.a0319cpmoacn01arce_4.view.triviaGame.TriviaActivity;


public class HomeFragment extends Fragment {


    private OnFragmentHomeInteractionListener mListener;
    public static final String TAG = "HomeFragment";
    private RecyclerView scoreListRecycler;
    private LinearLayoutManager layoutmanager;
    private ArrayList<ScoreItem> list;
    private DatabaseReference miDatabase;


    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        list = new ArrayList<>();


        scoreListRecycler = view.findViewById(R.id.scoreList);
        layoutmanager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        scoreListRecycler.setLayoutManager(layoutmanager);
        scoreListRecycler.setAdapter(new ScoreAdapter(list));

        ContextUtil.dismissLoading();

        FloatingActionButton btnTrivia;
        btnTrivia = view.findViewById(R.id.btnTrivia);
        btnTrivia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), TriviaActivity.class);
                startActivity(intent);
                getActivity().getSupportFragmentManager().popBackStack();

            }
        });

        ArrayList<ScoreItem> list = new ArrayList<>();
        if (ContextUtil.isNetConnected(getActivity())) {
            getForInternet();
        } else {
            list = ContextUtil.getScoreList();
        }

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentHomeInteractionListener) {
            mListener = (OnFragmentHomeInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {

        ArrayList<ScoreItem> list = new ArrayList<>();
        if (ContextUtil.isNetConnected(getActivity())) {
            getForInternet();

        } else {
            list = ContextUtil.getScoreList();
        }

        new Handler().postDelayed(() -> {
        ContextUtil.dismissLoading();

        }, 4000);
        super.onResume();

    }

    private void getForInternet() {
        ContextUtil.showLoading(getActivity(),"",getString(R.string.loading),true);
        miDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = miDatabase.child("Users");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        list = ContextUtil.revertList(ContextUtil.organizedByScoreList(collectScoreItems((Map<String, Object>) dataSnapshot.getValue())));

                        layoutmanager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        scoreListRecycler.setLayoutManager(layoutmanager);
                        scoreListRecycler.setAdapter(new ScoreAdapter(list));
                        ContextUtil.dismissLoading();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                        Log.d("ERROR:",databaseError.toString());

                    }
                });

    }

    private ArrayList<ScoreItem> collectScoreItems(Map<String, Object> users) {

        ArrayList<ScoreItem> scoreItems = new ArrayList<>();
        if (users != null) {

            //iterate through each user, ignoring their UID
            for (Map.Entry<String, Object> entry : users.entrySet()) {

                //Get user map
                Map singleUser = (Map) entry.getValue();
                //Get phone field and append to list
                String nacionality = String.valueOf(getImageNacionality((String) singleUser.get("nacionality")));
                scoreItems.add(new ScoreItem("", Integer.parseInt(String.valueOf(singleUser.get("score"))),
                        (String) singleUser.get("name"), nacionality));
            }
        }

        return scoreItems;
    }


    private int getImageNacionality(String nacionality) {
        switch (nacionality) {
            case "argentina":
                return R.drawable.ic_argentina;
            case "venezuela":
                return R.drawable.ic_venezuela;
            case "uruguay":
                return R.drawable.ic_uruguay;
            case "espana":
                return R.drawable.ic_spain;
        }
        return R.drawable.ic_argentina;
    }

    public interface OnFragmentHomeInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

