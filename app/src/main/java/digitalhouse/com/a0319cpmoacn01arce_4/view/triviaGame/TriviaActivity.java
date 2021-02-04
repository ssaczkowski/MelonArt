package digitalhouse.com.a0319cpmoacn01arce_4.view.triviaGame;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.com.a0319cpmoacn01arce_4.R;
import digitalhouse.com.a0319cpmoacn01arce_4.model.Cancion;
import digitalhouse.com.a0319cpmoacn01arce_4.model.Data;
import digitalhouse.com.a0319cpmoacn01arce_4.util.ContextUtil;
import digitalhouse.com.a0319cpmoacn01arce_4.view.detailPage.DetalleCancionFragment;
import digitalhouse.com.a0319cpmoacn01arce_4.view.gamePage.GeneroFragment;
import digitalhouse.com.a0319cpmoacn01arce_4.view.homePage.HomeFragment;

public class TriviaActivity extends AppCompatActivity implements TriviaAdapter.TriviaOnItemSelectedListener, DetalleCancionFragment.OnFragmentDetalleCancionInteractionListener, GeneroFragment.GeneroInterface {
    private DatabaseReference miDatabase;
    private FirebaseDatabase mDatabase;
    private String listaCancionesPorGenero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        miDatabase = FirebaseDatabase.getInstance().getReference();

        getSupportActionBar().hide();

        GeneroFragment generoFragment = new GeneroFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.contenedorTrivia,generoFragment);
        transaction.commit();



    }

    @Override
    public void onBackPressed(){
        int count = getSupportFragmentManager().getBackStackEntryCount();
           getSupportFragmentManager().getFragments().get(0);
        if (count == 0) {
            super.onBackPressed();
            } else if (count >= 1) {
            getSupportActionBar().hide();
            GeneroFragment generoFragment = new GeneroFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.contenedorTrivia,generoFragment);
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            transaction.commit();

        }

    }


    @Override
    public void TriviaonItemClick(Cancion cancion, Cancion cancionRandom) {
        if (cancion.getId() == cancionRandom.getId()) {

            ContextUtil.incrementScore(100);
            changeScore();
            Data data = new Data();
            Integer posicion = 0;


            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.alert_dialog);
            dialog.show();
            TriviaFragment.reproductorMusica.stop();
            ContextUtil.startSound(this,R.raw.good);
            dialog.findViewById(R.id.button_next_game).setOnClickListener (new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    ContextUtil.stopSound();

                    Bundle bundle = new Bundle();
                    bundle.putString("lista",listaCancionesPorGenero);

                    TriviaFragment triviaFragment = new TriviaFragment();
                    triviaFragment.setArguments(bundle);
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.contenedorTrivia,triviaFragment).addToBackStack(null);
                    transaction.commit();

                } });

            dialog.findViewById(R.id.alert_dialog_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    ContextUtil.stopSound();
                    HomeFragment detalleCancionFragment = HomeFragment.newInstance();
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.contenedorTrivia, detalleCancionFragment).addToBackStack(null);
                    transaction.commit();
                }
            });





        } else {
            TriviaFragment.reproductorMusica.stop();
            ContextUtil.startSoundDelay(this,R.raw.fail,500);

            ContextUtil.decrementScore(11);
            changeScore();

            new Handler().postDelayed(() -> {
                Integer posicion = 0;
                Bundle bundle = new Bundle();
                bundle.putString("lista", listaCancionesPorGenero);
                Data data = new Data();
                List<Cancion> canciones = new ArrayList<>();
                canciones.add(cancionRandom);
                data.setData(canciones);

                FregmentResultTrivia fregmentResultTrivia = FregmentResultTrivia.newInstance(cancionRandom.getAlbum().getNombre(), cancionRandom.getTitulo(), cancionRandom.getArtista().getNombre(), cancionRandom.getAlbum().getImagen(), cancionRandom.getPreview(), cancionRandom.getId(), data, posicion, listaCancionesPorGenero);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.contenedorTrivia, fregmentResultTrivia).addToBackStack(null);
                transaction.commit();
            }, 3000);


        }
    }

    private void changeScore() {

        DatabaseReference ref = miDatabase.child("Users").child(ContextUtil.getUserId());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null){
                    miDatabase.child("Users").child(ContextUtil.getUserId()).child("name").setValue(ContextUtil.getScoreItem().getUserName());
                    miDatabase.child("Users").child(ContextUtil.getUserId()).child("score").setValue(ContextUtil.getScoreItem().getScore());
                    miDatabase.child("Users").child(ContextUtil.getUserId()).child("nacionality").setValue("argentina");

                }else {
                    miDatabase.child("Users").child(ContextUtil.getUserId()).child("score").setValue(ContextUtil.getScoreItem().getScore());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }


    @Override
    public void onClickGenero(String listaGenero) {
        Bundle bundle = new Bundle();
        bundle.putString("lista",listaGenero);
        listaCancionesPorGenero = listaGenero;

        TriviaFragment triviaFragment = new TriviaFragment();
        triviaFragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contenedorTrivia,triviaFragment).addToBackStack(null);
        transaction.commit();

    }
}
